package net.esc20.txeis.EmployeeAccess.web.mvc.report;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.ComparatorType;
import net.esc20.txeis.EmployeeAccess.domainobject.report.IReport;
import net.esc20.txeis.EmployeeAccess.domainobject.report.ParameterReport;
import net.esc20.txeis.EmployeeAccess.domainobject.report.ReportFilter;
import net.esc20.txeis.EmployeeAccess.domainobject.report.ReportSort;
import net.esc20.txeis.EmployeeAccess.domainobject.report.ViewReportCommand;
import net.esc20.txeis.EmployeeAccess.service.report.IJasperReportService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.WebContextFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;


/**
 * @author drivera
 *
 */
public class ViewReportController extends SimpleFormController {
	
	protected final Log logger = LogFactory.getLog(getClass());

	private IJasperReportService jasperReportService;
	
	private HttpServletResponse responseHold;

	public void setJasperReportService(IJasperReportService jasperReportService) {
		this.jasperReportService = jasperReportService;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#handleRequestInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String path = getServletContext().getRealPath("/");
		if (path != null && !path.endsWith("\\")) {
			path = path.concat("\\");
		}
		jasperReportService.setRealPath(path);
		responseHold = response;

		ModelAndView mav = super.handleRequestInternal(request, response);

		return mav;
	}

