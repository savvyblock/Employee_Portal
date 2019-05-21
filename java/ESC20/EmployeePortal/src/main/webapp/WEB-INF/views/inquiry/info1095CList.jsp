<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table class="groupWrapTable" style="border:0;margin: 0;width:100%;">
	<tr>
		<td>
			<table class="table border-table responsive-table no-thead print-table middle-td-table print-show-table">
				<tr>
					<td class="no-border-td" colspan="16">
						<h2 class="sub-title">${sessionScope.languageJSON.info1095Table.partIIICoverIndividuals}</h2>
						<div>
								<span>${sessionScope.languageJSON.info1095Table.ifEmployerProvidedSelfInsurance02}</span>
						</div>
					</td>
					<td colspan="3">
						<span class="print-check-disabled print-show">
							<i class="fa fa-times"></i>
						</span>
						<input class="checkBoxOld print-hide" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}" checked disabled/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table class="table border-table responsive-table no-thead print-table middle-td-table">
				<thead>
					<tr>
						<th class="print-hide"><span>${sessionScope.languageJSON.info1095Table.firstName}</span></th>
						<th class="print-hide"><span>${sessionScope.languageJSON.info1095Table.lastName}</span></th>
						<th>
							<span class="print-hide">${sessionScope.languageJSON.info1095Table.middleName}</span>
							<span class="print-show">${sessionScope.languageJSON.info1095Table.nameIndividuals}</span>
						</th>
						<th class="print-hide">
							<span>${sessionScope.languageJSON.info1095Table.generation}</span>
						</th>
						<th>
							<span class="print-show">${sessionScope.languageJSON.info1095Table.SSNOrTIN}</span>
							<span class="print-hide">${sessionScope.languageJSON.info1095Table.ssn}</span>
						</th>
						<th>
							<span class="print-show">${sessionScope.languageJSON.info1095Table.DOB}</span>
							<span class="print-hide">${sessionScope.languageJSON.info1095Table.dob}</span>
						</th>
						<th>
							<span class="print-show">${sessionScope.languageJSON.info1095Table.coverAllMonths}</span>
							<span class="print-hide">${sessionScope.languageJSON.info1095Table.all}</span>
						</th>
						<th>${sessionScope.languageJSON.info1095Table.Jan}</th>
						<th>${sessionScope.languageJSON.info1095Table.Feb}</th>
						<th>${sessionScope.languageJSON.info1095Table.Mar}</th>
						<th>${sessionScope.languageJSON.info1095Table.Apr}</th>
						<th>${sessionScope.languageJSON.info1095Table.May}</th>
						<th>${sessionScope.languageJSON.info1095Table.Jun}</th>
						<th>${sessionScope.languageJSON.info1095Table.Jul}</th>
						<th>${sessionScope.languageJSON.info1095Table.Aug}</th>
						<th>${sessionScope.languageJSON.info1095Table.Sep}</th>
						<th>${sessionScope.languageJSON.info1095Table.Oct}</th>
						<th>${sessionScope.languageJSON.info1095Table.Nov}</th>
						<th>${sessionScope.languageJSON.info1095Table.Dec}</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${fn:length(cList) > 0}">
						<c:forEach var="itemC" items="${cList}">
							<tr>
								<td class="print-hide" scope="${sessionScope.languageJSON.info1095Table.firstName}"  data-title="${sessionScope.languageJSON.info1095Table.firstName}">${itemC.nameF}</td>
								<td class="print-hide" scope="${sessionScope.languageJSON.info1095Table.lastName}"  data-title="${sessionScope.languageJSON.info1095Table.lastName}">${itemC.nameM}</td>
								<td scope="${sessionScope.languageJSON.info1095Table.middleName}"  data-title="${sessionScope.languageJSON.info1095Table.middleName}">
									<span class="print-hide">${itemC.nameL}</span>
									<span class="print-show">${itemC.nameF} ${itemC.nameM} ${itemC.nameL}</span>
								</td>
								<td class="print-hide" scope="${sessionScope.languageJSON.info1095Table.generation}"  data-title="${sessionScope.languageJSON.info1095Table.generation}">
										${itemC.nameGen}
								</td>
								<td scope="${sessionScope.languageJSON.info1095Table.ssn}"  data-title="${sessionScope.languageJSON.info1095Table.ssn}">
										${itemC.ssn}
								</td>
								<td scope="${sessionScope.languageJSON.info1095Table.dob}"  data-title="${sessionScope.languageJSON.info1095Table.dob}">
										${itemC.dob}
								</td>
								<td scope="${sessionScope.languageJSON.info1095Table.all}"  data-title="${sessionScope.languageJSON.info1095Table.all}">
										<c:if test="${itemC.monAll == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemC.monAll=='N'}">
												<input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}" />
										</c:if>
								</td>
								<td  data-title="${sessionScope.languageJSON.info1095Table.Jan}">
										<c:if test="${itemC.mon01 == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemC.mon01=='N'}">
												<input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}" />
										</c:if>
								</td>
								<td  data-title="${sessionScope.languageJSON.info1095Table.Feb}">
										<c:if test="${itemC.mon02 == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemC.mon02=='N'}">
												<input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}" />
										</c:if>
								</td>
								<td  data-title="${sessionScope.languageJSON.info1095Table.Mar}">
										<c:if test="${itemC.mon03 == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemC.mon03=='N'}">
												<input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}" />
										</c:if>
								</td>
								<td  data-title="${sessionScope.languageJSON.info1095Table.Apr}">
										<c:if test="${itemC.mon04 == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemC.mon04=='N'}">
												<input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}" />
										</c:if>
								</td>
								<td  data-title="${sessionScope.languageJSON.info1095Table.May}">
										<c:if test="${itemC.mon05 == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemC.mon05=='N'}">
												<input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}" />
										</c:if>
								</td>
								<td  data-title="${sessionScope.languageJSON.info1095Table.Jun}">
										<c:if test="${itemC.mon6 == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemC.mon06=='N'}">
												<input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}" />
										</c:if>
								</td>
								<td  data-title="${sessionScope.languageJSON.info1095Table.Jul}">
										<c:if test="${itemC.mon07 == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemC.mon07=='N'}">
												<input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}" />
										</c:if>
								</td>
								<td  data-title="${sessionScope.languageJSON.info1095Table.Aug}">
										<c:if test="${itemC.mon08 == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemC.mon08=='N'}">
												<input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}" />
										</c:if>
								</td>
								<td  data-title="${sessionScope.languageJSON.info1095Table.Sep}">
									<c:if test="${itemC.mon09 == 'Y'}">
										<span class="print-check-disabled">
												<i class="fa fa-times"></i>
											</span>
								</c:if>
								<c:if test="${itemC.mon09=='N'}">
										<input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}" />
								</c:if>
								</td>
								<td  data-title="${sessionScope.languageJSON.info1095Table.Oct}">
									<c:if test="${itemC.mon10 == 'Y'}">
										<span class="print-check-disabled">
												<i class="fa fa-times"></i>
											</span>
								</c:if>
								<c:if test="${itemC.mon10=='N'}">
										<input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}" />
								</c:if>
								</td>
								<td  data-title="${sessionScope.languageJSON.info1095Table.Nov}">
									<c:if test="${itemC.mon11 == 'Y'}">
										<span class="print-check-disabled">
												<i class="fa fa-times"></i>
											</span>
								</c:if>
								<c:if test="${itemC.mon11=='N'}">
										<input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}" />
								</c:if>
								</td>
								<td  data-title="${sessionScope.languageJSON.info1095Table.Dec}">
									<c:if test="${itemC.mon12 == 'Y'}">
										<span class="print-check-disabled">
												<i class="fa fa-times"></i>
											</span>
								</c:if>
								<c:if test="${itemC.mon12=='N'}">
										<input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}" />
								</c:if>
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${fn:length(cList) == 0}">
						<tr class="no-print">
								<td colspan="19" style="text-align: center;">${sessionScope.languageJSON.label.noRows}</td>
						</tr>
						<tr class="print-tr">
								<td colspan="16" style="text-align: center;">${sessionScope.languageJSON.label.noRows}</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</td>
	</tr>
	
