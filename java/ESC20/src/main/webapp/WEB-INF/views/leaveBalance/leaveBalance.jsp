<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="en">
<head>
<title data-localize="headTitle.leaveBalance"></title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="../commons/header.jsp"%>
</head>
<body class="hold-transition sidebar-mini">
	<div class="wrapper">
		<%@ include file="../commons/bar.jsp"%>
		<main class="content-wrapper" tabindex="-1">
		<section class="content">
			<h1 class="clearfix no-print section-title" data-localize="title.leaveBalances"></h1>
			<div class="content-white">
					<form
					class="no-print searchForm"
					action="leaveBalanceByFreqency"
					id="changeFreqForm"
					method="POST"
								>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<div class="form-group in-line">
								<label class="form-title"  for="freq"  data-localize="label.pleaseSelectFre"></label>
				<select class="form-control" name="freq" id="freq" onchange="changeFreq()">
								<c:forEach var="freq" items="${availableFreqs}" varStatus="count">
									<option value="${freq.code}" <c:if test="${freq.code == selectedFreq }">selected</c:if>>${freq.description}</option>
								</c:forEach>
									</select>
								</div>
			</form>
	  <%@ include file="../commons/leaveBalanceTable.jsp"%>
				<form class="no-print searchForm" id="SearchForm" action="leaveBalance" method="post">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<input type="text" name="freq" hidden="hidden" value="${selectedFreq}" aria-label="" data-localize="accessHint.id">
					<div class="form-group type-group">
						<label class="form-title" for="SearchType"><span data-localize="leaveBalance.type"></span>:</label> 
						<select id="SearchType"
							class="form-control" name="SearchType" autocomplete="off">
							<c:forEach var="type" items="${leaveTypes}" varStatus="count">
									<option value="${type.code}" <c:if test="${type.code == SearchType }">selected</c:if>>${type.description}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
							<label class="form-title" for="SearchStartDate"><span data-localize="label.from"></span>:</label> 
							<div class="button-group">
							<input
								class="form-control dateInput" type="text" name="SearchStart"
								data-date-format="mm/dd/yyyy"  autocomplete="off"
								aria-label=""
								data-localize="label.mmddyyyyFormat"
								placeholder=""
								title=""
								id="SearchStartDate" value="${SearchStart}" />
								<button class="clear-btn" type="button" role="button" onclick="clearDate(this)" aria-label="" data-localize="label.removeContent" data-localize-location="aria-label" data-localize-notText="true">
									<i class="fa fa-times"></i>
								</button>
							</div>
						</div>
						<div class="form-group">
							<label class="form-title" for="SearchEndDate"> <span data-localize="label.to"></span>: </label> 
							<div class="button-group">
							<input
								class="form-control dateInput" type="text" name="SearchEnd"
								data-date-format="mm/dd/yyyy"  autocomplete="off"
								aria-label=""
								data-localize="label.mmddyyyyFormat"
								placeholder=""
								title=""
								id="SearchEndDate" value="${SearchEnd}"/>
								<button class="clear-btn" type="button" role="button" onclick="clearDate(this)" aria-label="" data-localize="label.removeContent" data-localize-location="aria-label" data-localize-notText="true" tabindex="0">
									<i class="fa fa-times"></i>
								</button>
							</div>
						</div>
						<div class="form-group btn-group">
							<div style="margin-top:20px;">
									<button id="retrieve" type="button" role="button" class="btn btn-primary" data-localize="leaveBalance.retrieve">
										</button>
							</div>
						</div>
					</form>
					<div class="form-group">
						<p class="error-hint hide" role="alert" aria-atomic="true" id="timeErrorMessage" data-localize="validator.fromDateNotGreaterToDate"></p>
					</div>
					
				<c:if test="${fn:length(leaves) > 0}">
					<table class="table request-list responsive-table">
						<thead>
							<tr>
								<th data-localize="leaveBalance.leaveType"></th>
								<th data-localize="leaveBalance.dateOfPay"></th>
								<th data-localize="leaveBalance.dateOfLeave"></th>
								<th data-localize="leaveBalance.leaveUsed"></th>
								<th class="text-right" data-localize="leaveBalance.leaveEarned"></th>
								<th class="text-center" data-localize="leaveBalance.status"></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="leave" items="${leaves}" varStatus="count">
								<tr>
									<td data-localize="leaveBalance.leaveType" data-localize-location="scope">
										<c:forEach var="type" items="${leaveTypes}" varStatus="count">
												<c:if test="${type.code == leave.lvTyp }">${type.description}</c:if>
										</c:forEach>
									</td>
									<td data-localize="leaveBalance.dateOfPay" data-localize-location="scope">
											${leave.dtOfPay}
									</td>
									<td data-localize="leaveBalance.dateOfLeave" data-localize-location="scope">${leave.dtOfAbs}</td>
									<td data-localize="leaveBalance.leaveUsed" data-localize-location="scope">${leave.lvUnitsUsed} 
										<span data-localize="label.days"></span>
									</td>
									<td class="text-right" data-localize="leaveBalance.leaveEarned" data-localize-location="scope">${leave.lvUnitsEarned}</td>
									<td class="text-center" data-localize="leaveBalance.status" data-localize-location="scope">
											<c:if test="${leave.processDt && leave.processDt != ''}">
												<span data-localize="label.processed"></span>
											</c:if>
											<c:if test="${!leave.processDt || leave.processDt == ''}">
												<span data-localize="label.notProcessed"></span>
											</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>

					</table>
				</c:if>
				<c:if test="${fn:length(leaves) == 0}">
					<div data-localize="label.noData">
					<div>
				</c:if>
			</div>
		</section>
			<form hidden="true" id="deleteForm" action="deleteLeaveRequest" method="post">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<input type="text" id="deleteId" name="id" aria-label="" data-localize="accessHint.id"/>
			</form>
		</main>
	</div>
	<%@ include file="../commons/footer.jsp"%>
</body>

<script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/leaveBalance/leaveBalance.js"></script>

</html>
