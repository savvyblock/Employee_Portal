package com.esc20.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaUsers;
import com.esc20.nonDBModels.ApproveTravelRequest;
import com.esc20.nonDBModels.ApproveTravelRequestCommand;
import com.esc20.service.ApproveTravelRequestService;

@Controller
@SessionAttributes("ApproveTravelRequestCommand")
@RequestMapping(value= {"**/approveTravelRequest/approveTravelRequestList"})
public class ApproveTravelRequestController extends BaseSupervisorController {

	@Autowired
	private ApproveTravelRequestService service;

	private static final String module = "Approve Travel Request";
	private static final String commandObject = "ApproveTravelRequestCommand";
	private static final String viewPath = "/supervisor/approveTravelRequestList";


	private String getUserEmpNbr(HttpServletRequest request) {
		return ((BeaUsers) request.getSession().getAttribute("user")).getEmpNbr();
	}
	
	private String getsrvcId(HttpServletRequest request) {
		return (String) request.getSession().getAttribute("srvcId");
		
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView approveGrantRequest(HttpServletRequest request){


		/* *************************************************************************
		 * Initialize Command Object
		 **************************************************************************/
		ApproveTravelRequestCommand command = new ApproveTravelRequestCommand();
		
		command.setApproveTravelRequests(service.getTravelRequestApprovalSummaries(getUserEmpNbr(request)));
		
		
		/* *************************************************************************
		 * Return Model & View
		 **************************************************************************/
		return new ModelAndView(viewPath, new ModelMap().addAttribute(commandObject, command));
	}
	
	
	@RequestMapping(method = RequestMethod.POST, params = "approveButtonTravelRequest")
	public ModelAndView approveTravelRequest(@ModelAttribute(commandObject) ApproveTravelRequestCommand command,
			BindingResult bindingResultErrors, HttpServletRequest request) {
		
		service.approveRequest( command.getApproveTravelRequests(),  command.getFinalApproverTravelRequests(), command.isAnySelected(), getsrvcId(request), getUserEmpNbr(request),  bindingResultErrors);
		
		command.setShowFinalApproverPopup( command.getFinalApproverTravelRequests().size() > 0 );
		
		if(command.getFinalApproverTravelRequests().size() == 0) {
			this.clearValues(command); 
			command.setApproveTravelRequests(service.getTravelRequestApprovalSummaries(getUserEmpNbr(request)));
		}
		
		return new ModelAndView(viewPath, new ModelMap().addAttribute(commandObject, command));
	}
	
	@RequestMapping(method = RequestMethod.POST, params = "returnTravelRequest")
	public ModelAndView returnTravelRequest(@ModelAttribute(commandObject) ApproveTravelRequestCommand command,
			BindingResult bindingResultErrors, HttpServletRequest request) {
		
		service.returnRequest(command.getApproveTravelRequests(), command.isAnySelected(), getsrvcId(request), getUserEmpNbr(request));
		
		command.setApproveTravelRequests(service.getTravelRequestApprovalSummaries(getUserEmpNbr(request)));
		
		this.clearValues(command);
		
		return new ModelAndView(viewPath, new ModelMap().addAttribute(commandObject, command));
	}
	

	@RequestMapping("retrieve")
	public ModelAndView retrieve(HttpServletRequest req, ApproveTravelRequestCommand command){

		return new ModelAndView(viewPath, new ModelMap().addAttribute(commandObject, command)); 
	}	
	
	
	private void clearValues(ApproveTravelRequestCommand command) {
		
		command.setAnySelected(false);
		command.setSelectAll(false);
		command.setShowFinalApproverPopup(false);
		command.setFinalApproverTravelRequests(new ArrayList<ApproveTravelRequest>());
		
		
	}

}
