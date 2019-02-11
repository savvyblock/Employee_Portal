package net.esc20.txeis.EmployeeAccess.service.api;

import java.util.List;

import org.springframework.binding.message.MessageContext;
import org.springframework.webflow.core.collection.LocalSharedAttributeMap;

import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.DemoDomainObject;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.DemoInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.DemoInfoFields;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.Email;
import net.esc20.txeis.EmployeeAccess.web.view.Demo;

public interface IDemoService {
	
	List<String> getAvailableMaritalStatuses();
	List<String> getAvailableMaritalActualStatuses();
	List<String> getAvailableRestrictionCodes();
	List<String> getAvailableStates();
	List<String> getAvailableTitles();
	List<String> getAvailableGenerations();

	DemoInfo getDemoInfo(String employeeNumber);
	DemoInfo getDemoInfoPending(DemoInfo demoInfo);
	Demo setOptions(Demo demo);
	DemoInfoFields getDemoInfoChanges(DemoInfo demoInfo, DemoInfo demoInfoPending);
	Boolean saveDemo(MessageContext messageContext, Demo demo, DemoInfo demoInfoPending, DemoInfo demoInfo) throws Exception; 
	DemoInfoFields getRequiredFields();

	Boolean sendEmail(LocalSharedAttributeMap employeeInfo, Demo demo, DemoInfo demoInfoPending, DemoInfo demoInfoCurrent);

	DemoInfoFields getDocRequiredFields();

	Boolean populateRequiredFields();
	Email getPendingEmailRequests(String employeeNumber);
	Email getCurrentEmail(String employeeNumber);
	
	DemoDomainObject revertDomainObject( DemoDomainObject currentDemoDomainObject );
	
	String getMessage();

	
}