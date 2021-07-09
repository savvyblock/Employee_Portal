package com.esc20.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.esc20.model.BeaEmpTrvl;
import com.esc20.model.BeaEmpTrvlAccId;
import com.esc20.model.BeaEmpTrvlAcct;
import com.esc20.model.BeaEmpTrvlId;
import com.esc20.model.BeaUsers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.model.WorkflowApprovalPath;
import com.esc20.nonDBModels.BfnOptionsTrvl;
import com.esc20.nonDBModels.ChildCode;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.EmpTravelRequest;
import com.esc20.nonDBModels.Options;
import com.esc20.nonDBModels.TravelCheckDetails;
import com.esc20.nonDBModels.TravelInfo;
import com.esc20.nonDBModels.TravelRequest;
import com.esc20.nonDBModels.TravelRequestCalendar;
import com.esc20.nonDBModels.TravelRequestInfo;
import com.esc20.nonDBModels.TravelType;
import com.esc20.service.AccountCodesService;
import com.esc20.service.ApprovalPathService;
import com.esc20.service.IndexService;
import com.esc20.service.ReferenceService;
import com.esc20.service.TravelRequestService;
import com.esc20.util.DateUtil;
import com.esc20.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.esc20.txeis.WorkflowLibrary.domain.RequestDetails;
import net.esc20.txeis.WorkflowLibrary.domain.Workflow;
import net.esc20.txeis.WorkflowLibrary.domain.WorkflowType;
import net.esc20.txeis.WorkflowLibrary.exceptions.WorkflowLibraryException;
import net.esc20.txeis.WorkflowLibrary.service.WorkflowService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Transactional (readOnly = true)

@Controller
@RequestMapping("/travelRequest")
public class TravelRequestController {

	private Logger logger = LoggerFactory.getLogger(TravelRequestController.class);

	@Autowired
	private IndexService indexService;

	@Autowired
	private TravelRequestService service;

	@Autowired
	private ReferenceService referenceService;

	@Autowired
	private AccountCodesService accountCodesService;

	@Autowired
	private ApprovalPathService approvalPathService;

	// private final String module = "Travel Reimbursement Request View";

