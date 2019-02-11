<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ attribute name="width" required="false"%>
<%@ attribute name="height" required="false"%>
<%@ attribute name="pagination" required="false" type="java.lang.Boolean" %>
<%@ attribute name="pageNumber" required="false" type="java.lang.Integer" %>
<%@ attribute name="pagesAvailable" required="false" type="java.lang.Integer" %>
<%@ attribute name="records" required="false" type="java.lang.Integer" %>
<%@ attribute name="id" required="false" type="java.lang.String" %>
<%@ attribute name="showAdd" required="false" type="java.lang.Boolean" %>
<%@ attribute name="showDelete" required="false" type="java.lang.Boolean" %>
<%@ attribute name="showFooter" required="false" type="java.lang.Boolean" %>
<%@ attribute name="showNumberRows" required="false" type="java.lang.Boolean" %>
<%@ attribute name="innerWidth" required="false" %>
<%@ attribute name="checkUnsaved" required="false" type="java.lang.Boolean" %>
<%@ attribute name="tabindex" required="false" type="java.lang.Integer" %>
<%@ attribute name="cssStyle" required="false" type="java.lang.String" %>

<c:set var="tabindex" value="${tabindex}" scope="request"/>

<c:if test="${not empty width and fn:contains(width,'px')}">
	<c:set var="pixelWidth" value="${fn:substringBefore(width,'px')}"/>
</c:if>

<c:if test="${empty innerWidth}">
	<c:choose>
		<c:when test="${empty pixelWidth}">
			<c:set var="innerWidth" value="98%"/>
		</c:when>
		<c:otherwise>
			<c:set var="innerWidth" value="${pixelWidth - 20}px"/>
		</c:otherwise>
	</c:choose>
</c:if>

<c:if test="${empty showFooter}">
	<c:set var="showFooter" value="true" />
</c:if>

<c:if test="${empty showNumberRows}">
	<c:set var="showNumberRows" value="true" />
</c:if>

<c:set var="tableid" value="${id}" scope="request"/>

<c:if test="${not empty id}">
	<c:set var="id" value="_${id}" />
</c:if>

<c:if test="${empty checkUnsaved}">
	<c:set var="checkUnsaved" value="false"/>
</c:if>

<c:if test="${empty width}">
	<c:set var="width" value="1130px;"/>
</c:if>

<c:if test="${empty height}">
	<c:set var="height" value="300px;"/>
</c:if>

<c:if test="${checkUnsaved}">
	<c:set var="onClickCheckUnsaved" value="$('#isUnsavedData').val(UnsavedDataWarning.isDirty());"/>
</c:if>

<c:if test="${not checkUnsaved}">
	<c:set var="onClickCheckUnsaved" value="UnsavedDataWarning.disable();"/>
</c:if>

<script type="text/javascript" src="<c:url value="/scripts/BoundTable.js" />"></script>

<script type="text/javascript">
	var doSelect = true;
</script>

