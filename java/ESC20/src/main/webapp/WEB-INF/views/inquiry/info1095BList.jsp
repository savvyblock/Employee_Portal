<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table class="table border-table responsive-table no-thead print-table middle-td-table">
    <thead>
        <tr>
            <td class="no-border-td" colspan="14">
                <span class="sub-title" data-localize="info1095Table.partIIICoverIndividuals"></span>
                <div>
                        <span data-localize="info1095Table.ifEmployerProvidedSelfInsurance02"></span>
                </div>
            </td>
            <td colspan="2">
                <span class="print-check-disabled">
                    <i class="fa fa-times"></i>
                </span>
                <input class="checkBoxOld" type="checkbox" aria-label=""  data-localize="label.checkbox" />
            </td>
        </tr>
        <tr>
            <th><span data-localize="info1095Table.nameIndividuals"></span></th>
            <th><span data-localize="info1095Table.generation"></span></th>
            <th><span data-localize="info1095Table.SSNOrTIN"></span></th>
            <th>
                <span data-localize="info1095Table.DOB"></span>
            </th>
            <th><span data-localize="info1095Table.coverAllMonths"></span></th>
            <th data-localize="info1095Table.Jan"></th>
            <th data-localize="info1095Table.Feb"></th>
            <th data-localize="info1095Table.Mar"></th>
            <th data-localize="info1095Table.Apr"></th>
            <th data-localize="info1095Table.May"></th>
            <th data-localize="info1095Table.Jun"></th>
            <th data-localize="info1095Table.Jul"></th>
            <th data-localize="info1095Table.Aug"></th>
            <th data-localize="info1095Table.Sep"></th>
            <th data-localize="info1095Table.Oct"></th>
            <th data-localize="info1095Table.Nov"></th>
            <th data-localize="info1095Table.Dec"></th>
        </tr>
    </thead>
    <tbody>
        <c:if test="${fn:length(bList) == 0}">
            <tr>
                <td colspan="17" style="text-align: center;" data-localize="label.noRows">No Rows</td>
            </tr>
        </c:if>
        <c:if test="${fn:length(bList) > 0}">
            <c:forEach var="itemB" items="${bList}">
                <tr>
                    <td data-localize="info1095Table.nameIndividuals" data-localize-location="scope">
                        ${itemB.nameF} ${itemB.nameM} ${itemB.nameL}
                    </td>
                    <td data-localize="info1095Table.generation" data-localize-location="scope">
                            ${itemB.nameGen}
                    </td>
                    <td data-localize="info1095Table.SSNOrTIN" data-localize-location="scope">
                            ${itemB.ssn}
                    </td>
                    <td data-localize="info1095Table.DOB" data-localize-location="scope">
                            ${itemB.dob}
                    </td>
                    <td data-localize="info1095Table.coverAllMonths" data-localize-location="scope">
                            <c:if test="${itemB.monAll == 'Y'}">
                                    <span class="print-check-disabled">
                                            <i class="fa fa-times"></i>
                                        </span>
                            </c:if>
                            <c:if test="${itemB.monAll=='N'}">
                                    <input class="checkBoxOld" type="checkbox" aria-label=""  data-localize="label.checkbox" />
                            </c:if>
                    </td>
                    <td data-localize="info1095Table.Jan" data-localize-location="scope">
                            <c:if test="${itemB.mon01 == 'Y'}">
                                    <span class="print-check-disabled">
                                            <i class="fa fa-times"></i>
                                        </span>
                            </c:if>
                            <c:if test="${itemB.mon01=='N'}">
                                    <input class="checkBoxOld" type="checkbox" aria-label=""  data-localize="label.checkbox" />
                            </c:if>
                    </td>
                    <td data-localize="info1095Table.Feb" data-localize-location="scope">
                            <c:if test="${itemB.mon02 == 'Y'}">
                                    <span class="print-check-disabled">
                                            <i class="fa fa-times"></i>
                                        </span>
                            </c:if>
                            <c:if test="${itemB.mon02=='N'}">
                                    <input class="checkBoxOld" type="checkbox" aria-label=""  data-localize="label.checkbox" />
                            </c:if>
                    </td>
                    <td data-localize="info1095Table.Mar" data-localize-location="scope">
                            <c:if test="${itemB.mon03 == 'Y'}">
                                    <span class="print-check-disabled">
                                            <i class="fa fa-times"></i>
                                        </span>
                            </c:if>
                            <c:if test="${itemB.mon03=='N'}">
                                    <input class="checkBoxOld" type="checkbox" aria-label=""  data-localize="label.checkbox" />
                            </c:if>
                    </td>
                    <td data-localize="info1095Table.Apr" data-localize-location="scope">
                            <c:if test="${itemB.mon04 == 'Y'}">
                                    <span class="print-check-disabled">
                                            <i class="fa fa-times"></i>
                                        </span>
                            </c:if>
                            <c:if test="${itemB.mon04=='N'}">
                                    <input class="checkBoxOld" type="checkbox" aria-label=""  data-localize="label.checkbox" />
                            </c:if>
                    </td>
                    <td data-localize="info1095Table.May" data-localize-location="scope">
                            <c:if test="${itemB.mon05 == 'Y'}">
                                    <span class="print-check-disabled">
                                            <i class="fa fa-times"></i>
                                        </span>
                            </c:if>
                            <c:if test="${itemB.mon05=='N'}">
                                    <input class="checkBoxOld" type="checkbox" aria-label=""  data-localize="label.checkbox" />
                            </c:if>
                    </td>
                    <td data-localize="info1095Table.Jun" data-localize-location="scope">
                            <c:if test="${itemB.mon6 == 'Y'}">
                                    <span class="print-check-disabled">
                                            <i class="fa fa-times"></i>
                                        </span>
                            </c:if>
                            <c:if test="${itemB.mon06=='N'}">
                                    <input class="checkBoxOld" type="checkbox" aria-label=""  data-localize="label.checkbox" />
                            </c:if>
                    </td>
                    <td data-localize="info1095Table.Jul" data-localize-location="scope">
                            <c:if test="${itemB.mon07 == 'Y'}">
                                    <span class="print-check-disabled">
                                            <i class="fa fa-times"></i>
                                        </span>
                            </c:if>
                            <c:if test="${itemB.mon07=='N'}">
                                    <input class="checkBoxOld" type="checkbox" aria-label=""  data-localize="label.checkbox" />
                            </c:if>
                    </td>
                    <td data-localize="info1095Table.Aug" data-localize-location="scope">
                            <c:if test="${itemB.mon08 == 'Y'}">
                                    <span class="print-check-disabled">
                                            <i class="fa fa-times"></i>
                                        </span>
                            </c:if>
                            <c:if test="${itemB.mon08=='N'}">
                                    <input class="checkBoxOld" type="checkbox" aria-label=""  data-localize="label.checkbox" />
                            </c:if>
                    </td>
                    <td data-localize="info1095Table.Sep" data-localize-location="scope">
                        <c:if test="${itemB.mon09 == 'Y'}">
                            <span class="print-check-disabled">
                                    <i class="fa fa-times"></i>
                                </span>
                    </c:if>
                    <c:if test="${itemB.mon09=='N'}">
                            <input class="checkBoxOld" type="checkbox" aria-label=""  data-localize="label.checkbox" />
                    </c:if>
                    </td>
                    <td data-localize="info1095Table.Oct" data-localize-location="scope">
                        <c:if test="${itemB.mon10 == 'Y'}">
                            <span class="print-check-disabled">
                                    <i class="fa fa-times"></i>
                                </span>
                    </c:if>
                    <c:if test="${itemB.mon10=='N'}">
                            <input class="checkBoxOld" type="checkbox" aria-label=""  data-localize="label.checkbox" />
                    </c:if>
                    </td>
                    <td data-localize="info1095Table.Nov" data-localize-location="scope">
                        <c:if test="${itemB.mon11 == 'Y'}">
                            <span class="print-check-disabled">
                                    <i class="fa fa-times"></i>
                                </span>
                    </c:if>
                    <c:if test="${itemB.mon11=='N'}">
                            <input class="checkBoxOld" type="checkbox" aria-label=""  data-localize="label.checkbox" />
                    </c:if>
                    </td>
                    <td data-localize="info1095Table.Dec" data-localize-location="scope">
                        <c:if test="${itemB.mon12 == 'Y'}">
                                <span class="print-check-disabled">
                                        <i class="fa fa-times"></i>
                                        </span>
                        </c:if>
                        <c:if test="${itemB.mon12=='N'}">
                                <input class="checkBoxOld" type="checkbox" aria-label=""  data-localize="label.checkbox" />
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="17">
                        <div class="pageGroup">
                                <button class="pageBtn firstPate" onclick="changePage(1)"
                                aria-label="" data-localize="label.firstPage" data-localize-location="aria-label" data-localize-notText="true"
                                        <c:if test="${ BPageNo ==1 }">disabled</c:if>>
                                        <i class="fa fa-angle-double-left "></i>
                                </button>  
                                <button class="pageBtn prevPage" onclick="changePage(${BPageNo - 1})" 
                                aria-label="" data-localize="label.prevPage" data-localize-location="aria-label" data-localize-notText="true"
                                        <c:if test="${ BPageNo ==1  }">disabled</c:if>>
                                        <i class="fa fa-angle-left "></i>
                                </button>
                                <select class="selectPage" name="page" id="pageNow" aria-label="" data-localize="label.choosePage" onchange="changePage()"  data-localize-location="title">
                                        <c:forEach  var="page"  begin="1" end="${BTotal}">
                                        <option value="${page}" <c:if test="${page == BPageNo }">selected</c:if>>${page}</option>
                                        </c:forEach>
                                </select>
                                <div class="page-list">
                                        <span class="slash">/</span>
                                        <span class="totalPate">${BTotal}</span>
                                </div>
                                <button class="pageBtn nextPate"  onclick="changePage(${BPageNo + 1})" 
                                aria-label="" data-localize="label.nextPage" data-localize-location="aria-label" data-localize-notText="true"
                                        <c:if test="${BPageNo == BTotal  }">disabled</c:if>>
                                                <i class="fa fa-angle-right "></i>
                                </button>
                                <button class="pageBtn lastPate"  onclick="changePage(${BTotal})" 
                                aria-label="" data-localize="label.lastPage" data-localize-location="aria-label" data-localize-notText="true"
                                        <c:if test="${BPageNo == BTotal   }">disabled</c:if>>
                                                <i class="fa fa-angle-double-right"></i>
                                </button>
                        </div>
                </td>
        </tr>
        </c:if>
        
    </tbody>
</table>
<div class="PageNext"></div>
<jsp:include page="../report-1095/1095B-${selectedYear}.jsp"></jsp:include>
<form id="changePageForm" hidden="hidden" action="sortOrChangePageForTypeB" method="POST">
        <input type="text" name="year" id="yearNow" value="${selectedYear}" aria-label="" data-localize="accessHint.year">
        <input type="text" name="BPageNo" id="selectPageNow" aria-label="" data-localize="accessHint.currentPage">
</form>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/inquiry/info1095BList.js"></script>
