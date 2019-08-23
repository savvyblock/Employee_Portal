<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:forEach var="cEmpItem" items="${cEmpList}" varStatus="status">
		<c:if test="${cEmpItem.year == selectedYear}">
			<table
			class="table border-table responsive-table no-thead print-table money-table mb-0 tableSelf"
			>
			<thead>
					<tr class="print-tr">
							<td
									colspan="7"
									class="sub-title"
									style="width:50%;"
							>
							<span>${sessionScope.constantJSON.info1095Table.partIIEmployee}</span>
							</td>
							<td colspan="7">
											<span>${sessionScope.constantJSON.info1095Table.planStartMonth}</span>:
									<b class="font-20">09</b>
							</td>
					</tr>
					<tr>
							<td></td>
							<th class="text-center" id="allMonthsMoney">${sessionScope.constantJSON.info1095Table.allMonths}</th>
							<th class="text-center" id="janMoney">${sessionScope.constantJSON.info1095Table.Jan}</th>
							<th class="text-center" id="febMoney">${sessionScope.constantJSON.info1095Table.Feb}</th>
							<th class="text-center" id="marMoney">${sessionScope.constantJSON.info1095Table.Mar}</th>
							<th class="text-center" id="aprMoney">${sessionScope.constantJSON.info1095Table.Apr}</th>
							<th class="text-center" id="mayMoney">${sessionScope.constantJSON.info1095Table.May}</th>
							<th class="text-center" id="junMoney">${sessionScope.constantJSON.info1095Table.Jun}</th>
							<th class="text-center" id="julMoney">${sessionScope.constantJSON.info1095Table.Jul}</th>
							<th class="text-center" id="augMoney">${sessionScope.constantJSON.info1095Table.Aug}</th>
							<th class="text-center" id="sepMoney">${sessionScope.constantJSON.info1095Table.Sep}</th>
							<th class="text-center" id="octMoney">${sessionScope.constantJSON.info1095Table.Oct}</th>
							<th class="text-center" id="novMoney">${sessionScope.constantJSON.info1095Table.Nov}</th>
							<th class="text-center" id="decMoney">${sessionScope.constantJSON.info1095Table.Dec}</th>
					</tr>
			</thead>
			<tbody>
					<tr>
							<th id="offerOfCoverage" class="tr-title">
									<div class="print-show">14 <span>${sessionScope.constantJSON.info1095Table.offerOfCoverage}</span></div>
									<span class="print-hide">${sessionScope.constantJSON.info1095Table.offerOfCoverageSimple}</span>
							</th>
							<td headers="allMonthsMoney offerOfCoverage" data-title="${sessionScope.constantJSON.info1095Table.allMonths}">${cEmpItem.monAll.offrOfCovrg}</td>
							<td headers="janMoney offerOfCoverage" data-title="${sessionScope.constantJSON.info1095Table.Jan}">${cEmpItem.mon01.offrOfCovrg}</td>
							<td headers="febMoney offerOfCoverage" data-title="${sessionScope.constantJSON.info1095Table.Feb}">${cEmpItem.mon02.offrOfCovrg}</td>
							<td headers="marMoney offerOfCoverage" data-title="${sessionScope.constantJSON.info1095Table.Mar}">${cEmpItem.mon03.offrOfCovrg}</td>
							<td headers="aprMoney offerOfCoverage" data-title="${sessionScope.constantJSON.info1095Table.Apr}">${cEmpItem.mon04.offrOfCovrg}</td>
							<td headers="mayMoney offerOfCoverage" data-title="${sessionScope.constantJSON.info1095Table.May}">${cEmpItem.mon05.offrOfCovrg}</td>
							<td headers="junMoney offerOfCoverage" data-title="${sessionScope.constantJSON.info1095Table.Jun}">${cEmpItem.mon06.offrOfCovrg}</td>
							<td headers="julMoney offerOfCoverage" data-title="${sessionScope.constantJSON.info1095Table.Jul}">${cEmpItem.mon07.offrOfCovrg}</td>
							<td headers="augMoney offerOfCoverage" data-title="${sessionScope.constantJSON.info1095Table.Aug}">${cEmpItem.mon08.offrOfCovrg}</td>
							<td headers="sepMoney offerOfCoverage" data-title="${sessionScope.constantJSON.info1095Table.Sep}">${cEmpItem.mon09.offrOfCovrg}</td>
							<td headers="octMoney offerOfCoverage" data-title="${sessionScope.constantJSON.info1095Table.Oct}">${cEmpItem.mon10.offrOfCovrg}</td>
							<td headers="novMoney offerOfCoverage" data-title="${sessionScope.constantJSON.info1095Table.Nov}">${cEmpItem.mon11.offrOfCovrg}</td>
							<td headers="decMoney offerOfCoverage" data-title="${sessionScope.constantJSON.info1095Table.Dec}">${cEmpItem.mon12.offrOfCovrg}</td>
					</tr>
					<tr>
							<th id="employeeShare" class="tr-title">
									<div class="print-show">15 <span>${sessionScope.constantJSON.info1095Table.employeeRequiredContribution}</span></div>
									<span class="print-hide">${sessionScope.constantJSON.info1095Table.employeeShare}</span>
							</th>
							<td headers="allMonthsMoney employeeShare" data-title="${sessionScope.constantJSON.info1095Table.allMonths}">
											<span class="unit-dollar">${sessionScope.constantJSON.info1095Table.dollar}</span> 
											<fmt:formatNumber value="${cEmpItem.monAll.empShr}" pattern="#,##0.00"/>
							</td>
							<td headers="janMoney employeeShare" data-title="${sessionScope.constantJSON.info1095Table.Jan}">
											<span class="unit-dollar">${sessionScope.constantJSON.info1095Table.dollar}</span> 
											<fmt:formatNumber value="${cEmpItem.mon01.empShr}" pattern="#,##0.00"/>
							</td>
							<td headers="febMoney employeeShare" data-title="${sessionScope.constantJSON.info1095Table.Feb}">
											<span class="unit-dollar">${sessionScope.constantJSON.info1095Table.dollar}</span> 
											<fmt:formatNumber value="${cEmpItem.mon02.empShr}" pattern="#,##0.00"/>
							</td>
							<td headers="marMoney employeeShare" data-title="${sessionScope.constantJSON.info1095Table.Mar}">
											<span class="unit-dollar">${sessionScope.constantJSON.info1095Table.dollar}</span> 
											<fmt:formatNumber value="${cEmpItem.mon03.empShr}" pattern="#,##0.00"/>
							</td>
							<td headers="aprMoney employeeShare" data-title="${sessionScope.constantJSON.info1095Table.Apr}">
											<span class="unit-dollar">${sessionScope.constantJSON.info1095Table.dollar}</span> 
											<fmt:formatNumber value="${cEmpItem.mon04.empShr}" pattern="#,##0.00"/>
							</td>
							<td headers="mayMoney employeeShare" data-title="${sessionScope.constantJSON.info1095Table.May}">
											<span class="unit-dollar">${sessionScope.constantJSON.info1095Table.dollar}</span> 
											<fmt:formatNumber value="${cEmpItem.mon05.empShr}" pattern="#,##0.00"/>
							</td>
							<td headers="junMoney employeeShare" data-title="${sessionScope.constantJSON.info1095Table.Jun}">
											<span class="unit-dollar">${sessionScope.constantJSON.info1095Table.dollar}</span> 
											<fmt:formatNumber value="${cEmpItem.mon06.empShr}" pattern="#,##0.00"/>
							</td>
							<td headers="julMoney employeeShare" data-title="${sessionScope.constantJSON.info1095Table.Jul}">
											<span class="unit-dollar">${sessionScope.constantJSON.info1095Table.dollar}</span> 
											<fmt:formatNumber value="${cEmpItem.mon07.empShr}" pattern="#,##0.00"/>
							</td>
							<td headers="augMoney employeeShare" data-title="${sessionScope.constantJSON.info1095Table.Aug}">
											<span class="unit-dollar">${sessionScope.constantJSON.info1095Table.dollar}</span> 
											<fmt:formatNumber value="${cEmpItem.mon08.empShr}" pattern="#,##0.00"/>
							</td>
							<td headers="sepMoney employeeShare" data-title="${sessionScope.constantJSON.info1095Table.Sep}">
											<span class="unit-dollar">${sessionScope.constantJSON.info1095Table.dollar}</span> 
											<fmt:formatNumber value="${cEmpItem.mon09.empShr}" pattern="#,##0.00"/>
							</td>
							<td headers="octMoney employeeShare" data-title="${sessionScope.constantJSON.info1095Table.Oct}">
											<span class="unit-dollar">${sessionScope.constantJSON.info1095Table.dollar}</span> 
											<fmt:formatNumber value="${cEmpItem.mon10.empShr}" pattern="#,##0.00"/>
							</td>
							<td headers="novMoney employeeShare" data-title="${sessionScope.constantJSON.info1095Table.Nov}">
											<span class="unit-dollar">${sessionScope.constantJSON.info1095Table.dollar}</span> 
											<fmt:formatNumber value="${cEmpItem.mon11.empShr}" pattern="#,##0.00"/>
							</td>
							<td headers="decMoney employeeShare" data-title="${sessionScope.constantJSON.info1095Table.Dec}">
											<span class="unit-dollar">${sessionScope.constantJSON.info1095Table.dollar}</span> 
											<fmt:formatNumber value="${cEmpItem.mon12.empShr}" pattern="#,##0.00"/>
							</td>
					</tr>
					<tr>
							<th id="SafeHarbor" class="tr-title">
									<div class="print-show">16 <span>${sessionScope.constantJSON.info1095Table.section4980HSafeHarbor}</span></div>
									<span class="print-hide">${sessionScope.constantJSON.info1095Table.SafeHarbor}</span>
							</th>
							<td headers="allMonthsMoney SafeHarbor" data-title="${sessionScope.constantJSON.info1095Table.allMonths}">${cEmpItem.monAll.safeHrbr}</td>
							<td headers="janMoney SafeHarbor" data-title="${sessionScope.constantJSON.info1095Table.Jan}">${cEmpItem.mon01.safeHrbr}</td>
							<td headers="febMoney SafeHarbor" data-title="${sessionScope.constantJSON.info1095Table.Feb}">${cEmpItem.mon02.safeHrbr}</td>
							<td headers="marMoney SafeHarbor" data-title="${sessionScope.constantJSON.info1095Table.Mar}">${cEmpItem.mon03.safeHrbr}</td>
							<td headers="aprMoney SafeHarbor" data-title="${sessionScope.constantJSON.info1095Table.Apr}">${cEmpItem.mon04.safeHrbr}</td>
							<td headers="mayMoney SafeHarbor" data-title="${sessionScope.constantJSON.info1095Table.May}">${cEmpItem.mon05.safeHrbr}</td>
							<td headers="junMoney SafeHarbor" data-title="${sessionScope.constantJSON.info1095Table.Jun}">${cEmpItem.mon06.safeHrbr}</td>
							<td headers="julMoney SafeHarbor" data-title="${sessionScope.constantJSON.info1095Table.Jul}">${cEmpItem.mon07.safeHrbr}</td>
							<td headers="augMoney SafeHarbor" data-title="${sessionScope.constantJSON.info1095Table.Aug}">${cEmpItem.mon08.safeHrbr}</td>
							<td headers="sepMoney SafeHarbor" data-title="${sessionScope.constantJSON.info1095Table.Sep}">${cEmpItem.mon09.safeHrbr}</td>
							<td headers="octMoney SafeHarbor" data-title="${sessionScope.constantJSON.info1095Table.Oct}">${cEmpItem.mon10.safeHrbr}</td>
							<td headers="novMoney SafeHarbor" data-title="${sessionScope.constantJSON.info1095Table.Nov}">${cEmpItem.mon11.safeHrbr}</td>
							<td headers="decMoney SafeHarbor" data-title="${sessionScope.constantJSON.info1095Table.Dec}">${cEmpItem.mon12.safeHrbr}</td>
					</tr>
			</tbody>
			</table>
			<h2 class="table-top-title no-print">
					<b>${sessionScope.constantJSON.info1095Table.coverIndividuals}</b>
			</h2>
			<div class="flex self-insured-flex no-print">
					<div class="self-insured-tips">
							<span>${sessionScope.constantJSON.info1095Table.ifEmployerProvidedSelfInsurance02}</span>
					</div>
					<div class="self-insured-check">
							<div>
											<label for="selfInsured">${sessionScope.constantJSON.info1095Table.selfInsured}</label>:
									<input
											class="icheck"
											type="checkbox"
											name="selfInsured"
											id="selfInsured"
											<c:if test="${cEmpItem.monAll.selfIns=='Y'}">checked="checked"</c:if>
											disabled
									/>
							</div>
					</div>
					<div class="self-insured-time">
									<span>${sessionScope.constantJSON.info1095Table.planStartMonth}</span>: ${cEmpItem.monAll.planStrtMon}
					</div>
			</div>
		</c:if>
