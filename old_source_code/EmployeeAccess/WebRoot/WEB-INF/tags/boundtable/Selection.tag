<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ attribute name="id" required="true" %>

<c:set scope="request" var="rowClass" value="selected"/>

<script type="text/javascript">
	var selectAction = new Array();
</script>

<jsp:doBody/>

<script type="text/javascript">
	var selectActionKeys${id} = new Array();
	var selectActionValues${id} = new Array();
	
	for(i = 0; i < selectAction.length; i++)
	{
		selectActionKeys${id}.push(selectAction[i].key);
		selectActionValues${id}.push(selectAction[i].value);
	}
</script>