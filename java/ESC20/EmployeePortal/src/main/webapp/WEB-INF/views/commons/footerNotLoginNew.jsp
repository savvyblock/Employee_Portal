<%@ page import="com.esc20.util.ManifestUtil" %>
<%
 String version = ManifestUtil.getVersionInfo(request.getServletContext());
%>
<!--ALC-13 UI Alignment of ASCENDER and portals-->
<footer class="login-footer no-print">
	<div class="row">
		<div class="col-md-8">
			<p class="text-left">
				<span>${sessionScope.languageJSON.login.authorizedText}</span><br>
				<span>${sessionScope.languageJSON.login.footerCopyright}</span><br>
				<!-- <span data-localize="login.footerAllCopyright"></span><br>
				<span data-localize="login.versionLabel"></span><span data-localize="login.version"></span><span class="hide" id="lastNumberSpan" data-localize="login.versionLastNumber"></span><span class="hide" id="timeStampSpan"><%=version %></span> -->
			</p>
		</div>
		<div class="col-md-4">
			<div class="text-right">
				<p>
				  <a href="javascript:void(0);" id="AccessibilityLink">${sessionScope.languageJSON.login.titleTccAccessibilityStatement}</a>  
				  <br/>
				  <span>${sessionScope.languageJSON.login.browserReuirements}
				  </span>
				  <a href="https://www.mozilla.org" title="" >
					<i class="fa fa-firefox link-color"></i>
				  </a>
				  <a href="https://www.google.com/chrome/" title=""  >
					<i class="fa fa-chrome link-color"></i>
				  </a>
				  <a href="https://www.apple.com/safari/" title="" >
					<i class="fa fa-safari link-color"></i>
				  </a>
				</p>
				<!--ALC-13 hide modal When you go to the page-->
				<div id="AccessibilityModal" class="modalAccessibility" style="display: none;">
					<div class="modalAccessibility-content">
					  <p>
						  <span class="close">&times;</span>
					  </p>
					  <p >
						  <span>${sessionScope.languageJSON.login.textTccAccessibilityStatement1}</span>
						  <a href="https://www.w3.org/TR/WCAG20/" target="_blank">${sessionScope.languageJSON.login.textTccAccessibilityStatement2}</a>
						  <span>${sessionScope.languageJSON.login.textTccAccessibilityStatement3} </span>
						  <a  href="https://docs.google.com/forms/d/e/1FAIpQLScVEpUzBsCM1XLzRVieEoJAaFWRZoPEmUU2fZcWz2TyDTsb7g/viewform?entry.372715739=txConnect" target="_blank" role="link" aria-label="" data-localize="login.goToContact"  data-localize-notText="true">
							  <span>${sessionScope.languageJSON.login.textTccAccessibilityStatement4}</span></a>.
					  </p>
					</div>
				 
				</div>
			</div>
  
		</div>
	  </div>
</footer>
