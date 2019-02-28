<button id="skipNav" data-localize="label.skipNav"></button>
<div class="account-header">
    <a href="/<%=request.getContextPath().split("/")[1]%>/index">
            <img class="logo logo-lg" src="/<%=request.getContextPath().split("/")[1]%>/images/logo.png" alt="" data-localize="logoName.esc"/>
            <img class="logo logo-sm" src="/<%=request.getContextPath().split("/")[1]%>/images/logo-account.png" alt="" data-localize="logoName.esc"/>
    </a>
    <div class="header-btn">
        <a href="/<%=request.getContextPath().split("/")[1]%>/index">
                <button type="submit" class="btn btn-primary"  data-localize="label.login">
                    </button>
        </a>
    </div>
    <div class="global-wrap">
            <%@ include file="global-select.jsp"%>
    </div>
</div>