<table class="tabular selectable_table single_selection" style="empty-cells:hide; ${cssStyle}">
	<tr>
		<td colspan="0">
			<div id="tabContent" style="width: ${width}; height: ${height}; overflow: auto; padding: 0px;">
				<input type="hidden" name="sortField${id}" id="sortField${id}" value="${sortField}"/>
				<input type="hidden" name="sortOrder${id}" id="sortOrder${id}" value="${sortOrder}"/>
				<table id="logTable${id}" class="tabular selectable_table single_selection" 
				style="height: auto; overflow: auto; width: ${innerWidth}">
					<jsp:doBody />
				</table>
			</div>
		</td>
	</tr>
	<c:if test="${showFooter}">
	<tr>
		<td class="tabular_footer" style="padding: 0px; 0px;">
			<table style="width:100%;" class="tabular_footer_buttons">
			<tr>
			
			
			<td style="border-top: 0px; padding: 0px 0px; text-align:left;">
			<c:if test="${pagination}">
				<div class="pagination">
					<input id="pageInput${id}" type="hidden" name="page${id}">
					<table>
						<tr>
							<td style="border-top: 0px; padding: 0px 0px;">
							<c:choose>
					    		<c:when test="${pageNumber == 1 || pageNumber == 0}">
					    			<img alt="First page (disabled)" src="/CommonWeb/images/table_first_d.gif" />
					    		</c:when>
					    		<c:otherwise>
					    			<input id="firstPage" class="hover_button" type="image"
										onclick="document.getElementById('pageInput${id}').value = 1; Spring.remoting.submitForm('firstPage', $(this).parents('form').attr('id'), { _eventId:'page${id}' }); return false;"
										alt="First page" tabindex="${tabindex}"
										hoversrc="/CommonWeb/images/table_first_h.gif"
										src="/CommonWeb/images/table_first.gif" />										
					    		</c:otherwise>
					    	</c:choose> 
					    	</td>
					    	<td style="border-top: 0px; padding: 0px 0px;">   	
							<c:choose>
					    		<c:when test="${pageNumber == 1 || pageNumber == 0}">
					    			<img alt="Previous page (disabled)" src="/CommonWeb/images/table_prev_d.gif" />
					    		</c:when>
					    		<c:otherwise>
					    			<input id="prevPage" class="hover_button" type="image"
										onclick="document.getElementById('pageInput${id}').value = <c:out value='${pageNumber - 1}'/>; Spring.remoting.submitForm('prevPage', $(this).parents('form').attr('id'), { _eventId:'page${id}' }); return false;"
										alt="Previous page" tabindex="${tabindex}"
										hoversrc="/CommonWeb/images/table_prev_h.gif"
										src="/CommonWeb/images/table_prev.gif" />
					    		</c:otherwise>
					    	</c:choose>
					    	</td>
					    	<td style="border-top: 0px; padding: 0px 0px;">
							<div>
								<select	id="pageSelection${id}" tabindex="${tabindex}" class="ignore_changes" 
								onchange="setSelectedPage('pageSelection${id}', 'pageInput${id}'); Spring.remoting.submitForm('pageSelection${id}', $(this).parents('form').attr('id'), { _eventId:'page${id}' }); return false;">
									<c:forEach begin="1" end="${pagesAvailable}" var="current">
										<option value="${current}" 
											<c:if test="${current == pageNumber}">selected="true"</c:if>>
											<c:out value="${current}" />
										</option>
									</c:forEach>
								</select> / <c:out value="${pagesAvailable}"/>
							</div>	
							</td>
							<td style="border-top: 0px; padding: 0px 0px;">						
							<c:choose>
					    		<c:when test="${pageNumber >= pagesAvailable}">
					    			<img alt="Next page (disabled)" src="/CommonWeb/images/table_next_d.gif" />
					    		</c:when>
					    		<c:otherwise>
					    			<input id="nextPage" class="hover_button" type="image"
										onclick="document.getElementById('pageInput${id}').value = <c:out value='${pageNumber + 1}'/>; Spring.remoting.submitForm('nextPage', $(this).parents('form').attr('id'), { _eventId:'page${id}' }); return false;"
										alt="Next page" tabindex="${tabindex}"
										hoversrc="/CommonWeb/images/table_next_h.gif"
										src="/CommonWeb/images/table_next.gif" />
					    		</c:otherwise>
					    	</c:choose>	
					    	</td>
					    	<td style="border-top: 0px; padding: 0px 0px;">				    	
					    	<c:choose>
					    		<c:when test="${pageNumber >= pagesAvailable}">
					    			<img alt="Last page (disabled)" src="/CommonWeb/images/table_last_d.gif" />
					    		</c:when>
					    		<c:otherwise>
					    			<input id="lastPage" class="hover_button" type="image"
										onclick="document.getElementById('pageInput${id}').value = <c:out value='${pagesAvailable}'/>; Spring.remoting.submitForm('lastPage', $(this).parents('form').attr('id'), { _eventId:'page${id}' }); return false;"
										alt="Last page" tabindex="${tabindex}"
										hoversrc="/CommonWeb/images/table_last_h.gif"
										src="/CommonWeb/images/table_last.gif" />
					    		</c:otherwise>
					    	</c:choose>	
					    	</td>	
				    	</tr>
			    	</table>	
				</div>
			</c:if>
			</td>
			<c:if test="${showAdd}">
				<td style="text-align:right;">
					<div class="table_buttons">
						<input id="addButton${id}" class="<c:if test="${notSelected}">disabled</c:if> hover_button" type="image" alt="Add" tabindex="${tabindex}"
							hoversrc="/CommonWeb/images/table_add_h.gif" src="/CommonWeb/images/table_add.gif"
							onclick="${onClickCheckUnsaved}Spring.remoting.submitForm('addButton${id}', $(this).parents('form').attr('id'), { _eventId:'add${id}' }); return false;" />
						<a id="addLink" onclick="${onClickCheckUnsaved}Spring.remoting.submitForm('addLink${id}', $(this).parents('form').attr('id'), { _eventId:'add${id}' }); return false;" href="#">Add</a>
					</div>
				</td>
			</c:if>
			<c:if test="${showDelete}">
				<td style="width: 100px; border-top: 0px; padding: 0px 0px; padding-right: 5px; text-align:right;">
					<div class="table_buttons">
						<input id="deleteButton${id}" class="<c:if test="${notSelected}">disabled</c:if> hover_button" type="image" alt="Delete" tabindex="${tabindex}"
							hoversrc="/CommonWeb/images/table_delete_h.gif" src="/CommonWeb/images/table_delete.gif"
							onclick="Spring.remoting.submitForm('deleteButton${id}', $(this).parents('form').attr('id'), { _eventId:'delete${id}' }); return false;" />
						<a id="deleteLink" onclick="Spring.remoting.submitForm('deleteLink${id}', $(this).parents('form').attr('id'), { _eventId:'delete${id}' }); return false;" href="#">Delete</a>
					</div>
				</td>
			</c:if>
			<c:if test="${showNumberRows}">
				<td style="width: 80px; border-top: 0px; padding: 0px 0px; padding-right: 5px; text-align:right;">
					<c:if test="${pagination}">
						<c:set var="itemCount" value="${records-1}"/>
					</c:if>
						Rows: <c:out value="${itemCount+1}"/>
				</td>
			</c:if>
			</tr>
			</table>
		</td>
	</tr>
	</c:if>
</table>

<%-- clear row selection so it doesn't affect other tables --%>
<c:set scope="request" var="rowClass" value="unselected"/>