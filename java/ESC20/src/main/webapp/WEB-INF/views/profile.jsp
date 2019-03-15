<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
    <head>
        <title data-localize="headTitle.profile"></title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <%@ include file="commons/header.jsp"%>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <%@ include file="commons/bar.jsp"%>
            <main class="content-wrapper" tabindex="-1">
                <section class="content">
                    <div class="content-white no-title profile">
                            <c:if test="${sessionScope.options.enableSelfServiceDemographic == true}">
                                <div class="profile-top first-child">
                                    <div class="profile-avatar" >
                                        <div>
                                                <div
                                                class="avatar"
                                                id="imgContentImg"
                                                style="background-image:url(${sessionScope.userDetail.avatar})"
                                            >
                                                <!-- <input id="userName" hidden="hidden" type="text" value="${sessionScope.userDetail.nameF}">
                                                <input id="avatarImg" hidden="hidden" type="text" name="file">
                                                <input id="avatarImgName" hidden="hidden" type="text" name="fileName">
                                                <input class="avatar-file" type="file" name="file"  id="imgUpFile"  onchange="startRead()"  accept="image/*"/> -->
                                                <button type="button" class="avatar-word" data-localize="profile.change" data-toggle="modal" data-target="#changeAvatarModal"></button>
                                            </div>
                                            <button type="button" class="btn btn-primary sm" data-toggle="modal" data-target="#changePasswordModal" data-localize="label.changePassword" data-localize-location="title">
                                                    <span data-localize="label.changePassword"></span>
                                                </button>
                                            </div>
                                        
                                    </div>
                                    <form class="profile-item" id="personalForm" action="saveName" method="POST">
                                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <div class="profile-left">
                                            <div class="profileTitle form-line profileInfo">
                                                <span class="currentTitle" data-localize="label.current"></span>
                                                <div class="newTitle" data-localize="label.new"></div>
                                            </div>
                                            <div class="profile-item-line form-line">
                                                <label class="profile-title" data-localize="profile.title"></label>
                                                <div class="profile-desc">
                                                    <span class="haveValue"
                                                        >${sessionScope.userDetail.namePre}</span
                                                    >
                                                    <input hidden="hidden" type="text" name="empNbr" value="${nameRequest.id.empNbr}" aria-label="" data-localize="accessHint.employeeNumber">
                                                    <input hidden="hidden" type="text" name="reqDts" value="${nameRequest.id.reqDts}" aria-label="" data-localize="accessHint.reqDts">
                                                    <div class="form-group valueInput">
                                                        <select
                                                            class="form-control <c:if test="${sessionScope.userDetail.namePre != nameRequest.namePreNew }">active</c:if>"
                                                            aria-label=""
                                                            data-localize="profile.title"
                                                            name="namePreNew"
                                                            id="titleString"
                                                        >
                                                        <c:forEach var="title" items="${titleOptions}" varStatus="count">
                                                            <option value="${title.description}" <c:if test="${title.description == nameRequest.namePreNew }">selected</c:if>>${title.description}</option>
                                                        </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="profile-item-line form-line">
                                                <label class="profile-title"  data-localize="profile.firstname"></label>
                                                <div class="profile-desc">
                                                    <span class="haveValue"
                                                        >${sessionScope.userDetail.nameF}</span
                                                    >
                                                    <div class="form-group valueInput">
                                                        <input
                                                            class="form-control <c:if test="${sessionScope.userDetail.nameF != nameRequest.nameFNew}">active</c:if>"
                                                            type="text"
                                                            value="${nameRequest.nameFNew}"
                                                            aria-label=""
                                                            data-localize="profile.firstname"
                                                            name="nameFNew"
                                                            id="firstName"
                                                            maxlength="17"
                                                        />
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="profile-item-line form-line">
                                                <label class="profile-title" data-localize="profile.middleName"
                                                    ></label
                                                >
                                                <div class="profile-desc">
                                                    <span class="haveValue"
                                                        >${sessionScope.userDetail.nameM}</span
                                                    >
                                                    <div class="form-group valueInput">
                                                        <input
                                                            class="form-control <c:if test="${sessionScope.userDetail.nameM != nameRequest.nameMNew}">active</c:if>"
                                                            type="text"
                                                            value="${nameRequest.nameMNew}"
                                                            aria-label=""
                                                            data-localize="profile.middleName"
                                                            name="nameMNew"
                                                            id="middleName"
                                                            maxlength="14"
                                                        />
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="profile-item-line form-line">
                                                <label class="profile-title" data-localize="profile.lastname"
                                                    ></label
                                                >
                                                <div class="profile-desc">
                                                    <span class="haveValue"
                                                        >${sessionScope.userDetail.nameL}</span
                                                    >
                                                    <div class="form-group valueInput">
                                                        <input
                                                            class="form-control <c:if test="${sessionScope.userDetail.nameL != nameRequest.nameLNew}">active</c:if>"
                                                            type="text"
                                                            value="${nameRequest.nameLNew}"
                                                            aria-label=""
                                                            data-localize="profile.lastname"
                                                            name="nameLNew"
                                                            id="lastName"
                                                            maxlength="25"
                                                        />
                                                    </div>
                                                </div>
                                            </div>
                                            
                                            <div class="profile-item-line form-line">
                                                <label class="profile-title" data-localize="profile.generation"
                                                    ></label
                                                >
                                                <div class="profile-desc">
                                                    <span class="haveValue"
                                                        >${sessionScope.userDetail.nameGen}</span
                                                    >
                                                    <div class="form-group valueInput">
                                                        <select
                                                            class="form-control <c:if test="${sessionScope.userDetail.nameGen != nameRequest.nameGenNew}">active</c:if>"
                                                            value="${nameRequest.nameGenNew}"
                                                            aria-label=""
                                                            data-localize="profile.generation"
                                                            name="nameGenNew"
                                                            id="generation"
                                                        >
                                                            <c:forEach var="gen" items="${generationOptions}" varStatus="count">
                                                                <option value="${gen.code}" <c:if test="${gen.code == nameRequest.nameGenNew }">selected</c:if>>${gen.description}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-btn">
                                            <div class="edit">
                                                <button
                                                    type="button"
                                                    class="btn btn-primary edit-btn" data-localize="label.edit"
                                                >
                                                </button>
                                            </div>

                                            <div class="saveOrCancel">
                                                <button
                                                    type="submit"
                                                    class="btn btn-primary save-btn" data-localize="label.update"
                                                    id="savePersonal"
                                                >
                                                
                                                </button>
                                                <button
                                                    type="button"
                                                    id="undoNameRequest"
                                                    class="btn btn-secondary"  data-localize="label.undo"  data-toggle="modal" data-target="#undoModal" 
                                                >
                                                
                                                </button>
                                                <button
                                                    type="button"
                                                    class="btn btn-secondary cancel-btn"  data-localize="label.cancel"
                                                >
                                                
                                                </button>
                                            </div>
                                        </div>
                                    </form>
                                    <form hidden="hidden" action="deleteNameRequest" id="deleteNameRequest" method="POST">
                                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    </form>
                                </div>

                                <p class="sub-title" data-localize="profile.MaritalStatus"></p>
                                <form class="profile-item" id="maritalStatusForm" action="saveMarital" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <div class="profile-left">
                                            <div class="profileTitle form-line profileInfo">
                                                    <span class="currentTitle" data-localize="label.current"></span>
                                                    <div class="newTitle" data-localize="label.new"></div>
                                                </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.local"></div>
                                            <div class="profile-desc">
                                                <span class="haveValue">
                                                    <c:forEach var="marital" items="${maritalOptions}" varStatus="count">
                                                        <c:if test="${marital.code == sessionScope.userDetail.maritalStat }">${marital.displayLabel}</c:if>
                                                    </c:forEach>
                                                </span>
                                                <input hidden="hidden" type="text" name="empNbr" value="${mrtlRequest.id.empNbr}"  aria-label="" data-localize="accessHint.employeeNumber">
                                                    <input hidden="hidden" type="text" name="reqDts" value="${mrtlRequest.id.reqDts}" aria-label="" data-localize="accessHint.reqDts">
                                                <div class="form-group valueInput">
                                                    <select
                                                        id="maritalStatus"
                                                        name="maritalStatNew"
                                                        class="form-control <c:if test="${sessionScope.userDetail.maritalStat != mrtlRequest.maritalStatNew }">active</c:if>"
                                                        aria-label=""
                                                        data-localize="profile.local"
                                                        
                                                    >
                                                        <c:forEach var="marital" items="${maritalOptions}" varStatus="count">
                                                            <option value="${marital.code}" <c:if test="${marital.code == mrtlRequest.maritalStatNew }">selected</c:if>>${marital.displayLabel}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="profile-btn">
                                        <div class="edit">
                                            <button
                                                type="button"
                                                class="btn btn-primary edit-btn" data-localize="label.edit"
                                            >
                                            
                                            </button>
                                        </div>
                                        <div class="saveOrCancel">
                                            <button
                                                type="submit"
                                                class="btn btn-primary save-btn" data-localize="label.update"
                                                id="saveMarital"
                                            >
                                            
                                            </button>
                                            <button
                                                    type="button"
                                                    id="undoMaritalRequest"
                                                    class="btn btn-secondary"  data-localize="label.undo"  data-toggle="modal" data-target="#undoModal" 
                                                >
                                                
                                                </button>
                                            <button
                                                type="button"
                                                class="btn btn-secondary cancel-btn"  data-localize="label.cancel"
                                            >
                                            
                                            </button>
                                        </div>
                                    </div>
                                </form>
                                <form hidden="hidden" action="deleteMaritalRequest" id="deleteMaritalRequest" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                                <p class="sub-title" data-localize="profile.driversLicense"></p>
                                <form class="profile-item" id="driverLicenseForm" action="saveDriversLicense" method="POST">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input hidden="hidden" type="text" name="empNbr" value="${licRequest.id.empNbr}" aria-label="" data-localize="accessHint.employeeNumber">
                                    <input hidden="hidden" type="text" name="reqDts" value="${licRequest.id.reqDts}" aria-label="" data-localize="accessHint.reqDts">
                                    <div class="profile-left">
                                            <div class="profileTitle form-line profileInfo">
                                                    <span class="currentTitle" data-localize="label.current"></span>
                                                    <div class="newTitle" data-localize="label.new"></div>
                                                </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.number"></div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.driversLicNbr}</span
                                                >
                                                <div class="form-group valueInput">
                                                    <input
                                                        type="text"
                                                        class="form-control <c:if test="${sessionScope.userDetail.driversLicNbr != licRequest.driversLicNbrNew }">active</c:if>"
                                                        value="${licRequest.driversLicNbrNew}"
                                                        name="driversLicNbrNew"
                                                        id="driversLicenseNumber"
                                                        data-localize="profile.driverLicenseNum"
                                                        aria-label=""
                                                        
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.state"></div>
                                            <div class="profile-desc">
                                                <span class="haveValue">
                                                    <c:forEach var="states" items="${statesOptions}" varStatus="count">
                                                            <c:if test="${states.code == sessionScope.userDetail.driversLicSt }">${states.displayLabel}</c:if>
                                                    </c:forEach>
                                                </span>
                                                
                                                <div class="form-group valueInput">
                                                    <select
                                                        id="driversState"
                                                        name="driversLicStNew"
                                                        value="${licRequest.driversLicStNew}"                                                
                                                        class="form-control <c:if test="${sessionScope.userDetail.driversLicSt != licRequest.driversLicStNew }">active</c:if>"
                                                        aria-label=""
                                                        data-localize="profile.driversLicenseState"
                                                    >
                                                    <c:forEach var="states" items="${statesOptions}" varStatus="count">
                                                            <option value="${states.code}" <c:if test="${states.code == licRequest.driversLicStNew }">selected</c:if>>${states.displayLabel}</option>
                                                        </c:forEach>
                                                        </select>
                                                </div>
                                            
                                            </div>
                                        </div>
                                    </div>
                                    <div class="profile-btn">
                                        <div class="edit">
                                            <button
                                                type="button"
                                                class="btn btn-primary edit-btn" data-localize="label.edit"
                                            >
                                            
                                            </button>
                                        </div>
                                        <div class="saveOrCancel">
                                            <button
                                                type="submit"
                                                class="btn btn-primary save-btn" data-localize="label.update"
                                                id="saveDriver"
                                            >
                                            
                                            </button>
                                            <button
                                                    type="button"
                                                    id="undoDriverLicense"
                                                    class="btn btn-secondary"  data-localize="label.undo"  data-toggle="modal" data-target="#undoModal" 
                                                >
                                                
                                                </button>
                                            <button
                                                type="button"
                                                class="btn btn-secondary cancel-btn"  data-localize="label.cancel"
                                            >
                                            
                                            </button>
                                        </div>
                                    </div>
                                </form>
                                <form hidden="hidden" action="deleteDriversLicenseRequest" id="deleteDriversLicense" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                                <p class="sub-title" data-localize="profile.restrictionCodes"></p>
                                <form class="profile-item" id="restrictionCodeForm" action="saveRestrictionCodes" method="POST">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input hidden="hidden" type="text" name="empNbr" value="${restrictRequest.id.empNbr}" aria-label="" data-localize="accessHint.employeeNumber">
                                    <input hidden="hidden" type="text" name="reqDts" value="${restrictRequest.id.reqDts}" aria-label="" data-localize="accessHint.reqDts">
                                    <div class="profile-left">
                                            <div class="profileTitle form-line profileInfo">
                                                    <span class="currentTitle" data-localize="label.current"></span>
                                                    <div class="newTitle" data-localize="label.new"></div>
                                                </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.local"></div>
                                            <div class="profile-desc">
                                                <span class="haveValue">
                                                    <c:forEach var="restrictions" items="${restrictionsOptions}" varStatus="count">
                                                        <c:if test="${restrictions.code == sessionScope.userDetail.restrictCd }">${restrictions.displayLabel}</c:if>
                                                    </c:forEach>
                                                </span>
                                                
                                                <div class="form-group valueInput">
                                                    <select
                                                        id="restrictionCodesLocalRestriction"
                                                        name="restrictCdNew"
                                                        class="form-control <c:if test="${sessionScope.userDetail.restrictCd != restrictRequest.restrictCdNew }">active</c:if>"
                                                        aria-label=""
                                                        data-localize="profile.restrictionCodesLocal"
                                                        
                                                    >
                                                    <c:forEach var="restrictions" items="${restrictionsOptions}" varStatus="count">
                                                            <option value="${restrictions.code}" <c:if test="${restrictions.code == restrictRequest.restrictCdNew }">selected</c:if>>${restrictions.displayLabel}</option>
                                                        </c:forEach>
                                                        </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.public"></div>
                                            <div class="profile-desc">
                                                <span class="haveValue">
                                                        <c:forEach var="restriction" items="${restrictionsOptions}" varStatus="count">
                                                                <c:if test="${restriction.code == sessionScope.userDetail.restrictCdPublic }">${restriction.displayLabel}</c:if>
                                                    </c:forEach>
                                                </span>
                                            
                                                <div class="form-group valueInput">
                                                    <select
                                                        id="restrictionCodesPublicRestriction"
                                                        name="restrictCdPublicNew"
                                                        class="form-control <c:if test="${sessionScope.userDetail.restrictCdPublic != restrictRequest.restrictCdPublicNew }">active</c:if>"
                                                        aria-label=""
                                                        data-localize="profile.restrictionCodesPublic"
                                                    >
                                                    <c:forEach var="restriction" items="${restrictionsOptions}" varStatus="count">
                                                            <option value="${restriction.code}" <c:if test="${restriction.code == restrictRequest.restrictCdPublicNew }">selected</c:if>>${restriction.displayLabel}</option>
                                                        </c:forEach>
                                                        </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="profile-btn">
                                        <div class="edit">
                                            <button
                                                type="button"
                                                class="btn btn-primary edit-btn" data-localize="label.edit"
                                            >
                                            
                                            </button>
                                        </div>
                                        <div class="saveOrCancel">
                                            <button
                                                type="submit"
                                                class="btn btn-primary save-btn" data-localize="label.update"
                                                id="saveRestrict"
                                            >
                                            
                                            </button>
                                            <button
                                                    type="button"
                                                    id="undoRestriction"
                                                    class="btn btn-secondary"  data-localize="label.undo"  data-toggle="modal" data-target="#undoModal" 
                                                >
                                                
                                                </button>
                                            <button
                                                type="button"
                                                class="btn btn-secondary cancel-btn"  data-localize="label.cancel"
                                            >
                                            
                                            </button>
                                        </div>
                                    </div>
                                </form>
                                <form hidden="hidden" action="deleteRestrictionCodesRequest" id="deleteRestrictionCodesRequest" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                                <p class="sub-title" data-localize="profile.email"></p>
                                <form class="profile-item" id="emailForm" action="saveEmail" method="POST">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input hidden="hidden" type="text" name="empNbr" value="${emailRequest.id.empNbr}" aria-label="" data-localize="accessHint.employeeNumber">
                                        <input hidden="hidden" type="text" name="reqDts" value="${emailRequest.id.reqDts}" aria-label="" data-localize="accessHint.reqDts">
                                    <div class="profile-left">
                                            <div class="profileTitle form-line profileInfo">
                                                    <span class="currentTitle" data-localize="label.current"></span>
                                                    <div class="newTitle" data-localize="label.new"></div>
                                                </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.workEmail"></div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.email}</span
                                                >
                                                <div class="form-group valueInput error-vertical">
                                                    <input
                                                        class="form-control"
                                                        name="emailNew"
                                                        value="${emailRequest.emailNew}"
                                                        id="emailWorkEmail"
                                                        class="form-control <c:if test="${sessionScope.userDetail.email != emailRequest.emailNew}">active</c:if>"
                                                        aria-label=""
                                                        data-localize="profile.workEmail"
                                                        
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.verifyEmail"></div>
                                            <div class="profile-desc">
                                                <span class="haveValue"></span>
                                                <div class="form-group valueInput error-vertical">
                                                    <input
                                                        class="form-control"
                                                        name="emailNewVerify"
                                                        value=""
                                                        id="emailVerifyWorkEmail"
                                                        class="form-control"
                                                        aria-label=""
                                                        data-localize="profile.verifyEmail"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.homeEmail"></div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.hmEmail}</span
                                                >
                                                <div class="form-group valueInput error-vertical">
                                                    <input
                                                        class="form-control <c:if test="${sessionScope.userDetail.hmEmail != emailRequest.hmEmailNew}">active</c:if>"
                                                        name="hmEmailNew"
                                                        id="emailHomeEmail"
                                                        aria-label=""
                                                        data-localize="profile.homeEmail"
                                                        value="${emailRequest.hmEmailNew}"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.verifyEmail"></div>
                                            <div class="profile-desc">
                                                <span class="haveValue"></span>
                                                <div class="form-group valueInput error-vertical">
                                                    <input
                                                        class="form-control"
                                                        name="hmEmailVerifyNew"
                                                        value=""
                                                        id="emailVerifyHomeEmail"
                                                        class="form-control"
                                                        aria-label=""
                                                        data-localize="profile.verifyEmail"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="profile-btn">
                                        <div class="edit">
                                            <button
                                                type="button"
                                                class="btn btn-primary edit-btn" data-localize="label.edit"
                                            >
                                            
                                            </button>
                                        </div>
                                        <div class="saveOrCancel">
                                            <button
                                                type="button"
                                                class="btn btn-primary save-btn" data-localize="label.update"
                                                id="saveEmail"
                                            >
                                            
                                            </button>
                                            <button
                                                    type="button"
                                                    id="undoEmail"
                                                    class="btn btn-secondary"  data-localize="label.undo"  data-toggle="modal" data-target="#undoModal" 
                                                >
                                                
                                                </button>
                                            <button
                                                type="button"
                                                class="btn btn-secondary cancel-btn"  data-localize="label.cancel"
                                            >
                                            
                                            </button>
                                        </div>
                                    </div>
                                </form>
                                <form hidden="hidden" action="deleteEmail" id="deleteEmail" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                                <p class="sub-title" data-localize="profile.emergenceContactInfo"></p>
                                <form class="profile-item" id="emergencyContactForm" action="saveEmergencyContact" method="POST">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input hidden="hidden" type="text" name="empNbr" value="${emerRequest.id.empNbr}" aria-label="" data-localize="accessHint.employeeNumber">
                                        <input hidden="hidden" type="text" name="reqDts" value="${emerRequest.id.reqDts}" aria-label="" data-localize="accessHint.reqDts">
                                    <div class="profile-left">
                                            <div class="profileTitle form-line profileInfo">
                                                    <span class="currentTitle" data-localize="label.current"></span>
                                                    <div class="newTitle" data-localize="label.new"></div>
                                                </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.name"></div>
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
                                                        aria-label=""
                                                        data-localize="profile.emergencyContactName"
                                                        maxlength="26"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.phoneNumber">
                                                
                                            </div>
                                            <div class="profile-desc">
                                                <span class="haveValue">
                                                    (${sessionScope.userDetail.emerPhoneAc})
                                                    ${sessionScope.userDetail.emerPhoneNbr}
                                                    &nbsp;&nbsp; <span data-localize="profile.ext"></span>
                                                    ${sessionScope.userDetail.emerPhoneExt}
                                                </span>
                                                <div class="valueInput flex">
                                                    <div class="form-group">
                                                        <input
                                                            class="form-control phoneAreaCode <c:if test="${sessionScope.userDetail.emerPhoneAc != emerRequest.emerPhoneAcNew}">active</c:if>"
                                                            name="emerPhoneAcNew"
                                                            id="emergencyContactAreaCode"
                                                            aria-label=""
                                                            data-localize="profile.emergencyContactAreaCode"
                                                            value="${emerRequest.emerPhoneAcNew}"
                                                        />
                                                    </div>
                                                    <div class="form-group">
                                                        <input
                                                            class="form-control phone-input <c:if test="${sessionScope.userDetail.emerPhoneNbr != emerRequest.emerPhoneNbrNew}">active</c:if>"
                                                            name="emerPhoneNbrNew"
                                                            id="emergencyContactPhoneNumber"
                                                            aria-label=""
                                                            data-localize="profile.emergencyContactPhoneNumber"
                                                            value="${emerRequest.emerPhoneNbrNew}"
                                                        />
                                                    </div>

                                                    &nbsp;&nbsp;<span data-localize="profile.ext"></span>
                                                    <div class="form-group">
                                                        <input
                                                            class="form-control phoneAreaCode <c:if test="${sessionScope.userDetail.emerPhoneExt != emerRequest.emerPhoneExtNew}">active</c:if>"
                                                            name="emerPhoneExtNew"
                                                            id="emergencyContactExtention"
                                                            aria-label=""
                                                            data-localize="profile.emergencyContactExtention"
                                                            value="${emerRequest.emerPhoneExtNew}"
                                                        />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.relationship">
                                                
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
                                                        aria-label=""
                                                        data-localize="profile.relationship"
                                                        value="${emerRequest.emerRelNew}"
                                                        maxlength="25"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.emergencyNotes">
                                                
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
                                                        aria-label=""
                                                        data-localize="profile.emergencyNotes"
                                                        value="${emerRequest.emerNoteNew}"
                                                        maxlength="25"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="profile-btn">
                                        <div class="edit">
                                            <button
                                                type="button"
                                                class="btn btn-primary edit-btn" data-localize="label.edit"
                                            >
                                            
                                            </button>
                                        </div>
                                        <div class="saveOrCancel">
                                            <button
                                                type="submit"
                                                class="btn btn-primary save-btn" data-localize="label.update"
                                                id="saveEmergency"
                                            >
                                            
                                            </button>
                                            <button
                                                    type="button"
                                                    id="undoEmergencyContact"
                                                    class="btn btn-secondary"  data-localize="label.undo"  data-toggle="modal" data-target="#undoModal" 
                                                >
                                                
                                                </button>
                                            <button
                                                type="button"
                                                class="btn btn-secondary cancel-btn"  data-localize="label.cancel"
                                            >
                                            
                                            </button>
                                        </div>
                                    </div>
                                </form>
                                <form hidden="hidden" action="deleteEmergencyContact" id="deleteEmergencyContact" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                                <p class="sub-title" data-localize="profile.mailingAddress"></p>
                                <form class="profile-item" id="mailingAddressForm" action="saveMailAddr" method="POST">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input hidden="hidden" type="text" name="empNbr" value="${mailAddrRequest.id.empNbr}" aria-label="" data-localize="accessHint.employeeNumber">
                                        <input hidden="hidden" type="text" name="reqDts" value="${mailAddrRequest.id.reqDts}" aria-label="" data-localize="accessHint.reqDts">
                                    <div class="profile-left">
                                            <div class="profileTitle form-line profileInfo">
                                                    <span class="currentTitle" data-localize="label.current"></span>
                                                    <div class="newTitle" data-localize="label.new"></div>
                                                </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.number"></div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.addrNbr}</span
                                                >
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control <c:if test="${sessionScope.userDetail.addrNbr != mailAddrRequest.addrNbrNew}">active</c:if>"
                                                        name="addrNbrNew"
                                                        id="mailAddrNumber"
                                                        aria-label=""
                                                        data-localize="profile.mailingAddressNumber"
                                                        value="${mailAddrRequest.addrNbrNew}"
                                                        
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.streetBox">
                                                
                                            </div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.addrStr}</span
                                                >
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control <c:if test="${sessionScope.userDetail.addrStr != mailAddrRequest.addrStrNew}">active</c:if>"
                                                        name="addrStrNew"
                                                        id="mailAddrStr"
                                                        aria-label=""
                                                        data-localize="profile.streetBox"
                                                        value="${mailAddrRequest.addrStrNew}"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.apt"></div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.addrApt}</span
                                                >
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control <c:if test="${sessionScope.userDetail.addrApt != mailAddrRequest.addrAptNew}">active</c:if>"
                                                        name="addrAptNew"
                                                        id="mailAddrApartment"
                                                        aria-label=""
                                                        data-localize="profile.apt"
                                                        value="${mailAddrRequest.addrAptNew}"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.city"></div>
                                            <div class="profile-desc">
                                                <span class="haveValue">
                                                    ${sessionScope.userDetail.addrCity}
                                                </span>
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control <c:if test="${sessionScope.userDetail.addrCity != mailAddrRequest.addrCityNew}">active</c:if>"
                                                        name="addrCityNew"
                                                        id="mailAddrCity"
                                                        aria-label=""
                                                        data-localize="profile.city"
                                                        value="${mailAddrRequest.addrCityNew}"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.state"></div>
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
                                                        aria-label=""
                                                        data-localize="profile.state"
                                                        class="form-control  <c:if test="${sessionScope.userDetail.addrSt != mailAddrRequest.addrStNew}">active</c:if>"
                                                    >
                                                        <c:forEach var="states" items="${statesOptions}" varStatus="count">
                                                            <option value="${states.code}" <c:if test="${states.code == mailAddrRequest.addrStNew }">selected</c:if>>${states.displayLabel}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.zip"></div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.addrZip}</span
                                                >
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control  <c:if test="${sessionScope.userDetail.addrZip != mailAddrRequest.addrZipNew}">active</c:if>"
                                                        name="addrZipNew"
                                                        id="mailAddrZip"
                                                        aria-label=""
                                                        data-localize="profile.zip"
                                                        value="${mailAddrRequest.addrZipNew}"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.zip4"></div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.addrZip4}</span
                                                >
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control <c:if test="${sessionScope.userDetail.addrZip4 != mailAddrRequest.addrZip4New}">active</c:if>"
                                                        name="addrZip4New"
                                                        id="mailAddrZipPlusFour"
                                                        aria-label=""
                                                        data-localize="profile.zip4"
                                                        value="${mailAddrRequest.addrZip4New}"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="profile-btn">
                                        <div class="edit">
                                            <button
                                                type="button"
                                                class="btn btn-primary edit-btn" data-localize="label.edit"
                                            >
                                            
                                            </button>
                                        </div>
                                        <div class="saveOrCancel">
                                            <button
                                                type="submit"
                                                class="btn btn-primary save-btn" data-localize="label.update"
                                                id="saveMailingAddress"
                                            >
                                            
                                            </button>
                                            <button
                                                    type="button"
                                                    id="undoMailingAddress"
                                                    class="btn btn-secondary"  data-localize="label.undo"  data-toggle="modal" data-target="#undoModal" 
                                                >
                                                
                                                </button>
                                            <button
                                                type="button"
                                                class="btn btn-secondary cancel-btn"  data-localize="label.cancel"
                                            >
                                            
                                            </button>
                                        </div>
                                    </div>
                                </form>
                                <form hidden="hidden" action="deleteMailAddr" id="deleteMailAddr" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                                <p class="sub-title" data-localize="profile.altAddr"></p>
                                <form class="profile-item" id="alternativeAddressForm" action="saveAltMailAddr" method="POST">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input hidden="hidden" type="text" name="empNbr" value="${altMailAddrRequest.id.empNbr}" aria-label="" data-localize="accessHint.employeeNumber">
                                        <input hidden="hidden" type="text" name="reqDts" value="${altMailAddrRequest.id.reqDts}" aria-label="" data-localize="accessHint.reqDts">
                                    <div class="profile-left">
                                            <div class="profileTitle form-line profileInfo">
                                                    <span class="currentTitle" data-localize="label.current"></span>
                                                    <div class="newTitle" data-localize="label.new"></div>
                                                </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.number"></div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.smrAddrNbr}</span
                                                >
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control <c:if test="${sessionScope.userDetail.smrAddrNbr != altMailAddrRequest.smrAddrNbrNew}">active</c:if>"
                                                        name="smrAddrNbrNew"
                                                        id="altAddrNumber"
                                                        aria-label=""
                                                        data-localize="profile.altAddrNumber"
                                                        value="${altMailAddrRequest.smrAddrNbrNew}"
                                                        
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.streetBox">
                                                
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
                                                        aria-label=""
                                                        data-localize="profile.streetBox"
                                                        value="${altMailAddrRequest.smrAddrStrNew}"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.apt"></div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.smrAddrApt}</span
                                                >
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control <c:if test="${sessionScope.userDetail.smrAddrApt != altMailAddrRequest.smrAddrAptNew}">active</c:if>"
                                                        name="smrAddrAptNew"
                                                        id="altAddrApartment"
                                                        aria-label=""
                                                        data-localize="profile.apt"
                                                        value="${altMailAddrRequest.smrAddrAptNew}"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.city"></div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.smrAddrCity}</span
                                                >
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control <c:if test="${sessionScope.userDetail.smrAddrCity != altMailAddrRequest.smrAddrCityNew}">active</c:if>"
                                                        name="smrAddrCityNew"
                                                        id="altAddrCity"
                                                        aria-label=""
                                                        data-localize="profile.city"
                                                        value="${altMailAddrRequest.smrAddrCityNew}"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.state"></div>
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
                                                        aria-label=""
                                                        data-localize="profile.state"
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
                                            <div class="profile-title" data-localize="profile.zip"></div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.smrAddrZip}</span
                                                >
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control <c:if test="${sessionScope.userDetail.smrAddrZip != altMailAddrRequest.smrAddrZipNew}">active</c:if>"
                                                        name="smrAddrZipNew"
                                                        id="altAddrZip"
                                                        aria-label=""
                                                        data-localize="profile.zip"
                                                        value="${altMailAddrRequest.smrAddrZipNew}"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.zip4"></div>
                                            <div class="profile-desc">
                                                <span class="haveValue"
                                                    >${sessionScope.userDetail.smrAddrZip4}</span
                                                >
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control <c:if test="${sessionScope.userDetail.smrAddrZip4 != altMailAddrRequest.smrAddrZip4New}">active</c:if>"
                                                        name="smrAddrZip4New"
                                                        id="altAddrZipPlusFour"
                                                        aria-label=""
                                                        data-localize="profile.zip4"
                                                        value="${altMailAddrRequest.smrAddrZip4New}"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="profile-btn">
                                        <div class="edit">
                                            <button
                                                type="button"
                                                class="btn btn-primary edit-btn" data-localize="label.edit"
                                            >
                                            
                                            </button>
                                        </div>
                                        <div class="saveOrCancel">
                                            <button
                                                type="submit"
                                                class="btn btn-primary save-btn" data-localize="label.update"
                                                id="saveAltAddress"
                                            >
                                            
                                            </button>
                                            <button
                                                    type="button"
                                                    id="undoAlternative"
                                                    class="btn btn-secondary"  data-localize="label.undo"  data-toggle="modal" data-target="#undoModal" 
                                                >
                                                
                                                </button>
                                            <button
                                                type="button"
                                                class="btn btn-secondary cancel-btn"  data-localize="label.cancel"
                                            >
                                            
                                            </button>
                                        </div>
                                    </div>
                                </form>
                                <form hidden="hidden" action="deleteAltMailAddr" id="deleteAltMailAddr" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                                <p class="sub-title" data-localize="profile.phoneNumbers"></p>
                                <form class="profile-item" id="phoneForm" action="savePhone" method="POST">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input hidden="hidden" type="text" name="empNbr" value="${hmRequest.id.empNbr}" aria-label="" data-localize="accessHint.employeeNumber">
                                        <input hidden="hidden" type="text" name="reqDts" value="${hmRequest.id.reqDts}" aria-label="" data-localize="accessHint.reqDts">
                                    <div class="profile-left">
                                            <div class="profileTitle form-line profileInfo">
                                                    <span class="currentTitle" data-localize="label.current"></span>
                                                    <div class="newTitle" data-localize="label.new"></div>
                                                </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.home"></div>
                                            <div class="profile-desc">
                                                <span class="haveValue">
                                                    (${sessionScope.userDetail.phoneArea})
                                                    ${sessionScope.userDetail.phoneNbr}
                                                </span>
                                                <div class="valueInput flex">
                                                    <div class="form-group">
                                                        <input
                                                            class="form-control phoneAreaCode  <c:if test="${sessionScope.userDetail.phoneArea != hmRequest.phoneAreaNew}">active</c:if>"
                                                            name="phoneAreaNew"
                                                            id="homePhoneAreaCode"
                                                            aria-label=""
                                                            data-localize="profile.homePhoneAreaCode"
                                                            value="${hmRequest.phoneAreaNew}"
                                                            
                                                        />
                                                    </div>
                                                    <div class="form-group">
                                                        <input
                                                            class="form-control phone-input  <c:if test="${sessionScope.userDetail.phoneNbr != hmRequest.phoneNbrNew}">active</c:if>"
                                                            name="phoneNbrNew"
                                                            id="homePhonePhoneNumber"
                                                            aria-label=""
                                                            data-localize="profile.homePhonePhoneNumber"
                                                            value="${hmRequest.phoneNbrNew}"
                                                        />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.cell"></div>
                                            <div class="profile-desc">
                                                <span class="haveValue">
                                                    (${sessionScope.userDetail.phoneAreaCell})
                                                    ${sessionScope.userDetail.phoneNbrCell}
                                                </span>
                                                <div class="valueInput flex">
                                                    <div class="form-group">
                                                        <input
                                                            class="form-control phoneAreaCode  <c:if test="${sessionScope.userDetail.phoneAreaCell != cellRequest.phoneAreaCellNew}">active</c:if>"
                                                            name="phoneAreaCellNew"
                                                            id="cellPhoneAreaCode"
                                                            aria-label=""
                                                            data-localize="profile.cellPhoneAreaCode"
                                                            value="${cellRequest.phoneAreaCellNew}"
                                                        />
                                                    </div>

                                                    <div class="form-group">
                                                        <input
                                                            class="form-control phone-input  <c:if test="${sessionScope.userDetail.phoneNbrCell != cellRequest.phoneNbrCellNew}">active</c:if>"
                                                            name="phoneNbrCellNew"
                                                            id="cellPhonePhoneNumber"
                                                            aria-label=""
                                                            data-localize="profile.cellPhonePhoneNumber"
                                                            value="${cellRequest.phoneNbrCellNew}"
                                                        />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.business"></div>
                                            <div class="profile-desc">
                                                <span class="haveValue">
                                                    (${sessionScope.userDetail.phoneAreaBus})
                                                    ${sessionScope.userDetail.phoneNbrBus}&nbsp;&nbsp;&nbsp;&nbsp;
                                                    <span data-localize="profile.ext"></span>
                                                    ${sessionScope.userDetail.busPhoneExt}
                                                </span>
                                                <div class="valueInput flex">
                                                    <div class="form-group">
                                                        <input
                                                            class="form-control phoneAreaCode  <c:if test="${sessionScope.userDetail.phoneAreaBus != busRequest.phoneAreaBusNew}">active</c:if>"
                                                            name="phoneAreaBusNew"
                                                            id="workPhoneAreaCode"
                                                            aria-label=""
                                                            data-localize="profile.workPhoneAreaCode"
                                                            value="${busRequest.phoneAreaBusNew}"
                                                        />
                                                    </div>

                                                    <div class="form-group">
                                                        <input
                                                            class="form-control phone-input  <c:if test="${sessionScope.userDetail.phoneNbrBus != busRequest.phoneNbrBusNew}">active</c:if>"
                                                            name="phoneNbrBusNew"
                                                            id="workPhonePhoneNumber"
                                                            aria-label=""
                                                            data-localize="profile.workPhonePhoneNumber"
                                                            value="${busRequest.phoneNbrBusNew}"
                                                        />
                                                    </div>

                                                    &nbsp;&nbsp;<span data-localize="profile.ext"></span>
                                                    <div class="form-group">
                                                        <input
                                                            class="form-control phoneAreaCode  <c:if test="${sessionScope.userDetail.busPhoneExt != busRequest.busPhoneExtNew}">active</c:if>"
                                                            name="busPhoneExtNew"
                                                            id="workPhoneExtention"
                                                            aria-label=""
                                                            data-localize="profile.workPhoneExtention"
                                                            value="${busRequest.busPhoneExtNew}"
                                                        />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="profile-btn">
                                        <div class="edit">
                                            <button
                                                type="button"
                                                class="btn btn-primary edit-btn" data-localize="label.edit"
                                            >
                                            
                                            </button>
                                        </div>
                                        <div class="saveOrCancel">
                                            <button
                                                type="submit"
                                                class="btn btn-primary save-btn" data-localize="label.update"
                                                id="savePhone"
                                            >
                                            
                                            </button>
                                            <button
                                                    type="button"
                                                    id="undoPhoneNumber"
                                                    class="btn btn-secondary"  data-localize="label.undo"  data-toggle="modal" data-target="#undoModal" 
                                                >
                                                
                                                </button>
                                            <button
                                                type="button"
                                                class="btn btn-secondary cancel-btn"  data-localize="label.cancel"
                                            >
                                            
                                            </button>
                                        </div>
                                    </div>
                                </form>
                                <form hidden="hidden" action="deletePhone" id="deletePhone" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                        </c:if>
                        
                        <c:if test="${sessionScope.options.enableSelfServicePayroll == true}">
                            <p class="sub-title" data-localize="profile.W4MaritalStatusInfo"></p>
                            <form
                                class="no-print searchForm"
                                action="profile"
                                id="changeFreqForm"
                                method="POST"
                                            >
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>           
                                <div class="form-group in-line p-l-0">
                                    <label class="form-title"  for="freq"  data-localize="label.payrollFreq"></label>
                                    
                                    <select class="form-control" name="freq" id="freq" onchange="changeFreq()">
                                        <c:forEach var="freq" items="${payRollFrequenciesOptions}" varStatus="count">
                                            <option value="${freq.code}" <c:if test="${freq.code == selectedFreq }">selected</c:if>>${freq.description}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </form>
                            <form class="profile-item" id="w4InfoForm" action="saveW4" method="POST">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input hidden="hidden" type="text" name="empNbr" value="${w4Request.id.empNbr}" aria-label="" data-localize="accessHint.employeeNumber">
                                <input hidden="hidden" type="text" name="reqDts" value="${w4Request.id.reqDts}" aria-label="" data-localize="accessHint.reqDts">
                                <input hidden="hidden" type="text" name="payFreq" value="${w4Request.id.payFreq}"  aria-label="" data-localize="accessHint.payFreq">
                                <input hidden="hidden" type="text" name="maritalStatTax" value="${payInfo.maritalStatTax}" aria-label="" data-localize="accessHint.maritalStatTax">
                                <input hidden="hidden" type="text" name="nbrTaxExempts" value="${payInfo.nbrTaxExempts}" aria-label="" data-localize="accessHint.nbrTaxExempts">
                                <div class="profile-left">
                                        <div class="profileTitle form-line profileInfo">
                                                <span class="currentTitle" data-localize="label.current"></span>
                                                <div class="newTitle" data-localize="label.new"></div>
                                            </div>
                                    <div class="profile-item-line form-line">
                                        <div class="profile-title" data-localize="profile.MaritalStatus">
                                        
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
                                                    name="maritalStatTaxNew"
                                                    class="form-control  <c:if test="${payInfo.maritalStatTax != w4Request.maritalStatTaxNew}">active</c:if>"
                                                    value="${w4Request.maritalStatTaxNew}"
                                                    aria-label=""
                                                    data-localize="profile.MaritalStatus"
                                                    
                                                >
                                                    <c:forEach var="maritalTax" items="${maritalTaxOptions}" varStatus="count">
                                                        <option value="${maritalTax.code}" <c:if test="${maritalTax.code == w4Request.maritalStatTaxNew }">selected</c:if>>${maritalTax.displayLabel}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="profile-item-line form-line">
                                        <div class="profile-title" data-localize="profile.NbrOfExemptions">
                                        
                                        </div>
                                        <div class="profile-desc">
                                            <span class="haveValue"
                                                >${payInfo.nbrTaxExempts}</span
                                            >
                                            <div class="form-group valueInput">
                                                <input
                                                    class="form-control <c:if test="${payInfo.nbrTaxExempts != w4Request.nbrTaxExemptsNew}">active</c:if>"
                                                    id="nbrTaxExemptsNew"
                                                    name="nbrTaxExemptsNew"
                                                    aria-label=""  data-localize="profile.NbrOfExemptions"
                                                    value="${w4Request.nbrTaxExemptsNew}"
                                                />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="profile-btn">
                                    <div class="edit">
                                        <button
                                            type="button"
                                            class="btn btn-primary edit-btn" data-localize="label.edit"
                                        >
                                        
                                        </button>
                                    </div>
                                    <div class="saveOrCancel">
                                        <button
                                            type="submit"
                                            class="btn btn-primary save-btn" data-localize="label.update"
                                            id="saveW4"
                                        >
                                        
                                        </button>
                                        <button
                                                type="button"
                                                id="undoW4"
                                                class="btn btn-secondary"  data-localize="label.undo"  data-toggle="modal" data-target="#undoModal" 
                                            >
                                            
                                            </button>
                                        <button
                                            type="button"
                                            class="btn btn-secondary cancel-btn"  data-localize="label.cancel"
                                        >
                                        
                                        </button>
                                    </div>
                                </div>
                            </form>
                            <form hidden="hidden" action="deleteW4" id="deleteW4" method="POST">
                            	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input hidden="hidden" type="text" name="empNbr" value="${w4Request.id.empNbr}" aria-label="" data-localize="accessHint.employeeNumber">
                                <input hidden="hidden" type="text" name="reqDts" value="${w4Request.id.reqDts}" aria-label="" data-localize="accessHint.reqDts">
                                <input hidden="hidden" type="text" name="payFreq" value="${w4Request.id.payFreq}" aria-label="" data-localize="accessHint.payFreq">
                                <input hidden="hidden" type="text" name="maritalStatTax" value="${payInfo.maritalStatTax}" aria-label="" data-localize="accessHint.maritalStatTax">
                                <input hidden="hidden" type="text" name="nbrTaxExempts" value="${payInfo.nbrTaxExempts}" aria-label="" data-localize="accessHint.nbrTaxExempts">
                            </form>
                            <p class="sub-title" data-localize="profile.directDepositBankAccounts"></p>
                            
                            <c:forEach var="bank" items="${banks}" varStatus="count">
                                
                                
                            <form
                                    class="profile-item border-0 bankAccountBlock  <c:if test="${bank.isDelete == false}">usedBank</c:if>  <c:if test="${bank.isDelete == true}">isDelete</c:if>"
                                    id="bankAccountForm_${count.index}"
                                    method="POST"
                                    >
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <div class="profile-left">
                                        <c:if test="${count.index == 0}">
                                        <div class="profileTitle form-line profileInfo">
                                                <span class="currentTitle" data-localize="label.current"></span>
                                                <div class="newTitle" data-localize="label.new"></div>
                                            </div>
                                            </c:if>
                                    <div class="profile-item-line form-line">
                                        <div class="profile-title" data-localize="profile.primary"></div>
                                        <div class="profile-desc">
                                            <span class="haveValue">
                                                <!-- <label>
                                                    <div class="noPrimary"></div>
                                                    <div class="yesPrimary">
                                                        <i class="fa fa-check"></i>
                                                    </div>
                                                </label> -->
                                            </span>
                                            <div class="form-group valueInput">
                                                <label for="primary_${count.index}">
                                                    <input
                                                        class="icheckRadioBank"
                                                        id="primary_${count.index}"
                                                        type="radio"
                                                        aria-label="" 
                                                        data-localize="accessHint.primaryAccountCheckbox"
                                                        name="primaryAccount"
                                                    />
                                                    <span class="hide" data-localize="accessHint.primaryAccountCheckbox"></span>
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="profile-item-line form-line">
                                        <div class="profile-title" data-localize="profile.bankName"></div>
                                        <div class="profile-desc">
                                            <span class="haveValue"
                                                >${bank.code.description}</span
                                            >
                                            
                                            <input type="hidden"  class="form-control bankcode bankNewCode"  id="codeNew_${count.index}" value="${bank.codeNew.code}"  aria-label="" data-localize="accessHint.bankCodeNew"/>
                                            <input type="hidden" id="code_${count.index}" value="${bank.code.code}"  aria-label="" data-localize="accessHint.bankCode"/>
                                            
                                            <div class="valueInput group-line">
                                                <div class="form-group inputDisabled">
                                                    <input
                                                        class="form-control name <c:if test="${bank.code.description != bank.codeNew.description}">active</c:if>"
                                                        type="text"
                                                        name="description"
                                                        aria-label="" 
                                                        data-localize="profile.bankName"
                                                        value="${bank.codeNew.description}"
                                                    />
                                                </div>

                                                <div class="form-group inputDisabled">
                                                    <input
                                                        class="form-control code <c:if test="${bank.code.subCode != bank.codeNew.subCode}">active</c:if>"
                                                        type="text"
                                                        name="subCode"
                                                        aria-label="" 
                                                        data-localize="accessHint.bankCode"
                                                        value="${bank.codeNew.subCode}"
                                                    />
                                                </div>

                                                <button
                                                    class="btn btn-secondary xs getBank"
                                                    type="button"
                                                    data-toggle= "modal"
                                                    data-target="#selectBankModal"
                                                    aria-label="" data-localize="profile.chooseBank" data-localize-location="aria-label" data-localize-notText="true"
                                                >
                                                    <i class="fa fa-ellipsis-h"></i>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="profile-item-line form-line">
                                        <div class="profile-title" data-localize="profile.bankAcctNbr">
                                        </div>
                                        <div class="profile-desc">
                                            <span class="haveValue" id="accountNumber_${count.index}"
                                                >${bank.accountNumber}</span
                                            >
                                            <div class="form-group valueInput">
                                                <input
                                                    class="form-control <c:if test="${bank.accountNumber != bank.accountNumberNew}">active</c:if>"
                                                    type="text"
                                                    aria-label=""
                                                    data-localize="profile.bankAcctNbr"
                                                    name="accountNumber"
                                                    id="accountNumberNew_${count.index}"
                                                    value="${bank.accountNumberNew}"
                                                />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="profile-item-line form-line">
                                        <div class="profile-title" data-localize="profile.bankAcctType">
                                        </div>
                                        <div class="profile-desc">
                                            <span class="haveValue"  id="accountType_${count.index}"
                                                >${bank.accountType.displayLabel}</span
                                            >
                                            <div class="form-group valueInput">
                                                <select
                                                    class="form-control <c:if test="${bank.accountType.code != bank.accountTypeNew.code}">active</c:if>"
                                                    aria-label=""
                                                    id="accountTypeNew_${count.index}"
                                                    data-localize="profile.bankAcctType"
                                                    name="displayLabel"
                                                >
                                                <c:forEach var="bankType" items="${bankAccountTypes}" varStatus="countBank">
                                                    <option value="${bankType.displayLabel}" <c:if test="${bankType.code == bank.accountTypeNew.code}">selected</c:if>>${bankType.displayLabel}</option>
                                                </c:forEach>
    
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="profile-item-line form-line">
                                        <div class="profile-title" data-localize="profile.bankAcctAmt">
                                        </div>
                                        <div class="profile-desc bankAmount">
                                            <span class="haveValue" id="displayAmount_${count.index}"
                                                >${bank.depositAmount.displayAmount}</span
                                            >
                                            <div class="form-group valueInput">
                                                <input
                                                    class="form-control amount_2 <c:if test="${bank.depositAmount.displayAmount != bank.depositAmountNew.displayAmount}">active</c:if>"
                                                    id="displayAmountNew_${count.index}"
                                                    type="text"
                                                    aria-label="" data-localize="profile.bankAcctAmt"
                                                    name="displayAmount"
                                                    value="${bank.depositAmountNew.displayAmount}"
                                                />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="profile-btn">
                                    <div class="saveOrCancel">
                                        <button
                                            type="button"
                                            class="btn btn-primary save-btn saveUpdateBankBtn" data-localize="label.update"
                                            id="saveBank_${count.index}"
                                            onclick="updateBank(${count.index})"
                                        >
                                        
                                        </button>
                                        <button
                                                type="button"
                                                id="undoBank_${count.index}"
                                                class="btn btn-secondary undo-btn"  data-localize="label.undo"  data-toggle="modal" data-target="#undoModal" 
                                                onclick="undoBank(${count.index})"
                                            >
                                            
                                        </button>
                                        <c:if test="${bank.code.subCode!=''}">
                                            <button
                                                type="button"
                                                id="deleteBank_${count.index}"
                                                class="btn btn-secondary delete-btn"  data-localize="label.delete" onclick="deleteBankAmount(${count.index})"
                                                data-toggle="modal" data-target="#deleteModal"
                                            >
                                            
                                            </button>
                                        </c:if>
                                    </div>
                                </div>
                            </form>
                                                    
                            </c:forEach>

                            <div>
                                <form hidden="hidden" action="updateBank" id="updateBankHidden" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="hidden" name="freq" id="hidden_freq_update"   aria-label="" data-localize="accessHint.reqDts"/>
                                    <input type="hidden" name="accountNumberNew" id="hidden_accountNumberNew_update"   aria-label="" data-localize="accessHint.accountNumberNew"/>
                                    <input type="hidden" name="accountNumber" id="hidden_accountNumber_update"   aria-label="" data-localize="accessHint.accountNumber"/>
                                    <input type="hidden" name="code" id="hidden_code_update" />
                                    <input type="hidden" name="codeNew" id="hidden_codeNew_update"   aria-label="" data-localize="accessHint.reqDts"/>
                                    
                                    <input type="hidden" name="accountType" id="hidden_accountType_update"   aria-label="" data-localize="accessHint.accountType"/>
                                    <input type="hidden" name="accountTypeNew" id="hidden_accountTypeNew_update"   aria-label="" data-localize="accessHint.accountTypeNew"/>
                                    <input type="hidden" name="displayAmount" id="hidden_displayAmount_update"   aria-label="" data-localize="accessHint.displayAmount"/>
                                    <input type="hidden" name="displayAmountNew" id="hidden_displayAmountNew_update"   aria-label="" data-localize="accessHint.displayAmountNew"/>
                                </form>
                                <form hidden="hidden" action="undoBank" id="undoBankHidden" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="hidden" name="freq" id="hidden_freq_undo"   aria-label="" data-localize="accessHint.frequency"/>
                                    <input type="hidden" name="accountNumberNew" id="hidden_accountNumberNew_undo"   aria-label="" data-localize="accessHint.accountNumberNew"/>
                                    <input type="hidden" name="accountNumber" id="hidden_accountNumber_undo"   aria-label="" data-localize="accessHint.accountNumber"/>
                                    <input type="hidden" name="code" id="hidden_code_undo"   aria-label="" data-localize="accessHint.bankCode"/>
                                    <input type="hidden" name="codeNew" id="hidden_codeNew_undo"   aria-label="" data-localize="accessHint.bankCodeNew"/>
                                </form>
                                <form hidden="hidden" action="deleteBank"  id="deleteBankHidden" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="hidden" name="freq" id="hidden_freq_delete"   aria-label="" data-localize="accessHint.frequency"/>
                                    <input type="hidden" name="accountNumber" id="hidden_accountNumber_delete"   aria-label="" data-localize="accessHint.accountNumber"/>
                                    <input type="hidden" name="code" id="hidden_code_delete"  aria-label="" data-localize="accessHint.bankCode"/>
                                    <input type="hidden" name="accountType" id="hidden_accountType_delete"   aria-label="" data-localize="accessHint.accountType"/>
                                    <input type="hidden" name="displayAmount" id="hidden_displayAmount_delete"   aria-label="" data-localize="accessHint.displayAmount"/>
                                </form>
                                <form hidden="hidden" action="saveBank" id="saveBankHidden" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="hidden" name="freq" id="hiddenfreq"   aria-label="" data-localize="accessHint.frequency"/>
                                    <input type="hidden" name="displayAmount" id="hiddendisplayAmount"  aria-label="" data-localize="accessHint.displayAmount"/>
                                    <input type="hidden" name="displayLabel" id="hiddendisplayLabel"   aria-label="" data-localize="accessHint.displayLabel"/>
                                    <input type="hidden" name="accountNumber" id="hiddenaccountNumber"   aria-label="" data-localize="accessHint.accountNumber"/>
                                    <input type="hidden" name="subCode" id="hiddensubCode"   aria-label="" data-localize="accessHint.bankCode"/>
                                    <input type="hidden" name="description" id="hiddendescription"   aria-label="" data-localize="accessHint.bankName"/>
                                </form>
                                <form
                                    class="profile-item border-0 activeEdit addBankForm"
                                    id="addBankAccountForm"
                                    method="POST"
                                >
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <div class="profile-left">
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.bankName">
                                            </div>
                                            <div class="profile-desc">
                                                <div class="valueInput group-line">
                                                    <div class="form-group inputDisabled">
                                                        <input
                                                            class="form-control name"
                                                            type="text"
                                                            name="description"
                                                            id="saveBankDescription"
                                                            aria-label="" data-localize="accessHint.bankName"
                                                            value=""
                                                        />
                                                    </div>

                                                    <div class="form-group inputDisabled">
                                                        <input
                                                            class="form-control code"
                                                            type="text"
                                                            name="subCode"
                                                            id="saveBankCode"
                                                            aria-label="" data-localize="accessHint.bankCode"
                                                            value=""
                                                        />
                                                    </div>
                                                    <input hidden="hidden" type="text" id="newBankCode" aria-label="" data-localize="accessHint.bankCode">
                                                    <button
                                                        class="btn btn-secondary xs getBank"
                                                        type="button"
                                                        data-toggle= "modal"
                                                        data-target="#selectBankModal"
                                                        aria-label="" data-localize="profile.chooseBank" data-localize-location="aria-label" data-localize-notText="true"
                                                    >
                                                        <i
                                                            class="fa fa-ellipsis-h"
                                                        ></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.bankAcctNbr">
                                            </div>
                                            <div class="profile-desc">
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control"
                                                        type="text"
                                                        aria-label="" data-localize="profile.bankAcctNbr"
                                                        name="accountNumber"
                                                        id="saveBankAccountNumber"
                                                        value=""
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.bankAcctType">
                                            </div>
                                            <div class="profile-desc">
                                                <div class="form-group valueInput">
                                                    <select
                                                        class="form-control"
                                                        aria-label="" data-localize="profile.bankAcctType"
                                                        name="displayLabel"
                                                        id="saveBankDisplayLabel"
                                                    >
                                                    <c:forEach var="bankType" items="${bankAccountTypes}" varStatus="count">
                                                        <option value="${bankType.displayLabel}">${bankType.displayLabel}</option>
                                                    </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title" data-localize="profile.bankAcctAmt">
                                            </div>
                                            <div class="profile-desc">
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control amount_2"
                                                        type="text"
                                                        aria-label="" data-localize="profile.bankAcctType"
                                                        name="displayAmount"
                                                        id="saveBankDisplayAmount"
                                                        value=""
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="profile-btn">
                                        <div class="saveOrCancel flex-line">
                                            <button
                                                type="button"
                                                class="btn btn-primary save-btn" data-localize="label.save"
                                                id="saveNewBank"
                                            >
                                            
                                            </button>
                                            <button
                                                type="button"
                                                class="btn btn-secondary cancel-add-btn"
                                                data-localize="label.cancel"
                                            >
                                            
                                            </button>
                                        </div>
                                    </div>
                                </form>
                                <button
                                    type="button"
                                    class="btn btn-primary add-bank-btn"
                                >
                                <span data-localize="label.add"></span>
                                </button>
                                <p class="error-hint hide bankSizeError" data-localize="validator.maximumBankAmmount">
                                </p>
                            </div>
                        </c:if>
                    </div>
                </section>
            </main>
        </div>
        <%@ include file="commons/footer.jsp"%>
        <%@ include file="modal/changePassword.jsp"%>
        <%@ include file="modal/undoModal.jsp"%>
        <%@ include file="modal/changeAvatar.jsp"%>
        <%@ include file="modal/deleteModal.jsp"%>
        <div
            class="modal fade"
            id="selectBankModal"
            tabindex="-1"
            role="dialog"
            aria-labelledby="selectBankModal"
            aria-hidden="true"
        >
            <div class="modal-dialog approveForm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button
                        type="button"
                        class="close"
                        data-dismiss="modal"
                        aria-hidden="true"
                        aria-label="" data-localize="label.closeModal" data-localize-location="aria-label" data-localize-notText="true"
                        >
                            &times;
                        </button>
                        <h4 class="modal-title" data-localize="profile.selectABank"></h4>
                    </div>
                    <div class="modal-body">
                        <form
                            action="/"
                            id="selectBank"
                            method="post"
                            class="flex"
                        >
                        	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <div class="form-group">
                                <label class="form-title" for="codeCriteriaSearchCode"
                                    ><span data-localize="profile.routingNumber"></span>:</label
                                >
                                <div class="button-group">
                                    <input
                                        id="codeCriteriaSearchCode"
                                        name="codeCriteriaSearchCode"
                                        class="form-control"
                                        type="text"
                                        value=""
                                    />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="form-title" for="codeCriteriaSearchDescription"
                                    ><span data-localize="profile.bankName"></span>:</label
                                >
                                <div class="button-group">
                                    <input
                                        id="codeCriteriaSearchDescription"
                                        name="codeCriteriaSearchDescription"
                                        class="form-control"
                                        type="text"
                                        value=""
                                    />
                                </div>
                            </div>
                            <div class="form-group btn-group">
                                <div style="margin-top:20px;">
                                    <button
                                        type="button" id="searchBankBtn"
                                        class="btn btn-primary"
                                        data-localize="label.search"
                                    ></button>
                                </div>
                            </div>
                        </form>
                        <div class="bankResult">
                            <table class="table border-table" id="bankTable">
                                <thead>
                                    <tr>
                                        <th data-localize="profile.routingNumber"></th>
                                        <th data-localize="profile.bankName"></th>
                                    </tr>
                                </thead>
                                <tbody>
                                 <!-- 
                                    <tr>
                                        <td data-localize="profile.routingNumber" data-localize-location="scope">
                                            <button
                                                class="a-btn bankNumberBtn"
                                                type="button"
                                                value="220483972"
                                                data-title="A+ FEDERAL CREDIT UNION"
                                            >
                                                220483972
                                            </button>
                                        </td>
                                        <td data-localize="profile.bankName" data-localize-location="scope">A+ FEDERAL CREDIT UNION</td>
                                    </tr>
                                   
                                    <tr>
                                            <td colspan="2">
                                                <div class="flex">
                                                        <div class="pageGroup">
                                                                <button class="pageBtn firstPate" aria-label="" data-localize="label.firstPage" data-localize-location="title">
                                                                        <i class="fa fa-angle-double-left "></i>
                                                                </button>  
                                                                <button class="pageBtn prevPage" aria-label="" data-localize="label.prevPage" data-localize-location="title">
                                                                        <i class="fa fa-angle-left "></i>
                                                                </button>
                                                                <select class="selectPage" name="page" id="pageNow" aria-label="" data-localize="label.choosePage" onchange="changePage()"  data-localize-location="title">
                                                                        <option value="1">1</option>
                                                                        <option value="2">2</option>
                                                                </select>
                                                                <div class="page-list">
                                                                        <span class="slash">/</span>
                                                                        <span class="totalPate">2</span>
                                                                </div>
                                                                <button class="pageBtn nextPate" aria-label="" data-localize="label.nextPage" data-localize-location="title">
                                                                                <i class="fa fa-angle-right "></i>
                                                                </button>
                                                                <button class="pageBtn lastPate" aria-label="" data-localize="label.lastPage" data-localize-location="title">
                                                                    <i class="fa fa-angle-double-right"></i>
                                                                </button>
                                                        </div>
                                                        <b class="totalRows">
                                                            <span data-localize="label.rows"></span>: 100
                                                        </b>
                                                </div>
                                                    
                                            </td>
                                    </tr>
                                     -->
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button
                            type="button"
                            class="btn btn-secondary"
                            data-dismiss="modal"
                            aria-hidden="true"
                            data-localize="label.cancel"
                        >
                        
                        </button>
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal -->
        </div>
    </body>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/profile.js"></script>

</html>
