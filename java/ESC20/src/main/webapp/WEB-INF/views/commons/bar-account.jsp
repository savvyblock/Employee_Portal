<button id="skipNav" data-localize="label.skipNav"></button>
<div class="account-header">
    <a href="/<%=request.getContextPath().split("/")[1]%>/login" role="link">
            <img class="logo logo-lg" src="/<%=request.getContextPath().split("/")[1]%>/images/logo.png" alt="" data-localize="logoName.esc"/>
            <img class="logo logo-sm" src="/<%=request.getContextPath().split("/")[1]%>/images/logo-account.png" alt="" data-localize="logoName.esc"/>
    </a>
    <div class="header-btn">
        <a href="/<%=request.getContextPath().split("/")[1]%>/login" role="link">
                <button type="submit" role="submitButton" class="btn btn-primary"  data-localize="label.returnLogin">
                    </button>
        </a>
    </div>
    
    <div class="global-wrap">
            <%@ include file="global-select.jsp"%>
    </div>
    <a class="a-line help" href="https://tcc-help.net/txeis/employeeaccess/doku.php" target="_blank" role="link"
          aria-label="" data-localize="accessHint.goHelp" data-localize-location="aria-label,title" data-localize-notText="true"
          >
          <i class="fa fa-question-circle"></i>
        </a>
</div>