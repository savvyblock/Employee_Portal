<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<style>
h4 {
	display: inline;
	padding: 0px;
	margin: 0px;
}
</style>
<title>${sessionScope.languageJSON.headTitle.travelRequests}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="../commons/header.jsp"%>
</head>
<body class="hold-transition sidebar-mini">
	<div class="wrapper">
		<%@ include file="../commons/bar.jsp"%>
		<main class="content-wrapper" tabindex="-1">
			<section class="content">
				<div class="clearfix section-title">
					<h1 class="pageTitle">${sessionScope.languageJSON.title.travelRequests}</h1>
					<div class="pull-right right-btn">
					<c:if test="${not empty travelRequestInfo.vendorNbr}">
						<button class="btn btn-primary" onclick="showAddRequestForm()"
							id="new-btn" data-toggle="modal"
							data-target="#travelRequestModal">
							<span>${sessionScope.languageJSON.label.add}</span>
						</button>
					</c:if>
					</div>
					<c:if test="${not empty sessionScope.options.messageTrvl}">
						<p class="topMsg error-hint" role="alert">${sessionScope.options.messageTrvl}</p>
					</c:if>
				</div>
				<div class="content-white EMP-detail heightFull">
					<table style="width: 70%"
						class="table no-border-table responsive-table print-table deductionTable">
						<c:choose>
							<c:when test="${not empty travelRequestInfo.vendorNbr}">
								<tr>
									<th class="text-left" style="width: 90px; ">${sessionScope.languageJSON.travelInfoTable.vendorNbr}:</th>
									<td class="text-left"">${travelRequestInfo.vendorNbr}</td>
									<th class="text-left" style="width: 90px;">${sessionScope.languageJSON.travelInfoTable.commuteDistance}:</th>
									<td class="text-left">${travelRequestInfo.trvlCommuteDist}
										<h4
											style="color: #0065FF; margin-left:15px; font-weight: 15; cursor: pointer; text-decoration: underline; text-decoration-color: #0065FF;"
											onclick="showChangeRequestForm()" id="new-btn"
											data-toggle="modal" data-target="#travelChangeModal">${sessionScope.languageJSON.label.change}</h4>
									</td>
								</tr>
								<tr>
									<th class="text-left">${sessionScope.languageJSON.travelInfoTable.address}:</th>
									<td class="text-left">${travelRequestInfo.addressAtn}<c:if test="${not empty travelRequestInfo.addressAtn}">${sessionScope.languageJSON.travelInfoTable.addressComma}</c:if>
										${travelRequestInfo.addressStreet}<br>
										${travelRequestInfo.addressCity}<c:if test="${not empty travelRequestInfo.addressCity}">${sessionScope.languageJSON.travelInfoTable.addressComma}</c:if>
										${travelRequestInfo.addressState}
										${travelRequestInfo.addressZip}<c:if test="${not empty travelRequestInfo.addressZip4}">${sessionScope.languageJSON.travelInfoTable.zipDash}${travelRequestInfo.addressZip4}</c:if>
									</td>
									<th class="text-left" style="width: 130px;">${sessionScope.languageJSON.travelInfoTable.payCampus}
										:</th>
									<td class="text-left">${travelRequestInfo.payCampus}</td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<td class="text-left"><span>${sessionScope.languageJSON.travelInfoTable.noVendorMsgOne}${sessionScope.userDetail.empNbr}${sessionScope.languageJSON.travelInfoTable.noVendorMsgTwo}</span>
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</table>
					<form class="no-print searchForm" action="travelRequest"
						id="SearchForm" method="POST">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						<div class="form-group type-group">
							<label class="form-title" for="SearchType"><span>${sessionScope.languageJSON.travelInfoTable.status}</span>:</label>
							<select id="SearchType" class="form-control" name="SearchType"
								autocomplete="off">
								<c:forEach var="type" items="${travelTypesforSearch}"
									varStatus="count">
									<option value="${type.code}"
										<c:if test="${type.code == SearchType }">selected</c:if>>${type.description}</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<label class="form-title" for="SearchStartInput"><span>${sessionScope.languageJSON.travelInfoTable.fromDate}</span>:</label>
							<div class="button-group">
								<div class="fDateGroup date" id="SearchStartDate"
									data-date-format="mm-dd-yyyy">
									<button class="prefix" type="button"
										aria-label="${sessionScope.languageJSON.label.showDatepicker}">
										<i class="fa fa-calendar"></i>
									</button>
									<input class="form-control dateInput" name="SearchStart"
										autocomplete="off" type="text"
										placeholder="${sessionScope.languageJSON.label.mmddyyyyFormat}"
										id="SearchStartInput" value="${SearchStart}">
									<button class="clear-btn" type="button" role="button"
										onclick="clearDate(this)"
										aria-label="${sessionScope.languageJSON.label.removeContent}">
										<i class="fa fa-times"></i>
									</button>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="form-title" for="SearchEndInput"> <span>${sessionScope.languageJSON.travelInfoTable.toDate}</span>:
							</label>
							<div class="button-group">
								<div class="fDateGroup date" id="SearchEndDate"
									data-date-format="mm-dd-yyyy">
									<button class="prefix" type="button"
										aria-label="${sessionScope.languageJSON.label.showDatepicker}">
										<i class="fa fa-calendar"></i>
									</button>
									<input class="form-control dateInput" type="text"
										name="SearchEnd" data-date-format="mm-dd-yyyy"
										autocomplete="off"
										placeholder="${sessionScope.languageJSON.label.mmddyyyyFormat}"
										title="${sessionScope.languageJSON.label.mmddyyyyFormat}"
										id="SearchEndInput" value="${SearchEnd}" />
									<button class="clear-btn" type="button" role="button"
										onclick="clearDate(this)"
										aria-label="${sessionScope.languageJSON.label.removeContent}"
										tabindex="0">
										<i class="fa fa-times"></i>
									</button>
								</div>
							</div>
						</div>
						<div class="form-group btn-group">
							<div style="margin-top:20px;">
								<button id="retrieve" type="button" role="button"
									class="btn btn-primary">
									${sessionScope.languageJSON.travelInfoTable.retrieve}</button>
							</div>
						</div>
					</form>
				<div class="form-group">
					<p class="error-hint hide" role="alert" aria-atomic="true"
						id="timeErrorMessage">
						${sessionScope.languageJSON.validator.fromDateNotGreaterToDate}</p>
				</div>
				<table class="table responsive-table mt-3 table-striped">
					<thead>
						<tr>
							<th class="text-center"></th>
							<th class="text-center">${sessionScope.languageJSON.travelInfoTable.trvlNbr}</th>
							<th class="text-center">${sessionScope.languageJSON.travelInfoTable.firstDate}</th>
							<th class="text-center">${sessionScope.languageJSON.travelInfoTable.status}</th>
							<th class="text-right">${sessionScope.languageJSON.travelInfoTable.requestTotal}</th>
							<th class="text-center">${sessionScope.languageJSON.travelInfoTable.checkNbr}</th>
							<th class="text-center">${sessionScope.languageJSON.travelInfoTable.checkDate}</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${fn:length(travelStatus) > 0}">
							<c:forEach var="TravelReport" items="${travelStatus}"
								varStatus="TravelReports">
								<tr>
									<td class="text-center">
											<c:if test="${TravelReport.status=='S' || TravelReport.status=='R'}">
											<h4 onClick="showTravelInformation('${TravelReport.tripNbr}')"
											style="color: #0065FF; font-weight: 15; cursor: pointer; text-decoration: underline; text-decoration-color: #0065FF;">
											${sessionScope.languageJSON.travelInfoTable.edit}</h4>
										</c:if>
											<c:if test="${TravelReport.status=='P' || TravelReport.status=='A'}">
											<h4 onClick="showTravelInformation('${TravelReport.tripNbr}')"
											style="color: #0065FF; font-weight: 15; cursor: pointer; text-decoration: underline; text-decoration-color: #0065FF;">
											${sessionScope.languageJSON.travelInfoTable.view}</h4>
										</c:if>
									</td>
									<td
										data-title="${sessionScope.languageJSON.travelInfoTable.travelNbr}"
										class="text-center">${TravelReport.tripNbr}</td>
									<td
										data-title="${sessionScope.languageJSON.travelInfoTable.firstDate}"
										class="text-center">${TravelReport.firstDate}</td>
										<td
											data-title="${sessionScope.languageJSON.travelInfoTable.status}"
											class="text-center">
											<c:if test="${TravelReport.status=='S'}">${sessionScope.languageJSON.travelInfoTable.saved}</c:if>
											<c:if test="${TravelReport.status=='P'}">${sessionScope.languageJSON.travelInfoTable.pending}</c:if>
											<c:if test="${TravelReport.status=='A'}">${sessionScope.languageJSON.travelInfoTable.approved}</c:if>
											<c:if test="${TravelReport.status=='R'}">${sessionScope.languageJSON.travelInfoTable.returned}</c:if>
										</td>
										<td
										data-title="${sessionScope.languageJSON.travelInfoTable.requestTotal}"
										class="text-right">${TravelReport.requestTotal}</td>
									<td
										data-title="${sessionScope.languageJSON.travelInfoTable.checkNbr}"
										class="text-center">${TravelReport.checkNbr}</td>
									<td
										data-title="${sessionScope.languageJSON.travelInfoTable.checkDate}"
										class="text-center">${TravelReport.checkDate}</td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${fn:length(travelStatus) == 0}">
							<tr>
								<td colspan="10" class="text-left"><span>${sessionScope.languageJSON.travelInfoTable.noRecords}</span>
								</td>
							</tr>
						</c:if>
					</tbody>
				</table>
	</div>
	<input type="hidden" id="sessionEmpNbr"
		value="${sessionScope.userDetail.empNbr}">
	<div class="needToClone">
		<input type="hidden" id="isPrintPDF" value="${isPrintPDF}" /> <input
			type="hidden" id="language" value="${language}" />
		<h2 class="table-top-title">
			<b>${sessionScope.languageJSON.label.vendorNbr}</b>
		</h2>
	</div>
	</section>
	</main>
	</div>
	<%@ include file="../commons/footer.jsp"%>
	<%@ include file="../modal/travelRequestModal.jsp"%>
	<%@ include file="../modal/travelChangeModal.jsp"%>
</body>
<script>
  var jsonTravelStatus = eval(${jsonTravelStatus});
</script>
<script
	src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/travelRequest/travelRequest.js"></script>
</html>