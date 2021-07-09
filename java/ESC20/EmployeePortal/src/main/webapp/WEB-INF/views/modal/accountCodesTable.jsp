<%@ include file="../../../WEB-INF/jsp/includes/taglibs.jspf"%>
<c:set var="commonBase"><spring:theme code="commonBase" /></c:set>

<style type="text/css">
	.selected{
		background-color: #ffffff;
	}
</style>

<p>Search: <input type="text" class="searchBox" tabindex="2" /></p>
<div style="max-height: 285px; overflow: auto;">
	<table id="accountCodeTable" class="tabular accountCodeTable sortable_table"  style="height: auto; overflow: auto;margin-left:0px;margin-right:0px; width:100%">
		<tr>
			<th style="text-align: center;"><a href="javascript:void(0)" class="searchSortHeader" colClass="selectableId">ID</a></th>
			<th style="text-align: center;"><a href="javascript:void(0)" class="searchSortHeader" colClass="descr">Description</a></th>
		</tr>
		<c:if test = "${empty accountCodes}">
			<tr id = "row" class="unselectedRow">
				<td colspan="3">No rows found</td>
			</tr>
		</c:if>
		<c:forEach var="row" items="${accountCodes}" varStatus="status" >
		<tr id = "row" class="unselectedRow searchTarget" searchtext="${row.data[1]}-${row.data[3]}-${row.data[4]}.${row.data[5]}-${row.data[6]}-${row.data[2]}${row.data[7]}${row.data[8]}${row.data[9]} ${row.data[10]}" name="${status.index}">
			<td style = "text-align:center; width:45%">
				<div onclick="selectAccount(this)" class="selectableId" tabindex="2" title="${row.data[1]}-${row.data[3]}-${row.data[4]}.${row.data[5]}-${row.data[6]}-${row.data[2]}${row.data[7]}${row.data[8]}${row.data[9]}">${row.data[1]}-${row.data[3]}-${row.data[4]}.${row.data[5]}-${row.data[6]}-${row.data[2]}${row.data[7]}${row.data[8]}${row.data[9]}</div>
			</td>
			<td id="accountCodeDescription" class="descr" style = "width:50%">
				${row.data[10]}
			</td>
		</tr>
		</c:forEach>
	</table>
	<a href="javascript:void(0)" class="dialogWrap" tabindex="2" style="color:transparent; outline:none">.</a>
</div>
<script type="text/javascript" src="<spring:theme code='commonBase' />scripts/search.js"></script>
<script type="text/javascript">
	reAuto();
	registerClickables();
	reWrap(null, $("#AccountCodesDialog").find(".cancelBtn"));
	reFocus();
</script>