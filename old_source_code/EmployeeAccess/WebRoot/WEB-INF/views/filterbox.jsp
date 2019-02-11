<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form id="wFilter">

	<div class="sectionHeader">Filter</div>
	<font color="red">
	<form:errors cssClass="error" htmlEscape="true"/>
	</font>
	<table class="filterTable" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<fieldset class="originalFilterFieldset" >
					<legend>Original Filter Criteria</legend>
					<textarea name="wFiltersimple_mle_originalfilter.value" id="wFiltersimple_mle_originalfilter" style="width:608px;height:54px;border:none;background-color:transparent;"><c:out value="${dataView.dataViewBean.originalFilter}" escapeXml="false" /></textarea>
				</fieldset>
			</td>
		</tr>
		<tr>
			<td>
				<fieldset class="newFilterFieldset" >
					<legend>New Filter Criteria</legend>
					<div class="filterContainer">
					<table width="612" height="168">
						<tbody>
							<tr>
								<td valign="top">
									<table cellspacing="0" cellpadding="0" border="2" frame="border" width="511" rules="all">
										<colgroup>
											<col style="width: 25px"/>
											<col style="width: 150px"/>
											<col style="width: 150px"/>
											<col style="width: 100px"/>
											<col style="width: 50px"/>
										</colgroup>
										<thead>
											<tr>
												<td>
													&nbsp;
												</td>
												<td>
													<div class="columnHeader">
														<span class="filterColumn_0">Column</span>
													</div>
												</td>
												<td>
													<div class="columnHeader">
														<span class="filterColumn_1">Operator</span>
													</div>
												</td>
												<td>
													<div class="columnHeader">
														<span class="filterColumn_2">Value</span>
													</div>
												</td>
												<td>
													<div class="columnHeader">
														<span class="filterColumn_3">Logical</span>
													</div>
												</td>
											</tr>
										</thead>
										<tbody id="wFilterCriteriaTBody">
											<c:choose>
												<c:when test="${command.filterCriteria eq null}">
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</c:when>
												<c:otherwise>
													<c:forEach items="${command.filterCriteria}" varStatus="status">
														<tr id="1">
															<td>
																<input type="checkbox" name="deleteFilterCriteria" id="deleteFilterCriteria_${status.index}" />
															</td>
															<td>
																<form:select path="filterCriteria[${status.index}].column" items="${availableColumns}" multiple="false" cssClass="fill" htmlEscape="true"/>
															</td>
															<td>
																<form:select path="filterCriteria[${status.index}].comparator" items="${comparators}" itemValue="operator" itemLabel="display" cssClass="fill" htmlEscape="true"/>
															</td>
															<td>
																<form:input path="filterCriteria[${status.index}].value" htmlEscape="true"/>
															</td>
															<td>
																<form:select path="filterCriteria[${status.index}].logicalOperator" htmlEscape="true">
																	<form:option value="" label=" " />
																	<form:option value="AND" label="And" />
																	<form:option value="OR" label="Or" />
																</form:select>
															</td>
														</tr>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</tbody>
									</table>
								</td>
								<td width="10px">
									&nbsp;
								</td>
								<td valign="top">
									<table>
										<tbody>
											<tr>
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td>
													<button type="button" id="addFilterButton" class="filterAdd" onclick="addFilterCriteria();">Add</button>
												</td>
											</tr>
											<tr>
												<td>
													<button type="button" id="deleteFilterButton" class="filterDelete" onclick="deleteClickFunc();">Delete</button>
												</td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</tbody>
					</table>	
					</div>	
				</fieldset>
			</td>
		</tr>
	</table>
	<p align="center">
		<input type="submit" id="filterSubmit" name="mySubmitFilter" value="&nbsp;&nbsp;&nbsp;&nbsp;OK&nbsp;&nbsp;&nbsp;&nbsp;">
		&nbsp;&nbsp;
		<input type="button" id="filterCancel" onclick="cancelFilter();" value="Cancel" />
		&nbsp;&nbsp;
		<input type="button" id="filterHelp" onclick="" value="&nbsp;&nbsp;Help&nbsp;&nbsp;" />
	</p>
</form:form>