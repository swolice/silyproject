(function(jQuery) {
	function Pannel(divId, options) {
		var _this = this;

		this.bind = divId;
		this.inited = false;
		this.id = "csch_area" + divId;
		
		this.options = jQuery.extend({
			'init':0
				}, options || {})
				
		
				
//		this.type = this.options.type;
//		this.initClass=this.options.initClass;

//		this.clearData = function() {
//			jQuery("#location_city_box" + _this.bind).empty().hide();
//			jQuery("#location_county_box" + _this.bind).empty().hide();
//			jQuery("span,p,.tac",jQuery("#location_school_box" + _this.bind)).remove();
//			jQuery("#location_school_box" + _this.bind).hide();
//		}

		this.empty=function (){
			jQuery("#"+_this.id).empty();
		}
		
		
		this.initSeg=function (){
			_this.empty();
			
			var f=jQuery("<form method='post'></form>");
			jQuery("#" + _this.id).append(f);
			var arr=new Array();
			arr.push("是否循环:<input type='radio' name='isLoop' value='0'/>是<input type='radio' name='isLoop' value='1' checked='true'/>否<br/>");
			arr.push("新记录:<input type='radio' name='isNew' value='0'/>是<input type='radio' name='isNew' value='1' checked='true'/>否<br/>");
			arr.push("开始:<input type='text' name='start'/><br/>");
			arr.push("结束:<input type='text' name='end'/><br/>");
			arr.push("<input type='hidden' name='id' value='"+_this.options.id+"'>");
			arr.push("<input type='hidden' name='fid' value='"+_this.options.fid+"'>");
			arr.push("<input type='hidden' name='ftype' value='"+_this.options.ftype+"'>");
			f.html(arr.join(""));

			var submit1=jQuery("<input type='button' name='submit1' value='提交'>");
			f.append(submit1);
			var cancle1=jQuery("<input type='button' name='submit1' value='取消'>");
			f.append(cancle1);
			cancle1.click(_this.restore);
			submit1.click(function(){
				var form1=f.get(0);
				form1.action='rule.do?method=addSeg';			
				form1.submit();
				//alert(jQuery(":radio[name=isLoop]:checked",f).val() +","+jQuery("input[name=start]",f).val())
			});
		}
		
		this.initValue=function (){
			_this.empty();
			
			var f=jQuery("<form  method='post'></form>");
			jQuery("#" + _this.id).append(f);
			var arr=new Array();
			arr.push("字段:<select name='name' ><option value='CREATE_TIME' >创建时间</option><option value='EDIT_TIME' >编辑时间</option><option value='CATEGORY' >团购类型</option><option value='CITY' >城市</option><option value='START_TIME' >开始时间</option><option value='END_TIME' >结束时间</option><option value='ORG_PRICE' >原价，分</option><option value='SELL_PRICE' >售价，分</option><option value='DISCOUNT' >折扣,折扣*100</option><option value='TITLE' >标题</option><option value='SITE_ID' >站点ID</option><option value='DETAIL' >详细信息</option><option value='ADDRESS' >商家地址</option><option value='USER_COUNT' >购买人数</option><option value='VIEW_FLAG' >是否显示</option><option value='DELETE_FLAG' >删除标识</option><option value='PHONE' >商家电话</option><option value='WEB_SITE' >商家网址</option><option value='TRAFFIC_INFO' >公交信息</option><option value='BIEFE' >简介</option><option value='LATLON' >经纬度</option><option value='AREA' >地区</option><option value='PIC_URL' >图片地址</option><option value='VIEW_NAME' >商家名称</option><option value='URL1' >实际地址</option></select>");
			arr.push("<br/>正则:<input type='text' name='reg'/><br/>");
			arr.push("<input type='hidden' name='id' value='"+_this.options.id+"'>");
			arr.push("<input type='hidden' name='fid' value='"+_this.options.fid+"'>");
			arr.push("<input type='hidden' name='ftype' value='"+_this.options.ftype+"'>");
			f.html(arr.join(""));

			var submit1=jQuery("<input type='button' name='submit1' value='提交'>");
			f.append(submit1);
			var cancle1=jQuery("<input type='button' name='submit1' value='取消'>");
			f.append(cancle1);
			cancle1.click(_this.restore);
			
			submit1.click(function(){
				var form1=f.get(0);
				form1.action='rule.do?method=addValue';			
				form1.submit();
				//alert(jQuery(":input[name=name]",f).val() +","+jQuery("input[name=reg]",f).val())
			});
			
		}
		
		this.initSpider=function (){
			_this.empty();
			
			var f=jQuery("<form  method='post'></form>");
			jQuery("#" + _this.id).append(f);
			var arr=new Array();
			arr.push("新记录:<input type='radio' name='isNew' value='0'/>是<input type='radio' name='isNew' value='1' checked='true'/>否<br/>");
			arr.push("正则:<input type='text' name='reg'/><br/>");
			arr.push("<input type='hidden' name='id' value='"+_this.options.id+"'>");
			arr.push("<input type='hidden' name='fid' value='"+_this.options.fid+"'>");
			arr.push("<input type='hidden' name='ftype' value='"+_this.options.ftype+"'>");
			f.html(arr.join(""));

			var submit1=jQuery("<input type='button' name='submit1' value='提交'>");
			f.append(submit1);
			var cancle1=jQuery("<input type='button' name='submit1' value='取消'>");
			f.append(cancle1);
			cancle1.click(_this.restore);
			
			submit1.click(function(){
				var form1=f.get(0);
				form1.action='rule.do?method=addSpider';			
				form1.submit();
				//alert(jQuery(":radio[name=isNew]:checked",f).val() +","+jQuery("input[name=reg]",f).val())
			});
						
		}
	
		this.init = function() {
			jQuery(".selectV1").hide();
			if (_this.inited) {
				_this.show();
				return;
			}
			var arr = new Array();
			arr
					.push('<div id="'
							+ _this.id
							+ '" style="text-align:left;width:603px;display:block; position:absolute;z-index:100; *left:0px; *top:25px;border:solid 1px #c3c3c3;margin:0 auto;padding:3px 8px;background:#fff;">');
			arr.push('</div>');

			var csch_area = jQuery(arr.join(""));
			csch_area.insertAfter(jQuery("#" + _this.bind));
			jQuery("#" + _this.id).addClass("selectV1");

			var b1=jQuery("<input type='button' name='b1' value='新增区块'/>");
			var b2=jQuery("<input type='button' name='b2' value='新增取值'/>");
			var b3=jQuery("<input type='button' name='b3' value='新增抓取'/>");
			var b4=jQuery("<input type='button' name='b4' value='取消'/>");
			
			csch_area.append(b1).append(b2).append(b3).append(b4);
			b1.click(_this.initSeg);
			b2.click(_this.initValue);
			b3.click(_this.initSpider);
			b4.click(_this.hide);
			_this.inited = true;
			_this.adjustPosition();
			_this.show();
			
		}
		

		this.restore=function(){
				_this.inited=false;
				_this.hide();
				jQuery("#"+_this.id).remove();				
		}

		this.hide = function() {
			jQuery("#" + _this.id).hide();
		}
		this.show = function() {
			jQuery("#" + _this.id).show();
		}
		this.adjustPosition = function() {
			var offset = jQuery("#" + _this.bind).offset();
			var left = offset.left - 10;
			var top = offset.top + 20;
			jQuery("#" + _this.id).css({
						top : top + "px",
						left : left + "px"
					});
		}


	}

	jQuery.fn.addRule = function(options, bind) {
		this.pannel = new Pannel(this.attr("id"), options);
		if (bind) {
			this.bind(bind, this.pannel.init);
		} else {
			this.click(this.pannel.init);
		}
		if (this.pannel.options.init==1){
			this.pannel.init();
		}
	}
})(jQuery)
