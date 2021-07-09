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
<title>${sessionScope.languageJSON.headTitle.travelRequestsMileage}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="../commons/header.jsp"%>
</head>

<body class="hold-transition sidebar-mini" onload="loadRows();">
	<div class="wrapper">
		<%@ include file="../commons/bar.jsp"%>
		<main class="content-wrapper" tabindex="-1">
			<section class="content">
				<div class="clearfix section-title">
					<div class="float-left">
						<input type="hidden" name="${_csrf.parameterName}" id="csrfToken"
							value="${_csrf.token}" /> <input type="hidden" name="empNbr"
							id="empNbrModal" value="${sessionScope.userDetail.empNbr}" /> <input
							type="hidden" name="tripNbr" id="tripNbrModal" />
						<h1 class="pageTitle">${sessionScope.languageJSON.title.mileageRequests}</h1>
						<c:if test="${not empty sessionScope.options.messageTrvl}">
							<p class="topMsg error-hint" role="alert">${sessionScope.options.messageTrvl}
							</p>
						</c:if>
						<p class="topMsg success-hint" role="alert" id="mileageSuccess"></p>
						<p class="topMsg error-hint" role="alert" id="mileageError"></p>
					</div>
					<div class="pull-right right-btn float-right">
						<button class="btn btn-primary"
							onclick="showAssignAccountCodeForm()" id="new-btn1"
							data-toggle="modal" data-target="#assignAccountCodesModal">
							<span>${sessionScope.languageJSON.label.assignAccountCodes}</span>
						</button>
						<button id="documentUpload" disabled class="btn btn-primary btn-sm" type="button">
							<span>${sessionScope.languageJSON.label.documents}</span>
						</button>
						<button class="btn btn-primary" style="display:none" onclick="showRequestForm()"
							id="new-btn3">
							<span>${sessionScope.languageJSON.label.print}</span>
						</button>
						<button class="btn btn-primary" onclick="saveRequestForm()"
							id="new-btn4">
							<span>${sessionScope.languageJSON.label.save}</span>
						</button>
						<button class="btn btn-primary" onclick="submitRequestForm()"
							id="new-btn5">
							<span>${sessionScope.languageJSON.label.submit}</span>
						</button>
						<button class="btn btn-secondary" onclick="cancelForm()"
							id="new-btn5">
							<span>${sessionScope.languageJSON.label.cancel}</span>
						</button>
					</div>
				</div>
				<div class="content-white TRVL-detail heightFull">
					<div class="groupbox">
						<table class="table no-border-table" style="padding:5px;">
							<tr>
								<td>
									<h4 class="groupTitle">
										${sessionScope.languageJSON.title.travelSummary}</h4>
								</td>
							</tr>
							<tr>
								<td style="width: 45%;">
									<table
										class="table no-border-table responsive-table print-table deductionTable">
										<c:choose>
											<c:when test="${empty travelRequestInfo.vendorNbr}">
												<tr>
													<td><label class="label-color text-left">${sessionScope.languageJSON.mileageInfoTable.travelNbr}:
															&nbsp;&nbsp;</label><label
														class="label-color text-right" id="travelNbr"></label></td>
													<td><label class="label-color text-left">${sessionScope.languageJSON.mileageInfoTable.status}:
															&nbsp;&nbsp;</label><label
														class="label-color text-right" id="travelStatus"></label></td>
												</tr>
												<tr>
													<td style="width: 37%"><ul>
															<li><label class="label-color float-left">${sessionScope.languageJSON.mileageInfoTable.poNbr}
																	:</label><input type="hidden" id="po_nbr" value="true">
																<input
																class="form-control-custom mr-sm-2 form-rounded border-secondary float-left"
																type="text" placeholder="5.0" aria-label="Search"
																id="po_nbr_ellipsis" style="width: 53%;" /></li></td>
													<td style="width: 30%;"><label
														class="label-color text-left">${sessionScope.languageJSON.mileageInfoTable.dateEntered}:&nbsp;&nbsp;</label><label
														class="label-color text-right" id="dateEntered"></label></td>
												</tr>
												<tr>
													<td />
													<td><label class="label-color text-left">${sessionScope.languageJSON.mileageInfoTable.totalMiles}:&nbsp;&nbsp;</label><label
														class="label-color text-right" id="totalMiles">${travelRequestInfo.totalMiles}</label></td>
												</tr>
												<tr>
													<td />
													<td><label class="label-color text-left">${sessionScope.languageJSON.mileageInfoTable.totalMisc}:&nbsp;&nbsp;</label><label
														class="label-color text-right" id="totalMisc">${travelRequestInfo.totalMisc}</label></td>
												</tr>
												<tr>
													<td />
													<td style="-webkit-column-width:200px"><label
														class="label-color text-left">${sessionScope.languageJSON.mileageInfoTable.totalRequest}:&nbsp;&nbsp;</label><label
														class="label-color text-right" id="totalRequest">${travelRequestInfo.totalRequest}</label></td>
												</tr>
											</c:when>
										</c:choose>
									</table>
								</td>
								<td style="width: 2%;" />
								<td>
									<table class="scrollableContent">
										<thead>
											<div class="row"></div>
											<tr>
												<td class="label-color text-left">
													${sessionScope.languageJSON.mileageInfoTable.account}</td>
												<td style="width:70%"></td>
												<td class="label-color text-right">
													${sessionScope.languageJSON.mileageInfoTable.amount}</td>
											</tr>
										</thead>
										</div>
										<tbody id="trvlSummaryTable">
											<tr />
										</tbody>
										</div>
									</table>
								</td>
								<td>
									<table>
										<thead>
											<th><label class="label-color text-left">${sessionScope.languageJSON.mileageInfoTable.approver}</label></th>
											<th><label class="label-color text-left">${sessionScope.languageJSON.mileageInfoTable.altApprover}</label></th>
											<th><label class="label-color text-left">${sessionScope.languageJSON.mileageInfoTable.approvalStatus}</label></th>
										</thead>
										<tbody>
											<c:if test="${fn:length(travelApprovalPath) > 0}">
												<c:forEach var="dataRow" items="${travelApprovalPath}" varStatus="row">
													<tr>
														<td id="wfPath_ApprvlUser_${row.index}" nowrap="nowrap" class="text-left">${fn:substring(dataRow.bpoReqApprvlUserId, 0, 10)}</td>
														<td id="wfPath_AlternateUser_${row.index}" nowrap="nowrap" class="text-left">${fn:substring(dataRow.bpoReqApprvlAltUserId, 0, 10)}</td>
														<td id="wfPath_Status_${row.index}" nowrap="nowrap" class="text-left">${dataRow.bpoReqApprvlStat}</td>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
								</td>
							</tr>
						</table>
					</div>
					<div class="content TRVL-detail heightFull">
						<div class="groupbox">
							<input type="text" class="hidden" id="focusFeild"
								value="ADDROWTAB">
							<table class="table no-border-table">
								<thead style="background-color: #2381A5; color:white">
									<div class="row">
										<tr class="columnheader">
											<th style="width:5%"></th>
											<th id="dtTrvl" style="width: 15%;">
												${sessionScope.languageJSON.mileageInfoTable.dateOfTravelTime}
											</th>
											<th style="width: 15%;"></th>
											<th style="width: 22%;"></th>
											<th id="pntOrigin" style="width: 20%; vertical-align: top;">
												${sessionScope.languageJSON.mileageInfoTable.pointOfOrigin}
											</th>
											<th id="dest" style="width: 20%; vertical-align: top;">
												${sessionScope.languageJSON.mileageInfoTable.destination}</th>
										</tr>
									</div>
								</thead>
								</div>
								<tbody id="trvlTable">
									<tr>
										<td />
									</tr>
								</tbody>
							</table>
						</div>
						<input type="hidden" name="isAdd" id="isAdd"> <input
							type="hidden" id="requiredField"
							value="${sessionScope.languageJSON.validator.requiredField}">
						<input type="hidden" id="mileageTotalMinusCommuteMsg"
							value="${sessionScope.languageJSON.validator.mileageTotalMinusCommute}">
						<input type="hidden" id="mileageBeginingGreater"
							value="${sessionScope.languageJSON.validator.mileageBeginingGreater}">
						<input type="hidden" id="startEndTimeGreaterMsg"
							value="${sessionScope.languageJSON.validator.startEndTimeGreater}">
						<input type="hidden" id="timeOverlapError"
							value="${sessionScope.languageJSON.validator.timeOverlap}">
						<input type="hidden" id="travelDateEmptyError"
							value="${sessionScope.languageJSON.validator.travelDateEmpty}">
						<input type="hidden" id="travelContactEmptyError"
							value="${sessionScope.languageJSON.validator.travelContactEmpty}">
						<input type="hidden" id="originDescriptionEmptyError"
							value="${sessionScope.languageJSON.validator.originDescriptionEmpty}">
						<input type="hidden" id="destinationDescriptionEmptyError"
							value="${sessionScope.languageJSON.validator.destinationDescriptionEmpty}">
						<input type="hidden" id="startTimeEmptyError"
							value="${sessionScope.languageJSON.validator.startTimeEmpty}">
						<input type="hidden" id="endTimeEmptyError"
							value="${sessionScope.languageJSON.validator.endTimeEmpty}">
						<input type="hidden" id="purposeEmptyError"
							value="${sessionScope.languageJSON.validator.purposeEmpty}">
						<input type="hidden" id="mileageStartEmptyError"
							value="${sessionScope.languageJSON.validator.mileageStartEmpty}">
						<input type="hidden" id="mileageEndEmptyError"
							value="${sessionScope.languageJSON.validator.mileageEndEmpty}">
						<input type="hidden" id="mapEmptyError"
							value="${sessionScope.languageJSON.validator.mapEmpty}">
						<input type="hidden" id="otherReasonEmptyError"
							value="${sessionScope.languageJSON.validator.otherReasonEmpty}">
						<input type="hidden" id="accountCodesEmptyError"
							value="${sessionScope.languageJSON.validator.accountCodesEmpty}">
						<input type="hidden" id="fromZipValidError"
							value="${sessionScope.languageJSON.validator.fromZipValid}">
						<input type="hidden" id="toZipValidError"
							value="${sessionScope.languageJSON.validator.toZipValid}">
						<input type="hidden" id="lblShowDatePicker"
							value="${sessionScope.languageJSON.label.showDatepicker}">
						<input type="hidden" id="lblMmddyyyyFormat"
							value="${sessionScope.languageJSON.label.mmddyyyyFormat}">
						<input type="hidden" id="lblMilage"
							value="${sessionScope.languageJSON.mileageInfoTable.milage}">
						<input type="hidden" id="lblCity"
							value="${sessionScope.languageJSON.mileageInfoTable.city}">
						<input type="hidden" id="lblState"
							value="${sessionScope.languageJSON.mileageInfoTable.state}">
						<input type="hidden" id="lblRoundTrip"
							value="${sessionScope.languageJSON.mileageInfoTable.roundTrip}">
						<input type="hidden" id="lblCommute"
							value="${sessionScope.languageJSON.mileageInfoTable.commute}">
						<input type="hidden" id="lblZip"
							value="${sessionScope.languageJSON.mileageInfoTable.zip}">
						<input type="hidden" id="lblMilageRate"
							value="${sessionScope.languageJSON.mileageInfoTable.milageRate}">
						<input type="hidden" id="lblCity"
							value="${sessionScope.languageJSON.mileageInfoTable.city}">
						<input type="hidden" id="lblTotMilageAmnt"
							value="${sessionScope.languageJSON.mileageInfoTable.totMilageAmnt}">
						<input type="hidden" id="lblMiscAmnt"
							value="${sessionScope.languageJSON.mileageInfoTable.miscAmnt}">
						<input type="hidden" id="lblTotMiscAmnt"
							value="${sessionScope.languageJSON.mileageInfoTable.totMiscAmnt}">
						<input type="hidden" id="lblAccntCodes"
							value="${sessionScope.languageJSON.mileageInfoTable.accntCodes}">
						<input type="hidden" id="lblTotReiumbersment"
							value="${sessionScope.languageJSON.mileageInfoTable.totReiumbersment}">
						<input type="hidden" id="lblTotal"
							value="${sessionScope.languageJSON.mileageInfoTable.total}">
						<input type="hidden" id="lblContact1"
							value="${sessionScope.languageJSON.mileageInfoTable.contact}">
						<input type="hidden" id="lblPurpose"
							value="${sessionScope.languageJSON.mileageInfoTable.purpose}">
						<input type="hidden" id="sessionEmpNbr"
							value="${sessionScope.userDetail.empNbr}"> <input
							type="hidden" id="travelRequestsData"
							value="${sessionScope.travelRequestsData}">
						<div class="needToClone">
							<input type="hidden" id="isPrintPDF" value="${isPrintPDF}" /> <input
								type="hidden" id="language" value="${language}" />
							<h2 class="table-top-title">
								<b>${sessionScope.languageJSON.label.vendorNbr}</b>
							</h2>
						</div>
					</div>
				</div>
			</section>
		</main>
	</div>
	<%@ include file="../modal/assignAccountCodesModal.jsp"%>
	<%@ include file="../modal/travelDocument.jsp"%>
	<%@ include file="../commons/footer.jsp"%>
</body>
<script>
  var tripData = eval(${tripNbr});
  var travelDataList = eval(${travelRequestsData});
  var accountsCodeList = eval(${accountCodesData});
  var accountDesc = eval(${accountDesc});
  var jsonTravelStatus = eval(${jsonTravelStatus});
  var overNightData = eval(${overnightTrip});
  var bfnOptionsTravel = eval(${bfnOptionsTrvl});
  var hasFirstApprover = eval(${hasFirstApprover});
  var commuteDist = eval(${commuteDistance});
  var areUDeleteRow = '${sessionScope.languageJSON.validator.areUDeleteRow}'
  var pressContinue = '${sessionScope.languageJSON.validator.pressContinue}'
</script>
<script
	src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/travelRequest/mileageScreen.js"></script>
</html>