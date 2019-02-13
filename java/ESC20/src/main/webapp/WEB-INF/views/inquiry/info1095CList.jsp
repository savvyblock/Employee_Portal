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
                <input class="checkBoxOld" type="checkbox" />
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
        <c:if test="${fn:length(cList) == 0}">
            <tr>
                <td colspan="16" style="text-align: center;" data-localize="label.noRows">No Rows</td>
            </tr>
        </c:if>
        <c:if test="${fn:length(cList) > 0}">
            <c:forEach var="itemC" items="${cList}">
                <tr>
                    <td data-localize="info1095Table.nameIndividuals" data-localize-location="scope">
                        ${itemC.nameF} ${itemC.nameM} ${itemC.nameL}
                    </td>
                    <td data-localize="info1095Table.generation" data-localize-location="scope">
                            ${itemC.nameGen}
                    </td>
                    <td data-localize="info1095Table.SSNOrTIN" data-localize-location="scope">
                            ${itemC.ssn}
                    </td>
                    <td data-localize="info1095Table.DOB" data-localize-location="scope">
                            ${itemC.dob}
                    </td>
                    <td data-localize="info1095Table.coverAllMonths" data-localize-location="scope">
                            <c:if test="${itemC.monAll == 'Y'}">
                                    <span class="print-check-disabled">
                                            <i class="fa fa-times"></i>
                                        </span>
                            </c:if>
                            <c:if test="${itemC.monAll=='N'}">
                                    <input class="checkBoxOld" type="checkbox" />
                            </c:if>
                    </td>
                    <td data-localize="info1095Table.Jan" data-localize-location="scope">
                            <c:if test="${itemC.mon01 == 'Y'}">
                                    <span class="print-check-disabled">
                                            <i class="fa fa-times"></i>
                                        </span>
                            </c:if>
                            <c:if test="${itemC.mon01=='N'}">
                                    <input class="checkBoxOld" type="checkbox" />
                            </c:if>
                    </td>
                    <td data-localize="info1095Table.Feb" data-localize-location="scope">
                            <c:if test="${itemC.mon02 == 'Y'}">
                                    <span class="print-check-disabled">
                                            <i class="fa fa-times"></i>
                                        </span>
                            </c:if>
                            <c:if test="${itemC.mon02=='N'}">
                                    <input class="checkBoxOld" type="checkbox" />
                            </c:if>
                    </td>
                    <td data-localize="info1095Table.Mar" data-localize-location="scope">
                            <c:if test="${itemC.mon03 == 'Y'}">
                                    <span class="print-check-disabled">
                                            <i class="fa fa-times"></i>
                                        </span>
                            </c:if>
                            <c:if test="${itemC.mon03=='N'}">
                                    <input class="checkBoxOld" type="checkbox" />
                            </c:if>
                    </td>
                    <td data-localize="info1095Table.Apr" data-localize-location="scope">
                            <c:if test="${itemC.mon04 == 'Y'}">
                                    <span class="print-check-disabled">
                                            <i class="fa fa-times"></i>
                                        </span>
                            </c:if>
                            <c:if test="${itemC.mon04=='N'}">
                                    <input class="checkBoxOld" type="checkbox" />
                            </c:if>
                    </td>
                    <td data-localize="info1095Table.May" data-localize-location="scope">
                            <c:if test="${itemC.mon05 == 'Y'}">
                                    <span class="print-check-disabled">
                                            <i class="fa fa-times"></i>
                                        </span>
                            </c:if>
                            <c:if test="${itemC.mon05=='N'}">
                                    <input class="checkBoxOld" type="checkbox" />
                            </c:if>
                    </td>
                    <td data-localize="info1095Table.Jun" data-localize-location="scope">
                            <c:if test="${itemC.mon6 == 'Y'}">
                                    <span class="print-check-disabled">
                                            <i class="fa fa-times"></i>
                                        </span>
                            </c:if>
                            <c:if test="${itemC.mon06=='N'}">
                                    <input class="checkBoxOld" type="checkbox" />
                            </c:if>
                    </td>
                    <td data-localize="info1095Table.Jul" data-localize-location="scope">
                            <c:if test="${itemC.mon07 == 'Y'}">
                                    <span class="print-check-disabled">
                                            <i class="fa fa-times"></i>
                                        </span>
                            </c:if>
                            <c:if test="${itemC.mon07=='N'}">
                                    <input class="checkBoxOld" type="checkbox" />
                            </c:if>
                    </td>
                    <td data-localize="info1095Table.Aug" data-localize-location="scope">
                            <c:if test="${itemC.mon08 == 'Y'}">
                                    <span class="print-check-disabled">
                                            <i class="fa fa-times"></i>
                                        </span>
                            </c:if>
                            <c:if test="${itemC.mon08=='N'}">
                                    <input class="checkBoxOld" type="checkbox" />
                            </c:if>
                    </td>
                    <td data-localize="info1095Table.Sep" data-localize-location="scope">
                        <c:if test="${itemC.mon09 == 'Y'}">
                            <span class="print-check-disabled">
                                    <i class="fa fa-times"></i>
                                </span>
                    </c:if>
                    <c:if test="${itemC.mon09=='N'}">
                            <input class="checkBoxOld" type="checkbox" />
                    </c:if>
                    </td>
                    <td data-localize="info1095Table.Oct" data-localize-location="scope">
                        <c:if test="${itemC.mon10 == 'Y'}">
                            <span class="print-check-disabled">
                                    <i class="fa fa-times"></i>
                                </span>
                    </c:if>
                    <c:if test="${itemC.mon10=='N'}">
                            <input class="checkBoxOld" type="checkbox" />
                    </c:if>
                    </td>
                    <td data-localize="info1095Table.Nov" data-localize-location="scope">
                        <c:if test="${itemC.mon11 == 'Y'}">
                            <span class="print-check-disabled">
                                    <i class="fa fa-times"></i>
                                </span>
                    </c:if>
                    <c:if test="${itemC.mon11=='N'}">
                            <input class="checkBoxOld" type="checkbox" />
                    </c:if>
                    </td>
                    <td data-localize="info1095Table.Dec" data-localize-location="scope">
                        <c:if test="${itemC.mon12 == 'Y'}">
                            <span class="print-check-disabled">
                                    <i class="fa fa-times"></i>
                                </span>
                    </c:if>
                    <c:if test="${itemC.mon12=='N'}">
                            <input class="checkBoxOld" type="checkbox" />
                    </c:if>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                        <td colspan="17">
                                <div class="pageGroup">
                                        <button class="pageBtn firstPate" title="" data-localize="label.firstPage" onclick="changePage(1)"
                                                <c:if test="${ CPageNo ==1 }">disabled</c:if>>
                                                <i class="fa fa-angle-double-left "></i>
                                        </button>  
                                        <button class="pageBtn prevPage" title="" data-localize="label.prevPage" onclick="changePage(${CPageNo - 1})"
                                                <c:if test="${ CPageNo ==1  }">disabled</c:if>>
                                                <i class="fa fa-angle-left "></i>
                                        </button>
                                        <select class="selectPage" name="page" id="pageNow" title="" data-localize="label.choosePage" onchange="changePage()">
                                                <c:forEach  var="page"  begin="1" end="${CTotal}">
                                                <option value="${page}" <c:if test="${page == CPageNo }">selected</c:if>>${page}</option>
                                                </c:forEach>
                                        </select>
                                        <div class="page-list">
                                                <span class="slash">/</span>
                                                <span class="totalPate">${CTotal}</span>
                                        </div>
                                        <button class="pageBtn nextPate" title="" data-localize="label.nextPage"  onclick="changePage(${CPageNo + 1})"
                                                <c:if test="${CPageNo == CTotal  }">disabled</c:if>>
                                                        <i class="fa fa-angle-right "></i>
                                        </button>
                                        <button class="pageBtn lastPate" title="" data-localize="label.lastPage"  onclick="changePage(${CTotal})"
                                                <c:if test="${CPageNo == CTotal   }">disabled</c:if>>
                                                        <i class="fa fa-angle-double-right"></i>
                                        </button>
                                </div>
                        </td>
                </tr>
        </c:if>
    </tbody>
</table>
<div class="PageNext"></div>
<jsp:include page="../report-1095/1095C-${selectedYear}.jsp"></jsp:include>
<form id="changePageForm" hidden="hidden" action="sortOrChangePageForTypeC" method="POST">
        <input type="text" name="year" id="yearNow" value="${selectedYear}">
        <input type="text" name="CPageNo" id="selectPageNow">
</form>
<script>
function changePage(page){
        if(page){
                $("#selectPageNow").val(page)
        }else{
                let pageNow = $("#pageNow").val();
                $("#selectPageNow").val(pageNow)

        }
        $("#changePageForm")[0].submit()
        
}
</script>