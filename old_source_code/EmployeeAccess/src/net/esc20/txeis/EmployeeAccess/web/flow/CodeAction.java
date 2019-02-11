package net.esc20.txeis.EmployeeAccess.web.flow;

import java.util.ArrayList;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.ICode;

import org.springframework.stereotype.Component;

@Component
public class CodeAction
{
	public List<ICode> filter(List<ICode> codes, String filterCode, String filterDescription)
	{
		List<ICode> filtered = new ArrayList<ICode>();
		
		if(codes != null)
		{
			for(ICode code : codes)
			{
				if((filterCode == null || code.getCode().toLowerCase().startsWith(filterCode.toLowerCase())) && 
					(filterDescription == null || code.getDescription().toLowerCase().startsWith(filterDescription.toLowerCase())))
				{
					filtered.add(code);
				}
			}
		}
		
		return filtered;
	}
}