<c:if test="${fn:length(cList) > 0}">
	<tr>
		<td>
				
			<div class="pageGroup">
				<button class="pageBtn firstPate"  onclick="changePage(1)" 
				aria-label="${sessionScope.languageJSON.label.firstPage}"
						<c:if test="${ CPageNo ==1 }">disabled</c:if>>
						<i class="fa fa-angle-double-left "></i>
				</button>  
				<button class="pageBtn prevPage" onclick="changePage(${CPageNo - 1})"
				aria-label="${sessionScope.languageJSON.label.prevPage}"
						<c:if test="${ CPageNo ==1  }">disabled</c:if>>
						<i class="fa fa-angle-left "></i>
				</button>
				<select class="selectPage" name="page" id="pageNow" aria-label="${sessionScope.languageJSON.label.choosePage}" onchange="changePage()">
						<c:forEach  var="page"  begin="1" end="${CTotal}">
						<option value="${page}" <c:if test="${page == CPageNo }">selected</c:if>>${page}</option>
						</c:forEach>
				</select>
				<div class="page-list">
						<span class="slash">/</span>
						<span class="totalPate">${CTotal}</span>
				</div>
				<button class="pageBtn nextPate"  onclick="changePage(${CPageNo + 1})"
				aria-label="${sessionScope.languageJSON.label.nextPage}"
						<c:if test="${CPageNo == CTotal  }">disabled</c:if>>
								<i class="fa fa-angle-right "></i>
				</button>
				<button class="pageBtn lastPate" onclick="changePage(${CTotal})"
				aria-label="${sessionScope.languageJSON.label.lastPage}"
						<c:if test="${CPageNo == CTotal   }">disabled</c:if>>
								<i class="fa fa-angle-double-right"></i>
				</button>
			</div>
		</td>
	</tr>
</c:if>
</table>
<form id="changePageForm" hidden="hidden" action="sortOrChangePageForTypeC" method="POST">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="year" id="yearNow" value="${selectedYear}">
        <input type="hidden" name="CPageNo" id="selectPageNow">
</form>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/inquiry/info1095CList.js"></script>
