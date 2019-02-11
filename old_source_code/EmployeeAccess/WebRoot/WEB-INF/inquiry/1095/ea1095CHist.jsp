<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ea" uri="http://www.esc20.net/tags/employeeaccess-util"%>

<table id="ea1095CParentTbl" style="width:900px" class="tabular marginless">
	<thead>
		<tr class="columnheader">
			<th style="width:100px;height:5px;"></th>
			<th>All</th>
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
	<tbody>
		<tr>
			<td style="width:100px;height:5px;padding-right:5px;padding-top:3px;padding-bottom:3px;">Offer of Coverage</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr1_td1" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonAll.offrOfCovrg">
					<span id="offrOfCovrgAll">${status.value}</span>
					</spring:bind>
			</td>
			<td style="width:12px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr1_td2" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonJan.offrOfCovrg">
					<span id="offrOfCovrgJan">${status.value}</span>
					</spring:bind>
			</td>
			<td style="width:12px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr1_td3" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonFeb.offrOfCovrg">
					<span id="offrOfCovrgFeb">${status.value}</span>
				</spring:bind>
			</td>
			<td style="width:12px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr1_td4" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonMar.offrOfCovrg">
					<span id="offrOfCovrgMar">${status.value}</span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr1_td5" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonApr.offrOfCovrg">
					<span id="offrOfCovrgApr">${status.value}</span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr1_td6" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonMay.offrOfCovrg">
					<span id="offrOfCovrgMay">${status.value}</span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr1_td7" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonJun.offrOfCovrg">
					<span id="offrOfCovrgJun">${status.value}</span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr1_td8" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonJul.offrOfCovrg">
					<span id="offrOfCovrgJul">${status.value}</span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr1_td9" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonAug.offrOfCovrg">
					<span id="offrOfCovrgAug">${status.value}</span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr1_td10" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonSep.offrOfCovrg">
					<span id="offrOfCovrgSep">${status.value}</span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr1_td11" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonOct.offrOfCovrg">
					<span id="offrOfCovrgOct">${status.value}</span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr1_td12" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonNov.offrOfCovrg">
					<span id="offrOfCovrgNov">${status.value}</span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr1_td13" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonDec.offrOfCovrg">
					<span id="offrOfCovrgDec">${status.value}</span>
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td  class="label" style="width:80px;height:5px;padding-right:5px;padding-top:3px;padding-bottom:3px;">Employee Share</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr2_td1" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonAll.empShr">
					<span ><fmt:formatNumber type="number" value="${status.value}" pattern="##,##0.00" /></span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr2_td2" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonJan.empShr">
					<span ><fmt:formatNumber type="number" value="${status.value}" pattern="##,##0.00" /></span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr2_td3" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonFeb.empShr">
					<span ><fmt:formatNumber type="number" value="${status.value}" pattern="##,##0.00" /></span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr2_td4" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonMar.empShr">
					<span ><fmt:formatNumber type="number" value="${status.value}" pattern="##,##0.00" /></span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr2_td5" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonApr.empShr">
					<span ><fmt:formatNumber type="number" value="${status.value}" pattern="##,##0.00" /></span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr2_td6" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonMay.empShr">
					<span ><fmt:formatNumber type="number" value="${status.value}" pattern="##,##0.00" /></span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr2_td7" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonJun.empShr">
					<span ><fmt:formatNumber type="number" value="${status.value}" pattern="##,##0.00" /></span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr2_td8" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonJul.empShr">
					<span ><fmt:formatNumber type="number" value="${status.value}" pattern="##,##0.00" /></span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr2_td9" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonAug.empShr">
					<span ><fmt:formatNumber type="number" value="${status.value}" pattern="##,##0.00" /></span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr2_td10" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonSep.empShr">
					<span ><fmt:formatNumber type="number" value="${status.value}" pattern="##,##0.00" /></span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr2_td11" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonOct.empShr">
					<span ><fmt:formatNumber type="number" value="${status.value}" pattern="##,##0.00" /></span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr2_td12" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonNov.empShr">
					<span ><fmt:formatNumber type="number" value="${status.value}" pattern="##,##0.00" /></span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr2_td13" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonDec.empShr">
					<span ><fmt:formatNumber type="number" value="${status.value}" pattern="##,##0.00" /></span>
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td class="label" style="width:80px;height:5px;padding-right:5px;padding-top:3px;padding-bottom:3px;">Safe Harbor</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr3_td1" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonAll.safeHrbr">
					<span >${status.value}</span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr3_td2" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonJan.safeHrbr">
					<span >${status.value}</span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr3_td3" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonFeb.safeHrbr">
					<span >${status.value}</span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr3_td4" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonMar.safeHrbr">
					<span >${status.value}</span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr3_td5" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonApr.safeHrbr">
					<span >${status.value}</span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr3_td6" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonMay.safeHrbr">
					<span >${status.value}</span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr3_td7" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonJun.safeHrbr">
					<span >${status.value}</span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr3_td8" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonJul.safeHrbr">
					<span >${status.value}</span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr3_td9" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonAug.safeHrbr">
					<span >${status.value}</span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr3_td10" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonSep.safeHrbr">
					<span >${status.value}</span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr3_td11" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonOct.safeHrbr">
					<span >${status.value}</span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr3_td12" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonNov.safeHrbr">
					<span >${status.value}</span>
				</spring:bind>
			</td>
			<td style="width:8px;padding-left:0px;padding-right:0px;" id="ea1095C_tbl_tr3_td13" align="center" nowrap="nowrap">
				<spring:bind path="ea1095CCalMonDec.safeHrbr">
					<span >${status.value}</span>
				</spring:bind>
			</td>
		</tr>
	</tbody>
