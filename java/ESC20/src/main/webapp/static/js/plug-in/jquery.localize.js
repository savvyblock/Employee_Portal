
/*
Copyright (c) Jim Garvin (http://github.com/coderifous), 2008.
Dual licensed under the GPL (http://dev.jquery.com/browser/trunk/jquery/GPL-LICENSE.txt) and MIT (http://dev.jquery.com/browser/trunk/jquery/MIT-LICENSE.txt) licenses.
Written by Jim Garvin (@coderifous) for use on LMGTFY.com.
http://github.com/coderifous/jquery-localize
Based off of Keith Wood's Localisation jQuery plugin.
http://keith-wood.name/localisation.html
 */
(function($) {
  var normaliseLang;
  normaliseLang = function(lang) {
    lang = lang.replace(/_/, '-').toLowerCase();
    if (lang.length > 3) {
      lang = lang.substring(0, 3) + lang.substring(3).toUpperCase();
    }
    return lang;
  };
  $.defaultLanguage = normaliseLang(navigator.languages && navigator.languages.length > 0 ? navigator.languages[0] : navigator.language || navigator.userLanguage);
  $.localize = function(pkg, options) {
    var defaultCallback, deferred, fileExtension, intermediateLangData, jsonCall, lang, loadLanguage, localizeElement,localizeTextElement,localizeAttrElement, localizeForSpecialKeys, localizeImageElement, localizeInputElement, localizeOptgroupElement, notifyDelegateLanguageLoaded, regexify, setAttrFromValueForKey, setTextFromValueForKey, valueForKey, wrappedSet;
    if (options == null) {
      options = {};
    }
    wrappedSet = this;
    intermediateLangData = {};
    fileExtension = options.fileExtension || "json";
    deferred = $.Deferred();
    loadLanguage = function(pkg, lang, level) {
      var file;
      if (level == null) {
        level = 1;
      }
      switch (level) {
        case 1:
          intermediateLangData = {};
          if (options.loadBase) {
            file = pkg + ("." + fileExtension);
            return jsonCall(file, pkg, lang, level);
          } else {
            return loadLanguage(pkg, lang, 2);
          }
          break;
        case 2:
          file = "" + pkg + "-" + (lang.split('-')[0]) + "." + fileExtension;
          return jsonCall(file, pkg, lang, level);
        case 3:
          file = "" + pkg + "-" + (lang.split('-').slice(0, 2).join('-')) + "." + fileExtension;
          return jsonCall(file, pkg, lang, level);
        default:
          return deferred.resolve();
      }
    };
    jsonCall = function(file, pkg, lang, level) {
      var ajaxOptions, errorFunc, successFunc;
      if (options.pathPrefix != null) {
        file = "" + options.pathPrefix + "/" + file;
      }
      successFunc = function(d) {
        $.extend(intermediateLangData, d);
        notifyDelegateLanguageLoaded(intermediateLangData);
        return loadLanguage(pkg, lang, level + 1);
      };
      errorFunc = function() {
        if (level === 2 && lang.indexOf('-') > -1) {
          return loadLanguage(pkg, lang, level + 1);
        } else if (options.fallback && options.fallback !== lang) {
          return loadLanguage(pkg, options.fallback);
        }
      };

      ajaxOptions = {
        url: file,
        type: "GET",
        dataType: "json",
        async: true,
        timeout: options.timeout != null ? options.timeout : 5000,
        success: successFunc,
        error: errorFunc
      };
      if (window.location.protocol === "file:") {
        ajaxOptions.error = function(xhr) {
          return successFunc($.parseJSON(xhr.responseText));
        };
      }
      return $.ajax(ajaxOptions);
    };
    notifyDelegateLanguageLoaded = function(data) {
      if (options.callback != null) {
        return options.callback(data, defaultCallback);
      } else {
        return defaultCallback(data);
      }
    };
    defaultCallback = function(data) {
      $.localize.data[pkg] = data;
      return wrappedSet.each(function() {
        var elem, key, value;
        elem = $(this);
        key = elem.data("localize");
        key || (key = elem.attr("rel").match(/localize\[(.*?)\]/)[1]);
        value = valueForKey(key, data);
        if (value != null) {
          return localizeElement(elem, key, value);
        }
      });
    };
    /****
    // elem  ----HTML tag
    // key---- value of data-localize
    // value--- words in JSON file
     ****/

    localizeElement = function(elem, key, value) {
      let locationAttr = elem.attr("data-localize-location")
      let locationNotText = elem.attr("data-localize-notText")
      let locationArry = []
      if(locationAttr && locationAttr!=''){
        locationArry = elem.attr("data-localize-location").split(',');
      }
      if (elem.is('input')) {
        localizeInputElement(elem, locationArry, value);
      } else if (elem.is('select')) {
        localizeInputElement(elem, locationArry, value);
      }else if (elem.is('textarea')) {
        localizeInputElement(elem, locationArry, value);
      } else if (elem.is('img')) {
        localizeImageElement(elem, locationArry, value);
      }else if(elem.is('td')&&!elem.hasClass("td-title")){
        localizeTdElement(elem, locationArry, value)
      }else if (!$.isPlainObject(value)) {
        localizeTextElement(elem,locationArry,locationNotText,value)
      }
      if ($.isPlainObject(value)) {
        return localizeForSpecialKeys(elem, value);
      }
    };
    localizeTextElement = function(elem,locationArry,locationNotText,value){
      if(locationNotText == 'true'){
        localizeAttrElement(elem, locationArry, value);
      }else{
        localizeAttrElement(elem,locationArry,value)
        elem.html(value);
      }
    };

    localizeInputElement = function(elem, locationArry, value) {
      localizeAttrElement(elem,locationArry,value)
      // return elem.val(val);
    };

    localizeImageElement = function(elem, locationArry, value) {
      localizeAttrElement(elem,locationArry,value)
    };
    
    localizeTdElement = function(elem, locationArry, value) {
      localizeAttrElement(elem,locationArry,value)
    };

    localizeAttrElement = function(elem,locationArry,value){
      let length = locationArry.length;
      for(let i=0;i<length;i++){
        if(locationArry[i] == 'text'){
          elem.val(value);
          elem.html(value);
        }else{
          elem.attr(locationArry[i], value);
        }
      }
      if(elem.is("[placeholder]")){
        elem.attr("placeholder", value);
      }
      if(elem.is("[aria-label]")){
        elem.attr("aria-label", value);
      }
      if(elem.is("[title]")){
        elem.attr("title", value);
      }
      if(elem.is("[data-title]")){
        elem.attr("title", value);
      }
      if(elem.is("[scope]")){
        elem.attr("scope", value);
      }
      if(elem.is("[alt]")){
        elem.attr("alt", value);
      }
    }

    
    localizeForSpecialKeys = function(elem, value) {
      setAttrFromValueForKey(elem, "title", value);
      setAttrFromValueForKey(elem, "href", value);
      return setTextFromValueForKey(elem, "text", value);
    };
    localizeOptgroupElement = function(elem, key, value) {
      return elem.attr("label", value);
    };
    
    
    
    valueForKey = function(key, data) {
      var keys, value, _i, _len;
      keys = key.split(/\./);
      value = data;
      for (_i = 0, _len = keys.length; _i < _len; _i++) {
        key = keys[_i];
        value = value != null ? value[key] : null;
      }
      return value;
    };
    setAttrFromValueForKey = function(elem, key, value) {
      // value = valueForKey(key, value);
      if (value != null) {
        elem.attr(key, value)
        return elem.attr(key, value);
      }
    };
    setTextFromValueForKey = function(elem, key, value) {
      value = valueForKey(key, value);
      if (value != null) {
        return elem.text(value);
      }
    };
    regexify = function(string_or_regex_or_array) {
      var thing;
      if (typeof string_or_regex_or_array === "string") {
        return "^" + string_or_regex_or_array + "$";
      } else if (string_or_regex_or_array.length != null) {
        return ((function() {
          var _i, _len, _results;
          _results = [];
          for (_i = 0, _len = string_or_regex_or_array.length; _i < _len; _i++) {
            thing = string_or_regex_or_array[_i];
            _results.push(regexify(thing));
          }
          return _results;
        })()).join("|");
      } else {
        return string_or_regex_or_array;
      }
    };
    lang = normaliseLang(options.language ? options.language : $.defaultLanguage);
    if (options.skipLanguage && lang.match(regexify(options.skipLanguage))) {
      deferred.resolve();
    } else {
      loadLanguage(pkg, lang, 1);
    }
    wrappedSet.localizePromise = deferred;
    return wrappedSet;
  };
  $.fn.localize = $.localize;
  return $.localize.data = {};
})(jQuery);
