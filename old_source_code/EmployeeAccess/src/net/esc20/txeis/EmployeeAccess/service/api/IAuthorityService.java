package net.esc20.txeis.EmployeeAccess.service.api;

import net.esc20.txeis.EmployeeAccess.domainobject.User;

import org.springframework.binding.message.MessageContext;

public interface IAuthorityService {

	boolean isAccountTempLocked(MessageContext messageContext, User user);
}
