<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<label hidden="true" for="globalSet">${sessionScope.languageJSON.label.chooseLanguage}</label>
${sessionScope.language}
<select class="form-control" id="globalSet"  onchange="chgLang();" role="select">
    <option <c:if test="${sessionScope.language == sessionScope.languageJSON.language.english}">selected</c:if> value="en">${sessionScope.languageJSON.language.english}</option>
    <option <c:if test="${sessionScope.language == sessionScope.languageJSON.language.spanish}">selected</c:if> value="es">${sessionScope.languageJSON.language.spanish}</option>
</select>