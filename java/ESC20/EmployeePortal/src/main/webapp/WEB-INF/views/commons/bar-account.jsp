<button id="skipNav">${sessionScope.languageJSON.label.skipNav}</button>
<div class="account-header">
    <a href="/<%=request.getContextPath().split("/")[1]%>/login" role="link">
            <img class="logo logo-lg" src="/<%=request.getContextPath().split("/")[1]%>/images/logo.png" alt="${sessionScope.languageJSON.logoName.esc}"/>
            <img class="logo logo-sm" src="/<%=request.getContextPath().split("/")[1]%>/images/logo-account.png" alt="${sessionScope.languageJSON.logoName.esc}"/>
    </a>
    <div class="header-btn">
        <a class="btn btn-primary" href="/<%=request.getContextPath().split("/")[1]%>/login?distid=${sessionScope.districtId}" role="link">
                	${sessionScope.languageJSON.label.returnLogin}
        </a>
    </div>
    
    <div class="global-wrap">
            <%@ include file="global-select.jsp"%>
    </div>

    <a class=" help helpLink" href="https://help.ascendertx.com/employeeportal/doku.php" target="_blank" role="link"
   	      aria-label="${sessionScope.languageJSON.accessHint.goHelp}" title="${sessionScope.languageJSON.accessHint.goHelp}">
   	 	Help &nbsp;<i class="fa fa-question-circle"></i>
   	</a>
   	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		
	<a 	style="line-height:0; " 
		title="Facebook"
		aria-label="Facebook" 
		class="link-on-login-header link-color" 
		href="https://www.facebook.com/Ascendertx/" 
		target="_blank"><i class="fa fa-facebook"></i>
	</a> 	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;				

	<a 	style="line-height:0; " 
		title="Twitter"
		aria-label="Twitter" 
		class="link-on-login-header link-color" 
		href="https://twitter.com/ascendertx"
		target="_blank"><i class="fa fa-twitter"></i>
	</a>	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;					

	<a 	style="line-height:0;" 
		title="Linkedin"
		aria-label="Linkedin" 
		class="link-on-login-header link-color" 
		href="https://www.linkedin.com/company/ascendertx/"
		target="_blank"><i class="fa fa-linkedin"></i>
	</a>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;


</div>