function Set( string )                // return character
{
    var character = string;
	document.getElementById("uploadForm:f_url").value=string;
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
	
	var fileName = link.text;
	var folderPath = link.rel + '/';
	var encodedFileName = encodeURIComponent(fileName);
	
	link.parentNode.parentNode.className='selected';
	document.getElementById("uploadForm:f_url").value=folderPath+encodedFileName;
}

function Init() {
  //__dlg_init();
  //setWindowSizeCentered(550,800);
  var param = window.dialogArguments;
  if (param) {
      document.getElementById("uploadForm:f_url").value = param["f_url"];
      document.getElementById("uploadForm:f_alt").value = param["f_alt"];
      document.getElementById("uploadForm:f_border").value = param["f_border"];
      document.getElementById("uploadForm:f_align").value = param["f_align"];
      document.getElementById("uploadForm:f_vert").value = param["f_vert"];
      document.getElementById("uploadForm:f_horiz").value = param["f_horiz"];
      window.ipreview.location.replace(param.f_url);
  }
  document.getElementById("uploadForm:f_url").focus();
};

function onOK() {
  var required = {
    "uploadForm:f_url": "You must enter the URL"
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
  var fields = ["f_url", "f_alt", "f_align", "f_border",
                "f_horiz", "f_vert"];
  var param = new Object();
  param["f_url"] = document.getElementById("uploadForm:f_url").value;
  param["f_alt"] = document.getElementById("uploadForm:f_alt").value;
  param["f_align"] = document.getElementById("f_align").value;
  param["f_border"] = document.getElementById("uploadForm:f_border").value;
  param["f_horiz"] = document.getElementById("uploadForm:f_horiz").value;
  param["f_vert"] = document.getElementById("uploadForm:f_vert").value;
  __dlg_close(param);
  return false;
};

function onCancel() {
  __dlg_close(null);
  return false;
};

function onPreview() {
  var f_url = document.getElementById("uploadForm:f_url");
  var url = f_url.value;
  if (!url) {
    alert("You have to enter an URL first");
    f_url.focus();
    return false;
  }
  window.ipreview.location.replace(url);
  return false;
};
