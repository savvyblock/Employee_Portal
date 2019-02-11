<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<center><img id="waitImagePicklist1" src="<spring:theme code="commonBase" />/images/wait.gif" style="margin:0px 0px 0px 0px;text-decoration: none; display:none; border: 1px solid #CCC;"/></center> 	
<div class="modal_film_addPicklist hidden"></div>
<div class="modal_contents_addPicklist hidden">
	<div id="popupPicklist" >
		<div id="dataDiv" ></div>
	</div>
</div>

<div class="modal_contents_addFilterPicklist hidden">
	<div id="filterPicklist" >
		<div id="internalDataDiv"></div>
	</div>
</div>
