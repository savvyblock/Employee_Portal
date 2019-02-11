<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style type="text/css">
<!--
.header {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 10pt;
	font-weight: bolder;
	text-align: left
}

.footer {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 10pt;
	font-weight: bolder;
	text-align: left
}

.footer-left {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 10pt;
	font-weight: bolder;
	text-align: left;
	float: left
}

.footer-right {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 7pt;
	font-weight: bolder;
	text-align: right;
	float: right
}

.blocktitle {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 10pt;
	white-space: nowrap;
	overflow: hidden;
	text-align: left
}

.boxtitle {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 10pt;
	font-weight: bolder;
	border: none;
	white-space: nowrap;
	overflow: visible;
	text-align: left;
}

.boxvalue {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 10pt;
	padding-left: 3pt;
	border: none;
	white-space: nowrap;
	overflow: visible;
	text-align: left
}

.boxvaluerj {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 10pt;
	padding-left: 3pt;
	border: none;
	white-space: nowrap;
	overflow: visible;
	text-align: right
}

.boximage {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 10pt;
	padding-left: 5pt;
	border: none;
	white-space: nowrap;
	overflow: visible;
	text-align: center
}

.wide {
	width: 3.6in
}

.thin {
	width: 1.8in
}

.singleborder {
	border: solid #000000 1pt;
	padding-left: 2pt;
	vertical-align: top
}

.doubleborder {
	border: solid #000000 3pt;
	padding-left: 2pt;
	vertical-align: top
}

.pad {
	padding-left: 5pt
}
-->
</style>

