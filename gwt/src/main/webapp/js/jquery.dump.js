/**
 * jQuery dump plugin. Inspect object properties in a popup window.
 *
 * Copyright (c) 2012 Block Alexander
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 */
(function ($) {
  var __jqdump__dump_OFF = false;         // global OFF switch
  var position = { left: 100, top: 120 }; // popup window position
  var defaultDepth = 2;                   // default dump depth

  /**
   * Dump object content in popup window with prettyprint and subnavigation
   *
   * <example>
   * // dump navigator properties
   * $.dump(window.navigator, 1, "window.navigator");
   *
   * // this may be used to disable all following function calls
   * $.dump("off");
   * </example>
   *
   * @param(obj): object to dump, or "off" string to disable all dump calls
   * @param(iDepth): number, optional dumping depth, default 2
   * @param(sHistoryPath): string, optional global properties path relative to the initial dump object value, used for display only.
   * @returns: null
   */
  $.dump = function (obj, iDepth, sHistoryPath) {
    if (typeof(obj) == "string" && /^off$/i.test(obj)) { __jqdump__dump_OFF = true; }
    return __jqdump__dump(obj, iDepth, sHistoryPath);
  };

  /**
   * Dump object content in popup window with prettyprint and subnavigation
   *
   * <example>
   * $("#element").dump(1);
   * // same as
   * $.dump($("#element"), 1, "(#element)");
   * </example>
   *
   * Call type: window.dump/$.dump( ... )
   * @param(iDepth): number, optional dumping depth, default 2
   * @returns: null
   */
  $.fn.dump = function (iDepth) {
    return __jqdump__dump(this, iDepth, this.selector? "("+ this.selector +")" : "");
  };

  /**
   * Dump object content in popup window with prettyprint and subnavigation
   *
   * @param(obj): object to dump
   * @param(iDepth): number, optional dumping depth, default 2
   * @param(sHistoryPath): string, optional global properties path relative to the initial dump object value, used for display only.
   * @returns: null
   */
  function __jqdump__dump (obj, iDepth, sHistoryPath) {
    if (__jqdump__dump_OFF) { return null; }

    // store current object value to allow continious/recursive dump browsing via window.opener.__jqdump__
    __jqdump__ = {data: obj, dump: __jqdump__dump };

    // provide defaults as needed
    iDepth = (typeof(iDepth) == "number" && iDepth > 0? parseInt(iDepth, 10) : defaultDepth);
    sHistoryPath = (typeof(sHistoryPath) == "string" && sHistoryPath.length > 0? sHistoryPath : "OBJECT");

    // adjust new window position
    position = { top: (position.top - 30) % 120, left: (position.left - 10) % 100 };

    // try to popup blank page
    var dumpWindow = window.open("about:blank", "_blank"
      , "width=600,height=800,menubar=0,left="+ position.left +",top="+ position.top
        +",location=0,toolbar=0,status=0,scrollbars=1,resizable=1", true);

    // popup blocked?
    if (!dumpWindow || dumpWindow.closed == true) {
      if (confirm("Dump Window couldn't showup cause of popup blocker.\nContinue using current window?")) {
        dumpWindow = window;
      } else {
        return null;
      }
    }

    // fill the page with dump content
    dumpWindow.document.write("<html><head><title>"+ sHistoryPath +" @ "+ DateToHMSMString(new Date()) +"</title>"
      +"<meta http-equiv='Content-Type' content='text/html;charset=utf-8'/>"
      +"<style type='text/css'>"
      +" body{background-color:#fff;color:#000;font-size:12px;margin-top:24px;}"
      +" #toolbar{position:fixed;top:0px;right:0px;z-index:9999;}"
      +" span.p.a:hover,span.p.a:hover+span {background-color: #B5F5FF;}"
      +" a{text-decoration:none;}"
      +" a:hover{text-decoration:underline;}"
      +" .h{display:none;}"                     // hidden element
      +" .a{cursor:pointer;}"                   // link like element
      +" .s{color:#740;}"                       // string
      +" .k{color:#427;font-weight:bold;font-style:italic;}"      // key-word
      +" .c{color:#666;font-style:italic;}"     // comment
      +" .u{color:#259;}"                       // reserved value
      +" .p{color:#155;font-weight:bold;}"      // punctuation
      +" .d{color:#800;font-weight:bold;}"      // diggit
      +" .e{color:#900;font-style:italic;background-color:#FAA;}" // exception
      +" .t{color:#080;font-weight:bold;}"      // true boolean value
      +" .f{color:#800;font-weight:bold;}"      // false boolean value
      +" .arg{color:#000;font-weight:normal;}"  // number of function arguments
      +"</style></head><body>"
      +"<div id='toolbar'>"
      +" <input type='button' id='btnClose' value='Close' onclick='window.close();'/>"
      +" <input type='button' value='Collapse All' onclick='ToggleCollapse(true);'/>"
      +" <input type='button' value='Expand All' onclick='ToggleCollapse(false);'/>"
      +"</div>"
      +"<code><pre>"+ sHistoryPath +" <span class='p'>=</span> "
      + __jqdump__next(obj, "  ", iDepth, "__jqdump__.data", sHistoryPath)
      +"<span class='p'>;</span></pre></code>"
      // provide data and code to the parent window
      +"<sc"+"ript type='text/javascript'><!-"+"-"
      +"\n __jqdump__ = (window.opener? window.opener.__jqdump__ : window.__jqdump__);"
      +"\n if (!__jqdump__) { __jqdump__ = {data: null, dump: function (o) { if (JSON) { alert(JSON.stringify(o)); } } }; }"
      +"\n "
      // focus the close button to allow fast window close by pressing button [space]
      +"\n window.onload = function () {"
      +"\n   window.focus();"
      +"\n   document.getElementById('btnClose').focus();"
      +"\n }"
      +"\n "
      +"\n function ToggleCollapse (bCollapse) {"
      +"\n   var span = document.getElementsByTagName('span');"
      +"\n   for (var i = 0; i < span.length; i++) {"
      +"\n     if (span[i].getAttribute('title') != 'collapse/expand' || (i == 1 && bCollapse)) { continue; }"
      +"\n     span[i].nextSibling.className = bCollapse? 'h' : '';"
      +"\n   }"
      +"\n }"
      +"\n-"+"-></sc"+"ript></body></html>"
      );

    // finalize writings
    dumpWindow.document.close();

    // ensure new window has data and code to continue further dumps
    dumpWindow.__jqdump__ = {
      data: __jqdump__.data
      , dump: __jqdump__.dump
      //*dbg*/, history: sHistoryPath
    };

    /**
     * @param(obj): object to dump
     * @param(sIndent): string, used for object properties alignment indentation
     * @param(iDepth): number, dumping depth, defaults to 2
     * @param(sContextPath): string, evaluable object properties path relative to the current obj value, used onclick event
     * @param(sHistoryPath): string, global properties path relative to the initial dump obj value, used for display only
    */
    function __jqdump__next (obj, sIndent, iDepth, sContextPath, sHistoryPath) {
      var objType = typeof(obj);
      if (null == obj && objType != "undefined") { return "<span class='u'>null</span>"; }

      switch (objType) {
        case "object": break;
        case "undefined": return "<span class='u'>undefined</span>";
        case "string": return obj.length? "\"<span class='s'>"+ htmlEscape(obj) +"</span>\"" : "\"\"";
        case "number": return "<span class='d'>"+ obj.toString() +"</span>";
        case "boolean": return "<span class='"+ (obj? "t" : "f") +"'>"+ obj.toString() +"</span>";
        // allow dumping of function return value (simple function call without arguments)
        case "function": return format(
          "<a href='javascript:;' class='k'"
          +" onclick='__jqdump__.dump((function(){try{return {0}();}catch(xcp){return {EXCEPTION_WRAPPER:xcp.toString()};}})()"
          +","+ defaultDepth +",this.title);' title='{1}()'>func"+"tion({2})</a>"
          , sContextPath
          , sHistoryPath
          , (obj.length? "<span class='arg'>"+ obj.length +"</span>"  : "")
          );
        default: return "<span class='e'>/* Unknown object type: {"+ objType +"}*/</span>";
      }

      if (obj instanceof Date) { return "new Date(\""+ obj.toUTCString() +"\")"; }

      if (iDepth == 0) { // stop here and allow deeper dumping by a click
        return format("<a href='javascript:;' class='p' title='show more'"
          +" onclick='__jqdump__.dump((function(){try{return {0};}catch(xcp){return {EXCEPTION_WRAPPER:xcp.toString()};}})(),"
          + defaultDepth +",\"{1}\");'"
          +">{ ... }</a>"
          , sContextPath
          , sHistoryPath.replace(/"/g, "\\\""));
      }

      var bIsArray = (obj instanceof Array)
        , sNewContextPath, sNewHistoryPath
        , obja = [], prop;

      var rv = [
        "<span class='p a' title='collapse/expand'"
        +" onclick='this.nextSibling.className=(this.nextSibling.className==\"h\"?\"\":\"h\")'"
        +">&#160;"+ (bIsArray? "[" : "{") +"&#160;</span><span>\n"
      ];

      try {
        // making sorted array of object property names
        if (bIsArray) {
          for (var i = 0; i < obj.length; i++) { obja.push(i); }
        } else {
          for (prop in obj) { obja.push(prop); }
          obja.sort(function (a, b) {
            return (isNaN(a)? (a.toLowerCase() >= b.toLowerCase()? 1 : -1) : (Number(a) >= Number(b))? 1 : -1);
          });
        }

        // quering object with names via sorted array
        for (var c = 0, length = obja.length; c < length; c++) {
          try {
            prop = obja[c];
            // skip self properties
            if (/__jqdump__/.test(prop)) { continue; }

            if (bIsArray) { // array index:
              sNewContextPath = format("{0}[\"{1}\"]", sContextPath, prop);
              sNewHistoryPath = format("{0}[\"{1}\"]", sHistoryPath, prop);
              rv.push(format(
                "{0}<span class='a c' onclick='alert(this.title);' title='{1}'>/*{2}*/</span> "
                , sIndent, sNewHistoryPath, prop
                ));
            } else {// object property:
              if (/(\W)|(^\d)/.test(prop)) {//- as string
                sNewContextPath = format("{0}[\"{1}\"]", sContextPath, prop);
                sNewHistoryPath = format("{0}[\"{1}\"]", sHistoryPath, htmlEscape(prop));
                rv.push(format(
                  "{0}<span class='s a' onclick='alert(this.title);' title='{1}'>\"{2}\"</span> <span class='p'>:</span> "
                  , sIndent, sNewHistoryPath, htmlEscape(prop)
                  ));
              } else {//- as conventional variable name
                sNewContextPath = format("{0}.{1}", sContextPath, prop);
                sNewHistoryPath = format("{0}.{1}", sHistoryPath, htmlEscape(prop));
                rv.push(format(
                  "{0}<span class='a' onclick='alert(this.title);' title='{1}'>{2}</span> <span class='p'>:</span> "
                  , sIndent, sNewHistoryPath, htmlEscape(prop)
                  ));
              }
            }

            rv.push(__jqdump__next(obj[prop], sIndent +"  ", iDepth - 1, sNewContextPath, sNewHistoryPath));
          } catch (xcp) {
            rv.push(format("<span class='e'>/*{0} - {1}*/</span>", xcp.name, xcp.message));
          }

          rv.push((c < (obja.length-1)? "<span class='p'>,</span>\n" : "\n"));
        }
      } catch (xcp) {
        rv.push(format("<span class='e'>/*{0} - {1}*/</span>", xcp.name, xcp.message));
      }

      rv.push(format("{0}</span><span class='p'>{1}</span>", sIndent.replace("  ", ""), (bIsArray? "]" : "}")));

      return rv.join("");
    };

    /**
     * Escape native string characters before writing it as html text
     */
    function htmlEscape (str) {
      // trying to speedup the process by checking the length
      return str.length? str.replace(/&/g, "&amp;").replace(/\</g, "&lt;").replace(/\>/g, "&gt;") : str;
    };

    /**
     * Replaces each format item "{n}" in `this` string with the string equivalent of a corresponding
     * parameters object's value. For example "{0}" reffers to the second argument, "{1}" to the third.
     *
     * @note: format identifier "{i}" may be repeated multiple times in any order as long as it reffers
     * to corresponding position of the argument
     * @param(0): first parameter is a format-string, containing meta information of insertion positions
     * @param(...): any type, other arguments reffered by the format-string will be evaluated to string
     * @return: string, formating result
     */
    function format (/* ... */) {
      var match = null, rv = arguments[0];

      for (var c = 1, length = arguments.length; c < length; c++) {
        match = new RegExp("\\{"+ (c - 1) +"\\}", "g");
        if (match.test(rv)) {
          rv = rv.replace(match, typeof(arguments[c]) == "undefined" ? "(undefined)" : arguments[c].toString());
        }
      }

      match = null;
      return rv;
    };

    /**
     * Time to string in HH:MM:SS.mmm format
     * @param(d): date object
     */
    function DateToHMSMString (d) {
      var iHours = d.getHours(), iMinutes = d.getMinutes(), iSeconds = d.getSeconds(), iMSeconds = d.getMilliseconds();

      return (iHours < 10? "0" : "") + iHours +":"+ (iMinutes < 10? "0" : "") + iMinutes
        +":"+ (iSeconds < 10? "0" : "") + iSeconds
        +"."+ (iMSeconds < 100? "0" : "") + (iMSeconds < 10? "0" : "") + iMSeconds;
    }

    return null;
  };

})(jQuery);
