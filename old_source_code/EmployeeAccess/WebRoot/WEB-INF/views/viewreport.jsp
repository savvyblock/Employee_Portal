<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
	<title>Employee Access Report</title>

	<meta http-equiv="content-type" content="text/html; charset=ISO-8859-1" />
	
	<c:set var="commonBase">/CommonWeb/</c:set>
	
	<link rel="stylesheet" href="${commonBase}styles/peimsReport.css" type="text/css" media="all" />
	<link rel="stylesheet" href="${commonBase}styles/reset.css" type="text/css" media="all" />
	<link rel="stylesheet" href="${commonBase}styles/typography.css" type="text/css" media="all" />
	
	<link rel="shortcut icon" href="${commonBase}images/texas.ico" />
	
	<!-- Dependencies -->
    <script type="text/javascript">
		function exportReport(type) {
			document.viewReportForm.output.value = "" + type;
			//ts20131218 for IE 10 - make sure that pdf will open in separate tab page from preview rpt
			if(type === 'pdf') {
				document.viewReportForm.target = "_blank";
				document.getElementById('exportButton').click();
				document.viewReportForm.target = "";
			} else {
				document.getElementById('exportButton').click();
			}
		}
	</script>
	
	<!-- DWR Files -->
	<script type="text/javascript" src="/EmployeeAccess/dwr/interface/viewReportController.js"></script>
  	<script type="text/javascript" src="/EmployeeAccess/dwr/engine.js"></script>
	
	<script type="text/javascript" src="/PEIMS/js/common.js"></script>
	<script type="text/javascript" src="/PEIMS/js/navigation.js"></script>
	<script type="text/javascript" src="/PEIMS/js/validation.js"></script>
	<script type="text/javascript" src="/EmployeeAccess/scripts/viewreport.js"></script>
	
	<c:import url="/WEB-INF/views/js-includes-top.jsp" />
	
	<style type="text/css">
		p, ul, ol, table, h1, h2, h3, img {
			  margin-bottom: 0px;
			  margin-left: 0px;
			  margin-right: 0px;
		}
	</style>
	
</head>

