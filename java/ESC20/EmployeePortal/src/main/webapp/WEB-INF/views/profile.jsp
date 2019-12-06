<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html> 
<html lang="en">
    <head>
        <title>${sessionScope.languageJSON.headTitle.profile}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <%@ include file="commons/header.jsp"%>
        <style>
            .profile .profile-item .profile-btn .saveOrCancel button.undoBankHaveModal,
            .profile .profile-item .profile-btn .saveOrCancel button.undoBankNoModal{
                display: none;
            }
            
            .loadingOn{
                background: #fff;
                position: fixed;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background: rgba(0,0,0,.6);
                z-index: 1999;
                
            }
            .loadingOn .loadEffect{
                    position: absolute;
                    top: 50%;
                    margin-top: -50px;
                    left: 50%;
                    margin-left: -50px;
                }
            .loadEffect{
                width: 100px;
                height: 100px;
                position: relative;
                margin: 0 auto;
                padding:20px 10px;
            }
            .loadEffect span{
                display: inline-block;
                width: 20px;
                height: 20px;
                border-radius: 50%;
                background: #fff;
                position: absolute;
                -webkit-animation: load 1.04s ease infinite;
            }
            @-webkit-keyframes load{
                0%{
                    -webkit-transform: scale(1.2);
                    opacity: 1;
                }
                100%{
                    -webkit-transform: scale(.5);
                    opacity: 0.5;
                }
            }
            .loadEffect span:nth-child(1){
                left: 0;
                top: 50%;
                margin-top:-10px;
                -webkit-animation-delay:0.13s;
            }
            .loadEffect span:nth-child(2){
                left: 14px;
                top: 14px;
                -webkit-animation-delay:0.26s;
            }
            .loadEffect span:nth-child(3){
                left: 50%;
                top: 0;
                margin-left: -10px;
                -webkit-animation-delay:0.39s;
            }
            .loadEffect span:nth-child(4){
                top: 14px;
                right:14px;
                -webkit-animation-delay:0.52s;
            }
            .loadEffect span:nth-child(5){
                right: 0;
                top: 50%;
                margin-top:-10px;
                -webkit-animation-delay:0.65s;
            }
            .loadEffect span:nth-child(6){
                right: 14px;
                bottom:14px;
                -webkit-animation-delay:0.78s;
            }
            .loadEffect span:nth-child(7){
                bottom: 0;
                left: 50%;
                margin-left: -10px;
                -webkit-animation-delay:0.91s;
            }
            .loadEffect span:nth-child(8){
                bottom: 14px;
                left: 14px;
                -webkit-animation-delay:1.04s;
            }
        </style>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <%@ include file="commons/bar.jsp"%>
            <main class="content-wrapper" tabindex="-1">
                <section class="content">
                    <div class="content-white no-title profile">
                        <c:if test="${sessionScope.enableSelfServiceDemographic}">
                            <div class="profile-item">
                                    <button type="button" role="button" class="btn btn-primary sm" data-toggle="modal" data-target="#changePasswordModal" onclick="showPasswordModal()">
                                        <span>${sessionScope.languageJSON.label.changePassword}</span>
                                    </button>
                            </div>
                            <br/>
                                <c:set var="canEditAll" value="false"/>
                                <c:if test="${demoOptions.fieldDisplayOptionName == 'U' || demoOptions.fieldDisplayOptionMarital == 'U' || demoOptions.fieldDisplayOptionDriversLicense == 'U' 
                                || demoOptions.fieldDisplayOptionRestrictionCodes == 'U' || demoOptions.fieldDisplayOptionEmergencyContact == 'U' || demoOptions.fieldDisplayOptionMailAddr == 'U'
                                || demoOptions.fieldDisplayOptionAltAddr == 'U' || demoOptions.fieldDisplayOptionHomePhone =='U' || demoOptions.fieldDisplayOptionWorkPhone == 'U' 
                                || demoOptions.fieldDisplayOptionEmail == 'U' || demoOptions.fieldDisplayOptionCellPhone == 'U'}"> 
                                    <c:set var="canEditAll" value="true"/>
                                </c:if>
                                <c:set var="canResetAll" value="false"/>
                                <c:if test="${demoOptions.fieldDisplayOptionName == 'U' || demoOptions.fieldDisplayOptionMarital == 'U' || demoOptions.fieldDisplayOptionDriversLicense == 'U' 
                                || demoOptions.fieldDisplayOptionRestrictionCodes == 'U' || demoOptions.fieldDisplayOptionEmergencyContact == 'U' || demoOptions.fieldDisplayOptionMailAddr == 'U'
                                || demoOptions.fieldDisplayOptionAltAddr == 'U' || demoOptions.fieldDisplayOptionHomePhone =='U' || demoOptions.fieldDisplayOptionWorkPhone == 'U' 
                                || demoOptions.fieldDisplayOptionEmail == 'U' || demoOptions.fieldDisplayOptionCellPhone == 'U' || payrollOption.fieldDisplayOptionBank == 'U' || payrollOption.fieldDisplayOptionInfo == 'U'}"> 
                                    <c:set var="canResetAll" value="true"/>
                                </c:if>
                                    <div class="text-left">
                                            <c:if test="${canEditAll == true}">

                                            <button type="button" role="button" id="saveAll" class="btn btn-primary"  <c:if test="${canEditAll == false}">disabled="disabled"</c:if>>
                                                <span>${sessionScope.languageJSON.label.save}</span>
                                            </button>  
                                            </c:if>
                                            <c:if test="${canResetAll == true}">
                                                <a href="/EmployeePortal/profile/profile" id="reset" class="btn btn-default">
                                                    <span>${sessionScope.languageJSON.label.reset}</span>
                                                </a>
                                            </c:if>
                                    </div>
                                
                                    <c:if test="${not empty sessionScope.options.messageSelfServiceDemographic}">
                                            <p class="topMsg error-hint" role="alert">${sessionScope.options.messageSelfServiceDemographic}</p>
                                        </c:if>
                                        
                            <c:if test="${demoOptions.fieldDisplayOptionName == 'N' && demoOptions.fieldDisplayOptionMarital == 'N' && demoOptions.fieldDisplayOptionDriversLicense == 'N' 
                                && demoOptions.fieldDisplayOptionRestrictionCodes == 'N' && demoOptions.fieldDisplayOptionEmergencyContact == 'N' && demoOptions.fieldDisplayOptionMailAddr == 'N'
                                && demoOptions.fieldDisplayOptionAltAddr == 'N' && demoOptions.fieldDisplayOptionHomePhone =='N' && demoOptions.fieldDisplayOptionWorkPhone == 'N' 
                                && demoOptions.fieldDisplayOptionEmail == 'N' && demoOptions.fieldDisplayOptionCellPhone == 'N'}">
                                
                            
                                <br/>
                                <p class="topMsg error-hint" role="alert">${sessionScope.languageJSON.profile.approverNotSetNotifyBusinessOffice}</p>
                                <br/>
                            </c:if>
                            
                            <form  id="profileForm" action="saveAll" method="POST">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="hidden" name="empNbr" value="${nameRequest.id.empNbr}">
                                    <input type="hidden" name="nameReqDts" value="${nameRequest.id.reqDts}">
                                    <input type="hidden" name="mrtlReqDts" value="${mrtlRequest.id.reqDts}">
                                    <input type="hidden" name="licReqDts" value="${licRequest.id.reqDts}">
                                    <input type="hidden" name="restrictReqDts" value="${restrictRequest.id.reqDts}">
                                    <input type="hidden" name="emailReqDts" value="${emailRequest.id.reqDts}">
                                    <input type="hidden" name="emerReqDts" value="${emerRequest.id.reqDts}">
                                    <input type="hidden" name="mailAddrReqDts" value="${mailAddrRequest.id.reqDts}">
                                    <input type="hidden" name="altMailAddrReqDts" value="${altMailAddrRequest.id.reqDts}">
                                    <input type="hidden" name="hmReqDts" value="${hmRequest.id.reqDts}">
                                    <input type="hidden" name="w4ReqDts" value="${w4Request.id.reqDts}">
                                    <input type="hidden" id="undoName" name="undoName" value="">
                                <c:set var="readOnlyName" value="false"/>
                                <c:if test="${demoOptions.fieldDisplayOptionName =='I'}"> 
                                    <c:set var="readOnlyName" value="true"/>
                                </c:if>
                                <c:if test="${demoOptions.fieldDisplayOptionName !='N'}">
                                    <h2 class="sub-title">${sessionScope.languageJSON.profile.LegalName}</h2>
                                    
                                    <div class="profile-top first-child">
                                        <div class="profile-item" id="personalForm" >
                                            <div class="profile-left">
                                                <div class="profileTitle form-line profileInfo">
                                                    <span class="currentTitle">${sessionScope.languageJSON.label.current}</span>
                                                    <div class="newTitle">${sessionScope.languageJSON.label.new}</div>
                                                </div>
                                                <div class="profile-item-line form-line">
                                                    <label class="profile-title">${sessionScope.languageJSON.profile.title}</label>
                                                    <div class="profile-desc">
                                                        <span class="haveValue"
                                                            >${sessionScope.userDetail.namePre}</span
                                                        >
                                                        <div class="form-group valueInput">
                                                            <select
                                                                class="form-control <c:if test="${fn:trim(sessionScope.userDetail.namePre) != fn:trim(nameRequest.namePreNew)}">active</c:if>"
                                                                aria-label="${sessionScope.languageJSON.profile.title}"
                                                                name="namePreNew" <c:if test="${readOnlyName == true}">disabled="disabled"</c:if>
                                                                id="titleString">
                                                                <c:forEach var="title" items="${titleOptions}" varStatus="count">
                                                                    <c:choose>
                                                                        <c:when test="${fn:trim(title.description)==''}">
                                                                            <option value="${fn:trim(title.description)}" <c:if test="${fn:trim(title.description) == fn:trim(nameRequest.namePreNew) }">selected</c:if>>&nbsp;</option>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <option value="${fn:trim(title.description)}" <c:if test="${fn:trim(title.description) == fn:trim(nameRequest.namePreNew) }">selected</c:if>>${fn:trim(title.description)}</option>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="profile-item-line form-line">
                                                    <label class="profile-title"
                                                        >${sessionScope.languageJSON.profile.lastname}</label
                                                    >
                                                    <div class="profile-desc">
                                                        <span class="haveValue"
                                                            >${sessionScope.userDetail.nameL}</span
                                                        >
                                                        <div class="form-group valueInput">
                                                            <input
                                                                class="form-control <c:if test="${sessionScope.userDetail.nameL != nameRequest.nameLNew}">active</c:if>"
                                                                type="text" <c:if test="${readOnlyName == true}">disabled="disabled"</c:if>
                                                                value="${nameRequest.nameLNew}"
                                                                aria-label="${sessionScope.languageJSON.profile.lastname}"
                                                            
                                                                name="nameLNew"
                                                                id="lastName"
                                                                maxlength="60"
                                                            />
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="profile-item-line form-line">
                                                    <label class="profile-title" >${sessionScope.languageJSON.profile.firstname}</label>
                                                    <div class="profile-desc">
                                                        <span class="haveValue"
                                                            >${sessionScope.userDetail.nameF}</span
                                                        >
                                                        <div class="form-group valueInput">
                                                            <input
                                                                class="form-control <c:if test="${sessionScope.userDetail.nameF != nameRequest.nameFNew}">active</c:if>"
                                                                type="text" <c:if test="${readOnlyName == true}">disabled="disabled"</c:if>
                                                                value="${nameRequest.nameFNew}"
                                                                aria-label="${sessionScope.languageJSON.profile.firstname}"
                                                            
                                                                name="nameFNew"
                                                                id="firstName"
                                                                maxlength="60"
                                                            />
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="profile-item-line form-line">
                                                    <label class="profile-title">
                                                            ${sessionScope.languageJSON.profile.middleName}
                                                    </label>
                                                    <div class="profile-desc">
                                                        <span class="haveValue"
                                                            >${sessionScope.userDetail.nameM}</span
                                                        >
                                                        <div class="form-group valueInput">
                                                            <input
                                                                class="form-control <c:if test="${sessionScope.userDetail.nameM != nameRequest.nameMNew}">active</c:if>"
                                                                type="text" <c:if test="${readOnlyName == true}">disabled="disabled"</c:if>
                                                                value="${nameRequest.nameMNew}"
                                                                aria-label="${sessionScope.languageJSON.profile.middleName}"
                                                            
                                                                name="nameMNew"
                                                                id="middleName"
                                                                maxlength="60"
                                                            />
                                                        </div>
                                                    </div>
                                                </div>
                                            
                                                
                                                <div class="profile-item-line form-line">
                                                    <label class="profile-title"
                                                        >${sessionScope.languageJSON.profile.generation}</label
                                                    >
                                                    <div class="profile-desc">
                                                        <span class="haveValue"
                                                            >${sessionScope.userDetail.genDescription}</span
                                                        >
                                                        <div class="form-group valueInput">
                                                            <select
                                                                class="form-control <c:if test="${fn:trim(sessionScope.userDetail.nameGen) != fn:trim(nameRequest.nameGenNew)}">active</c:if>"
                                                                aria-label="${sessionScope.languageJSON.profile.generation}"
                                                                <c:if test="${readOnlyName == true}">disabled="disabled"</c:if>
                                                                name="nameGenNew"
                                                                id="generation"
                                                            >
                                                                <c:forEach var="gen" items="${generationOptions}" varStatus="count">
                                                                    <c:if test="${count.index<=2}">
                                                                            <c:choose>
                                                                                    <c:when test="${gen.description==''}">
                                                                                            <option value="${gen.code}" <c:if test="${gen.code == nameRequest.nameGenNew }">selected</c:if>>&nbsp;</option>
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                            <option value="${gen.code}" <c:if test="${gen.code == nameRequest.nameGenNew }">selected</c:if>>${gen.description}</option>
                                                                                    </c:otherwise>
                                                                                </c:choose>
                                                                    </c:if>
                                                                </c:forEach>
                                                                <c:forEach var="gen" items="${generationOptions}" varStatus="count">
                                                                    <c:if test="${gen.code=='A'}">
                                                                        <option value="${gen.code}" <c:if test="${gen.code == nameRequest.nameGenNew }">selected</c:if>>${gen.description}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                                <c:forEach var="gen" items="${generationOptions}" varStatus="count">
                                                                    <c:if test="${count.index>2&&gen.code!='A'}">
                                                                        <option value="${gen.code}" <c:if test="${gen.code == nameRequest.nameGenNew }">selected</c:if>>${gen.description}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <c:if test="${!readOnlyName}">
                                                <div class="profile-btn">
                                                    <div class="edit">
                                                        <button
                                                            type="button" role="button"
                                                            class="btn btn-primary edit-btn" <c:if test="${readOnlyName == true}">disabled="disabled"</c:if>
                                                        >${sessionScope.languageJSON.label.edit}
                                                        </button>
                                                    </div>
                                                    
                                                    <div class="saveOrCancel">
                                                        <button
                                                            type="submit" role="button"
                                                            class="btn btn-primary save-btn hide"
                                                            id="savePersonal"  aria-label = "${sessionScope.languageJSON.label.updatePersonalInfo}"
                                                        >
                                                        ${sessionScope.languageJSON.label.update}
                                                        </button>
                                                        <button
                                                            type="button" role="button"
                                                            id="undoNameRequest" aria-label = "${sessionScope.languageJSON.label.undoPersonalInfo}"
                                                            class="btn btn-secondary" data-toggle="modal" data-target="#undoModal" 
                                                        >
                                                        ${sessionScope.languageJSON.label.undo}
                                                        </button>
                                                        <button
                                                            type="button" role="button" aria-label = "${sessionScope.languageJSON.label.cancelPersonalInfo}"
                                                            class="btn btn-secondary cancel-btn" 
                                                        >
                                                        ${sessionScope.languageJSON.label.cancel}
                                                        </button>
                                                    </div>
                                                    
                                                </div>
                                            </c:if>
                                        </div>
                                        <!-- 
                                        
                                        <form hidden="hidden" action="deleteNameRequest" id="deleteNameRequest" method="POST">
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        </form>
                                        -->
                                        
                                    </div>
                                </c:if>
                                <c:set var="readOnlyMarital" value="false"/>
                                <c:if test="${demoOptions.fieldDisplayOptionMarital =='I'}"> 
                                    <c:set var="readOnlyMarital" value="true"/>
                                </c:if>
                                <c:if test="${demoOptions.fieldDisplayOptionMarital !='N'}">
                                <h2 class="sub-title">${sessionScope.languageJSON.profile.MaritalStatus}</h2>
                                
                                <div class="profile-item" id="maritalStatusForm" >
                                <!--  
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                	-->
                                    <div class="profile-left">
                                            <div class="profileTitle form-line profileInfo">
                                                    <span class="currentTitle">${sessionScope.languageJSON.label.current}</span>
                                                    <div class="newTitle">${sessionScope.languageJSON.label.new}</div>
                                                </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.local}</div>
                                            <div class="profile-desc">
                                                <span class="haveValue">
                                                    <c:forEach var="marital" items="${maritalOptions}" varStatus="count">
                                                        <c:if test="${marital.code == sessionScope.userDetail.maritalStat }">${marital.displayLabel}</c:if>
                                                    </c:forEach>
                                                </span>
                                                <div class="form-group valueInput">
                                                    <select
                                                        id="maritalStatus"
                                                        name="maritalStatNew" <c:if test="${readOnlyMarital == true}">disabled="disabled"</c:if>
                                                        class="form-control <c:if test="${fn:trim(sessionScope.userDetail.maritalStat) != fn:trim(mrtlRequest.maritalStatNew) }">active</c:if>"
                                                        aria-label="${sessionScope.languageJSON.profile.local}"
                                                    >
                                                        <c:forEach var="marital" items="${maritalOptions}" varStatus="count">
                                                                <c:choose>
                                                                        <c:when test="${marital.displayLabel==''}">
                                                                                <option value="${marital.code}" <c:if test="${marital.code == mrtlRequest.maritalStatNew }">selected</c:if>>&nbsp;</option>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                                <option value="${marital.code}" <c:if test="${marital.code == mrtlRequest.maritalStatNew }">selected</c:if>>${marital.displayLabel}</option>
                                                                        </c:otherwise>
                                                                     </c:choose>
                                                            
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <c:if test="${!readOnlyMarital}">
                                    <div class="profile-btn">
                                        <div class="edit">
                                            <button
                                                type="button" role="button"
                                                class="btn btn-primary edit-btn"
                                            >
                                            ${sessionScope.languageJSON.label.edit}
                                            </button>
                                        </div>
                                        <div class="saveOrCancel">
                                            <button
                                                type="submit" role="button"
                                                class="btn btn-primary save-btn hide"
                                                id="saveMarital" aria-label = "${sessionScope.languageJSON.label.updateMaritalStatus}"
                                            >
                                            ${sessionScope.languageJSON.label.update}
                                            </button>
                                            <button
                                                    type="button" role="button"
                                                    id="undoMaritalRequest" aria-label = "${sessionScope.languageJSON.label.undoMaritalStatus}"
                                                    class="btn btn-secondary"   data-toggle="modal" data-target="#undoModal" 
                                                >
                                                ${sessionScope.languageJSON.label.undo}
                                                </button>
                                            <button
                                                type="button" role="button" aria-label = "${sessionScope.languageJSON.label.cancelMaritalStatus}"
                                                class="btn btn-secondary cancel-btn" 
                                            >
                                            ${sessionScope.languageJSON.label.cancel}
                                            </button>
                                        </div>
                                    </div>
                                </c:if>
                                </div>
                                 <!-- 
                                <form hidden="hidden" action="deleteMaritalRequest" id="deleteMaritalRequest" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                                 -->
                                </c:if>
                                <c:set var="readOnlyDriversLicense" value="false"/>
                                <c:if test="${demoOptions.fieldDisplayOptionDriversLicense =='I'}"> 
                                    <c:set var="readOnlyDriversLicense" value="true"/>
                                </c:if>
                                <c:if test="${demoOptions.fieldDisplayOptionDriversLicense !='N'}">
                                <h2 class="sub-title">${sessionScope.languageJSON.profile.driversLicense}</h2>
                                <div class="profile-item" id="driverLicenseForm" >
                                    <div class="profile-left">
                                            <div class="profileTitle form-line profileInfo">
                                                    <span class="currentTitle">${sessionScope.languageJSON.label.current}</span>
                                                    <div class="newTitle">${sessionScope.languageJSON.label.new}</div>
                                                </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.number}</div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.driversLicNbr}</span
                                                >
                                                <div class="form-group valueInput">
                                                    <input
                                                        type="text" <c:if test="${readOnlyDriversLicense == true}">disabled="disabled"</c:if>
                                                        class="form-control <c:if test="${sessionScope.userDetail.driversLicNbr != licRequest.driversLicNbrNew }">active</c:if>"
                                                        value="${licRequest.driversLicNbrNew}"
                                                        name="driversLicNbrNew"
                                                        id="driversLicenseNumber"
                                                       
                                                        aria-label="${sessionScope.languageJSON.profile.driverLicenseNum}"
                                                        
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.state}</div>
                                            <div class="profile-desc">
                                                <span class="haveValue">
                                                    <c:forEach var="states" items="${statesOptions}" varStatus="count">
                                                            <c:if test="${states.code == sessionScope.userDetail.driversLicSt }">${states.displayLabel}</c:if>
                                                    </c:forEach>
                                                </span>
                                                
                                                <div class="form-group valueInput">
                                                    <select
                                                        id="driversState"
                                                        name="driversLicStNew" <c:if test="${readOnlyDriversLicense == true}">disabled="disabled"</c:if>
                                                        class="form-control <c:if test="${sessionScope.userDetail.driversLicSt != licRequest.driversLicStNew }">active</c:if>"
                                                        aria-label="${sessionScope.languageJSON.profile.driversLicenseState}"
                                                       
                                                    >
                                                    <c:forEach var="states" items="${statesOptions}" varStatus="count">
                                                            <option value="${states.code}" <c:if test="${states.code == licRequest.driversLicStNew }">selected</c:if>>${states.displayLabel}</option>
                                                        </c:forEach>
                                                        </select>
                                                </div>
                                            
                                            </div>
                                        </div>
                                    </div>
                                    <c:if test="${!readOnlyDriversLicense}">
                                        <div class="profile-btn">
                                            <div class="edit">
                                                <button
                                                    type="button" role="button"
                                                    class="btn btn-primary edit-btn"
                                                >${sessionScope.languageJSON.label.edit}
                                                
                                                </button>
                                            </div>
                                            <div class="saveOrCancel">
                                                <button
                                                    type="submit" role="button"
                                                    class="btn btn-primary save-btn hide"
                                                    id="saveDriver" aria-label = "${sessionScope.languageJSON.label.updateDriver}"
                                                >
                                                ${sessionScope.languageJSON.label.update}
                                                </button>
                                                <button
                                                        type="button" role="button"
                                                        id="undoDriverLicense" aria-label = "${sessionScope.languageJSON.label.undoDriver}"
                                                        class="btn btn-secondary"   data-toggle="modal" data-target="#undoModal" 
                                                    >
                                                    ${sessionScope.languageJSON.label.undo}
                                                    </button>
                                                <button
                                                    type="button" role="button" aria-label = "${sessionScope.languageJSON.label.cancelDriver}"
                                                    class="btn btn-secondary cancel-btn" 
                                                >
                                                ${sessionScope.languageJSON.label.cancel}
                                                </button>
                                            </div>
                                        </div>
                                    </c:if>
                                </div>
                                <!-- 
                                <form hidden="hidden" action="deleteDriversLicenseRequest" id="deleteDriversLicense" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                                 -->
                                </c:if>  
                                <c:set var="readOnlyRestriction" value="false"/>
                                <c:if test="${demoOptions.fieldDisplayOptionRestrictionCodes =='I'}"> 
                                    <c:set var="readOnlyRestriction" value="true"/>
                                </c:if>
                                <c:if test="${demoOptions.fieldDisplayOptionRestrictionCodes !='N'}">
                                <h2 class="sub-title">${sessionScope.languageJSON.profile.restrictionCodes}</h2>
                                <div class="profile-item" id="restrictionCodeForm" >
                                    <div class="profile-left">
                                            <div class="profileTitle form-line profileInfo">
                                                    <span class="currentTitle">${sessionScope.languageJSON.label.current}</span>
                                                    <div class="newTitle">${sessionScope.languageJSON.label.new}</div>
                                                </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.local}</div>
                                            <div class="profile-desc">
                                                <span class="haveValue">
                                                    <c:forEach var="restrictions" items="${restrictionsOptions}" varStatus="count">
                                                        <c:if test="${restrictions.code == sessionScope.userDetail.restrictCd }">${restrictions.displayLabel}</c:if>
                                                    </c:forEach>
                                                </span>
                                                
                                                <div class="form-group valueInput">
                                                    <select
                                                        id="restrictionCodesLocalRestriction"
                                                        name="restrictCdNew" <c:if test="${readOnlyRestriction == true}">disabled="disabled"</c:if>
                                                        class="form-control <c:if test="${sessionScope.userDetail.restrictCd != restrictRequest.restrictCdNew }">active</c:if>"
                                                        aria-label="${sessionScope.languageJSON.profile.restrictionCodesLocal}"
                                                       
                                                        
                                                    >
                                                    <c:forEach var="restrictions" items="${restrictionsOptions}" varStatus="count">
                                                            <option value="${restrictions.code}" <c:if test="${restrictions.code == restrictRequest.restrictCdNew }">selected</c:if>>${restrictions.displayLabel}</option>
                                                        </c:forEach>
                                                        </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.public}</div>
                                            <div class="profile-desc">
                                                <span class="haveValue">
                                                        <c:forEach var="restriction" items="${restrictionsOptions}" varStatus="count">
                                                                <c:if test="${restriction.code == sessionScope.userDetail.restrictCdPublic }">${restriction.displayLabel}</c:if>
                                                    </c:forEach>
                                                </span>
                                            
                                                <div class="form-group valueInput">
                                                    <select
                                                        id="restrictionCodesPublicRestriction"
                                                        name="restrictCdPublicNew" <c:if test="${readOnlyRestriction == true}">disabled="disabled"</c:if>
                                                        class="form-control <c:if test="${sessionScope.userDetail.restrictCdPublic != restrictRequest.restrictCdPublicNew }">active</c:if>"
                                                        aria-label="${sessionScope.languageJSON.profile.restrictionCodesPublic}"
                                                       
                                                    >
                                                    <c:forEach var="restriction" items="${restrictionsOptions}" varStatus="count">
                                                            <option value="${restriction.code}" <c:if test="${restriction.code == restrictRequest.restrictCdPublicNew }">selected</c:if>>${restriction.displayLabel}</option>
                                                        </c:forEach>
                                                        </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <c:if test="${!readOnlyRestriction}">
                                        <div class="profile-btn">
                                            <div class="edit">
                                                <button
                                                    type="button" role="button"
                                                    class="btn btn-primary edit-btn"
                                                >
                                                ${sessionScope.languageJSON.label.edit}
                                                </button>
                                            </div>
                                            <div class="saveOrCancel">
                                                <button
                                                    type="submit" role="button"
                                                    class="btn btn-primary save-btn hide"
                                                    id="saveRestrict" aria-label = "${sessionScope.languageJSON.label.updateRestrict}"
                                                >
                                                ${sessionScope.languageJSON.label.update}
                                                </button>
                                                <button
                                                        type="button" role="button"
                                                        id="undoRestriction" aria-label = "${sessionScope.languageJSON.label.undoRestrict}"
                                                        class="btn btn-secondary"   data-toggle="modal" data-target="#undoModal" 
                                                    >
                                                    ${sessionScope.languageJSON.label.undo}
                                                    </button>
                                                <button
                                                    type="button" role="button" aria-label = "${sessionScope.languageJSON.label.cancelRestrict}"
                                                    class="btn btn-secondary cancel-btn" 
                                                >
                                                ${sessionScope.languageJSON.label.cancel}
                                                </button>
                                            </div>
                                        </div>
                                    </c:if>
                                </div>
                                <!-- 
                                <form hidden="hidden" action="deleteRestrictionCodesRequest" id="deleteRestrictionCodesRequest" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                                 -->
                                </c:if>  
                                <c:set var="readOnlyEmail" value="false"/>
                                <c:if test="${demoOptions.fieldDisplayOptionEmail =='I'}"> 
                                    <c:set var="readOnlyEmail" value="true"/>
                                </c:if>
                                <c:if test="${demoOptions.fieldDisplayOptionEmail !='N'}">
                                <h2 class="sub-title">${sessionScope.languageJSON.profile.email}</h2>
                                <div class="profile-item" id="emailForm" >
                                    <div class="profile-left">
                                            <div class="profileTitle form-line profileInfo">
                                                    <span class="currentTitle">${sessionScope.languageJSON.label.current}</span>
                                                    <div class="newTitle">${sessionScope.languageJSON.label.new}</div>
                                                </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.workEmail}</div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.email}</span
                                                >
                                                <div class="form-group valueInput error-vertical">
                                                    <input
                                                        name="emailNew"
                                                        value="${emailRequest.emailNew}"
                                                        id="emailWorkEmail"  <c:if test="${readOnlyEmail == true}">disabled="disabled"</c:if>
                                                        class="form-control <c:if test="${sessionScope.userDetail.email != emailRequest.emailNew}">active</c:if>"
                                                        aria-label="${sessionScope.languageJSON.profile.workEmail}"
                                                       
                                                        
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.verifyEmail}</div>
                                            <div class="profile-desc">
                                                <span class="haveValue"></span>
                                                <div class="form-group valueInput error-vertical">
                                                    <input
                                                        class="form-control"
                                                        name="emailNewVerify"
                                                        value=""
                                                        id="emailVerifyWorkEmail"
                                                        class="form-control"  <c:if test="${readOnlyEmail == true}">disabled="disabled"</c:if>
                                                        aria-label="${sessionScope.languageJSON.profile.verifyEmail}"
                                                       
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.homeEmail}</div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.hmEmail}</span
                                                >
                                                <div class="form-group valueInput error-vertical">
                                                    <input
                                                        class="form-control <c:if test="${sessionScope.userDetail.hmEmail != emailRequest.hmEmailNew}">active</c:if>"
                                                        name="hmEmailNew"
                                                        id="emailHomeEmail"  <c:if test="${readOnlyEmail == true}">disabled="disabled"</c:if>
                                                        aria-label="${sessionScope.languageJSON.profile.homeEmail}"
                                                       
                                                        value="${emailRequest.hmEmailNew}"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.verifyEmail}</div>
                                            <div class="profile-desc">
                                                <span class="haveValue"></span>
                                                <div class="form-group valueInput error-vertical">
                                                    <input
                                                        class="form-control"
                                                        name="hmEmailVerifyNew"
                                                        value=""
                                                        id="emailVerifyHomeEmail"
                                                        class="form-control"  <c:if test="${readOnlyEmail == true}">disabled="disabled"</c:if>
                                                        aria-label="${sessionScope.languageJSON.profile.verifyEmail}"
                                                       
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <c:if test="${!readOnlyEmail}"> 
                                    <div class="profile-btn">
                                        <div class="edit">
                                            <button
                                                type="button" role="button"
                                                class="btn btn-primary edit-btn"
                                            >
                                            ${sessionScope.languageJSON.label.edit}
                                            </button>
                                        </div>
                                        <div class="saveOrCancel">
                                            <button
                                                type="button" role="button"
                                                class="btn btn-primary save-btn hide"
                                                id="saveEmail" aria-label = "${sessionScope.languageJSON.label.updateEmail}"
                                            >
                                            ${sessionScope.languageJSON.label.update}
                                            </button>
                                            <button
                                                    type="button" role="button"
                                                    id="undoEmail" aria-label = "${sessionScope.languageJSON.label.undoEmail}"
                                                    class="btn btn-secondary"   data-toggle="modal" data-target="#undoModal" 
                                                >
                                                ${sessionScope.languageJSON.label.undo}
                                                </button>
                                            <button
                                                type="button" role="button" aria-label = "${sessionScope.languageJSON.label.cancelEmail}"
                                                class="btn btn-secondary cancel-btn" 
                                            >
                                            ${sessionScope.languageJSON.label.cancel}
                                            </button>
                                        </div>
                                    </div>
                                    </c:if>
                                </div>
                                <!-- 
                                <form hidden="hidden" action="deleteEmail" id="deleteEmail" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                                 -->
                                </c:if>
                                <c:set var="readOnlyEmergency" value="false"/>
                                <c:if test="${demoOptions.fieldDisplayOptionEmergencyContact =='I'}"> 
                                    <c:set var="readOnlyEmergency" value="true"/>
                                </c:if>
                                <c:if test="${demoOptions.fieldDisplayOptionEmergencyContact !='N'}">
                                <h2 class="sub-title">${sessionScope.languageJSON.profile.emergenceContactInfo}</h2>
                                <div class="profile-item" id="emergencyContactForm" >
                                    <div class="profile-left">
                                            <div class="profileTitle form-line profileInfo">
                                                    <span class="currentTitle">${sessionScope.languageJSON.label.current}</span>
                                                    <div class="newTitle">${sessionScope.languageJSON.label.new}</div>
                                                </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.name}</div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.emerContact}</span
                                                >
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control <c:if test="${sessionScope.userDetail.emerContact != emerRequest.emerContactNew}">active</c:if>"
                                                        type="text"
                                                        id="emergencyContactName"
                                                        name="emerContactNew"
                                                        value="${emerRequest.emerContactNew}"
                                                        aria-label="${sessionScope.languageJSON.profile.emergencyContactName}"
                                                        <c:if test="${readOnlyEmergency == true}">disabled="disabled"</c:if>
                                                        maxlength="26"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">
                                                    ${sessionScope.languageJSON.profile.phoneNumber}
                                            </div>
                                            <div class="profile-desc">
                                                <span class="haveValue">
                                                    (${sessionScope.userDetail.emerPhoneAc})
                                                    <c:if test="${sessionScope.userDetail.emerPhoneNbr != null && fn:trim(sessionScope.userDetail.emerPhoneNbr) != ''}">
                                                        <c:set var="nbr" scope="page" value="${fn:replace(sessionScope.userDetail.emerPhoneNbr, '-', '')}"/>
                                                        <c:set var="nbr1" scope="page" value="${fn:substring(nbr, 0, 3)}"/>
                                                        <c:set var="nbr2" scope="page" value="${fn:substring(nbr, 3, 7)}"/>
                                                        ${nbr1}-${nbr2}
                                                        <c:set var="emerPhoneNbrCurrent" scope="page" value="${nbr1}${nbr2}"/>
                                                    </c:if>
                                                    <c:if test="${emerRequest.emerPhoneNbrNew != null && fn:trim(emerRequest.emerPhoneNbrNew) != ''}">
                                                        <c:set var="emerPhoneNbr" scope="page" value="${fn:replace(emerRequest.emerPhoneNbrNew, '-', '')}"/>
                                                        <c:set var="emerPhoneNbr1New" scope="page" value="${fn:substring(emerPhoneNbr, 0, 3)}"/>
                                                        <c:set var="emerPhoneNbr2New" scope="page" value="${fn:substring(emerPhoneNbr, 3, 7)}"/>
                                                        <c:set var="emerPhoneNbrNew" scope="page" value="${emerPhoneNbr1New}${emerPhoneNbr2New}"/>
                                                    </c:if>
                                                    <!-- ${sessionScope.userDetail.emerPhoneNbr} -->
                                                    &nbsp;&nbsp; <span>${sessionScope.languageJSON.profile.ext}</span>
                                                    ${sessionScope.userDetail.emerPhoneExt}
                                                </span>
                                                <div class="valueInput flex">
                                                    <div class="form-group">
                                                        <input
                                                            class="form-control phoneAreaCode <c:if test="${sessionScope.userDetail.emerPhoneAc != emerRequest.emerPhoneAcNew}">active</c:if>"
                                                            name="emerPhoneAcNew"
                                                            id="emergencyContactAreaCode"
                                                            aria-label="${sessionScope.languageJSON.profile.emergencyContactAreaCode}"
                                                            <c:if test="${readOnlyEmergency == true}">disabled="disabled"</c:if>
                                                            value="${emerRequest.emerPhoneAcNew}"
                                                            oninput="value=value.replace(/[^\d]/g,'')"
                                                        />
                                                    </div>
                                                    <div class="form-group">
                                                        <input
                                                            class="form-control phone-input <c:if test="${emerPhoneNbrCurrent != emerPhoneNbrNew}">active</c:if>"
                                                            name="emerPhoneNbrNew"
                                                            id="emergencyContactPhoneNumber"
                                                            aria-label="${sessionScope.languageJSON.profile.emergencyContactPhoneNumber}"
                                                            <c:if test="${readOnlyEmergency == true}">disabled="disabled"</c:if>
                                                            value="${emerRequest.emerPhoneNbrNew}"
                                                            oninput="value=value.replace(/[^\d\-]/g,'')"
                                                        />
                                                    </div>

                                                    &nbsp;&nbsp;<span <c:if test="${readOnlyEmergency == true}">class="disabledSpan"</c:if>>${sessionScope.languageJSON.profile.ext}</span>
                                                    <div class="form-group">
                                                        <input
                                                            class="form-control phoneAreaCode <c:if test="${sessionScope.userDetail.emerPhoneExt != emerRequest.emerPhoneExtNew}">active</c:if>"
                                                            name="emerPhoneExtNew"
                                                            id="emergencyContactExtention"
                                                            aria-label="${sessionScope.languageJSON.profile.emergencyContactExtention}"
                                                            <c:if test="${readOnlyEmergency == true}">disabled="disabled"</c:if>
                                                            value="${emerRequest.emerPhoneExtNew}"
                                                            oninput="value=value.replace(/[^\d]/g,'')"
                                                        />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">
                                                    ${sessionScope.languageJSON.profile.relationship}
                                            </div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.emerRel}</span
                                                >
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control <c:if test="${sessionScope.userDetail.emerRel != emerRequest.emerRelNew}">active</c:if>"
                                                        name="emerRelNew"
                                                        id="emergencyContactRelationship"
                                                        aria-label="${sessionScope.languageJSON.profile.relationship}"
                                                        <c:if test="${readOnlyEmergency == true}">disabled="disabled"</c:if>
                                                        value="${emerRequest.emerRelNew}"
                                                        maxlength="25"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">
                                                    ${sessionScope.languageJSON.profile.emergencyNotes}
                                            </div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.emerNote}</span
                                                >
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control <c:if test="${sessionScope.userDetail.emerNote != emerRequest.emerNoteNew}">active</c:if>"
                                                        name="emerNoteNew"
                                                        id="emergencyContactEmergencyNotes"
                                                        aria-label="${sessionScope.languageJSON.profile.emergencyNotes}"
                                                        <c:if test="${readOnlyEmergency == true}">disabled="disabled"</c:if>
                                                        value="${emerRequest.emerNoteNew}"
                                                        maxlength="25"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <c:if test="${!readOnlyEmergency}"> 
                                    <div class="profile-btn">
                                        <div class="edit">
                                            <button
                                                type="button" role="button"
                                                class="btn btn-primary edit-btn"
                                            >
                                            ${sessionScope.languageJSON.label.edit}
                                            </button>
                                        </div>
                                        <div class="saveOrCancel">
                                            <button
                                                type="submit" role="button"
                                                class="btn btn-primary save-btn hide"
                                                id="saveEmergency" aria-label = "${sessionScope.languageJSON.label.updateEmergency}"
                                            >
                                            ${sessionScope.languageJSON.label.update}
                                            </button>
                                            <button
                                                    type="button" role="button"
                                                    id="undoEmergencyContact" aria-label = "${sessionScope.languageJSON.label.undoEmergency}"
                                                    class="btn btn-secondary"   data-toggle="modal" data-target="#undoModal" 
                                                >
                                                ${sessionScope.languageJSON.label.undo}
                                                </button>
                                            <button
                                                type="button" role="button" aria-label = "${sessionScope.languageJSON.label.cancelEmergency}"
                                                class="btn btn-secondary cancel-btn" 
                                            >
                                            ${sessionScope.languageJSON.label.cancel}
                                            </button>
                                        </div>
                                    </div>
                                    </c:if>
                                </div>
                                <!--  
                                <form hidden="hidden" action="deleteEmergencyContact" id="deleteEmergencyContact" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                                -->
                                </c:if>
                                <c:set var="readOnlyMailAddr" value="false"/>
                                <c:if test="${demoOptions.fieldDisplayOptionMailAddr =='I'}"> 
                                    <c:set var="readOnlyMailAddr" value="true"/>
                                </c:if>
                                <c:if test="${demoOptions.fieldDisplayOptionMailAddr !='N'}">
                                <h2 class="sub-title">${sessionScope.languageJSON.profile.mailingAddress}</h2>
                                <div class="profile-item" id="mailingAddressForm" >
                                    
                                    <div class="profile-left">
                                            <div class="profileTitle form-line profileInfo">
                                                    <span class="currentTitle">${sessionScope.languageJSON.label.current}</span>
                                                    <div class="newTitle">${sessionScope.languageJSON.label.new}</div>
                                                </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.number}</div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.addrNbr}</span
                                                >
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control <c:if test="${fn:trim(sessionScope.userDetail.addrNbr) != fn:trim(mailAddrRequest.addrNbrNew)}">active</c:if>"
                                                        name="addrNbrNew"
                                                        id="mailAddrNumber"
                                                        aria-label="${sessionScope.languageJSON.profile.mailingAddressNumber}"
                                                        <c:if test="${readOnlyMailAddr == true}">disabled="disabled"</c:if>
                                                        value="${mailAddrRequest.addrNbrNew}"
                                                        
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">
                                                    ${sessionScope.languageJSON.profile.streetBox}
                                            </div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.addrStr}</span
                                                >
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control <c:if test="${fn:trim(sessionScope.userDetail.addrStr) != fn:trim(mailAddrRequest.addrStrNew)}">active</c:if>"
                                                        name="addrStrNew"
                                                        id="mailAddrStr"
                                                        aria-label="${sessionScope.languageJSON.profile.streetBox}"
                                                        <c:if test="${readOnlyMailAddr == true}">disabled="disabled"</c:if>
                                                        value="${mailAddrRequest.addrStrNew}"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.apt}</div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.addrApt}</span
                                                >
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control <c:if test="${fn:trim(sessionScope.userDetail.addrApt) != fn:trim(mailAddrRequest.addrAptNew)}">active</c:if>"
                                                        name="addrAptNew"
                                                        id="mailAddrApartment"
                                                        aria-label="${sessionScope.languageJSON.profile.apt}"
                                                        <c:if test="${readOnlyMailAddr == true}">disabled="disabled"</c:if>
                                                        value="${mailAddrRequest.addrAptNew}"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.city}</div>
                                            <div class="profile-desc">
                                                <span class="haveValue">
                                                    ${sessionScope.userDetail.addrCity}
                                                </span>
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control <c:if test="${fn:trim(sessionScope.userDetail.addrCity) != fn:trim(mailAddrRequest.addrCityNew)}">active</c:if>"
                                                        name="addrCityNew"
                                                        id="mailAddrCity"
                                                        aria-label="${sessionScope.languageJSON.profile.city}"
                                                        <c:if test="${readOnlyMailAddr == true}">disabled="disabled"</c:if>
                                                        value="${mailAddrRequest.addrCityNew}"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.state}</div>
                                            <div class="profile-desc">
                                                <span class="haveValue">
                                                        <c:forEach var="states" items="${statesOptions}" varStatus="count">
                                                                <c:if test="${states.code == sessionScope.userDetail.addrSt }">${states.displayLabel}</c:if>
                                                        </c:forEach>
                                                </span>
                                                <div class="form-group valueInput">
                                                    <select
                                                        id="mailAddrState"
                                                        name="addrStNew"
                                                        aria-label="${sessionScope.languageJSON.profile.state}"
                                                        <c:if test="${readOnlyMailAddr == true}">disabled="disabled"</c:if>
                                                        class="form-control  <c:if test="${fn:trim(sessionScope.userDetail.addrSt) != fn:trim(mailAddrRequest.addrStNew)}">active</c:if>"
                                                    >
                                                        <c:forEach var="states" items="${statesOptions}" varStatus="count">
                                                            <option value="${states.code}" <c:if test="${states.code == mailAddrRequest.addrStNew }">selected</c:if>>${states.displayLabel}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.zip}</div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.addrZip}</span
                                                >
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control  <c:if test="${fn:trim(sessionScope.userDetail.addrZip) != fn:trim(mailAddrRequest.addrZipNew)}">active</c:if>"
                                                        name="addrZipNew"
                                                        id="mailAddrZip"
                                                        aria-label="${sessionScope.languageJSON.profile.zip}"
                                                        <c:if test="${readOnlyMailAddr == true}">disabled="disabled"</c:if>
                                                        value="${mailAddrRequest.addrZipNew}"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.zip4}</div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.addrZip4}</span
                                                >
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control <c:if test="${fn:trim(sessionScope.userDetail.addrZip4) != fn:trim(mailAddrRequest.addrZip4New)}">active</c:if>"
                                                        name="addrZip4New"
                                                        id="mailAddrZipPlusFour"
                                                        aria-label="${sessionScope.languageJSON.profile.zip4}"
                                                        <c:if test="${readOnlyMailAddr == true}">disabled="disabled"</c:if>
                                                        value="${mailAddrRequest.addrZip4New}"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <c:if test="${!readOnlyMailAddr}"> 
                                    <div class="profile-btn">
                                        <div class="edit">
                                            <button
                                                type="button" role="button"
                                                class="btn btn-primary edit-btn"
                                            >
                                            ${sessionScope.languageJSON.label.edit}
                                            </button>
                                        </div>
                                        <div class="saveOrCancel">
                                            <button
                                                type="submit" role="button"
                                                class="btn btn-primary save-btn hide"
                                                id="saveMailingAddress" aria-label = "${sessionScope.languageJSON.label.updateMailingAddress}"
                                            >
                                            ${sessionScope.languageJSON.label.update}
                                            </button>
                                            <button
                                                    type="button" role="button"
                                                    id="undoMailingAddress" aria-label = "${sessionScope.languageJSON.label.undoMailingAddress}"
                                                    class="btn btn-secondary"   data-toggle="modal" data-target="#undoModal" 
                                                >
                                                ${sessionScope.languageJSON.label.undo}
                                                </button>
                                            <button
                                                type="button" role="button" aria-label = "${sessionScope.languageJSON.label.cancelMailingAddress}"
                                                class="btn btn-secondary cancel-btn" 
                                            >${sessionScope.languageJSON.label.cancel}
                                            
                                            </button>
                                        </div>
                                    </div>
                                    </c:if>
                                </div>
                                <!-- 
                                <form hidden="hidden" action="deleteMailAddr" id="deleteMailAddr" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                                 -->
                                </c:if>
                                <c:set var="readOnlyAltAddr" value="false"/>
                                <c:if test="${demoOptions.fieldDisplayOptionAltAddr =='I'}"> 
                                    <c:set var="readOnlyAltAddr" value="true"/>
                                </c:if>
                                <c:if test="${demoOptions.fieldDisplayOptionAltAddr !='N'}">
                                <h2 class="sub-title">${sessionScope.languageJSON.profile.altAddr}</h2>
                                <div class="profile-item" id="alternativeAddressForm" >
                                       
                                    <div class="profile-left">
                                            <div class="profileTitle form-line profileInfo">
                                                    <span class="currentTitle">${sessionScope.languageJSON.label.current}</span>
                                                    <div class="newTitle">${sessionScope.languageJSON.label.new}</div>
                                                </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.number}</div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.smrAddrNbr}</span
                                                >
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control <c:if test="${sessionScope.userDetail.smrAddrNbr != altMailAddrRequest.smrAddrNbrNew}">active</c:if>"
                                                        name="smrAddrNbrNew"
                                                        id="altAddrNumber"
                                                        aria-label="${sessionScope.languageJSON.profile.altAddrNumber}"
                                                        <c:if test="${readOnlyAltAddr == true}">disabled="disabled"</c:if>
                                                        value="${altMailAddrRequest.smrAddrNbrNew}"
                                                        
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">
                                                    ${sessionScope.languageJSON.profile.streetBox}
                                            </div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.smrAddrStr}</span
                                                >
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control <c:if test="${sessionScope.userDetail.smrAddrStr != altMailAddrRequest.smrAddrStrNew}">active</c:if>"
                                                        name="smrAddrStrNew"
                                                        id="altAddrStr"
                                                        aria-label="${sessionScope.languageJSON.profile.streetBox}"
                                                        <c:if test="${readOnlyAltAddr == true}">disabled="disabled"</c:if>
                                                        value="${altMailAddrRequest.smrAddrStrNew}"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.apt}</div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.smrAddrApt}</span
                                                >
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control <c:if test="${sessionScope.userDetail.smrAddrApt != altMailAddrRequest.smrAddrAptNew}">active</c:if>"
                                                        name="smrAddrAptNew"
                                                        id="altAddrApartment"
                                                        aria-label="${sessionScope.languageJSON.profile.apt}"
                                                        <c:if test="${readOnlyAltAddr == true}">disabled="disabled"</c:if>
                                                        value="${altMailAddrRequest.smrAddrAptNew}"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.city}</div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.smrAddrCity}</span
                                                >
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control <c:if test="${sessionScope.userDetail.smrAddrCity != altMailAddrRequest.smrAddrCityNew}">active</c:if>"
                                                        name="smrAddrCityNew"
                                                        id="altAddrCity"
                                                        aria-label="${sessionScope.languageJSON.profile.city}"
                                                        <c:if test="${readOnlyAltAddr == true}">disabled="disabled"</c:if>
                                                        value="${altMailAddrRequest.smrAddrCityNew}"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.state}</div>
                                            <div class="profile-desc">
                                                <span class="haveValue">
                                                        <c:forEach var="states" items="${statesOptions}" varStatus="count">
                                                            <c:if test="${states.code == sessionScope.userDetail.smrAddrSt }">${states.displayLabel}</c:if>
                                                        </c:forEach>
                                                </span>
                                                <div class="form-group valueInput">
                                                    <select
                                                        id="altAddrState"
                                                        name="smrAddrStNew"
                                                        aria-label="${sessionScope.languageJSON.profile.state}"
                                                        <c:if test="${readOnlyAltAddr == true}">disabled="disabled"</c:if>
                                                        class="form-control <c:if test="${sessionScope.userDetail.smrAddrSt != altMailAddrRequest.smrAddrStNew}">active</c:if>"
                                                    >
                                                    <c:forEach var="states" items="${statesOptions}" varStatus="count">
                                                            <option value="${states.code}" <c:if test="${states.code == altMailAddrRequest.smrAddrStNew }">selected</c:if>>${states.displayLabel}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.zip}</div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.smrAddrZip}</span
                                                >
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control <c:if test="${sessionScope.userDetail.smrAddrZip != altMailAddrRequest.smrAddrZipNew}">active</c:if>"
                                                        name="smrAddrZipNew"
                                                        id="altAddrZip"
                                                        aria-label="${sessionScope.languageJSON.profile.zip}"
                                                        <c:if test="${readOnlyAltAddr == true}">disabled="disabled"</c:if>
                                                        value="${altMailAddrRequest.smrAddrZipNew}"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.zip4}</div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.smrAddrZip4}</span
                                                >
                                                <div class="form-group valueInput">
                                                    <input  <c:if test="${readOnlyAltAddr == true}">disabled="disabled"</c:if>
                                                        class="form-control <c:if test="${sessionScope.userDetail.smrAddrZip4 != altMailAddrRequest.smrAddrZip4New}">active</c:if>"
                                                        name="smrAddrZip4New"
                                                        id="altAddrZipPlusFour"
                                                        aria-label="${sessionScope.languageJSON.profile.zip4}"
                                                        value="${altMailAddrRequest.smrAddrZip4New}"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <c:if test="${!readOnlyAltAddr}"> 
                                    <div class="profile-btn">
                                        <div class="edit">
                                            <button
                                                type="button" role="button"
                                                class="btn btn-primary edit-btn"
                                            >
                                            ${sessionScope.languageJSON.label.edit}
                                            </button>
                                        </div>
                                        <div class="saveOrCancel">
                                            <button
                                                type="submit" role="button"
                                                class="btn btn-primary save-btn hide"
                                                id="saveAltAddress" aria-label = "${sessionScope.languageJSON.label.updateAltAddress}"
                                            >${sessionScope.languageJSON.label.update}
                                            
                                            </button>
                                            <button
                                                    type="button" role="button"
                                                    id="undoAlternative" aria-label = "${sessionScope.languageJSON.label.undoAltAddress}"
                                                    class="btn btn-secondary"   data-toggle="modal" data-target="#undoModal" 
                                                >
                                                ${sessionScope.languageJSON.label.undo}
                                                </button>
                                            <button
                                                type="button" role="button" aria-label = "${sessionScope.languageJSON.label.cancelAltAddress}"
                                                class="btn btn-secondary cancel-btn" 
                                            >
                                            ${sessionScope.languageJSON.label.cancel}
                                            </button>
                                        </div>
                                    </div>
                                    </c:if>
                                </div>
                                <!-- 
                                <form hidden="hidden" action="deleteAltMailAddr" id="deleteAltMailAddr" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                                 -->
                                </c:if>
                                 <c:set var="readOnlyHomePhone" value="false"/>
                                 <c:if test="${demoOptions.fieldDisplayOptionHomePhone == 'I'}"> 
                                     <c:set var="readOnlyHomePhone" value="true"/>
                                 </c:if>
                                 <c:set var="readOnlyWorkPhone" value="false"/>
                                 <c:if test="${demoOptions.fieldDisplayOptionWorkPhone == 'I'}"> 
                                     <c:set var="readOnlyWorkPhone" value="true"/>
                                 </c:if>
                                 <c:set var="readOnlyCellPhone" value="false"/>
                                 <c:if test="${demoOptions.fieldDisplayOptionCellPhone == 'I'}"> 
                                     <c:set var="readOnlyCellPhone" value="true"/>
                                 </c:if>
                             <c:if test="${demoOptions.fieldDisplayOptionHomePhone !='N' || demoOptions.fieldDisplayOptionWorkPhone != 'N' ||demoOptions.fieldDisplayOptionWorkPhone != 'N'}">
 
                                <h2 class="sub-title">${sessionScope.languageJSON.profile.phoneNumbers}</h2>
                                <div class="profile-item" id="phoneForm" >
                                    <div class="profile-left">
                                            <div class="profileTitle form-line profileInfo">
                                                    <span class="currentTitle">${sessionScope.languageJSON.label.current}</span>
                                                    <div class="newTitle">${sessionScope.languageJSON.label.new}</div>
                                                </div>
                                        <c:if test="${demoOptions.fieldDisplayOptionHomePhone !='N'}">       
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.home}</div>
                                            <div class="profile-desc">
                                                <span class="haveValue">
                                                    (${sessionScope.userDetail.phoneArea})
                                                    <c:if test="${sessionScope.userDetail.phoneNbr != null && fn:trim(sessionScope.userDetail.phoneNbr) != ''}">
                                                        <c:set var="phoneNbr" scope="page" value="${fn:replace(sessionScope.userDetail.phoneNbr, '-', '')}"/>
                                                        <c:set var="phoneNbr1" scope="page" value="${fn:substring(phoneNbr, 0, 3)}"/>
                                                        <c:set var="phoneNbr2" scope="page" value="${fn:substring(phoneNbr, 3, 7)}"/>
                                                        ${phoneNbr1}-${phoneNbr2}
                                                        <c:set var="homeNumberCurrent" scope="page" value="${phoneNbr1}${phoneNbr2}"/>
                                                    </c:if>
                                                    <c:if test="${hmRequest.phoneNbrNew != null && fn:trim(hmRequest.phoneNbrNew) != ''}">
                                                        <c:set var="phoneNbrNew" scope="page" value="${fn:replace(hmRequest.phoneNbrNew, '-', '')}"/>
                                                        <c:set var="phoneNbr1New" scope="page" value="${fn:substring(phoneNbrNew, 0, 3)}"/>
                                                        <c:set var="phoneNbr2New" scope="page" value="${fn:substring(phoneNbrNew, 3, 7)}"/>
                                                        <c:set var="homeNumberNew" scope="page" value="${phoneNbr1New}${phoneNbr2New}"/>
                                                    </c:if>
                                                    <!-- ${sessionScope.userDetail.phoneNbr} -->
                                                </span>
                                                <div class="valueInput flex">
                                                    <div class="form-group">
                                                        <input
                                                            class="form-control phoneAreaCode  <c:if test="${sessionScope.userDetail.phoneArea != hmRequest.phoneAreaNew}">active</c:if>"
                                                            name="phoneAreaNew"
                                                            id="homePhoneAreaCode"
                                                            aria-label="${sessionScope.languageJSON.profile.homePhoneAreaCode}"
                                                            <c:if test="${readOnlyHomePhone == true}">disabled="disabled"</c:if>
                                                            value="${hmRequest.phoneAreaNew}"
                                                            oninput="value=value.replace(/[^\d]/g,'')"
                                                            
                                                        />
                                                    </div>
                                                    <div class="form-group">
                                                        <input
                                                            class="form-control phone-input  <c:if test="${homeNumberCurrent != homeNumberNew}">active</c:if>"
                                                            name="phoneNbrNew"
                                                            id="homePhonePhoneNumber"
                                                            aria-label="${sessionScope.languageJSON.profile.homePhonePhoneNumber}"
                                                            <c:if test="${readOnlyHomePhone == true}">disabled="disabled"</c:if>
                                                            value="${hmRequest.phoneNbrNew}"
                                                            oninput="value=value.replace(/[^\d\-]/g,'')"
                                                        />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                    <c:if test="${demoOptions.fieldDisplayOptionCellPhone !='N'}"> 
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.cell}</div>
                                            <div class="profile-desc">
                                                <span class="haveValue">
                                                    (${sessionScope.userDetail.phoneAreaCell})
                                                    <c:if test="${sessionScope.userDetail.phoneNbrCell != null && fn:trim(sessionScope.userDetail.phoneNbrCell) != ''}">
                                                        <c:set var="phoneNbrCell" scope="page" value="${fn:replace(sessionScope.userDetail.phoneNbrCell, '-', '')}"/>
                                                        <c:set var="phoneNbrCell1" scope="page" value="${fn:substring(phoneNbrCell, 0, 3)}"/>
                                                        <c:set var="phoneNbrCell2" scope="page" value="${fn:substring(phoneNbrCell, 3, 7)}"/>
                                                        ${phoneNbrCell1}-${phoneNbrCell2}
                                                        <c:set var="cellNumberCurrent" scope="page" value="${phoneNbrCell1}${phoneNbrCell2}"/>
                                                    </c:if>
                                                    <c:if test="${cellRequest.phoneNbrCellNew != null && fn:trim(cellRequest.phoneNbrCellNew) != ''}">
                                                        <c:set var="phoneNbrCellNew" scope="page" value="${fn:replace(cellRequest.phoneNbrCellNew, '-', '')}"/>
                                                        <c:set var="phoneNbrCell1New" scope="page" value="${fn:substring(phoneNbrCellNew, 0, 3)}"/>
                                                        <c:set var="phoneNbrCell2New" scope="page" value="${fn:substring(phoneNbrCellNew, 3, 7)}"/>
                                                        <c:set var="cellNumberNew" scope="page" value="${phoneNbrCell1New}${phoneNbrCell2New}"/>
                                                    </c:if>
                                                    <!-- ${sessionScope.userDetail.phoneNbrCell} -->
                                                </span>
                                                <div class="valueInput flex">
                                                    <div class="form-group">
                                                        <input
                                                            class="form-control phoneAreaCode  <c:if test="${sessionScope.userDetail.phoneAreaCell != cellRequest.phoneAreaCellNew}">active</c:if>"
                                                            name="phoneAreaCellNew"
                                                            id="cellPhoneAreaCode"
                                                            aria-label="${sessionScope.languageJSON.profile.cellPhoneAreaCode}"
                                                            <c:if test="${readOnlyCellPhone == true}">disabled="disabled"</c:if>
                                                            value="${cellRequest.phoneAreaCellNew}"
                                                            oninput="value=value.replace(/[^\d]/g,'')"
                                                        />
                                                    </div>

                                                    <div class="form-group">
                                                        <input
                                                            class="form-control phone-input  <c:if test="${cellNumberCurrent != cellNumberNew}">active</c:if>"
                                                            name="phoneNbrCellNew"
                                                            id="cellPhonePhoneNumber"
                                                            aria-label="${sessionScope.languageJSON.profile.cellPhonePhoneNumber}"
                                                            <c:if test="${readOnlyCellPhone == true}">disabled="disabled"</c:if>
                                                            value="${cellRequest.phoneNbrCellNew}"
                                                            oninput="value=value.replace(/[^\d\-]/g,'')"
                                                        />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                    <c:if test="${demoOptions.fieldDisplayOptionWorkPhone !='N'}"> 
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.business}</div>
                                            <div class="profile-desc">
                                                <span class="haveValue">
                                                    (${sessionScope.userDetail.phoneAreaBus})
                                                    <c:if test="${sessionScope.userDetail.phoneNbrBus != null && fn:trim(sessionScope.userDetail.phoneNbrBus) != ''}">
                                                        <c:set var="phoneNbrBus" scope="page" value="${fn:replace(sessionScope.userDetail.phoneNbrBus, '-', '')}"/>
                                                        <c:set var="phoneNbrBus1" scope="page" value="${fn:substring(phoneNbrBus, 0, 3)}"/>
                                                        <c:set var="phoneNbrBus2" scope="page" value="${fn:substring(phoneNbrBus, 3, 7)}"/>
                                                        ${phoneNbrBus1}-${phoneNbrBus2}
                                                        <c:set var="workNumberCurrent" scope="page" value="${phoneNbrBus1}${phoneNbrBus2}"/>
                                                    </c:if>
                                                    <c:if test="${busRequest.phoneNbrBusNew != null && fn:trim(busRequest.phoneNbrBusNew) != ''}">
                                                        <c:set var="phoneNbrBusNew" scope="page" value="${fn:replace(busRequest.phoneNbrBusNew, '-', '')}"/>
                                                        <c:set var="phoneNbrBus1New" scope="page" value="${fn:substring(phoneNbrBusNew, 0, 3)}"/>
                                                        <c:set var="phoneNbrBus2New" scope="page" value="${fn:substring(phoneNbrBusNew, 3, 7)}"/>
                                                        <c:set var="workNumberNew" scope="page" value="${phoneNbrBus1New}${phoneNbrBus2New}"/>
                                                    </c:if>
                                                    <!-- ${sessionScope.userDetail.phoneNbrBus} -->
                                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                                    <span>${sessionScope.languageJSON.profile.ext}</span>
                                                    ${sessionScope.userDetail.busPhoneExt}
                                                </span>
                                                <div class="valueInput flex">
                                                    <div class="form-group">
                                                        <input
                                                            class="form-control phoneAreaCode  <c:if test="${sessionScope.userDetail.phoneAreaBus != busRequest.phoneAreaBusNew}">active</c:if>"
                                                            name="phoneAreaBusNew"
                                                            id="workPhoneAreaCode"
                                                            aria-label="${sessionScope.languageJSON.profile.workPhoneAreaCode}"
                                                            <c:if test="${readOnlyWorkPhone == true}">disabled="disabled"</c:if>
                                                            value="${busRequest.phoneAreaBusNew}"
                                                            oninput="value=value.replace(/[^\d]/g,'')"
                                                        />
                                                    </div>

                                                    <div class="form-group">
                                                        <input
                                                            class="form-control phone-input  <c:if test="${workNumberCurrent != workNumberNew}">active</c:if>"
                                                            name="phoneNbrBusNew"
                                                            id="workPhonePhoneNumber"
                                                            aria-label="${sessionScope.languageJSON.profile.workPhonePhoneNumber}"
                                                            <c:if test="${readOnlyWorkPhone == true}">disabled="disabled"</c:if>
                                                            value="${busRequest.phoneNbrBusNew}"
                                                            oninput="value=value.replace(/[^\d\-]/g,'')"
                                                        />
                                                    </div>

                                                    &nbsp;&nbsp;<span <c:if test="${readOnlyWorkPhone == true}">class="disabledSpan"</c:if>>${sessionScope.languageJSON.profile.ext}</span>
                                                    <div class="form-group">
                                                        <input
                                                            class="form-control phoneAreaCode  <c:if test="${sessionScope.userDetail.busPhoneExt != busRequest.busPhoneExtNew}">active</c:if>"
                                                            name="busPhoneExtNew"
                                                            id="workPhoneExtention"
                                                            aria-label="${sessionScope.languageJSON.profile.workPhoneExtention}"
                                                            <c:if test="${readOnlyWorkPhone == true}">disabled="disabled"</c:if>
                                                            value="${busRequest.busPhoneExtNew}"
                                                            oninput="value=value.replace(/[^\d]/g,'')"
                                                        />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                    </div>
                                    <c:if test="${demoOptions.fieldDisplayOptionHomePhone =='U' || demoOptions.fieldDisplayOptionCellPhone =='U' || demoOptions.fieldDisplayOptionWorkPhone =='U'}"> 
                                    <div class="profile-btn">
                                        <div class="edit">
                                            <button
                                                type="button" role="button"
                                                class="btn btn-primary edit-btn"
                                            >
                                            ${sessionScope.languageJSON.label.edit}
                                            </button>
                                        </div>
                                        <div class="saveOrCancel">
                                            <button
                                                type="submit" role="button"
                                                class="btn btn-primary save-btn hide"
                                                id="savePhone" aria-label = "${sessionScope.languageJSON.label.updatePhone}"
                                            >
                                            ${sessionScope.languageJSON.label.update}
                                            </button>
                                            <button
                                                    type="button" role="button"
                                                    id="undoPhoneNumber" aria-label = "${sessionScope.languageJSON.label.undoPhone}"
                                                    class="btn btn-secondary"   data-toggle="modal" data-target="#undoModal" 
                                                >
                                                ${sessionScope.languageJSON.label.undo}
                                                </button>
                                            <button
                                                type="button" role="button" aria-label = "${sessionScope.languageJSON.label.cancelPhone}"
                                                class="btn btn-secondary cancel-btn" 
                                            >
                                            ${sessionScope.languageJSON.label.cancel}
                                            </button>
                                        </div>
                                    </div>
                                    </c:if>
                                </div>
                                <!-- 
                                <form hidden="hidden" action="deletePhone" id="deletePhone" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                                 -->
                                </c:if>
                                 </form>
                        </c:if>
                        
                        <c:if test="${sessionScope.enableSelfServicePayroll}">
                            <c:if test="${not empty sessionScope.options.messageSelfServicePayroll}">
                            	<p class="topMsg error-hint" role="alert">${sessionScope.options.messageSelfServicePayroll}</p>
                        	</c:if>
                           
                            <form
                                class="no-print searchForm"
                                action="profile"
                                id="changeFreqForm"
                                method="POST"
                                            >
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>           
                                <div class="form-group in-line p-l-0">
                                    <label class="form-title"  for="freq" >${sessionScope.languageJSON.label.payrollFreq}:</label>
                                    
                                    <select class="form-control" name="freq" id="freq" onchange="changeFreq()">
                                        <c:forEach var="freq" items="${payRollFrequenciesOptions}" varStatus="count">
                                            <option value="${freq.code}" <c:if test="${freq.code == selectedFreq }">selected</c:if>>${freq.description}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </form>
                            <c:if test="${payrollOption.fieldDisplayOptionInfo == 'N' && payrollOption.fieldDisplayOptionBank == 'N'}">
                                <br/>
                                <p class="topMsg error-hint" role="alert">${sessionScope.languageJSON.profile.approverNotSetNotifyBusinessOffice}</p>
                                <br/>
                            </c:if>
                            <c:if test="${payrollOption.fieldDisplayOptionInfo !='N'}">
                                    <c:set var="readOnlyInfo" value="false"/>
                                    <c:if test="${payrollOption.fieldDisplayOptionInfo =='I'}"> 
                                        <c:set var="readOnlyInfo" value="true"/>
                                    </c:if>
                                    <c:set var="readOnlyInfoW4" value="false"/>
                                    <c:if test="${payrollOption.fieldDisplayOptionInfo =='U'}"> 
                                            <c:set var="readOnlyInfoW4" value="true"/>
                                        </c:if>
                             <h2 class="sub-title">${sessionScope.languageJSON.profile.W4MaritalStatusInfo}</h2>
                            <form class="profile-item" id="w4InfoForm" action="saveW4" method="POST">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="hidden" name="empNbr" value="${w4Request.id.empNbr}">
                                <input type="hidden" name="reqDts" value="${w4Request.id.reqDts}">
                                <input type="hidden" name="payFreq" value="${w4Request.id.payFreq}">
                                <input type="hidden" name="maritalStatTax" value="${payInfo.maritalStatTax}">
                                <input type="hidden" name="nbrTaxExempts" value="${payInfo.nbrTaxExempts}">
                                
                                
                                <input type="hidden" name="w4FileStat" value="${w4Request.w4FileStat}">
                                <input type="hidden" name="w4MultiJob" value="${w4Request.w4MultiJob}">
                                <input type="hidden" name="w4NbrChldrn" value="${w4Request.w4NbrChldrn}">
                                <input type="hidden" name="w4NbrOthrDep" value="${w4Request.w4NbrOthrDep}">
                                <input type="hidden" name="w4OthrIncAmt" value="${w4Request.w4OthrIncAmt}">
                                <input type="hidden" name="w4OthrDedAmt" value="${w4Request.w4OthrDedAmt}">
                                <input type="hidden" name="w4OthrExmptAmt" value="${w4Request.w4OthrExmptAmt}">
                                <input type="hidden" name="maritalStatTaxNew" value="${w4Request.maritalStatTaxNew}">
                                <input type="hidden" name="nbrTaxExemptsNew" value="${w4Request.nbrTaxExemptsNew}">
                                
                                <div class="profile-left">
                                        <div class="profileTitle form-line profileInfo">
                                                <span class="currentTitle">${sessionScope.languageJSON.label.current}</span>
                                                <div class="newTitle">${sessionScope.languageJSON.label.new}</div>
                                            </div>
                                    <div class="profile-item-line form-line">
                                        <div class="profile-title">
                                                ${sessionScope.languageJSON.profile.W4MaritalStatus}
                                        </div>
                                        <div class="profile-desc">
                                            <span class="haveValue">
                                                    <c:forEach var="maritalTax" items="${maritalTaxOptions}" varStatus="count">
                                                            <c:if test="${maritalTax.code == payInfo.maritalStatTax }">${maritalTax.displayLabel}</c:if>
                                                        </c:forEach>
                                            </span>
                                            <div class="form-group valueInput">
                                                <select
                                                    id="maritalStatusLabel"
                                                      <c:if test="${readOnlyInfo == true || readOnlyInfoW4 == true}">disabled="disabled"</c:if>
                                                    class="form-control  <c:if test="${payInfo.maritalStatTax != w4Request.maritalStatTaxNew}">active</c:if>"
                                                    aria-label="${sessionScope.languageJSON.profile.W4MaritalStatus}"
                                                   
                                                    
                                                >
                                                    <c:forEach var="maritalTax" items="${maritalTaxOptions}" varStatus="count">
                                                        <option value="${maritalTax.code}" <c:if test="${maritalTax.code == w4Request.maritalStatTaxNew }">selected</c:if>>${maritalTax.displayLabel}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="profile-item-line form-line">
                                        <div class="profile-title">
                                                ${sessionScope.languageJSON.profile.NbrOfExemptions}
                                        </div>
                                        <div class="profile-desc">
                                            <span class="haveValue"
                                                >${payInfo.nbrTaxExempts}</span
                                            >
                                            <div class="form-group valueInput">
                                                <input
                                                    class="form-control <c:if test="${payInfo.nbrTaxExempts != w4Request.nbrTaxExemptsNew}">active</c:if>"
                                                    id="nbrTaxExemptsNew"
                                                    
                                                    <c:if test="${readOnlyInfo == true || readOnlyInfoW4 == true}">disabled="disabled"</c:if>
                                                    aria-label="${sessionScope.languageJSON.profile.NbrOfExemptions}" 
                                                    value="${w4Request.nbrTaxExemptsNew}"
                                                />
                                            </div>
                                        </div>
                                    </div>

                                    <div class="profile-item-line form-line">
                                        <div class="profile-title">
                                                ${sessionScope.languageJSON.profile.fillingStatus}
                                        </div>
                                        <div class="profile-desc">
                                                <span class="haveValue">
                                                    <c:forEach var="w4FileStat" items="${w4FileStatOptions}" varStatus="count">
                                                        <c:if test="${w4FileStat.code == w4Request.w4FileStat }">
                                                            ${w4FileStat.displayLabel}
                                                        </c:if>
                                                    </c:forEach>
                                                </span>
                                                <div class="form-group valueInput">
                                                    <select
                                                        class="form-control <c:if test="${w4Request.w4FileStat != w4Request.w4FileStatNew}">active</c:if>"
                                                        id="w4FileStatNew"
                                                        name="w4FileStatNew"
                                                        aria-label="${sessionScope.languageJSON.profile.fillingStatus}" 
                                                        <c:if test="${readOnlyInfo == true}">disabled="disabled"</c:if>>
                                                        <c:forEach var="w4FileStat" items="${w4FileStatOptions}" varStatus="count">
                                                            <option 
                                                                value="${w4FileStat.code}" 
                                                                <c:if test="${w4FileStat.code == w4Request.w4FileStatNew }">selected</c:if>>
                                                                ${w4FileStat.displayLabel}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        
                                    </div>

                                    <div class="profile-item-line form-line">
                                        <div class="profile-title">
                                                ${sessionScope.languageJSON.profile.multiJobs}
                                        </div>
                                        <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${w4Request.w4MultiJob}</span
                                                >
                                                <div class="form-group valueInput flexInline  <c:if test="${w4Request.w4MultiJob != w4Request.w4MultiJobNew}">active</c:if>">
                                                    <label class="flexInline" for="w4MultiJobNewYes">
                                                        <input type="radio"
                                                        class="form-control"
                                                        id="w4MultiJobNewYes"
                                                        name="w4MultiJobNew"
                                                        aria-label="${sessionScope.languageJSON.profile.multiJobs}" 
                                                        value="Y"
                                                        <c:if test="${w4Request.w4MultiJobNew == 'Y'}">checked</c:if>
                                                        <c:if test="${readOnlyInfo == true}">disabled="disabled"</c:if>/>
                                                        <span>${sessionScope.languageJSON.profile.yes}</span>
                                                    </label>
    
                                                    <label class="flexInline" for="w4MultiJobNewNo">
                                                        <input type="radio"
                                                        class="form-control"
                                                        id="w4MultiJobNewNo"
                                                        name="w4MultiJobNew"
                                                        aria-label="${sessionScope.languageJSON.profile.multiJobs}" 
                                                        value="N"
                                                        <c:if test="${w4Request.w4MultiJobNew == 'N'}">checked</c:if>
                                                        <c:if test="${readOnlyInfo == true}">disabled="disabled"</c:if>/>
                                                        <span>${sessionScope.languageJSON.profile.no}</span>
                                                    </label>
                                                </div>
                                            </div>
                                    </div>

                                    <div class="profile-item-line form-line">
                                        <div class="profile-title">
                                                ${sessionScope.languageJSON.profile.childrenUnder17}
                                        </div>
                                        <div class="profile-desc">
                                            <span class="haveValue"
                                                >
                                                <fmt:formatNumber value="${w4Request.w4NbrChldrn}" pattern="#,##0"/>
                                                </span
                                            >
                                            <div class="form-group valueInput">
                                                    <c:set var="w4NbrChldrnNewSet" value="${w4Request.w4NbrChldrnNew}"/>
                                                    <c:if test="${w4Request.w4NbrChldrnNew =='' || w4Request.w4NbrChldrnNew ==null}"> 
                                                            <c:set var="w4NbrChldrnNewSet" value="0"/>
                                                    </c:if>
                                                <input
                                                    class="form-control   <c:if test="${w4Request.w4NbrChldrn != w4Request.w4NbrChldrnNew}">active</c:if>"
                                                    id="w4NbrChldrnNew"
                                                    name="w4NbrChldrnNew"
                                                    aria-label="${sessionScope.languageJSON.profile.childrenUnder17}" 
                                                    value="<fmt:formatNumber value='${w4NbrChldrnNewSet}' pattern='#,##0'/>"
                                                    oninput="clearNoNumWhole(this)"
                                                    <c:if test="${readOnlyInfo == true}">disabled="disabled"</c:if>/>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="profile-item-line form-line">
                                        <div class="profile-title">
                                                ${sessionScope.languageJSON.profile.otherDependents}
                                        </div>
                                        <div class="profile-desc">
                                            <span class="haveValue"
                                                ><fmt:formatNumber value="${w4Request.w4NbrOthrDep}" pattern="#,##0"/></span
                                            >
                                            <div class="form-group valueInput">
                                                    <c:set var="w4NbrOthrDepNewSet" value="${w4Request.w4NbrOthrDepNew}"/>
                                                    <c:if test="${w4Request.w4NbrOthrDepNew =='' || w4Request.w4NbrOthrDepNew ==null}"> 
                                                            <c:set var="w4NbrOthrDepNewSet" value="0"/>
                                                    </c:if>
                                                <input
                                                    class="form-control  <c:if test="${w4Request.w4NbrOthrDep != w4Request.w4NbrOthrDepNew}">active</c:if>"
                                                    id="w4NbrOthrDepNew"
                                                    name="w4NbrOthrDepNew"
                                                    aria-label="${sessionScope.languageJSON.profile.otherDependents}" 
                                                    value="<fmt:formatNumber value='${w4NbrOthrDepNewSet}' pattern='#,##0'/>"
                                                    oninput="clearNoNumWhole(this)"
                                                    <c:if test="${readOnlyInfo == true}">disabled="disabled"</c:if>/>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="profile-item-line form-line">
                                        <div class="profile-title">
                                                ${sessionScope.languageJSON.profile.otherIncome}
                                        </div>
                                        <div class="profile-desc">
                                            <span class="haveValue"
                                                >
                                                <fmt:formatNumber value="${w4Request.w4OthrIncAmt}" pattern="#,##0.00"/>
                                                </span
                                            >
                                            <div class="form-group valueInput">
                                                    <c:set var="w4OthrIncAmtNewSet" value="${w4Request.w4OthrIncAmtNew}"/>
                                                    <c:if test="${w4Request.w4OthrIncAmtNew =='' || w4Request.w4OthrIncAmtNew ==null}"> 
                                                            <c:set var="w4OthrIncAmtNewSet" value="0"/>
                                                    </c:if>
                                                <input
                                                    class="form-control decimal2  <c:if test="${w4Request.w4OthrIncAmt != w4Request.w4OthrIncAmtNew}">active</c:if>"
                                                    id="w4OthrIncAmtNew"
                                                    name="w4OthrIncAmtNew"
                                                    oninput="clearNoNum(this)"
                                                    aria-label="${sessionScope.languageJSON.profile.otherIncome}" 
                                                    value="<fmt:formatNumber value='${w4OthrIncAmtNewSet}' pattern='#,##0.00'/>"
                                                    <c:if test="${readOnlyInfo == true}">disabled="disabled"</c:if>/>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="profile-item-line form-line">
                                        <div class="profile-title">
                                                ${sessionScope.languageJSON.profile.deductions}
                                        </div>
                                        <div class="profile-desc">
                                            <span class="haveValue"
                                                ><fmt:formatNumber value="${w4Request.w4OthrDedAmt}" pattern="#,##0.00"/></span
                                            >
                                            <div class="form-group valueInput">
                                                    <c:set var="w4OthrDedAmtNewSet" value="${w4Request.w4OthrDedAmtNew}"/>
                                                    <c:if test="${w4Request.w4OthrDedAmtNew =='' || w4Request.w4OthrDedAmtNew ==null}"> 
                                                            <c:set var="w4OthrDedAmtNewSet" value="0"/>
                                                    </c:if>
                                                <input
                                                    class="form-control decimal2  <c:if test="${w4Request.w4OthrDedAmt != w4Request.w4OthrDedAmtNew}">active</c:if>"
                                                    id="w4OthrDedAmtNew"
                                                    name="w4OthrDedAmtNew"
                                                    aria-label="${sessionScope.languageJSON.profile.deductions}" 
                                                    value="<fmt:formatNumber value='${w4OthrDedAmtNewSet}' pattern='#,##0.00'/>"
                                                    oninput="clearNoNum(this)"
                                                    <c:if test="${readOnlyInfo == true}">disabled="disabled"</c:if>/>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="profile-item-line form-line">
                                        <div class="profile-title">
                                                ${sessionScope.languageJSON.profile.otherExemption}
                                        </div>
                                        <div class="profile-desc">
                                            <span class="haveValue"
                                                ><fmt:formatNumber value="${w4Request.w4OthrExmptAmt}" pattern="#,##0.00"/></span
                                            >
                                            <div class="form-group valueInput">
                                                    <c:set var="w4OthrExmptAmtNewSet" value="${w4Request.w4OthrExmptAmtNew}"/>
                                                    <c:if test="${w4Request.w4OthrExmptAmtNew =='' || w4Request.w4OthrExmptAmtNew ==null}"> 
                                                            <c:set var="w4OthrExmptAmtNewSet" value="0"/>
                                                    </c:if>
                                                <input
                                                    class="form-control decimal2  <c:if test="${w4Request.w4OthrExmptAmt != w4Request.w4OthrExmptAmtNew}">active</c:if>"
                                                    id="w4OthrExmptAmtNew"
                                                    name="w4OthrExmptAmtNew"
                                                    aria-label="${sessionScope.languageJSON.profile.otherExemption}" 
                                                    value="<fmt:formatNumber value='${w4OthrExmptAmtNewSet}' pattern='#,##0.00'/>"
                                                    oninput="clearNoNum(this)"
                                                    <c:if test="${readOnlyInfo == true}">disabled="disabled"</c:if>/>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                                <c:if test="${!readOnlyInfo}">
                                    <div class="profile-btn">
                                        <div class="edit">
                                            <button
                                                type="button" role="button"
                                                class="btn btn-primary edit-btn"
                                            >
                                            ${sessionScope.languageJSON.label.edit}
                                            </button>
                                        </div>
                                        <div class="saveOrCancel">
                                            <button
                                                type="submit" role="button"
                                                class="btn btn-primary save-btn"
                                                id="saveW4" aria-label = "${sessionScope.languageJSON.label.updateW4}"
                                            >
                                            ${sessionScope.languageJSON.label.update}
                                            </button>
                                            <button
                                                    type="button" role="button"
                                                    id="undoW4" aria-label = "${sessionScope.languageJSON.label.undoW4}"
                                                    class="btn btn-secondary"   data-toggle="modal" data-target="#undoModal" 
                                                >
                                                ${sessionScope.languageJSON.label.undo}
                                                </button>
                                            <button
                                                type="button" role="button" aria-label = "${sessionScope.languageJSON.label.cancelW4}"
                                                class="btn btn-secondary cancel-btn" 
                                            >
                                            ${sessionScope.languageJSON.label.cancel}
                                            </button>
                                        </div>
                                    </div>
                                </c:if>
                            </form>
                            <form hidden="hidden" action="deleteW4" id="deleteW4" method="POST">
                            	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="hidden" name="empNbr" value="${w4Request.id.empNbr}">
                                <input type="hidden" name="reqDts" value="${w4Request.id.reqDts}">
                                <input type="hidden" name="payFreq" value="${w4Request.id.payFreq}">
                                <input type="hidden" name="maritalStatTax" value="${payInfo.maritalStatTax}">
                                <input type="hidden" name="nbrTaxExempts" value="${payInfo.nbrTaxExempts}">
                            </form>
                        </c:if>
                        <c:if test="${payrollOption.fieldDisplayOptionBank !='N'}">	
                            <h2 class="sub-title">${sessionScope.languageJSON.profile.directDepositBankAccounts}</h2>
                            <p class="error-hint atLeastOneBankRequiredError" style="display: none;">${sessionScope.languageJSON.validator.atLeastOneBankRequired}</p>
                            <p class="error-hint duplicateBankAccountError" style="display: none;">${sessionScope.languageJSON.validator.duplicateBankAccount}</p>
                            <p class="error-hint selectAnotherAsPrimaryError" style="display: none;">${sessionScope.languageJSON.validator.selectAnotherAsPrimary}</p>
                            <div class="profile-item" style="padding-bottom: 0;border-bottom:0;">
                                <div class="bankPart">
                                    <div class="profileTitle form-line profileInfo">
                                        <span class="primaryTitle">${sessionScope.languageJSON.profile.primary}</span>
                                        <span class="currentTitle">${sessionScope.languageJSON.label.current}</span>
                                        <div class="newTitle">${sessionScope.languageJSON.label.new}</div>
                                    </div>
                                </div>
                            </div>
                            <c:forEach var="bank" items="${banks}" varStatus="count">
                                <c:set var="readOnlyBank" value="false"/>
                                <c:if test="${payrollOption.fieldDisplayOptionBank =='I' || bank.invalidAccount}"> 
                                    <c:set var="readOnlyBank" value="true"/>
                                </c:if>
                                
                                <c:set var="hasInvalid" value=""/>
                                <c:if test ="${bank.invalidAccount}">
                                    <c:set var="hasInvalid" value="hasInvalid"/>
                                </c:if>
                                
                                <c:set var="hideDelete" value=""/>
                                <c:if test="${(bank.code.code =='' && bank.accountNumber ==''  && bank.accountType.code =='') ||  bank.invalidAccount}">
                                    <c:set var="hideDelete" value="hide"/>
                                </c:if>
                            <form class="profile-item border-0 bankAccountBlock updateBankForm  <c:if test="${bank.isDelete == false}">usedBank</c:if>  <c:if test="${bank.isDelete == true}">isDelete</c:if>"
                                    id="bankAccountForm_${count.index}"
                                    action="updateBank" method="POST" >
                                <input type="hidden" name="freq" class="hidden_freq_update" />
                                <div class="bankPart" role="main" aria-label="<c:if test="${bank.isDelete == true}">${sessionScope.languageJSON.accessHint.deletedPart}</c:if>">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <div class="profile-left">
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.bankName}</div>
                                            <div class="primaryBank">
                                                    <input
                                                        class="icheckRadioBank"
                                                        id="primary_${count.index}"
                                                        type="radio"
                                                        aria-label="${sessionScope.languageJSON.accessHint.primaryAccountCheckbox}" 
                                                        aria-disabled="${bank.isDelete}"
                                                        <c:if test="${bank.isDelete == true || readOnlyBank == true}">disabled="disabled"</c:if>
                                                        name="primaryAccount"
                                                    />
                                            </div>
                                            <div class="profile-desc">
                                                <span class="haveValue">${bank.code.description}</span>
                                                
                                                <input type="hidden"  class="form-control bankcode bankNewCode"  name="codeNew"  id="codeNew_${count.index}" value="${bank.codeNew.code}" />
                                                <input type="hidden" id="code_${count.index}" name="code" value="${bank.code.code}" />
                                                
                                                <div class="valueInput group-line">
                                                    <div class="form-group inputDisabled">
                                                        <input
                                                            class="form-control name <c:if test="${bank.code.description != bank.codeNew.description}">active</c:if>"
                                                            type="text"
                                                            name="description"
                                                            aria-label="${sessionScope.languageJSON.profile.bankName}" 
                                                            aria-disabled="${bank.isDelete}"
                                                            tabindex="-1"
                                                            <c:if test="${bank.isDelete == true || readOnlyBank == true}">disabled="disabled"</c:if>
                                                            value="${bank.codeNew.description}"
                                                        />
                                                    </div>

                                                    <div class="form-group inputDisabled">
                                                        <input
                                                            class="form-control code <c:if test="${bank.code.subCode != bank.codeNew.subCode}">active</c:if>"
                                                            type="text"
                                                            name="subCode"
                                                            aria-label="${sessionScope.languageJSON.accessHint.bankCode}" 
                                                            aria-disabled="${bank.isDelete}"
                                                            tabindex="-1"
                                                            <c:if test="${bank.isDelete == true || readOnlyBank == true}">disabled="disabled"</c:if>
                                                            value="${bank.codeNew.subCode}"
                                                        />
                                                    </div>

                                                    <button
                                                        class="btn btn-secondary xs getBank"
                                                        type="button" role="button"
                                                        data-toggle= "modal"
                                                        data-target="#selectBankModal"
                                                        aria-label="${sessionScope.languageJSON.profile.chooseBank}"
                                                        aria-disabled="${bank.isDelete}"
                                                        <c:if test="${bank.isDelete == true || readOnlyBank == true}">disabled="disabled"</c:if>
                                                    >
                                                        <i class="fa fa-ellipsis-h"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">
                                                    ${sessionScope.languageJSON.profile.bankAcctNbr}
                                            </div>
                                            <div class="primaryBank"></div>
                                            <div class="profile-desc">
                                                <span class="haveValue" id="accountNumber_${count.index}"
                                                    >${bank.accountNumber}</span
                                                >
                                                <input type="hidden" name="accountNumber" value="${bank.accountNumber}">
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control <c:if test="${bank.accountNumber != bank.accountNumberNew}">active</c:if>"
                                                        type="text"
                                                        aria-label="${sessionScope.languageJSON.profile.bankAcctNbr}"
                                                        aria-disabled="${bank.isDelete}"
                                                        <c:if test="${bank.isDelete == true || readOnlyBank == true}">disabled="disabled"</c:if>
                                                        name="accountNumberNew"
                                                        id="accountNumberNew_${count.index}"
                                                        value="${bank.accountNumberNew}"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">
                                                    ${sessionScope.languageJSON.profile.bankAcctType}
                                            </div>
                                            <div class="primaryBank"></div>
                                            <div class="profile-desc">
                                                <span class="haveValue"  id="accountType_${count.index}"
                                                    >${bank.accountType.displayLabel}</span
                                                >
                                                <input type="hidden" name="accountType" value="${bank.accountType.displayLabel}">
                                                <div class="form-group valueInput">
                                                    <select
                                                        class="form-control <c:if test="${bank.accountType.code != bank.accountTypeNew.code}">active</c:if>"
                                                        aria-label="${sessionScope.languageJSON.profile.bankAcctType}"
                                                        id="accountTypeNew_${count.index}"
                                                        aria-disabled="${bank.isDelete}"
                                                        <c:if test="${bank.isDelete == true || readOnlyBank == true}">disabled="disabled"</c:if>
                                                        name="accountTypeNew"
                                                    >
                                                    <c:forEach var="bankType" items="${bankAccountTypes}" varStatus="countBank">
                                                            <c:choose>
                                                                    <c:when test="${bankType.displayLabel==''}">
                                                                            <option value="${bankType.displayLabel}" <c:if test="${bankType.code == bank.accountTypeNew.code}">selected</c:if>>&nbsp;</option>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                            <option value="${bankType.displayLabel}" <c:if test="${bankType.code == bank.accountTypeNew.code}">selected</c:if>>${bankType.displayLabel}</option>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                        
                                                    </c:forEach>

                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">
                                                    ${sessionScope.languageJSON.profile.bankAcctAmt}
                                            </div>
                                            <div class="primaryBank"></div>
                                            <div class="profile-desc bankAmount">
                                                <span class="haveValue" id="displayAmount_${count.index}"
                                                    >${bank.depositAmount.displayAmount}</span
                                                >
                                                <input type="hidden" name="displayAmount" value="${bank.depositAmount.displayAmount}">
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control amount_2 <c:if test="${bank.depositAmount.displayAmount != bank.depositAmountNew.displayAmount}">active</c:if>"
                                                        id="displayAmountNew_${count.index}"
                                                        type="text"
                                                        aria-label="${sessionScope.languageJSON.profile.bankAcctAmt}"
                                                        name="displayAmountNew"
                                                        value="${bank.depositAmountNew.displayAmount}"
                                                        oninput="clearNoNum(this)"
                                                        aria-disabled="${bank.isDelete}"
                                                        <c:if test="${bank.isDelete == true || readOnlyBank == true}">disabled="disabled"</c:if>
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <c:if test="${!readOnlyBank}">
                                        <div class="profile-btn">
                                            <div class="saveOrCancel">
                                                <button
                                                    type="button" role="button"
                                                    class="btn btn-primary save-btn saveUpdateBankBtn"
                                                    id="saveBank_${count.index}" aria-label = "${sessionScope.languageJSON.label.updateBank}"
                                                    onclick="updateBank(${count.index})"
                                                >
                                                ${sessionScope.languageJSON.label.update}
                                                </button>
                                                <button
                                                        type="button" role="button"
                                                        <c:if test="${fn:trim(bank.code.code)!=''||(bank.depositAmountNew.displayAmount!=0&&fn:trim(bank.depositAmountNew.displayAmount)!='0.00')}">style="display:block;"</c:if>
                                                        id="undoBank_${count.index}" aria-label = "${sessionScope.languageJSON.label.undoBank}"
                                                        class="btn btn-secondary undo-btn undoBankHaveModal"   data-toggle="modal" data-target="#undoModal" 
                                                        onclick="undoBank(${count.index})"
                                                    >
                                                    ${sessionScope.languageJSON.label.undo}
                                                </button>
                                                <button
                                                        type="button" role="button" 
                                                        <c:if test="${fn:trim(bank.code.code)==''&&(bank.depositAmountNew.displayAmount==0||fn:trim(bank.depositAmountNew.displayAmount)=='0.00')}">style="display:block;"</c:if>
                                                        id="undoBank_${count.index}" aria-label = "${sessionScope.languageJSON.label.undoBank}"
                                                        class="btn btn-secondary undo-btn undoBankNoModal"
                                                        onclick="undoBank(${count.index})"
                                                    >
                                                    ${sessionScope.languageJSON.label.undo}
                                                </button>
                                                <c:if test="${bank.code.subCode!=''}">
                                                    <c:if test="${fn:length(banks)>1}">
                                                        <button
                                                            type="button" role="button"
                                                            id="deleteBank_${count.index}" aria-label = "${sessionScope.languageJSON.label.deleteBank}"
                                                            class="btn btn-secondary delete-btn ${hideDelete}"  onclick="deleteBankAmount(${count.index})"
                                                            data-toggle="modal" data-target="#deleteModal"
                                                        >
                                                        ${sessionScope.languageJSON.label.delete}
                                                        </button>
                                                    </c:if>
                                                    <c:if test="${fn:length(banks)<=1}">
                                                        <button
                                                            type="button" role="button"
                                                            id="deleteBank_${count.index}" aria-label = "${sessionScope.languageJSON.label.deleteBank}"
                                                            class="btn btn-secondary delete-btn ${hideDelete}"  onclick="deleteBankAmount(${count.index})"
                                                        >
                                                        ${sessionScope.languageJSON.label.delete}
                                                        </button>
                                                    </c:if>
                                                </c:if>
                                            </div>
                                        </div>
                                    </c:if>
                                </div>   
                            </form>
                            </c:forEach>
                            <p class="error-hint updateMessageFailed hide">${sessionScope.languageJSON.validator.updateFailed}</p>
                            <!-- <div>
                                <button
                                        type="button" role="button"
                                        class="btn btn-primary save-btn saveUpdateBankBtn"
                                        id="saveBank" aria-label = "${sessionScope.languageJSON.label.updateBank}"
                                        onclick="updateBank()"
                                    >
                                    ${sessionScope.languageJSON.label.update}
                                </button>
                            </div> -->
                            <br/>
                            <div class="bankPart">
                                <form hidden="hidden" action="updateBank" id="updateBankHidden" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="hidden" name="freq" id="hidden_freq_update" />
                                    <input type="hidden" name="accountNumberNew" id="hidden_accountNumberNew_update" />
                                    <input type="hidden" name="accountNumber" id="hidden_accountNumber_update"/>
                                    <input type="hidden" name="code" id="hidden_code_update" />
                                    <input type="hidden" name="codeNew" id="hidden_codeNew_update"/>
                                    
                                    <input type="hidden" name="accountType" id="hidden_accountType_update"/>
                                    <input type="hidden" name="accountTypeNew" id="hidden_accountTypeNew_update"/>
                                    <input type="hidden" name="displayAmount" id="hidden_displayAmount_update" />
                                    <input type="hidden" name="displayAmountNew" id="hidden_displayAmountNew_update" />
                                </form>
                                <form hidden="hidden" action="undoBank" id="undoBankHidden" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="hidden" name="freq" id="hidden_freq_undo"   aria-label="" />
                                    <input type="hidden" name="accountNumberNew" id="hidden_accountNumberNew_undo"/>
                                    <input type="hidden" name="accountNumber" id="hidden_accountNumber_undo" />
                                    <input type="hidden" name="code" id="hidden_code_undo"  />
                                    <input type="hidden" name="codeNew" id="hidden_codeNew_undo"/>
                                </form>
                                <form hidden="hidden" action="deleteBank"  id="deleteBankHidden" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="hidden" name="freq" id="hidden_freq_delete"  />
                                    <input type="hidden" name="accountNumber" id="hidden_accountNumber_delete"  />
                                    <input type="hidden" name="code" id="hidden_code_delete" />
                                    <input type="hidden" name="accountType" id="hidden_accountType_delete" />
                                    <input type="hidden" name="displayAmount" id="hidden_displayAmount_delete" />
                                </form>
                                <form hidden="hidden" action="saveBank" id="saveBankHidden" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="hidden" name="freq" id="hiddenfreq"   aria-label=""/>
                                    <input type="hidden" name="displayAmount" id="hiddendisplayAmount"/>
                                    <input type="hidden" name="displayLabel" id="hiddendisplayLabel" />
                                    <input type="hidden" name="accountNumber" id="hiddenaccountNumber" />
                                    <input type="hidden" name="subCode" id="hiddensubCode"  />
                                    <input type="hidden" name="code" id="hiddenCode"  />
                                    <input type="hidden" name="description" id="hiddendescription" />
                                    <input type="hidden" name="bankArray" id="bankArrayGroup"  />
                                </form>
                                <form
                                    class="profile-item border-0 activeEdit addBankForm"
                                    id="addBankAccountForm"
                                    method="POST"
                                >
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="hidden" class="form-control bankcode" id="newBankCode" name="code" />
                                    <div class="profile-left">
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">
                                                    ${sessionScope.languageJSON.profile.bankName}
                                            </div>
                                            <div class="primaryBank">
                                                    <input
                                                        class="icheckRadioBank"
                                                        id="primary_add"
                                                        type="radio"
                                                        aria-label="${sessionScope.languageJSON.accessHint.primaryAccountCheckbox}" 
                                                        name="primaryAccount"
                                                    />
                                            </div>
                                            <div class="profile-desc">
                                                <div class="valueInput group-line">
                                                    <div class="form-group inputDisabled">
                                                        <input
                                                            class="form-control name"
                                                            type="text"
                                                            name="description"
                                                            id="saveBankDescription"
                                                            aria-label="${sessionScope.languageJSON.accessHint.bankName}"
                                                            value=""
                                                            tabindex="-1"
                                                        />
                                                    </div>

                                                    <div class="form-group inputDisabled">
                                                        <input
                                                            class="form-control code"
                                                            type="text"
                                                            name="subCode"
                                                            id="saveBankCode"
                                                            aria-label="${sessionScope.languageJSON.accessHint.bankCode}"
                                                            value=""
                                                            tabindex="-1"
                                                        />
                                                    </div>
                                                    <button
                                                        class="btn btn-secondary xs getBank"
                                                        type="button" role="button"
                                                        data-toggle= "modal"
                                                        data-target="#selectBankModal"
                                                        aria-label="${sessionScope.languageJSON.profile.chooseBank}"
                                                    >
                                                        <i
                                                            class="fa fa-ellipsis-h"
                                                        ></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">
                                                    ${sessionScope.languageJSON.profile.bankAcctNbr}
                                            </div>
                                            <div class="primaryBank"></div>
                                            <div class="profile-desc">
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control"
                                                        type="text"
                                                        aria-label="${sessionScope.languageJSON.profile.bankAcctNbr}"
                                                        name="accountNumber"
                                                        id="saveBankAccountNumber"
                                                        value=""
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">
                                                    ${sessionScope.languageJSON.profile.bankAcctType}
                                            </div>
                                            <div class="primaryBank"></div>
                                            <div class="profile-desc">
                                                <div class="form-group valueInput">
                                                    <select
                                                        class="form-control"
                                                        aria-label="${sessionScope.languageJSON.profile.bankAcctType}"
                                                        name="displayLabel"
                                                        id="saveBankDisplayLabel"
                                                    >
                                                    <c:forEach var="bankType" items="${bankAccountTypes}" varStatus="count">
                                                            <c:choose>
                                                                    <c:when test="${bankType.displayLabel==''}">
                                                                            <option value="${bankType.displayLabel}">&nbsp;</option>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                            <option value="${bankType.displayLabel}">${bankType.displayLabel}</option>
                                                                    </c:otherwise>
                                                                 </c:choose>
                                                       
                                                    </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">
                                                    ${sessionScope.languageJSON.profile.bankAcctAmt}
                                            </div>
                                            <div class="primaryBank"></div>
                                            <div class="profile-desc bankAmount">
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control amount_2"
                                                        type="text"
                                                        aria-label="${sessionScope.languageJSON.profile.bankAcctType}"
                                                        name="displayAmount"
                                                        id="saveBankDisplayAmount"
                                                        value="0.00"
                                                        oninput="clearNoNum(this)"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="profile-btn">
                                        <div class="saveOrCancel flex-line">
                                            <button
                                                type="button" role="button"
                                                class="btn btn-primary save-btn"
                                                id="saveNewBank"
                                            >
                                            ${sessionScope.languageJSON.label.save}
                                            </button>
                                            <button
                                                type="button" role="button"
                                                class="btn btn-secondary cancel-add-btn"
                                               
                                            >
                                            ${sessionScope.languageJSON.label.cancel}
                                            </button>
                                        </div>
                                    </div>
                                </form>
                                <button <c:if test="${payrollOption.fieldDisplayOptionBank !='U'}">disabled="disabled"</c:if>
                                    type="button" role="button" aria-label = "${sessionScope.languageJSON.label.addBank}"
                                    class="btn btn-primary add-bank-btn"
                                >
                                <span>${sessionScope.languageJSON.label.add}</span>
                                </button>
                                <p class="error-hint hide bankSizeError" role="alert" aria-atomic="true">
                                        ${sessionScope.languageJSON.validator.maximumBankAmmount}
                                </p>
                            </div>
                        </c:if>
                        </c:if>
                    </div>
                </section>
            </main>
        </div>
        <div class="loadingOn" style="display:none;">
            <div class="loadEffect">
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                <span></span>
        </div>
    </div>
        <%@ include file="commons/footer.jsp"%>
        <%@ include file="modal/changePassword.jsp"%>
        <%@ include file="modal/undoModal.jsp"%>
        <%@ include file="modal/deleteModal.jsp"%>
        <%@ include file="modal/searchForBank.jsp"%>
    </body>
    <script>
        var personalValidatorValue = '${demoOptions.fieldDisplayOptionName}'
        var maritalStatusValidatorValue = '${demoOptions.fieldDisplayOptionMarital}'
        var driverLicenseValidatorValue = '${demoOptions.fieldDisplayOptionDriversLicense}'
        var restrictionCodeFormValidatorValue = '${demoOptions.fieldDisplayOptionRestrictionCodes}'
        var emailFormValidatorValue = '${demoOptions.fieldDisplayOptionEmail}'
        var emergencyContactFormValidatorValue = '${demoOptions.fieldDisplayOptionEmergencyContact}'
        var mailingAddressValidatorValue = '${demoOptions.fieldDisplayOptionMailAddr}'
        var alternativeAddressValidatorValue = '${demoOptions.fieldDisplayOptionAltAddr}'

        var cellPhoneValidatorValue = '${demoOptions.fieldDisplayOptionCellPhone}'
        var homePhoneValidatorValue = '${demoOptions.fieldDisplayOptionHomePhone}'
        var workPhoneValidatorValue = '${demoOptions.fieldDisplayOptionWorkPhone}'
        
        var w4InfoValidatorValue = '${payrollOption.fieldDisplayOptionInfo}'
        var bankAccountValidatorValue = '${payrollOption.fieldDisplayOptionBank}'
        var passwordModalShow = '${changePSW}'
    </script>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/profile.js"></script>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/modal/searchForBank.js"></script>
</html>
