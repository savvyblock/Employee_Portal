package net.esc20.txeis.EmployeeAccess.service.api;

import java.util.List;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.ICode;

public interface ICodeService
{
	List<ICode> getFrequencies();
	List<ICode> getFrequenciesLeave();
}