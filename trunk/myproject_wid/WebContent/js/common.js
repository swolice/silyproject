<!--
/********************************************************
 * 函数名: showDialog
 * 功  能: 弹出模式对话框
 * 参  数: url,args,width,height
 * 返回值: returnValue
 * author: Wu Yanhong
 ********************************************************/
function showDialog(url,args,width,height) {
	var widths="";
	var heights="";
	var top="";
	var left="";
	if (width && width!=0) {
		widths=";dialogWidth:"+width+"px";
		//widths+=";dialogLeft:"+(screen.availWidth/2-width/2)+"px";
	}
	if (height && height!=0) {
		heights=";dialogHeight:"+height+"px";
		//heights+=";dialogLeft:"+(screen.availHeight/2-height/2)+"px";
	}
	var sFts="status:0;scroll:1"+widths+heights;
	return window.showModalDialog(url,args,sFts);
}

/********************************************************
 * 函数名: Clock
 * 功  能: 在指定ID域显示当前日期时间
 * 参  数: id, 指定的ID域
 * 返回值: Object
 * author: Wu Yanhong
 * 
 * 使用方法：new Clock(id).showTime();
 *
 ********************************************************/
function Clock(id) {
	var timefield = document.getElementById(id);
	var year, month, date, hour, minute, second, day;
	var week = new Array();
	week[0] = "星期日";
	week[1] = "星期一";
	week[2] = "星期二";
	week[3] = "星期三";
	week[4] = "星期四";
	week[5] = "星期五";
	week[6] = "星期六";
	return {
		refreshTime: function() {
			var now = new Date();
			year = now.getFullYear();
			month = now.getMonth() + 1;
			date = now.getDate();
			hour = now.getHours();
			if (hour<10)
			{
				hour = "0" + hour;
			}
			minute = now.getMinutes();
			if (minute<10)
			{
				minute = "0" + minute;
			}
			second = now.getSeconds();
			if (second<10)
			{
				second = "0" + second;
			}
			day = week[now.getDay()];
			
			timefield.innerText = year+"年"+month+"月"+date+"日"+" "+day+" "+hour+":"+minute+":"+second;
		},
		showTime: function() {
			setInterval(this.refreshTime, 1000);
		}
	};
}

/********************************************************
 * 函数名: OpenWindow
 * 功  能: 弹出window窗口
 *
 ********************************************************/
function OpenWindow()
{
	this.url = null;
	this.height="300";
	this.width="400";
}
OpenWindow.prototype.getUrl = function()
{
	return this.url;
}
OpenWindow.prototype.setUrl = function(url)
{
	this.url = url;
}
OpenWindow.prototype.getHeight = function()
{
	return this.height;
}
OpenWindow.prototype.setHeight = function(height)
{
	this.height = height;
}
OpenWindow.prototype.getWidth = function()
{
	return this.width;
}
OpenWindow.prototype.setWidth = function(width)
{
	this.width = width;
}
OpenWindow.prototype.Show = function()
{
	this.left = (window.screen.width-this.width)/2;
	this.top = (window.screen.height-this.height)/2;
	return window.open(
		this.url,
		'',
		'height=' + this.height +
		',width=' + this.width + 
		',left=' + this.left + 
		',top=' + this.top +
		',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no'
	)
}

//-->