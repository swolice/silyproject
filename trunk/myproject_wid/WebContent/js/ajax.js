/**
	调用的例子----->

	 //调用AJAX方法.得到返回值得重写AJAX的WRITE方法.
	 function downloadTest(){
	 	
	 	 var path=document.getElementById("contextPath").value;
	 	
	 	 //(URL地址,要使用的方法,要传入的值(各值之间要用;号隔开))
	 	 new ajax.Request(path+"/interfaceSearch.crm","ftpIVNPDownloadTest","type=1"); 
	 	
	 	
	 	 //重写方法,从而接到返回的参数打页面
	 	 ajax.write = function (responseValue){
	 	
	 	 	document.getElementById("ftpState").innerHTML= responseValue;
  		
  		 }
	 }

*/


//AJAX类
function ajax(){
}

//得到URL
ajax.getPrement = function(url,method,prement){
	//如果VALUE字符串是空
	if(prement == null || prement == "" ){
		//如果调用的方法是空就直接返回这个URL
		if(method == null || method == "" ){
			return url;
		}
		//如果调用的方法不是空就直接把这个方法加到URL后台返回
		return url += "?method=" + method;
	}else{
		//如果VALUE字符串不是空,就可以截串
		var str = prement.split(";");
		//用来判断VALUE前是否有方法.如果有就用&号连接,如果没有就用?号连接
		var flag = 0;
		//如果方法不是空,就把方法加URL里,标志位不为0
		if(method != null || method != "" ){
		   url += "?method=" + method;
		   flag = 1;
		}
		//无论方法是什么,VALUE值都会加到后面
		return ajax.getPrementUrl(str,url,flag);
	}
	return url;
}
//判断VALUE字符串
ajax.getPrementUrl = function (str,url,flag){
	for ( i = 0 ; i < str.length ; i++ ){
		if ( flag == 0 ) {
		 	url += "?" + str[i];
		}else {
			url += "&" + str[i];
		}
	}	
	return url;
}


//执行方法
ajax.Request = function(url,method,prement,flag){
 	url=ajax.getPrement(url,method,prement);
 	var xmlhttp = new ajax.createXmlHttp(); 		
	if(flag == "1"){
		xmlhttp.open("post",url,false);
		xmlhttp.send();
		ajax.write(xmlhttp.responseText);
	}else{
		xmlhttp.open("post",url,true);
		xmlhttp.onreadystatechange=function(){
	         if ( xmlhttp.readyState == 4 ) {    
	             if( xmlhttp.status == 200 ) {  
	               ajax.write(xmlhttp.responseText);
	             }
	          } 
	    }
	    xmlhttp.send();	
    }       
}

//返回方法,须用户重写
ajax.write = function (responseValue){
}


//判断浏览器,创建XmlHttpRequest对象
ajax.createXmlHttp = function (){
  	 	
  	 	//非IE浏览器创建XmlHttpRequest对象
    	if(window.XmlHttpRequest) {
    		return new XmlHttpRequest();
    	}
    	
    	//IE浏览器创建XmlHttpRequest对象
    	 if(window.ActiveXObject){
	   		 try{
	     		return new ActiveXObject("Microsoft.XMLHTTP"); 
	    	} catch(e){
	    		try{
	     			return new ActiveXObject("msxml2.XMLHTTP");
	     		} catch(ex){}
	    	}
    	}
    	return null;
}


 