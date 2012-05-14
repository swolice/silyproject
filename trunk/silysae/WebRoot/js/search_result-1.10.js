/********获取Cookie中的值********/
function getCookie(forecastCode){
	if (document.cookie.length>0){
	  c_start=document.cookie.indexOf(forecastCode + "=")
	  if (c_start!=-1){ 
		c_start=c_start + forecastCode.length+1 
		c_end=document.cookie.indexOf(";",c_start)
		if (c_end==-1) c_end=document.cookie.length
		return unescape(document.cookie.substring(c_start,c_end))
		} 
	}
	return ""
}
/**********设置cookie值****************/
function setCookie(forecastCode,value,expiredays){
	var exdate=new Date()
	exdate.setDate(exdate.getDate()+expiredays);
	document.cookie=forecastCode + "=" + escape(value) + ((expiredays==null) ? "" : ";expires="+exdate.toGMTString())+";path=/";
}				

function setProvinceSelect(scode){

	setCookie('city',scode,365);

	var selectName = "pcode";
	var funChange="changeProvince()";
	var sSelectPv = PStation.getProvinceSelect(scode,selectName,funChange);
	document.getElementById("tdprovince").innerHTML="<div>省份:&nbsp;"+sSelectPv+'</div>';
 }

 function changeStation(){
	var selectName = "pcode";
	var stName = "scode";
	var formStation = stationSearch.scode;
	if(formStation!=null){
		var sSelectedScode= getRadioValue(formStation);
		var sKey="keys";
		var inputKeys = document.getElementById(sKey);
		inputKeys.value=sSelectedScode;
		var stationSelected = PStation.getStation(sSelectedScode);
		if(stationSelected!=null){
		  document.getElementById("stationname").innerHTML=stationSelected.sname;
		}
	  }
  }
  
  function changeProvince(stationCode){
	var province_code = $("#pcode :selected").val()
  	var pc = PStation.get(province_code);
  	var stlist = pc.stationlist;

  	var sSelect="<option>请选择市区</option>";
  	for(i=0;i<stlist.data.length;i++){
  		db=stlist.data[i];
  		if(stationCode == db.scode)
  			selected = ' selected';
  		else
  			selected = '';
  		//sSelect +="<option value='"+db.pcode+"/"+db.pinyin+".html'"+selected+">"+db.sname+"</option>";
  		sSelect +="<option value='"+db.pcode+"/"+db.pinyin+"'"+selected+">"+db.sname+"</option>";
  	}
  	$("#cityValue").html(sSelect);
  }
  
  function openCity()
  {
	 // window.location = "/publish/forecast/" + $("#cityValue :selected").val();
	  var url =  "http://www.weather.gov.cn/publish/forecast/" + $("#cityValue :selected").val() + "_iframe.html";
	  jQuery("#w1").html(url);
	  jQuery("#if1").attr("src",url);
  }
  
  function getURL(){
	  var url =  "http://www.weather.gov.cn/publish/forecast/" + $("#cityValue :selected").val() + "_iframe.html";
	  return url;
  }
  
  var tag = true;
  function changShowPanlOpen(){
	if(tag){
		document.getElementById("showpanl").style.display="block";
		tag = false;
	}else{
		changShowPanlColse();
	}
	
  }

  function changShowPanlColse(){
	document.getElementById("showpanl").style.display="none";
	tag = true;
  }

  function setRadioFirstChecked(radio) {     
	if (!radio.length && radio.type.toLowerCase() == 'radio') { 
		radio.checked=true;  
	}else if (radio[0].tagName.toLowerCase() != 'input' ||  radio[0].type.toLowerCase() != 'radio') { 
		return ; 
	}else{
		radio[0].checked=true;
	}
	changeStation();
  }
  
  function getRadioValue(radio) {     
	if (!radio.length && radio.type.toLowerCase() == 'radio') { 
		return (radio.checked)?radio.value:'';  
	}      
	if (radio[0].tagName.toLowerCase() != 'input' ||  radio[0].type.toLowerCase() != 'radio') { 
		return ''; 
	}      
	var len = radio.length;     
	for(i=0; i<len; i++) {
		if (radio[i].checked){
			return radio[i].value;
		}
	}
	return ''; 
  }
  function change_order()
  {
	  if($("#order_hour").val() == 1)
	  {
		  $("#6h_list").hide();
		  $("#6hdiv").hide();
		  $("#24hdiv").hide();
		  $("#1h_list").show();
		  $("#1hdiv").show();
	  }
	  else if($("#order_hour").val() == 6)
	  {
		  $("#1h_list").hide();
		  $("#1hdiv").hide();
		  $("#24hdiv").hide();
		  $("#6h_list").show();
		  $("#6hdiv").show();
		  if($("#6h0").html() == '')
		  {
			  	$("#6h0").load('/ajax_order.php?name=6h_js_'+$("#6h_list").val()+'.html');
			  	$("#6h1").load('/ajax_order.php?name=6h_qw_'+$("#6h_list").val()+'.html');
		  }
	  }
	  else
	  {
		  $("#1h_list").hide();
		  $("#1hdiv").hide();
		  $("#6h_list").hide();
		  $("#6hdiv").hide();
		  $("#24hdiv").show();
		  
		  if($("#24h0").html() == '')
		  {
			  	$("#24h0").load('/ajax_order.php?name=r08_js_'+$("#r08_js").val()+'.html');
			    $("#24h1").load('/ajax_order.php?name=r20_js_'+$("#r20_js").val()+'.html');
			    $("#24h2").load('/ajax_order.php?name=ht_qw_'+$("#ht_qw").val()+'.html');
			    $("#24h3").load('/ajax_order.php?name=lt_qw_'+$("#lt_qw").val()+'.html');
		  }
	  }
  }
  function get_order(times)
  {
      if(times == '1')
      {
      $.each(['1h_js_','1h_qw_','1h_fs_'],function(index,value){ 
      	$.ajax({
         		type: "GET",
         		url: "/ajax_order.php",
         		data: "name="+value+$("#1h_list :selected").val()+'.html',
         		success: function(msg)
         		{
         			$("#1h"+index).html(msg);
         		}
         	  });
    	});
      }
      else if(times == '6')
      {
    	  $("#6h0").load('/ajax_order.php?name=6h_js_'+$("#6h_list").val()+'.html');
    	  $("#6h1").load('/ajax_order.php?name=6h_qw_'+$("#6h_list").val()+'.html');
      }
  }
  function selectTag(showContent,index,selfObj){
   		// 操作标签
   		var tag = document.getElementById("tags"+showContent).getElementsByTagName("li");
   		var taglength = tag.length;
   		for(i=0; i<taglength; i++){
   			tag[i].className = "";
   		}
   		selfObj.parentNode.className = "selectTag";
   		// 操作内容
   		for(i=0; j=document.getElementById(showContent+i); i++){
   			j.style.display = "none";
   		}
   		document.getElementById(showContent+index).style.display = "block";	
   	}
  function addCity()
  {
  	var custom_city = getCookie('customcity');
  	if(custom_city == '')
  		custom_city = '54511,北京;58367,上海;59287,广州;56571,西昌';
  	var stationcode = $("#stationcode :selected").val();
  	var stationname = $("#stationcode :selected").text();
  	if(!custom_city.match(stationcode))
  	{
  		var cc = custom_city.split(';');
  		custom_city = '';
  		$.each(cc,function(index,value){
  			if(index != 0)
  			{
  				var city = value.split(',');
  				custom_city += city[0]+','+city[1]+';';
  			}
  		});
  		custom_city += stationcode+','+stationname;
  	}
  	setCookie('customcity',custom_city,365);
  	loadCity();
  }

  function loadCity()
  {
  	var custom_city = getCookie('customcity');
  	if(custom_city == '')
  		custom_city = '54511,北京;58367,上海;59287,广州;56571,西昌';
  	var cc = custom_city.split(';');
  	$.each(cc,function(index,value){
  		var city = value.split(',');
  		$("#if"+index).attr('src','/publish/weather/24/'+city[0]+'.html');
  	});
  }
  function listCity()
  {
  	var province_code = $("#province :selected").val()
  	var pc = PStation.get(province_code);
  	var stlist = pc.stationlist;

  	var sSelect="";
  	for(i=0;i<stlist.data.length;i++){
  		db=stlist.data[i];
  		sSelect +="<option value='"+db.scode+"'>"+db.sname+"</option>";
  	}
  	$("#stationcode").html(sSelect);
  }
  
  function more_weather()
  {
  	if($('.if_weather').css('display') == 'none')
  	{
  		$('.if_weather').show();
  		$('#city_body').css("height",'');
  		$('#city_weather').css("height",'');
  		$('#more_weather').hide();
  	}
  	else
  	{
  		$('.if_weather').hide();
  		$('#city_body').css("height",'auto');
  		$('.city_weather').css("height",'auto');
  		$('#more_weather').show();
  	}
  }
  function getDayOfWeek(dayValue){
  	var ymd = dayValue.substr(0,4)+'/'+dayValue.substr(4,2)+'/'+dayValue.substr(6,2);
      var day = new Date(Date.parse(ymd));
      var today = new Array("周日","周一","周二","周三","周四","周五","周六");
      return today[day.getDay()];
   }
   
  function getTempChart(tempchart_date,tempchart_high,tempchart_low)
  {
  	var chart = new Highcharts.Chart({
  		chart:{renderTo:'tempchart',defaultSeriesType:'spline',margin: [50, 30, 50, 70]},
  		title:{text:"",style:{margin:''}},
  		subtitle:{style:{margin:'0 30px 0 0'}},
  		xAxis:{
  			categories:[],
  			labels:{formatter:function(){return this.value.substr(4,4)+'<br/>'+getDayOfWeek(this.value);}},
  			plotBands:[{color:'#e6f7e3',width:2,from:7,to:14,id:'plotline-1'}]
  		},
  		yAxis:{
  			title:{text:'温度',style:{color:'#f3715c'}},
  			labels:{formatter:function(){return this.value +'°C';},style:{color:'#f3715c'}},
  			plotLines:[{value:0,width:1,color:'#808080'}]
  		},
  		tooltip:{formatter:function(){return '<b>'+this.series.name+'</b><br/>'+this.x.substr(0,4)+'-'+this.x.substr(4,2)+'-'+this.x.substr(6,2)+': '+this.y+'°C';}},
  		legend:{style:{left:'280px',top:'10px'}},
  		series:[{name:'高温',color:'#f3715c'},{name:'低温'}]
  	});
  	chart.xAxis[0].setCategories(tempchart_date);
  	chart.xAxis[0].addPlotBand({from:-1,to:7,color:'#e0e9ff',id:'plot-band-1'});
  	chart.xAxis[0].addPlotBand({from:7,to:14,color:'#e1ffc7',id:'plot-band-2'});
  	chart.series[0].hide();
  	chart.series[1].hide();
  	chart.series[0].setData(tempchart_high);
  	chart.series[0].show();
  	chart.series[1].setData(tempchart_low);
  	$("#htemp").click(function(){
  		if($("#htemp").attr('checked')==true)
  			chart.series[0].show();
  		else
  			chart.series[0].hide();
  	});
  	$("#ltemp").click(function(){
  		if($("#ltemp").attr('checked')==true)
  			chart.series[1].show();
  		else
  			chart.series[1].hide();
  	});
  }
  function getHours(hours_tdate,hours_tvalue,hours_pvalue,hours_hvalue,hours_airpressure,hours_wind,hours_line)
  {
  	var hours = new Highcharts.Chart({
  		chart:{renderTo:'hours',defaultSeriesType:'spline',margin:[60,170,60,130]},
  		title:{text:"",style:{margin:''}},
  		subtitle:{text:'',style:{margin:'0 100px 0 0'}},
  		xAxis:{title:{text:''}},
  		yAxis:[
  			{labels:{formatter:function(){return this.value;},style:{color:'#2468a2'}},title:{text:'降水(mm)',style:{color:'#2468a2'},margin:3},	opposite:true},
  			{title:{text:'气温(°C)',	margin:3,style:{color:'#f3715c'}},labels:{formatter:function(){return this.value;},style:{color:'#f3715c'}},offset:0},
  			{title:{text:'相对湿度',	margin:3,style:{color:'#AA4643'}},labels:{formatter:function(){return this.value +'%';},style:{color:'#AA4643'}},opposite:true,offset:50},
  			{title:{text:'气压(hPa)',margin:3,style:{color:'#585eaa'}},labels:{style:{color:'#585eaa'}},offset:53}, 
  			{title:{text:'风速(M/S)',margin:3,style:{color:'#918597'}},labels:{style:{color:'#918597'}},opposite:true,offset:110}
  		],
  		tooltip:{
  			formatter:function(){
  				var flagname="";
  				if(this.series.name=="气温")	{
  					flagname="°C"
  				}else if (this.series.name=="降水"){
  				  	flagname="mm";
  				}else if (this.series.name=="气压"){
  				  	flagname="hPa";
  				}else if (this.series.name=="相对湿度"){
  				  	flagname="%";
  				}else{
  				 	flagname="M/S";
  				}
  				return '<b>'+   this.series.name +'</b><br/>'+ this.y+flagname;
  			}
  		},
  		plotOptions:{series:{events:{hide: function(a){this.yAxis.axisTitle.hide();},show: function(){this.yAxis.axisTitle.show();}}}},
  		legend:{style:{left:'180px',top:'5px'}},
  		series:[
  			{name:'气温',color:'#f3715c',yAxis:1},
  			{name:'降水',type:'column',color:'#2468a2',yAxis:0},
  			{name:'相对湿度',yAxis:2,color:'#AA4643'},
  			{name:'气压',yAxis:3,color:'#585eaa'},
  			{name:'风速',yAxis:4,color:'#918597'}]
  		});
  	hours.xAxis[0].setCategories(hours_tdate);
  	hours.xAxis[0].addPlotLine({value: -0.5,color: '#c0c0c0',width: 1,id: 'plot-line_0' });
  	for (i=0;i<24 ;i++ )
  	{
  		hours.xAxis[0].addPlotLine({value:i+".5",color:'#c0c0c0',width:1,id:'plot-line_'+i});
  	}
  	if(hours_line != '')
  		hours.xAxis[0].addPlotLine({value:hours_line,color:'green',width:2,id:'plot-line_0'});
  	hours.series[0].hide();
  	hours.series[1].hide();
  	hours.series[2].hide();
  	hours.series[3].hide();
  	hours.series[4].hide();
  	hours.series[0].setData(hours_tvalue);
  	hours.series[0].show();
  	hours.series[1].show();
  	hours.series[1].setData(hours_pvalue);
  	hours.series[2].setData(hours_hvalue);
  	hours.series[3].setData(hours_airpressure);
  	hours.series[4].setData(hours_wind);
  	var precipitation = hours_pvalue[23] == '0' ? '0.0' : hours_pvalue[23];
  	var qw = (hours_tvalue[23] < 9999 && hours_tvalue[23] != null) ? hours_tvalue[23]+"℃" : "无数据";
  	var js = (precipitation < 9999 && precipitation != null) ? precipitation+"mm" : "无数据";
  	var sd = (hours_hvalue[23] < 9999 && hours_hvalue[23] != null) ? hours_hvalue[23]+"%" : "无数据";
  	var qy = (hours_airpressure[23] < 9999 && hours_airpressure[23] != null) ? hours_airpressure[23]+"hPa" : "无数据";
  	var fs = (hours_wind[23].y < 9999 && hours_wind[23].y != null) ? hours_wind[23].y+"M/S" : "无数据";
  	
  	$('#hours_title').html("最新整点实况（"+hours_tdate[23]+"时）：<font color='#f3715c'>气温："+qw+"</font> <font color='#2468a2'>降水："+js+"</font> <font color='#AA4643'>相对湿度："+sd+"</font> <font color='#585eaa'>气压："+qy+"</font> <font color='#918597'>风速："+fs+"</font>");
  	$("#c_temperature").click(function(){
  		if($("#c_temperature").attr('checked')==true)
  			hours.series[0].show();
  		else
  			hours.series[0].hide();
  	});
  	$("#c_precipitation").click(function(){
  		if($("#c_precipitation").attr('checked')==true)
  			hours.series[1].show();
  		else
  			hours.series[1].hide();
  	});
  	$("#c_humidity").click(function(){
  		if($("#c_humidity").attr('checked')==true)
  			hours.series[2].show();
  		else
  			hours.series[2].hide();
  	});
  	$("#c_airpressure").click(function(){
  		if($("#c_airpressure").attr('checked')==true)
  			hours.series[3].show();
  		else
  			hours.series[3].hide();
  	});
  	$("#c_wind").click(function(){
  		if($("#c_wind").attr('checked')==true)
  			hours.series[4].show();
  		else
  			hours.series[4].hide();
  	});
  }
  function setRealWeatherDate(strDate)
  {
	  $("#realdate").html('（'+strDate+'发布）');
  }