package net.esc20.txeis.EmployeeAccess.web.mvc.report;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

/**
 * @author Jason Parker
 *
 */
public class MethodMapper {
	
	protected static final Log logger = LogFactory.getLog(MethodMapper.class);
	
	private static final Pattern alpha = Pattern.compile("\\w+");
	
	/**
	 * @param instance
	 * @param myClass
	 * @param params
	 * @return
	 * @throws Exception
	 */
	protected static ModelAndView callAppropriateMethod(Object instance, Class myClass,
			ISubmitParams params) {
		
		ModelAndView mav = null; 
		
		@SuppressWarnings("unchecked")
		Map<String, Object> map = WebUtils.getParametersStartingWith(params.getRequest(),
				"mySubmit");
		
		if (map != null) {
			Set<String> keyset = map.keySet();
			for (String key : keyset) {
				if (alpha.matcher(key).matches() && key.length() < 32) {
					String methodName = "on" + key;
					
					Class[] paramTypes = new Class[1];
					if (params instanceof OnSubmitParams) {					
						paramTypes[0] = OnSubmitParams.class;
					} else if (params instanceof OnSubmitAnnotatedParams) {
						paramTypes[0] = OnSubmitAnnotatedParams.class;
					}
					
					Method meth = null;
					try {
						meth = myClass.getDeclaredMethod(methodName, paramTypes);
						meth.setAccessible(true);
						Object returnValue = meth.invoke(instance, new Object[] { params });
						mav = (ModelAndView) returnValue;
					}
					catch (NoSuchMethodException nsme) {
						logger.error("No method corresponds with the name: " + methodName + " for the class: " + myClass.getCanonicalName());
						mav = null;
						break;
					}
					catch (InvocationTargetException ite) {
						logger.error("Error while processing method " + methodName, ite);
						
						mav = null;
						break;
					}
					catch (IllegalAccessException iae) {
						mav = null;
						break;
					}
					
					break;
				}
			}
		}
		
		return mav;
	}
	
	
	public static ModelAndView callAppropriateMethod(Object instance, Class myClass, OnSubmitParams params) {
		return callAppropriateMethod(instance, myClass, (ISubmitParams)params);
	}

	/**
	 * @param instance
	 * @param myClass
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static ModelAndView callAppropriateMethod(Object instance, Class myClass, OnSubmitAnnotatedParams params) {
		return callAppropriateMethod(instance, myClass, (ISubmitParams)params);
	}
}
