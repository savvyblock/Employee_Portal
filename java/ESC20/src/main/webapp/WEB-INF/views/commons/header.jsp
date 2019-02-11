
<link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/font-awesome.min.css">
<link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/animate.css" />
<link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/foundation-datepicker.css" />
<link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/mobiscroll.css" />
<link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/icheck.css">
<link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/common.css">
<link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/gap.css">
<link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/button.css">
<link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/bar.css">
<link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/content.css">
<script src="/<%=request.getContextPath().split("/")[1]%>/js/jquery.min.js"></script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/bootstrap.min.js"></script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/icheck.min.js"></script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/foundation-datepicker.js"></script>
<%--foundation datepicker control language package --start --%>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/locales/foundation-datepicker.en.js"></script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/locales/foundation-datepicker.es.js"></script>
<!-- foundation datepicker control language package  --end -->
<script src="/<%=request.getContextPath().split("/")[1]%>/js/jquery.localize.js"></script>

<script src="/<%=request.getContextPath().split("/")[1]%>/js/mobiscroll.js"></script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/adminlte.js"></script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/moment.min.js"></script>

<script src="/<%=request.getContextPath().split("/")[1]%>/js/fullcalendar.min.js"></script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/theme-chooser.js"></script>
<!-- fullcalendar language package --start -->
<script src="/<%=request.getContextPath().split("/")[1]%>/js/locale-all.js"></script>

<script>
	var ctx = "<%=request.getContextPath().split("/")[1]%>";
	console.log("ctx:"+ctx)
</script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/common.js"></script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/bootstrapValidator.js"></script>
<c:if test="${sessionScope.companyId == 1}">
<style>
.main-sidebar{
	background-color: #a93439;
}
.navbar-badge{
	background-color: #a93439;
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
</style>
</c:if>