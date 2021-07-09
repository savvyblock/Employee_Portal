package com.esc20.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esc20.model.BeaUsers;
import com.esc20.nonDBModels.Fund;
import com.esc20.service.FundService;

@RestController
@RequestMapping("fundRequest")
public class FundController {

	@Autowired
	private FundService fundService;

	@RequestMapping("funds")
	public List<Fund> getAccountCodes(HttpServletRequest request) {

		// Get User Information from Session.
		HttpSession session = request.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");

		final String empNbr = user.getEmpNbr();
		final String fileId = "C";		

		String type = request.getParameter("type");
		type = type == null ? "" : type.trim();

		String search = StringUtils.trim(request.getParameter("q"));
		search = search == null ? "" : search.trim();

		if (type.equalsIgnoreCase("fund")) {
			return fundService.getFundInformation(fileId, empNbr);
		}
		else if (type.equalsIgnoreCase("function")) {
			return fundService.getFunctionInformation(fileId, empNbr);
		}
		else if (type.equalsIgnoreCase("object")) {
			return fundService.getObjectInformation(fileId, empNbr);
		}
		else if (type.equalsIgnoreCase("subobject")) {
			return fundService.getSubobjectInformation(fileId, empNbr);
		}
		else if (type.equalsIgnoreCase("organization")) {
			return fundService.getOrganizationInformation(fileId, empNbr);
		}
		else if (type.equalsIgnoreCase("program")) {
			return fundService.getProgramInformation(fileId, empNbr);
		}
		else if (type.equalsIgnoreCase("education")) {
			return fundService.getEducationalSpanInformation(fileId, empNbr);
		}
		else if (type.equalsIgnoreCase("project")) {
			return fundService.getProjectDetailInformation(fileId, empNbr);
		}
		else if (type.equalsIgnoreCase("trvlAccountCodeAutoComplete")) {
			return fundService.getAccountCodeAutoComplete(fileId, empNbr, search);
		}

		return new ArrayList<Fund>();
	}
}