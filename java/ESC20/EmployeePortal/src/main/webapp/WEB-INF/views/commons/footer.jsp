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
               <a
		            id="helpLink"
		            class="helpLink"
		            href="javascript:void(0);"
		            aria-label=""
		            title=""
		            data-localize="nav.help"
		            data-localize-notText="true"
		            tabindex="0"
		            target="_blank"
		        >Help&nbsp;
		            <i class="fa fa-question-circle" aria-hidden="true"></i>
		            <span class="sr-only" title="" data-localize="login.help">
		                Help
		            </span>
		        </a>
		       &nbsp;&nbsp;&nbsp;		
        </div>
 
</footer>