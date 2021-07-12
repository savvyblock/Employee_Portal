<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%-- <c:if test="${!acceptModel}"> --%>
<%
	if (request.getSession().getAttribute("isLicense") == "N") {
%>
<!-- ALC-9 -->
<link href="https://fonts.googleapis.com/css?family=Nunito:400,700" rel="stylesheet">
<div class="modal show" id="agreementModal" tabindex="-1" role="dialog" style="display:block"  data-backdrop="static">
    <div class="modal-dialog modal-md">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">
                      ${sessionScope.languageJSON.createAccount.licenseAgreement}
                </h4>
              </div>
            <div class="modal-body">
                <div id="licenseWrap"></div>
               <input type="hidden" id="useValue" name="useValue" value="${useValue}" />
            </div>
            <div class="flexInline" style="justify-content: flex-end;">
						<div>
							<label class="flexInline"> <input type="radio" name="agreeTermForPopup" value="Y"> <span>${sessionScope.languageJSON.createAccount.accept}</span></label>
						</div>
						<div>
							<label class="flexInline"> <input type="radio" name="agreeTermForPopup" checked="checked" value="N"><span>${sessionScope.languageJSON.createAccount.notAccept}</span></label>
						</div>
						<div class="error-hint agreeError" id="agreeErrorForPopup" style="display: none;"><span>${sessionScope.languageJSON.createAccount.licenseWarning}</span></div>
					</div>
            <input type="hidden" id="isAdmin" value="${sessionScope.isAdmin}" />
            <div class="modal-footer">
                <button id="submitBtn" type="button" class="btn btn-primary inlineBlock ui-button" data-dismiss="modal" data-localize="button.submit">${sessionScope.languageJSON.buttons.submit}</button>
            </div>
            <form action="/<%=request.getContextPath().split("/")[1]%>/logoutEA" method="post" id="logoutForm">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                     <input type="submit" style="display:none" value="submit"/>
            </form>
        </div>
    </div>
  </div>
  <%
	}
%>

 <script src="<c:url value="/js/agreementModal.js" />"></script>
 <script>
  var licenseAPIPortal = '/EmployeePortal/getLicenseContent'
 </script>
 <script type="text/javascript" language="JavaScript" charset="utf-8" src="<spring:theme code="commonBase"/>scripts/license.js"></script>