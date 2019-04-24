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

var  addLabel = "${sessionScope.languageJSON.label.add}";
var  currentMonthLabel = "${sessionScope.languageJSON.label.currentMonth}";
var  leaveTypeLabel = "${sessionScope.languageJSON.leaveBalance.leaveType}";
var  beginningBalanceLabel = "${sessionScope.languageJSON.leaveBalance.beginningBalance}";
var  advancedEarnedLabel = "${sessionScope.languageJSON.leaveBalance.advancedEarned}";
var  pendingEarnedLabel = "${sessionScope.languageJSON.leaveBalance.pendingEarned}";
var  usedLabel = "${sessionScope.languageJSON.leaveBalance.used}";
var  pendingUsedLabel = "${sessionScope.languageJSON.leaveBalance.pendingUsed}";
var  availableLabel = "${sessionScope.languageJSON.leaveBalance.available}";
var  unitsLabel = "${sessionScope.languageJSON.leaveBalance.units}";
var  payrollFreqLabel = "${sessionScope.languageJSON.label.payrollFreq}";
var  daysFreqLabel = "${sessionScope.languageJSON.label.days}";
var  hoursLabel = "${sessionScope.languageJSON.label.hours}";
var  noDataLabel = "${sessionScope.languageJSON.label.noData}";

var todayLabel = "${sessionScope.languageJSON.label.today}";
var selectedLabel = "${sessionScope.languageJSON.label.selected}";
var datepickerLabel = "${sessionScope.languageJSON.label.datepicker}";
var closeDialogLabel = "${sessionScope.languageJSON.label.closeDialog}";

var  rowNbrLabel = "${sessionScope.languageJSON.setTemporaryApprovers.rowNbr}";
var  temporaryApproverLabel = "${sessionScope.languageJSON.setTemporaryApprovers.temporaryApprover}";
var  fromDateLabel = "${sessionScope.languageJSON.setTemporaryApprovers.fromDate}";
var  toDateLabel = "${sessionScope.languageJSON.setTemporaryApprovers.toDate}";
var  deleteLabel = "${sessionScope.languageJSON.setTemporaryApprovers.delete}";
var  deleteBtnLabel = "${sessionScope.languageJSON.label.delete}";

var  routingNumberLabel = "${sessionScope.languageJSON.profile.routingNumber}";
var  bankNameLabel = "${sessionScope.languageJSON.profile.bankName}";

var  prevLabel = "${sessionScope.languageJSON.accessHint.prev}";
var  nextLabel = "${sessionScope.languageJSON.accessHint.next}";
var  closeModalLabel = "${sessionScope.languageJSON.label.closeModal}";
var showDatepickerLabel = "${sessionScope.languageJSON.label.showDatepicker}"
var addNewRequestLabel = "${sessionScope.languageJSON.label.addNewRequestOn}"

var answerErrorValidator = "${sessionScope.languageJSON.validator.answerError}";
var requiredFieldValidator = "${sessionScope.languageJSON.validator.requiredField}";
var notValidValidator = "${sessionScope.languageJSON.validator.notValid}";
var pleaseEnterCorrectMailFormatValidator = "${sessionScope.languageJSON.validator.pleaseEnterCorrectMailFormat}";
var twoPasswordsNotMatchValidator = "${sessionScope.languageJSON.validator.twoPasswordsNotMatch}";
var passwordLengthNotLessThan6Validator = "${sessionScope.languageJSON.validator.passwordLengthNotLessThan6}";
var lengthNotLessThan6_9Validator = "${sessionScope.languageJSON.validator.lengthNotLessThan6_9}";
var lengthNotLessThan6_8Validator = "${sessionScope.languageJSON.validator.lengthNotLessThan6_8}";
var usernameCannotBeEmptyValidator = "${sessionScope.languageJSON.validator.usernameCannotBeEmpty}";
var passwordCannotBeEmptyValidator = "${sessionScope.languageJSON.validator.passwordCannotBeEmpty}";
var passwordNotMatchValidator = "${sessionScope.languageJSON.validator.passwordNotMatch}";

