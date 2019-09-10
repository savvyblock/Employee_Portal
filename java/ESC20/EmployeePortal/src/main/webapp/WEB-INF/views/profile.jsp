<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html> 
<html lang="en">
    <head>
        <title>${sessionScope.languageJSON.headTitle.profile}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <%@ include file="commons/header.jsp"%>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <%@ include file="commons/bar.jsp"%>
            <main class="content-wrapper" tabindex="-1">
                <section class="content">
                    <div class="content-white no-title profile">
                            <c:if test="${sessionScope.enableSelfServiceDemographic}">
                                <div class="profile-item">
                                        <button type="button" role="button" class="btn btn-primary sm" data-toggle="modal" data-target="#changePasswordModal">
                                                <span>${sessionScope.languageJSON.label.changePassword}</span>
                                            </button>
                                </div>
                                <c:if test="${not empty sessionScope.options.messageSelfServiceDemographic}">
	                            	<p class="topMsg error-hint" role="alert">${sessionScope.options.messageSelfServiceDemographic}</p>
	                        	</c:if>
	                        	 <h2 class="sub-title">${sessionScope.languageJSON.profile.LegalName}</h2>
                                <div class="profile-top first-child">
                                  
                                    <form class="profile-item" id="personalForm" action="saveName" method="POST">
                                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
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
                                                    <input type="hidden" name="empNbr" value="${nameRequest.id.empNbr}">
                                                    <input type="hidden" name="reqDts" value="${nameRequest.id.reqDts}">
                                                    <div class="form-group valueInput">
                                                        <select
                                                            class="form-control <c:if test="${sessionScope.userDetail.namePre != nameRequest.namePreNew }">active</c:if>"
                                                            aria-label="${sessionScope.languageJSON.profile.title}"
                                                            name="namePreNew"
                                                            id="titleString"
                                                        >
                                                        <c:forEach var="title" items="${titleOptions}" varStatus="count">
                                                                <c:choose>
                                                                        <c:when test="${title.description==''}">
                                                                                <option value="${title.description}" <c:if test="${title.description == nameRequest.namePreNew }">selected</c:if>>&nbsp;</option>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                                <option value="${title.description}" <c:if test="${title.description == nameRequest.namePreNew }">selected</c:if>>${title.description}</option>
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
                                                            type="text"
                                                            value="${nameRequest.nameLNew}"
                                                            aria-label="${sessionScope.languageJSON.profile.lastname}"
                                                           
                                                            name="nameLNew"
                                                            id="lastName"
                                                            maxlength="25"
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
                                                            type="text"
                                                            value="${nameRequest.nameFNew}"
                                                            aria-label="${sessionScope.languageJSON.profile.firstname}"
                                                           
                                                            name="nameFNew"
                                                            id="firstName"
                                                            maxlength="17"
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
                                                            type="text"
                                                            value="${nameRequest.nameMNew}"
                                                            aria-label="${sessionScope.languageJSON.profile.middleName}"
                                                           
                                                            name="nameMNew"
                                                            id="middleName"
                                                            maxlength="14"
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
                                                            class="form-control <c:if test="${sessionScope.userDetail.nameGen != nameRequest.nameGenNew}">active</c:if>"
                                                            aria-label="${sessionScope.languageJSON.profile.generation}"
                                                           
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
                                                    class="btn btn-primary save-btn"
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
                                    </form>
                                    <form hidden="hidden" action="deleteNameRequest" id="deleteNameRequest" method="POST">
                                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    </form>
                                </div>

                                <h2 class="sub-title">${sessionScope.languageJSON.profile.MaritalStatus}</h2>
                                <form class="profile-item" id="maritalStatusForm" action="saveMarital" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
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
                                                <input type="hidden" name="empNbr" value="${mrtlRequest.id.empNbr}" >
                                                    <input type="hidden" name="reqDts" value="${mrtlRequest.id.reqDts}">
                                                <div class="form-group valueInput">
                                                    <select
                                                        id="maritalStatus"
                                                        name="maritalStatNew"
                                                        class="form-control <c:if test="${sessionScope.userDetail.maritalStat != mrtlRequest.maritalStatNew }">active</c:if>"
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
                                </form>
                                <form hidden="hidden" action="deleteMaritalRequest" id="deleteMaritalRequest" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                                <h2 class="sub-title">${sessionScope.languageJSON.profile.driversLicense}</h2>
                                <form class="profile-item" id="driverLicenseForm" action="saveDriversLicense" method="POST">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="hidden" name="empNbr" value="${licRequest.id.empNbr}">
                                    <input type="hidden" name="reqDts" value="${licRequest.id.reqDts}">
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
                                                        type="text"
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
                                                        name="driversLicStNew"
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
                                                class="btn btn-primary save-btn"
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
                                </form>
                                <form hidden="hidden" action="deleteDriversLicenseRequest" id="deleteDriversLicense" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                                <h2 class="sub-title">${sessionScope.languageJSON.profile.restrictionCodes}</h2>
                                <form class="profile-item" id="restrictionCodeForm" action="saveRestrictionCodes" method="POST">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="hidden" name="empNbr" value="${restrictRequest.id.empNbr}">
                                    <input type="hidden" name="reqDts" value="${restrictRequest.id.reqDts}">
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
                                                        name="restrictCdNew"
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
                                                        name="restrictCdPublicNew"
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
                                </form>
                                <form hidden="hidden" action="deleteRestrictionCodesRequest" id="deleteRestrictionCodesRequest" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                                <h2 class="sub-title">${sessionScope.languageJSON.profile.email}</h2>
                                <form class="profile-item" id="emailForm" action="saveEmail" method="POST">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input type="hidden" name="empNbr" value="${emailRequest.id.empNbr}">
                                        <input type="hidden" name="reqDts" value="${emailRequest.id.reqDts}">
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
                                                        id="emailWorkEmail"
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
                                                        class="form-control"
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
                                                        id="emailHomeEmail"
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
                                                        class="form-control"
                                                        aria-label="${sessionScope.languageJSON.profile.verifyEmail}"
                                                       
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
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
                                                class="btn btn-primary save-btn"
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
                                </form>
                                <form hidden="hidden" action="deleteEmail" id="deleteEmail" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                                <h2 class="sub-title">${sessionScope.languageJSON.profile.emergenceContactInfo}</h2>
                                <form class="profile-item" id="emergencyContactForm" action="saveEmergencyContact" method="POST">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input type="hidden" name="empNbr" value="${emerRequest.id.empNbr}">
                                        <input type="hidden" name="reqDts" value="${emerRequest.id.reqDts}">
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
                                                    ${sessionScope.userDetail.emerPhoneNbr}
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
                                                           
                                                            value="${emerRequest.emerPhoneAcNew}"
                                                            oninput="value=value.replace(/[^\d]/g,'')"
                                                        />
                                                    </div>
                                                    <div class="form-group">
                                                        <input
                                                            class="form-control phone-input <c:if test="${sessionScope.userDetail.emerPhoneNbr != emerRequest.emerPhoneNbrNew}">active</c:if>"
                                                            name="emerPhoneNbrNew"
                                                            id="emergencyContactPhoneNumber"
                                                            aria-label="${sessionScope.languageJSON.profile.emergencyContactPhoneNumber}"
                                                           
                                                            value="${emerRequest.emerPhoneNbrNew}"
                                                            oninput="value=value.replace(/[^\d]/g,'')"
                                                        />
                                                    </div>

                                                    &nbsp;&nbsp;<span>${sessionScope.languageJSON.profile.ext}</span>
                                                    <div class="form-group">
                                                        <input
                                                            class="form-control phoneAreaCode <c:if test="${sessionScope.userDetail.emerPhoneExt != emerRequest.emerPhoneExtNew}">active</c:if>"
                                                            name="emerPhoneExtNew"
                                                            id="emergencyContactExtention"
                                                            aria-label="${sessionScope.languageJSON.profile.emergencyContactExtention}"
                                                           
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
                                </form>
                                <form hidden="hidden" action="deleteEmergencyContact" id="deleteEmergencyContact" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                                <h2 class="sub-title">${sessionScope.languageJSON.profile.mailingAddress}</h2>
                                <form class="profile-item" id="mailingAddressForm" action="saveMailAddr" method="POST">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input type="hidden" name="empNbr" value="${mailAddrRequest.id.empNbr}">
                                        <input type="hidden" name="reqDts" value="${mailAddrRequest.id.reqDts}">
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
                                                       
                                                        value="${mailAddrRequest.addrZip4New}"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
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
                                </form>
                                <form hidden="hidden" action="deleteMailAddr" id="deleteMailAddr" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                                <h2 class="sub-title">${sessionScope.languageJSON.profile.altAddr}</h2>
                                <form class="profile-item" id="alternativeAddressForm" action="saveAltMailAddr" method="POST">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input type="hidden" name="empNbr" value="${altMailAddrRequest.id.empNbr}">
                                        <input type="hidden" name="reqDts" value="${altMailAddrRequest.id.reqDts}">
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
                                                    <input
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
                                </form>
                                <form hidden="hidden" action="deleteAltMailAddr" id="deleteAltMailAddr" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                                <h2 class="sub-title">${sessionScope.languageJSON.profile.phoneNumbers}</h2>
                                <form class="profile-item" id="phoneForm" action="savePhone" method="POST">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input type="hidden" name="empNbr" value="${hmRequest.id.empNbr}">
                                        <input type="hidden" name="reqDts" value="${hmRequest.id.reqDts}">
                                    <div class="profile-left">
                                            <div class="profileTitle form-line profileInfo">
                                                    <span class="currentTitle">${sessionScope.languageJSON.label.current}</span>
                                                    <div class="newTitle">${sessionScope.languageJSON.label.new}</div>
                                                </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.home}</div>
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
                                                            aria-label="${sessionScope.languageJSON.profile.homePhoneAreaCode}"
                                                           
                                                            value="${hmRequest.phoneAreaNew}"
                                                            oninput="value=value.replace(/[^\d]/g,'')"
                                                            
                                                        />
                                                    </div>
                                                    <div class="form-group">
                                                        <input
                                                            class="form-control phone-input  <c:if test="${sessionScope.userDetail.phoneNbr != hmRequest.phoneNbrNew}">active</c:if>"
                                                            name="phoneNbrNew"
                                                            id="homePhonePhoneNumber"
                                                            aria-label="${sessionScope.languageJSON.profile.homePhonePhoneNumber}"
                                                           
                                                            value="${hmRequest.phoneNbrNew}"
                                                            oninput="value=value.replace(/[^\d]/g,'')"
                                                        />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.cell}</div>
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
                                                            aria-label="${sessionScope.languageJSON.profile.cellPhoneAreaCode}"
                                                           
                                                            value="${cellRequest.phoneAreaCellNew}"
                                                            oninput="value=value.replace(/[^\d]/g,'')"
                                                        />
                                                    </div>

                                                    <div class="form-group">
                                                        <input
                                                            class="form-control phone-input  <c:if test="${sessionScope.userDetail.phoneNbrCell != cellRequest.phoneNbrCellNew}">active</c:if>"
                                                            name="phoneNbrCellNew"
                                                            id="cellPhonePhoneNumber"
                                                            aria-label="${sessionScope.languageJSON.profile.cellPhonePhoneNumber}"
                                                           
                                                            value="${cellRequest.phoneNbrCellNew}"
                                                            oninput="value=value.replace(/[^\d]/g,'')"
                                                        />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">${sessionScope.languageJSON.profile.business}</div>
                                            <div class="profile-desc">
                                                <span class="haveValue">
                                                    (${sessionScope.userDetail.phoneAreaBus})
                                                    ${sessionScope.userDetail.phoneNbrBus}&nbsp;&nbsp;&nbsp;&nbsp;
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
                                                           
                                                            value="${busRequest.phoneAreaBusNew}"
                                                            oninput="value=value.replace(/[^\d]/g,'')"
                                                        />
                                                    </div>

                                                    <div class="form-group">
                                                        <input
                                                            class="form-control phone-input  <c:if test="${sessionScope.userDetail.phoneNbrBus != busRequest.phoneNbrBusNew}">active</c:if>"
                                                            name="phoneNbrBusNew"
                                                            id="workPhonePhoneNumber"
                                                            aria-label="${sessionScope.languageJSON.profile.workPhonePhoneNumber}"
                                                           
                                                            value="${busRequest.phoneNbrBusNew}"
                                                            oninput="value=value.replace(/[^\d]/g,'')"
                                                        />
                                                    </div>

                                                    &nbsp;&nbsp;<span>${sessionScope.languageJSON.profile.ext}</span>
                                                    <div class="form-group">
                                                        <input
                                                            class="form-control phoneAreaCode  <c:if test="${sessionScope.userDetail.busPhoneExt != busRequest.busPhoneExtNew}">active</c:if>"
                                                            name="busPhoneExtNew"
                                                            id="workPhoneExtention"
                                                            aria-label="${sessionScope.languageJSON.profile.workPhoneExtention}"
                                                           
                                                            value="${busRequest.busPhoneExtNew}"
                                                            oninput="value=value.replace(/[^\d]/g,'')"
                                                        />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
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
                                </form>
                                <form hidden="hidden" action="deletePhone" id="deletePhone" method="POST">
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
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
                             <h2 class="sub-title">${sessionScope.languageJSON.profile.W4MaritalStatusInfo}</h2>
                            <form class="profile-item" id="w4InfoForm" action="saveW4" method="POST">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="hidden" name="empNbr" value="${w4Request.id.empNbr}">
                                <input type="hidden" name="reqDts" value="${w4Request.id.reqDts}">
                                <input type="hidden" name="payFreq" value="${w4Request.id.payFreq}">
                                <input type="hidden" name="maritalStatTax" value="${payInfo.maritalStatTax}">
                                <input type="hidden" name="nbrTaxExempts" value="${payInfo.nbrTaxExempts}">
                                <div class="profile-left">
                                        <div class="profileTitle form-line profileInfo">
                                                <span class="currentTitle">${sessionScope.languageJSON.label.current}</span>
                                                <div class="newTitle">${sessionScope.languageJSON.label.new}</div>
                                            </div>
                                    <div class="profile-item-line form-line">
                                        <div class="profile-title">
                                                ${sessionScope.languageJSON.profile.MaritalStatus}
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
                                                    aria-label="${sessionScope.languageJSON.profile.MaritalStatus}"
                                                   
                                                    
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
                                                    name="nbrTaxExemptsNew"
                                                    aria-label="${sessionScope.languageJSON.profile.NbrOfExemptions}" 
                                                    value="${w4Request.nbrTaxExemptsNew}"
                                                />
                                            </div>
                                        </div>
                                    </div>
                                </div>
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
                            </form>
                            <form hidden="hidden" action="deleteW4" id="deleteW4" method="POST">
                            	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="hidden" name="empNbr" value="${w4Request.id.empNbr}">
                                <input type="hidden" name="reqDts" value="${w4Request.id.reqDts}">
                                <input type="hidden" name="payFreq" value="${w4Request.id.payFreq}">
                                <input type="hidden" name="maritalStatTax" value="${payInfo.maritalStatTax}">
                                <input type="hidden" name="nbrTaxExempts" value="${payInfo.nbrTaxExempts}">
                            </form>
                            <h2 class="sub-title">${sessionScope.languageJSON.profile.directDepositBankAccounts}</h2>
                            
                            <c:forEach var="bank" items="${banks}" varStatus="count">
                                
                                
                            <form
                                    class="profile-item border-0 bankAccountBlock updateBankForm  <c:if test="${bank.isDelete == false}">usedBank</c:if>  <c:if test="${bank.isDelete == true}">isDelete</c:if>"
                                    id="bankAccountForm_${count.index}"
                                    action="updateBank" method="POST"
                                    >
                                    <input type="hidden" name="freq" class="hidden_freq_update" />
                              <div role="main" aria-label="<c:if test="${bank.isDelete == true}">${sessionScope.languageJSON.accessHint.deletedPart}</c:if>" class="profile-item border-0 bankAccountBlock  <c:if test="${bank.isDelete == false}">usedBank</c:if>  <c:if test="${bank.isDelete == true}">isDelete</c:if>">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <div class="profile-left">
                                    <c:if test="${count.index == 0}">
                                    <div class="profileTitle form-line profileInfo">
                                            <span class="currentTitle">${sessionScope.languageJSON.label.current}</span>
                                            <div class="newTitle">${sessionScope.languageJSON.label.new}</div>
                                        </div>
                                        </c:if>
                                <div class="profile-item-line form-line">
                                    <div class="profile-title">${sessionScope.languageJSON.profile.primary}</div>
                                    <div class="profile-desc">
                                        <div class="haveValue">
                                                <div class="noPrimary"></div>
                                                <div class="yesPrimary">
                                                    <i class="fa fa-check"></i>
                                                </div>
                                        </div>
                                        <div class="form-group valueInput">
                                            <div>
                                                <input
                                                    class="icheckRadioBank"
                                                    id="primary_${count.index}"
                                                    type="radio"
                                                    aria-label="${sessionScope.languageJSON.accessHint.primaryAccountCheckbox}" 
                                                    aria-disabled="${bank.isDelete}"
                                                    <c:if test="${bank.isDelete == true}">disabled="disabled"</c:if>
                                                    name="primaryAccount"
                                                />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="profile-item-line form-line">
                                    <div class="profile-title">${sessionScope.languageJSON.profile.bankName}</div>
                                    <div class="profile-desc">
                                        <span class="haveValue"
                                            >${bank.code.description}</span
                                        >
                                        
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
                                                    <c:if test="${bank.isDelete == true}">disabled="disabled"</c:if>
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
                                                    <c:if test="${bank.isDelete == true}">disabled="disabled"</c:if>
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
                                                <c:if test="${bank.isDelete == true}">disabled="disabled"</c:if>
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
                                                <c:if test="${bank.isDelete == true}">disabled="disabled"</c:if>
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
                                                <c:if test="${bank.isDelete == true}">disabled="disabled"</c:if>
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
                                                <c:if test="${bank.isDelete == true}">disabled="disabled"</c:if>
                                            />
                                        </div>
                                    </div>
                                </div>
                            </div>
                                <div class="profile-btn">
                                    <div class="saveOrCancel">
                                        <!-- <button
                                            type="button" role="button"
                                            class="btn btn-primary save-btn saveUpdateBankBtn"
                                            id="saveBank_${count.index}" aria-label = "${sessionScope.languageJSON.label.updateBank}"
                                            onclick="updateBank(${count.index})"
                                        >
                                        ${sessionScope.languageJSON.label.update}
                                        </button> -->
                                        <button
                                                type="button" role="button"
                                                id="undoBank_${count.index}" aria-label = "${sessionScope.languageJSON.label.undoBank}"
                                                class="btn btn-secondary undo-btn"   data-toggle="modal" data-target="#undoModal" 
                                                onclick="undoBank(${count.index})"
                                            >
                                            ${sessionScope.languageJSON.label.undo}
                                        </button>
                                        <c:if test="${bank.code.subCode!=''}">
                                            <button
                                                type="button" role="button"
                                                id="deleteBank_${count.index}" aria-label = "${sessionScope.languageJSON.label.deleteBank}"
                                                class="btn btn-secondary delete-btn"  onclick="deleteBankAmount(${count.index})"
                                                data-toggle="modal" data-target="#deleteModal"
                                            >
                                            ${sessionScope.languageJSON.label.delete}
                                            </button>
                                        </c:if>
                                    </div>
                                </div>
							</div>   
                            </form>
                            </c:forEach>
                            <p class="error-hint updateMessageFailed hide">${sessionScope.languageJSON.validator.updateFailed}</p>
                            <div>
                                <button
                                        type="button" role="button"
                                        class="btn btn-primary save-btn saveUpdateBankBtn"
                                        id="saveBank" aria-label = "${sessionScope.languageJSON.label.updateBank}"
                                        onclick="updateBank()"
                                    >
                                    ${sessionScope.languageJSON.label.update}
                                </button>
                            </div>
                            <br/>
                            <div>
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
                                    <input type="hidden" name="description" id="hiddendescription" />
                                </form>
                                <form
                                    class="profile-item border-0 activeEdit addBankForm"
                                    id="addBankAccountForm"
                                    method="POST"
                                >
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <div class="profile-left">
                                        <div class="profile-item-line form-line">
                                            <div class="profile-title">
                                                    ${sessionScope.languageJSON.profile.bankName}
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
                                                    <input type="hidden" id="newBankCode" aria-label="${sessionScope.languageJSON.accessHint.bankCode}">
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
                                            <div class="profile-desc">
                                                <div class="form-group valueInput">
                                                    <input
                                                        class="form-control amount_2"
                                                        type="text"
                                                        aria-label="${sessionScope.languageJSON.profile.bankAcctType}"
                                                        name="displayAmount"
                                                        id="saveBankDisplayAmount"
                                                        value=""
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
                                <button
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
                    </div>
                </section>
            </main>
        </div>
        <%@ include file="commons/footer.jsp"%>
        <%@ include file="modal/changePassword.jsp"%>
        <%@ include file="modal/undoModal.jsp"%>
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
                        type="button" role="button"
                        class="close"
                        data-dismiss="modal"
                        aria-hidden="true"
                        aria-label="${sessionScope.languageJSON.label.closeModal}"
                        >
                            &times;
                        </button>
                        <h4 class="modal-title">${sessionScope.languageJSON.profile.selectABank}</h4>
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
                                    ><span>${sessionScope.languageJSON.profile.routingNumber}</span>:</label
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
                                    ><span>${sessionScope.languageJSON.profile.bankName}</span>:</label
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
                                        type="button" role="button" id="searchBankBtn"
                                        class="btn btn-primary"
                                       
                                    >${sessionScope.languageJSON.label.search}</button>
                                </div>
                            </div>
                        </form>
                        <div class="bankResult">
                            <table class="table border-table" id="bankTable">
                                <thead>
                                    <tr>
                                        <th>${sessionScope.languageJSON.profile.routingNumber}</th>
                                        <th>${sessionScope.languageJSON.profile.bankName}</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button
                            type="button" role="button"
                            class="btn btn-secondary"
                            data-dismiss="modal"
                            aria-hidden="true"
                           
                        >
                        ${sessionScope.languageJSON.label.cancel}
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
