<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table class="groupWrapTable" style="border:0;margin: 0;width:100%;">
	<tr>
		<td style="padding:0;">
			<table class="table border-table responsive-table no-thead print-table middle-td-table print-show-table tableSelf">
				<tr>
					<td class="no-border-td">
						<h2 class="sub-title">${sessionScope.languageJSON.info1095Table.partIIICoverIndividuals}</h2>
						<div>
								<span>${sessionScope.languageJSON.info1095Table.ifEmployerProvidedSelfInsurance02}</span>
						</div>
					</td>
					<td>
						<span class="print-check-disabled print-show">
							<i class="fa fa-times"></i>
						</span>
						<input class="checkBoxOld print-hide" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}"   checked disabled/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td style="padding:0;">
			<table class="table border-table responsive-table no-thead print-table middle-td-table tableSelf" style="margin-bottom:5px;">
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
					<c:if test="${fn:length(bList) > 0}">
						<c:forEach var="itemB" items="${bList}">
							<tr>
								<td class="print-hide" scope="${sessionScope.languageJSON.info1095Table.firstName}" data-title="${sessionScope.languageJSON.info1095Table.firstName}">${itemC.nameF}</td>
								<td class="print-hide" scope="${sessionScope.languageJSON.info1095Table.lastName}" data-title="${sessionScope.languageJSON.info1095Table.lastName}">${itemC.nameM}</td>
								<td scope="${sessionScope.languageJSON.info1095Table.middleName}" data-title="${sessionScope.languageJSON.info1095Table.middleName}">
									<span class="print-hide">${itemB.nameL}</span>
									<span class="print-show">${itemB.nameF} ${itemB.nameM} ${itemB.nameL}</span>
								</td>
								<td class="print-hide" scope="${sessionScope.languageJSON.info1095Table.generation}" data-title="${sessionScope.languageJSON.info1095Table.generation}">
										${itemB.nameGen}
								</td>
								<td scope="${sessionScope.languageJSON.info1095Table.ssn}" data-title="${sessionScope.languageJSON.info1095Table.ssn}">
										${itemB.ssn}
								</td>
								<td scope="${sessionScope.languageJSON.info1095Table.dob}" data-title="${sessionScope.languageJSON.info1095Table.dob}">
										${itemB.dob}
								</td>
								<td scope="${sessionScope.languageJSON.info1095Table.all}" data-title="${sessionScope.languageJSON.info1095Table.all}">
										<c:if test="${itemB.monAll == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemB.monAll=='N'}">
												<input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}"  />
										</c:if>
								</td>
								<td data-title="${sessionScope.languageJSON.info1095Table.Jan}">
										<c:if test="${itemB.mon01 == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemB.mon01=='N'}">
												<input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}"  />
										</c:if>
								</td>
								<td data-title="${sessionScope.languageJSON.info1095Table.Feb}">
										<c:if test="${itemB.mon02 == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemB.mon02=='N'}">
												<input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}" />
										</c:if>
								</td>
								<td data-title="${sessionScope.languageJSON.info1095Table.Mar}">
										<c:if test="${itemB.mon03 == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemB.mon03=='N'}">
												<input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}" />
										</c:if>
								</td>
								<td data-title="${sessionScope.languageJSON.info1095Table.Apr}">
										<c:if test="${itemB.mon04 == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemB.mon04=='N'}">
												<input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}" />
										</c:if>
								</td>
								<td data-title="${sessionScope.languageJSON.info1095Table.May}">
										<c:if test="${itemB.mon05 == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemB.mon05=='N'}">
												<input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}" />
										</c:if>
								</td>
								<td data-title="${sessionScope.languageJSON.info1095Table.Jun}">
										<c:if test="${itemB.mon6 == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemB.mon06=='N'}">
												<input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}" />
										</c:if>
								</td>
								<td data-title="${sessionScope.languageJSON.info1095Table.Jul}">
										<c:if test="${itemB.mon07 == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemB.mon07=='N'}">
												<input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}" />
										</c:if>
								</td>
								<td data-title="${sessionScope.languageJSON.info1095Table.Aug}">
										<c:if test="${itemB.mon08 == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemB.mon08=='N'}">
												<input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}" />
										</c:if>
								</td>
								<td data-title="${sessionScope.languageJSON.info1095Table.Sep}">
									<c:if test="${itemB.mon09 == 'Y'}">
										<span class="print-check-disabled">
												<i class="fa fa-times"></i>
											</span>
								</c:if>
								<c:if test="${itemB.mon09=='N'}">
										<input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}" />
								</c:if>
								</td>
								<td data-title="${sessionScope.languageJSON.info1095Table.Oct}">
									<c:if test="${itemB.mon10 == 'Y'}">
										<span class="print-check-disabled">
												<i class="fa fa-times"></i>
											</span>
								</c:if>
								<c:if test="${itemB.mon10=='N'}">
										<input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}" />
								</c:if>
								</td>
								<td data-title="${sessionScope.languageJSON.info1095Table.Nov}">
									<c:if test="${itemB.mon11 == 'Y'}">
										<span class="print-check-disabled">
												<i class="fa fa-times"></i>
											</span>
								</c:if>
								<c:if test="${itemB.mon11=='N'}">
										<input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}" />
								</c:if>
								</td>
								<td data-title="${sessionScope.languageJSON.info1095Table.Dec}">
									<c:if test="${itemB.mon12 == 'Y'}">
											<span class="print-check-disabled">
													<i class="fa fa-times"></i>
													</span>
									</c:if>
									<c:if test="${itemB.mon12=='N'}">
											<input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.label.checkbox}" />
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${fn:length(bList) == 0}">
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
	
	<c:if test="${fn:length(bList) > 0}">
		<tr class="print-hide">
			<td>
				<table class="table border-table responsive-table no-thead print-table middle-td-table">
					<tr>
						<td colspan="19">
							<div class="pageGroup">
									<button class="pageBtn firstPate" onclick="changePage(1)"
										aria-label="${sessionScope.languageJSON.label.firstPage}"
											<c:if test="${ BPageNo ==1 }">disabled</c:if>>
											<i class="fa fa-angle-double-left "></i>
									</button>  
									<button class="pageBtn prevPage" onclick="changePage(${BPageNo - 1})" 
									aria-label="${sessionScope.languageJSON.label.prevPage}"
											<c:if test="${ BPageNo ==1  }">disabled</c:if>>
											<i class="fa fa-angle-left "></i>
									</button>
									<select class="selectPage" name="page" id="pageNow" aria-label="${sessionScope.languageJSON.label.choosePage}" onchange="changePage()">
											<c:forEach  var="page"  begin="1" end="${BTotal}">
											<option value="${page}" <c:if test="${page == BPageNo }">selected</c:if>>${page}</option>
											</c:forEach>
									</select>
									<div class="page-list">
											<span class="slash">/</span>
											<span class="totalPate">${BTotal}</span>
									</div>
									<button class="pageBtn nextPate"  onclick="changePage(${BPageNo + 1})" 
									aria-label="${sessionScope.languageJSON.label.nextPage}"
											<c:if test="${BPageNo == BTotal  }">disabled</c:if>>
													<i class="fa fa-angle-right "></i>
									</button>
									<button class="pageBtn lastPate"  onclick="changePage(${BTotal})" 
									aria-label="${sessionScope.languageJSON.label.lastPage}"
											<c:if test="${BPageNo == BTotal   }">disabled</c:if>>
													<i class="fa fa-angle-double-right"></i>
									</button>
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</c:if>
</table>
<form id="changePageForm" hidden="hidden" action="sortOrChangePageForTypeB" method="POST">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" name="year" id="yearNow" value="${selectedYear}">
    <input type="hidden" name="BPageNo" id="selectPageNow">
</form>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/inquiry/info1095BList.js"></script>
