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
            <main class="content-wrapper">
                <section class="content">
                    <div class="content-white no-title profile">
                        <div class="profile-item first-child">
                            <div class="profile-title" >
                                <form id="changeAvatar" action="changeAvatar" method="POST">
                                        <div
                                        class="avatar"
                                        id="imgContentImg"
                                        style="background-image:url(${sessionScope.userDetail.avatar})"
                                    >
                                        <input id="userName" hidden="hidden" type="text" value="${sessionScope.userDetail.nameF}">
                                        <input id="avatarImg" hidden="hidden" type="text" name="file">
                                        <input id="avatarImgName" hidden="hidden" type="text" name="fileName">
                                        <input class="avatar-file" type="file" name="file"  id="imgUpFile"  onchange="startRead()"  accept="image/*"/>
                                        <label class="avatar-word" data-localize="profile.change"></label>
                                    </div>
                                    <button type="button" class="btn btn-primary sm" data-toggle="modal" data-target="#changePasswordModal" data-localize="label.changePassword" data-localize-location="title">
                                            <span data-localize="label.changePassword"></span>
                                        </button>
                                    </form>
                                
                            </div>
                            <form class="profile-desc" id="personalForm" action="saveName" method="POST">
                                <div class="profile-content">
                                    <div class="profile-desc-item form-line">
                                        <label class="desc-title" data-localize="profile.title"></label>
                                        <div class="desc-content">
                                            <span class="haveValue"
                                                >${sessionScope.userDetail.namePre}</span
                                            >
                                            <input hidden="hidden" type="text" name="empNbr" value="${nameRequest.id.empNbr}">
                                            <input hidden="hidden" type="text" name="reqDts" value="${nameRequest.id.reqDts}">
                                            <div class="form-group valueInput">
                                                <select
                                                    class="form-control"
                                                    title=""
                                                    data-localize="profile.title"
                                                    name="namePreNew"
                                                    id="titleString"
                                                    value="${nameRequest.namePreNew}"
                                                    autofocus
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
                                            class="btn btn-secondary cancel-btn"  data-localize="label.undo"
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
                                        <input hidden="hidden" type="text" name="empNbr" value="${mrtlRequest.id.empNbr}">
                                            <input hidden="hidden" type="text" name="reqDts" value="${mrtlRequest.id.reqDts}">
                                        <div class="form-group valueInput">
                                            <select
                                                id="maritalStatus"
                                                name="maritalStatNew"
                                                class="form-control"
                                                title=""
                                                data-localize="profile.local"
                                                autofocus
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
                                            class="btn btn-secondary cancel-btn"  data-localize="label.undo"
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
                         	<input hidden="hidden" type="text" name="empNbr" value="${licRequest.id.empNbr}">
                            <input hidden="hidden" type="text" name="reqDts" value="${licRequest.id.reqDts}">
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
                                                autofocus
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
                                            class="btn btn-secondary cancel-btn"  data-localize="label.undo"
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
                        	<input hidden="hidden" type="text" name="empNbr" value="${restrictRequest.id.empNbr}">
                            <input hidden="hidden" type="text" name="reqDts" value="${restrictRequest.id.reqDts}">
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
                                                autofocus
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
                                                style="width:188px"
                                                title=""
                                                data-localize="profile.public"
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
                                            class="btn btn-secondary cancel-btn"  data-localize="label.undo"
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
                                <input hidden="hidden" type="text" name="empNbr" value="${emailRequest.id.empNbr}">
                                <input hidden="hidden" type="text" name="reqDts" value="${emailRequest.id.reqDts}">
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
                                                autofocus
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
                                            class="btn btn-secondary cancel-btn"  data-localize="label.undo"
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
                                <input hidden="hidden" type="text" name="empNbr" value="${emerRequest.id.empNbr}">
                                <input hidden="hidden" type="text" name="reqDts" value="${emerRequest.id.reqDts}">
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
                                                autofocus
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
                                            class="btn btn-secondary cancel-btn"  data-localize="label.undo"
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
                                <input hidden="hidden" type="text" name="empNbr" value="${mailAddrRequest.id.empNbr}">
                                <input hidden="hidden" type="text" name="reqDts" value="${mailAddrRequest.id.reqDts}">
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
                                                autofocus
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
                                            class="btn btn-secondary cancel-btn"  data-localize="label.undo"
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
                                <input hidden="hidden" type="text" name="empNbr" value="${altMailAddrRequest.id.empNbr}">
                                <input hidden="hidden" type="text" name="reqDts" value="${altMailAddrRequest.id.reqDts}">
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
                                                autofocus
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
                                            class="btn btn-secondary cancel-btn"  data-localize="label.undo"
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
                                <input hidden="hidden" type="text" name="empNbr" value="${hmRequest.id.empNbr}">
                                <input hidden="hidden" type="text" name="reqDts" value="${hmRequest.id.reqDts}">
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
                                                    autofocus
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
                                            class="btn btn-secondary cancel-btn"  data-localize="label.undo"
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
                        <form class="profile-item" id="w4InfoForm">
                            <div class="profile-left">
                                <div class="profile-item-line form-line">
                                    <div class="profile-title" data-localize="profile.MaritalStatus">
                                       
                                    </div>
                                    <div class="profile-desc">
                                        <span class="haveValue"
                                            >${payInfo.maritalStatus.displayLabel}</span
                                        >
                                        <div class="form-group valueInput">
                                            <select
                                                id="maritalStatusLabel"
                                                name="payInfo.maritalStatus.displayLabel"
                                                class="form-control"
                                                title=""
                                                data-localize="profile.MaritalStatus"
                                                autofocus
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
                                            >${payInfo.numberOfExemptions}</span
                                        >
                                        <div class="form-group valueInput">
                                            <input
                                                class="form-control"
                                                id="numberOfExemptions"
                                                name="payInfo.numberOfExemptions"
                                                title=""  data-localize="profile.NbrOfExemptions"
                                                value="${payInfo.numberOfExemptions}"
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
                                        id="saveW4Info"
                                    >
                                     
                                    </button>
                                    <button
                                            type="button"
                                            id=""
                                            class="btn btn-secondary cancel-btn"  data-localize="label.undo"
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
                        <p class="sub-title" data-localize="profile.directDepositBankAccounts"></p>
                        <form
                            class="profile-item border-0 bankAccountBlock"
                            id="bankAccountForm_01"
                        >
                            <div class="profile-left">
                                <div class="profile-item-line form-line">
                                    <div class="profile-title" data-localize="profile.primary"></div>
                                    <div class="profile-desc">
                                        <span class="haveValue">
                                            <label for="primaryAccount">
                                                <input
                                                    class="icheck"
                                                    id="primary_0"
                                                    type="checkbox"
                                                    name="primaryAccount"
                                                    disabled
                                                />
                                            </label>
                                        </span>
                                        <div class="form-group valueInput">
                                            <label for="primaryAccount">
                                                <input
                                                    class="icheck"
                                                    id="primary_1"
                                                    type="checkbox"
                                                    name="primaryAccount"
                                                />
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="profile-item-line form-line">
                                    <div class="profile-title" data-localize="profile.bankName"></div>
                                    <div class="profile-desc">
                                        <span class="haveValue"
                                            >${accountInfo[0].code.description}</span
                                        >
                                        <div class="valueInput group-line">
                                            <div class="form-group">
                                                <input
                                                    class="form-control name"
                                                    type="text"
                                                    name="accountInfo[0].code.description"
                                                    value="${accountInfo[0].code.description}"
                                                    disabled
                                                />
                                            </div>

                                            <div class="form-group">
                                                <input
                                                    class="form-control code"
                                                    type="text"
                                                    name="accountInfo[0].code.subCode"
                                                    value="${accountInfo[0].code.subCode}"
                                                    disabled
                                                />
                                            </div>

                                            <button
                                                class="btn btn-secondary xs getBank"
                                                type="button"
                                                title=""
                                                data-localize="profile.chooseBank"
                                                data-localize-location="title"
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
                                        <span class="haveValue"
                                            >${accountInfo[0].accountNumber}</span
                                        >
                                        <div class="form-group valueInput">
                                            <input
                                                id="bankAccountNumber_0"
                                                class="form-control"
                                                type="text"
                                                title=""
                                                data-localize="profile.bankAcctNbr"
                                                name="accountInfo[0].accountNumber"
                                                value="${accountInfo[0].accountNumber}"
                                            />
                                        </div>
                                    </div>
                                </div>
                                <div class="profile-item-line form-line">
                                    <div class="profile-title" data-localize="profile.bankAcctType">
                                    </div>
                                    <div class="profile-desc">
                                        <span class="haveValue"
                                            >${accountInfo[0].accountType.displayLabel}</span
                                        >
                                        <div class="form-group valueInput">
                                            <select
                                                class="form-control"
                                                id="bankAccountType_1"
                                                title=""
                                                data-localize="profile.bankAcctType"
                                                name="accountInfo[0].accountType.displayLabel"
                                            >
                                                <option value=""></option>
                                                <option
                                                    value="2 - Checking account"
                                                    selected="selected"
                                                    >2 - Checking
                                                    account</option
                                                >
                                                <option
                                                    value="3 - Savings account"
                                                    >3 - Savings account</option
                                                >
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="profile-item-line form-line">
                                    <div class="profile-title" data-localize="profile.bankAcctAmt">
                                    </div>
                                    <div class="profile-desc">
                                        <span class="haveValue"
                                            >${accountInfo[0].depositAmount.displayAmount}</span
                                        >
                                        <div class="form-group valueInput">
                                            <input
                                                class="form-control amount_2"
                                                id="bankDepositAmount_1"
                                                type="text"
                                                title="" data-localize="profile.bankAcctAmt"
                                                name="accountInfo[0].depositAmount.displayAmount"
                                                value="${accountInfo[0].depositAmount.displayAmount}"
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
                                    <button
                                        type="button"
                                        class="btn btn-secondary delete-btn" data-localize="label.delete"
                                    >
                                    </button>
                                </div>
                                <div class="saveOrCancel">
                                    <button
                                        type="submit"
                                        class="btn btn-primary save-btn" data-localize="label.update"
                                        id="saveBank_01"
                                    >
                                     
                                    </button>
                                    <button
                                            type="button"
                                            id=""
                                            class="btn btn-secondary cancel-btn"  data-localize="label.undo"
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

                        <div>
                            <form
                                action=""
                                class="profile-item border-0 activeEdit addBankForm"
                                id="addBankAccountForm"
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
                                                        name="accountInfo[0].code.description"
                                                        value=""
                                                        disabled
                                                    />
                                                </div>

                                                <div class="form-group">
                                                    <input
                                                        class="form-control code"
                                                        type="text"
                                                        name="accountInfo[0].code.subCode"
                                                        value=""
                                                        disabled
                                                    />
                                                </div>

                                                <button
                                                    class="btn btn-secondary xs getBank"
                                                    type="button"
                                                    title=""
                                                    data-localize="profile.chooseBank"
                                                    data-localize-location="title"
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
                                                    id="bankAccountNumber_1"
                                                    class="form-control"
                                                    type="text"
                                                    title="" data-localize="profile.bankAcctNbr"
                                                    name="accountInfo[0].accountNumber"
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
                                                    id="bankAccountType_0"
                                                    title="" data-localize="profile.bankAcctType"
                                                    name="accountInfo[0].accountType.displayLabel"
                                                >
                                                    <option value=""></option>
                                                    <option
                                                        value="2 - Checking account"
                                                        >2 - Checking
                                                        account</option
                                                    >
                                                    <option
                                                        value="3 - Savings account"
                                                        >3 - Savings
                                                        account</option
                                                    >
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
                                                    id="bankDepositAmount_0"
                                                    type="text"
                                                    title=""
                                                    name="accountInfo[0].depositAmount.displayAmount"
                                                    value=""
                                                />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="profile-btn">
                                    <div class="saveOrCancel flex-line">
                                        <button
                                            type="submit"
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
                                data-localize-location="title"
                                data-localize="profile.addANewBankAccount"
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
                            data-localize="label.closeModal"
                            data-localize-location="title"
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
                            <div class="form-group">
                                <label class="form-title" for="SearchStartDate"
                                    ><span data-localize="profile.routingNumber"></span>:</label
                                >
                                <div class="button-group">
                                    <input
                                        id="codeCriteria.searchCode"
                                        name="codeCriteria.searchCode"
                                        class="form-control"
                                        type="text"
                                        value=""
                                    />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="form-title" for="SearchStartDate"
                                    ><span data-localize="profile.bankName"></span>:</label
                                >
                                <div class="button-group">
                                    <input
                                        id="codeCriteria.searchDescription"
                                        name="codeCriteria.searchDescription"
                                        class="form-control"
                                        type="text"
                                        value=""
                                    />
                                </div>
                            </div>
                            <div class="form-group btn-group">
                                <div style="margin-top:20px;">
                                    <button
                                        type="submit"
                                        class="btn btn-primary"
                                        data-localize="label.search"
                                    ></button>
                                </div>
                            </div>
                        </form>
                        <div class="bankResult">
                            <table class="table border-table">
                                <thead>
                                    <tr>
                                        <th data-localize="profile.routingNumber"></th>
                                        <th data-localize="profile.bankName"></th>
                                    </tr>
                                </thead>
                                <tbody>
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
                            id="cancelAdd"
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
            bank01 = $('#bankAccountForm_01').length
            if (bank01 != 0) {
                bankAccount01Validator()
            }
            bank02 = $('#bankAccountForm_02').length
            if (bank02 != 0) {
                bankAccount02Validator()
            }
            bankAccountAddValidator()

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
                let arrayBankLength = $('.bankAccountBlock').length
                $('.profile-item').removeClass('activeEdit')
                $('.addBankForm').addClass('activeEdit')
                if (arrayBankLength >= 2) {
                    $('.bankSizeError').show()
                } else {
                    $('.addBankForm').show()
                    $(this).hide()
                }
            })
            $('.getBank').click(function() {
                $('#selectBankModal').modal('show')
                bankInputName = $(this)
                    .parent()
                    .find('.form-control.name')
                bankInputCode = $(this)
                    .parent()
                    .find('.form-control.code')
            })
            $('.bankNumberBtn').click(function() {
                let number = $(this).val()
                let name = $(this).attr('data-title')
                console.log(number)
                console.log(name)
                bankInputName.val(name)
                bankInputCode.val(number)
                $('#selectBankModal').modal('hide')
            })
        
            $("#undoNameRequest").click(function(){
                $("#deleteNameRequest")[0].submit()
            })
            $("#undoMaritalRequest").click(function(){
                $("#deleteMaritalRequest")[0].submit()
            })
            $("#undoDriverLicense").click(function(){
                $("#deleteDriversLicense")[0].submit()
            })
            $("#undoRestriction").click(function(){
                $("#deleteRestrictionCodesRequest")[0].submit()
            })
            $("#undoEmail").click(function(){
                $("#deleteEmail")[0].submit()
            })
            $("#undoEmergencyContact").click(function(){
                $("#deleteEmergencyContact")[0].submit()
            })
            $("#undoMailingAddress").click(function(){
                $("#deleteMailAddr")[0].submit()
            })
            $("#undoAlternative").click(function(){
                $("#deleteAltMailAddr")[0].submit()
            })
            $("#undoPhoneNumber").click(function(){
                $("#")[0].submit()
            })
        })
        function startRead() {
            var fileDom = document.getElementById('imgUpFile')
            var img = document.getElementById('imgContentImg')
            if (fileDom && img) {
                fileHandle(fileDom, img)
            }
        }
        function fileHandle(fileDom, img) {
            //read
            var file = fileDom.files[0]
            var fileName = file.name
            var type = file.type.split("/")
            var username = $("#userName").val()
            var name = (new Date()).valueOf() + username  + "." + type[1];
            console.log(name)
            $("#avatarImgName").val(name)
            var reader = new FileReader()
            reader.readAsDataURL(file)
            reader.onloadstart = function() {
                console.log('do upload ......')
            }
            //done
            reader.onload = function(e) {
                //file
                $("#avatarImg").val(reader.result)
                $("#changeAvatar")[0].submit()
                img.style.backgroundImage = "url('" + reader.result + " ') "
            }
            reader.onerror = function(){
                /* error handler **/
            }
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
                        trigger: 'input',
                        
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            }
                        }
                    },
                    'nameLNew': {
                        trigger: 'input',
                        
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
                        trigger: 'input',
                        
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
                        trigger: 'input',
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
                        trigger: 'input',
                        
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
                        trigger: 'input',
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
                        trigger: 'input',
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
                        trigger: 'input',
                        stringLength: {
                                max: 26,
                                message: 'validator.maxLength26'
                            },
                    },
                    'emerPhoneAcNew': {
                        trigger: 'input',
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
                        trigger: 'input',
                        
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
                        trigger: 'input',
                        validators: {
                            regexp: {
                                regexp: /^[\d]{0,4}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    },
                    'emerRelNew': {
                        trigger: 'input',
                        stringLength: {
                                max: 25,
                                message: 'validator.maxLength25'
                            },
                    },
                    'emerNoteNew': {
                        trigger: 'input',
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
                        trigger: 'input',
                        validators: {
                            regexp: {
                                regexp: /^[0-9]\d{0,7}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    },
                    'addrStrNew': {
                        trigger: 'input',
                        validators: {
                            stringLength: {
                                max: 20,
                                message: 'validator.maxLength20'
                            },
                        }
                    },
                    'addrAptNew': {
                        trigger: 'input',
                        validators: {
                            stringLength: {
                                max: 7,
                                message: 'validator.maxLength7'
                            },
                        }
                    },
                    'addrCityNew': {
                        trigger: 'input',
                        validators: {
                            stringLength: {
                                max: 20,
                                message: 'validator.maxLength20'
                            },
                        }
                    },
                    'addrZipNew': {
                        trigger: 'input',
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
                        trigger: 'input',
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
                        trigger: 'input',
                        validators: {
                            regexp: {
                                regexp: /^[0-9]\d{0,7}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    },
                    'smrAddrStrNew': {
                        trigger: 'input',
                        validators: {
                            stringLength: {
                                max: 20,
                                message: 'validator.maxLength20'
                            },
                        }
                    },
                    'smrAddrAptNew': {
                        trigger: 'input',
                        validators: {
                            stringLength: {
                                max: 7,
                                message: 'validator.maxLength7'
                            },
                        }
                    },
                    'smrAddrCityNew': {
                        trigger: 'input',
                        validators: {
                            stringLength: {
                                max: 20,
                                message: 'validator.maxLength20'
                            },
                        }
                    },
                    'smrAddrZipNew': {
                        trigger: 'input',
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
                        trigger: 'input',
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
                        trigger: 'input',
                        validators: {
                            regexp: {
                                regexp: /^[0-9]\d{2}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    },
                    'phoneNbrNew': {
                        trigger: 'input',
                        validators: {
                            regexp: {
                                regexp: /^[0-9][\d]{2}[\-]?[\d]{4}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    },
                    'phoneAreaCellNew': {
                        trigger: 'input',
                        validators: {
                            regexp: {
                                regexp: /^[0-9]\d{2}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    },
                    'phoneNbrCellNew': {
                        trigger: 'input',
                        validators: {
                            regexp: {
                                regexp: /^[0-9][\d]{2}[\-]?[\d]{4}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    },
                    'phoneAreaBusNew': {
                        trigger: 'input',
                        validators: {
                            regexp: {
                                regexp: /^[0-9]\d{2}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    },
                    'phoneNbrBusNew': {
                        trigger: 'input',
                        validators: {
                            regexp: {
                                regexp: /^[0-9][\d]{2}[\-]?[\d]{4}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    },
                    'busPhoneExtNew': {
                        trigger: 'input',
                        
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
                submitButtons: '#saveW4Info',
                
                feedbackIcons: {
                    valid: 'fa fa-check ',
                    // invalid: 'fa fa-times',
                    validating: 'fa fa-refresh'
                },
                fields: {
                    'payInfo.maritalStatus.displayLabel': {
                        trigger: 'input',
                        
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            }
                        }
                    },
                    'payInfo.numberOfExemptions': {
                        trigger: 'input',
                        
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

        function bankAccount01Validator() {
            $('#bankAccountForm_01').bootstrapValidator({
                live: 'enable',
                submitButtons: '#saveBank_01',
                
                feedbackIcons: {
                    valid: 'fa fa-check ',
                    // invalid: 'fa fa-times',
                    validating: 'fa fa-refresh'
                },
                fields: {
                    'accountInfo[0].code.description': {
                        trigger: 'input',
                        
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            }
                        }
                    },
                    'accountInfo[0].code.subCode': {
                        trigger: 'input',
                        
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            }
                        }
                    },
                    'accountInfo[0].accountNumber': {
                        trigger: 'input',
                        
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
                    'accountInfo[0].accountType.displayLabel': {
                        trigger: 'input',
                        
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            }
                        }
                    },
                    'accountInfo[0].depositAmount.displayAmount': {
                        trigger: 'input',
                        
                        validators: {
                            regexp: {
                                regexp: /^\d+$|^\d+[\.]{1}\d{2}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    }
                }
            })
        }
        function bankAccount02Validator() {
            $('#bankAccountForm_02').bootstrapValidator({
                live: 'enable',
                submitButtons: '#saveBank_02',
                
                feedbackIcons: {
                    valid: 'fa fa-check ',
                    // invalid: 'fa fa-times',
                    validating: 'fa fa-refresh'
                },
                fields: {
                    'accountInfo[1].code.description': {
                        trigger: 'input',
                        
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            }
                        }
                    },
                    'accountInfo[1].code.subCode': {
                        trigger: 'input',
                        
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            }
                        }
                    },
                    'accountInfo[1].accountNumber': {
                        trigger: 'input',
                        
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
                    'accountInfo[1].accountType.displayLabel': {
                        trigger: 'input',
                        
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            }
                        }
                    },
                    'accountInfo[1].depositAmount.displayAmount': {
                        trigger: 'input',
                        
                        validators: {
                            regexp: {
                                regexp: /^\d+$|^\d+[\.]{1}\d{2}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    }
                }
            })
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
                    'accountInfo[0].code.description': {
                        trigger: 'input',
                        
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            }
                        }
                    },
                    'accountInfo[0].code.subCode': {
                        trigger: 'input',
                        
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            }
                        }
                    },
                    'accountInfo[0].accountNumber': {
                        trigger: 'input',
                        
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
                    'accountInfo[0].accountType.displayLabel': {
                        trigger: 'input',
                        
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            }
                        }
                    },
                    'accountInfo[0].depositAmount.displayAmount': {
                        trigger: 'input',
                        
                        validators: {
                            regexp: {
                                regexp: /^\d+$|^\d+[\.]{1}\d{2}$/,
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    }
                }
            })
        }
    </script>
</html>