	@Override
	protected ModelAndView processFormSubmission(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception
	{
		if (errors != null && errors.hasGlobalErrors())
		{
			((ViewReportCommand) command).setErrors(true);
			ModelAndView mav = new ModelAndView(this.getFormView(), errors.getModel());
			IReport report = (IReport) WebUtils.getSessionAttribute(request, "PARAMETER_REPORT");
			
			if (report.getSortColumns() != null) {
				mav.getModel().put("availableColumns", report.getSortColumns());
				WebUtils.setSessionAttribute(request, "AVAIL_COLUMNS", report.getSortColumns());
				if (report instanceof ParameterReport) {
					//add boolean as to whether report has either sort, filter, or both
					mav.getModel().put("sortable", ((ParameterReport)report).isSortable());
					mav.getModel().put("filterable", ((ParameterReport)report).isFilterable());
				}
				else { //these will be the create reports
					mav.getModel().put("sortable", true);
					mav.getModel().put("filterable", true);
				}
			}
			mav.getModel().put("comparators", ComparatorType.values());
			return mav;
		}
		
		((ViewReportCommand) command).setErrors(false);			
		return super.processFormSubmission(request, response, command, errors);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest, java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	protected Map referenceData(HttpServletRequest request, Object command, Errors errors)
			throws Exception {

		Map<String, Object> referenceData = super.referenceData(request, command, errors);
		IReport report = (IReport) WebUtils.getSessionAttribute(request, "PARAMETER_REPORT");
		
		if (!this.isFormSubmission(request)) {			
			if (report != null) {
				referenceData = new HashMap<String, Object>();
				
				// Build and compile the Jasper report and put it into the session
				JasperPrint jprint = jasperReportService.buildReport(report);
				
				// checks if an error was generated by report
				// this will typically occur during pre report task where data is validated
				if (report instanceof ParameterReport) {
					String errorMsg = jprint.getProperty("errorMsg"); 
					if (errorMsg != null && !"".equals(errorMsg.trim())) {
						referenceData.put("errorMsg", errorMsg);
					}
				}
				
				WebUtils.setSessionAttribute(request,ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jprint);
				
				if (report.getId().equalsIgnoreCase("W2Report") ||
					(report.getId().length() > 16 &&
					 (report.getId().substring(0,16).equalsIgnoreCase("DHrs5250Form1095") ||
					  report.getId().substring(0,16).equalsIgnoreCase("DHrs5255Form1095")))) {
//					HttpServletResponse response = (HttpServletResponse) getResponse();
					JRPdfExporter pdfExporter = new JRPdfExporter(); 
					String date = new SimpleDateFormat("yyyy-MM-dd_HH-mm").format(Calendar.getInstance().getTime());
				
					responseHold.setHeader("Content-Disposition"," attachment; filename=" + jprint.getName() + "_" + date + ".pdf");
					responseHold.addHeader("Content-Description", "Download");
					responseHold.setContentType("application/pdf");
					responseHold.setHeader("Pragma", "public");
					responseHold.setHeader("Cache-control", "must-revalidate");
					pdfExporter = new JRPdfExporter();
					pdfExporter.setParameter(JRPdfExporterParameter.PDF_VERSION, JRPdfExporterParameter.PDF_VERSION_1_7);
					pdfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, responseHold.getOutputStream());
					pdfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jprint);
					pdfExporter.exportReport();
					
					return null;
				}  else {
					int page = 0;
					StringBuffer reportResult = new StringBuffer();
					int lastPage = getReport(jprint, page, reportResult);
					
					((ViewReportCommand) command).setCurrentPage(page);
					((ViewReportCommand) command).setLastPage(lastPage);
					
					referenceData.put("report", reportResult);
					
	
					// check to see if a report was returned and set the value stating it did
					if (reportResult.length() <= 0)
						referenceData.put("noReport", true);
				}
			}
		}
		// Add the sort-able/filter-able columns to the model
		if (report != null && report.getSortColumns() != null) {
			referenceData.put("availableColumns", report.getSortColumns());
			WebUtils.setSessionAttribute(request, "AVAIL_COLUMNS", report.getSortColumns());
			if (report instanceof ParameterReport) {
				//add boolean as to whether report has either sort, filter, or both
				referenceData.put("sortable", ((ParameterReport)report).isSortable());
				referenceData.put("filterable", ((ParameterReport)report).isFilterable());
			}
		}
		if (referenceData != null) {   //jf20120724 avoid null reference error
			referenceData.put("comparators", ComparatorType.values());
		}   //jf20120724
		return referenceData;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {

		ViewReportCommand command = (ViewReportCommand) super.formBackingObject(request);
		
		List<ReportFilter> filters = ListUtils.lazyList(new ArrayList<ReportFilter>(), 
				new Factory() {
					public Object create() {
						return new ReportFilter();
					}
				});
		
		command.setFilterCriteria(filters);
		//This also creates the first object
		//command.getFilterCriteria().get(0).setComparator(ComparatorType.EQUAL.getOperator());
		
		List<ReportSort> sorts = ListUtils.lazyList(new ArrayList<ReportSort>(), 
				new Factory() {
					public Object create() {
						return new ReportSort();
					}
				});
		
		command.setSortCriteria(sorts);
		//command.getSortCriteria().get(0).setAscending("ascending");
		return command;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
			Object command, BindException errors) throws Exception {

		OnSubmitParams params = new OnSubmitParams(request, response, command, errors);		
		ModelAndView mav = MethodMapper.callAppropriateMethod(this, ViewReportController.class,
				params);

		if (mav != null) {
			//After each submit add the sort/filter columns on the mav
			IReport report = (IReport) WebUtils.getSessionAttribute(params.getRequest(),
			"PARAMETER_REPORT");
	
			if (report.getSortColumns() != null) {
				mav.getModel().put("availableColumns", report.getSortColumns());
				WebUtils.setSessionAttribute(params.getRequest(), "AVAIL_COLUMNS", report.getSortColumns());
				if (report instanceof ParameterReport) {
					//add boolean as to whether report has either sort, filter, or both
					mav.getModel().put("sortable", ((ParameterReport)report).isSortable());
					mav.getModel().put("filterable", ((ParameterReport)report).isFilterable());
				}
			}
	
			mav.getModel().put("comparators", ComparatorType.values());
		}
		return mav;
	}

	/**
	 * @param params
	 * @return
	 * @throws JRException
	 * @throws SQLException
	 */
	protected ModelAndView onRefresh(OnSubmitParams params) throws JRException, SQLException {
		
		ModelAndView mav = new ModelAndView(this.getFormView(), params.getErrors().getModel());
		ViewReportCommand myCommand = (ViewReportCommand) params.getCommand();

		IReport report = (IReport) WebUtils.getSessionAttribute(params.getRequest(),
				"PARAMETER_REPORT");

		// Build and compile the Jasper report and put it into the session
		JasperPrint jprint = jasperReportService.buildReport(report);

		WebUtils.setSessionAttribute(params.getRequest(),
				ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jprint);
		
		int page = 0;
		StringBuffer reportResult = new StringBuffer();
		int lastPage = getReport(jprint, page, reportResult);

		((ViewReportCommand) myCommand).setCurrentPage(page);
		((ViewReportCommand) myCommand).setLastPage(lastPage);

		mav.getModel().put("report", reportResult);

		// check to see if a report was returned and set the value stating it did
		if (reportResult.length() <= 0)
			mav.getModel().put("noReport", true);

		return mav;
	}
	
	/**
	 * @param params
	 * @return
	 * @throws JRException
	 * @throws SQLException
	 */
	protected ModelAndView onSort(OnSubmitParams params) throws JRException, SQLException {

		ModelAndView mav = new ModelAndView(this.getFormView(), params.getErrors().getModel());
		ViewReportCommand myCommand = (ViewReportCommand) params.getCommand();

		IReport report = (IReport) WebUtils.getSessionAttribute(params.getRequest(),
				"PARAMETER_REPORT");

		// Build and compile the Jasper report and put it into the session
		JasperPrint jprint = null;
		
		if (myCommand.getSortCriteria() != null && myCommand.getSortCriteria().size() > 0) {
			jprint = jasperReportService.buildReportWithSort(report, myCommand.getSortCriteria());
		}
		else {
			jprint = jasperReportService.buildReport(report);
		}

		WebUtils.setSessionAttribute(params.getRequest(),
				ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jprint);
		
		int page = 0;
		StringBuffer reportResult = new StringBuffer();
		int lastPage = getReport(jprint, page, reportResult);
		
		((ViewReportCommand) myCommand).setCurrentPage(page);
		((ViewReportCommand) myCommand).setLastPage(lastPage);

		mav.getModel().put("report", reportResult);
		
		return mav;
	}
	
	/**
	 * @param params
	 * @return
	 * @throws JRException
	 * @throws SQLException
	 */
	protected ModelAndView onFilter(OnSubmitParams params) throws JRException, SQLException {
		
		ModelAndView mav = new ModelAndView(this.getFormView(), params.getErrors().getModel());
		ViewReportCommand myCommand = (ViewReportCommand) params.getCommand();

		IReport report = (IReport) WebUtils.getSessionAttribute(params.getRequest(),
				"PARAMETER_REPORT");

		// Build and compile the Jasper report and put it into the session
		JasperPrint jprint = null;
		if (myCommand.getFilterCriteria() != null && myCommand.getFilterCriteria().size() > 0) {
			jprint = jasperReportService.buildReportWithFilter(report, myCommand.getFilterCriteria());
		} else {
			jprint = jasperReportService.buildReport(report);
		}
		WebUtils.setSessionAttribute(params.getRequest(),
				ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jprint);
		
		int page = 0;
		StringBuffer reportResult = new StringBuffer();
		int lastPage = getReport(jprint, page, reportResult);

		((ViewReportCommand) myCommand).setCurrentPage(page);
		((ViewReportCommand) myCommand).setLastPage(lastPage);

		mav.getModel().put("report", reportResult);

		return mav;
	}
	/**
	 * Setting Content-Description, Pragma, and Cache-control are necessary for successful
	 * download via Internet Explorer.
	 * 
	 * @param params
	 * @return
	 * @throws IOException
	 * @throws JRException
	 * @throws SQLException
	 */
	protected ModelAndView onExport(OnSubmitParams params) throws IOException, JRException,
			SQLException {

		JasperPrint jprint = (JasperPrint) WebUtils.getSessionAttribute(params.getRequest(),
				ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE);
		
		String exportType = params.getRequest().getParameter("output");
		String date = new SimpleDateFormat("yyyy-MM-dd_HH-mm").format(Calendar.getInstance().getTime());
		
		if (exportType != null && exportType.length() > 0) {
			JRExporter exporter = null;

			if (exportType.equals("pdf")) {
				params.getResponse().setHeader("Content-Disposition",
						" attachment; filename=" + jprint.getName() + "_" + date + ".pdf");
				params.getResponse().addHeader("Content-Description", "Download");
				params.getResponse().setContentType("application/pdf");
				params.getResponse().setHeader("Pragma", "public");
				params.getResponse().setHeader("Cache-control", "must-revalidate");
				exporter = new JRPdfExporter();
				exporter.setParameter(JRPdfExporterParameter.PDF_VERSION, JRPdfExporterParameter.PDF_VERSION_1_7);
			}
			else if (exportType.equals("xls")) {
				params.getResponse().setHeader("Content-Disposition",
						" attachment; filename=" + jprint.getName() + "_" + date + ".xls");
				params.getResponse().addHeader("Content-Description", "Download");	
				params.getResponse().setContentType("application/vnd.ms-excel");
				params.getResponse().setHeader("Pragma", "public");
				params.getResponse().setHeader("Cache-control", "must-revalidate");
				exporter = new JExcelApiExporter();
				exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
				exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
				exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				exporter.setParameter(JRXlsAbstractExporterParameter.IS_FONT_SIZE_FIX_ENABLED, Boolean.TRUE);
			}
			else if (exportType.equals("rtf")) {
				params.getResponse().setHeader("Content-Disposition",
						" attachment; filename=" + jprint.getName() + "_" + date + ".rtf");
				params.getResponse().addHeader("Content-Description", "Download");	
				params.getResponse().setContentType("application/msword");
				params.getResponse().setHeader("Pragma", "public");
				params.getResponse().setHeader("Cache-control", "must-revalidate");
				exporter = new JRRtfExporter();
			}
			else if (exportType.equals("csv")) {
				params.getResponse().setHeader("Content-Disposition",
						" attachment; filename=" + jprint.getName() + "_" + date + ".csv");
				params.getResponse().addHeader("Content-Description", "Download");	
				params.getResponse().setContentType("application/vnd.ms-excel");
				params.getResponse().setHeader("Pragma", "public");
				params.getResponse().setHeader("Cache-control", "must-revalidate");
				exporter = new JRCsvExporter();
			}
			else {
				return null;
			}
			
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, 
					params.getResponse().getOutputStream());
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jprint);

			exporter.exportReport();
		}
		
