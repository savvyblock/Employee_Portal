
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>


<title>
	${sessionScope.languageJSON.headTitle.approveTravelRequest}</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet"
	href="<spring:theme code="commonPortals"/>/css/fullcalendar.min.css" />
<link rel="stylesheet"
	href="/<%=request.getContextPath().split("/")[1]%>/css/approveTravelRequest.css"
	type="text/css" media="all" />

<%@ include file="../commons/header.jsp"%>

</head>
<body class="hold-transition sidebar-mini">

	<%@ include file="../supervisor/apporverDashboardPopup/approvedRequest.jsp"%>
	
	<form:form  id="ApproveTravelRequestForm" name="ApproveTravelRequestForm" method="POST" modelAttribute="ApproveTravelRequestCommand" action="">
	
		<div class="wrapper">

			<form:hidden cssClass="text noReadOnly" id="anySelected" path="anySelected" />
			<form:hidden cssClass = "text noReadOnly" id = "showFinalApproverPopup" path="showFinalApproverPopup" />
			<form:hidden cssClass = "text noReadOnly" id = "refreshData" path="refreshData" />
			

			<%@ include file="../commons/bar.jsp"%>
			<main class="content-wrapper" tabindex="-1">
				<section class="content">
					<div class="clearfix section-title">
						<h1 class="pageTitle">${sessionScope.languageJSON.title.approveTravelRequests}</h1>
					</div>


					<div class="container-fluid">

						<div class="form-group in-line flex-auto leftPadding">

							<form:button id="ApproveButton" name="approveButtonTravelRequest" value="approveButtonTravelRequest" tabindex="1" class="btn btn-primary sm"> ${sessionScope.languageJSON.label.approve}</form:button>

							<form:button id="ReturnButton" name="returnTravelRequest" value="returnTravelRequest" tabindex="1" class="btn btn-primary sm">${sessionScope.languageJSON.label.return}</form:button>

						</div>

						<div class="content-white">
							<table class="table request-list responsive-table-1320"
								style="width:100%">
								<thead>
									<tr>
										<th scope="col" class="text-center">
											${sessionScope.languageJSON.label.selectAll} <br> <spring:bind
												path="selectAll">

												<form:checkbox path="selectAll"
													onclick="selectAllCheckboxes('toggleChkColumn', this, 'anySelected');"
													value="${selectAll}" tabindex="1"
													class="ignore_changes saveValue"
													id="approveTravelRequestSelectAll" />
											</spring:bind>

										</th>
										<th scope="col" class="text-center">
											${sessionScope.languageJSON.label.detail}</th>
										<th scope="col" class="text-center">
											${sessionScope.languageJSON.label.doc}</th>
										<th scope="col" class="text-center">
											${sessionScope.languageJSON.approveTravelRequest.vendorNumber}
										</th>
										<th scope="col" class="text-left">
											${sessionScope.languageJSON.approveTravelRequest.employeeName}
										</th>
										<th scope="col" class="text-center">
											${sessionScope.languageJSON.approveTravelRequest.travelRequestNumber}
										</th>
										<th scope="col" class="text-center">
											${sessionScope.languageJSON.approveTravelRequest.dateRequested}
										</th>
										<th scope="col" class="text-right">
											${sessionScope.languageJSON.approveTravelRequest.requestTotal}
										</th>
									</tr>

								</thead>



								<div>
									<tbody>
										<c:if
											test="${ApproveTravelRequestCommand.approveTravelRequests.size() > 0}">
											<c:forEach var="dataRow"
												items="${ApproveTravelRequestCommand.approveTravelRequests}"
												varStatus="row">

												<tr id="actionList_${row.index}" class="text-center">

													<td class="text-center"
														data-title="${sessionScope.languageJSON.label.selectAll}">
														<spring:bind
															path="approveTravelRequests[${row.index}].checkboxSelected">
															<form:checkbox
																class="toggleChkColumn ignore_changes saveValue"
																path="approveTravelRequests[${row.index}].checkboxSelected"
																onclick="selectCheckbox('approveTravelRequestSelectAll', this, 'toggleChkColumn','anySelected');"
																tabindex="1" id="checkboxSelected${row.index}"
																name="checkboxSelected${row.index}" />
														</spring:bind>
													</td>
													<td class="text-center"
														data-title="${sessionScope.languageJSON.label.detail}">
														<img src="<spring:theme code="commonBase" />images/details.png"
														onclick="showDetailTrvlRequest('${dataRow.travelRequestNumber}', '${dataRow.empNbr}', '${dataRow.overnight}')"
														aria-label="${sessionScope.languageJSON.approveTravelRequest.showTravelRecords}"/>
													</td>
													<td class="text-center"
														data-title="${sessionScope.languageJSON.label.document}">
														<img class="hover_button"
														src="<spring:theme code="commonBase" />images/document.gif"
														onclick="showDocumentModal('001', 'TRAVEL DOCUMENTS', 'TRAVEL', 'Y', '${dataRow.travelRequestNumber}')"
														style="margin:0px 0px 0px 0px; text-decoration: none;" />
													</td>
													<td class="text-center"
														data-title="${sessionScope.languageJSON.approveTravelRequest.vendorNumber}">
														${dataRow.vendorNbr}</td>
													<td class="text-left"
														data-title="${sessionScope.languageJSON.approveTravelRequest.employeeName}">
														${dataRow.employeeName}</td>
													<td class="text-center"
														data-title="${sessionScope.languageJSON.approveTravelRequest.travelRequestNumber}">
														${dataRow.travelRequestNumber}</td>
													<td class="text-center"
														data-title="${sessionScope.languageJSON.approveTravelRequest.dateRequested}">
														${dataRow.dateRequested}</td>
													<td class="text-right"
														data-title="${sessionScope.languageJSON.approveTravelRequest.requestTotal}">
														${dataRow.requestTotal}</td>

												</tr>
											</c:forEach>
										</c:if>
										<c:if
											test="${ApproveTravelRequestCommand.approveTravelRequests.size() eq 0}">
											<tr>
												<td colspan="9" class="text-left"><span>${sessionScope.languageJSON.label.noData}</span>
												</td>
											</tr>
										</c:if>
									</tbody>
								</div>
							</table>
						</div>
					</div>
				</section>

			</main>

			<%@ include file="../modal/leaveListCalendar.jsp"%>
			<%@ include file="../modal/travelDocument.jsp"%>
			<%@ include file="../commons/footer.jsp"%>
		
	
		</div>
	</form:form>
	
	
</body>
<script type="text/javascript"
	src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/supervisor/approveTravelRequestList.js"></script>
</html>
