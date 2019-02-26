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
                        <div class="profile-item first-child">
                            <div class="profile-title" >
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
                            <form class="profile-desc" id="personalForm" action="saveName" method="POST">
                                <div class="profile-content">
                                    <div class="profile-desc-item form-line">
                                        <label class="desc-title" data-localize="profile.title"></label>
                                        <div class="desc-content">
                                            <span class="haveValue"
                                                >${sessionScope.userDetail.namePre}</span
                                            >
                                            <input hidden="hidden" type="text" name="empNbr" value="${nameRequest.id.empNbr}" title="" data-localize="accessHint.employeeNumber">
                                            <input hidden="hidden" type="text" name="reqDts" value="${nameRequest.id.reqDts}" title="" data-localize="accessHint.reqDts">
                                            <div class="form-group valueInput">
                                                <select
                                                    class="form-control"
                                                    title=""
                                                    data-localize="profile.title"
                                                    name="namePreNew"
                                                    id="titleString"
                                                    value="${nameRequest.namePreNew}"
                                                    
                                                >
                                                <c:forEach var="title" items="${titleOptions}" varStatus="count">
                                                    <option value="${title.code}" <c:if test="${title.code == nameRequest.namePreNew }">selected</c:if>>${title.description}</option>
                                                </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="profile-desc-item form-line">
                                        <label class="desc-title"  data-localize="profile.firstname"></label>
                                        <div class="desc-content">
                                            <span class="haveValue"
                                                >${sessionScope.userDetail.nameF}</span
                                            >
                                            <div class="form-group valueInput">
                                                <input
                                                    class="form-control"
                                                    type="text"
                                                    value="${nameRequest.nameFNew}"
                                                    title=""
                                                    data-localize="profile.firstname"
                                                    name="nameFNew"
                                                    id="firstName"
                                                />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="profile-desc-item form-line">
                                        <label class="desc-title" data-localize="profile.lastname"
                                            ></label
                                        >
                                        <div class="desc-content">
                                            <span class="haveValue"
                                                >${sessionScope.userDetail.nameL}</span
                                            >
                                            <div class="form-group valueInput">
                                                <input
                                                    class="form-control"
                                                    type="text"
                                                    value="${nameRequest.nameLNew}"
                                                    title=""
                                                    data-localize="profile.lastname"
                                                    name="nameLNew"
                                                    id="lastName"
                                                />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="profile-desc-item form-line">
                                        <label class="desc-title" data-localize="profile.middleName"
                                            ></label
                                        >
                                        <div class="desc-content">
                                            <span class="haveValue"
                                                >${sessionScope.userDetail.nameM}</span
                                            >
                                            <div class="form-group valueInput">
                                                <input
                                                    class="form-control"
                                                    type="text"
                                                    value="${nameRequest.nameMNew}"
                                                    title=""
                                                    data-localize="profile.middleName"
                                                    name="nameMNew"
                                                    id="middleName"
                                                />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="profile-desc-item form-line">
                                        <label class="desc-title" data-localize="profile.generation"
                                            ></label
                                        >
                                        <div class="desc-content">
                                            <span class="haveValue"
                                                >${sessionScope.userDetail.nameGen}</span
                                            >
                                            <div class="form-group valueInput">
                                                <select
                                                    class="form-control"
                                                    value="${nameRequest.nameGenNew}"
                                                    title=""
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
                                            class="btn btn-secondary"  data-localize="label.undo"
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
                            <form hidden="hidden" action="deleteNameRequest" id="deleteNameRequest" method="POST"></form>
                        </div>

                        <p class="sub-title" data-localize="profile.MaritalStatus"></p>
                        <form class="profile-item" id="maritalStatusForm" action="saveMarital" method="POST">
                            <div class="profile-left">
                                <div class="profile-item-line form-line">
                                    <div class="profile-title" data-localize="profile.local"></div>
                                    <div class="profile-desc">
                                        <span class="haveValue">
                                            ${sessionScope.userDetail.maritalStat}</span
                                        >
                                        <input hidden="hidden" type="text" name="empNbr" value="${mrtlRequest.id.empNbr}"  title="" data-localize="accessHint.employeeNumber">
                                            <input hidden="hidden" type="text" name="reqDts" value="${mrtlRequest.id.reqDts}" title="" data-localize="accessHint.reqDts">
                                        <div class="form-group valueInput">
                                            <select
                                                id="maritalStatus"
                                                name="maritalStatNew"
                                                class="form-control"
                                                title=""
                                                data-localize="profile.local"
                                                
                                            >
                                            <c:forEach var="marital" items="${maritalOptions}" varStatus="count">
                                                    <option value="${marital.code}" <c:if test="${marital.code == mrtlRequest.maritalStatNew }">selected</c:if>>${marital.description}</option>
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
                                            class="btn btn-secondary"  data-localize="label.undo"
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
                        <form hidden="hidden" action="deleteMaritalRequest" id="deleteMaritalRequest" method="POST"></form>
                        <p class="sub-title" data-localize="profile.driversLicense"></p>
                        <form class="profile-item" id="driverLicenseForm" action="saveDriversLicense" method="POST">
                         	<input hidden="hidden" type="text" name="empNbr" value="${licRequest.id.empNbr}" title="" data-localize="accessHint.employeeNumber">
                            <input hidden="hidden" type="text" name="reqDts" value="${licRequest.id.reqDts}" title="" data-localize="accessHint.reqDts">
                            <div class="profile-left">
                                <div class="profile-item-line form-line">
                                    <div class="profile-title" data-localize="profile.number"></div>
                                    <div class="profile-desc">
                                        <span class="haveValue"
                                            >${sessionScope.userDetail.driversLicNbr}</span
                                        >
                                        <div class="form-group valueInput">
                                            <input
                                                type="text"
                                                class="form-control"
                                                value="${licRequest.driversLicNbrNew}"
                                                name="driversLicNbrNew"
                                                id="driversLicenseNumber"
                                                data-localize="profile.driverLicenseNum"
                                                title=""
                                                
                                            />
                                        </div>
                                    </div>
                                </div>
                                <div class="profile-item-line form-line">
                                    <div class="profile-title" data-localize="profile.state"></div>
                                    <div class="profile-desc">
                                        <span class="haveValue"
                                            >${sessionScope.userDetail.driversLicSt}</span
                                        >
                                        
                                        <div class="form-group valueInput">
                                            <select
                                                id="driversState"
                                                name="driversLicStNew"
                                                value="${licRequest.driversLicStNew}"                                                
                                                class="form-control"
                                                title="driver state"
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
                                            class="btn btn-secondary"  data-localize="label.undo"
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
                        <form hidden="hidden" action="deleteDriversLicenseRequest" id="deleteDriversLicense" method="POST"></form>
                        <p class="sub-title" data-localize="profile.restrictionCodes"></p>
                        <form class="profile-item" id="restrictionCodeForm" action="saveRestrictionCodes" method="POST">
                        	<input hidden="hidden" type="text" name="empNbr" value="${restrictRequest.id.empNbr}" title="" data-localize="accessHint.employeeNumber">
                            <input hidden="hidden" type="text" name="reqDts" value="${restrictRequest.id.reqDts}" title="" data-localize="accessHint.reqDts">
                            <div class="profile-left">
                                <div class="profile-item-line form-line">
                                    <div class="profile-title" data-localize="profile.local"></div>
                                    <div class="profile-desc">
                                        <span class="haveValue"
                                            >${sessionScope.userDetail.restrictCd}</span
                                        >
                                        
                                         <div class="form-group valueInput">
                                             <select
                                                id="restrictionCodesLocalRestriction"
                                                name="restrictCdNew"
                                                class="form-control"
                                                title=""
                                                data-localize="profile.restrictionCodesLocal"
                                                
                                            >
                                            <c:forEach var="restrictions" items="${restrictionsOptions}" varStatus="count">
                                                    <option value="${restrictions.code}" <c:if test="${restrictions.code == restrictRequest.restrictCdNew }">selected</c:if>>${restrictions.code} - ${restrictions.description}</option>
                                                </c:forEach>
                                                </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="profile-item-line form-line">
                                    <div class="profile-title" data-localize="profile.public"></div>
                                    <div class="profile-desc">
                                        <span class="haveValue"
                                            >${sessionScope.userDetail.restrictCdPublic}</span
                                        >
                                       
                                        <div class="form-group valueInput">
                                             <select
                                                id="restrictionCodesPublicRestriction"
                                                name="restrictCdPublicNew"
                                                class="form-control"
                                                title=""
                                                style="width:188px"
                                                data-localize="profile.restrictionCodesPublic"
                                            >
                                            <c:forEach var="restriction" items="${restrictionsOptions}" varStatus="count">
                                                    <option value="${restriction.code}" <c:if test="${restriction.code == restrictRequest.restrictCdPublicNew }">selected</c:if>>${restriction.code} - ${restriction.description}</option>
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
                                            class="btn btn-secondary"  data-localize="label.undo"
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
                        <form hidden="hidden" action="deleteRestrictionCodesRequest" id="deleteRestrictionCodesRequest" method="POST"></form>
                        <p class="sub-title" data-localize="profile.email"></p>
                        <form class="profile-item" id="emailForm" action="saveEmail" method="POST">
                                <input hidden="hidden" type="text" name="empNbr" value="${emailRequest.id.empNbr}" title="" data-localize="accessHint.employeeNumber">
                                <input hidden="hidden" type="text" name="reqDts" value="${emailRequest.id.reqDts}" title="" data-localize="accessHint.reqDts">
                            <div class="profile-left">
                                <div class="profile-item-line form-line">
                                    <div class="profile-title" data-localize="profile.workEmail"></div>
                                    <div class="profile-desc">
                                        <span class="haveValue"
                                            >${sessionScope.userDetail.email}</span
                                        >
                                        <div class="form-group valueInput">
                                            <input
                                                class="form-control"
                                                name="emailNew"
                                                value="${emailRequest.emailNew}"
                                                id="emailWorkEmail"
                                                class="form-control"
                                                title=""
                                                data-localize="profile.workEmail"
                                                
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
                                        <div class="form-group valueInput">
                                            <input
                                                class="form-control"
                                                name="hmEmailNew"
                                                id="emailHomeEmail"
                                                title=""
                                                data-localize="profile.homeEmail"
                                                value="${emailRequest.hmEmailNew}"
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
                                        id="saveEmail"
                                    >
                                     
                                    </button>
                                    <button
                                            type="button"
                                            id="undoEmail"
                                            class="btn btn-secondary"  data-localize="label.undo"
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
                        <form hidden="hidden" action="deleteEmail" id="deleteEmail" method="POST"></form>
                        <p class="sub-title" data-localize="profile.emergenceContactInfo"></p>
                        <form class="profile-item" id="emergencyContactForm" action="saveEmergencyContact" method="POST">
                                <input hidden="hidden" type="text" name="empNbr" value="${emerRequest.id.empNbr}" title="" data-localize="accessHint.employeeNumber">
                                <input hidden="hidden" type="text" name="reqDts" value="${emerRequest.id.reqDts}" title="" data-localize="accessHint.reqDts">
                            <div class="profile-left">
                                <div class="profile-item-line form-line">
                                    <div class="profile-title" data-localize="profile.name"></div>
                                    <div class="profile-desc">
                                        <span class="haveValue"
                                            >${sessionScope.userDetail.emerContact}</span
                                        >
                                        <div class="form-group valueInput">
                                            <input
                                                class="form-control"
                                                type="text"
                                                id="emergencyContactName"
                                                name="emerContactNew"
                                                value="${emerRequest.emerContactNew}"
                                                title=""
                                                data-localize="profile.emergencyContactName"
                                                
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
                                                    class="form-control phoneAreaCode"
                                                    name="emerPhoneAcNew"
                                                    id="emergencyContactAreaCode"
                                                    title=""
                                                    data-localize="profile.emergencyContactAreaCode"
                                                    value="${emerRequest.emerPhoneAcNew}"
                                                />
                                            </div>
                                            <div class="form-group">
                                                <input
                                                    class="form-control"
                                                    name="emerPhoneNbrNew"
                                                    id="emergencyContactPhoneNumber"
                                                    title=""
                                                    data-localize="profile.emergencyContactPhoneNumber"
                                                    value="${emerRequest.emerPhoneNbrNew}"
                                                />
                                            </div>

                                            <span data-localize="profile.ext"></span>
                                            <div class="form-group">
                                                <input
                                                    class="form-control phoneAreaCode"
                                                    name="emerPhoneExtNew"
                                                    id="emergencyContactExtention"
                                                    title=""
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
                                                class="form-control"
                                                name="emerRelNew"
                                                id="emergencyContactRelationship"
                                                title=""
                                                data-localize="profile.relationship"
                                                value="${emerRequest.emerRelNew}"
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
                                                class="form-control"
                                                name="emerNoteNew"
                                                id="emergencyContactEmergencyNotes"
                                                title=""
                                                data-localize="profile.emergencyNotes"
                                                value="${emerRequest.emerNoteNew}"
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
                                            class="btn btn-secondary"  data-localize="label.undo"
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
                        <form hidden="hidden" action="deleteEmergencyContact" id="deleteEmergencyContact" method="POST"></form>
                        <p class="sub-title" data-localize="profile.mailingAddress"></p>
                        <form class="profile-item" id="mailingAddressForm" action="saveMailAddr" method="POST">
                                <input hidden="hidden" type="text" name="empNbr" value="${mailAddrRequest.id.empNbr}" title="" data-localize="accessHint.employeeNumber">
                                <input hidden="hidden" type="text" name="reqDts" value="${mailAddrRequest.id.reqDts}" title="" data-localize="accessHint.reqDts">
                            <div class="profile-left">
                                <div class="profile-item-line form-line">
                                    <div class="profile-title" data-localize="profile.number"></div>
                                    <div class="profile-desc">
                                        <span class="haveValue"
                                            >${sessionScope.userDetail.addrNbr}</span
                                        >
                                        <div class="form-group valueInput">
                                            <input
                                                class="form-control"
                                                name="addrNbrNew"
                                                id="mailAddrNumber"
                                                title=""
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
                                                class="form-control"
                                                name="addrStrNew"
                                                id="mailAddrStr"
                                                title=""
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
                                                class="form-control"
                                                name="addrAptNew"
                                                id="mailAddrApartment"
                                                title=""
                                                data-localize="profile.apt"
                                                value="${mailAddrRequest.addrAptNew}"
                                            />
                                        </div>
                                    </div>
                                </div>
                                <div class="profile-item-line form-line">
                                    <div class="profile-title" data-localize="profile.city"></div>
                                    <div class="profile-desc">
                                        <span class="haveValue"
                                            >${sessionScope.userDetail.addrCity}</span
                                        >
                                        <div class="form-group valueInput">
                                            <input
                                                class="form-control"
                                                name="addrCityNew"
                                                id="mailAddrCity"
                                                title=""
                                                data-localize="profile.city"
                                                value="${mailAddrRequest.addrCityNew}"
                                            />
                                        </div>
                                    </div>
                                </div>
                                <div class="profile-item-line form-line">
                                    <div class="profile-title" data-localize="profile.state"></div>
                                    <div class="profile-desc">
                                        <span class="haveValue"
                                            >${sessionScope.userDetail.addrSt}</span
                                        >
                                        <div class="form-group valueInput">
                                            <select
                                                id="mailAddrState"
                                                name="addrStNew"
                                                title=""
                                                data-localize="profile.state"
                                                class="form-control"
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
                                                class="form-control"
                                                name="addrZipNew"
                                                id="mailAddrZip"
                                                title=""
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
                                                class="form-control"
                                                name="addrZip4New"
                                                id="mailAddrZipPlusFour"
                                                title=""
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
                                            class="btn btn-secondary"  data-localize="label.undo"
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
                        <form hidden="hidden" action="deleteMailAddr" id="deleteMailAddr" method="POST"></form>
                        <p class="sub-title" data-localize="profile.altAddr"></p>
                        <form class="profile-item" id="alternativeAddressForm" action="saveAltMailAddr" method="POST">
                                <input hidden="hidden" type="text" name="empNbr" value="${altMailAddrRequest.id.empNbr}" title="" data-localize="accessHint.employeeNumber">
                                <input hidden="hidden" type="text" name="reqDts" value="${altMailAddrRequest.id.reqDts}" title="" data-localize="accessHint.reqDts">
                            <div class="profile-left">
                                <div class="profile-item-line form-line">
                                    <div class="profile-title" data-localize="profile.number"></div>
                                    <div class="profile-desc">
                                        <span class="haveValue"
                                            >${sessionScope.userDetail.smrAddrNbr}</span
                                        >
                                        <div class="form-group valueInput">
                                            <input
                                                class="form-control"
                                                name="smrAddrNbrNew"
                                                id="altAddrNumber"
                                                title=""
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
                                                class="form-control"
                                                name="smrAddrStrNew"
                                                id="altAddrStr"
                                                title=""
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
                                                class="form-control"
                                                name="smrAddrAptNew"
                                                id="altAddrApartment"
                                                title=""
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
                                                class="form-control"
                                                name="smrAddrCityNew"
                                                id="altAddrCity"
                                                title=""
                                                data-localize="profile.city"
                                                value="${altMailAddrRequest.smrAddrCityNew}"
                                            />
                                        </div>
                                    </div>
                                </div>
                                <div class="profile-item-line form-line">
                                    <div class="profile-title" data-localize="profile.state"></div>
                                    <div class="profile-desc">
                                        <span class="haveValue"
                                            >${sessionScope.userDetail.smrAddrSt}</span
                                        >
                                        <div class="form-group valueInput">
                                            <select
                                                id="altAddrState"
                                                name="smrAddrStNew"
                                                title=""
                                                data-localize="profile.state"
                                                class="form-control"
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
                                                class="form-control"
                                                name="smrAddrZipNew"
                                                id="altAddrZip"
                                                title=""
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
                                                class="form-control"
                                                name="smrAddrZip4New"
                                                id="altAddrZipPlusFour"
                                                title=""
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
                                            class="btn btn-secondary"  data-localize="label.undo"
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
                        <form hidden="hidden" action="deleteAltMailAddr" id="deleteAltMailAddr" method="POST"></form>
                        <p class="sub-title" data-localize="profile.phoneNumbers"></p>
                        <form class="profile-item" id="phoneForm" action="savePhone" method="POST">
                                <input hidden="hidden" type="text" name="empNbr" value="${hmRequest.id.empNbr}" title="" data-localize="accessHint.employeeNumber">
                                <input hidden="hidden" type="text" name="reqDts" value="${hmRequest.id.reqDts}" title="" data-localize="accessHint.reqDts">
                            <div class="profile-left">
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
                                                    class="form-control phoneAreaCode"
                                                    name="phoneAreaNew"
                                                    id="homePhoneAreaCode"
                                                    title=""
                                                    data-localize="profile.homePhoneAreaCode"
                                                    value="${hmRequest.phoneAreaNew}"
                                                    
                                                />
                                            </div>
                                            <div class="form-group">
                                                <input
                                                    class="form-control"
                                                    name="phoneNbrNew"
                                                    id="homePhonePhoneNumber"
                                                    title=""
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
                                                    class="form-control phoneAreaCode"
                                                    name="phoneAreaCellNew"
                                                    id="cellPhoneAreaCode"
                                                    title=""
                                                    data-localize="profile.cellPhoneAreaCode"
                                                    value="${cellRequest.phoneAreaCellNew}"
                                                />
                                            </div>

                                            <div class="form-group">
                                                <input
                                                    class="form-control"
                                                    name="phoneNbrCellNew"
                                                    id="cellPhonePhoneNumber"
                                                    title=""
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
                                                    class="form-control phoneAreaCode"
                                                    name="phoneAreaBusNew"
                                                    id="workPhoneAreaCode"
                                                    title=""
                                                    data-localize="profile.workPhoneAreaCode"
                                                    value="${busRequest.phoneAreaBusNew}"
                                                />
                                            </div>

                                            <div class="form-group">
                                                <input
                                                    class="form-control"
                                                    name="phoneNbrBusNew"
                                                    id="workPhonePhoneNumber"
                                                    title=""
                                                    data-localize="profile.workPhonePhoneNumber"
                                                    value="${busRequest.phoneNbrBusNew}"
                                                />
                                            </div>

                                            <span data-localize="profile.ext"></span>
                                            <div class="form-group">
                                                <input
                                                    class="form-control phoneAreaCode"
                                                    name="busPhoneExtNew"
                                                    id="workPhoneExtention"
                                                    title=""
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
                                            class="btn btn-secondary"  data-localize="label.undo"
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
                        <form hidden="hidden" action="deletePhone" id="deletePhone" method="POST"></form>
                        <p class="sub-title" data-localize="profile.W4MaritalStatusInfo"></p>
                        <form
                            class="no-print searchForm"
                            action="profile"
                            id="changeFreqForm"
                            method="POST"
										>
										
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
                        	<input hidden="hidden" type="text" name="empNbr" value="${w4Request.id.empNbr}" title="" data-localize="accessHint.employeeNumber">
                            <input hidden="hidden" type="text" name="reqDts" value="${w4Request.id.reqDts}" title="" data-localize="accessHint.reqDts">
                            <input hidden="hidden" type="text" name="payFreq" value="${w4Request.id.payFreq}"  title="" data-localize="accessHint.payFreq">
                            <input hidden="hidden" type="text" name="maritalStatTax" value="${payInfo.maritalStatTax}" title="" data-localize="accessHint.maritalStatTax">
                            <input hidden="hidden" type="text" name="nbrTaxExempts" value="${payInfo.nbrTaxExempts}" title="" data-localize="accessHint.nbrTaxExempts">
                            <div class="profile-left">
                                <div class="profile-item-line form-line">
                                    <div class="profile-title" data-localize="profile.MaritalStatus">
                                       
                                    </div>
                                    <div class="profile-desc">
                                        <span class="haveValue"
                                            >${payInfo.maritalStatTax}</span
                                        >
                                        <div class="form-group valueInput">
                                            <select
                                                id="maritalStatusLabel"
                                                name="maritalStatTaxNew"
                                                class="form-control"
                                                value="${w4Request.maritalStatTaxNew}"
                                                title=""
                                                data-localize="profile.MaritalStatus"
                                                
                                            >
                                                <c:forEach var="maritalTax" items="${maritalTaxOptions}" varStatus="count">
                                                    <option value="${maritalTax.code}" <c:if test="${maritalTax.code == w4Request.maritalStatTaxNew }">selected</c:if>>${maritalTax.description}</option>
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
                                                class="form-control"
                                                id="nbrTaxExemptsNew"
                                                name="nbrTaxExemptsNew"
                                                title=""  data-localize="profile.NbrOfExemptions"
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
                                            class="btn btn-secondary"  data-localize="label.undo"
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
                        	<input hidden="hidden" type="text" name="empNbr" value="${w4Request.id.empNbr}" title="" data-localize="accessHint.employeeNumber">
                            <input hidden="hidden" type="text" name="reqDts" value="${w4Request.id.reqDts}" title="" data-localize="accessHint.reqDts">
                            <input hidden="hidden" type="text" name="payFreq" value="${w4Request.id.payFreq}" title="" data-localize="accessHint.payFreq">
                            <input hidden="hidden" type="text" name="maritalStatTax" value="${payInfo.maritalStatTax}" title="" data-localize="accessHint.maritalStatTax">
                            <input hidden="hidden" type="text" name="nbrTaxExempts" value="${payInfo.nbrTaxExempts}" title="" data-localize="accessHint.nbrTaxExempts">
                        </form>
                        <p class="sub-title" data-localize="profile.directDepositBankAccounts"></p>
                        
                        <c:forEach var="bank" items="${banks}" varStatus="count">
							
							
						<form
                            	class="profile-item border-0 bankAccountBlock  <c:if test="${bank.isDelete == false}">usedBank</c:if>  <c:if test="${bank.isDelete == true}">isDelete</c:if>"
                                id="bankAccountForm_${count.index}"
                                method="POST"
                        		>
                            <div class="profile-left">
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
                                                    title="" 
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
                                        
                                        <input type="hidden"  class="form-control bankcode bankNewCode"  id="codeNew_${count.index}" value="${bank.codeNew.code}"  title="" data-localize="accessHint.bankCodeNew"/>
                                        <input type="hidden" id="code_${count.index}" value="${bank.code.code}"  title="" data-localize="accessHint.bankCode"/>
                                        
                                        <div class="valueInput group-line">
                                            <div class="form-group inputDisabled">
                                                <input
                                                    class="form-control name"
                                                    type="text"
                                                    name="description"
                                                    title="" 
                                                    data-localize="accessHint.description"
                                                    value="${bank.codeNew.description}"
                                                />
                                            </div>

                                            <div class="form-group inputDisabled">
                                                <input
                                                    class="form-control code"
                                                    type="text"
                                                    name="subCode"
                                                    title="" 
                                                    data-localize="accessHint.bankCode"
                                                    value="${bank.codeNew.subCode}"
                                                />
                                            </div>

                                            <button
                                                class="btn btn-secondary xs getBank"
                                                type="button"
                                                data-toggle= "modal"
                                                data-target="#selectBankModal"
                                            >
                                                <span class="hide" data-localize="profile.chooseBank"></span>
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
                                                class="form-control"
                                                type="text"
                                                title=""
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
                                                class="form-control"
                                                title=""
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
                                    <div class="profile-desc">
                                        <span class="haveValue" id="displayAmount_${count.index}"
                                            >${bank.depositAmount.displayAmount}</span
                                        >
                                        <div class="form-group valueInput">
                                            <input
                                                class="form-control amount_2"
                                                id="displayAmountNew_${count.index}"
                                                type="text"
                                                title="" data-localize="profile.bankAcctAmt"
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
                                            class="btn btn-secondary undo-btn"  data-localize="label.undo"
                                            onclick="undoBank(${count.index})"
                                        >
                                        
                                    </button>
                                    <button
                                        type="button"
                                        id="deleteBank_${count.index}"
                                        class="btn btn-secondary delete-btn"  data-localize="label.delete" onclick="deleteBankAmount(${count.index})"
                                    >
                                     
                                    </button>
                                </div>
                            </div>
                        </form>
                                                   
                        </c:forEach>

                        <div>
                        <form hidden="hidden" action="updateBank" id="updateBankHidden" method="POST">
	                        <input type="hidden" name="freq" id="hidden_freq_update"   title="" data-localize="accessHint.reqDts"/>
	                        <input type="hidden" name="accountNumberNew" id="hidden_accountNumberNew_update"   title="" data-localize="accessHint.accountNumberNew"/>
	                        <input type="hidden" name="accountNumber" id="hidden_accountNumber_update"   title="" data-localize="accessHint.accountNumber"/>
	                        <input type="hidden" name="code" id="hidden_code_update" />
	                        <input type="hidden" name="codeNew" id="hidden_codeNew_update"   title="" data-localize="accessHint.reqDts"/>
	                        
	                        <input type="hidden" name="accountType" id="hidden_accountType_update"   title="" data-localize="accessHint.accountType"/>
	                        <input type="hidden" name="accountTypeNew" id="hidden_accountTypeNew_update"   title="" data-localize="accessHint.accountTypeNew"/>
	                        <input type="hidden" name="displayAmount" id="hidden_displayAmount_update"   title="" data-localize="accessHint.displayAmount"/>
	                        <input type="hidden" name="displayAmountNew" id="hidden_displayAmountNew_update"   title="" data-localize="accessHint.displayAmountNew"/>
                        </form>
                        <form hidden="hidden" action="undoBank" id="undoBankHidden" method="POST">
	                        <input type="hidden" name="freq" id="hidden_freq_undo"   title="" data-localize="accessHint.frequency"/>
	                        <input type="hidden" name="accountNumberNew" id="hidden_accountNumberNew_undo"   title="" data-localize="accessHint.accountNumberNew"/>
	                        <input type="hidden" name="accountNumber" id="hidden_accountNumber_undo"   title="" data-localize="accessHint.accountNumber"/>
	                        <input type="hidden" name="code" id="hidden_code_undo"   title="" data-localize="accessHint.bankCode"/>
	                        <input type="hidden" name="codeNew" id="hidden_codeNew_undo"   title="" data-localize="accessHint.bankCodeNew"/>
                        </form>
                        <form hidden="hidden" action="deleteBank"  id="deleteBankHidden" method="POST">
	                         <input type="hidden" name="freq" id="hidden_freq_delete"   title="" data-localize="accessHint.frequency"/>
	                        <input type="hidden" name="accountNumber" id="hidden_accountNumber_delete"   title="" data-localize="accessHint.accountNumber"/>
	                        <input type="hidden" name="code" id="hidden_code_delete"  title="" data-localize="accessHint.bankCode"/>
	                        <input type="hidden" name="accountType" id="hidden_accountType_delete"   title="" data-localize="accessHint.accountType"/>
	                        <input type="hidden" name="displayAmount" id="hidden_displayAmount_delete"   title="" data-localize="accessHint.displayAmount"/>
                        </form>
                        <form hidden="hidden" action="saveBank" id="saveBankHidden" method="POST">
	                        <input type="hidden" name="freq" id="hiddenfreq"   title="" data-localize="accessHint.frequency"/>
	                        <input type="hidden" name="displayAmount" id="hiddendisplayAmount"  title="" data-localize="accessHint.displayAmount"/>
	                        <input type="hidden" name="displayLabel" id="hiddendisplayLabel"   title="" data-localize="accessHint.displayLabel"/>
	                        <input type="hidden" name="accountNumber" id="hiddenaccountNumber"   title="" data-localize="accessHint.accountNumber"/>
	                        <input type="hidden" name="subCode" id="hiddensubCode"   title="" data-localize="accessHint.bankCode"/>
	                        <input type="hidden" name="description" id="hiddendescription"   title="" data-localize="accessHint.bankName"/>
                        </form>
                            <form
                                class="profile-item border-0 activeEdit addBankForm"
                                id="addBankAccountForm"
                                method="POST"
                            >
                                <div class="profile-left">
                                    <div class="profile-item-line form-line">
                                        <div class="profile-title" data-localize="profile.bankName">
                                        </div>
                                        <div class="profile-desc">
                                            <div class="valueInput group-line">
                                                <div class="form-group">
                                                    <input
                                                        class="form-control name"
                                                        type="text"
                                                        name="description"
                                                        id="saveBankDescription"
                                                        title="" data-localize="accessHint.bankName"
                                                        value=""
                                                    />
                                                </div>

                                                <div class="form-group">
                                                    <input
                                                        class="form-control code"
                                                        type="text"
                                                        name="subCode"
                                                         id="saveBankCode"
                                                         title="" data-localize="accessHint.bankCode"
                                                        value=""
                                                    />
                                                </div>

                                                <button
                                                    class="btn btn-secondary xs getBank"
                                                    type="button"
                                                    data-toggle= "modal"
                                                    data-target="#selectBankModal"
                                                >
                                                    <span class="hide" data-localize="profile.chooseBank"></span>
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
                                                    title="" data-localize="profile.bankAcctNbr"
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
                                                    title="" data-localize="profile.bankAcctType"
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
                                                    title="" data-localize="profile.bankAcctType"
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
                        >
                        <span class="hide" data-localize="label.closeModal"></span>
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
                                                                <button class="pageBtn firstPate" title="" data-localize="label.firstPage" data-localize-location="title">
                                                                        <i class="fa fa-angle-double-left "></i>
                                                                </button>  
                                                                <button class="pageBtn prevPage" title="" data-localize="label.prevPage" data-localize-location="title">
                                                                        <i class="fa fa-angle-left "></i>
                                                                </button>
                                                                <select class="selectPage" name="page" id="pageNow" title="" data-localize="label.choosePage" onchange="changePage()"  data-localize-location="title">
                                                                        <option value="1">1</option>
                                                                        <option value="2">2</option>
                                                                </select>
                                                                <div class="page-list">
                                                                        <span class="slash">/</span>
                                                                        <span class="totalPate">2</span>
                                                                </div>
                                                                <button class="pageBtn nextPate" title="" data-localize="label.nextPage" data-localize-location="title">
                                                                                <i class="fa fa-angle-right "></i>
                                                                </button>
                                                                <button class="pageBtn lastPate" title="" data-localize="label.lastPage" data-localize-location="title">
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
    <script>
        let bank01, bank02
        var formSelect
        var formUndoSelect
        var willSubmitFormDelete
        $(function() {
            personalValidator()
            maritalStatusValidator()
            driverLicenseValidator()
            restrictionCodeFormValidator()
            emailFormValidator()
            emergencyContactFormValidator()
            mailingAddressValidator()
            alternativeAddressValidator()
            phoneValidator()
            w4InfoValidator()
            initSessionPws()
            //edit
            bankAccountValidator()
            //add
            bankAccountAddValidator()
            $(".icheckRadioBank").on('click', function(event) {
                if($(this).is(':checked')){
                    let  indexBank = $(".icheckRadioBank").index(this);
                    $('.icheckRadioBank').each(function(index){
                        if(index!=indexBank){
                            $(this).prop('checked',false);
                        }
                    })
                }
            })
            $(".icheckRadioBank").keypress(function(e){
                console.log(e)
                var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
                if (eCode == 13){
                    $(this).click()
                    let  indexBank = $(".icheckRadioBank").index(this);
                    $('.icheckRadioBank').each(function(index){
                        if(index!=indexBank){
                            $(this).prop('checked',false);
                        }
                    })
                }
            })
            let bankInputName, bankInputCode
            $('.edit-btn').click(function() {
                $('.addBankForm').hide()
                $('.add-bank-btn').show()
                $('.profile-item').removeClass('activeEdit')
                $(this)
                    .parents('.profile-item')
                    .addClass('activeEdit')
                let groupSize = $(this)
                    .parents('.profile-item')
                    .find('.form-line:first-child .form-group').length
                console.log(groupSize)
                if (groupSize > 1) {
                    $(this)
                        .parents('.profile-item')
                        .find('.form-line:first-child')
                        .find('.form-group:first-child .form-control')
                        .focus()
                } else {
                    $(this)
                        .parents('.profile-item')
                        .find('.form-line:first-child')
                        .find('.form-control')
                        .focus()
                    $(this)
                        .parents('.profile-item')
                        .find('.form-line:first-child')
                        .find('.icheck')
                        .focus()
                }
            })
            $('.cancel-btn').click(function() {
                $(this)
                    .parents('.profile-item')
                    .removeClass('activeEdit')
                clearValidator()
            })
            // $('.save-btn').click(function() {
            //     $(this)
            //         .parents('.profile-item')
            //         .removeClass('activeEdit')
            // })
            $('.cancel-add-btn').click(function() {
                $('.addBankForm').hide()
                $('.add-bank-btn').show()
            })
            $('.add-bank-btn').click(function() {
                let arrayBankLength = $('.usedBank').length
                $('.profile-item').removeClass('activeEdit')
                $('.addBankForm').addClass('activeEdit')
                if (arrayBankLength >= 2) {
                    $('.bankSizeError').show()
                } else {
                    $('.addBankForm').show()
                    $(this).hide()
                }
            })
            
            $('#saveNewBank').click(function() {
                let bankAccountValidator = $("#addBankAccountForm").data('bootstrapValidator')
                bankAccountValidator.validate()
                console.log(bankAccountValidator.isValid())
                if (bankAccountValidator.isValid()) {
                    var freq = $('#freq').val();
                    var saveBankDescription = $('#saveBankDescription').val();
                    var saveBankCode = $('#saveBankCode').val();
                    var saveBankAccountNumber = $('#saveBankAccountNumber').val();
                    var saveBankDisplayLabel = $('#saveBankDisplayLabel').val();
                    var saveBankDisplayAmount = $('#saveBankDisplayAmount').val();
                    
                    $('#hiddenfreq').val(freq);
                    $('#hiddendescription').val(saveBankDescription);
                    $('#hiddensubCode').val(saveBankCode);
                    $('#hiddenaccountNumber').val(saveBankAccountNumber);
                    $('#hiddendisplayLabel').val(saveBankDisplayLabel);
                    $('#hiddendisplayAmount').val(saveBankDisplayAmount);

                    $('#saveBankHidden').submit();
                }

            })
            
            $('.getBank').click(function() {
            	
            	var page={
            			currentPage:1,
            			perPageRows:10
            	};
                let that = this
            	$.ajax({
                     type: "POST",
                     dataType: "json",
                     url: "/profile/getAllBanks" ,
                     data:JSON.stringify(page),
                     contentType: 'application/json;charset=UTF-8',
                     success: function (result) {
                    	 console.log(result);
                    	 //$('#bankTable').find('tr').remove();
                    	 $("#bankTable  tr:not(:first)").empty(""); 
                    	 var res = result.result;
                    	 for (var p in res) {
                    		 var bankTr= "<tr><td data-localize='profile.routingNumber' data-localize-location='scope'>";
                    		 bankTr = bankTr + "<button class='a-btn bankNumberBtn' type='button' value='"+res[p].bankCd+"' data-title='"+res[p].bankName+"' > "+ res[p].transitRoute +" </button> </td>";
                    		 bankTr = bankTr + " <td data-localize='profile.bankName' data-localize-location='scope'>"+res[p].bankName+"</td> </tr>";
                    		 $("#bankTable").append(bankTr);
                    	 }
                    	 
                    	    // $('#selectBankModal').modal('show')
                            bankInputName = $(that)
                                .parent()
                                .find('.form-control.name');
                    	    bankInputBankCode = $(that)
                            .parent()
                            .find('.form-control.bankcode');
                            bankInputCode = $(that)
                                .parent()
                                .find('.form-control.code');
                         bankInputNewCode = $(that)
                                .parents(".profile-desc")
                                .find('.bankNewCode');
                            
                            $('.bankNumberBtn').click(function() {
                            	let number = $(this).text()
                                let code = $(this).val()
                                let name = $(this).attr('data-title')
                                console.log(number)
                                console.log(name)
                                console.log(code)
                                bankInputName.val(name).change()
                                bankInputBankCode.val(code)
                                bankInputCode.val(number).change()
                                bankInputNewCode.val(code)
                                $('#selectBankModal').modal('hide')
                            })
                    	 
                     },
                     error : function(e) {
                    	 console.log(e);
                     }
                 });
            	
            })
            $('#searchBankBtn').click(function() {
            	
            	var page={
            			"currentPage":1,
            			"perPageRows":10
            	};
            	
            	
            	var searchCode = $('#codeCriteriaSearchCode').val();
				var searchDescription = $('#codeCriteriaSearchDescription').val();
            	
            	var criteria ={
            			  "searchCode":searchCode,
            			  "searchDescription":searchDescription
            	}
            	
            	var searchCriteria = {"page":page,"criteria":criteria};
                let that = this
            	$.ajax({
                     type: "POST",
                     dataType: "json",
                     url: "/profile/searchBanks" ,
                     data:JSON.stringify(searchCriteria),
                     contentType: 'application/json;charset=UTF-8',
                     success: function (result) {
                    	 console.log(result);
                         $("#bankTable  tr:not(:first)").empty(""); 
                         if(result.result&&result.result.length>0){
                            var res = result.result;
                    	 for (var p in res) {
                    		 var bankTr= "<tr><td data-localize='profile.routingNumber' data-localize-location='scope'>";
                    		 bankTr = bankTr + "<button class='a-btn bankNumberBtn' type='button' value='"+res[p].bankCd+"' data-title='"+res[p].bankName+"' > "+ res[p].transitRoute +" </button> </td>";
                    		 bankTr = bankTr + " <td data-localize='profile.bankName' data-localize-location='scope'>"+res[p].bankName+"</td> </tr>";
                    		 $("#bankTable").append(bankTr);
                    	 }
                    	 
                    	    //$('#selectBankModal').modal('show')
                            bankInputName = $(that)
                                .parent()
                                .find('.form-control.name');
                            bankInputBankCode = $(that)
                            .parent()
                            .find('.form-control.bankcode');
                            bankInputCode = $(that)
                                .parent()
                                .find('.form-control.code');
                            
                            $('.bankNumberBtn').click(function() {
                                let number = $(this).text()
                                let code = $(this).val()
                                let name = $(this).attr('data-title')
                                console.log(number)
                                console.log(name)
                                console.log(code)
                                bankInputName.val(name).change()
                                bankInputBankCode.val(code)
                                bankInputCode.val(number).change()
                                $('#selectBankModal').modal('hide')
                            })
                         }else{
                            $("#bankTable tbody").empty()
                            let noResult = `<tr><td colspan="2"> <span data-localize="label.noData"></span></td></tr>`
                            $("#bankTable tbody").append(noResult)
                         }
                    	 
                            setGlobal()
                    	 
                     },
                     error : function(e) {
                    	 console.log(e);
                         $("#bankTable tbody").empty()
                         let noResult = `<tr><td colspan="2"> <span data-localize="label.noData"></span></td></tr>`
                         $("#bankTable tbody").append(noResult)
                         setGlobal()
                     }
                 });
            })
          
            
            $("#undoNameRequest").click(function(){
                $('#undoModal').modal('show')
                formSelect = $("#deleteNameRequest")
            })
            $("#undoMaritalRequest").click(function(e){
            	e.preventDefault();
                $('#undoModal').modal('show')
                formSelect = $("#deleteMaritalRequest")
            })
            $("#undoDriverLicense").click(function(){
                $('#undoModal').modal('show')
                formSelect = $("#deleteDriversLicense")
            })
            $("#undoRestriction").click(function(){
                $('#undoModal').modal('show')
                formSelect = $("#deleteRestrictionCodesRequest")
            })
            $("#undoEmail").click(function(){
                $('#undoModal').modal('show')
                formSelect = $("#deleteEmail")
            })
            $("#undoEmergencyContact").click(function(){
                $('#undoModal').modal('show')
                formSelect = $("#deleteEmergencyContact")
            })
            $("#undoMailingAddress").click(function(){
                $('#undoModal').modal('show')
                formSelect = $("#deleteMailAddr")
            })
            $("#undoAlternative").click(function(){
                $('#undoModal').modal('show')
                formSelect = $("#deleteAltMailAddr")
            })
            $("#undoPhoneNumber").click(function(){
                $('#undoModal').modal('show')
                formSelect = $("#deletePhone")
            })
             $("#undoW4").click(function(){
                $('#undoModal').modal('show')
                formSelect = $("#deleteW4")
            })
            $(".sureUndo").click(function(){
                console.log("modal -- undo")
                formSelect.submit();
            })
            $(".sureDelete").click(function(){
                console.log("modal -- delete")
                willSubmitFormDelete.submit();
            })
        })
        function deleteBankAmount(index){
            $('#deleteModal').modal('show')
            console.log("delete="+index)
            
            var freq = $('#freq').val();
	       	var code = $('#code_'+index).val();
	       	var accountNumber = $('#accountNumber_'+index).text();
	       	var accountType = $('#accountType_'+index).text();
	       	var displayAmount = $('#displayAmount_'+index).text();
               console.log(code)
               console.log(accountNumber)
               console.log(accountType)
               console.log(displayAmount)
       	 
             $('#hidden_freq_delete').val(freq);
	       	 $('#hidden_accountNumber_delete').val(accountNumber);
	       	 $('#hidden_code_delete').val(code);
	       	 
	       	 $('#hidden_accountType_delete').val(accountType);
	       	 $('#hidden_displayAmount_delete').val(displayAmount);
            willSubmitFormDelete = $('#deleteBankHidden')
        }
        function updateBank(index){
             console.log("updateBank="+index)
             let bankAccountForm = '#bankAccountForm_'+index
             let bankAccountValidator = $(bankAccountForm).data('bootstrapValidator')
             console.log( $(bankAccountForm))
             console.log(bankAccountValidator)
             bankAccountValidator.validate()
            console.log(bankAccountValidator.isValid())
            if (bankAccountValidator.isValid()) {
                var freq = $('#freq').val();
 	       	var code = $('#code_'+index).val();
 	       	var accountNumber = $('#accountNumber_'+index).text();
 	       	var accountType = $('#accountType_'+index).text();
 	       	var displayAmount = $('#displayAmount_'+index).text();
        	 
 	   		var codeNew = $('#codeNew_'+index).val();
                console.log("codeNew" + codeNew)
               var accountNumberNew = $('#accountNumberNew_'+index).val();
              
               var accountTypeNew = $('#accountTypeNew_'+index).val();
               console.log(accountTypeNew)
	       	var displayAmountNew = $('#displayAmountNew_'+index).val();
 	       	
             $('#hidden_freq_update').val(freq);
 	       	 $('#hidden_code_update').val(code);
 	       	 $('#hidden_codeNew_update').val(codeNew);
 	       	 $('#hidden_accountNumber_update').val(accountNumber);
 	       	 $('#hidden_accountNumberNew_update').val(accountNumberNew);
 	       	 
 	       	$('#hidden_accountType_update').val(accountType);
	       	 $('#hidden_accountTypeNew_update').val(accountTypeNew);
	       	 $('#hidden_displayAmount_update').val(displayAmount);
	       	 $('#hidden_displayAmountNew_update').val(displayAmountNew);
 	       	 $('#updateBankHidden').submit();
            }
            
        }
        function undoBank(index){
             var freq = $('#freq').val();
	       	 var code = $('#code_'+index).val();
	       	 var codeNew = $('#codeNew_'+index).val();
	       	 var accountNumber = $('#accountNumber_'+index).text();
	       	 var accountNumberNew = $('#accountNumberNew_'+index).val();
       	 
             $('#hidden_freq_undo').val(freq);
	       	 $('#hidden_code_undo').val(code);
	       	 $('#hidden_codeNew_undo').val(codeNew);
	       	 $('#hidden_accountNumber_undo').val(accountNumber);
	       	 $('#hidden_accountNumberNew_undo').val(accountNumberNew);
                
             $('#undoModal').modal('show')
             formSelect = $('#undoBankHidden')

        }
        
            function changeFreq(){
				$("#changeFreqForm")[0].submit();
			}
        function clearValidator() {
            // reset  #personalForm form
            $('#personalForm')
                .data('bootstrapValidator')
                .destroy()
            $('#personalForm').data('bootstrapValidator', null)
            $('#personalForm')[0].reset()
            personalValidator()
            // reset  Marital Status form
            $('#maritalStatusForm')
                .data('bootstrapValidator')
                .destroy()
            $('#maritalStatusForm').data('bootstrapValidator', null)
            $('#maritalStatusForm')[0].reset()
            maritalStatusValidator()
            //reset driver license form
            $('#driverLicenseForm')
                .data('bootstrapValidator')
                .destroy()
            $('#driverLicenseForm').data('bootstrapValidator', null)
            $('#driverLicenseForm')[0].reset()
            driverLicenseValidator()
            // reset Restriction Codes form
            $('#restrictionCodeForm')
                .data('bootstrapValidator')
                .destroy()
            $('#restrictionCodeForm').data('bootstrapValidator', null)
            $('#restrictionCodeForm')[0].reset()
            restrictionCodeFormValidator()
            // reset email form
            $('#emailForm')
                .data('bootstrapValidator')
                .destroy()
            $('#emailForm').data('bootstrapValidator', null)
            $('#emailForm')[0].reset()
            emailFormValidator()
            // reset Emergency Contact Information form
            $('#emergencyContactForm')
                .data('bootstrapValidator')
                .destroy()
            $('#emergencyContactForm').data('bootstrapValidator', null)
            $('#emergencyContactForm')[0].reset()
            emergencyContactFormValidator()
            // reset Mailing Address form
            $('#mailingAddressForm')
                .data('bootstrapValidator')
                .destroy()
            $('#mailingAddressForm').data('bootstrapValidator', null)
            $('#mailingAddressForm')[0].reset()
            mailingAddressValidator()

            // reset Alternative Address form
            $('#alternativeAddressForm')
                .data('bootstrapValidator')
                .destroy()
            $('#alternativeAddressForm').data('bootstrapValidator', null)
            $('#alternativeAddressForm')[0].reset()
            alternativeAddressValidator()
            // reset Phone Numbers form
            $('#phoneForm')
                .data('bootstrapValidator')
                .destroy()
            $('#phoneForm').data('bootstrapValidator', null)
            $('#phoneForm')[0].reset()
            phoneValidator()

            // reset W4 Marital Status Information form
            $('#w4InfoForm')
                .data('bootstrapValidator')
                .destroy()
            $('#w4InfoForm').data('bootstrapValidator', null)
            $('#w4InfoForm')[0].reset()
            w4InfoValidator()

            //reset Direct Deposit Bank01 Accounts
            if (bank01 != 0) {
                $('#bankAccountForm_01')
                    .data('bootstrapValidator')
                    .destroy()
                $('#bankAccountForm_01').data('bootstrapValidator', null)
                $('#bankAccountForm_01')[0].reset()
                bankAccount01Validator()
            }
            //reset Direct Deposit Bank02 Accounts
            if (bank02 != 0) {
                $('#bankAccountForm_02')
                    .data('bootstrapValidator')
                    .destroy()
                $('#bankAccountForm_02').data('bootstrapValidator', null)
                $('#bankAccountForm_02')[0].reset()
                bankAccount02Validator()
            }
            //reset add Direct Deposit Bank Accounts
            $('#addBankAccountForm')
                .data('bootstrapValidator')
                .destroy()
            $('#addBankAccountForm').data('bootstrapValidator', null)
            $('#addBankAccountForm')[0].reset()
            bankAccountAddValidator()
        }
        function personalValidator() {
            $('#personalForm').bootstrapValidator({
                live: 'enable',
                submitButtons: '#savePersonal',
                feedbackIcons: {
                    valid: 'fa fa-check ',
                    // invalid: 'fa fa-times',
                    validating: 'fa fa-refresh'
                },
                fields: {
                    'nameFNew': {
                        trigger: 'blur keyup',
                        
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            }
                        }
                    },
                    'nameLNew': {
                        trigger: 'blur keyup',
                        
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            }
                        }
                    }
                }
            })
        }

        function maritalStatusValidator() {
            $('#maritalStatusForm').bootstrapValidator({
                live: 'enable',
                submitButtons: '#saveMarital',
                
                feedbackIcons: {
                    valid: 'fa fa-check ',
                    // invalid: 'fa fa-times',
                    validating: 'fa fa-refresh'
                },
                fields: {
                    'no': {
                        trigger: 'blur keyup',
                        
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            }
                        }
                    }
                }
            })
        }

        function driverLicenseValidator() {
            $('#driverLicenseForm').bootstrapValidator({
                live: 'enable',
                submitButtons: '#saveDriver',
                
                feedbackIcons: {
                    valid: 'fa fa-check ',
                    // invalid: 'fa fa-times',
                    validating: 'fa fa-refresh'
                },
                fields: {
                    'driversLicNbrNew': {
                        trigger: 'blur keyup',
                        validators: {
                            stringLength: {
                                max: 19,
                                message: 'validator.maxLength19'
                            },
                        }
                    }
                }
            })
        }

        function restrictionCodeFormValidator() {
            $('#restrictionCodeForm').bootstrapValidator({
                live: 'enable',
                submitButtons: '#saveRestrict',
                
                feedbackIcons: {
                    valid: 'fa fa-check ',
                    // invalid: 'fa fa-times',
                    validating: 'fa fa-refresh'
                },
                fields: {
                    'no': {
                        trigger: 'blur keyup',
                        
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            }
                        }
                    }
                }
            })
        }

        function emailFormValidator() {
            $('#emailForm').bootstrapValidator({
                live: 'enable',
                submitButtons: '#saveEmail',
                feedbackIcons: {
                    valid: 'fa fa-check ',
                    // invalid: 'fa fa-times',
                    validating: 'fa fa-refresh'
                },
                fields: {
                    'emailNew': {
                        trigger: 'blur keyup',
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            },
                            emailAddress: {
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    },
                    'hmEmailNew': {
                        trigger: 'blur keyup',
                        validators: {
                            emailAddress: {
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    }
                }
            })
        }

        function emergencyContactFormValidator() {
            $('#emergencyContactForm').bootstrapValidator({
                live: 'enable',
                submitButtons: '#saveEmergency',
                
                feedbackIcons: {
                    valid: 'fa fa-check ',
                    // invalid: 'fa fa-times',
                    validating: 'fa fa-refresh'
                },
                fields: {
                    'emerContactNew': {
                        trigger: 'blur keyup',
                        stringLength: {
                                max: 26,
                                message: 'validator.maxLength26'
                            },
                    },
                    'emerPhoneAcNew': {
                        trigger: 'blur keyup',
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            },
                            regexp: {
                                regexp: /^[0-9]\d{2}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    },
                    'emerPhoneNbrNew': {
                        trigger: 'blur keyup',
                        
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            },
                            regexp: {
                                regexp: /^[0-9][\d]{2}[\-]?[\d]{4}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    },
                    'emerPhoneExtNew': {
                        trigger: 'blur keyup',
                        validators: {
                            regexp: {
                                regexp: /^[\d]{0,4}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    },
                    'emerRelNew': {
                        trigger: 'blur keyup',
                        stringLength: {
                                max: 25,
                                message: 'validator.maxLength25'
                            },
                    },
                    'emerNoteNew': {
                        trigger: 'blur keyup',
                        stringLength: {
                                max: 25,
                                message: 'validator.maxLength25'
                            },
                    }
                }
            })
        }

        function mailingAddressValidator() {
            $('#mailingAddressForm').bootstrapValidator({
                live: 'enable',
                submitButtons: '#saveMailingAddress',
                
                feedbackIcons: {
                    valid: 'fa fa-check ',
                    // invalid: 'fa fa-times',
                    validating: 'fa fa-refresh'
                },
                fields: {
                    'addrNbrNew': {
                        trigger: 'blur keyup',
                        validators: {
                            regexp: {
                                regexp: /^[0-9]\d{0,7}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    },
                    'addrStrNew': {
                        trigger: 'blur keyup',
                        validators: {
                            stringLength: {
                                max: 20,
                                message: 'validator.maxLength20'
                            },
                        }
                    },
                    'addrAptNew': {
                        trigger: 'blur keyup',
                        validators: {
                            stringLength: {
                                max: 7,
                                message: 'validator.maxLength7'
                            },
                        }
                    },
                    'addrCityNew': {
                        trigger: 'blur keyup',
                        validators: {
                            stringLength: {
                                max: 20,
                                message: 'validator.maxLength20'
                            },
                        }
                    },
                    'addrZipNew': {
                        trigger: 'blur keyup',
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            },
                            regexp: {
                                regexp: /^[0-9]\d{4}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    },
                    'addrZip4New': {
                        trigger: 'blur keyup',
                        validators: {
                            regexp: {
                                regexp: /^[0-9][\d]{3}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    }
                }
            })
        }
        function alternativeAddressValidator() {
            $('#alternativeAddressForm').bootstrapValidator({
                live: 'enable',
                submitButtons: '#saveAltAddress',
                
                feedbackIcons: {
                    valid: 'fa fa-check ',
                    // invalid: 'fa fa-times',
                    validating: 'fa fa-refresh'
                },
                fields: {
                    'smrAddrNbrNew': {
                        trigger: 'blur keyup',
                        validators: {
                            regexp: {
                                regexp: /^[0-9]\d{0,7}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    },
                    'smrAddrStrNew': {
                        trigger: 'blur keyup',
                        validators: {
                            stringLength: {
                                max: 20,
                                message: 'validator.maxLength20'
                            },
                        }
                    },
                    'smrAddrAptNew': {
                        trigger: 'blur keyup',
                        validators: {
                            stringLength: {
                                max: 7,
                                message: 'validator.maxLength7'
                            },
                        }
                    },
                    'smrAddrCityNew': {
                        trigger: 'blur keyup',
                        validators: {
                            stringLength: {
                                max: 20,
                                message: 'validator.maxLength20'
                            },
                        }
                    },
                    'smrAddrZipNew': {
                        trigger: 'blur keyup',
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            },
                            regexp: {
                                regexp: /^[0-9]\d{4}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    },
                    'smrAddrZip4New': {
                        trigger: 'blur keyup',
                        validators: {
                            regexp: {
                                regexp: /^[0-9][\d]{3}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    }
                }
            })
        }

        function phoneValidator() {
            $('#phoneForm').bootstrapValidator({
                live: 'enable',
                submitButtons: '#savePhone',
                feedbackIcons: {
                    valid: 'fa fa-check ',
                    // invalid: 'fa fa-times',
                    validating: 'fa fa-refresh'
                },
                fields: {
                    'phoneAreaNew': {
                        trigger: 'blur keyup',
                        validators: {
                            regexp: {
                                regexp: /^[0-9]\d{2}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    },
                    'phoneNbrNew': {
                        trigger: 'blur keyup',
                        validators: {
                            regexp: {
                                regexp: /^[0-9][\d]{2}[\-]?[\d]{4}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    },
                    'phoneAreaCellNew': {
                        trigger: 'blur keyup',
                        validators: {
                            regexp: {
                                regexp: /^[0-9]\d{2}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    },
                    'phoneNbrCellNew': {
                        trigger: 'blur keyup',
                        validators: {
                            regexp: {
                                regexp: /^[0-9][\d]{2}[\-]?[\d]{4}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    },
                    'phoneAreaBusNew': {
                        trigger: 'blur keyup',
                        validators: {
                            regexp: {
                                regexp: /^[0-9]\d{2}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    },
                    'phoneNbrBusNew': {
                        trigger: 'blur keyup',
                        validators: {
                            regexp: {
                                regexp: /^[0-9][\d]{2}[\-]?[\d]{4}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    },
                    'busPhoneExtNew': {
                        trigger: 'blur keyup',
                        
                        validators: {
                            regexp: {
                                regexp: /^[\d]{0,4}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    }
                }
            })
        }

        function w4InfoValidator() {
            $('#w4InfoForm').bootstrapValidator({
                live: 'enable',
                submitButtons: '#saveW4',
                
                feedbackIcons: {
                    valid: 'fa fa-check ',
                    // invalid: 'fa fa-times',
                    validating: 'fa fa-refresh'
                },
                fields: {
                    'maritalStatTaxNew': {
                        trigger: 'blur keyup',
                        
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            }
                        }
                    },
                    'nbrTaxExemptsNew': {
                        trigger: 'blur keyup',
                        
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            },
                            regexp: {
                                regexp: /^[0-9]\d{0,1}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    }
                }
            })
        }

        function bankAccountValidator() {
            let arrayBankLength = $('.bankAccountBlock').length
            for(let i = 0;i<arrayBankLength;i++){
                $('#bankAccountForm_'+i).bootstrapValidator({
                    live: 'enable',
                    submitButtons: '.saveUpdateBankBtn',
                    feedbackIcons: {
                        valid: 'fa fa-check ',
                        // invalid: 'fa fa-times',
                        validating: 'fa fa-refresh'
                    },
                    fields: {
                        'description': {
                            trigger: 'blur keyup',
                            validators: {
                                notEmpty: {
                                    message: 'validator.requiredField'
                                }
                            }
                        },
                        'subCode': {
                            trigger: 'blur keyup',
                            validators: {
                                notEmpty: {
                                    message: 'validator.requiredField'
                                }
                            }
                        },
                        'accountNumber': {
                            trigger: 'blur keyup',
                            validators: {
                                notEmpty: {
                                    message: 'validator.requiredField'
                                },
                                regexp: {
                                    regexp: /^[0-9]\d{0,16}$/,
                                    message: 'validator.pleaseEnterCorrectFormat'
                                }
                            }
                        },
                        'displayLabel': {
                            trigger: 'blur keyup',
                            validators: {
                            }
                        },
                        'displayAmount': {
                            trigger: 'blur keyup',
                            validators: {
                                regexp: {
                                    regexp: /^\d+$|^\d+[\.]{1}\d{1,2}$/,
                                    message: 'validator.pleaseEnterCorrectFormat'
                                }
                            }
                        }
                    }
                })
        
            }
            
        }
        function bankAccountAddValidator() {
            $('#addBankAccountForm').bootstrapValidator({
                live: 'enable',
               submitButtons: '#saveNewBank',
                feedbackIcons: {
                    valid: 'fa fa-check ',
                    // invalid: 'fa fa-times',
                    validating: 'fa fa-refresh'
                },
                fields: {
                    'description': {
                        trigger: 'blur keyup',
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            }
                        }
                    },
                    'subCode': {
                        trigger: 'blur keyup',
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            }
                        }
                    },
                    'accountNumber': {
                        trigger: 'blur keyup',
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            },
                            regexp: {
                                regexp: /^[0-9]\d{0,16}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    },
                    'displayLabel': {
                        trigger: 'blur keyup',
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            }
                        }
                    },
                    'displayAmount': {
                        trigger: 'blur keyup',
                        validators: {
                            regexp: {
                                regexp: /^\d+$|^\d+[\.]{1}\d{1,2}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    }
                }
            })
        }
        
       function initSessionPws(){
    	   const psd = $("#sessionPsd").val();
    	   if(psd !== ""){
    		   sessionStorage.setItem("sessionPws",psd);
    	   }
       }
        
    </script>
</html>
