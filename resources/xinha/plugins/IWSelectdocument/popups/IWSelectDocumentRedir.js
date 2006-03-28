function Set( string )                // return character
{
    var character = string;
	document.getElementById("uploadForm:f_href").value=string;
}

var previousSelected;
var previousSelectedClassName;

function SelectDocument( link )
{
	if(previousSelected){
		previousSelected.parentNode.parentNode.className=previousSelectedClassName;
	}
	previousSelected=link;
	previousSelectedClassName=link.parentNode.parentNode.className;
	href = link.href;
	link.parentNode.parentNode.className='selected';
	//link.className='selected';
	document.getElementById("uploadForm:f_href").value=href;
}

// Taken from link.html
//I18N = window.opener.HTMLArea.I18N.dialogs;

function i18n(str) {
  return (I18N[str] || str);
};

function onTargetChanged() {
  var f = document.getElementById("f_other_target");
  if (this.value == "_other") {
    f.style.visibility = "visible";
    f.select();
    f.focus();
  } else f.style.visibility = "hidden";
};

function Init() {
 // __dlg_translate(I18N);
  //__dlg_init();
  var param = window.dialogArguments;
  var target_select = document.getElementById("f_target");
  var use_target = true;
  if (param) {
    if ( typeof param["f_usetarget"] != "undefined" ) {
      use_target = param["f_usetarget"];
    }
    if ( typeof param["f_href"] != "undefined" ) {
		if (param["f_href"] != "") {
		alert('param["f_href"].indexOf("mailto") = '+param["f_href"].indexOf("mailto"));
			if (param["f_href"].indexOf("mailto") == 0) {
				 window.location="../workspace/linkchooser/edit?PARAMETER_HREF=gimmi";
			}
      		document.getElementById("uploadForm:f_href").value = param["f_href"];
      	}
		if (param["f_title"] != "") {
      		document.getElementById("uploadForm:f_title").value = param["f_title"];
      	}
		if (param["f_target"] != "") {
      		comboSelectValue(target_select, param["f_target"]);
      	}
//      if (target_select.value != param.f_target) {
//        var opt = document.createElement("option");
//        opt.value = param.f_target;
//        opt.innerHTML = opt.value;
//        target_select.appendChild(opt);
//        opt.selected = true;
//      }
    }
  }
//  if (! use_target) {
//    document.getElementById("uploadForm:f_target_label").style.visibility = "hidden";
//    document.getElementById("uploadForm:f_target").style.visibility = "hidden";
//    document.getElementById("uploadForm:f_target_other").style.visibility = "hidden";
//  }
//  var opt = document.createElement("option");
//  opt.value = "_other";
//  opt.innerHTML = i18n("Other");
//  target_select.appendChild(opt);
//  target_select.onchange = onTargetChanged;
//  document.getElementById("uploadForm:f_href").focus();
//  document.getElementById("uploadForm:f_href").select();
};

function onOK() {
  var required = {
    // f_href shouldn't be required or otherwise removing the link by entering an empty
    // url isn't possible anymore.
    // "f_href": i18n("You must enter the URL where this link points to")
  };
  for (var i in required) {
    var el = document.getElementById(i);
    if (!el.value) {
      alert(required[i]);
      el.focus();
      return false;
    }
  }
  // pass data back to the calling window
//  var fields = ["uploadForm:f_href", "uploadForm:f_title", "f_target" ];
  var param = new Object();
//  for (var i in fields) {
//    var id = fields[i];
//    var el = document.getElementById(id);
//    param[id] = el.value;
//  }
  
    var el = document.getElementById("uploadForm:f_href");
	param["f_href"] = el.value;
    var el = document.getElementById("uploadForm:f_title");
    param["f_title"] = el.value;
    var el = document.getElementById("f_target");
    param["f_target"] = el.value;
  
  
  if (param.f_target == "_other")
    param.f_target = document.getElementById("uploadForm:f_other_target").value;
  __dlg_close(param);
  return false;
};

function onCancel() {
  __dlg_close(null);
  return false;
};
