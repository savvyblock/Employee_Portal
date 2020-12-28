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
    
	<div class="langSelect">
		<ul class="nav navbar-nav langSelectInner">
			<!--AUS-13 add language dropdown-->
			<li class="dropdown">
				<a
					href="javascript:void(0);"
					data-toggle="dropdown"
					class="dropdown-toggle tab-click"
					id="globalSet2"
					tabindex="0"
					aria-label="${sessionScope.languageJSON.label.chooseLanguage}"
				>
					<span>${sessionScope.languageJSON.language.english}</span>
					&nbsp;
				</a>
				<ul class="dropdown-menu" role="menu" aria-labelledby="globalSet2">
					<li>
						<a
							title=""
							href=""
							onclick="showText('en')"
						>
						${sessionScope.languageJSON.language.english}
						</a>
					</li>
					<li>
						<a
							title=""
							href=""
							onclick="showText('es')"
						>
						${sessionScope.languageJSON.language.spanish}
						</a>
					</li>
				</ul>
			</li>
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