</table>
<table style="width:100%" class="form_layout">
	<tr>
		<td><b>Covered Individuals</b></td>
	</tr>
	<tr>
		<td class="label" style="padding-right:5px;">If Employer provided self-insured coverage, check the box and enter the information for each covered individual.</td>
		<td class="label" style="padding-right:5px;">Self-Insured:</td>
		<td style="padding-right:5px;">
			<form:checkbox path="selfIns" id="selfIns" disabled="true" />
		</td>
		<td class="label" style="padding-right:5px;">Plan Start Month:</td>
		<td style="padding-right:5px;">
			<spring:bind path="planStrtMon">
				<span >${status.value}</span>
			</spring:bind>
		</td>
	</tr>
</table>
<table id="ea1095CTbl" style="width:99.9%;overflow:auto" class="tabular">
	<thead>
		<tr class="columnheader">
			<th class="left_align">First Name</th>
			<th class="left_align">Middle Name</th>
			<th class="left_align">Last Name</th>
			<th>Generation</th>
			<th>SSN</th>
			<th>DOB</th>
			<th>All</th>
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
		<c:if test="${fn:length(EA1095InfoCommand.ea1095CCovrgData.pageItems) == 0}">
			<tr>
				<td colspan="20" style="text-align: center;">No Rows</td>
			</tr>
		</c:if>
		<c:forEach var="row" items="${EA1095InfoCommand.ea1095CCovrgData.pageItems}" varStatus="step">

			<c:if test="${step.current.selected}">
				<c:set var="selectedRowVal" value="${row.index}" scope="request" />
			</c:if>

			<tr class="actionTableRow" row-selector="tbl_tr_${step.index}_selected">

				<td id="ea1095C_tbl_td_${step.index}_0" align="left" nowrap="nowrap">
					<spring:bind path="ea1095CCovrgData.pageItems[${step.index}].fName">
						<span >${status.value}</span>
					</spring:bind>
				</td>
				<td id="ea1095C_tbl_td_${step.index}_1" align="left" nowrap="nowrap">
					<spring:bind path="ea1095CCovrgData.pageItems[${step.index}].mName">
						<span >${status.value}</span>
					</spring:bind>
				</td>
				<td id="ea1095C_tbl_td_${step.index}_2" align="left" nowrap="nowrap">
					<spring:bind path="ea1095CCovrgData.pageItems[${step.index}].lName">
						<span >${status.value}</span>
					</spring:bind>
				</td>
				<td id="ea1095C_tbl_td_${step.index}_3" align="left" nowrap="nowrap">
					<spring:bind path="ea1095CCovrgData.pageItems[${step.index}].nameGen">
						<span >${status.value}</span>
					</spring:bind>
				</td>
				<td id="ea1095C_tbl_td_${step.index}_4" style="width:50px;" align="center" nowrap="nowrap">
					<spring:bind path="ea1095CCovrgData.pageItems[${step.index}].ssn">
						<span >${status.value}</span>
					</spring:bind>
				</td>
				<td id="ea1095C_tbl_td_${step.index}_5" style="width:50px;" align="center" nowrap="nowrap">
					<spring:bind path="ea1095CCovrgData.pageItems[${step.index}].dob">
						<span >${status.value}</span>
					</spring:bind>
				</td>
				<td id="ea1095C_tbl_td_${step.index}_6" align="center" nowrap="nowrap">
					<form:checkbox path="ea1095CCovrgData.pageItems[${step.index}].allMon" id="allMon_${step.index}" disabled="true" />
				</td>
				<td id="ea1095C_tbl_td_${step.index}_7" align="center" nowrap="nowrap">
					<form:checkbox path="ea1095CCovrgData.pageItems[${step.index}].jan" id="jan_${step.index}" disabled="true" />
				</td>
				<td id="ea1095C_tbl_td_${step.index}_8" align="center" nowrap="nowrap">
					<form:checkbox path="ea1095CCovrgData.pageItems[${step.index}].feb" id="feb_${step.index}" disabled="true" />
				</td>
				<td id="ea1095C_tbl_td_${step.index}_9" align="center" nowrap="nowrap">
					<form:checkbox path="ea1095CCovrgData.pageItems[${step.index}].mar" id="mar_${step.index}" disabled="true" />
				</td>
				<td id="ea1095C_tbl_td_${step.index}_10" align="center" nowrap="nowrap">
					<form:checkbox path="ea1095CCovrgData.pageItems[${step.index}].apr" id="apr_${step.index}" disabled="true" />
				</td>
				<td id="ea1095C_tbl_td_${step.index}_11" align="center" nowrap="nowrap">
					<form:checkbox path="ea1095CCovrgData.pageItems[${step.index}].may" id="may_${step.index}" disabled="true" />
				</td>
				<td id="ea1095C_tbl_td_${step.index}_12" align="center" nowrap="nowrap">
					<form:checkbox path="ea1095CCovrgData.pageItems[${step.index}].jun" id="jun_${step.index}" disabled="true" />
				</td>
				<td id="ea1095C_tbl_td_${step.index}_13" align="center" nowrap="nowrap">
					<form:checkbox path="ea1095CCovrgData.pageItems[${step.index}].jul" id="jul_${step.index}" disabled="true" />
				</td>
				<td id="ea1095C_tbl_td_${step.index}_14" align="center" nowrap="nowrap">
					<form:checkbox path="ea1095CCovrgData.pageItems[${step.index}].aug" id="aug_${step.index}" disabled="true" />
				</td>
				<td id="ea1095C_tbl_td_${step.index}_15" align="center" nowrap="nowrap">
					<form:checkbox path="ea1095CCovrgData.pageItems[${step.index}].sep" id="sep_${step.index}" disabled="true" />
				</td>
				<td id="ea1095C_tbl_td_${step.index}_16" align="center" nowrap="nowrap">
					<form:checkbox path="ea1095CCovrgData.pageItems[${step.index}].oct" id="oct_${step.index}" disabled="true" />
				</td>
				<td id="ea1095C_tbl_td_${step.index}_17" align="center" nowrap="nowrap">
					<form:checkbox path="ea1095CCovrgData.pageItems[${step.index}].nov" id="nov_${step.index}" disabled="true" />
				</td>
				<td id="ea1095C_tbl_td_${step.index}_18" align="center" nowrap="nowrap">
					<form:checkbox path="ea1095CCovrgData.pageItems[${step.index}].dec" id="dec_${step.index}" disabled="true" />
				</td>
			</tr>
		</c:forEach>
	</tbody>
	<tfoot>
		<tr>
			<td colspan = "19" class="tabular_footer" >
				<!-- Pagination -->
				<c:if test="${ EA1095InfoCommand.currentPage1095C != null && EA1095InfoCommand.currentPage1095C != ''}">

					<div class="hidden">
						Pagination Fields
						<form:hidden path="currentPage1095C" id="CurrentPage" />
						<form:hidden path="totalPages1095C" id="TotalPages"/>

						Pagination Buttons
						<input id="SelectedPageButton" class="hidden" type="submit" name="mySubmitSelectedPageButton" value="Submit Query" />
						<input id="PaginationButton" class="hidden" type="submit" name="mySubmitPaginationButton" value="Submit Query" />
					</div>

					<div class="pagination">
						<c:choose>
							<c:when test="${EA1095InfoCommand.currentPage1095C != 1}">
								<a href="#" id="FirstPage" class="link_button unsavedDataCheck" >
									<img class="hover_button" alt="First page" src="<spring:theme code="commonBase" />images/table_first.gif" hoverSrc="<spring:theme code="commonBase" />images/table_first_h.gif" />
								</a>
							</c:when>
							<c:otherwise>
								<img alt="First page (disabled)" src="<spring:theme code="commonBase" />images/table_first_d.gif" />
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="${EA1095InfoCommand.currentPage1095C != 1}">
								<a href="#" id="PreviousPage" class="link_button unsavedDataCheck" >
									<img class="hover_button" alt="Previous page" src="<spring:theme code="commonBase" />images/table_prev.gif" hoverSrc="<spring:theme code="commonBase" />images/table_prev_h.gif" />
								</a>
							</c:when>
							<c:otherwise>
								<img alt="Previous page (disabled)" src="<spring:theme code="commonBase" />images/table_prev_d.gif" />
							</c:otherwise>
						</c:choose>

						<div>
							<form:select path="selectedPage1095C" id="SelectedPage" cssClass="unsavedDataCheckOnChange ignore_changes last_field" tabindex="1">
								<c:forEach begin="1" end="${EA1095InfoCommand.totalPages1095C}" var="current">
									<form:option value="${current}" />
								</c:forEach>
							</form:select>
							 / ${EA1095InfoCommand.totalPages1095C}
						</div>

						<c:choose>
							<c:when test="${EA1095InfoCommand.currentPage1095C < EA1095InfoCommand.totalPages1095C}">
								<a href="#" id="NextPage" class="link_button unsavedDataCheck">
									<img class="hover_button" alt="Next page" src="<spring:theme code="commonBase" />images/table_next.gif" hoverSrc="<spring:theme code="commonBase" />images/table_next_h.gif" />
								</a>
							</c:when>
							<c:otherwise>
								<img alt="Next page (disabled)" src="<spring:theme code="commonBase" />images/table_next_d.gif" />
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="${EA1095InfoCommand.currentPage1095C < EA1095InfoCommand.totalPages1095C}">
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