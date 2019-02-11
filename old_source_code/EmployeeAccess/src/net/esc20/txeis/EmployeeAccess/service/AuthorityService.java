package net.esc20.txeis.EmployeeAccess.service;

import net.esc20.txeis.EmployeeAccess.dao.AuthorityDao;
import net.esc20.txeis.EmployeeAccess.dao.api.IAuthorityDao;
import net.esc20.txeis.EmployeeAccess.domainobject.User;
import net.esc20.txeis.EmployeeAccess.service.api.IAuthorityService;
import net.esc20.txeis.EmployeeAccess.validation.ContextValidationUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService implements IAuthorityService {

	private IAuthorityDao authorityDao;
	
	@Autowired
	public AuthorityService(AuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}
	
	@Override
	public boolean isAccountTempLocked(MessageContext messageContext, User user)
	{
		String result = authorityDao.retrieveTempLock(user.getUserName());
		
		if("Y".equals(result))
		{
			if(messageContext != null)
				ContextValidationUtil.addMessage(messageContext, "", "User has retrieved allowed maximum number of temporary passwords. Please contact personnel office to delete existing user account before creating a new one.");
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	public boolean isEmployeePayCampusLeaveCampus(String employeeNumber) {
		Integer count = authorityDao.employeePayCampusLeaveCampusCount(employeeNumber);
		if (count > 0)
			return true;
		else
			return false;
	}
	
	public boolean isLeaveSupervisor(String employeeNumber, boolean usePMISSupervisorLevels) {
		if (usePMISSupervisorLevels) {
			return authorityDao.isLeavePMISSupervisor(employeeNumber);
		} else {
			return authorityDao.isLeaveSupervisor(employeeNumber);
		}
	}

}
