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
		<main class="content-wrapper">
		<section class="content">
			<h2 class="clearfix no-print section-title" data-localize="title.leaveBalances"></h2>
			<div class="content-white">
					<form
					class="no-print searchForm"
					action="leaveBalanceByFreqency"
					id="changeFreqForm"
					method="POST"
								>
								<div class="form-group in-line">
								<label class="form-title"  for="freq"  data-localize="label.payrollFreq"></label>
				<select class="form-control" name="freq" id="freq" onchange="changeFreq()">
								<c:forEach var="freq" items="${availableFreqs}" varStatus="count">
									<option value="${freq.code}" <c:if test="${freq.code == selectedFreq }">selected</c:if>>${freq.description}</option>
								</c:forEach>
									</select>
								</div>
			</form>
	  <%@ include file="../commons/leaveBalanceTable.jsp"%>
				<form class="no-print searchForm" id="SearchForm" action="leaveBalance" method="post">
						<input type="text" name="freq" hidden="hidden" value="${selectedFreq}">
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
								class="form-control" type="text" name="SearchStart"
								id="SearchStartDate" readonly value="${SearchStart}" />
								<a class="clear-btn" onclick="clearDate(this)" data-localize="label.removeContent" data-localize-location="title"><i class="fa fa-times"></i></a>
							</div>
						</div>
						<div class="form-group">
							<label class="form-title" for="SearchEndDate"> <span data-localize="label.to"></span>: </label> 
							<div class="button-group">
							<input
								class="form-control" type="text" name="SearchEnd"
								id="SearchEndDate" readonly value="${SearchEnd}"/>
								<a class="clear-btn" onclick="clearDate(this)" data-localize="label.removeContent" data-localize-location="title"><i class="fa fa-times"></i></a>
							</div>
						</div>
						<div class="form-group btn-group">
							<div style="margin-top:20px;">
									<button type="submit" class="btn btn-primary" data-localize="leaveBalance.retrieve">
										</button>
							</div>
						</div>
					</form>

				<c:if test="${fn:length(leaves) > 0}">
					<table class="table request-list responsive-table">
						<thead>
							<tr>
								<th data-localize="leaveBalance.leaveType"></th>
								<th data-localize="leaveBalance.dateOfPay"></th>
								<th data-localize="leaveBalance.dateOfLeave"></th>
								<th data-localize="leaveBalance.leaveUsed"></th>
								<th data-localize="leaveBalance.leaveEarned"></th>
								<th data-localize="leaveBalance.status"></th>
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
											${leave.id.dtOfPay}
									</td>
									<td data-localize="leaveBalance.dateOfLeave" data-localize-location="scope">${leave.dtOfAbs}</td>
									<td data-localize="leaveBalance.leaveUsed" data-localize-location="scope">${leave.lvUnitsUsed} 
										<span data-localize="label.days"></span>
									</td>
									<td data-localize="leaveBalance.leaveEarned" data-localize-location="scope">${leave.lvUnitsEarned}</td>
									<td data-localize="leaveBalance.status" data-localize-location="scope">
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
				<input type="text" id="deleteId" name="id"/>
			</form>
		</main>
	</div>
	<%@ include file="../commons/footer.jsp"%>
	<%@ include file="../modal/eventModal.jsp"%>
</body>

<script>
	$(document).ready(
			function() {
				console.log(initialLocaleCode)
				$('#SearchStartDate').fdatepicker({
					format:'mm/dd/yyyy',
					language:initialLocaleCode
				})
				$('#SearchEndDate').fdatepicker({
					format:'mm/dd/yyyy',
					language:initialLocaleCode
				})
			});

	function changeMMYYDDFormat(date){
		let string = date.split("/")
		console.log(string[2]+string[0]+string[1])
		return string[2]+string[0]+string[1]
	}
</script>
</html>
