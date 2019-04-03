<label hidden="true" for="globalSet">${sessionScope.languageJSON.label.chooseLanguage}</label>
<select class="form-control" id="globalSet"  onchange="chgLang();" role="select">
    <option value="en">${sessionScope.languageJSON.language.english}</option>
    <option value="es">${sessionScope.languageJSON.language.spanish}</option>
</select>