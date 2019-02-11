package net.esc20.txeis.EmployeeAccess.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.esc20.txeis.EmployeeAccess.dao.AuthorityDao;
import net.esc20.txeis.EmployeeAccess.dao.OptionsDao;
import net.esc20.txeis.EmployeeAccess.dao.PasswordDao;
import net.esc20.txeis.EmployeeAccess.dao.UserDao;
import net.esc20.txeis.EmployeeAccess.dao.api.IAuthorityDao;
import net.esc20.txeis.EmployeeAccess.dao.api.IOptionsDao;
import net.esc20.txeis.EmployeeAccess.dao.api.IPasswordDao;
import net.esc20.txeis.EmployeeAccess.dao.api.IUserDao;
import net.esc20.txeis.EmployeeAccess.domainobject.DatabaseContextHolder;
import net.esc20.txeis.EmployeeAccess.domainobject.Options;
import net.esc20.txeis.EmployeeAccess.service.AuthorityService;
import net.esc20.txeis.EmployeeAccess.service.LeaveRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;
import org.springframework.security.userdetails.User;

public class CustomAuthenticationProcessingFilter extends AuthenticationProcessingFilter {

	public static final String SPRING_SECURITY_FORM_DEPARTMENT_PARAM_KEY = "j_district";
	private String departmentParameter = SPRING_SECURITY_FORM_DEPARTMENT_PARAM_KEY;

	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private LeaveRequestService leaveRequestService;

	private IAuthorityDao authorityDao;
	private IUserDao userDao;
	private IPasswordDao passwordDao;
	private IOptionsDao optionsDao;

	public CustomAuthenticationProcessingFilter(AuthorityDao authorityDao, UserDao userDao, PasswordDao passwordDao, OptionsDao optionsDao) {
		this.authorityDao = authorityDao;
		this.userDao = userDao;
		this.passwordDao = passwordDao;
		this.optionsDao = optionsDao;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request) {
		DatabaseContextHolder.setCountyDistrict((String)request.getSession().getAttribute("district"));
		return super.attemptAuthentication(request); 
	}

	@Override
	protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
		net.esc20.txeis.EmployeeAccess.domainobject.User result = userDao.retrieveExistingUser(((User)authResult.getPrincipal()).getUsername());
		boolean usedTempPass = authorityDao.isTempPassword(((User)authResult.getPrincipal()).getUsername());

		if(usedTempPass) {
			request.getSession().setAttribute("usedTempPass", true);
			authorityDao.deletePassword(((User)authResult.getPrincipal()).getUsername());
		}
		passwordDao.resetLocks(((User)authResult.getPrincipal()).getUsername());
		request.getSession().setAttribute("currentUser_employeeNumber", result.getEmployeeNumber());
		request.getSession().setAttribute("currentUser_userName", result.getUserName());
		request.getSession().setAttribute("currentUser_firstName", result.getFirstName());
		request.getSession().setAttribute("currentUser_middleName", result.getMiddleName());
		request.getSession().setAttribute("currentUser_lastName", result.getLastName());
		request.getSession().setAttribute("currentUser_workEmail", result.getWorkEmail());
		request.getSession().setAttribute("currentUser_homeEmail", result.getHomeEmail());
		request.getSession().setAttribute("currentUser_password", null);
		DatabaseContextHolder.setCountyDistrict((String)request.getSession().getAttribute("district"));

		setMenuPermissions(request);

		// set flag indicating if PMIS is being used for supervisors
		Options options = optionsDao.getOptions();
		if (options.isUsePMISSpvsrLevels()) {
			request.getSession().setAttribute("usePMISSpvsrLevels", "Y");
		} else {				
			request.getSession().setAttribute("usePMISSpvsrLevels", "N");
		}

		// check if the employee's pay campus & department has has access to the leave request menu items
		validateLeaveRequestPermission(request);
				
		super.onSuccessfulAuthentication(request, response, authResult);
	}

	@Override
	protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
		int lock_count = authorityDao.retrievePassCount((String)failed.getAuthentication().getPrincipal());
		request.getSession().setAttribute("temp_pass", false);
		if(lock_count < 3 && "User is disabled".equals(failed.getMessage())) {
			super.onUnsuccessfulAuthentication(request, response, failed);
			request.getSession().setAttribute("temp_pass", true);
			return;
		}

		if(lock_count < 3) {
			lock_count++;
			authorityDao.updatePassCount(lock_count, (String)failed.getAuthentication().getPrincipal());
		}

		super.onUnsuccessfulAuthentication(request, response, failed);
	}

	public void setMenuPermissions(HttpServletRequest request) {
		List <Integer> permissions;

		permissions = authorityDao.retrieveMenuPermissions();

		request.getSession().setAttribute("enableEA",permissions.get(0));
		request.getSession().setAttribute("enableEarn", permissions.get(1));
		request.getSession().setAttribute("enableLeave", permissions.get(2));
		request.getSession().setAttribute("enableW2", permissions.get(3));
		request.getSession().setAttribute("enableCYD", permissions.get(4));
		request.getSession().setAttribute("enableCPI", permissions.get(5));
		request.getSession().setAttribute("enableDeduct", permissions.get(6));
		request.getSession().setAttribute("enableDemo", permissions.get(7));
		request.getSession().setAttribute("enablePayroll", permissions.get(8));
		request.getSession().setAttribute("enable1095", permissions.get(9));
		request.getSession().setAttribute("enableElecConsntW2", permissions.get(10));
		request.getSession().setAttribute("enableElecConsnt1095", permissions.get(11));
		request.getSession().setAttribute("enableLeaveRequest", permissions.get(12));
	}	
	
	public void validateLeaveRequestPermission(HttpServletRequest request) {
		if (((Integer) request.getSession().getAttribute("enableLeaveRequest")).intValue() == 1) {
			if (authorityService.isEmployeePayCampusLeaveCampus((String)request.getSession().getAttribute("currentUser_employeeNumber")) == false) {
				// prevent employee's access to the leave request menu items
				request.getSession().setAttribute("enableLeaveRequest", new Integer(0));
			} else {
				// determine if the user has access to the supervisor menu item and which of its sub-menu items
				boolean usePMISSupervisorLevels = ((String)request.getSession().getAttribute("usePMISSpvsrLevels")).equals("Y") ? true : false;
				if (authorityService.isLeaveSupervisor((String)request.getSession().getAttribute("currentUser_employeeNumber"), usePMISSupervisorLevels)) {
					request.getSession().setAttribute("enableLeaveRequestSupervisor", new Integer(1));
				} else {
					request.getSession().setAttribute("enableLeaveRequestSupervisor", new Integer(0));
					if (leaveRequestService.isLeaveApprovalAccessGranted((String)request.getSession().getAttribute("currentUser_employeeNumber"))) {
						request.getSession().setAttribute("enableLeaveRequestTemporaryApprover", new Integer(1));
					} else {
						request.getSession().setAttribute("enableLeaveRequestTemporaryApprover", new Integer(0));						
					}
				}
			}
		}
	}
}