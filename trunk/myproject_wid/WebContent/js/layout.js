<!--
/************************控制伸缩面板 请保留该信息 谢谢! ***************************   
 *jimzou
 * Creation date: 2007-11-10 
**/
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
function MM_reloadPage(init) { //reloads the window if Nav4 resized 
if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) { 
document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }} 
else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload(); 
} 
MM_reloadPage(true); 

/********************************************************
/* 函数名: shrink_onClick
/* 功  能: 收缩上侧工具栏
/* 参  数: 无
/* 返回值: 无
/********************************************************/
function shrink_onClick() { //v3.0
	//alert(parent.document.getElementsByTagName("FRAMESET")[1].rows);
	document.getElementById("sidebarShrink").style.display="none";
	document.getElementById("sidebarExpand").style.display="block";
	parent.document.getElementsByTagName("FRAMESET")[1].rows="20,*";
	parent.document.getElementById("ordertop").noResize=true;
}
/********************************************************
/* 函数名: expand_onClick
/* 功  能: 扩展上侧工具栏
/* 参  数: 无
/* 返回值: 无
/********************************************************/
function expand_onClick() { //v3.0
	document.getElementById("sidebarShrink").style.display="block";
	document.getElementById("sidebarExpand").style.display="none";
	parent.document.getElementsByTagName("FRAMESET")[1].rows="280,*";
	parent.document.getElementById("ordertop").noResize=false;
}
/********************************************************
/* 函数名: MM_showHideLayers
/* 功  能: 显示隐藏Menu
/* 参  数: 无
/* 返回值: 无
/********************************************************/
function MM_showHideLayers() { //v9.0
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3) 
  with (document) if (getElementById && ((obj=getElementById(args[i]))!=null)) { v=args[i+2];
    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v=='hide')?'hidden':v; }
    obj.visibility=v; }
	alert(obj.style.visibility);
	if(obj.style.visibility=="visible"){obj.style.visibility=="hidden"}
}
function fnDropDown(obj,Lay){
    var tagName = document.getElementById(Lay);
	if(typeof tagName == 'undefined') {
		return;
	}
    var leftSide = findPosX(obj);
    var topSide = findPosY(obj);
    var maxW = tagName.style.width;
    var widthM = maxW.substring(0,maxW.length-2);
    var getVal = eval(leftSide) + eval(widthM);
    if(getVal  > document.body.clientWidth ){
        leftSide = eval(leftSide) - eval(widthM);
        tagName.style.left = leftSide + 34 + 'px';
    }
    else
        tagName.style.left= leftSide + 'px';
    tagName.style.top= topSide + 20 +'px';
    tagName.style.display = 'block';
 }

function findPosX(obj) {
	var curleft = 0;
	if (document.getElementById || document.all) {
		while (obj.offsetParent) {
			curleft += obj.offsetLeft
			obj = obj.offsetParent;
		}
	} else if (document.layers) {
		curleft += obj.x;
	}
	return curleft;
}

function findPosY(obj) {
	var curtop = 0;
	if (document.getElementById || document.all) {
		while (obj.offsetParent) {
			curtop += obj.offsetTop
			obj = obj.offsetParent;
		}
	} else if (document.layers) {
		curtop += obj.y;
	}
	return curtop;
}
function fnShowDrop(obj){
	document.getElementById(obj).style.display='block';
}

function fnHideDrop(obj){
	document.getElementById(obj).style.display='none';
}