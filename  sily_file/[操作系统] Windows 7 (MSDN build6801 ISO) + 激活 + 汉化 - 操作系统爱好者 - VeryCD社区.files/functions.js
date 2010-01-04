function trim(str)
{
   return str.replace(/^\s*|\s*$/g,"").replace(/\&\#32;/g,' ');
}
function isNumeric(number)
{
   return /^[0-9]+$/.test(number);
}
function setCookie(name, value, hours) {
  var expire = "";
  if(hours != null)  {
    expire = new Date((new Date()).getTime() + hours * 3600000);
    expire = "; expires=" + expire.toGMTString();
  }
  document.cookie = name + "=" + escape(value) + expire + ';domain=.verycd.com;path=/;';
}


function readCookie(name) {
  var cookieValue = "";
  var search = name + "=";
  if(document.cookie.length > 0) { 
    offset = document.cookie.indexOf(search);
    if (offset != -1) { 
      offset += search.length;
      end = document.cookie.indexOf(";", offset);
      if (end == -1) end = document.cookie.length;
      cookieValue = unescape(document.cookie.substring(offset, end))
    }
  }
  return cookieValue;
}

function getRadioValue(obj_list)
{
	for (var i=0; i<obj_list.length; i++) {
		if (typeof obj_list[i] != 'object') {
			continue;
		}
		if (obj_list[i].checked == true) {
			return obj_list[i].value;
		}
	}
	return false;
}

function submitWithKeyboard(event,submit_obj) {
	if (event.altKey && 83 == event.keyCode) {
		submit_obj.click();
	}
	if(event.ctrlKey && event.keyCode==13)	{
		submit_obj.click();
	}
}
submitByKeyboard=submitWithKeyboard;
function clearForm(form)
{
	for (i=0;i<form.length ;i++ ) {
		try {
			e=form[i];
			if (e.name!='') {
			
				if (e.type=='select-one') {
					e.selectedIndex = 0;
				} else if (e.type=='checkbox' || e.type=='radio') {
					e.checked = false
				} else {
					e.value = '';
				}
			}
		} catch (e) {}
		
		
	}
}
function disableSelect(form_obj)
{
	try {
		for (i=0;i<form_obj.length ;i++ )
		{
			e=form_obj[i];
			if (e.name!='')
			{
				if (e.type=='select-one')
				{
					e.style.visibility = "hidden";
				}
			}
			
		}	
	} catch (e) {
	}
	
}
function enableSelect(form_obj)
{
	try {
		for (i=0;i<form_obj.length ;i++ )
		{
			e=form_obj[i];
			if (e.name!='')
			{
				if (e.type=='select-one')
				{
					e.style.visibility = "visible";
				}
			}
			
		}
	} catch (e) {
	}
	
}


function formToRequestString(form_obj)
{
	var query_string='';
	var and='';
	for (i=0;i<form_obj.length ;i++ )
	{
		e=form_obj[i];
		
		
		if (e.name) {
			if (e.type=='select-one') {
				element_value=e.options[e.selectedIndex].value;
			} else if (e.type=='checkbox' || e.type=='radio') {
				if (e.checked==false)
				{
					continue;	
				}
				element_value=e.value;
			} else if (typeof e.value != 'undefined') {
				element_value=e.value;
			} else {
				continue;
			}
			query_string+=and+e.name+'='+element_value.replace(/\&/g,"%26");
			and="&"
		}
		
	}
	return query_string;
}




function getNumOfCheckedBox(cb_obj)
{
	var n = 0;
	if (typeof cb_obj == 'string') {
		try {
			cb_obj = document.getElementById(cb_obj);
		} catch (e) {
			return 0;
		}		
	}
	for (var i=0; i<cb_obj.length; i++) {
		try {
			if (cb_obj[i].checked == true) {
				n++;
			}
		} catch (e) {
		}
		
	}
	return n;
}




function setMaxCanBeChecked(n,cb_obj,e,d)
{
	if (typeof n == 'object') {
		var tmp_n = n;
		n      = cb_obj;
		cb_obj = tmp_n;
	}
	for (var i=0; i<cb_obj.length; i++) {
		if (typeof cb_obj[i] != 'object') {
			continue;
		}
		abcdiojfdieopqjncjguo = cb_obj;
		cb_obj[i].onclick = function(){ 
			if (getNumOfCheckedBox(abcdiojfdieopqjncjguo)>n) {
				try {
					e(this);
				} catch (et) {}
			} else {
				try {
					d(this);
				} catch (et) {}
			}
		}
	}
}

function setSelected(s_obj,v)
{
	if (typeof s_obj != 'object') {
		return;
	}
	
	for (var i=0; i<s_obj.options.length; i++) {
		if (v==s_obj.options[i].value) {
			s_obj.selectedIndex = i;
			return true;
		}
	}
}
function getScrollTop()
{
	if (document.documentElement.scrollTop!=0) { 
		return document.documentElement.scrollTop;
	}
	return document.body.scrollTop;
}
viewImage = function(url,hidden_objs) {
    if (navigator.userAgent.toLowerCase().indexOf('msie')!=-1) {
    } else {
        //return true;
    }
	var saver = new ScreenSaver();
	saver.style2 = {
		'vals' : new Array('position','zIndex','background','filter','opacity','MozOpacity'),
		'vars' : new Array("absolute","3","#666","alpha(opacity=70)",70/100,70/100)
	}
    if (typeof hidden_objs != 'undefined') {
        saver.hiddenObjects=hidden_objs;
    }
    saver.build();
	var div_obj = $("view_image_div");
	if (!div_obj) {
		var div_obj = document.createElement("div");
		document.body.appendChild(div_obj);
	}
	document.onkeydown = function(e){
		if (VeryCD.client.agent.ie) {
			key_code = event.keyCode;
		} else {
			key_code = e.keyCode;
		}
		if (key_code==27) {
			return false;
		}		
	}
	div_obj.onclick = function(){
		div_obj.innerHTML = '';
		saver.destroy();
	}
	saver.obj.onclick = function(){
		div_obj.innerHTML = '';
		saver.destroy();
	}
	div_obj.id = 'view_image_div';
	div_obj.style.position = 'absolute';
	if (navigator.userAgent.toLowerCase().indexOf("msie")==-1) {
		saver.obj.style.cursor  = 'pointer';
		div_obj.style.cursor = 'pointer';
	} else {
		div_obj.style.left   = '0px';	
		saver.obj.style.cursor  = 'hand';
		div_obj.style.cursor = 'hand';
	}
	div_obj.style.width  = '100%';
	div_obj.style.top = getScrollTop()+'px';
	div_obj.style.zIndex = '6';
	div_obj.innerHTML = '<span style="margin-left:auto;margin-right:auto;display:block;height:32px" id="image_loading"><img src="http://statics.verycd.com/images/ajax-loader.gif" id="base_image_viewer" />&nbsp;&nbsp;图片加载中...</span><span style="margin-left:auto;margin-right:auto"><img src="'+url+'" onload="document.getElementById(\'image_loading\').innerHTML=\'\'"  /></span>';
    return false;
}
viewFileImage = function(url) {
    var objs=new Array();
    if (typeof flash_ids!='undefined') {
        for (var i=0;i<flash_ids.length;i++) {
            objs[objs.length]=$('player_'+flash_ids[i]);
        }
    }
    viewImage(url,objs);
}
