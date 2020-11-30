<table class="table responsive-table mt-3 table-striped">
    <thead>
        <tr>
                <th>${sessionScope.languageJSON.leaveBalance.leaveType}</th>
                <th class="text-right">${sessionScope.languageJSON.leaveBalance.beginningBalance}</th>
                <th class="text-right">${sessionScope.languageJSON.leaveBalance.advancedEarned}</th>
                <th class="text-right">${sessionScope.languageJSON.leaveBalance.pendingEarned}</th>
                <th class="text-right">${sessionScope.languageJSON.leaveBalance.used}</th>
                <th class="text-right">${sessionScope.languageJSON.leaveBalance.pendingUsed}</th>
                <th class="text-right">${sessionScope.languageJSON.leaveBalance.available}</th>
                <th class="text-left">${sessionScope.languageJSON.leaveBalance.units}</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="leave" items="${leaveInfo}" varStatus="count">
            <tr>
                <td data-title="${sessionScope.languageJSON.leaveBalance.leaveType}">
                        ${leave.type.description}
                </td>
                <td class="text-right" data-title="${sessionScope.languageJSON.leaveBalance.beginningBalance}">${leave.beginBalanceLabel}</td>
                <td class="text-right" data-title="${sessionScope.languageJSON.leaveBalance.advancedEarned}">${leave.advancedEarnedLabel}</td>
                <td class="text-right" data-title="${sessionScope.languageJSON.leaveBalance.pendingEarned}">${leave.pendingEarnedLabel}</td>
                <td class="text-right" data-title="${sessionScope.languageJSON.leaveBalance.used}">${leave.usedLabel}</td>
                <td class="text-right" data-title="${sessionScope.languageJSON.leaveBalance.pendingUsed}">${leave.totalPendingUsedLabel}</td>
                <td class="text-right" data-title="${sessionScope.languageJSON.leaveBalance.available}">
                    <span id="available${leave.type.code}">${leave.availableBalanceLabel}</span>
                </td>
                <td class="text-left" data-title="${sessionScope.languageJSON.leaveBalance.units}">
                        <c:choose> 
                            <c:when test="${leave.daysHrs == 'D'}">   
                                <span>${sessionScope.languageJSON.label.days}</span>
                            </c:when> 
                            <c:when test="${leave.daysHrs == 'H'}">   
                                <span>${sessionScope.languageJSON.label.hours}</span>
                            </c:when> 
                            <c:otherwise>   
                                <span>${sessionScope.languageJSON.label.days}</span>
                            </c:otherwise> 
                        </c:choose> 
                </td>
                <td class="hidden" data-title="${sessionScope.languageJSON.leaveBalance.postAgainstZeroBalanceValue}">
                    <span id="postAgnstZeroBal${leave.type.code}">${leave.postAgainstZeroBalanceValue}</span>
                </td>
                     
            </tr>
        </c:forEach>
    </tbody>
</table>