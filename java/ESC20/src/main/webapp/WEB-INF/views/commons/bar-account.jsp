<button id="skipNav">${sessionScope.languageJSON.label.skipNav}</button>
<div class="account-header">
    <a href="/<%=request.getContextPath().split("/")[1]%>/login" role="link">
            <img class="logo logo-lg" src="/<%=request.getContextPath().split("/")[1]%>/images/logo.png" alt="${sessionScope.languageJSON.logoName.esc}"/>
            <img class="logo logo-sm" src="/<%=request.getContextPath().split("/")[1]%>/images/logo-account.png" alt="${sessionScope.languageJSON.logoName.esc}"/>
    </a>
    <div class="header-btn">
        <a href="/<%=request.getContextPath().split("/")[1]%>/login" role="link">
                <button type="submit" role="submitButton" class="btn btn-primary">
                	${sessionScope.languageJSON.label.returnLogin}
                </button>
        </a>
    </div>
    
    <div class="global-wrap">
            <%@ include file="global-select.jsp"%>
    </div>
    <a class="a-line help" href="https://tcc-help.net/txeis/employeeaccess/doku.php" target="_blank" role="link"
          aria-label="${sessionScope.languageJSON.accessHint.goHelp}" title="${sessionScope.languageJSON.accessHint.goHelp}">
		<i class="fa fa-question-circle"></i>
    </a>
</div>