package net.esc20.txeis.EmployeeAccess.validation;

import java.util.HashMap;
import java.util.Map;

import net.esc20.txeis.EmployeeAccess.web.view.Leave;

import org.springframework.binding.validation.ValidationContext;
import org.springframework.stereotype.Component;

@Component
public class LeaveValidator extends AbstractValidator
{
	private static final long serialVersionUID = -2585511365510500746L;

	public void validateLeave(Leave leave, ValidationContext context)
	{
		validateView(context);
		
		if(leave.getFrom() != null && leave.getTo() != null && leave.getFrom().after(leave.getTo()))
		{
			ContextValidationUtil.addMessage(context.getMessageContext(), "from", 
					"From Date of Leave cannot be greater than To Date of Leave.");
		}
	}
	
	@Override
	protected Map<String, String> createTypeValidationMap() {
		Map<String,String> map = new HashMap<String,String>();
		map.put("from", "Invalid From Date of Leave");
		map.put("to", "Invalid To Date of Leave");
		return map;
	}
}