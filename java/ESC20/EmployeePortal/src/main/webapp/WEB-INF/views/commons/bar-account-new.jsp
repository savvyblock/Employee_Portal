<!--ALC-13 UI Alignment of ASCENDER and portals-->
<button id="skipNav">${sessionScope.languageJSON.label.skipNav}</button>
<div class="account-header">
    <a href="/<%=request.getContextPath().split("/")[1]%>/login" role="link">
            <img class="logo logo-lg" src="<spring:theme code="commonBase"/>images/EmployeePortal/EmployeePortalLogo.png" alt="${sessionScope.languageJSON.logoName.esc}"/>
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
							title="en"
							href=""
							onclick="showText('en')"
						>
						${sessionScope.languageJSON.language.english}
						</a>
					</li>
					<li>
						<a
							title="es"
							href=""
							onclick="showText('es')"
						>
						${sessionScope.languageJSON.language.spanish}
						</a>
					</li>
				</ul>
			</li>
            <li>
			<%--<a class=" help helpLink" href="https://tcc-help.net/txeis/employeeaccess/doku.php" target="_blank" role="link"
				aria-label="${sessionScope.languageJSON.accessHint.goHelp}" title="${sessionScope.languageJSON.accessHint.goHelp}">
				<span>${sessionScope.languageJSON.nav.help}</span>&nbsp;<i class="fa fa-question-circle"></i>
		        </a>
		    --%>
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
		    </li>
			<li>
				<a 
				title="Facebook"
				aria-label="Facebook" 
				class="link-on-login-header link-color social-media-icon" 
				href="https://www.facebook.com/Ascendertx/" 
				target="_blank"><i class="fa fa-facebook-official"></i>
			</a> 
			</li>
			<li>
				<a 
					title="Twitter"
					aria-label="Twitter" 
					class="link-on-login-header link-color social-media-icon" 
					href="https://twitter.com/ascendertx"
					target="_blank"><i class="fa fa-twitter"></i>
				</a>	
			</li>
			<li>
				<a 	
					title="LinkedIn"
					aria-label="LinkedIn" 
					class="link-on-login-header link-color social-media-icon" 
					href="https://www.linkedin.com/company/ascendertx/"
					target="_blank"><i class="fa fa-linkedin-square"></i>
				</a>
			</li>
		</ul>

	</div>
</div>