	@RequestMapping("travelRequest")
	public ModelAndView travelRequest(HttpServletRequest req, String SearchType, String SearchStart, String SearchEnd,
			Boolean isAdd) throws ParseException {
		ModelAndView mav = new ModelAndView();
		try {
			HttpSession session = req.getSession();
			BeaUsers user = (BeaUsers) session.getAttribute("user");
			BhrEmpDemo userDetail = indexService.getUserDetail(user.getEmpNbr());
			Options options = indexService.getOptions();
			District districtInfo = indexService.getDistrict((String) session.getAttribute("srvcId"));
			String districtId = (String)session.getAttribute("srvcId"); 
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
			session.setAttribute("userDetail", userDetail);
			session.setAttribute("options", options);
			session.setAttribute("district", districtInfo);

			mav.setViewName("/travelRequest/travelRequest");
			BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
			String changeCommuteDist = req.getParameter("changeCommuteDist");
			if (StringUtils.isNotEmpty(changeCommuteDist) && StringUtils.isNotBlank(changeCommuteDist)) {
				service.updateTravelRequestCommuteDist(demo.getEmpNbr(), changeCommuteDist);
			}
			if (StringUtils.isNotEmpty(SearchType) && StringUtils.isNotBlank(SearchType)) {
				if ((StringUtils.isNotEmpty(SearchType) && StringUtils.isNotBlank(SearchType)
						&& (StringUtils.isNotEmpty(SearchStart)) && StringUtils.isNotBlank(SearchStart))
						&& (StringUtils.isNotEmpty(SearchEnd)) && StringUtils.isNotBlank(SearchEnd)) {
					String StartDate = formatDate(SearchStart);
					String EndDate = formatDate(SearchEnd);
					List<TravelInfo> travelStatusList = service.getTravelInfo(demo.getEmpNbr(), SearchType, StartDate,
							EndDate);
					if (travelStatusList.size() > 0) {
						for (TravelInfo checkDetails : travelStatusList) {
							if (StringUtils.isNotEmpty(checkDetails.getTripNbr())
									&& StringUtils.isNotBlank(checkDetails.getTripNbr())) {
								TravelCheckDetails travelCheckDetails = service
										.getTripCheckNumDt(checkDetails.getTripNbr());
								String checkNbr = travelCheckDetails.getCheckNum();
								String checkDate = travelCheckDetails.getCheckDt();
								if (StringUtils.isNotEmpty(checkNbr) && StringUtils.isNotBlank(checkNbr)) {
									checkDetails.setCheckNbr(checkNbr);
								}
								if (StringUtils.isNotEmpty(checkDate) && StringUtils.isNotBlank(checkDate)) {
									String formatCheckDt = formatDateDash(checkDate);
									checkDetails.setCheckDate(formatCheckDt);
								}
							}
						}
					}
					List<TravelInfo> travelStatus = getTravelInfoList(travelStatusList, demo.getEmpNbr());
					JSONArray jsonTravelStatus = JSONArray.fromObject(travelStatus);
					session.setAttribute("jsonTravelStatus", jsonTravelStatus);
					mav.addObject("travelStatus", travelStatus);
				} else if ((StringUtils.isNotEmpty(SearchType) && StringUtils.isNotBlank(SearchType)
						&& (StringUtils.isEmpty(SearchStart)) && StringUtils.isBlank(SearchStart))
						&& (StringUtils.isEmpty(SearchEnd)) && StringUtils.isBlank(SearchEnd)) {
					List<TravelInfo> travelStatusList = service.getTravelInfo(demo.getEmpNbr(), SearchType, null, null);
					if (travelStatusList.size() > 0) {
						for (TravelInfo checkDetails : travelStatusList) {
							if (StringUtils.isNotEmpty(checkDetails.getTripNbr())
									&& StringUtils.isNotBlank(checkDetails.getTripNbr())) {
								TravelCheckDetails travelCheckDetails = service
										.getTripCheckNumDt(checkDetails.getTripNbr());
								String checkNbr = travelCheckDetails.getCheckNum();
								String checkDate = travelCheckDetails.getCheckDt();
								if (StringUtils.isNotEmpty(checkNbr) && StringUtils.isNotBlank(checkNbr)) {
									checkDetails.setCheckNbr(checkNbr);
								}
								if (StringUtils.isNotEmpty(checkDate) && StringUtils.isNotBlank(checkDate)) {
									String formatCheckDt = formatDateDash(checkDate);
									checkDetails.setCheckDate(formatCheckDt);
								}
							}
						}
					}
					List<TravelInfo> travelStatus = getTravelInfoList(travelStatusList, demo.getEmpNbr());
					JSONArray jsonTravelStatus = JSONArray.fromObject(travelStatus);
					session.setAttribute("jsonTravelStatus", jsonTravelStatus);
					mav.addObject("travelStatus", travelStatus);
				} else if ((StringUtils.isNotEmpty(SearchType) && StringUtils.isNotBlank(SearchType)
						&& (StringUtils.isNotEmpty(SearchStart)) && StringUtils.isNotBlank(SearchStart))
						&& (StringUtils.isEmpty(SearchEnd)) && StringUtils.isBlank(SearchEnd)) {
					String StartDate = formatDate(SearchStart);
					List<TravelInfo> travelStatusList = service.getTravelInfo(demo.getEmpNbr(), SearchType, StartDate,
							null);
					if (travelStatusList.size() > 0) {
						for (TravelInfo checkDetails : travelStatusList) {
							if (StringUtils.isNotEmpty(checkDetails.getTripNbr())
									&& StringUtils.isNotBlank(checkDetails.getTripNbr())) {
								TravelCheckDetails travelCheckDetails = service
										.getTripCheckNumDt(checkDetails.getTripNbr());
								String checkNbr = travelCheckDetails.getCheckNum();
								String checkDate = travelCheckDetails.getCheckDt();
								if (StringUtils.isNotEmpty(checkNbr) && StringUtils.isNotBlank(checkNbr)) {
									checkDetails.setCheckNbr(checkNbr);
								}
								if (StringUtils.isNotEmpty(checkDate) && StringUtils.isNotBlank(checkDate)) {
									String formatCheckDt = formatDateDash(checkDate);
									checkDetails.setCheckDate(formatCheckDt);
								}
							}
						}
					}
					List<TravelInfo> travelStatus = getTravelInfoList(travelStatusList, demo.getEmpNbr());
					JSONArray jsonTravelStatus = JSONArray.fromObject(travelStatus);
					session.setAttribute("jsonTravelStatus", jsonTravelStatus);
					mav.addObject("travelStatus", travelStatus);
				} else if ((StringUtils.isNotEmpty(SearchType) && StringUtils.isNotBlank(SearchType)
						&& (StringUtils.isEmpty(SearchStart)) && StringUtils.isBlank(SearchStart))
						&& (StringUtils.isNotEmpty(SearchEnd)) && StringUtils.isNotBlank(SearchEnd)) {
					String EndDate = formatDate(SearchEnd);
					List<TravelInfo> travelStatusList = service.getTravelInfo(demo.getEmpNbr(), SearchType, null,
							EndDate);
					if (travelStatusList.size() > 0) {
						for (TravelInfo checkDetails : travelStatusList) {
							if (StringUtils.isNotEmpty(checkDetails.getTripNbr())
									&& StringUtils.isNotBlank(checkDetails.getTripNbr())) {
								TravelCheckDetails travelCheckDetails = service
										.getTripCheckNumDt(checkDetails.getTripNbr());
								String checkNbr = travelCheckDetails.getCheckNum();
								String checkDate = travelCheckDetails.getCheckDt();
								if (StringUtils.isNotEmpty(checkNbr) && StringUtils.isNotBlank(checkNbr)) {
									checkDetails.setCheckNbr(checkNbr);
								}
								if (StringUtils.isNotEmpty(checkDate) && StringUtils.isNotBlank(checkDate)) {
									String formatCheckDt = formatDateDash(checkDate);
									checkDetails.setCheckDate(formatCheckDt);
								}
							}
						}
					}
					List<TravelInfo> travelStatus = getTravelInfoList(travelStatusList, demo.getEmpNbr());
					JSONArray jsonTravelStatus = JSONArray.fromObject(travelStatus);
					session.setAttribute("jsonTravelStatus", jsonTravelStatus);
					mav.addObject("travelStatus", travelStatus);
				}
			}
			if ((StringUtils.isEmpty(SearchType) && StringUtils.isBlank(SearchType)
					&& (StringUtils.isEmpty(SearchStart)) && StringUtils.isBlank(SearchStart))
					&& (StringUtils.isEmpty(SearchEnd)) && StringUtils.isBlank(SearchEnd)) {
				List<TravelInfo> travelStatusList = service.getTravelInfo(demo.getEmpNbr(), null, null, null);
				if (travelStatusList.size() > 0) {
					for (TravelInfo checkDetails : travelStatusList) {
						if (StringUtils.isNotEmpty(checkDetails.getTripNbr())
								&& StringUtils.isNotBlank(checkDetails.getTripNbr())) {
							TravelCheckDetails travelCheckDetails = service
									.getTripCheckNumDt(checkDetails.getTripNbr());
							String checkNbr = travelCheckDetails.getCheckNum();
							String checkDate = travelCheckDetails.getCheckDt();
							if (StringUtils.isNotEmpty(checkNbr) && StringUtils.isNotBlank(checkNbr)) {
								checkDetails.setCheckNbr(checkNbr);
							}
							if (StringUtils.isNotEmpty(checkDate) && StringUtils.isNotBlank(checkDate)) {
								String formatCheckDt = formatDateDash(checkDate);
								checkDetails.setCheckDate(formatCheckDt);
							}
						}
					}
				}
				List<TravelInfo> travelStatus = getTravelInfoList(travelStatusList, demo.getEmpNbr());
				JSONArray jsonTravelStatus = JSONArray.fromObject(travelStatus);
				session.setAttribute("jsonTravelStatus", jsonTravelStatus);
				mav.addObject("travelStatus", travelStatus);
			}
			TravelRequestInfo travelRequestInfo = new TravelRequestInfo();
			List<TravelRequestInfo> travelInfo = service.getTravelRequestInfo(demo.getEmpNbr());
			for (TravelRequestInfo items : travelInfo) {
				travelRequestInfo.setVendorNbr(items.getVendorNbr());
				travelRequestInfo.setTrvlCommuteDist(items.getTrvlCommuteDist());
				if ((StringUtils.isNotEmpty(items.getVendorNbr())) && StringUtils.isNotBlank(items.getVendorNbr())) {
					List<TravelRequestInfo> travelVendorAddressInfo = service
							.getVendorAddressInfo(items.getVendorNbr());
					for (TravelRequestInfo addressList : travelVendorAddressInfo) {
						travelRequestInfo.setAddressAtn(addressList.getAddressAtn());
						travelRequestInfo.setAddressStreet(addressList.getAddressStreet());
						travelRequestInfo.setAddressCity(addressList.getAddressCity());
						travelRequestInfo.setAddressState(addressList.getAddressState());
						travelRequestInfo.setAddressZip(addressList.getAddressZip());
						if (StringUtils.isEmpty(addressList.getAddressZip4().trim())
								&& StringUtils.isBlank(addressList.getAddressZip4().trim())) {
							travelRequestInfo.setAddressZip4(null);
						} else {
							travelRequestInfo.setAddressZip4(addressList.getAddressZip4().trim());
						}
					}
				}
			}
			List<TravelRequestInfo> travelcampuses = service.getCampusPay(demo.getEmpNbr());
			if (travelcampuses.size() > 1) {
				mav.addObject("travelcampuses", travelcampuses);
			}
			travelRequestInfo.setPayCampus(service.getReimburseCampus(demo.getEmpNbr()));
			List<TravelType> travelTypesforSearch = service.getTravelStatus();
			mav.addObject("travelTypesforSearch", travelTypesforSearch);
			mav.addObject("SearchType", SearchType);
			mav.addObject("SearchStart", SearchStart);
			mav.addObject("SearchEnd", SearchEnd);
			mav.addObject("travelRequestInfo", travelRequestInfo);
			mav.addObject("districtId", districtId);

		} catch (Exception e) {
			logger.error("Error occurred while travelRequest method for summary screen for travel :", e.getMessage());
		}
		return mav;
	}