<div style="width: 100%" align="center">

	<!-- Copy B -->
	<div class="breakafter" align="center">
		<table cellpadding="0" cellspacing="0"
			style="width: 7.6; margin: 0 0 0 0;" align="center">

			<tr>
				<td class="header" colspan="3" valign="top">
					Form W-2 Wage and Tax Statement
				</td>
			</tr>
			<tr>
				<td class="singleborder">
					<div class="boxtitle wide">
						a Employee's social security number
					</div>
					<div class="boxvalue wide">
						<c:out value="${w2Print.ssn1}" />
						-
						<c:out value="${w2Print.ssn2}" />
						-
						<c:out value="${w2Print.ssn3}" />
					</div>
					<div class="boxtitle wide">
						&nbsp;
					</div>
				</td>
				<td class="doubleborder">
					<div class="boxtitle thin">
						1 Wages, Tips other compensation
					</div>
					<div class="boxvalue thin">
						<c:out value="${w2Print.tgross}" />
					</div>
					<div class="boxtitle thin">
						&nbsp;
					</div>
				</td>
				<td class="doubleborder">
					<div class="boxtitle thin">
						2 Federal income tax withheld
					</div>
					<div class="boxvalue thin">
						<c:out value="${w2Print.whold}" />
					</div>
					<div class="boxtitle thin">
						&nbsp;
					</div>
				</td>
			</tr>
			<tr>
				<td class="singleborder">
					<div class="boxtitle thin">
						b Employer identification number (EIN)
					</div>
					<div class="boxvalue thin">
						<c:out value="${w2Print.ein1}" />
						&nbsp;-&nbsp;
						<c:out value="${w2Print.ein2}" />
					</div>
					<div class="boxtitle wide">
						&nbsp;
					</div>
				</td>
				<td class="singleborder">
					<div class="boxtitle thin">
						3 Social security wages
					</div>
					<div class="boxvalue thin">
						<c:out value="${w2Print.fgross}" />
					</div>
					<div class="boxtitle thin">
						&nbsp;
					</div>
				</td>
				<td class="singleborder">
					<div class="boxtitle thin">
						4 Social security tax withheld
					</div>
					<div class="boxvalue thin">
						<c:out value="${w2Print.ftax}" />
					</div>
					<div class="boxtitle thin">
						&nbsp;
					</div>
				</td>
			</tr>
			<tr>
				<td rowspan="2" class="singleborder">
					<div class="boxtitle wide">
						c Employer's name, address and Zip code
					</div>
					<div class="boxvalue wide">
						<c:out value="${w2Print.ename}" />
					</div>
					<div class="boxvalue wide">
						<c:out value="${w2Print.eaddress}" />
					</div>
					<div class="boxvalue wide">
						<c:out value="${w2Print.ecityst}" />
					</div>
				</td>
				<td class="singleborder">
					<div class="boxtitle thin">
						5 Medicare wages and tips
					</div>
					<div class="boxvalue thin">
						<c:out value="${w2Print.mgross}" />
					</div>
					<div class="boxtitle thin">
						&nbsp;
					</div>
				</td>
				<td class="singleborder">
					<div class="boxtitle thin">
						6 Medicare tax withheld
					</div>
					<div class="boxvalue thin">
						<c:out value="${w2Print.mtax}" />
					</div>
					<div class="boxtitle thin">
						&nbsp;
					</div>
				</td>
			</tr>
			<tr>
				<td class="doubleborder">
					<div class="boxtitle">
						9 Advanced EIC payment
					</div>
					<div class="boxvalue">
						<c:out value="${w2Print.eic}" />
					</div>
					<div class="boxtitle thin">
						&nbsp;
					</div>
				</td>
				<td class="doubleborder">
					<div class="boxtitle thin">
						10 Dependent care benefits
					</div>
					<div class="boxvalue thin">
						<c:out value="${w2Print.dcare}" />
					</div>
					<div class="boxtitle thin">
						&nbsp;
					</div>
				</td>
			</tr>
			<tr>
				<td rowspan="2" class="singleborder">
					<div class="boxtitle wide">
						e Employee's name
					</div>
					<div class="boxvalue wide">
						<c:out value="${w2Print.empfname}" />
						&nbsp;
						<c:out value="${w2Print.emplname}" />
					</div>
					<div class="boxvalue wide">
						<c:out value="${w2Print.empaddress}" />
					</div>
					<div class="boxvalue wide">
						<c:out value="${w2Print.empcityst}" />
					</div>
				</td>
				<td class="singleborder">
					<div class="thin">
						<table cellpadding="0" cellspacing="0">
							<col
								style="text-align: left; padding-left: 2pt; padding-right: 3pt">
							<col style="text-align: right; padding-right: 3pt">
							<tr>
								<td class="boxtitle" colspan="2">
									12 See Instrs. for box 12
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1201}" />
									&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1201}" />
									&nbsp;
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1202}" />
									&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1202}" />
									&nbsp;
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1203}" />
									&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1203}" />
									&nbsp;
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1204}" />
									&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1204}" />
									&nbsp;
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1205}" />
									&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1205}" />
									&nbsp;
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1206}" />
									&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1206}" />
									&nbsp;
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1207}" />
									&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1207}" />
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
					<div class="boxtitle thin">
						&nbsp;
					</div>
				</td>
				<td class="singleborder">
					<div class="thin">
						<table cellpadding="0" cellspacing="0">
							<col
								style="text-align: left; padding-left: 2pt; padding-right: 3pt">
							<col style="text-align: right; padding-right: 3pt">
							<tr>
								<td class="boxtitle" colspan="2">
									14 See Instrs. for box 14
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1401}" />&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1401}" />&nbsp;
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1402}" />&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1402}" />&nbsp;
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1403}" />&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1403}" />&nbsp;
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1404}" />&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1404}" />&nbsp;
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1405}" />&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1405}" />&nbsp;
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1406}" />&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1406}" />&nbsp;
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1407}" />&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1407}" />&nbsp;
								</td>
							</tr>
						</table>
					</div>
					<div class="boxtitle thin">
						&nbsp;
					</div>
				</td>
			</tr>

			<tr>
				<td colspan="2" class="singleborder">
					<table cellpadding="3" cellspacing="0">
						<tr>
							<td valign="top">
								<div class="boxtitle">
									13
								</div>
							</td>
							<td valign="top">
								<div class="boxtitle">
									Statutory Employee
								</div>
								<div class="boximage">
									<c:if test="${w2Print.statemp1}">
									<img id="STATEMP_CHECKED_1"
										src="${pageContext.request.contextPath}/images/checkedbox.gif"  height="12"
										width="12" alt="">
									</c:if>
									<c:if test="${not w2Print.statemp1}">
									<img id="STATEMP_UNCHECKED_1"
										src="${pageContext.request.contextPath}/images/uncheckedbox.gif" height="12"
										width="12" alt="">
									</c:if>
								</div>
							</td>
							<td valign="top">
								<div class="boxtitle">
									Retirement Plan
								</div>
								<div class="boximage">
									<c:if test="${w2Print.retplan1}">
									<img id="RETPLAN_CHECKED_1"
										src="${pageContext.request.contextPath}/images/checkedbox.gif" height="12"
										width="12" alt="">
									</c:if>
									<c:if test="${not w2Print.retplan1}">
									<img id="RETPLAN_UNCHECKED_1"
										src="${pageContext.request.contextPath}/images/uncheckedbox.gif" height="12"
										width="12" alt="">
									</c:if>
								</div>
							</td>
							<td valign="top">
								<div class="boxtitle">
									Third party sick pay
								</div>
								<div class="boximage">
									<c:if test="${w2Print.thrdsick1}">
									<img id="THRDSICK_CHECKED_1"
										src="${pageContext.request.contextPath}/images/checkedbox.gif" height="12"
										width="12" alt="">
									</c:if>
									<c:if test="${not w2Print.thrdsick1}">
									<img id="THRDSICK_UNCHECKED_1"
										src="${pageContext.request.contextPath}/images/uncheckedbox.gif" height="12"
										width="12" alt="">
									</c:if>
								</div>
							</td>
						</tr>
					</table>
					<div class="boxtitle wide">
						&nbsp;
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="3" valign="top">
					<div class="footer-left">
						Copy B, To Be Filed With Employee's FEDERAL Tax Return
					</div>
					<div class="footer-right">
						Department of the Treasury - Internal Revenue Service
					</div>
				</td>
			</tr>
			<tr>
				<td class="footer" colspan="3" valign="top"
					style="text-align: center">
					<h1>
						2009
					</h1>
				</td>
			</tr>
		</table>
		<hr>
		<!-- Copy C -->

		<table cellpadding="0" cellspacing="0"
			style="width: 7.6; margin: 0 0 0 0;" align="center">

			<tr>
				<td class="header" colspan="3" valign="top">
					Form W-2 Wage and Tax Statement
				</td>
			</tr>
			<tr>
				<td class="singleborder">
					<div class="boxtitle wide">
						a Employee's social security number
					</div>
					<div class="boxvalue wide">
						<c:out value="${w2Print.ssn1}" />-<c:out value="${w2Print.ssn2}" />-<c:out value="${w2Print.ssn3}" />
					</div>
					<div class="boxtitle wide">
						&nbsp;
					</div>
				</td>
				<td class="doubleborder">
					<div class="boxtitle thin">
						1 Wages, Tips other compensation
					</div>
					<div class="boxvalue thin">
						<c:out value="${w2Print.tgross}" />
					</div>
					<div class="boxtitle thin">
						&nbsp;
					</div>
				</td>
				<td class="doubleborder">
					<div class="boxtitle thin">
						2 Federal income tax withheld
					</div>
					<div class="boxvalue thin">
						<c:out value="${w2Print.whold}" />
					</div>
					<div class="boxtitle thin">
						&nbsp;
					</div>
				</td>
			</tr>
			<tr>
				<td class="singleborder">
					<div class="boxtitle thin">
						b Employer identification number (EIN)
					</div>
					<div class="boxvalue thin">
						<c:out value="${w2Print.ein1}" />&nbsp;-&nbsp;<c:out value="${w2Print.ein2}" />
					</div>
					<div class="boxtitle wide">
						&nbsp;
					</div>
				</td>
				<td class="singleborder">
					<div class="boxtitle thin">
						3 Social security wages
					</div>
					<div class="boxvalue thin">
						<c:out value="${w2Print.fgross}" />
					</div>
					<div class="boxtitle thin">
						&nbsp;
					</div>
				</td>
				<td class="singleborder">
					<div class="boxtitle thin">
						4 Social security tax withheld
					</div>
					<div class="boxvalue thin">
						<c:out value="${w2Print.ftax}" />
					</div>
					<div class="boxtitle thin">
						&nbsp;
					</div>
				</td>
			</tr>
			<tr>
				<td rowspan="2" class="singleborder">
					<div class="boxtitle wide">
						c Employer's name, address and Zip code
					</div>
					<div class="boxvalue wide">
						<c:out value="${w2Print.ename}" />
					</div>
					<div class="boxvalue wide">
						<c:out value="${w2Print.eaddress}" />
					</div>
					<div class="boxvalue wide">
						<c:out value="${w2Print.ecityst}" />
					</div>
				</td>
				<td class="singleborder">
					<div class="boxtitle thin">
						5 Medicare wages and tips
					</div>
					<div class="boxvalue thin">
						<c:out value="${w2Print.mgross}" />
					</div>
					<div class="boxtitle thin">
						&nbsp;
					</div>
				</td>
				<td class="singleborder">
					<div class="boxtitle thin">
						6 Medicare tax withheld
					</div>
					<div class="boxvalue thin">
						<c:out value="${w2Print.mtax}" />
					</div>
					<div class="boxtitle thin">
						&nbsp;
					</div>
				</td>
			</tr>
			<tr>
				<td class="doubleborder">
					<div class="boxtitle">
						9 Advanced EIC payment
					</div>
					<div class="boxvalue">
						<c:out value="${w2Print.eic}" />
					</div>
					<div class="boxtitle thin">
						&nbsp;
					</div>
				</td>
				<td class="doubleborder">
					<div class="boxtitle thin">
						10 Dependent care benefits
					</div>
					<div class="boxvalue thin">
						<c:out value="${w2Print.dcare}" />
					</div>
					<div class="boxtitle thin">
						&nbsp;
					</div>
				</td>
			</tr>
			<tr>
				<td rowspan="2" class="singleborder">
					<div class="boxtitle wide">
						e Employee's name
					</div>
					<div class="boxvalue wide">
						<c:out value="${w2Print.empfname}" />&nbsp;<c:out value="${w2Print.emplname}" />
					</div>
					<div class="boxvalue wide">
						<c:out value="${w2Print.empaddress}" />
					</div>
					<div class="boxvalue wide">
						<c:out value="${w2Print.empcityst}" />
					</div>
				</td>
				<td class="singleborder">
					<div class="thin">
						<table cellpadding="0" cellspacing="0">
							<col
								style="text-align: left; padding-left: 2pt; padding-right: 3pt">
							<col style="text-align: right; padding-right: 3pt">
							<tr>
								<td class="boxtitle" colspan="2">
									12 See Instrs. for box 12
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1201}" />&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1201}" />&nbsp;
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1202}" />&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1202}" />&nbsp;
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1203}" />&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1203}" />&nbsp;
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1204}" />&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1204}" />&nbsp;
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1205}" />&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1205}" />&nbsp;
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1206}" />&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1206}" />&nbsp;
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1207}" />&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1207}" />&nbsp;
								</td>
							</tr>
						</table>
					</div>
					<div class="boxtitle thin">
						&nbsp;
					</div>
				</td>
				<td class="singleborder">
					<div class="thin">
						<table cellpadding="0" cellspacing="0">
							<col
								style="text-align: left; padding-left: 2pt; padding-right: 3pt">
							<col style="text-align: right; padding-right: 3pt">
							<tr>
								<td class="boxtitle" colspan="2">
									14 See Instrs. for box 14
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1401}" />&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1401}" />&nbsp;
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1402}" />&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1402}" />&nbsp;
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1403}" />&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1403}" />&nbsp;
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1404}" />&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1404}" />&nbsp;
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1405}" />&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1405}" />&nbsp;
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1406}" />&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1406}" />&nbsp;
								</td>
							</tr>
							<tr>
								<td class="boxvalue">
									<c:out value="${w2Print.code1407}" />&nbsp;
								</td>
								<td class="boxvaluerj">
									<c:out value="${w2Print.amt1407}" />&nbsp;
								</td>
							</tr>
						</table>
					</div>
					<div class="boxtitle thin">
						&nbsp;
					</div>
				</td>
			</tr>

			<tr>
				<td colspan="2" class="singleborder">
					<table cellpadding="3" cellspacing="0">
						<tr>
							<td valign="top">
								<div class="boxtitle">
									13
								</div>
							</td>
							<td valign="top">
								<div class="boxtitle">
									Statutory Employee
								</div>
								<div class="boximage">
									<c:if test="${w2Print.statemp2}">
									<img id="STATEMP_CHECKED_2"
										src="${pageContext.request.contextPath}/images/checkedbox.gif" height="12"
										width="12" alt="">
									</c:if>
									<c:if test="${not w2Print.statemp2}">
									<img id="STATEMP_UNCHECKED_2"
										src="${pageContext.request.contextPath}/images/uncheckedbox.gif" height="12"
										width="12" alt="">
									</c:if>
								</div>
							</td>
							<td valign="top">
								<div class="boxtitle">
									Retirement Plan
								</div>
								<div class="boximage">
									<c:if test="${w2Print.retplan2}">
									<img id="RETPLAN_CHECKED_2"
										src="${pageContext.request.contextPath}/images/checkedbox.gif" height="12"
										width="12" alt="">
									</c:if>
									<c:if test="${not w2Print.retplan2}">
									<img id="RETPLAN_UNCHECKED_2"
										src="${pageContext.request.contextPath}/images/uncheckedbox.gif" height="12"
										width="12" alt="">
									</c:if>
								</div>
							</td>
							<td valign="top">
								<div class="boxtitle">
									Third party sick pay
								</div>
								<div class="boximage">
									<c:if test="${w2Print.thrdsick2}">
									<img id="THRDSICK_CHECKED_2"
										src="${pageContext.request.contextPath}/images/checkedbox.gif" height="12"
										width="12" alt="">
									</c:if>
									<c:if test="${not w2Print.thrdsick2}">
									<img id="THRDSICK_UNCHECKED_2"
										src="${pageContext.request.contextPath}/images/uncheckedbox.gif" height="12"
										width="12" alt="">
									</c:if>
								</div>
							</td>
						</tr>
					</table>
					<div class="boxtitle wide">
						&nbsp;
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="3" valign="top">
					<div class="footer-left">
						Copy C, For Employee's RECORDS
					</div>
					<div class="footer-right">
						Department of the Treasury - Internal Revenue Service
					</div>
				</td>
			</tr>
			<tr>
				<td class="footer" colspan="3" valign="top"
					style="text-align: center">
					<h1>
						2009
					</h1>
				</td>
			</tr>
		</table>
	</div>




	<div class="breakafter" id="THIRDPARTY" style="width: 100%"
		align="center">

		<div class="breakafter" align="center">
			<hr style="height: 10pt;">
			<br>
			<h1>
				Third Party W-2 Follows
			</h1>
			<hr style="height: 10pt;">
		</div>

		<div align="center">
			<!-- Copy B -->
			<table cellpadding="0" cellspacing="0"
				style="width: 7.6; margin: 0 0 0 0;" align="center">

				<tr>
					<td class="header" colspan="3" valign="top">
						Form W-2 Wage and Tax Statement
					</td>
				</tr>
				<tr>
					<td class="singleborder">
						<div class="boxtitle wide">
							a Employee's social security number
						</div>
						<div class="boxvalue wide">
							<c:out value="${w2Print.ssn1}" />-<c:out value="${w2Print.ssn2}" />-<c:out value="${w2Print.ssn3}" />
						</div>
						<div class="boxtitle wide">
							&nbsp;
						</div>
					</td>
					<td class="doubleborder">
						<div class="boxtitle thin">
							1 Wages, Tips other compensation
						</div>
						<div class="boxvalue thin">
							<c:out value="${w2Print.tgross3}" />
						</div>
						<div class="boxtitle thin">
							&nbsp;
						</div>
					</td>
					<td class="doubleborder">
						<div class="boxtitle thin">
							2 Federal income tax withheld
						</div>
						<div class="boxvalue thin">
							<c:out value="${w2Print.whold3}" />
						</div>
						<div class="boxtitle thin">
							&nbsp;
						</div>
					</td>
				</tr>
				<tr>
					<td class="singleborder">
						<div class="boxtitle thin">
							b Employer identification number (EIN)
						</div>
						<div class="boxvalue thin">
							<c:out value="${w2Print.ein1}" />&nbsp;-&nbsp;<c:out value="${w2Print.ein2}" />
						</div>
						<div class="boxtitle wide">
							&nbsp;
						</div>
					</td>
					<td class="singleborder">
						<div class="boxtitle thin">
							3 Social security wages
						</div>
						<div class="boxvalue thin">
							<c:out value="${w2Print.fgross3}" />
						</div>
						<div class="boxtitle thin">
							&nbsp;
						</div>
					</td>
					<td class="singleborder">
						<div class="boxtitle thin">
							4 Social security tax withheld
						</div>
						<div class="boxvalue thin">
							<c:out value="${w2Print.ftax3}" />
						</div>
						<div class="boxtitle thin">
							&nbsp;
						</div>
					</td>
				</tr>
				<tr>
					<td rowspan="2" class="singleborder">
						<div class="boxtitle wide">
							c Employer's name, address and Zip code
						</div>
						<div class="boxvalue wide">
							<c:out value="${w2Print.ename}" />
						</div>
						<div class="boxvalue wide">
							<c:out value="${w2Print.eaddress}" />
						</div>
						<div class="boxvalue wide">
							<c:out value="${w2Print.ecityst}" />
						</div>
					</td>
					<td class="singleborder">
						<div class="boxtitle thin">
							5 Medicare wages and tips
						</div>
						<div class="boxvalue thin">
							<c:out value="${w2Print.mgross3}" />
						</div>
						<div class="boxtitle thin">
							&nbsp;
						</div>
					</td>
					<td class="singleborder">
						<div class="boxtitle thin">
							6 Medicare tax withheld
						</div>
						<div class="boxvalue thin">
							<c:out value="${w2Print.mtax3}" />
						</div>
						<div class="boxtitle thin">
							&nbsp;
						</div>
					</td>
				</tr>
				<tr>
					<td class="doubleborder">
						<div class="boxtitle">
							9 Advanced EIC payment
						</div>
						<div class="boxvalue">
							<c:out value="${w2Print.eic3}" />
						</div>
						<div class="boxtitle thin">
							&nbsp;
						</div>
					</td>
					<td class="doubleborder">
						<div class="boxtitle thin">
							10 Dependent care benefits
						</div>
						<div class="boxvalue thin">
							<c:out value="${w2Print.dcare3}" />
						</div>
						<div class="boxtitle thin">
							&nbsp;
						</div>
					</td>
				</tr>
				<tr>
					<td rowspan="2" class="singleborder">
						<div class="boxtitle wide">
							e Employee's name
						</div>
						<div class="boxvalue wide">
							<c:out value="${w2Print.empfname}" />&nbsp;<c:out value="${w2Print.emplname}" />
						</div>
						<div class="boxvalue wide">
							<c:out value="${w2Print.empaddress}" />
						</div>
						<div class="boxvalue wide">
							<c:out value="${w2Print.empcityst}" />
						</div>
					</td>
					<td class="singleborder">
						<div class="thin">
							<table cellpadding="0" cellspacing="0">
								<col
									style="text-align: left; padding-left: 2pt; padding-right: 3pt">
								<col style="text-align: right; padding-right: 3pt">
								<tr>
									<td class="boxtitle" colspan="2">
										12 See Instrs. for box 12
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31201}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31201}" />&nbsp;
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31202}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31202}" />&nbsp;
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31203}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31203}" />&nbsp;
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31204}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31204}" />&nbsp;
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31205}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31205}" />&nbsp;
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31206}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31206}" />&nbsp;
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31207}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31207}" />&nbsp;
									</td>
								</tr>
							</table>
						</div>
						<div class="boxtitle thin">
							&nbsp;
						</div>
					</td>
					<td class="singleborder">
						<div class="thin">
							<table cellpadding="0" cellspacing="0">
								<col
									style="text-align: left; padding-left: 2pt; padding-right: 3pt">
								<col style="text-align: right; padding-right: 3pt">
								<tr>
									<td class="boxtitle" colspan="2">
										14 See Instrs. for box 14
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31401}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31401}" />&nbsp;
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31402}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31402}" />&nbsp;
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31403}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31403}" />&nbsp;
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31404}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31404}" />&nbsp;
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31405}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31405}" />&nbsp;
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31406}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31406}" />&nbsp;
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31407}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31407}" />&nbsp;
									</td>
								</tr>
							</table>
						</div>
						<div class="boxtitle thin">
							&nbsp;
						</div>
					</td>
				</tr>

				<tr>
					<td colspan="2" class="singleborder">
						<table cellpadding="3" cellspacing="0">
							<tr>
								<td valign="top">
									<div class="boxtitle">
										13
									</div>
								</td>
								<td valign="top">
									<div class="boxtitle">
										Statutory Employee
									</div>
									<div class="boximage">
										<c:if test="${w2Print.statemp31}">
										<img id="STATEMP3_CHECKED_1"
											src="${pageContext.request.contextPath}/images/checkedbox.gif" height="12"
											width="12" alt="">
										</c:if>
										<c:if test="${not w2Print.statemp31}">
										<img id="STATEMP3_UNCHECKED_1"
											src="${pageContext.request.contextPath}/images/uncheckedbox.gif" height="12"
											width="12" alt="">
										</c:if>
									</div>
								</td>
								<td valign="top">
									<div class="boxtitle">
										Retirement Plan
									</div>
									<div class="boximage">
										<c:if test="${w2Print.retplan31}">
										<img id="RETPLAN3_CHECKED_1"
											src="${pageContext.request.contextPath}/images/checkedbox.gif" height="12"
											width="12" alt="">
										</c:if>
										<c:if test="${not w2Print.retplan31}">
										<img id="RETPLAN3_UNCHECKED_1"
											src="${pageContext.request.contextPath}/images/uncheckedbox.gif" height="12"
											width="12" alt="">
										</c:if>
									</div>
								</td>
								<td valign="top">
									<div class="boxtitle">
										Third party sick pay
									</div>
									<div class="boximage">
										<c:if test="${w2Print.thrdsick31}">
										<img id="THRDSICK3_CHECKED_1"
											src="${pageContext.request.contextPath}/images/checkedbox.gif" height="12"
											width="12" alt="">
										</c:if>
										<c:if test="${not w2Print.thrdsick31}">
										<img id="THRDSICK3_UNCHECKED_1"
											src="${pageContext.request.contextPath}/images/uncheckedbox.gif" height="12"
											width="12" alt="">
										</c:if>
									</div>
								</td>
							</tr>
						</table>
						<div class="boxtitle wide">
							&nbsp;
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="3" valign="top">
						<div class="footer-left">
							Copy B, To Be Filed With Employee's FEDERAL Tax Return
						</div>
						<div class="footer-right">
							Department of the Treasury - Internal Revenue Service
						</div>
					</td>
				</tr>
				<tr>
					<td class="footer" colspan="3" valign="top"
						style="text-align: center">
						<h1>
							2009
						</h1>
					</td>
				</tr>
			</table>

			<hr>
			<!-- Copy C -->
			<table cellpadding="0" cellspacing="0"
				style="width: 7.6; margin: 0 0 0 0;" align="center">

				<tr>
					<td class="header" colspan="3" valign="top">
						Form W-2 Wage and Tax Statement
					</td>
				</tr>
				<tr>
					<td class="singleborder">
						<div class="boxtitle wide">
							a Employee's social security number
						</div>
						<div class="boxvalue wide">
							<c:out value="${w2Print.ssn1}" />-<c:out value="${w2Print.ssn2}" />-<c:out value="${w2Print.ssn3}" />
						</div>
						<div class="boxtitle wide">
							&nbsp;
						</div>
					</td>
					<td class="doubleborder">
						<div class="boxtitle thin">
							1 Wages, Tips other compensation
						</div>
						<div class="boxvalue thin">
							<c:out value="${w2Print.tgross3}" />
						</div>
						<div class="boxtitle thin">
							&nbsp;
						</div>
					</td>
					<td class="doubleborder">
						<div class="boxtitle thin">
							2 Federal income tax withheld
						</div>
						<div class="boxvalue thin">
							<c:out value="${w2Print.whold3}" />
						</div>
						<div class="boxtitle thin">
							&nbsp;
						</div>
					</td>
				</tr>
				<tr>
					<td class="singleborder">
						<div class="boxtitle thin">
							b Employer identification number (EIN)
						</div>
						<div class="boxvalue thin">
							<c:out value="${w2Print.ein1}" />&nbsp;-&nbsp;<c:out value="${w2Print.ein2}" />
						</div>
						<div class="boxtitle wide">
							&nbsp;
						</div>
					</td>
					<td class="singleborder">
						<div class="boxtitle thin">
							3 Social security wages
						</div>
						<div class="boxvalue thin">
							<c:out value="${w2Print.fgross3}" />
						</div>
						<div class="boxtitle thin">
							&nbsp;
						</div>
					</td>
					<td class="singleborder">
						<div class="boxtitle thin">
							4 Social security tax withheld
						</div>
						<div class="boxvalue thin">
							<c:out value="${w2Print.ftax3}" />
						</div>
						<div class="boxtitle thin">
							&nbsp;
						</div>
					</td>
				</tr>
				<tr>
					<td rowspan="2" class="singleborder">
						<div class="boxtitle wide">
							c Employer's name, address and Zip code
						</div>
						<div class="boxvalue wide">
							<c:out value="${w2Print.ename}" />
						</div>
						<div class="boxvalue wide">
							<c:out value="${w2Print.eaddress}" />
						</div>
						<div class="boxvalue wide">
							<c:out value="${w2Print.ecityst}" />
						</div>
					</td>
					<td class="singleborder">
						<div class="boxtitle thin">
							5 Medicare wages and tips
						</div>
						<div class="boxvalue thin">
							<c:out value="${w2Print.mgross3}" />
						</div>
						<div class="boxtitle thin">
							&nbsp;
						</div>
					</td>
					<td class="singleborder">
						<div class="boxtitle thin">
							6 Medicare tax withheld
						</div>
						<div class="boxvalue thin">
							<c:out value="${w2Print.mtax3}" />
						</div>
						<div class="boxtitle thin">
							&nbsp;
						</div>
					</td>
				</tr>
				<tr>
					<td class="doubleborder">
						<div class="boxtitle">
							9 Advanced EIC payment
						</div>
						<div class="boxvalue">
							<c:out value="${w2Print.eic3}" />
						</div>
						<div class="boxtitle thin">
							&nbsp;
						</div>
					</td>
					<td class="doubleborder">
						<div class="boxtitle thin">
							10 Dependent care benefits
						</div>
						<div class="boxvalue thin">
							<c:out value="${w2Print.dcare3}" />
						</div>
						<div class="boxtitle thin">
							&nbsp;
						</div>
					</td>
				</tr>
				<tr>
					<td rowspan="2" class="singleborder">
						<div class="boxtitle wide">
							e Employee's name
						</div>
						<div class="boxvalue wide">
							<c:out value="${w2Print.empfname}" />&nbsp;<c:out value="${w2Print.emplname}" />
						</div>
						<div class="boxvalue wide">
							<c:out value="${w2Print.empaddress}" />
						</div>
						<div class="boxvalue wide">
							<c:out value="${w2Print.empcityst}" />
						</div>
					</td>
					<td class="singleborder">
						<div class="thin">
							<table cellpadding="0" cellspacing="0">
								<col
									style="text-align: left; padding-left: 2pt; padding-right: 3pt">
								<col style="text-align: right; padding-right: 3pt">
								<tr>
									<td class="boxtitle" colspan="2">
										12 See Instrs. for box 12
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31201}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31201}" />&nbsp;
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31202}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31202}" />&nbsp;
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31203}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31203}" />&nbsp;
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31204}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31204}" />&nbsp;
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31205}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31205}" />&nbsp;
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31206}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31206}" />&nbsp;
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31207}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31207}" />&nbsp;
									</td>
								</tr>
							</table>
						</div>
						<div class="boxtitle thin">
							&nbsp;
						</div>
					</td>
					<td class="singleborder">
						<div class="thin">
							<table cellpadding="0" cellspacing="0">
								<col
									style="text-align: left; padding-left: 2pt; padding-right: 3pt">
								<col style="text-align: right; padding-right: 3pt">
								<tr>
									<td class="boxtitle" colspan="2">
										14 See Instrs. for box 14
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31401}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31401}" />&nbsp;
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31402}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31402}" />&nbsp;
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31403}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31403}" />&nbsp;
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31404}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31404}" />&nbsp;
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31405}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31405}" />&nbsp;
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31406}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31406}" />&nbsp;
									</td>
								</tr>
								<tr>
									<td class="boxvalue">
										<c:out value="${w2Print.code31407}" />&nbsp;
									</td>
									<td class="boxvaluerj">
										<c:out value="${w2Print.amt31407}" />&nbsp;
									</td>
								</tr>
							</table>
						</div>
						<div class="boxtitle thin">
							&nbsp;
						</div>
					</td>
				</tr>

				<tr>
					<td colspan="2" class="singleborder">
						<table cellpadding="3" cellspacing="0">
							<tr>
								<td valign="top">
									<div class="boxtitle">
										13
									</div>
								</td>
								<td valign="top">
									<div class="boxtitle">
										Statutory Employee
									</div>
									<div class="boximage">
										<c:if test="${w2Print.statemp32}">
										<img id="STATEMP3_CHECKED_2"
											src="${pageContext.request.contextPath}/images/checkedbox.gif" height="12"
											width="12" alt="">
										</c:if>
										<c:if test="${not w2Print.statemp32}">
										<img id="STATEMP3_UNCHECKED_2"
											src="${pageContext.request.contextPath}/images/uncheckedbox.gif" height="12"
											width="12" alt="">
										</c:if>
									</div>
								</td>
								<td valign="top">
									<div class="boxtitle">
										Retirement Plan
									</div>
									<div class="boximage">
										<c:if test="${w2Print.retplan32}">
										<img id="RETPLAN3_CHECKED_2"
											src="${pageContext.request.contextPath}/images/checkedbox.gif" height="12"
											width="12" alt="">
										</c:if>
										<c:if test="${not w2Print.retplan32}">
										<img id="RETPLAN3_UNCHECKED_2"
											src="${pageContext.request.contextPath}/images/uncheckedbox.gif" height="12"
											width="12" alt="">
										</c:if>
									</div>
								</td>
								<td valign="top">
									<div class="boxtitle">
										Third party sick pay
									</div>
									<div class="boximage">
										<c:if test="${w2Print.thrdsick32}">
										<img id="THRDSICK3_CHECKED_2"
											src="${pageContext.request.contextPath}/images/checkedbox.gif" height="12"
											width="12" alt="">
										</c:if>
										<c:if test="${not w2Print.thrdsick32}">
										<img id="THRDSICK3_UNCHECKED_2"
											src="${pageContext.request.contextPath}/images/uncheckedbox.gif" height="12"
											width="12" alt="">
										</c:if>
									</div>
								</td>
							</tr>
						</table>
						<div class="boxtitle wide">
							&nbsp;
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="3" valign="top">
						<div class="footer-left">
							Copy C, For Employee's RECORDS
						</div>
						<div class="footer-right">
							Department of the Treasury - Internal Revenue Service
						</div>
					</td>
				</tr>
				<tr>
					<td class="footer" colspan="3" valign="top"
						style="text-align: center">
						<h1>
							2009
						</h1>
					</td>
				</tr>
			</table>
		</div>

	</div>


	<div class="breakafter" align="center">
		<table cellpadding="15" cellspacing="0"
			style="width: 7.6in; text-align: left;">
			<tr>
				<td style="width: 50%" valign="top">
					<strong style="font-size: large">Notice to Employee</strong>
				</td>
				<td style="width: 50%" valign="top">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td style="width: 50%" valign="top">
					<strong>Refund.</strong> Even if you do not have to file a tax
					return, you should file to get a refund if box 2 shows federal
					income tax withheld or if you can take the earned income credit.
					<br>
					<br>
					<strong>Earned income credit (EIC).</strong> You must file a tax
					return if any amount is shown in box 9.
					<br>
					<br>
					You may be able to take the EIC for 2009 if (a) you do not have a
					qualifying child and you earned less than $13,440 ($16,560 if
					married filing jointly), (b) you have one qualifying child and you
					earned less than $35,463 ($38,583 if married filing jointly), or
					(c) you have more than one qualifying child and you earned less
					than $40,295 ($43,415 if married filing jointly). You and any
					qualifying children must have valid social security numbers (SSNs).
					You cannot take the EIC if your investment income is more than
					$3,100. Any EIC that is more than your tax liability is refunded to
					you, but only if you file a tax return. If you have at least one
					qualifying child, you may get as much as $1,826 of the EIC in
					advance by completing Form W-5, Earned Income Credit Advance
					Payment Certificate, and giving it to your employer.
					<br>
					<br>
					<strong>Clergy and religious workers.</strong> If you are not
					subject to social security and Medicare taxes, see Publication 517,
					Social Security and Other Information for Members of the Clergy and
					Religious Workers.
				</td>
				<td style="width: 50%" valign="top">
					<strong>Corrections.</strong> If your name, SSN, or address is
					incorrect, correct Copies B, C, and 2 and ask your employer to
					correct your employment record. Be sure to ask the employer to file
					Form W-2c, Corrected Wage and Tax Statement, with the Social
					Security Administration (SSA) to correct any name, SSN, or money
					amount error reported to the SSA on Form W-2. If your name and SSN
					are correct but are not the same as shown on your social security
					card, you should ask for a new card that displays your correct name
					at any SSA office or by calling 1-800-772-1213.
					<br>
					<br>
					Credit for excess taxes. If you had more than one employer in 2009
					and more than $6,621.60 in social security and/or Tier I railroad
					retirement (RRTA) taxes were withheld, you may be able to claim a
					credit for the excess against your federal income tax. If you had
					more than one railroad employer and more than $3,088.80 in Tier II
					RRTA tax was withheld, you also may be able to claim a credit. See
					your Form 1040 or Form 1040A instructions and Publication 505, Tax
					Withholding and Estimated Tax.
					<br>
					<br>
					(Also see Instructions for Employee on the back of Copy C.)
				</td>
			</tr>
		</table>
	</div>

	<div class="breakafter" align="center">
		<table cellpadding="15" cellspacing="0"
			style="width: 7.6in; text-align: left;">
			<tr>
				<td style="width: 50%" valign="top">
					<strong style="font-size: large">Instructions for Employee</strong>
					(Also see Notice to Employee, on the back of Copy B.)
				</td>
				<td style="width: 50%" valign="top">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td style="width: 50%" valign="top">
					<strong>Box 1. </strong>Enter this amount on the wages line of your
					tax return.
					<br>
					<strong>Box 2. </strong>Enter this amount on the federal income tax
					withheld line of your tax return.
					<br>
					<strong>Box 8. </strong>This amount is not included in boxes 1, 3,
					5, or 7. For information on how to report tips on your tax return,
					see your Form 1040 instructions.
					<br>
					<strong>Box 9. </strong>Enter this amount on the advance earned
					income credit payments line of your Form 1040 or Form 1040A.
					<br>
					<strong>Box 10. </strong>This amount is the total dependent care
					benefits that your employer paid to you or incurred on your behalf
					(including amounts from a section 125 (cafeteria) plan). Any amount
					over $5,000 is also included in box 1. You must complete Schedule 2
					(Form 1040A) or Form 2441, Child and Dependent Care Expenses, to
					compute any taxable and nontaxable amounts.
					<br>
					<strong>Box 11. </strong>This amount is (a) reported in box 1 if it
					is a distribution made to you from a nonqualified deferred
					compensation or nongovernmental section 457(b) plan or (b) included
					in box 3 and/or 5 if it is a prior year deferral under a
					nonqualified or section 457(b) plan that became taxable for social
					security and Medicare taxes this year because there is no longer a
					substantial risk of forfeiture of your right to the deferred
					amount.
					<br>
					<strong>Box 12.</strong> The following list explains the codes
					shown in box 12. You may need this information to complete your tax
					return. Elective deferrals (codes D, E, F, and S) and designated
					Roth contributions (codes AA and BB) under all plans are generally
					limited to a total of $16,500 ($11,500 if you only have SIMPLE
					plans; $19,500 for section 403(b) plans if you qualify for the
					15-year rule explained in Pub. 571). Deferrals under code G are
					limited to $16,500. Deferrals under code H are limited to $7,000.
				</td>
				<td style="width: 50%" valign="top">
					However, if you were at least age 50 in 2009, your employer may
					have allowed an additional deferral of up to $5,500 ($2,500 for
					section 401(k)(11) and 408(p) SIMPLE plans). This additional
					deferral amount is not subject to the overall limit on elective
					deferrals. For code G, the limit on elective deferrals may be
					higher for the last 3 years before you reach retirement age.
					Contact your plan administrator for more information. Amounts in
					excess of the overall elective deferral limit must be included in
					income. See the “Wages, Salaries, Tips, etc.” line instructions for
					Form 1040.
					<br>
					<strong>Note.</strong> If a year follows code D through H, S, Y,
					AA, or BB, you made a make-up pension contribution for a prior
					year(s) when you were in military service. To figure whether you
					made excess deferrals, consider these amounts for the year shown,
					not the current year. If no year is shown, the contributions are
					for the current year.
					<br>
					<strong>A - </strong>Uncollected social security or RRTA tax on tips.
					Include this tax on Form 1040. See “Total Tax” in the Form 1040
					instructions.
					<br>
					<strong>B - </strong>Uncollected Medicare tax on tips. Include this
					tax on Form 1040. See “Total Tax” in the Form 1040 instructions.
					<br>
					<strong>C - </strong>Taxable cost of group-term life insurance over
					$50,000 (included in boxes 1, 3 (up to social security wage base),
					and 5)
					<br>
					<strong>D - </strong>Elective deferrals to a section 401(k) cash or
					deferred arrangement. Also includes deferrals under a SIMPLE
					retirement account that is part of a section 401(k) arrangement.
					<br>
					<strong>E - </strong>Elective deferrals under a section 403(b) salary
					reduction agreement
					<br>
					<em>(continued on back of Copy 2)</em>
				</td>
			</tr>
		</table>
	</div>

	<div align="center">
		<table cellpadding="15" cellspacing="0"
			style="width: 7.6in; text-align: left;">
			<tr>
				<td style="width: 50%" valign="top">
					<strong style="font-size: large">Instructions for Employee</strong>
					(continued from back of Copy C)
				</td>
				<td style="width: 50%" valign="top">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td style="width: 50%" valign="top">
					<strong>F - </strong>Elective deferrals under a section 408(k)(6)
					salary reduction SEP
					<br>
					<strong>G - </strong>Elective deferrals and employer contributions
					(including nonelective deferrals) to a section 457(b) deferred
					compensation plan
					<br>
					<strong>H - </strong>Elective deferrals to a section 501(c)(18)(D)
					tax-exempt organization plan. See “Adjusted Gross Income” in the
					Form 1040 instructions for how to deduct.
					<br>
					<strong>J - </strong>Nontaxable sick pay (information only, not
					included in boxes 1, 3, or 5)
					<br>
					<strong>K - </strong>20% excise tax on excess golden parachute
					payments. See “Total Tax” in the Form 1040 instructions.
					<br>
					<strong>L - </strong>Substantiated employee business expense
					reimbursements (nontaxable)
					<br>
					<strong>M - </strong>Uncollected social security or RRTA tax on
					taxable cost of group-term life insurance over $50,000 (former
					employees only). See “Total Tax” in the Form 1040 instructions.
					<br>
					<strong>N - </strong>Uncollected Medicare tax on taxable cost of
					group-term life insurance over $50,000 (former employees only). See
					“Total Tax” in the Form 1040 instructions.
					<br>
					<strong>P - </strong>Excludable moving expense reimbursements paid
					directly to employee (not included in boxes 1, 3, or 5)
					<br>
					<strong>Q - </strong>Nontaxable combat pay. See the instructions for
					Form 1040 or Form 1040A for details on reporting this amount.
					<br>
					<strong>R - </strong>Employer contributions to your Archer MSA.
					Report on Form 8853, Archer MSAs and Long-Term Care Insurance
					Contracts.
					<br>
					<strong>S - </strong>Employee salary reduction contributions under a
					section 408(p) SIMPLE (not included in box 1)
					<br>
					<strong>T - </strong>Adoption benefits (not included in box 1). You
					must complete Form 8839, Qualified Adoption Expenses, to compute
					any taxable and nontaxable amounts.
					<br>
					<strong>V - </strong>Income from exercise of nonstatutory stock
					option(s) (included in boxes 1, 3 (up to social security wage
					base), and 5)

				</td>
				<td style="width: 50%" valign="top">
					<br>
					<strong>W - </strong>Employer contributions to your Health Savings
					Account. Report on Form 8889, Health Savings Accounts (HSAs).
					<br>
					<strong>Y - </strong>Deferrals under a section 409A nonqualified
					deferred compensation plan.
					<br>
					<strong>Z - </strong>Income under section 409A on a nonqualified
					deferred compensation plan. This amount is also included in box 1.
					It is subject to an additional 20% tax plus interest. See “Total
					Tax” in the Form 1040 instructions.
					<br>
					<strong>AA - </strong>Designated Roth contributions under a section
					401(k) plan.
					<br>
					<strong>BB - </strong>Designated Roth contributions under a section
					403(b) plan.
					<br>
					<strong>Box 13. </strong>If the “Retirement plan” box is checked,
					special limits may apply to the amount of traditional IRA
					contributions that you may deduct.
					<br>
					<strong>Box 14.</strong>
					<br>
					<strong>HEALTH</strong> - Employee deductions for health and dental
					insurance.
					<br>
					<strong>NTA</strong> - Non-TRS Non-Taxable allowance amount paid to
					the employee for travel pay.
					<br>
					<strong>TXA</strong> - Non-TRS Non-Taxable business expense paid to
					the employee.
					<br>
					<strong>CAF</strong> - Salary reduction amount deducted from the
					employee as defined under Section 125 of the Internal Revenue
					Service Code.
					<br>
					<strong>TRS</strong> - Employee contributions to the Teachers
					Retirement System of Texas.
					<br>
					<strong>401A</strong> - The amount deducted from the employee for
					annuities as defined under Section 401A of the Internal Revenue
					Code
					<br>
					<strong>TFB</strong> - Fringe benefits amount taxed for withholding
					purposes.
					<br>
					<strong>Note. </strong>Keep Copy C of Form W-2 for at least 3 years
					after the due date for filing your income tax return. However, to
					help protect your social security benefits, keep Copy C until you
					begin receiving social security benefits, just in case there is a
					question about your work record and/or earnings in a particular
					year. Compare the Social Security wages and the Medicare wages to
					the information shown on your annual (for workers over 25) Social
					Security Statement.
				</td>
			</tr>
		</table>
	</div>
</div>
