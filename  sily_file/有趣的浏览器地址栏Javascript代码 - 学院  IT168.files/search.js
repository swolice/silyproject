String.prototype.trim = function() { return this.replace(/^\s+|\s+$/g,''); }
var IEBrowser = (window.navigator.userAgent.toLowerCase().indexOf('msie') > -1);
function returnClick(event, rbool)
{
	if (IEBrowser) {
		window.event.returnValue = rbool;
	} else {
		if(!rbool && event.preventDefault) event.preventDefault(); //ff
	}
}
function SearchCheck1(form)
{
	//var form = document.form5;
	if (form.ckey.value.trim() == "") {
		alert("搜索关键字不能为空！");
		form.ckey.focus();
		returnClick(event, false);
	}
	else
	{
		form.action = "http://sou.it168.com/psearch.aspx?keyWord=" + form.ckey.value;
    	returnClick(event, true);
	}
 }
 
 function SearchCheck2(form)
{
	//var form = document.form5;
	if (form.ckey.value.trim() == "") {
		alert("搜索关键字不能为空！");
		form.ckey.focus();
		returnClick(event, false);
	}
	else
	{
		//switch(form.cflag_2.value.trim())
		var aa="1";
		switch(aa)
		{
			case "bc1": form.action = "http://sou.it168.com/psearch.aspx?keyWord=" + form.ckey.value;

				break;
			case "sy1": form.action = "http://sou.it168.com/pwsearch.aspx?keyWord=" + form.ckey.value;
				break;
			case "sms1": form.action = "http://sou.it168.com/esearch.aspx?keyWord=" + form.ckey.value;
				break;
			case "sd1": form.action = "http://sou.it168.com/sdsearch.aspx?keyWord=" + form.ckey.value;
				break;
			default: form.action="http://sou.it168.com/psearch.aspx?keyWord=" + form.ckey.value;
				break;
		}
    	returnClick(event, true);
	}
 }

function SearchCheck3(form)
{
//	alert("aa");
	//var form = document.form5;
	if (form.ckey.value.trim() == "") {
		alert("搜索关键字不能为空！");
		form.ckey.focus();
		returnClick(event, false);
	}
	else
	{
		switch(form.cflag_3.value.trim())
		{
			case "all": form.action = "http://sou.it168.com/asearch.aspx?category=0&keyWord=" + form.ckey.value;

				break;
			case "zx": form.action = "http://sou.it168.com/asearch.aspx?category=1&keyWord=" + form.ckey.value;
				break;
			case "pc": form.action = "http://sou.it168.com/evsearch.aspx?category=0&keyWord=" + form.ckey.value;
				break;
			case "hq": form.action = "http://sou.it168.com/asearch.aspx?category=3&keyWord=" + form.ckey.value;
				break;
			case "dg": form.action = "http://sou.it168.com/asearch.aspx?category=4&keyWord=" + form.ckey.value;
				break;
			case "pl": form.action = "http://sou.it168.com/asearch.aspx?category=5&keyWord=" + form.ckey.value;
				break;
			case "itss": 
						form.action = "http://www.itsousuo.com/search/search.aspx";
						form.txtkey.value=form.ckey.value.trim();	
				break;
				
				
				
			default: form.action="" ;
				break;
		}
    	returnClick(event, true);
	}
 }
 
 function SearchCheck4(form)
{
	//var form = document.form5;
	if (form.ckey.value.trim() == "") {
		alert("搜索关键字不能为空！");
		form.ckey.focus();
		returnClick(event, false);
	}
	else
	{
		form.action = "http://sou.it168.com/bbssearch.aspx?keyWord=" + form.ckey.value;
    	returnClick(event, true);
	}
 }
function SearchCheck5(form)
{
	//var form = document.form5;
	if (form.ckey.value.trim() == "") {
		alert("搜索关键字不能为空！");
		form.ckey.focus();
		returnClick(event, false);
	}
	else
	{
		form.action = "http://sou.it168.com/imgSearch.aspx?keyWord=" + form.ckey.value;
    	returnClick(event, true);
	}
 }
 function SearchCheck6(form)
{
	//var form = document.form5;
	if (form.ckey.value.trim() == "") {
		alert("搜索关键字不能为空！");
		form.ckey.focus();
		returnClick(event, false);
	}
	else
	{
		form.action = "http://sou.it168.com/qdsearch.aspx?keyWord=" + form.ckey.value;
    	returnClick(event, true);
	}
 }

function SearchCheck1_b(form)
{
	//var form = document.form5;
	if (form.ckey.value.trim() == "") {
		alert("搜索关键字不能为空！");
		form.ckey.focus();
		returnClick(event, false);
	}
	else
	{
		switch(form.cflag.value.trim())
		{
			case "shuyu": form.action = "http://detail.it168.com/common/shuyuxiangjie/files/searchshuyuxiangjie.asp?ckey=" + form.ckey.value;

				break;
			case "detail": form.action = "http://guide.it168.com/new/cShuomingshuList.asp?shuocSearchName=" + form.ckey.value;
				break;
			case "jingxiao": form.action = "http://sou.it168.com/dsearch.aspx?keyword=" + form.ckey.value;
				break;
			case "logtext": form.action = "http://weixiu.it168.com/files/searchtest.asp?keyword=" + form.ckey.value;
				break;
			case "zwss": 
				form.action = "http://www.itsousuo.com/search/search.aspx";
				form.txtkey.value=form.ckey.value.trim();
				break;
			case "software":
					form.action = "http://download.it168.com/down2.asp?cName=" + form.ckey.value;
				break;
			case "qudong":
					form.action = "http://driver.it168.com/Query.aspx?cFlag=driver&textfield=" + form.ckey.value;
			    break;
			default: form.action="http://search.it168.com/files/search.asp?ckey=" + form.ckey.value;
				break;
		}
		returnClick(event, true);
	}
 }
 function SearchCheck_final(form)
{
	//var form = document.form5;
	if (form.ckey.value.trim() == "") {
		alert("搜索关键字不能为空！");
		form.ckey.focus();
		returnClick(event, false);
	}
	else
	{
		switch(form.cflag.value.trim())
		{
			case "shuyu": form.action = "http://detail.it168.com/common/shuyuxiangjie/files/searchshuyuxiangjie.asp?ckey=" + form.ckey.value;
				break;
			case "detail": form.action = "http://guide.it168.com/new/cShuomingshuList.asp?shuocSearchName=" + form.ckey.value;
				break;
			case "jingxiao": form.action = "http://search.it168.com/files/cDealersearch.asp?ckey=" + form.ckey.value;
				break;
			case "logtext": form.action = "http://weixiu.it168.com/files/searchtest.asp?keyword=" + form.ckey.value;
				break;
			case "zwss": 
				form.action = "http://www.itsousuo.com/search/search.aspx";
				form.txtkey.value=form.ckey.value.trim();
				break;
			default: form.action="http://search.it168.com/files/search.asp?ckey=" + form.ckey.value;
				break;
		}
		returnClick(event, true);
	}
 }
function big5bg(big){
	var urlft;
	var urljt;
	var url1;
	var url2;
	var url3;
	url1="http";
	url2="://www.it168.com";
	url3="://big5.it168.com:82/gate/big5/www.it168.com/"; 
	urlft=url1+url2;
	urljt=url1+url3;
	if (big==1) { document.location.href=urlft; }
	if (big==2) { document.location.href=urljt; }
}
