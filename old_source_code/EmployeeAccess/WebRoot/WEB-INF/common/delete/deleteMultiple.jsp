<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="employeeaccess" uri="http://www.esc20.net/tags/employeeaccess-util"%>

<form:form method="post">
	<table class="layout">
		<tr>
			<td class="message_icon">
				<img src="/CommonWeb/images/message_box_question.gif" />
			</td>

			<td>
				<h1>Delete Row</h1>
				<p>Are you sure you want to delete the selected rows?</p>

				<table class="button_layout">
					<tr>
						<td>
							<employeeaccess:button id="okButton" event="ok" 
								tabindex="1" type="tab" formid="mainForm"
								label="Yes" checkunsaved="false" cssClass="default" />
						</td>
						<td>
							<employeeaccess:button id="cancelButton" event="cancel" 
								tabindex="1" type="tab" formid="mainForm"
								label="No" checkunsaved="false" cssClass="default" />
						</td>
					</tr>
				</table>

			</td>
		</tr>
	</table>



</form:form>