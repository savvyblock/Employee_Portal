<div class="account-header">
    <a href="/<%=request.getContextPath().split("/")[1]%>/">
            <img class="logo logo-lg" src="/<%=request.getContextPath().split("/")[1]%>/images/logo.png" alt="esc logo" data-localize="logoName.esc"/>
            <img class="logo logo-sm" src="/<%=request.getContextPath().split("/")[1]%>/images/logo-account.png" alt="esc logo" data-localize="logoName.esc"/>
    </a>
    <div class="header-btn">
        <a href="/<%=request.getContextPath().split("/")[1]%>/">
                <button type="submit" class="btn btn-primary"  data-localize="label.login">
                    </button>
        </a>
    </div>
    <div class="global-wrap">
            <%@ include file="global-select.jsp"%>
    </div>
</div>