		//TODO: figure out what really needs to be done here and in else statement above
		return null;
	}

	/**
	 * @param params
	 * @return
	 * @throws JRException
	 * @throws SQLException
	 */
	protected ModelAndView onNextPage(OnSubmitParams params) throws JRException, SQLException {

		ModelAndView mav = new ModelAndView(this.getFormView(), params.getErrors().getModel());
		ViewReportCommand myCommand = (ViewReportCommand) params.getCommand();

		int pageNumber = myCommand.getCurrentPage();

		// check to make sure this isn't the last page
		if (pageNumber < myCommand.getLastPage()) {
			pageNumber++;
		}

		setPage(params.getRequest(), mav, myCommand, pageNumber);

		return mav;
	}

	/**
	 * @param params
	 * @return
	 * @throws JRException
	 * @throws SQLException
	 */
	protected ModelAndView onPreviousPage(OnSubmitParams params) throws JRException, SQLException {

		ModelAndView mav = new ModelAndView(this.getFormView(), params.getErrors().getModel());
		ViewReportCommand myCommand = (ViewReportCommand) params.getCommand();

		int pageNumber = myCommand.getCurrentPage();

		// check to see if this isn't the first page
		if (pageNumber > 0) {
			pageNumber--;
		}

		setPage(params.getRequest(), mav, myCommand, pageNumber);

		return mav;
	}

	/**
	 * @param params
	 * @return
	 * @throws JRException
	 * @throws SQLException
	 */
	protected ModelAndView onFirstPage(OnSubmitParams params) throws JRException, SQLException {

		ModelAndView mav = new ModelAndView(this.getFormView(), params.getErrors().getModel());
		ViewReportCommand myCommand = (ViewReportCommand) params.getCommand();

		int pageNumber = 0;

		setPage(params.getRequest(), mav, myCommand, pageNumber);

		return mav;
	}

	/**
	 * @param params
	 * @return
	 * @throws JRException
	 * @throws SQLException
	 */
	protected ModelAndView onLastPage(OnSubmitParams params) throws JRException, SQLException {

		ModelAndView mav = new ModelAndView(this.getFormView(), params.getErrors().getModel());
		ViewReportCommand myCommand = (ViewReportCommand) params.getCommand();

		int pageNumber = myCommand.getLastPage();

		setPage(params.getRequest(), mav, myCommand, pageNumber);

		return mav;
	}

	/**
	 * @param request
	 * @param mav
	 * @param command
	 * @param page
	 * @throws JRException
	 * @throws SQLException
	 */
	private void setPage(HttpServletRequest request, ModelAndView mav, ViewReportCommand command,
			Integer page) throws JRException, SQLException {

		StringBuffer reportResult = new StringBuffer();

		JasperPrint jprint = (JasperPrint) WebUtils.getSessionAttribute(request,
				ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE);

		int lastPage = getReport(jprint, page, reportResult);

		command.setCurrentPage(page);
		command.setLastPage(lastPage);

		mav.getModel().put("report", reportResult);
	}

	/**
	 * @param jprint
	 * @param page
	 * @param report
	 * @return
	 * @throws JRException
	 * @throws SQLException
	 */
	private int getReport(JasperPrint jprint, Integer page, StringBuffer report)
			throws JRException, SQLException {

		int lastPage = 0;

		if (jprint != null) {
			boolean emptyReport = jprint.getPages().isEmpty();
			
			if (!emptyReport) {

				lastPage = (jprint.getPages().size() - 1);

				if (page < 0) {
					page = 0;
				}

				if (page > lastPage) {
					page = lastPage;
				}

				JRExporter exporter = new JRHtmlExporter();
				
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jprint);
				exporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER, report);
				exporter.setParameter(JRExporterParameter.PAGE_INDEX, page);
				
				exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER, "");
				exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML, "");
				exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, "");

				exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.valueOf(false));
				exporter.setParameter(JRHtmlExporterParameter.FRAMES_AS_NESTED_TABLES, Boolean.valueOf(true));
				
				exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "../image?image=");
				exporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT, JRHtmlExporterParameter.SIZE_UNIT_POINT);   //jf20140219 uncommented to make report preview bigger
				
				exporter.exportReport();
			}
		}

		return lastPage;
	}
	
	/**
	 * AJAX function that is called whenever the user adds a new row to the Filter Criteria.
	 * @return
	 */
	public String[][] getAvailableColumns() {
		
		IReport report = (IReport) WebUtils.getSessionAttribute(WebContextFactory.get()
				.getHttpServletRequest(), "PARAMETER_REPORT");
		

		
		Map<String, String> columns = report.getSortColumns();
		Iterator<Map.Entry<String, String>> it = columns.entrySet().iterator();
		
		String[][] columnArray = new String[columns.size()][2];
		int i = 0;
		
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			columnArray[i][0] = entry.getKey();
			columnArray[i][1] = entry.getValue();
			
			i++;
		}
		
		return columnArray;
	}
	
	/**
	 * AJAX function that is called whenever the user adds a new row to the Filter Criteria.
	 * @return
	 */
	public ComparatorType[] getComparators() {
		
		ComparatorType[] comparators = ComparatorType.values();

		return comparators;
	}
}