	@PostMapping(path = "travelRequestAdd")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
	public ModelAndView addTravelRequest(HttpServletRequest req, @RequestBody JSONObject json) throws ParseException {
		ModelAndView mav = new ModelAndView();
		try {
			HttpSession session = req.getSession();
			session.setAttribute("travelApprovalPath", null);
			BeaUsers user = (BeaUsers) session.getAttribute("user");
			String district = service.getReimburseCampus(user.getEmpNbr());
			String districtId = (String)session.getAttribute("srvcId"); 
			List<String> listCommuteDistance = new ArrayList<>();
			List<String> bfnOptionsTrvlLs = this.getBfnTrvlOptions(district, WorkflowType.TRAVEL.getId());
			BigDecimal commuteDistance = this.service.getCommuteDistance(user.getEmpNbr());
			listCommuteDistance.add(commuteDistance.toString());
			JSONArray jsonbfnOptionsTrvl = JSONArray.fromObject(bfnOptionsTrvlLs);
			session.setAttribute("bfnOptionsTrvl", jsonbfnOptionsTrvl);
			JSONArray jsonCommuteDistance = JSONArray.fromObject(listCommuteDistance);
			session.setAttribute("commuteDistance", jsonCommuteDistance);
			JSONArray travelRequests = json.getJSONArray("gridForMileage");
			for (int i = 0; i < travelRequests.size(); i++) {
				JSONObject jsonObject = travelRequests.getJSONObject(i);
				if (jsonObject.has("tripNbr")) {
					String empNbr = jsonObject.getString("empNbr");
					String tripNbr = jsonObject.getString("tripNbr");
					if ((StringUtils.isNotEmpty(tripNbr.trim()) && StringUtils.isNotBlank(tripNbr.trim()))) {
						List<TravelRequestCalendar> travelRequestExtended = service
								.getTravelRequestCalendarParameters(empNbr, tripNbr);
						JSONArray jsonTripNum = new JSONArray();  
						jsonTripNum.add(tripNbr);    
						session.setAttribute("tripNbr", jsonTripNum);
						JSONArray jsonList = JSONArray.fromObject(travelRequestExtended);
						List<Map<String, String>> accountCodes = accountCodesService.getAccountCodesTrip(empNbr,
								tripNbr);
						List<String> accountDesc = new ArrayList<>();
						Set<Long> tripSeqNr = new HashSet<Long>();
						for (Map<String, String> listItem : accountCodes) {
							BeaEmpTrvlAcct accCodes = getAccountCodesSeperate(listItem);
							tripSeqNr.add(accCodes.getId().getTripSeqNbr());
							String desc = accountCodesService.getAccountDescTrip(accCodes.getFund(), accCodes.getFunc(),
									accCodes.getObj(), accCodes.getSobj(), accCodes.getOrg(), accCodes.getFsclYr(),
									accCodes.getPgm(), accCodes.getEdSpan(), accCodes.getProjDtl());
							accountDesc.add(desc);
						}
						List<ChildCode> childCode = new ArrayList<ChildCode>();
						for (Long items : tripSeqNr) {
							List<Map<String, String>> accountCodesSeqWise = accountCodesService
									.getAccountCodesTripSeqWise(empNbr, tripNbr, items);
							List<ChildCode> childCodeSeqWise = getAccountCodeList(accountCodesSeqWise, false);
							childCode.addAll(childCodeSeqWise);
						}
						JSONArray jsonAccountCodes = JSONArray.fromObject(childCode);
						JSONArray jsonAccountDesc = JSONArray.fromObject(accountDesc);
						session.setAttribute("accountCodesData", jsonAccountCodes);
						session.setAttribute("travelRequestsData", jsonList);
						session.setAttribute("accountDesc", jsonAccountDesc);
						session.setAttribute("overnightTrip", jsonObject.getBoolean("overnightTrip"));

						// Get Approval Path Information
						List<WorkflowApprovalPath> workflowPath = approvalPathService.getWorkFlowPath(tripNbr, (String) session.getAttribute("srvcId"), user.getEmpNbr());
						session.setAttribute("travelApprovalPath", workflowPath);
					}
				} else {
					session.removeAttribute("tripNbr");
					session.setAttribute("travelRequestsData", travelRequests.getString(i).replaceAll("\"", "\'"));
					session.setAttribute("overnightTrip", jsonObject.getBoolean("overnightTrip"));
				}
			}
			BhrEmpDemo userDetail = indexService.getUserDetail(user.getEmpNbr());
			session.setAttribute("userDetail", userDetail);
			mav.addObject("districtId", districtId);
			mav.setViewName("/travelRequest/mileageScreen");
		} catch (Exception e) {
			logger.error("Error occurred while saving travel request :", e.getMessage());
		}
		return mav;
	}

