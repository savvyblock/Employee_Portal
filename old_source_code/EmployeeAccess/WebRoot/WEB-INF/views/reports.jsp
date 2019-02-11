<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="employeeaccess" uri="http://www.esc20.net/tags/employeeaccess-util"%>

<html>
<head>
	<title>Employee Access Reports</title>

</head>

<body navReports="active" breadcrumb="<c:out value='${breadcrumb}'/>" windowid="<c:out value='${windowId}'/>" helpPath="employeeaccess/reports/employeeaccessreports">
	<table class="tabs">
		<tr>
			<td colspan="2" style="height: 10px" class="top">
				<table class="tab_list">
					<tr>
						<employeeaccess:tab id="reportsTab" href="#" name="Employee Access Reports" active="true" leftMost="true" hoverGroup="tab0" tabGroup="maintabs" />
					</tr>
				</table>
			</td>
			<td class="top_right" style="height: 10px;"></td>
		</tr>
		<tr>
			<td class="left" />
			<td class="center">
				<div class="tab_dimensions">
					<div class="mainDiv" style="line-height: 2em;">
						<ul>
							<c:forEach var="report" items="${reports}">
								<li class="hover_style_target">
									<a href="${reportsParameterUrl}?report=<c:out value="${report.id}" />" >
										<c:out value="${report.id}" /> - <c:out value="${report.title}" />
									</a>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</td>
			<td class="right" />
		</tr>
		<tr>
			<td class="bottom_left" />
			<td class="bottom" />
			<td class="bottom_right" />
		</tr>
	</table>
</body>
</html>