<footer id="mainFooter" class="main-footer">
        <div style="padding-left: 3.5%; font-size: 12px;margin-bottom:10px">
                <c:out  value="County/District: ${districtId}"> </c:out>
        </div>
        <div class="footer-word" style="margin-bottom:10px">
                <span>${sessionScope.constantJSON.label.copyRight}</span> 2020
                <a class="ascender" href="#"><span>${sessionScope.constantJSON.label.ASCENDER}</span></a>
                <span>${sessionScope.constantJSON.label.allRightReserved}</span>
        </div>
      
        <div class="footer-help">
                <a class="helpLink" href="" target="_blank" aria-label="${sessionScope.constantJSON.accessHint.goHelp}"
                        title="${sessionScope.constantJSON.accessHint.goHelp}">
                        Help <i class="fa fa-question-circle"></i>
                </a>
        </div>
</footer>