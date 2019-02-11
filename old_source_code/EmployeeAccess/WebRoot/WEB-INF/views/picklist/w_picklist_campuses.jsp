<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<div id="directory" class="fill_section picklist_box" style="width: 225px; height: 350px;">
	<div id="picklistData" style="overflow: auto;"> 
	<c:if test="${selectionType != 'single_selection'}">
	<table class="layout" style="width: 100%;">
			<tbody>
				<tr>
					<td>
						<div id="picklistDiv" style="overflow: auto;">
							<table id="picklistTable" class="tabular selectable_table ${selectionType}">
								<thead id="picklistThead">
									<tr>
											<th style="text-align: center;">
												Select
											</th>
										<th style="text-align: center;">
											<span id="campusId" class="picklist_data">Id</span>
										</th>
										<th style="text-align: center;">
											<span id="campusName" class="picklist_data">Campus Name</span>
										</th>
									</tr>
								</thead>
								<tbody id="picklistTbody">
									<c:forEach items="${campuses}" varStatus="parm">
										<c:set var="wrap" value=""/>
										<c:if test="${parm.index == 0}">
											<c:set var="wrap" value="wrap_field initial_focus"/>
										</c:if>
										<tr>
											<td>
												<a href="#" id="listdata_${parm.index}" picklistType="${picklistType}" parameterId="${parameterId}" tabindex="1" class="link_button ${wrap}" onclick="setPicklist(this);">${campuses[parm.index].id}</a>
											</td>
											<td style="white-space: nowrap" >
												${campuses[parm.index].name}
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</c:if>
	<c:if test="${selectionType == 'single_selection'}">
	
		<display:table name="${campuses}" id="campus" class="tabular selectable_table ${selectionType}"
		pagesize="20" style="width:223px; margin-top:0px;margin-bottom:0px;margin-left:1px;margin-right:0px; "
		requestURI="" keepStatus="true" >
		  <display:column title="Id" ><a href="#" id="listdata_${campus_rowNum}" picklistType="${picklistType}" parameterId="${parameterId}" tabindex="1" class="link_button ${wrap}" onclick="setPicklist(this);">${campus.id}</a></display:column>
		  <display:column title="Campus Name" >${campus.name} </display:column>
		</display:table>
	</c:if>
	</div>
	<p id="picklist_error" class=""></p>
	<div class="has_default">
		<table>
			<tr>
				<c:if test="${selectionType != 'single_selection'}">
					<td>
						<a href="#" class="button tabbutton_medium hover_trigger selectVendorButton" 
				       	   hoverclass="tabbutton_medium_hover" 
					       focus-class="tabbutton_medium_focus"
					       disabled-class="tabbutton_medium_disabled"
					       picklistType="${picklistType}"
					       parameterId="${parameterId}"
					       tabindex="1"
					       id="selectVendorButton">Select</a>
					</td>
				</c:if>
				<td style="padding-left:40px">
					<a href="#" class="button tabbutton_medium hover_trigger default pickListCancelBtn" 
					   onkeydown="if(event.keyCode==9){$('#listdata_1').focus(); return false;}"
			       	   hoverclass="tabbutton_medium_hover" 
				       focus-class="tabbutton_medium_focus"
				       disabled-class="tabbutton_medium_disabled"
				       picklistType="${picklistType}"
				       tabindex="1"
				       id="cancelButton">Cancel</a>
				</td>
			</tr>
		</table>				       
	</div>
</div>