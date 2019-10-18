package com.esc20.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaUsers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.Deduction;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.Frequency;
import com.esc20.nonDBModels.Options;
import com.esc20.nonDBModels.PayInfo;
import com.esc20.service.IndexService;
import com.esc20.service.InquiryService;
import com.esc20.service.ReferenceService;
import com.esc20.util.DateUtil;
import com.esc20.util.StringUtil;

@Controller
@RequestMapping("/deductions")
public class DeductionsController {
	@Autowired
	private IndexService indexService;

	@Autowired
	private InquiryService service;

	@Autowired
	private ReferenceService referenceService;

	@RequestMapping("deductions")
	public ModelAndView getDeductions(HttpServletRequest req) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();

		BeaUsers user = (BeaUsers) session.getAttribute("user");
		BhrEmpDemo userDetail = this.indexService.getUserDetail(user.getEmpNbr());
		Options options = this.indexService.getOptions();
		Boolean isSupervisor = this.indexService.isSupervisor(user.getEmpNbr(), options.getUsePMISSpvsrLevels());
		Boolean isTempApprover = this.indexService.isTempApprover(user.getEmpNbr());
		session.setAttribute("isSupervisor", isSupervisor);
		session.setAttribute("isTempApprover", isTempApprover);
		String district = (String) session.getAttribute("districtId");
		District districtInfo = this.indexService.getDistrict(district);
		userDetail.setEmpNbr(user.getEmpNbr());
		userDetail.setDob(DateUtil.formatDate(userDetail.getDob(), "yyyyMMdd", "MM-dd-yyyy"));
		List<Code> gens = referenceService.getGenerations();
		for (Code gen : gens) {
			if (userDetail.getNameGen() != null
					&& gen.getCode().trim().equals(userDetail.getNameGen().toString().trim())) {
				userDetail.setGenDescription(gen.getDescription());
			}
		}

		String phone = districtInfo.getPhone();
		districtInfo.setPhone(
				StringUtil.left(phone, 3) + "-" + StringUtil.mid(phone, 4, 3) + "-" + StringUtil.right(phone, 4));

		session.setAttribute("options", options);
		session.setAttribute("userDetail", userDetail);
		session.setAttribute("companyId", district);
		session.setAttribute("options", options);
		session.setAttribute("district", districtInfo);

		// BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		String employeeNumber = userDetail.getEmpNbr();
		List<Frequency> frequencies = this.service.getAvailableFrequencies(employeeNumber);
		Map<Frequency, PayInfo> payInfos = this.service.retrievePayInfo(employeeNumber, frequencies);
		Map<Frequency, List<Deduction>> deductions = this.service.retrieveAllDeductions(employeeNumber, frequencies);
		// Options options = this.indexService.getOptions();
		// session.setAttribute("options", options);
		mav.setViewName("/inquiry/deductions");
		mav.addObject("frequencies", frequencies);
		mav.addObject("payInfos", payInfos);
		mav.addObject("deductions", deductions);
		return mav;
	}
}
