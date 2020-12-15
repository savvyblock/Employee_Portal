<!--ALC-13 UI Alignment of ASCENDER and portals-->
<button id="skipNav">${sessionScope.languageJSON.label.skipNav}</button>
<div class="account-header">
    <a href="/<%=request.getContextPath().split("/")[1]%>/login" role="link">
            <img class="logo logo-lg" src="<spring:theme code="commonBase"/>images/EmployeePortal/ascender_logo.png" alt="${sessionScope.languageJSON.logoName.esc}"/>
            <img class="logo logo-sm" src="/<%=request.getContextPath().split("/")[1]%>/images/logo-account.png" alt="${sessionScope.languageJSON.logoName.esc}"/>
    </a>
    <div class="header-btn">
        <a class="btn btn-primary" href="/<%=request.getContextPath().split("/")[1]%>/login?distid=${sessionScope.districtId}" role="link">
                	${sessionScope.languageJSON.label.returnLogin}
        </a>
    </div>
    
    <div class="global-wrap">
            <%@ include file="global-select.jsp"%>
	</div>&nbsp;&nbsp;&nbsp;&nbsp;
	<div class="langSelect">
		<ul class="nav navbar-nav langSelectInner">
            <li>
				<a class=" help helpLink" href="https://tcc-help.net/txeis/employeeaccess/doku.php" target="_blank" role="link"
				aria-label="${sessionScope.languageJSON.accessHint.goHelp}" title="${sessionScope.languageJSON.accessHint.goHelp}">
				<span>${sessionScope.languageJSON.nav.help}</span>&nbsp;<i class="fa fa-question-circle"></i>
		        </a>
			</li>
			<li>
				<a 
				title="Facebook"
				aria-label="Facebook" 
				class="link-on-login-header link-color helpLink" 
				href="https://www.facebook.com/Ascendertx/" 
				target="_blank"><i class="fa fa-facebook-official"></i>
			</a> 
			</li>
			<li>
				<a 
					title="Twitter"
					aria-label="Twitter" 
					class="link-on-login-header link-color helpLink" 
					href="https://twitter.com/ascendertx"
					target="_blank"><i class="fa fa-twitter"></i>
				</a>	
			</li>
			<li>
				<a 	
					title="Linkedin"
					aria-label="Linkedin" 
					class="link-on-login-header link-color helpLink" 
					href="https://www.linkedin.com/company/ascendertx/"
					target="_blank"><i class="fa fa-linkedin-square"></i>
				</a>
			</li>
		</ul>

	</div>
</div>