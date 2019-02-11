<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<div id="directory" class="fill_section picklist_box" style="width: 800px; height: 430px;">
	<div id="picklistFilter" class="has_default">
		<div style="margin-top: 5px;">
			<table>
				<tr>
					<td style="padding-right: 4px;padding-top:5px; white-space: nowrap;">
						School Year
					</td>
					<td style="padding-right: 8px;padding-top:5px;">
						<input type="text" class="text is_filter disabled"
							   value="${selected_schoolyear}"
							   style="width: 30px;" maxlength="4" 
							   id="school_year"
							   disabled="disabled"/>
					</td>
					<td style="padding-right: 4px;padding-top:5px; white-space: nowrap;">
						Course Title
					</td>
					<td style="padding-right: 8px;padding-top:5px;">
						<input type="text" class="text is_filter focusable wrap_field initial_focus"
							   value="" focus-class="focus_text"
							   style="width: 100px;" maxlength="25"
							   id="course_title"
							   tabindex="1"/>
					</td>
					<td style="padding-right: 4px;padding-top:5px; white-space: nowrap;">
						Campus ID
					</td>
					<td style="padding-top:5px;">						
						<input type="text" class="text is_filter is_required"
							   name="w_picklist_campuses" value="" focus-class="focus_text"
							   style="width: 30px;" maxlength="3"
							   id="campuses"
							   tabindex="1"/>
					</td>
					<td style="padding-right: 8px;padding-top:5px;">
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

	<div id="picklistData" style="width: 760px; height: 310px; overflow: auto;">
		<table class="layout" style="width: 100%;">
			<tbody>
				<tr>
					<td>
						<div id="picklistDiv" class="fill_section" style="height: 300px; overflow: auto;">
							<table id="picklistTable" class="tabular selectable_table ${selectionType}" style="overflow: auto; height: auto; width: 100%">
								<thead id="picklistThead">
									<tr>
										<c:if test="${selectionType != 'single_selection'}">
											<th style="text-align: center;">
												Select
											</th>
										</c:if>
										<th style="text-align: center;">
											<span id="courseNumber" class="picklist_data">Course Number</span>
										</th>
										<th style="text-align: center;">
											<span id="title" class="picklist_data">Title</span>
										</th>
										<th style="text-align: center;">
											<span id="serviceId" class="picklist_data">Service ID</span>
										</th>
										<th style="text-align: center;">
											<span id="nbrOfSem" class="picklist_data">Nbr Of Sem</span>
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
					<a href="#" class="button tabbutton_medium hover_trigger last_field pickListCancelBtn" 
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

											