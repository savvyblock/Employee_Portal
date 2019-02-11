<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ea" uri="http://www.esc20.net/tags/employeeaccess-util"%>

<table>
	<tr>
		<td class="label" style="padding-right:5px;">Coverage Type:</td>
		<td style="padding-right:5px;">
			<span id="covrgTyp">${EA1095InfoCommand.covrgTyp}<c:if test='${EA1095InfoCommand.covrgDescr != null && EA1095InfoCommand.covrgDescr != ""}'> - ${EA1095InfoCommand.covrgDescr}</c:if></span>
		</td>
	</tr>
</table>
<table style="width:100%" class="form_layout">
	<tr><b style="" >Covered Individuals</b></tr>
</table>
<table id="ea1095BTbl" style="width:99.9%;overflow:auto" class="tabular">
	<thead>
		<tr>
			<th class="left_align">First Name</th>
			<th class="left_align">Middle Name</th>
			<th class="left_align">Last Name</th>
			<th>Generation</th>
			<th>SSN</th>
			<th>DOB</th>
			<th>All</a></th>
			<th>Jan</th>
			<th>Feb</th>
			<th>Mar</th>
			<th>Apr</th>
			<th>May</th>
			<th>Jun</th>
			<th>Jul</th>
			<th>Aug</th>
			<th>Sep</th>
			<th>Oct</th>
			<th>Nov</th>
			<th>Dec</th>
		</tr>
	</thead>
	<tbody >
		<c:if test="${fn:length(EA1095InfoCommand.ea1095BData.pageItems) == 0}">
			<tr>
				<td colspan="20" style="text-align: center;">No Rows</td>
			</tr>
		</c:if>
		<c:forEach var="row" items="${EA1095InfoCommand.ea1095BData.pageItems}" varStatus="step">

			<tr class="actionTableRow" row-selector="tbl_tr_${step.index}_selected">

				<td id="ea1095B_tbl_td_${step.index}_0" align="left" nowrap="nowrap">
					<spring:bind path="ea1095BData.pageItems[${step.index}].fName">
						<span id="fName_${step.index}">${status.value}</span>
					</spring:bind>
				</td>
				<td id="ea1095B_tbl_td_${step.index}_1" align="left" nowrap="nowrap">
					<spring:bind path="ea1095BData.pageItems[${step.index}].mName">
						<span id="mName_${step.index}">${status.value}</span>
					</spring:bind>
				</td>
				<td id="ea1095B_tbl_td_${step.index}_2" align="left" nowrap="nowrap">
					<spring:bind path="ea1095BData.pageItems[${step.index}].lName">
						<span id="lName_${step.index}">${status.value}</span>
					</spring:bind>
				</td>
				<td id="ea1095B_tbl_td_${step.index}_3" align="left" nowrap="nowrap">
					<spring:bind path="ea1095BData.pageItems[${step.index}].nameGen">
						<span id="nameGen_${step.index}">${status.value}</span>
					</spring:bind>
				</td>
				<td id="ea1095B_tbl_td_${step.index}_4" style="width:50px;" align="center" nowrap="nowrap">
					<spring:bind path="ea1095BData.pageItems[${step.index}].ssn">
						<span id="ssn_${step.index}">${status.value}</span>
					</spring:bind>
				</td>
				<td id="ea1095B_tbl_td_${step.index}_5" style="width:50px;" align="center" nowrap="nowrap">
					<spring:bind path="ea1095BData.pageItems[${step.index}].dob">
						<span id="dob_${step.index}">${status.value}</span>
					</spring:bind>
				</td>
				<td id="ea1095B_tbl_td_${step.index}_6" align="center" nowrap="nowrap">
					<form:checkbox path="ea1095BData.pageItems[${step.index}].allMon" id="allMon_${step.index}" disabled="true" cssClass="center_align"/>
				</td>
				<td id="ea1095B_tbl_td_${step.index}_7" align="center" nowrap="nowrap">
					<form:checkbox path="ea1095BData.pageItems[${step.index}].jan" id="jan_${step.index}" disabled="true"  />
				</td>
				<td id="ea1095B_tbl_td_${step.index}_8" align="center" nowrap="nowrap">
					<form:checkbox path="ea1095BData.pageItems[${step.index}].feb" id="feb_${step.index}" disabled="true" />
				</td>
				<td id="ea1095B_tbl_td_${step.index}_9" align="center" nowrap="nowrap">
					<form:checkbox path="ea1095BData.pageItems[${step.index}].mar" id="mar_${step.index}" disabled="true" />
				</td>
				<td id="ea1095B_tbl_td_${step.index}_10" align="center" nowrap="nowrap">
					<form:checkbox path="ea1095BData.pageItems[${step.index}].apr" id="apr_${step.index}" disabled="true" />
				</td>
				<td id="ea1095B_tbl_td_${step.index}_11" align="center" nowrap="nowrap">
					<form:checkbox path="ea1095BData.pageItems[${step.index}].may" id="may_${step.index}" disabled="true" />
				</td>
				<td id="ea1095B_tbl_td_${step.index}_12" align="center" nowrap="nowrap">
					<form:checkbox path="ea1095BData.pageItems[${step.index}].jun" id="jun_${step.index}" disabled="true" />
				</td>
				<td id="ea1095B_tbl_td_${step.index}_13" align="center" nowrap="nowrap">
					<form:checkbox path="ea1095BData.pageItems[${step.index}].jul" id="jul_${step.index}" disabled="true" />
				</td>
				<td id="ea1095B_tbl_td_${step.index}_14" align="center" nowrap="nowrap">
					<form:checkbox path="ea1095BData.pageItems[${step.index}].aug" id="aug_${step.index}" disabled="true" />
				</td>
				<td id="ea1095B_tbl_td_${step.index}_15" align="center" nowrap="nowrap">
					<form:checkbox path="ea1095BData.pageItems[${step.index}].sep" id="sep_${step.index}" disabled="true" />
				</td>
				<td id="ea1095B_tbl_td_${step.index}_16" align="center" nowrap="nowrap">
					<form:checkbox path="ea1095BData.pageItems[${step.index}].oct" id="oct_${step.index}" disabled="true" />
				</td>
				<td id="ea1095B_tbl_td_${step.index}_17" align="center" nowrap="nowrap">
					<form:checkbox path="ea1095BData.pageItems[${step.index}].nov" id="nov_${step.index}" disabled="true" />
				</td>
				<td id="ea1095B_tbl_td_${step.index}_18" align="center" nowrap="nowrap">
					<form:checkbox path="ea1095BData.pageItems[${step.index}].dec" id="dec_${step.index}" disabled="true" />
				</td>
			</tr>
		</c:forEach>
	</tbody>
	<tfoot>
		<tr>
			<td colspan = "19" class="tabular_footer" >
				<!-- Pagination -->
				<c:if test="${ EA1095InfoCommand.currentPage1095B != null && EA1095InfoCommand.currentPage1095B != ''}">

					<div class="hidden">
						Pagination Fields
						<form:hidden path="currentPage1095B" id="CurrentPage" />
						<form:hidden path="totalPages1095B" id="TotalPages"/>

						Pagination Buttons
						<input id="SelectedPageButton" class="hidden" type="submit" name="mySubmitSelectedPageButton" value="Submit Query" />
						<input id="PaginationButton" class="hidden" type="submit" name="mySubmitPaginationButton" value="Submit Query" />
					</div>

					<div class="pagination">
						<c:choose>
							<c:when test="${EA1095InfoCommand.currentPage1095B != 1}">
								<a href="#" id="FirstPage" class="link_button unsavedDataCheck" >
									<img class="hover_button" alt="First page" src="<spring:theme code="commonBase" />images/table_first.gif" hoverSrc="<spring:theme code="commonBase" />images/table_first_h.gif" />
								</a>
							</c:when>
							<c:otherwise>
								<img alt="First page (disabled)" src="<spring:theme code="commonBase" />images/table_first_d.gif" />
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="${EA1095InfoCommand.currentPage1095B != 1}">
								<a href="#" id="PreviousPage" class="link_button unsavedDataCheck" >
									<img class="hover_button" alt="Previous page" src="<spring:theme code="commonBase" />images/table_prev.gif" hoverSrc="<spring:theme code="commonBase" />images/table_prev_h.gif" />
								</a>
							</c:when>
							<c:otherwise>
								<img alt="Previous page (disabled)" src="<spring:theme code="commonBase" />images/table_prev_d.gif" />
							</c:otherwise>
						</c:choose>

						<div>
							<form:select path="selectedPage1095B" id="SelectedPage" cssClass="unsavedDataCheckOnChange ignore_changes last_field" tabindex="1">
								<c:forEach begin="1" end="${EA1095InfoCommand.totalPages1095B}" var="current">
									<form:option value="${current}" />
								</c:forEach>
							</form:select>
							 / ${EA1095InfoCommand.totalPages1095B}
						</div>

						<c:choose>
							<c:when test="${EA1095InfoCommand.currentPage1095B < EA1095InfoCommand.totalPages1095B}">
								<a href="#" id="NextPage" class="link_button unsavedDataCheck">
									<img class="hover_button" alt="Next page" src="<spring:theme code="commonBase" />images/table_next.gif" hoverSrc="<spring:theme code="commonBase" />images/table_next_h.gif" />
								</a>
							</c:when>
							<c:otherwise>
								<img alt="Next page (disabled)" src="<spring:theme code="commonBase" />images/table_next_d.gif" />
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="${EA1095InfoCommand.currentPage1095B < EA1095InfoCommand.totalPages1095B}">
								<a href="#" id="LastPage" class="link_button unsavedDataCheck" >
									<img class="hover_button" alt="Last page" src="<spring:theme code="commonBase" />images/table_last.gif" hoverSrc="<spring:theme code="commonBase" />images/table_last_h.gif" />
								</a>
							</c:when>
							<c:otherwise>
								<img alt="Last page (disabled)" src="<spring:theme code="commonBase" />images/table_last_d.gif" />
							</c:otherwise>
						</c:choose>
					</div>
				</c:if>
			</td> 
		</tr>
	</tfoot>
</table>