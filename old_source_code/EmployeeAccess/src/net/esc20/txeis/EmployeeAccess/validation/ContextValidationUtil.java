package net.esc20.txeis.EmployeeAccess.validation;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import net.esc20.txeis.EmployeeAccess.conversion.RscccTime;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.ICode;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;

import org.springframework.binding.message.Message;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.message.MessageResolver;

public class ContextValidationUtil{
	static public void assertValidTime(String value, MessageContext messageContext, String path, String message, boolean failOnNull)
	{
		boolean isInvalid = false;
		
		if(StringUtil.isNullOrEmpty(value) && failOnNull)
		{
			isInvalid = true;
		}
		else if(!StringUtil.isNullOrEmpty(value))
		{
			String[] t = value.split("[\\:\\s]");
			if(t.length == 3)
			{
				try
				{
					if(Integer.parseInt(t[0]) > 12 || Integer.parseInt(t[1]) > 59)
					{
						isInvalid = true;
					}
					else
					{
						try
						{
		
							RscccTime.convertToDate(value);
						}
						catch(ParseException ex) 
						{
							isInvalid = true;
						}
					}
				}
				catch(NumberFormatException ex)
				{
					isInvalid = true;
				}
			}
			else
			{
				isInvalid = true;
			}
		}
		
		if(isInvalid)
		{
			addMessage(messageContext, path, message);
		}
	}

	static public void assertEquals(Object value, Object target, MessageContext messageContext, String path, String message, boolean failOnNull)
	{
		if((failOnNull && isNullOrEmpty(value)) ||
				!value.equals(target))
		{
			addMessage(messageContext, path, message);
		}
	}
	
	static public boolean isNullOrEmpty(Object value)
	{
		return value == null || 
		(value instanceof String && StringUtil.isNullOrEmpty((String)value)) ||
		(value instanceof BigDecimal && ((BigDecimal)value).doubleValue() == 0);
	}
	
	static public boolean areEqual(Object value, Object target)
	{
		return (value == null && target == null) || ((value != null && target != null) && value.equals(target));
	}
	
	static public void assertMatches(String value, String regex, MessageContext messageContext, String path, String message, boolean failOnNull)
	{
		if((failOnNull && StringUtil.isNullOrEmpty(value)) ||
			(!StringUtil.isNullOrEmpty(value) && !value.matches(regex)))
		{
			addMessage(messageContext, path, message);
		}
	}
	
	static public boolean assertContains(String value, List<? extends ICode> list, MessageContext messageContext, String path, String fieldName, boolean failOnNull)
	{
		if(StringUtil.isNullOrEmpty(value))
		{
			if(failOnNull)
			{
				addMessage(messageContext, path, getInvalidMessage(fieldName));
				return false;
			}
			else
			{
				return true;
			}
		}
		
		for(ICode code : list)
		{
			if(StringUtil.trim(code.getCode()).equals(StringUtil.trim(value.toString())))
			{
				return true;
			}
		}
		
		addMessage(messageContext, path, getInvalidMessage(fieldName));
		return false;
	}
	
	static public boolean assertNotNull(Object value, MessageContext messageContext, String path, String message)
	{
		if(isNullOrEmpty(value))
		{
			addMessage(messageContext, path, message);
			return false;
		}		
		return true;
	}
	
	// Checks that string length is within min & max.  Also does null/empty check if required is set to true.
	static public boolean assertStringLength(int minLength, int maxLength, boolean required, String value, 
												MessageContext messageContext, String path, String fieldName)
	{
		if(required && isNullOrEmpty(value))
		{
			String message = ContextValidationUtil.getNullMessage(fieldName, "");
			addMessage(messageContext, path, message);
			return false;
		}
		else
		{
			if(value.length() < minLength || value.length() > maxLength)
			{
				String message = ContextValidationUtil.getStringLengthMessage(fieldName, minLength, maxLength);
				addMessage(messageContext, path, message);
				return false;
			}
		}
		return true;
	}
	
	static public boolean assertNumeric(Object value, MessageContext messageContext, String path, String message)
	{
		if(value instanceof String && !StringUtil.isNullOrEmpty((String)value) && !StringUtil.isDouble((String)value) )
		{
			addMessage(messageContext, path, message);
			return false;
		}
		return true;
	}
	
	static public boolean assertInteger(Object value, MessageContext messageContext, String path, String message)
	{
		if(value instanceof String && !StringUtil.isNullOrEmpty((String)value) && !StringUtil.isInteger((String)value) )
		{
			addMessage(messageContext, path, message);
			return false;
		}
		return true;
	}
	
	static public MessageContext checkTypeMismatch(MessageContext messageContext, Map<String, String> messageMap) {
		Message[] messages = messageContext.getAllMessages();
		messageContext.clearMessages();
		
		for(Message message : messages)
		{
			String text = message.getText();
			String source = (String)message.getSource();
			
			if(text.startsWith("typeMismatch"))
			{
				if(messageMap.containsKey(source))
				{
					text = messageMap.get(source);
				}
				else 
				{
					text = ContextValidationUtil.getInvalidMessage(text.replaceAll("typeMismatch on", "").trim());
				}
			} else if(text.startsWith("evaluationException")) {
				if(messageMap.containsKey(source))
				{
					text = messageMap.get(source);
				}
				else 
				{
					text = ContextValidationUtil.getInvalidMessage(text.replaceAll("evaluationException on", "").trim());
				}
			}
			
			messageContext.addMessage(buildMessage(message.getSource().toString(), text));
		}
		
		return messageContext;
	}
	
	static public boolean assertSuccess(MessageContext messageContext)
	{
		if(messageContext.getAllMessages().length == 0)
		{
			MessageResolver mr = new MessageBuilder().info().source(null).defaultText("Save successful").build();
			messageContext.addMessage(mr);
			return true;
		}
		
		return false;
	}
	
	static public void addMessage(MessageContext messageContext, String path, String message)
	{
		messageContext.addMessage(buildMessage(path, message));
	}
	
	static public MessageResolver buildMessage(String source, String message)
	{
		return new MessageBuilder().error().source(source).defaultText(message).build();
	}
	
	static public String getEqualsMessage(String field, String target)
	{
		return field + " must be " + target;
	}
	
	static public String getInvalidMessage(String field, String example)
	{
		return getInvalidMessage(field) + " " + example;
	}
	
	static public String getInvalidMessage(String field)
	{
		return "Invalid " + field;
	}
	
	static public String getNullMessage(String field, String example)
	{
		return getNullMessage(field) + " " + example;
	}
	
	static public String getNumericMessage(String field)
	{
		return field + " must be a numeric value.";
	}
	
	static public String getNullMessage(String field)
	{
		return "Required value missing for " + field;
	}
	
	static public String getStringLengthMessage(String field, int minLength, int maxLength)
	{
		return field + " must be between " + minLength + "-" + maxLength + " characters in length";
	}
	
	static public String getDateExample()
	{
		return "(Example: 04-05-2006)";
	}
	
	static public String getTimeExample()
	{
		return "(Example: 09:15 A)";
	}
	
	static public String getHeightExample()
	{
		return "(Example: 72.50)";
	}
	
	static public String getWeightExample()
	{
		return "(Example: 190.0)";
	}
	
	static public String getSystolicExample()
	{
		return "(Example: 120)";
	}
	
	static public String getDiastolicExample()
	{
		return "(Example: 80)";
	}
	
	static public String getStudentIdExample()
	{
		return "(Example: 012345)";
	}
	
	static public MessageResolver buildSaveMessage(String source, String message)
	{
		return new MessageBuilder().source(source).defaultText(message).build();
	}
}
