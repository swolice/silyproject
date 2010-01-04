/**
* @desc 给当前页面增加一块蒙板
*
* @author surfchen@gmail.com
*
* @example
*        var screen_obj = new ScreenSaver();
*        screen_obj.build();//建立
*	     screen_obj.destroy();//销毁
*
* $Id: screen.js 4817 2007-04-04 07:18:03Z kafeifei $
*/
function ScreenSaver()
{
	this.style1 = {
		'vals' : new Array('display','top','left','margin','padding'),
		'vars' : new Array('block','0px','0px','0px','0px')
	}

	this.style2 = {
		'vals' : new Array('position','zIndex','background','filter','opacity','MozOpacity'),
		'vars' : new Array("absolute","3","#F0F0F0","alpha(opacity=40)",40/100,40/100)
	}
	this.id = 'verycd_screen'+Math.round(Math.random()*1000);
	this.obj = document.getElementById(this.id);
    this.hiddenObjects=new Array();

	if (!this.obj)
	{
		this.obj    = document.createElement("div");
		this.obj.id = this.id;
	}
	
	this.setStyle=function (name,value)
	{
		this.obj.style[name] = value;
	}
	this.setStyles1=function()
	{
		var sval = this.style1.vals;
		var svar = this.style1.vars;
		for (i=0; i<sval.length; i++) {
			this.setStyle(sval[i],svar[i]);
		}
	}
	this.setStyles2=function()
	{
		var sval = this.style2.vals;
		var svar = this.style2.vars;
		for (i=0; i<sval.length; i++) {
			this.setStyle(sval[i],svar[i]);
		}
	}
	this.build=function()
	{
        for (var i =0;i<this.hiddenObjects.length;i++) {
            try {
                this.hiddenObjects[i].style.visibility='hidden';
            } catch (e) {}
        }
		this.setStyles1();
        this.obj.style.height = getWindowLength('h')+'px';
        if (0 && location.href.indexOf('groups')!=-1) {
            this.obj.style.width = getWindowLength('w')+'px';
        } else {
            this.obj.style.width = '100%';
        }
		this.setStyles2();

		document.body.appendChild(this.obj);
		this.hideAllSelect();
	}
	this.destroy=function()
	{
        for (var i =0;i<this.hiddenObjects.length;i++) {
            try {
                this.hiddenObjects[i].style.visibility='visible';
            } catch (e) {}
        }
		this.setStyle("display","none");
		this.showAllSelect();
	}
	this.hideAllSelect = function ()
	{
		var slt = document.getElementsByTagName?document.getElementsByTagName("select"):new Array()
		for (var i=0; i<slt.length; i++)
		{
			slt[i].style.visibility = "hidden";
		}
	}

	this.showAllSelect = function ()
	{
		var slt = document.getElementsByTagName?document.getElementsByTagName("select"):new Array()
		for (var i=0; i<slt.length; i++)
		{
			slt[i].style.visibility = "visible";
		}
	}
}
function ScreenToggler(o) {
    this.o=o;
    switch ((typeof this.o).toLowerCase()) {
        case 'string':
            this.o=document.getElementById(this.o);
            break;
    }
    this.saver=new ScreenSaver();
    this.full=false;
    this.little_style={//styles for little boy
        'position':'',
        'backgroundColor':'#00ffff'
    };
    this.full_style={
        'position':'absolute',
        'backgroundColor':'#000000'
    };
    this.exec = function() {
        if (this.full) {//to little boy
            this.saver.destroy();
            var styles=this.little_style;
            this.full=false;
        } else {//to strong man
            this.saver.build();
            var styles=this.full_style;
            this.full=true;
        }
        for (style in styles) {
            this.o.style[style]=styles[style];
        }
    }
    this.exe=this.exec;
}
