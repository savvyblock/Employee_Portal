package net.esc20.txeis.EmployeeAccess.validation;

import java.io.Serializable;
import java.util.Map;

import org.springframework.binding.message.Message;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;

public abstract class AbstractValidator implements Serializable
{	
	private static final long serialVersionUID = -476927665705352473L;

	protected abstract Map<String,String> createTypeValidationMap();
	
	public void validateView(ValidationContext context)
	{
		MessageContext messageContext = context.getMessageContext();
		validateMessageContext(messageContext);
	}
	
	public void validateMessageContext(MessageContext messageContext)
	{
		Message[] messages = messageContext.getAllMessages();
		messageContext.clearMessages();
		Map<String,String> messageMap = createTypeValidationMap();

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
					text = text.replaceAll("\\s.*\\.", " ").trim();
					
					StringBuffer tempString = new StringBuffer(text);
					int index = 0;
					boolean lastSpace = false;
					for(char c: text.toCharArray())
					{
						if(Character.isUpperCase(c) && index > 0)
						{
							tempString.insert(index, " ");
							index++;
						}
						
						if(lastSpace && Character.isLowerCase(c))
						{
							tempString.replace(index, index+1, Character.toString(Character.toUpperCase(c)));
						}
						
						if(Character.isWhitespace(c) && index > 0)
						{
							lastSpace = true;
						}
						else
						{
							lastSpace = false;
						}
						
						index++;
					}
					text = tempString.toString();
					
				}
			}
			if(text.startsWith("evaluationException"))
			{
				if(messageMap.containsKey(source))
				{
					text = messageMap.get(source);
				}
				else
				{
					text = ContextValidationUtil.getInvalidMessage(text.replaceAll("evaluationException  on", "Invalid").trim());
					text = text.replaceAll("\\s.*\\.", " ").trim();
					
					StringBuffer tempString = new StringBuffer(text);
					int index = 0;
					boolean lastSpace = false;
					for(char c: text.toCharArray())
					{
						if(Character.isUpperCase(c) && index > 0)
						{
							tempString.insert(index, " ");
							index++;
						}
						
						if(lastSpace && Character.isLowerCase(c))
						{
							tempString.replace(index, index+1, Character.toString(Character.toUpperCase(c)));
						}
						
						if(Character.isWhitespace(c) && index > 0)
						{
							lastSpace = true;
						}
						else
						{
							lastSpace = false;
						}
						
						index++;
					}
					text = tempString.toString();
					
				}
			}
			
			messageContext.addMessage(ContextValidationUtil.buildMessage(message.getSource().toString(), text));
		}
	}
	
	public boolean validate(MessageContext messageContext)
	{
		messageContext.clearMessages();
		
		if(messageContext.getAllMessages().length > 0 && 
				!messageContext.getAllMessages()[0].getText().contains("Save successful"))
		{
			return false;
		}
		
		return true;
	}
	
	protected void assertSuccess(MessageContext messageContext)
	{
	/*	if(!ContextValidationUtil.assertSuccess(messageContext))
		{
			boolean hasDeleted = false;
			
			for(Selectable s : o.getSelected())
			{
				if(s.isSelected())
				{
					hasDeleted = true;
					break;
				}
			}
			
			if(hasDeleted)
			{
				ContextValidationUtil.addMessage(messageContext, "", "No deletions were performed");
			}
		}*/
	}
}
