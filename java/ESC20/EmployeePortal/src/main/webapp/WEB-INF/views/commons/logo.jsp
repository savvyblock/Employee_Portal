     <a class="nav-logo" href="/<%=request.getContextPath().split("/")[1]%>/home">
         <img src="<spring:theme code="commonBase"/>images/EmployeePortal/EmployeePortalLogo.png" alt="${sessionScope.languageJSON.logoName.esc}"/>
     </a>


<%-- <c:if test="${sessionScope.companyId == '001904'}">
        <a class="nav-logo" href="/<%=request.getContextPath().split("/")[1]%>/home">
          <img src="/<%=request.getContextPath().split("/")[1]%>/images/logo.png" alt="${sessionScope.languageJSON.logoName.esc}"/>
        </a>
    </c:if>
    <c:if test="${sessionScope.companyId == '123456'}">
        <a class="nav-logo" href="/<%=request.getContextPath().split("/")[1]%>/home">
          <img src="/<%=request.getContextPath().split("/")[1]%>/images/logo-austin.png" alt="${sessionScope.languageJSON.logoName.esc}"/>
        </a>
    </c:if>
    <c:if test="${sessionScope.companyId == 2}">
        <a class="nav-logo" href="/<%=request.getContextPath().split("/")[1]%>/home">
          <img src="/<%=request.getContextPath().split("/")[1]%>/images/logo-saisd.png" alt="${sessionScope.languageJSON.logoName.esc}"/>
        </a>
    </c:if> --%>