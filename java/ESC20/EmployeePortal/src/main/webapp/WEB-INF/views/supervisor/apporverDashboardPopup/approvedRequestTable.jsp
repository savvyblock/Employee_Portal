<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

	<table id="table" class="table request-list responsive-table-1320" style="width:100%">

			<thead>
				<%-- Grid Table Header --%>
				<tr> 
				
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
		
			<tbody>
	
					<c:if
						test="${ApproveTravelRequestCommand.finalApproverTravelRequests.size() eq 0}">
						<tr>
							<td class="noRowsRow" colspan="5"
								style="text-align: center;">No Rows</td>
								
						</tr>
					</c:if>
					
					<c:forEach var="dataRow" items="${ApproveTravelRequestCommand.finalApproverTravelRequests}" varStatus="row">	

						<tr class="selectable_row actionTableRow"
							onclick="processRowClick(this, ${row.index})"
							row-selector="tbl_tr_${row.index}_selected">

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
					</tbody>

	</table>

