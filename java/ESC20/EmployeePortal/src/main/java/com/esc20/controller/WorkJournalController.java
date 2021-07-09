package com.esc20.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BhrEmpDemo;
import com.esc20.model.BeaEmpWrkJrnl;
import com.esc20.nonDBModels.BeaEmpWrkJrnlId;
import com.esc20.nonDBModels.Frequency;
import com.esc20.service.IndexService;
import com.esc20.service.WrkjlService;
import com.esc20.util.DateUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/wrkjl")
public class WorkJournalController {

	@Autowired
	private WrkjlService wrkjlService;
	
	@PostMapping("submitWrkjlRequest")
	@ResponseBody
	public String submitRequest(HttpServletRequest req, @RequestBody JSONObject param) throws ParseException{
		String  data = "";

		try {
		BeaEmpWrkJrnl w = new BeaEmpWrkJrnl();
		BeaEmpWrkJrnlId wId = new BeaEmpWrkJrnlId();

		wId.setEmpNbr(param.getString("empNbr"));
		wId.setWrkDate(param.getString("wrkDate"));
		wId.setJobCode(param.getString("jobCode"));

		w.setComment(param.getString("commnt"));
		w.setEndAmOrPm(param.getString("toTmAp"));
		w.setEndHour(param.getString("toTmHr"));
		w.setEndMinute(param.getString("toTmMin"));
		w.setStartAmOrPm(param.getString("fromTmAp"));
		w.setStartHour(param.getString("fromTmHr"));
		w.setStartMinute(param.getString("fromTmMin"));
		

		//getting pay freq code from label
		
		String payFreq = "";
		if(param.getString("payFreq") != null) {
			String freqLabel = param.getString("payFreq");
	
			if(freqLabel.equals("Biweekly")) //jlf 20111104
			{
				payFreq = "4";
			}
			else if(freqLabel.equals("Semimonthly")) //jlf 20111104
			{
				payFreq = "5";
			}
			else if(freqLabel.equals("Monthly"))
			{
				payFreq = "6";
			}
		}
		wId.setPayFreq(payFreq);
		
//		if new data entry set seq nbr
		if(param.getString("isNew").equals("true")) {
			Object sec = wrkjlService.getMaxSec(wId.getWrkDate(), wId.getEmpNbr());
			int maxSec = 0;
			String s = (String) sec;
			if(!s.equals("")) {
			 maxSec = Integer.parseInt((String) sec) + 1;
			}
			wId.setSeqNbr(Integer.toString(maxSec));
		}
		w.setWrkJrnlStat("S");
		w.setId(wId);

		wrkjlService.saveWrkjl(w);
		data = "sucess";
	   } catch (Exception e) {
           System.err.println(e.getStackTrace());
           e.printStackTrace();
           data = "sucess";

       }
        return data;

	}
	
}
