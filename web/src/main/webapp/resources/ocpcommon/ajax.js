/**
 * ***************************************
 * OcpSoft generic utilities
 */

if (!window["ocpsoft"]) {
	var ocpsoft = {};
}

ocpsoft.hide = function hide(id) {
	var element = document.getElementById(id);
	element.style.display = "none";
};

ocpsoft.displayInline = function displayInline(id) {
	var element = document.getElementById(id);
	element.style.display = "inline";
};

ocpsoft.displayBlock = function displayBlock(id) {
	var element = document.getElementById(id);
	element.style.display = "block";
};


/**
 * ***************************************
 * Edit Text
 */
if (!window["edittext"]) {
	var edittext = {};
}

edittext.toggle = function toggle(idOn, idOff) {
	try {
		ocpsoft.displayInline(idOn);
		ocpsoft.hide(idOff);
	} catch (ex) {
		alert(ex);
	}
};

edittext.submitButton = function submitButton(componentID, event) {
	try {
		var edit1 = componentID + ':edit1';
		var edit2 = componentID + ':edit2';
		edittext.toggle(edit1, edit2);
		return true;
	} catch (ex) {
		alert(ex);
	}
	return false;
};

edittext.cancelButton = function cancelButton(componentID) {
	try {
		var edit1 = componentID + ':edit1';
		var edit2 = componentID + ':edit2';
		var error = componentID + ':error';
		
		var link = document.getElementById(componentID + ':output');
		var input = document.getElementById(componentID + ':editInput');
		
		input.value = link.innerHTML;
		
		edittext.toggle(edit1, edit2);
		ocpsoft.hide(error);
	} catch (ex) {
		alert(ex);
	}
	return false;
};

edittext.linkClick = function linkClick(componentID) {
	try {
		var edit1 = componentID + ':edit1';
		var edit2 = componentID + ':edit2';
		var editInput = componentID + ':editInput';
		edittext.toggle(edit2, edit1);
		document.getElementById(editInput).focus();
		document.getElementById(editInput).select();
	} catch (ex) {
		alert(ex);
	}
	return false;
};



/**
 * ***************************************
 * Busy Status
 */
if (!window["busystatus"]) {
	var busystatus = {};
}

busystatus.onStatusChange = function onStatusChange(data) {
	var status = data.status;
	
	if (status === "begin") {
		if(busystatus['timeout'])
		{
			clearTimeout(busystatus['timeout']);
			busystatus['timeout'] = null;
		}
		document.body.style.cursor = 'wait';
	} else {
		busystatus['timeout'] = setTimeout('document.body.style.cursor="auto"',250);
	}
};

jsf.ajax.addOnEvent(busystatus.onStatusChange);
