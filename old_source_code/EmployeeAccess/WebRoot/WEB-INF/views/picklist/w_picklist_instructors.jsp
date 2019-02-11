<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<div id="directory" class="fill_section picklist_box" style="width: 810px; height: 410px;">
	<div id="picklistFilter" class="has_default">
		<div style="margin-top: 5px;">
			<table>
				<tr>
					<td style="padding-right: 4px; white-space: nowrap;">
						School Year
					</td>
					<td style="padding-right: 8px;">
						<input type="text" class="text is_filter disabled"
							   value="${selected_schoolyear}"
							   style="width: 25px;" maxlength="4" 
							   id="school_year"
							   disabled="disabled"/>
					</td>
					<td style="padding-right: 4px; white-space: nowrap;">
						Last Name
					</td>
					<td style="padding-right: 8px;">
						<input type="text" class="text is_filter focusable wrap_field initial_focus" 
							   value="" focus-class="focus_text"
							   style="width: 100px;" maxlength="25"
							   id="last_name"
							   tabindex="1"/>
					</td>
					<td style="padding-right: 4px; white-space: nowrap;">
						First Name
					</td>
					<td style="padding-right: 8px;">
						<input type="text" class="text focusable is_filter"
							   value="" focus-class="focus_text"
							   style="width: 100px;" maxlength="17"
							   id="first_name"
							   tabindex="1"/>
					</td>
					<td style="padding-right: 4px; white-space: nowrap;">
						Campus ID
					</td>
					<td>						
						<input type="text" class="text is_filter is_required"
							   name="w_picklist_campuses" value="" focus-class="focus_text"
							   style="width: 30px;" maxlength="3"
							   id="campuses"
							   tabindex="1"/>
					</td>
					<td style="padding-right: 8px;">
						<a id="" class="dropDownButton hover_trigger picklist"
							tabindex="11"
							disabled-class="dropDownButton_disabled"
							hoverclass="dropDownButton_hover"
							picklistName="w_picklist_campuses"
							selectionType="single_selection"
							parameterId="campuses"
							picklistType="FilterPicklist"
							href="#"></a>
					</td>
					<td style="padding-right: 4px; white-space: nowrap;">
						Grade Level
					</td>
					<td>						
						<input type="text" class="text is_filter"
							   name="w_picklist_grd_lvl" value="" focus-class="focus_text"
							   style="width: 30px;" maxlength="2"
							   id="grades"
							   tabindex="1"/>
					</td>
					<td>
						<a id="" class="dropDownButton hover_trigger picklist"
							tabindex="-1"
							disabled-class="dropDownButton_disabled"
							hoverclass="dropDownButton_hover"
							picklistName="w_picklist_grd_lvl"
							selectionType="single_selection"
							parameterId="grades"
							picklistType="FilterPicklist"
							href="#"></a>
					</td>
					<td>
						<a href="#" class="button tabbutton_small hover_trigger default link_button pickListRetrieveBtn"
						   hoverclass="tabbutton_small_hover"
						   defaultclass="tabbutton_small_default"
						   defaulthoverclass="tabbutton_small_default_hover"
						   focus-class="tabbutton_small_focus"
						   defaultfocus-class="tabbutton_small_default_focus"
						   tabindex="1"
						   selectionType="${selectionType}"
						   parameterId="${parameterId}"
						   picklistName="${picklistName}"
						   picklistType="${picklistType}"
						   buttonid="pickListRetrieveBtn">Retrieve</a>
					</td>
				</tr>
			</table>
		</div>				
	</div>
	
	<center><img id="waitImagePicklist2" src="/CommonWeb/images/wait.gif" style="margin:0px 0px 0px 0px;text-decoration: none;display:none;"/></center>

	<div id="picklistData" style=" height: 310px; overflow: auto;">
		<table class="layout">
			<tbody>
				<tr>
					<td>
						<div id="picklistDiv" >
							<table id="picklistTable" class="tabular selectable_table ${selectionType}" style="overflow: auto; height: auto; width:740px">
								<thead id="picklistThead">
									<tr>
										<c:if test="${selectionType != 'single_selection'}">
											<th style="text-align: center;">
												Select
											</th>
										</c:if>
										<th style="text-align: center;">
											<span id="instructorId" class="picklist_data">Instructor ID</span>
										</th>
										<th style="text-align: center">
											<span id="name" class="picklist_data">Name</span>
										</th>
										<th style="text-align: center;">
											<span id="campus" class="picklist_data">Campus</span>
										</th>
										<th style="text-align: center;">
											<span id="grade" class="picklist_data">Grade</span>
										</th>
										<th style="text-align: center;">
											<span id="homeroom" class="picklist_data">Homeroom</span>
										</th>
									</tr>
								</thead>
								<tbody id="picklistTbody">
								</tbody>
							</table>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<p id="picklist_error" class=""></p>
	<div>
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
				<td>
					<a href="#" class="button tabbutton_medium hover_trigger pickListCancelBtn" 
			       	   onkeydown="if(event.keyCode==9){$('#last_name').focus(); return false;}"	
			       	   hoverclass="tabbutton_medium_hover" 
				       focus-class="tabbutton_medium_focus"
				       disabled-class="tabbutton_medium_disabled"
				       picklistType="${picklistType}"
				       id="cancelButton">Cancel</a>
				</td>
			</tr>
		</table>				       
	</div>
</div>
