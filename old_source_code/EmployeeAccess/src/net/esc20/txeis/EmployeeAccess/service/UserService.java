package net.esc20.txeis.EmployeeAccess.service;

import net.esc20.txeis.EmployeeAccess.dao.UserDao;
import net.esc20.txeis.EmployeeAccess.dao.api.IUserDao;
import net.esc20.txeis.EmployeeAccess.domainobject.DatabaseContextHolder;
import net.esc20.txeis.EmployeeAccess.domainobject.User;
import net.esc20.txeis.EmployeeAccess.search.EmployeeCriteria;
import net.esc20.txeis.EmployeeAccess.service.api.IMailUtilService;
import net.esc20.txeis.EmployeeAccess.service.api.IUserService;
import net.esc20.txeis.EmployeeAccess.util.DateUtil;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;
import net.esc20.txeis.EmployeeAccess.validation.ContextValidationUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageContext;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService
{
	private IUserDao userDao;
	private IMailUtilService mailUtilService;
    private SimpleMailMessage message;
    
    private static Log log = LogFactory.getLog(UserService.class);

	
	@Autowired
	public UserService(UserDao userDao, MailUtilService mailUtilService ) {
		this.userDao = userDao;
		this.mailUtilService = mailUtilService;
		this.message = new SimpleMailMessage();
	}
	
	@Override
	public User retrieveEmployee(String employeeNumber)
	{
		return userDao.retrieveEmployee(employeeNumber);
	}
	
	@Override
	public User retrieveEmployee(MessageContext messageContext, EmployeeCriteria searchInfo, boolean existsCheck, boolean useSSN)
	{
		User result = null; 
		
		if(useSSN)
		{
			searchInfo.setSearchEmployeeNumber(userDao.retrieveEmpNbrBySSN(searchInfo.getSearchSSN()));
		}
		
		searchInfo.setSearchEmployeeNumber(StringUtil.leftZeroPad(6, searchInfo.getSearchEmployeeNumber()));
		if(!hasExistingUser(searchInfo.getSearchEmployeeNumber()) || !existsCheck)
		{
			 result = userDao.retrieveEmployee(searchInfo.getSearchEmployeeNumber(), searchInfo.getSearchFormattedDateofBirth(),
					                          searchInfo.getSearchZipCode());
			 
			 boolean isDate = DateUtil.isDate(searchInfo.getSearchFormattedDateofBirth(), "yyyyMMdd");
			 
			 if(!isDate)
			 {
				 ContextValidationUtil.addMessage(messageContext, "", "The date entered below is an invalid date. Please re-enter a date of format: mm/dd/yyyy.");
			 }
			 else if(result == null)
			 	{
					ContextValidationUtil.addMessage(messageContext, "", "No employees matching the below criteria have been found for this district.");
				}
		}
		else if(hasExistingUser(searchInfo.getSearchEmployeeNumber()) && existsCheck)
		{
			ContextValidationUtil.addMessage(messageContext, "", "User account already associated with employee. Please contact personnel office to delete existing user account before creating a new one.");
		}
		
		return result;
	}
	
	@Override
	public User retrieveExistingUser(MessageContext messageContext, EmployeeCriteria searchInfo, boolean useSSN)
	{
		User result = null;
		
		if(useSSN)
		{
			searchInfo.setSearchEmployeeNumber(userDao.retrieveEmpNbrBySSN(searchInfo.getSearchSSN()));
		}
		
		searchInfo.setSearchEmployeeNumber(StringUtil.leftZeroPad(6, searchInfo.getSearchEmployeeNumber()));
		
		result = userDao.retrieveExistingUserByEmpData(searchInfo.getSearchEmployeeNumber(), searchInfo.getSearchFormattedDateofBirth(),
                									   searchInfo.getSearchZipCode());
		if(result == null)
		{
			ContextValidationUtil.addMessage(messageContext, "", "No employees matching the below criteria have been found for this district.");
		}
		
		return result;
	}
	
	@Override
	public Boolean useSSN(String district)
	{
		if(!"".equals(district))
			DatabaseContextHolder.setCountyDistrict(district);
		String ssnCheck = userDao.employeeRetrievalMethod();
		if ("S".equals(ssnCheck))
			return true;
		else if ("E".equals(ssnCheck))
			return false;
		else
			return null;
	}
	
	@Override
	public boolean saveUser(MessageContext messageContext, User user)
	{
		if(userExists(user.getUserName()))
		{
			ContextValidationUtil.addMessage(messageContext, "", "User name is already associated with another employee. Please choose a different user name.");
			return false;
		}
		else
		{
			userDao.insertUser(user);
			if((user.isHomeEmailEmpty() && !"".equals(user.getHomeEmail())) || (user.isWorkEmailEmpty() && !"".equals(user.getWorkEmail())))
			{
				userDao.updateEmail(user);
			}
			sendMail(user);
			return true;
		}

	}
	
	@Override
	public boolean hasExistingUser(String employeeNumber)
	{
		User tempUser = userDao.retrieveExistingUserByEmpNbr(employeeNumber);
		if(tempUser == null)
			return false;
		else
			return true;
	}
	
	@Override
	public boolean userExists(String userName)
	{
		User tempUser = userDao.retrieveExistingUser(userName);
		if(tempUser == null)
			return false;
		else
			return true;
	}
	
	@Override
	public void sendMail(User user)
	{
		SimpleMailMessage msg = new SimpleMailMessage(this.message);
		msg.setSubject("New User Created");
		msg.setText("Thank you for Registering for Employee Access.  Your User ID is: " + user.getUserName());
		
		if(!"".equals(user.getWorkEmail()))
		{
			msg.setTo(user.getWorkEmail());
			
			try{
				mailUtilService.sendMail(msg);
				} 
			catch(MailException ex) {
				log.info("An exception has occured with mailing the user.");
			} 
		}
		
		else if(!"".equals(user.getHomeEmail()))
		{
			msg.setTo(user.getHomeEmail());
			
			try{
				mailUtilService.sendMail(msg);
				} 
			catch(MailException ex) {
				log.info("An exception has occured with mailing the user.");
			} 
		}
		
	}
}