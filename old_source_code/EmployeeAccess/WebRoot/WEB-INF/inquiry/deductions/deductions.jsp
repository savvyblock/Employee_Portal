<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ea" uri="http://www.esc20.net/tags/employeeaccess-util"%>

<div style="padding: 10px 10px 10px 10px">
	<c:if test="${not empty message}">
		<span style="color:red"><ea:label value="${message}"/></span><br/>
		<br/>
	</c:if>
	<c:if test="${empty frequencies}">
		<div class="error">No Deductions information is available.</div>
	</c:if>
	<c:if test="${not empty frequencies}">
		<c:forEach items="${frequencies}" var="frequency">
		<div class="groupBox">
			<div class="groupTitle">
				Frequency: <ea:label value="${frequency}"/>
			</div>
			<div class="groupContent" style="width:710px;">
				Marital Status: <ea:label value="${payInfo[frequency].maritalStatus.displayLabel}"/><br/>
				Number of Exemptions: <ea:label value="${payInfo[frequency].numberOfExemptions}"/><br/>
				<br/>
				<ea:Table height="230px;" width="700px;" tabindex="1" showNumberRows="false">
					<ea:Header title="Deduction<br/>Code" ignoreHtmlEscape="true" cssStyle="border-right:1px solid white;"/>
					<ea:Header title="Description" cssStyle="width:300px;border-right:1px solid white;" />
					<ea:Header title="Amount" cssStyle="border-right:1px solid white;text-align:center"/>
					<ea:Header title="Cafeteria<br/>Flag" ignoreHtmlEscape="true" cssStyle="border-right:1px solid white;"/>
					<ea:Header title="Employer<br/>Contribution<br/>Amount" ignoreHtmlEscape="true"/>
					<c:forEach items="${deductions[frequency]}" var="deduct" varStatus="counter">
						<ea:Row id="${counter.index}">
							<ea:Cell value="${deduct.code.code}"/>
							<ea:Cell value="${deduct.code.description}" cssStyle="text-align:left"/>
							<ea:Cell value="${deduct.amount}" cssStyle="text-align:right"/>
							<ea:Cell value="${deduct.cafeteria}" cssStyle="text-align:center" type="yesno"/>
							<ea:Cell value="${deduct.employerContributionAmount}" cssStyle="text-align:right"/>
						</ea:Row>
					</c:forEach>
				</ea:Table>
			</div>
		</div>
		<br/>
		</c:forEach>
	</c:if>
</div>