<body>
	<c:choose>
		<c:when test="${errorMsg != null}">	
			<div style="color: #B60000;">
				<h1>
					${errorMsg}
				</h1>
			</div>
		</c:when>
		<c:otherwise>
			<form:form name="viewReportForm">
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr>
						 <td width="50%"><br />&nbsp;</td>
						 <td align="left">
						 	<hr size="1" color="#000000" />
					   		<table width="100%" cellpadding="0" cellspacing="0" border="0">
					     		<tr>
									<td>
										<a href="javascript:exportReport('pdf')">
											<img src="${commonBase}images/reports/pdf.gif" border="0" alt="Export to PDF format" />
										</a>
									</td>
					       			<td>&nbsp;&nbsp;&nbsp;</td>
					       			<%-- 
									<td>
										<a href="javascript:exportReport('xls')">
											<img src="${commonBase}images/reports/xls.gif" border="0" alt="Export to XLS format" />
										</a>
									</td>
					       			<td>&nbsp;&nbsp;&nbsp;</td>
					       			--%>
					       			<td>
					       				<c:if test="${!param.hidecsv}" >										
											<a href="javascript:exportReport('csv')">
												<img src="${commonBase}images/reports/csv.gif" border="0" alt="Export to CSV format" />
											</a>										
										</c:if>
									</td>									
					       			<td width="45%">
					       				&nbsp;
				       				</td>
					   				
					   				<c:choose>
										<c:when test="${command.currentPage gt 0}">
											<td>
												<a href="#" class="link_button" buttonid="firstPageButton">
													<img src="${commonBase}images/reports/first.gif" border="0" alt="First Page" />
												</a>									
				    							<input class="hidden" type="submit" id="firstPageButton" name="mySubmitFirstPage" value="FirstPage" />
											</td>
											<td>
												<a href="#" class="link_button" buttonid="previousPageButton">
													<img src="${commonBase}images/reports/prev.gif" border="0" alt="Previous Page" />
												</a>									
				    							<input class="hidden" type="submit" id="previousPageButton" name="mySubmitPreviousPage" value="PreviousPage" />
											</td>        
										</c:when>
										<c:otherwise>
											<td>
												<img src="${commonBase}images/reports/first-d.gif" border="0" alt="First Page" />
											</td>
											<td>
												<img src="${commonBase}images/reports/prev-d.gif" border="0" alt="Previous Page" />
											</td>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${command.currentPage lt command.lastPage}">
											<td>									
												<a href="#" class="link_button" buttonid="nextPageButton">
													<img src="${commonBase}images/reports/next.gif" border="0" alt="Next Page" />
												</a>									
				    							<input class="hidden" type="submit" id="nextPageButton" name="mySubmitNextPage" value="NextPage" />
											</td>
											<td>
												<a href="#" class="link_button" buttonid="lastPageButton">
													<img src="${commonBase}images/reports/last.gif" border="0" alt="Last Page" />
												</a>									
				    							<input class="hidden" type="submit" id="lastPageButton" name="mySubmitLastPage" value="LastPage" />
											</td>
										</c:when>
										<c:otherwise>
											<td>
												<img src="${commonBase}images/reports/next-d.gif" border="0" alt="Next Page" />
											</td>
											<td>
												<img src="${commonBase}images/reports/last-d.gif" border="0" alt="Last Page" />
											</td>
										</c:otherwise>
									</c:choose>
										
									<td width="30%">
										&nbsp;
									</td>
									<td width="20%">
										<c:if test="${!param.hidebuttons}" >
											<input type="button" value="Sort"
											 <c:if test="${empty availableColumns || !sortable}">disabled="disabled"</c:if> 
											class="popup_trigger" popuptarget="reportSort" popupcentered="popupcentered"/>
											<input type="button" value="Filter" 
											 <c:if test="${empty availableColumns || !filterable}">disabled="disabled"</c:if> 
											class="popup_trigger" popuptarget="reportFilter" popupcentered="popupcentered" id="filterButton"/>
											<input type="submit" id="refreshButton" name="mySubmitRefresh" value="Refresh"  />
										</c:if>  
									</td>
					      		</tr>
				    		</table>
					    	<hr size="1" color="#000000" />
					  	</td>
					  	<td width="50%"><br />&nbsp;</td>
					</tr>
					<tr>
						<td width="50%">&nbsp;</td>
						<td align="center">
						    ${report}
						</td>
						<td width="50%">&nbsp;</td>
					</tr>
				</table>
		
				<form:hidden path="currentPage" htmlEscape="true" />
				<form:hidden path="lastPage" htmlEscape="true" />
				<input type="hidden" name="output" value="" />
				<input class="hidden" type="submit" id="exportButton" name="mySubmitExport" value="Export" />  
			</form:form>
		
			<div id="reportFilter" class="popup hidden">
				<c:import url="/WEB-INF/views/filterbox.jsp" />
			</div>
		
			<div id="reportSort" class="popup hidden">
		   		<c:import url="/WEB-INF/views/sortbox.jsp" />
			</div>
		</c:otherwise>
	</c:choose>
	
	<c:import url="/WEB-INF/views/js-includes-bottom.jsp" />
	
	<script type="text/javascript">
		$().ready(function() {  
			$('#add').click(function() {  
	   			return !$('#select1 option:selected').remove().appendTo('#select2');  
	  		});
	  		  
	  		$('#remove').click(function() {  
	   			return !$('#select2 option:selected').remove().appendTo('#select1');  
	  		});
	  		
	  		$('#wSort').submit(function() {  
				$('#select2 option').each(function(i) { 
					$(this).attr("selected", "selected");  
				});  
	 		});
	 	});
	 </script>
	 
	 <script type="text/javascript">	 
		<c:if  test="${command.errors == true}">
			document.getElementById('filterButton').click();
		</c:if>
	</script>
	
</body>
</html>