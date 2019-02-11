package net.esc20.txeis.EmployeeAccess.web.mvc.report;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.domainobject.report.ReportFilter;
import net.esc20.txeis.EmployeeAccess.domainobject.report.ViewReportCommand;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author skrishnan
 *
 */
public class ViewReportValidator implements Validator {

	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return ViewReportCommand.class.isAssignableFrom(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@SuppressWarnings("unchecked")
	public void validate(Object target, Errors errors) {
		ViewReportCommand inCommand = (ViewReportCommand) target;

		List<ReportFilter> reportfilters = inCommand.getFilterCriteria();

		HashMap<String, String> colAndLogicalMap = new HashMap();
		Iterator<ReportFilter> it = reportfilters.iterator();
		while (it.hasNext()) {
			ReportFilter filter = (ReportFilter) it.next();
			if (!isEmpty(filter.getColumn()) && isEmpty(filter.getValue())) {
				errors.reject("error." + filter.getColumn(),
						"Invalid Filter. Enter a value for " + filter.getColumn());
				return;
			}

			if (it.hasNext() && isEmpty(filter.getLogicalOperator())) {
				errors.reject("error." + "missingLogical_" + filter.getColumn(),
						"Invalid Filter. Enter a Logical Operator for " + filter.getColumn());
				return;
			}

			if (!it.hasNext() && !isEmpty(filter.getLogicalOperator())) {
				errors.reject("error." + "invalidLogical_" + filter.getColumn(),
						"Invalid Filter. Please Remove the Logical Operator for "
								+ filter.getColumn());
				return;
			}
			
			if (colAndLogicalMap.containsKey(filter.getColumn())) {
				if ((colAndLogicalMap.get(filter.getColumn())).equals("AND")) {
					errors.reject("error."+filter.getColumn(), filter.getColumn().toUpperCase() + " Column already exists on the Filter.");
					return;
				} else {
					colAndLogicalMap.put(filter.getColumn(), filter.getLogicalOperator());
				}
			} else {
				colAndLogicalMap.put(filter.getColumn(), filter.getLogicalOperator());
			}
		}
		
		
	}

	/**
	 * @param text
	 * @return
	 */
	private boolean isEmpty(String text) {
		return (text == null || text.trim().length() < 1);
	}	
	
}