	@RequestMapping("travelRequestMapper")
	public ModelAndView mileageRequest(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		try {
			HttpSession session = req.getSession();
			BeaUsers user = (BeaUsers) session.getAttribute("user");
			Boolean overnightTrip = (Boolean) session.getAttribute("overnightTrip");
			String districtId = (String)session.getAttribute("srvcId"); 

			if (overnightTrip) {
				mav.setViewName("/travelRequest/extendedScreen");
			} else {
				mav.setViewName("/travelRequest/mileageScreen");
			}
			// Get Approval Path Information
			mav.addObject("travelApprovalPath", session.getAttribute("travelApprovalPath"));
			mav.addObject("districtId", districtId);

		} catch (Exception e) {
			logger.error("Error occurred while view for travel mileage or extended screen :", e.getMessage());
		}
		return mav;
	}
	
	private List<String> getBfnTrvlOptions(String district, int workFlowTypeId) {
		List<String> bfnOptionsTrvlLs = new ArrayList<>();
		try {
			BfnOptionsTrvl bfnOptionsTrvl = this.service.getBfnOptionsTrvl();
			bfnOptionsTrvlLs.add(bfnOptionsTrvl.getReqStEndTimes());
			bfnOptionsTrvlLs.add(bfnOptionsTrvl.getReqOdometer());
			bfnOptionsTrvlLs.add(String.valueOf(bfnOptionsTrvl.getMileageRate()));
			bfnOptionsTrvlLs.add(bfnOptionsTrvl.getLocLocking());
			bfnOptionsTrvlLs.add(String.valueOf(bfnOptionsTrvl.getBrkfstRt()));
			bfnOptionsTrvlLs.add(String.valueOf(bfnOptionsTrvl.getLunchRt()));
			bfnOptionsTrvlLs.add(String.valueOf(bfnOptionsTrvl.getDinRt()));
			int firstApprover = service.getFirstApproverExists(district, workFlowTypeId);
			if(firstApprover > 0) {
				bfnOptionsTrvlLs.add(String.valueOf('Y'));
			} else {
				bfnOptionsTrvlLs.add(String.valueOf('N'));
			}
		} catch (Exception e) {
			logger.error("Error occurred while getting getBfnTrvlOptions records :", e.getMessage());
		}
		return bfnOptionsTrvlLs;
	}

	private String formatDate(String formatDate) {
		try {
			SimpleDateFormat sdfSource = new SimpleDateFormat("MM-dd-yyyy");
			Date finalDate = sdfSource.parse(formatDate);
			SimpleDateFormat sdfDestination = new SimpleDateFormat("yyyyMMdd");
			formatDate = sdfDestination.format(finalDate);
		} catch (ParseException pe) {
			logger.error("Error in parsing search date and time :", pe);
		}
		return formatDate;
	}

	private String formatDateDash(String formatDate) {
		try {
			SimpleDateFormat sdfSource = new SimpleDateFormat("yyyyMMdd");
			Date finalDate = sdfSource.parse(formatDate);
			SimpleDateFormat sdfDestination = new SimpleDateFormat("MM-dd-yyyy");
			formatDate = sdfDestination.format(finalDate);
		} catch (ParseException pe) {
			logger.error("Error in parsing search date and time :", pe);
		}
		return formatDate;
	}

	private List<TravelInfo> getTravelInfoList(List<TravelInfo> travelStatusList, String employeeNumber) {
		List<TravelInfo> travelStatus = new ArrayList<TravelInfo>();
		try {
			for (TravelInfo items : travelStatusList) {
				double tripTotal = Double.parseDouble(service.getTripTotalAmount(employeeNumber, items.getTripNbr()));
				String requestTotal = String.format("%,.2f", tripTotal);
				items.setRequestTotal(requestTotal);
				if (items.getFirstDate() != null && items.getFirstDate().trim().length() > 0) {
					items.setFirstDate(formatDateDash(items.getFirstDate()));
				}
				travelStatus.add(items);
			}
		} catch (Exception e) {
			logger.error("Error occurred while getting getTripTotalAmount records :", e.getMessage());
		}
		return travelStatus;
	}

