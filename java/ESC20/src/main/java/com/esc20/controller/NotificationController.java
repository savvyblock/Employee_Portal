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
import com.esc20.service.IndexService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/notifications")
public class NotificationController extends IndexController{

    @Autowired
    private IndexService indexService;
	
	@RequestMapping("notifications")
    public ModelAndView getNotifications(HttpServletRequest req){
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        List<BeaAlert> unReadList = this.indexService.getUnReadAlert(demo.getEmpNbr());
        mav.setViewName("notifications");
        mav.addObject("user", user);
        mav.addObject("unReadList",unReadList);
        return mav;
    }

    @RequestMapping("markAsRead")
    public ModelAndView markAsRead(HttpServletRequest req,String id){
        HttpSession session = req.getSession();
        BeaUsers user = (BeaUsers)session.getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(null == user){
        	return this.getIndexPage(mav);
        }
        Long alertId = Long.parseLong(id);
        BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
        this.indexService.deleteAlert(id);
        List<BeaAlert> unReadList = this.indexService.getUnReadAlert(demo.getEmpNbr());
        mav.setViewName("notifications");
        mav.addObject("user", user);
        mav.addObject("unReadList",unReadList);
        return mav;
    }
    
    @RequestMapping(value = "getBudgeCount", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> getBudgeCount(HttpServletRequest req) throws Exception{
    	HttpSession session = req.getSession();
    	Map<String, String> res = new HashMap<>();
    	BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
    	if(demo==null)
    		return null;
    	Integer count = this.indexService.getBudgeCount(demo.getEmpNbr());
        res.put("count", count.toString());
        return res;
    }

    @RequestMapping(value = "getTop5Alerts", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, JSONArray> getTop5Alerts(HttpServletRequest req) throws Exception{
    	HttpSession session = req.getSession();
    	Map<String, JSONArray> result = new HashMap<>();
    	BhrEmpDemo demo = ((BhrEmpDemo)session.getAttribute("userDetail"));
    	if(demo==null)
    		return null;
    	List<BeaAlert> top5 = this.indexService.getTop5Alerts(demo.getEmpNbr());
        JSONArray res = new JSONArray();
        JSONObject obj = new JSONObject();
        for(BeaAlert item: top5) {
        	obj = new JSONObject();
        	obj.put("msgContent", item.getMsgContent());
        	res.add(obj);
        }
        result.put("list", res);
        return result;
    }
}
