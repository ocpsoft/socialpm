/** 
 * Copyright (c)2010 Lincoln Baxter, III <lincoln@ocpsoft.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */

function addMega() {
	$(this).addClass("hovering");
}

function removeMega() {
	$(this).removeClass("hovering");
}

com_ocpsoft_megaConfig = {
		interval: 100,
		sensitivity: 4,
		over: addMega,
		timeout: 100,
		out: removeMega
};

var initTemplate = function initTemplate(data) {
	$(".megaMenu").hoverIntent(com_ocpsoft_megaConfig);
}

$(document).ready(function() { $(".megaMenu").hoverIntent(com_ocpsoft_megaConfig) });
$(document).ready(function() {jsf.ajax.addOnEvent(initTemplate);});



/** Input Hovers 
 * 
 * (hovering the mouse over an input will cause that input to be focused after N seconds)
 * 
 * 

function setHover() {
    $(":input").blur();
    $(this).focus();
}

function removeHover() {
    //$(this).blur();
}

com_ocpsoft_inputHoverConfig = {
        interval: 10000,
        sensitivity: 4,
        over: setHover,
        timeout: 100,
        out: removeHover
};

var initHover = function initTemplate(data) {
    $(".megaMenu").hoverIntent(com_ocpsoft_inputHoverConfig);
}

$(document).ready(function() { $(":input").hoverIntent(com_ocpsoft_inputHoverConfig) });
$(document).ready(function() {jsf.ajax.addOnEvent(initHover);});

 **/