var emailNotMatchValidator = "${sessionScope.languageJSON.validator.emailNotMatch}";
var maximumBankAmmountValidator = "${sessionScope.languageJSON.validator.maximumBankAmmount}";
var pleaseEnterCorrectFormatValidator = "${sessionScope.languageJSON.validator.pleaseEnterCorrectFormat}";
var pleaseEnterCorrectFormatBankAmountValidator = "${sessionScope.languageJSON.validator.pleaseEnterCorrectFormatBankAmount}";
var pleaseSelectOneValidator = "${sessionScope.languageJSON.validator.pleaseSelectOne}";
var pleaseEnterCommentValidator = "${sessionScope.languageJSON.validator.pleaseEnterComment}";
var newRequestValidator = "${sessionScope.languageJSON.validator.newRequest}";
var editRequestValidator = "${sessionScope.languageJSON.validator.editRequest}";
var startNotBeGreaterThanEndTimeValidator = "${sessionScope.languageJSON.validator.startNotBeGreaterThanEndTime}";
var startNotBeGreaterThanEndDateValidator = "${sessionScope.languageJSON.validator.startNotBeGreaterThanEndDate}";
var startDateCannotBeEmptyValidator = "${sessionScope.languageJSON.validator.startDateCannotBeEmpty}";

var endDateCannotBeEmptyValidator = "${sessionScope.languageJSON.validator.endDateCannotBeEmpty}";
var startTimeCannotBeEmptyValidator = "${sessionScope.languageJSON.validator.startTimeCannotBeEmpty}";
var endTimeCannotBeEmptyValidator = "${sessionScope.languageJSON.validator.endTimeCannotBeEmpty}";
var remarksCannotBeEmptyValidator = "${sessionScope.languageJSON.validator.remarksCannotBeEmpty}";
var updateWasSuccessfulValidator = "${sessionScope.languageJSON.validator.updateWasSuccessful}";
var maxLength7Validator = "${sessionScope.languageJSON.validator.maxLength7}";
var maxLength6Validator = "${sessionScope.languageJSON.validator.maxLength6}";
var maxLength19Validator = "${sessionScope.languageJSON.validator.maxLength19}";
var maxLength20Validator = "${sessionScope.languageJSON.validator.maxLength20}";
var maxLength25Validator = "${sessionScope.languageJSON.validator.maxLength25}";
var maxLength26Validator = "${sessionScope.languageJSON.validator.maxLength26}";

var oldPasswordWrongValidator = "${sessionScope.languageJSON.validator.oldPasswordWrong}";
var usernameOrPasswordErrorValidator = "${sessionScope.languageJSON.validator.usernameOrPasswordError}";
var usernameOrPasswordIncorrectValidator = "${sessionScope.languageJSON.validator.usernameOrPasswordIncorrect}";
var authenticateFailedValidator = "${sessionScope.languageJSON.validator.authenticateFailed}";
var emailNotExitValidator = "${sessionScope.languageJSON.validator.emailNotExit}";
var userNotExistValidator = "${sessionScope.languageJSON.validator.userNotExist}";
var userExistValidator = "${sessionScope.languageJSON.validator.userExist}";
var saveUserSuccessValidator = "${sessionScope.languageJSON.validator.saveUserSuccess}";
var usernameOrEmailErrorValidator = "${sessionScope.languageJSON.validator.usernameOrEmailError}";
var usernameOrEmailNotExitValidator = "${sessionScope.languageJSON.validator.usernameOrEmailNotExit}";
var fromDateNotGreaterToDateValidator = "${sessionScope.languageJSON.validator.fromDateNotGreaterToDate}";

var resetPswSuccess = "${sessionScope.languageJSON.validator.resetPswSuccess}";
var resetPswFaildValidator = "${sessionScope.languageJSON.validator.resetPswFaild}";
var noUserAccountAssociatedValidator = "${sessionScope.languageJSON.validator.noUserAccountAssociated}";
var pleaseCompleteFormValidator = "${sessionScope.languageJSON.validator.pleaseCompleteForm}";
var noEmployeeAccountAssociatedValidator = "${sessionScope.languageJSON.validator.noEmployeeAccountAssociated}";
var pictureTooLargeValidator = "${sessionScope.languageJSON.validator.pictureTooLarge}";
var noResultErrorValidator = "${sessionScope.languageJSON.validator.noResultError}";
var repeatErrorValidator = "${sessionScope.languageJSON.validator.repeatError}";
var pleaseSelectAgreeWayValidator = "${sessionScope.languageJSON.validator.pleaseSelectAgreeWay}";
var haveEnteredThreeValidator = "${sessionScope.languageJSON.validator.haveEnteredThree}";
var haveNotSpaceValidator = "${sessionScope.languageJSON.validator.haveNotSpace}";
var resetLockedValidator = "${sessionScope.languageJSON.validator.resetLocked}";
var userNotRegisteredValidator = "${sessionScope.languageJSON.validator.userNotRegistered}";
var pictureChooseValidator = "${sessionScope.languageJSON.validator.pictureChoose}";
var availableErrorValidator = "${sessionScope.languageJSON.validator.availableError}";



var languageSet = "${sessionScope.language}"
</script>
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