	@PostMapping(path = "submitTravelRequests")
	@ResponseBody
	@Transactional (propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
	public JSONObject submitTravelReimbursementRequests(@RequestBody EmpTravelRequest empTravelRequest,
			HttpServletRequest req) throws Exception {		
		HttpSession session = req.getSession();
		String employeeNumber = ((BeaUsers) session.getAttribute("user")).getEmpNbr();
		WorkflowService workflowService = WorkflowService.getWorkflowService();
		try {
			String nextTripNbr = "";
			if (empTravelRequest.getTravelRequests()[1].getTripNbr() == "") {
				int maxTripNbr = service.getMaxTripNum();
				int getNxtTripNbr = Integer.valueOf(service.getNxtTripNbr());
				if (maxTripNbr >= getNxtTripNbr) {
					nextTripNbr = sixDigitFormatTripNbr(maxTripNbr + 1);
				} else {
					nextTripNbr = service.getNxtTripNbr();
				}
			}
			String tripNbr = ((empTravelRequest.getTravelRequests()[1].getTripNbr() != null && empTravelRequest.getTravelRequests()[1].getTripNbr() != "") ? empTravelRequest.getTravelRequests()[1].getTripNbr() : nextTripNbr);
			Workflow workflow = new Workflow(WorkflowType.TRAVEL,tripNbr,service.getReimburseCampus(employeeNumber));
			workflow.setDistrictId(service.getDistrictId());
			workflowService.setDaoDataSource(StringUtils.defaultString((String) session.getAttribute("srvcId")));
			workflow.setUserIdSrc("E");
			int iFirstApprover = 2; // This method is NOT used for First Approvers. Default to 2 - Not First Approver.
			if (workflowService.isOnlyApprover(workflow, employeeNumber, new ArrayList<RequestDetails>(), new HashMap<String, Object>(), iFirstApprover)) 
			{
				throw new RuntimeException("Approval Path must be added before a Transaction can be submitted");
			} else {
				workflowService.startWorkflow(service.getSecUserIdByEmpNbr(employeeNumber), employeeNumber, workflow.getDepartmentId(),
						workflow);
			}
		} catch (WorkflowLibraryException wfle) {
			logger.error("Error in submitting travel request", wfle.getMessage());
		}
		return savetravelReimbursementRequestPram(empTravelRequest, req);
	}

	@PostMapping(path = "saveTravelRequests")
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
	public JSONObject savetravelReimbursementRequestPram(@RequestBody EmpTravelRequest empTravelRequest,
			HttpServletRequest req) throws ParseException {
		JSONObject result = new JSONObject();
		try {
			HttpSession session = req.getSession();
			BeaUsers user = (BeaUsers) session.getAttribute("user");
			String employeeNumber = user.getEmpNbr();
			TravelRequest[] travelRequestArr = Arrays.copyOfRange(empTravelRequest.getTravelRequests(), 1,
					empTravelRequest.getTravelRequests().length);
			List<String> success = new ArrayList<String>();
			if (travelRequestArr.length > 0) {
				List<BeaEmpTrvl> empTrvls = createTravelRequestList(travelRequestArr, employeeNumber);
				if (empTrvls.size() > 0) {
					List<Map<String, Long>> resultOfSave = service.saveTravelReimbursementRequest(employeeNumber,
							empTrvls.get(0).getId().getTripNbr(), empTrvls);
					List<BeaEmpTrvlAcct> beaEmpTrvlAccts = new ArrayList<>();
					for (TravelRequest travelRequest : travelRequestArr) {
						for (ChildCode beaEmpTrvlAcct : travelRequest.getAccountCodes()) {
							int i = 0;
							for (String accountCode : beaEmpTrvlAcct.getChildAccountCodes()) {
								BeaEmpTrvlAcct beaEmpTrvlAccP = accountCodeSplit(accountCode);
								BeaEmpTrvlAccId id = new BeaEmpTrvlAccId(
										NumberUtils.toLong(beaEmpTrvlAcct.getId()) + 1l);
								beaEmpTrvlAccP.setId(id);
								if (!resultOfSave.isEmpty()) {
									for (Map<String, Long> map : resultOfSave) {
										if (map.entrySet().iterator().next().getValue()
												.equals(beaEmpTrvlAccP.getId().getTripSeqNbr())) {
											id.setTripNbr((map.entrySet().iterator().next().getKey()));
										}
									}
								}
								id.setDistrSeqNbr(Long.valueOf(i));
								beaEmpTrvlAccts.add(beaEmpTrvlAccP);
								i++;
							}
						}
						try {
							accountCodesService.saveAccountCodes(beaEmpTrvlAccts);
						} catch (Exception e) {
							logger.error("Error occurred while inserting account codes to trip number :"
									+ beaEmpTrvlAccts.get(0).getId().getTripNbr(), e.getMessage());
						}
					}
					if ((!resultOfSave.isEmpty())
							&& StringUtils.isEmpty(empTravelRequest.getTravelRequests()[0].getTripNbr())
							&& StringUtils.isBlank(empTravelRequest.getTravelRequests()[0].getTripNbr())) {
						String savedTripNbr = "";
						String nextTripNbr = "";
						for (Map<String, Long> map : resultOfSave) {
							savedTripNbr = map.entrySet().iterator().next().getKey();
							int nextTrip = (Integer.parseInt(map.entrySet().iterator().next().getKey()) + 1);
							nextTripNbr = sixDigitFormatTripNbr(nextTrip);
						}
						if (StringUtils.isNotEmpty(nextTripNbr) && StringUtils.isNotBlank(nextTripNbr)) {
							try {
								service.updateNxtTripNbr(nextTripNbr);
								success.add(savedTripNbr);
								success.add(String.valueOf(empTrvls.get(0).getTrvlReqStat()));
								success.add(empTrvls.get(0).getEntryDt());
								logger.info("Addded new trip number as : " + savedTripNbr
										+ " and updated next trip number as : " + nextTripNbr);
							} catch (Exception e) {
								logger.error("Error occurred while updating next trip number :", e.getMessage());
							}
						}
					}
					if ((!resultOfSave.isEmpty())
							&& StringUtils.isNotEmpty(empTravelRequest.getTravelRequests()[0].getTripNbr())
							&& StringUtils.isNotBlank(empTravelRequest.getTravelRequests()[0].getTripNbr())) {
						String editedTripNbr = "";
						for (Map<String, Long> map : resultOfSave) {
							editedTripNbr = map.entrySet().iterator().next().getKey();
						}
						if (StringUtils.isNotEmpty(editedTripNbr) && StringUtils.isNotBlank(editedTripNbr)) {
							success.add(editedTripNbr);
							success.add(String.valueOf(empTrvls.get(0).getTrvlReqStat()));
							success.add(empTrvls.get(0).getEntryDt());
							logger.info("Updated existing trip Number as : " + editedTripNbr);
						}
					}
				}
			} else {
				String tripNumber = empTravelRequest.getTravelRequests()[0].getTripNbr();
				if (StringUtils.isNotEmpty(tripNumber) && StringUtils.isNotBlank(tripNumber)) {
					service.deleteTravelReimbursementRequest(employeeNumber, tripNumber);
					success.add(tripNumber);
					logger.info("Deleted trip number as user edited and deleted all records on existing trip as : "
							+ tripNumber);
				}
			}
			result.put("result", success);
		} catch (Exception e) {
			logger.error("Error occurred while getting getTripTotalAmount records :", e.getMessage());
		}
		return result;
	}
	
	private String sixDigitFormatTripNbr(int nextTrip) {
		String nextTripNumber = null;
		try {
			nextTripNumber = String.format("%06d", nextTrip);
		} catch (Exception e) {
			logger.error("Error while padding zeros in trip number", e.getMessage());
		}
		return nextTripNumber;
	}

	private List<BeaEmpTrvl> createTravelRequestList(TravelRequest[] travelRequestArr, String employeeNumber) {
		List<BeaEmpTrvl> empTrvls = new ArrayList<BeaEmpTrvl>();
		try {
			for (int i = 0; i < travelRequestArr.length; i++) {
				String nextTripNbr = "";
				if (travelRequestArr[i].getTripNbr() == "") {
					int maxTripNbr = service.getMaxTripNum();
					int getNxtTripNbr = Integer.valueOf(service.getNxtTripNbr());
					if (maxTripNbr >= getNxtTripNbr) {
						nextTripNbr = sixDigitFormatTripNbr(maxTripNbr + 1);
					} else {
						nextTripNbr = service.getNxtTripNbr();
					}
				}
				BeaEmpTrvlId id = new BeaEmpTrvlId(
						travelRequestArr[i].getTripNbr() != "" ? travelRequestArr[i].getTripNbr() : nextTripNbr, i + 1);
				List<TravelRequestInfo> travelInfo = service.getTravelRequestInfo(employeeNumber);
				empTrvls.add(new BeaEmpTrvl(travelRequestArr[i], id, travelInfo,
						service.getReimburseCampus(employeeNumber), employeeNumber));
			}
		} catch (Exception e) {
			logger.error("Error occurred while creating travel request list from travel array", e.getMessage());
		}
		return empTrvls;
	}

	private BeaEmpTrvlAcct accountCodeSplit(String accountCode) {
		BeaEmpTrvlAcct beaEmpTrvlAcc = new BeaEmpTrvlAcct();
		try {
			String codeSplit[] = accountCode.split("-");
			int i = 1;
			for (String codeHalf : codeSplit) {
				switch (i) {
				case 1:
					beaEmpTrvlAcc.setFund(codeHalf);
					break;
				case 2:
					beaEmpTrvlAcc.setFunc(codeHalf);
					break;
				case 3:
					beaEmpTrvlAcc.setObj(codeHalf.substring(0, 4));
					beaEmpTrvlAcc.setSobj(codeHalf.substring(5, 7));
					break;
				case 4:
					beaEmpTrvlAcc.setOrg(codeHalf);
					break;
				case 5:
					beaEmpTrvlAcc.setFsclYr(codeHalf.substring(0, 1));
					beaEmpTrvlAcc.setPgm(codeHalf.substring(1, 3));
					beaEmpTrvlAcc.setEdSpan(codeHalf.substring(3, 4));
					beaEmpTrvlAcc.setProjDtl(codeHalf.substring(4, 6));
					break;
				case 6:
					double value;
					value = Double.valueOf(codeHalf);
					double percentage = Double.valueOf(100);
					double perCentValue = value / (double) percentage;
					beaEmpTrvlAcc.setAcctPct(perCentValue);
					break;
				case 7:
					beaEmpTrvlAcc.setAcctAmt(Double.valueOf(codeHalf));
					break;
				}
				i++;
			}
		} catch (Exception e) {
			logger.error("Error occurred on accountCodeSplit logic : ", e.getMessage());
		}
		return beaEmpTrvlAcc;
	}

	@RequestMapping("getAccountCodes")
	@ResponseBody
	public JSONObject getAccountCodes(HttpServletRequest req) {
		JSONObject result = new JSONObject();
		try {
			HttpSession session = req.getSession();
			BeaUsers user = (BeaUsers) session.getAttribute("user");
			String employeeNumber = user.getEmpNbr();
			List<Map<String, String>> accountCodes = accountCodesService.getAccountCodes(employeeNumber);
			JSONArray json = JSONArray.fromObject(accountCodes);
			result.put("result", json);
			result.put("isSuccess", "true");
		} catch (Exception e) {
			logger.error("Error occurred on getAccountCodes method : ", e.getMessage());
		}
		return result;
	}
	
	@PostMapping(path = "checkAccountCodes")
	@ResponseBody
	public JSONObject checkAccountCodes(HttpServletRequest req, @RequestBody JSONObject json) {
		JSONObject result = new JSONObject();
		try {
			HttpSession session = req.getSession();
			final String fileId = "C";
			BeaUsers user = (BeaUsers) session.getAttribute("user");
			String employeeNumber = user.getEmpNbr();
			String accountCd = (String) json.get("accountCd");
			Boolean accountCodeExists = accountCodesService.accountCodeExists(accountCd, fileId, employeeNumber);
			result.put("result", accountCodeExists);
			result.put("isSuccess", "true");
		} catch (Exception e) {
			logger.error("Error occurred on getAccountCodes method : ", e.getMessage());
		}
		return result;
	}
	
	@PostMapping(path = "checkDocuments")
	@ResponseBody
	public JSONObject checkDocuments(HttpServletRequest req, @RequestBody JSONObject json) {
		JSONObject result = new JSONObject();
		try {
			String tripNbr = (String) json.get("tripNbr");
			Boolean isDocumentAttached = service.isDocoumentAttached(tripNbr);
			result.put("result", isDocumentAttached);
			result.put("isSuccess", "true");
		} catch (Exception e) {
			logger.error("Error occurred on checkDocuments method : ", e.getMessage());
		}
		return result;
	}
	
	@PostMapping(path = "getAccountCodeDesc")
	@ResponseBody
	public JSONObject getAccountCodeDesc(HttpServletRequest req, @RequestBody JSONObject json) {
		JSONObject result = new JSONObject();
		try {
			String accountCodeDesc = "";
			String accountCode = (String) json.get("accountCode");
			BeaEmpTrvlAcct accCodes = accountCodeSplit(accountCode);
			accountCodeDesc = accountCodesService.getAccountDescTrip(accCodes.getFund(), accCodes.getFunc(),
					accCodes.getObj(), accCodes.getSobj(), accCodes.getOrg(), accCodes.getFsclYr(),
					accCodes.getPgm(), accCodes.getEdSpan(), accCodes.getProjDtl());
			result.put("result", accountCodeDesc);
			result.put("isSuccess", "true");
		} catch (Exception e) {
			logger.error("Error occurred on getAccountCodeDesc method : ", e.getMessage());
		}
		return result;
	}

	@RequestMapping("getStates")
	@ResponseBody
	public JSONObject getStates() {
		List<Code> statesOptions = referenceService.getStates();
		JSONArray json = JSONArray.fromObject(statesOptions);
		JSONObject result = new JSONObject();
		result.put("result", json);
		result.put("isSuccess", "true");
		return result;
	}

	@RequestMapping(value = "getDocumentStorageInfo")
	@ResponseBody
	public Map<String, Object> getDocumentStorageInfo(HttpServletRequest req) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Integer langCode = (Integer) req.getSession().getAttribute("langCode");
			String clientCode = service.GetSISPreference("ds_client_token");
			String docStorageServiceURI = service.GetSISTaskURL("Document Attachments");
			String sessionId = req.getSession().getId();
			Object key = req.getSession().getAttribute("pRegKey");
			String pRegKey = "";
			if (key != null) {
				pRegKey = (String) key;
			}
			result.put("isSuccess", "true");
			result.put("sessionID", sessionId);
			result.put("langCode", langCode);
			result.put("pRegKey", pRegKey);
			result.put("docStorageServiceURI", docStorageServiceURI);
			result.put("clientCode", clientCode);
		} catch (Exception e) {
			logger.error("Error occured on getDocumentStorageInfo method : ", e.getMessage());
		}
		return result;
	}
	
	private List<ChildCode> getAccountCodeList(List<Map<String, String>> accountCodes, Boolean onlyAcCode) {
		List<ChildCode> childCodeList = new ArrayList<ChildCode>();
		try {
			List<String> accountCode = new ArrayList<String>();
			String accAmount = "";
			String accPercent = "";
			if (accountCodes != null && !accountCodes.isEmpty()) {
				ChildCode childCode = new ChildCode();
				for (Map<String, String> listItem : accountCodes) {
					String accountCo;
					BeaEmpTrvlAcct beaEmpTrvlAcct = new BeaEmpTrvlAcct();
					BeaEmpTrvlAccId beaEmpTrvlAcctId = new BeaEmpTrvlAccId();
					Iterator<Map.Entry<String, String>> it = listItem.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry<String, String> pair = (Map.Entry<String, String>) it.next();
						String key = pair.getKey().toString();
						switch (key) {
						case "tripSeqNbr":
							String tripSeqNbr = pair.getValue().toString().trim();
							Long tripSeqNbrL = Long.valueOf(tripSeqNbr);
							beaEmpTrvlAcctId.setTripSeqNbr(tripSeqNbrL);
							break;
						case "fund":
							String fund = pair.getValue().toString().trim();
							beaEmpTrvlAcct.setFund(fund);
							break;
						case "func":
							String func = pair.getValue().toString().trim();
							beaEmpTrvlAcct.setFunc(func);
							break;
						case "obj":
							String obj = pair.getValue().toString().trim();
							beaEmpTrvlAcct.setObj(obj);
							break;
						case "sobj":
							String sobj = pair.getValue().toString().trim();
							beaEmpTrvlAcct.setSobj(sobj);
							break;
						case "org":
							String org = pair.getValue().toString().trim();
							beaEmpTrvlAcct.setOrg(org);
							break;
						case "fscl_yr":
							String fsclYr = pair.getValue().toString().trim();
							beaEmpTrvlAcct.setFsclYr(fsclYr);
							break;
						case "pgm":
							String pgm = pair.getValue().toString().trim();
							beaEmpTrvlAcct.setPgm(pgm);
							break;
						case "ed_span":
							String edSpan = pair.getValue().toString().trim();
							beaEmpTrvlAcct.setEdSpan(edSpan);
							break;
						case "proj_dtl":
							String projDtl = pair.getValue().toString().trim();
							beaEmpTrvlAcct.setProjDtl(projDtl);
							break;
						case "acct_amt":
							accAmount = pair.getValue().toString().trim();
							break;
						case "acct_pct":
							String acctPct = pair.getValue().toString().trim();
							if (StringUtils.isNotEmpty(acctPct) && StringUtils.isNotBlank(acctPct)) {
								accPercent = formatAccountCodes(acctPct);
							}
							break;
						}
					}
					if (onlyAcCode) {
						accountCo = beaEmpTrvlAcct.getFund() + "-" + beaEmpTrvlAcct.getFunc() + "-"
								+ beaEmpTrvlAcct.getObj() + "." + beaEmpTrvlAcct.getSobj() + "-"
								+ beaEmpTrvlAcct.getOrg() + "-" + beaEmpTrvlAcct.getFsclYr() + beaEmpTrvlAcct.getPgm()
								+ beaEmpTrvlAcct.getEdSpan() + beaEmpTrvlAcct.getProjDtl();
					} else {
						accountCo = beaEmpTrvlAcct.getFund() + "-" + beaEmpTrvlAcct.getFunc() + "-"
								+ beaEmpTrvlAcct.getObj() + "." + beaEmpTrvlAcct.getSobj() + "-"
								+ beaEmpTrvlAcct.getOrg() + "-" + beaEmpTrvlAcct.getFsclYr() + beaEmpTrvlAcct.getPgm()
								+ beaEmpTrvlAcct.getEdSpan() + beaEmpTrvlAcct.getProjDtl() + '-' + accPercent + '-'
								+ accAmount;
					}
					accountCode.add(accountCo);
					childCode.setId(String.valueOf(beaEmpTrvlAcctId.getTripSeqNbr() - 1));
					childCode.setChildAccountCodes(accountCode);
				}
				childCodeList.add(childCode);
			}
		} catch (Exception e) {
			logger.error("Error occured on getAccountCodeList method : ", e.getMessage());
		}
		return childCodeList;
	}
	
	private String formatAccountCodes(String acctPct) {
		String accPercent = "";
		try {
			double dnum = Double.parseDouble(acctPct);
			double percent = dnum * 100;
			String str = Double.toString(percent);
			String[] array = str.split("[.]");
			String beforeDec = array[0];
			String afterDec = array[1];
			if (afterDec.equals("0")) {
				accPercent = beforeDec + "." + "000";
			} else {
				accPercent = beforeDec + "." + afterDec + "00";
			}
		} catch (Exception e) {
			logger.error("Error occured on formatAccountCodes method : ", e.getMessage());
		}
		return accPercent;
	}
	
	private BeaEmpTrvlAcct getAccountCodesSeperate(Map<String, String> accountCodes) {
		BeaEmpTrvlAcct beaEmpTrvlAcct = new BeaEmpTrvlAcct();
		try {
			BeaEmpTrvlAccId beaEmpTrvlAcctId = new BeaEmpTrvlAccId();
			if (accountCodes != null && !accountCodes.isEmpty()) {
				Iterator<Map.Entry<String, String>> it = accountCodes.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<String, String> pair = (Map.Entry<String, String>) it.next();
					String key = pair.getKey().toString();
					switch (key) {
					case "tripNbr":
						String tripNbr = pair.getValue().toString().trim();
						beaEmpTrvlAcctId.setTripNbr(tripNbr);
						;
						break;
					case "tripSeqNbr":
						String tripSeqNbr = pair.getValue().toString().trim();
						Long tripSeqNbrL = Long.valueOf(tripSeqNbr);
						beaEmpTrvlAcctId.setTripSeqNbr(tripSeqNbrL);
						break;
					case "distrSeqNbr":
						String distrSeqNbr = pair.getValue().toString().trim();
						Long distrSeqNbrL = Long.valueOf(distrSeqNbr);
						beaEmpTrvlAcctId.setDistrSeqNbr(distrSeqNbrL);
						;
						break;
					case "fund":
						String fund = pair.getValue().toString().trim();
						beaEmpTrvlAcct.setFund(fund);
						break;
					case "func":
						String func = pair.getValue().toString().trim();
						beaEmpTrvlAcct.setFunc(func);
						break;
					case "obj":
						String obj = pair.getValue().toString().trim();
						beaEmpTrvlAcct.setObj(obj);
						break;
					case "sobj":
						String sobj = pair.getValue().toString().trim();
						beaEmpTrvlAcct.setSobj(sobj);
						break;
					case "org":
						String org = pair.getValue().toString().trim();
						beaEmpTrvlAcct.setOrg(org);
						break;
					case "fscl_yr":
						String fsclYr = pair.getValue().toString().trim();
						beaEmpTrvlAcct.setFsclYr(fsclYr);
						break;
					case "pgm":
						String pgm = pair.getValue().toString().trim();
						beaEmpTrvlAcct.setPgm(pgm);
						break;
					case "ed_span":
						String edSpan = pair.getValue().toString().trim();
						beaEmpTrvlAcct.setEdSpan(edSpan);
						break;
					case "proj_dtl":
						String projDtl = pair.getValue().toString().trim();
						beaEmpTrvlAcct.setProjDtl(projDtl);
						break;
					}

				}
				beaEmpTrvlAcct.setId(beaEmpTrvlAcctId);
			}
		} catch (Exception e) {
			logger.error("Error occured on getAccountCodesSeperate method : ", e.getMessage());
		}
		return beaEmpTrvlAcct;
	}
}
