<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="arrow_back" value="/CommonWeb/images/reports/arrow_back.gif" />
<c:set var="arrow_next" value="/CommonWeb/images/reports/arrow_next.gif" />

<form:form id="wSort">
		
	<div class="sectionHeader">Sort</div>

<table class="filterTable" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
			<div class="sortContainer">
					<table width="400" height="168">
						<tbody>
							<tr>
								<td valign="top">
									<table cellspacing="0" cellpadding="0" border="2" frame="border" width="350" rules="all">
										<colgroup>
											<col style="width: 25px"/>
											<col style="width: 150px"/>
											<col style="width: 150px"/>
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
														<span class="filterColumn_1">Sort Order</span>
													</div>
												</td>
											</tr>
										</thead>
										<tbody id="wSortCriteriaTBody">
											<c:choose>
												<c:when test="${command.sortCriteria eq null}">
													<td></td>
													<td></td>
													<td></td>
												</c:when>
												<c:otherwise>
													<c:forEach items="${command.sortCriteria}" varStatus="status">
														<tr id="1">
															<td>
																<input type="checkbox" name="deleteSortCriteria" id="deleteSortCriteria_${status.index}" />
															</td>
															<td>
																<form:select path="sortCriteria[${status.index}].column" items="${availableColumns}" multiple="false" cssClass="fill" htmlEscape="true" />
															</td>
															<td>
																<form:select path="sortCriteria[${status.index}].ascending" htmlEscape="true" >
																	<form:option value="ascending" label="Ascending" />
																	<form:option value="decending" label="Descending" />
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
													<button type="button" id="addSortButton" class="filterAdd" onclick="addSortCriteria();">Add</button>
												</td>
											</tr>
											<tr>
												<td>
													<button type="button" id="deleteSortButton" class="filterDelete" onclick="deleteSortClickFunc();">Delete</button>
												</td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</tbody>
					</table>
					</div>	
			</td>
		</tr>
	</table>
	
	<p align="center">
		<input type="submit" id="sortSubmit" name="mySubmitSort" value="&nbsp;&nbsp;OK&nbsp;&nbsp;">
		&nbsp;&nbsp;
		<input type="button" id="sortCancel" onclick="cancelSort();" value="Cancel" />
	</p>
</form:form>
