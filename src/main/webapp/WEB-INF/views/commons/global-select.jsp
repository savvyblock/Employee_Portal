<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<select class="form-control" id="globalSet"  onchange="chgLang();" aria-label="${sessionScope.languageJSON.label.chooseLanguage}">
    <option value="en">${sessionScope.languageJSON.language.english}</option>
    <option value="es">${sessionScope.languageJSON.language.spanish}</option>
</select>