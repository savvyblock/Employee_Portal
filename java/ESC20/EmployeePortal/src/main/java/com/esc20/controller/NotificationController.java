package com.esc20.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaAlert;
import com.esc20.model.BeaUsers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.Options;
import com.esc20.service.IndexService;
import com.esc20.service.ReferenceService;
import com.esc20.util.DateUtil;
import com.esc20.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

	@Autowired
	private IndexService indexService;

	@Autowired
	private ReferenceService referenceService;

	private final String module = "Notifications";

	@RequestMapping("notifications")
	public ModelAndView getNotifications(HttpServletRequest req) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		BhrEmpDemo demo = this.indexService.getUserDetail(user.getEmpNbr());
		demo.setEmpNbr(user.getEmpNbr());
		demo.setDob(DateUtil.formatDate(demo.getDob(), "yyyyMMdd", "MM-dd-yyyy"));
		List<Code> gens = referenceService.getGenerations();
		for (Code gen : gens) {
			if (demo.getNameGen() != null && gen.getCode().trim().equals(demo.getNameGen().toString().trim())) {
				demo.setGenDescription(gen.getDescription());
			}
		}
		session.setAttribute("userDetail", demo);
		Options options = this.indexService.getOptions();

		Boolean isSupervisor = this.indexService.isSupervisor(user.getEmpNbr(), options.getUsePMISSpvsrLevels());
		Boolean isTempApprover = this.indexService.isTempApprover(user.getEmpNbr());
		session.setAttribute("isSupervisor", isSupervisor);
		session.setAttribute("isTempApprover", isTempApprover);

		// BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
		List<BeaAlert> unReadList = this.indexService.getUnReadAlert(demo.getEmpNbr());
		mav.setViewName("notifications");
		mav.addObject("user", user);
		mav.addObject("unReadList", unReadList);
		return mav;
	}

	@RequestMapping("markAsRead")
	public ModelAndView markAsRead(HttpServletRequest req, String id) {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (id == null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Mark as Read");
			mav.addObject("errorMsg", "No id provided.");
			return mav;
		}
		BhrEmpDemo demo = this.indexService.getUserDetail(user.getEmpNbr());
		demo.setEmpNbr(user.getEmpNbr());
		demo.setDob(DateUtil.formatDate(demo.getDob(), "yyyyMMdd", "MM-dd-yyyy"));
		List<Code> gens = referenceService.getGenerations();
		for (Code gen : gens) {
			if (demo.getNameGen() != null && gen.getCode().trim().equals(demo.getNameGen().toString().trim())) {
				demo.setGenDescription(gen.getDescription());
			}
		}

		session.setAttribute("userDetail", demo);
		// BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
		this.indexService.deleteAlert(id);
		List<BeaAlert> unReadList = this.indexService.getUnReadAlert(demo.getEmpNbr());
		mav.setViewName("notifications");
		mav.addObject("user", user);
		mav.addObject("unReadList", unReadList);
		return mav;
	}

	@RequestMapping(value = "getBudgeCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> getBudgeCount(HttpServletRequest req) throws Exception {
		HttpSession session = req.getSession();
		Map<String, String> res = new HashMap<>();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		BhrEmpDemo demo = this.indexService.getUserDetail(user.getEmpNbr());
		demo.setEmpNbr(user.getEmpNbr());
		demo.setDob(DateUtil.formatDate(demo.getDob(), "yyyyMMdd", "MM-dd-yyyy"));
		List<Code> gens = referenceService.getGenerations();
		for (Code gen : gens) {
			if (demo.getNameGen() != null && gen.getCode().trim().equals(demo.getNameGen().toString().trim())) {
				demo.setGenDescription(gen.getDescription());
			}
		}

		session.setAttribute("userDetail", demo);
		// BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
		if (demo == null)
			return null;
		Integer count = this.indexService.getBudgeCount(demo.getEmpNbr());
		res.put("count", count.toString());
		return res;
	}

	@RequestMapping(value = "getTop5Alerts", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, JSONArray> getTop5Alerts(HttpServletRequest req) throws Exception {
		HttpSession session = req.getSession();
		Map<String, JSONArray> result = new HashMap<>();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		BhrEmpDemo demo = this.indexService.getUserDetail(user.getEmpNbr());
		demo.setEmpNbr(user.getEmpNbr());
		demo.setDob(DateUtil.formatDate(demo.getDob(), "yyyyMMdd", "MM-dd-yyyy"));
		List<Code> gens = referenceService.getGenerations();
		for (Code gen : gens) {
			if (demo.getNameGen() != null && gen.getCode().trim().equals(demo.getNameGen().toString().trim())) {
				demo.setGenDescription(gen.getDescription());
			}
		}

		session.setAttribute("userDetail", demo);
		// BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
		if (demo == null)
			return null;
		List<BeaAlert> top5 = this.indexService.getTop5Alerts(demo.getEmpNbr());
		JSONArray res = new JSONArray();
		JSONObject obj = new JSONObject();
		for (BeaAlert item : top5) {
			obj = new JSONObject();
			obj.put("msgContent", item.getMsgContent());
			res.add(obj);
		}
		result.put("list", res);
		return result;
	}
}
