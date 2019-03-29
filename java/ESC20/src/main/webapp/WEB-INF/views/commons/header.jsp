<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/font-awesome.min.css"/>
<link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/animate.css" />
<link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/jquery.autocomplete.css"/>
<link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/foundation-datepicker.css" />
<link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/mobiscroll.css" />
<link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/icheck.css"/>
<link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/common.css"/>
<link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/gap.css"/>
<link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/button.css"/>
<link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/bar.css"/>
<link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/content.css"/>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/plug-in/jquery.min.js"></script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/plug-in/html2canvas.js"></script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/plug-in/jspdf.debug.js"></script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/plug-in/bluebird.js"></script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/plug-in/bootstrap.min.js"></script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/plug-in/icheck.min.js"></script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/plug-in/foundation-datepicker.js"></script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/plug-in/jquery.autocomplete.js"></script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/locales/foundation-datepicker.en.js"></script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/locales/foundation-datepicker.es.js"></script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/plug-in/jquery.localize.js"></script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/plug-in/mobiscroll.js"></script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/plug-in/adminlte.js"></script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/plug-in/moment.min.js"></script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/plug-in/fullcalendar.js"></script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/plug-in/theme-chooser.js"></script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/plug-in/locale-all.js"></script>
<script>
	var urlMain = '<%=request.getContextPath()%>'
	console.log('url'+urlMain)
	var ctx = "<%=request.getContextPath().split("/")[1]%>";
	console.log("ctx:"+ctx)
	
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
	});

var sessionTime="<%=session.getMaxInactiveInterval()%>";
console.log(sessionTime);
</script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/common.js"></script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/plug-in/bootstrapValidator.js"></script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/plug-in/autoAdvance.js"></script>
<c:if test="${sessionScope.companyId == 1}">
<style>
.main-sidebar{
	background-color: #a93439;
}
.navbar-badge{
	background-color: #a93439;
}
.main-header .bars-btn{
	color: #a93439;
}
</style>
</c:if>
<c:if test="${sessionScope.companyId == 2}">
<style>
.main-sidebar{
	background-color: #00529b;
}
.navbar-badge{
	background-color: #00529b;
}
.main-header .bars-btn{
	color: #00529b;
}
</style>
</c:if>