</c:forEach>
<table class="groupWrapTable" style="border:0;margin: 0;width:100%;">
	<tr>
		<td style="padding:0;">
			<table class="table border-table responsive-table no-thead print-table middle-td-table print-show-table tableSelf">
				<tr>
					<td class="no-border-td">
						<h2 class="sub-title">${sessionScope.constantJSON.info1095Table.partIIICoverIndividuals}</h2>
						<div>
								<span>${sessionScope.constantJSON.info1095Table.ifEmployerProvidedSelfInsurance02}</span>
						</div>
					</td>
					<td>
						<span class="print-check-disabled print-show">
							<i class="fa fa-times"></i>
						</span>
						<input class="checkBoxOld print-hide" type="checkbox" disabled="disabled" aria-label="${sessionScope.languageJSON.label.checkbox}" checked disabled/>
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
						<th class="print-hide"><span>${sessionScope.constantJSON.info1095Table.firstName}</span></th>
						<th>
							<span class="print-hide">${sessionScope.constantJSON.info1095Table.middleName}</span>
							<span class="print-show">${sessionScope.constantJSON.info1095Table.nameIndividuals}</span>
						</th>
						<th class="print-hide"><span>${sessionScope.constantJSON.info1095Table.lastName}</span></th>
						<th class="print-hide">
							<span>${sessionScope.constantJSON.info1095Table.generation}</span>
						</th>
						<th class="text-center">
							<span class="print-show">${sessionScope.constantJSON.info1095Table.SSNOrTIN}</span>
							<span class="print-hide">${sessionScope.constantJSON.info1095Table.ssn}</span>
						</th>
						<th class="text-center">
							<span class="print-show">${sessionScope.constantJSON.info1095Table.DOB}</span>
							<span class="print-hide">${sessionScope.constantJSON.info1095Table.dob}</span>
						</th>
						<th class="text-center">
							<span class="print-show">${sessionScope.constantJSON.info1095Table.coverAllMonths}</span>
							<span class="print-hide">${sessionScope.constantJSON.info1095Table.all}</span>
						</th>
						<th class="text-center">${sessionScope.constantJSON.info1095Table.Jan}</th>
						<th class="text-center">${sessionScope.constantJSON.info1095Table.Feb}</th>
						<th class="text-center">${sessionScope.constantJSON.info1095Table.Mar}</th>
						<th class="text-center">${sessionScope.constantJSON.info1095Table.Apr}</th>
						<th class="text-center">${sessionScope.constantJSON.info1095Table.May}</th>
						<th class="text-center">${sessionScope.constantJSON.info1095Table.Jun}</th>
						<th class="text-center">${sessionScope.constantJSON.info1095Table.Jul}</th>
						<th class="text-center">${sessionScope.constantJSON.info1095Table.Aug}</th>
						<th class="text-center">${sessionScope.constantJSON.info1095Table.Sep}</th>
						<th class="text-center">${sessionScope.constantJSON.info1095Table.Oct}</th>
						<th class="text-center">${sessionScope.constantJSON.info1095Table.Nov}</th>
						<th class="text-center">${sessionScope.constantJSON.info1095Table.Dec}</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${fn:length(cList) > 0}">
						<c:forEach var="itemC" items="${cList}">
							<tr>
								<td class="print-hide"  data-title="${sessionScope.constantJSON.info1095Table.firstName}">${itemC.nameF}</td>
								<td  data-title="${sessionScope.constantJSON.info1095Table.middleName}">
									<span class="print-hide">${itemC.nameM}</span>
									<span class="print-show">${itemC.nameF} ${itemC.nameM} ${itemC.nameL}</span>
								</td>
								<td class="print-hide"  data-title="${sessionScope.constantJSON.info1095Table.lastName}">${itemC.nameL}</td>
								<td class="print-hide"  data-title="${sessionScope.constantJSON.info1095Table.generation}">
										${itemC.genDescription}
								</td>
								<td class="text-center"  data-title="${sessionScope.constantJSON.info1095Table.ssn}">
										${itemC.ssn}
								</td>
								<td class="text-center" data-title="${sessionScope.constantJSON.info1095Table.dob}">
										${itemC.dob}
								</td>
								<td class="text-center" data-title="${sessionScope.constantJSON.info1095Table.all}">
										<c:if test="${itemC.monAll == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemC.monAll=='N'}">
												<input class="checkBoxOld" type="checkbox" disabled="disabled" aria-label="${sessionScope.languageJSON.label.checkbox}" />
										</c:if>
								</td>
								<td class="text-center" data-title="${sessionScope.constantJSON.info1095Table.Jan}">
										<c:if test="${itemC.mon01 == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemC.mon01=='N'}">
												<input class="checkBoxOld" type="checkbox" disabled="disabled" aria-label="${sessionScope.languageJSON.label.checkbox}" />
										</c:if>
								</td>
								<td class="text-center" data-title="${sessionScope.constantJSON.info1095Table.Feb}">
										<c:if test="${itemC.mon02 == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemC.mon02=='N'}">
												<input class="checkBoxOld" type="checkbox" disabled="disabled" aria-label="${sessionScope.languageJSON.label.checkbox}" />
										</c:if>
								</td>
								<td class="text-center" data-title="${sessionScope.constantJSON.info1095Table.Mar}">
										<c:if test="${itemC.mon03 == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemC.mon03=='N'}">
												<input class="checkBoxOld" type="checkbox" disabled="disabled" aria-label="${sessionScope.languageJSON.label.checkbox}" />
										</c:if>
								</td>
								<td class="text-center" data-title="${sessionScope.constantJSON.info1095Table.Apr}">
										<c:if test="${itemC.mon04 == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemC.mon04=='N'}">
												<input class="checkBoxOld" type="checkbox" disabled="disabled" aria-label="${sessionScope.languageJSON.label.checkbox}" />
										</c:if>
								</td>
								<td class="text-center" data-title="${sessionScope.constantJSON.info1095Table.May}">
										<c:if test="${itemC.mon05 == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemC.mon05=='N'}">
												<input class="checkBoxOld" type="checkbox" disabled="disabled" aria-label="${sessionScope.languageJSON.label.checkbox}" />
										</c:if>
								</td>
								<td class="text-center" data-title="${sessionScope.constantJSON.info1095Table.Jun}">
										<c:if test="${itemC.mon06 == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemC.mon06=='N'}">
												<input class="checkBoxOld" type="checkbox" disabled="disabled" aria-label="${sessionScope.languageJSON.label.checkbox}" />
										</c:if>
								</td>
								<td class="text-center" data-title="${sessionScope.constantJSON.info1095Table.Jul}">
										<c:if test="${itemC.mon07 == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemC.mon07=='N'}">
												<input class="checkBoxOld" type="checkbox" disabled="disabled" aria-label="${sessionScope.languageJSON.label.checkbox}" />
										</c:if>
								</td>
								<td class="text-center" data-title="${sessionScope.constantJSON.info1095Table.Aug}">
										<c:if test="${itemC.mon08 == 'Y'}">
												<span class="print-check-disabled">
														<i class="fa fa-times"></i>
													</span>
										</c:if>
										<c:if test="${itemC.mon08=='N'}">
												<input class="checkBoxOld" type="checkbox" disabled="disabled" aria-label="${sessionScope.languageJSON.label.checkbox}" />
										</c:if>
								</td>
								<td class="text-center" data-title="${sessionScope.constantJSON.info1095Table.Sep}">
									<c:if test="${itemC.mon09 == 'Y'}">
										<span class="print-check-disabled">
												<i class="fa fa-times"></i>
											</span>
								</c:if>
								<c:if test="${itemC.mon09=='N'}">
										<input class="checkBoxOld" type="checkbox" disabled="disabled" aria-label="${sessionScope.languageJSON.label.checkbox}" />
								</c:if>
								</td>
								<td class="text-center" data-title="${sessionScope.constantJSON.info1095Table.Oct}">
									<c:if test="${itemC.mon10 == 'Y'}">
										<span class="print-check-disabled">
												<i class="fa fa-times"></i>
											</span>
								</c:if>
								<c:if test="${itemC.mon10=='N'}">
										<input class="checkBoxOld" type="checkbox" disabled="disabled" aria-label="${sessionScope.languageJSON.label.checkbox}" />
								</c:if>
								</td>
								<td class="text-center" data-title="${sessionScope.constantJSON.info1095Table.Nov}">
									<c:if test="${itemC.mon11 == 'Y'}">
										<span class="print-check-disabled">
												<i class="fa fa-times"></i>
											</span>
								</c:if>
								<c:if test="${itemC.mon11=='N'}">
										<input class="checkBoxOld" type="checkbox" disabled="disabled" aria-label="${sessionScope.languageJSON.label.checkbox}" />
								</c:if>
								</td>
								<td class="text-center" data-title="${sessionScope.constantJSON.info1095Table.Dec}">
									<c:if test="${itemC.mon12 == 'Y'}">
										<span class="print-check-disabled">
												<i class="fa fa-times"></i>
											</span>
								</c:if>
								<c:if test="${itemC.mon12=='N'}">
										<input class="checkBoxOld" type="checkbox" disabled="disabled" aria-label="${sessionScope.languageJSON.label.checkbox}" />
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
	<tr class="print-hide">
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

<script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/inquiry/info1095CList.js"></script>
