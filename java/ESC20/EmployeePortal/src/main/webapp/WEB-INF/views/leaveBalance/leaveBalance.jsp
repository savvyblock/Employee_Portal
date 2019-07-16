<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html> 
<html lang="en">
<head>
<title>${sessionScope.languageJSON.headTitle.leaveBalance}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="../commons/header.jsp"%>
</head>
<body class="hold-transition sidebar-mini">
	<div class="wrapper">
		<%@ include file="../commons/bar.jsp"%>
		<main class="content-wrapper" tabindex="-1">
		<section class="content">
			<h1 class="clearfix no-print section-title">${sessionScope.languageJSON.title.leaveBalances}</h1>
			<div class="content-white">
					<form
					class="no-print searchForm"
					action="leaveBalanceByFreqency"
					id="changeFreqForm"
					method="POST"
								>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<div class="form-group in-line">
								<label class="form-title"  for="freq">${sessionScope.languageJSON.label.pleaseSelectFre}</label>
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
						<input type="text" name="freq" hidden="hidden" value="${selectedFreq}" aria-label="${sessionScope.languageJSON.accessHint.id}"/>
					<div class="form-group type-group">
						<label class="form-title" for="SearchType"><span>${sessionScope.languageJSON.leaveBalance.leaveType}</span>:</label> 
						<select id="SearchType"
							class="form-control" name="SearchType" autocomplete="off">
							<c:forEach var="type" items="${leaveTypes}" varStatus="count">
									<option value="${type.code}" <c:if test="${type.code == SearchType }">selected</c:if>>${type.description}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
							<label class="form-title" for="SearchStartInput"><span>${sessionScope.languageJSON.leaveBalance.fromDateLeave}</span>:</label> 
							<div class="button-group">
								<div class="fDateGroup date" id="SearchStartDate" data-date-format="mm/dd/yyyy">
											<button class="prefix" type="button" aria-label="${sessionScope.languageJSON.label.showDatepicker}"><i class="fa fa-calendar"></i></button>
											<input class="form-control dateInput" name="SearchStart" autocomplete="off" type="text"
											placeholder="${sessionScope.languageJSON.label.mmddyyyyFormat}" id="SearchStartInput" value="${SearchStart}">
											<button class="clear-btn" type="button" role="button" onclick="clearDate(this)" aria-label="${sessionScope.languageJSON.label.removeContent}">
												<i class="fa fa-times"></i>
											</button>
								</div>
							<!-- <input
								class="form-control dateInput" type="text" name="SearchStart"
								data-date-format="mm/dd/yyyy"  autocomplete="off"
								aria-label="${sessionScope.languageJSON.label.mmddyyyyFormat}"
								placeholder="${sessionScope.languageJSON.label.mmddyyyyFormat}"
								title="${sessionScope.languageJSON.label.mmddyyyyFormat}"
								id="SearchStartDate" value="${SearchStart}" />
								<button class="clear-btn" type="button" role="button" onclick="clearDate(this)" aria-label="${sessionScope.languageJSON.label.removeContent}">
									<i class="fa fa-times"></i>
								</button> -->
							</div>
						</div>
						<div class="form-group">
							<label class="form-title" for="SearchEndInput"> <span>${sessionScope.languageJSON.leaveBalance.toDateLeave}</span>: </label> 
							<div class="button-group">
									<div class="fDateGroup date" id="SearchEndDate" data-date-format="mm/dd/yyyy">
										<button class="prefix" type="button" aria-label="${sessionScope.languageJSON.label.showDatepicker}"><i class="fa fa-calendar"></i></button>
										<input class="form-control dateInput" type="text" name="SearchEnd"
										data-date-format="mm/dd/yyyy"  autocomplete="off"
										placeholder="${sessionScope.languageJSON.label.mmddyyyyFormat}"
										title="${sessionScope.languageJSON.label.mmddyyyyFormat}"
										id="SearchEndInput" value="${SearchEnd}"/>
										<button class="clear-btn" type="button" role="button" onclick="clearDate(this)" aria-label="${sessionScope.languageJSON.label.removeContent}" tabindex="0">
												<i class="fa fa-times"></i>
											</button>
							</div>
							<!-- <input
								class="form-control dateInput" type="text" name="SearchEnd"
								data-date-format="mm/dd/yyyy"  autocomplete="off"
								aria-label="${sessionScope.languageJSON.label.mmddyyyyFormat}"
								placeholder="${sessionScope.languageJSON.label.mmddyyyyFormat}"
								title="${sessionScope.languageJSON.label.mmddyyyyFormat}"
								id="SearchEndDate" value="${SearchEnd}"/>
								<button class="clear-btn" type="button" role="button" onclick="clearDate(this)" aria-label="${sessionScope.languageJSON.label.removeContent}" tabindex="0">
									<i class="fa fa-times"></i>
								</button> -->
							</div>
						</div>
						<div class="form-group btn-group">
							<div style="margin-top:20px;">
									<button id="retrieve" type="button" role="button" class="btn btn-primary">
										${sessionScope.languageJSON.leaveBalance.retrieve}
									</button>
							</div>
						</div>
					</form>
					<div class="form-group">
						<p class="error-hint hide" role="alert" aria-atomic="true" id="timeErrorMessage">${sessionScope.languageJSON.validator.fromDateNotGreaterToDate}</p>
					</div>
					
				<c:if test="${fn:length(leaves) > 0}">
					<table class="table request-list responsive-table">
						<thead>
							<tr>
								<th>${sessionScope.languageJSON.leaveBalance.leaveType}</th>
								<th>${sessionScope.languageJSON.leaveBalance.dateOfPay}</th>
								<th>${sessionScope.languageJSON.leaveBalance.dateOfLeave}</th>
								<th>${sessionScope.languageJSON.leaveBalance.leaveUsed}</th>
								<th class="text-right">${sessionScope.languageJSON.leaveBalance.leaveEarned}</th>
								<th class="text-center">${sessionScope.languageJSON.leaveBalance.status}</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="leave" items="${leaves}" varStatus="count">
								<tr>
									<td data-title="${sessionScope.languageJSON.leaveBalance.leaveType}">
										<c:forEach var="type" items="${leaveTypes}" varStatus="count">
												<c:if test="${type.code == leave.lvTyp }">${type.description}</c:if>
										</c:forEach>
									</td>
									<td data-title="${sessionScope.languageJSON.leaveBalance.dateOfPay}">
											${leave.dtOfPay}
									</td>
									<td data-title="${sessionScope.languageJSON.leaveBalance.dateOfLeave}">${leave.dtOfAbs}</td>
									<td data-title="${sessionScope.languageJSON.leaveBalance.leaveUsed}">${leave.lvUnitsUsed} 
										<span>${sessionScope.languageJSON.label.days}</span>
									</td>
									<td data-title="${sessionScope.languageJSON.leaveBalance.leaveEarned}" class="text-right">${leave.lvUnitsEarned}</td>
									<td data-title="${sessionScope.languageJSON.leaveBalance.status}" class="text-center">
											<c:if test="${leave.processDt && leave.processDt != ''}">
												<span>${sessionScope.languageJSON.label.processed}</span>
											</c:if>
											<c:if test="${!leave.processDt || leave.processDt == ''}">
												<span>${sessionScope.languageJSON.label.notProcessed}</span>
											</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>

					</table>
				</c:if>
				<c:if test="${fn:length(leaves) == 0}">
					<div>
						${sessionScope.languageJSON.label.noData}
					</div>
				</c:if>
			</div>
		</section>
			<form id="deleteForm" action="deleteLeaveRequest" method="post">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<input type="hidden" id="deleteId" name="id" aria-label="${sessionScope.languageJSON.accessHint.id}"/>
			</form>
		</main>
	</div>
	<%@ include file="../commons/footer.jsp"%>
</body>

<script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/leaveBalance/leaveBalance.js"></script>

</html>
