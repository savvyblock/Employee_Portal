<c:if test="${sessionScope.companyId == 0}">
        <a class="nav-logo" href="/<%=request.getContextPath().split("/")[1]%>/home">
          <img src="/<%=request.getContextPath().split("/")[1]%>/images/logo.png" alt="esc logo" data-localize="logoName.esc">
        </a>
    </c:if>
    <c:if test="${sessionScope.companyId == 1}">
        <a class="nav-logo" href="/<%=request.getContextPath().split("/")[1]%>/home">
          <img src="/<%=request.getContextPath().split("/")[1]%>/images/logo-austin.png" alt="austin school logo" data-localize="logoName.esc">
        </a>
    </c:if>
    <c:if test="${sessionScope.companyId == 2}">
        <a class="nav-logo" href="/<%=request.getContextPath().split("/")[1]%>/home">
          <img src="/<%=request.getContextPath().split("/")[1]%>/images/logo-saisd.png" alt="sanantonio logo" data-localize="logoName.esc">
        </a>
    </c:if>