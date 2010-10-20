function gotoPage(pageNum,theForm){
   if (!theForm)
   theForm=document.pageForm;

   theForm.pageNo.value = pageNum;
   theForm.pageSize.value =jQuery("#_pageSize").val();
   theForm.submit();
}

function gotoPageNum(sumPages,formName){
   if (!theForm)
   theForm=document.pageForm;

    if(theForm.pageNum.value.length==0) {
	    alert("页号不能为空");
	    return false;
	}
	if((theForm.pageNum.value).search("^[1-9]\d*|0$")!=0){
	     alert("必须输入数字");
	     theForm.pageNum.value = "";
	     return false;
    }
   if(theForm.pageNum.value<1||theForm.pageNum.value>sumPages) {
         theForm.pageNum.value = "";
         return false;
    }
    gotoPage(theForm.pageNum.value,formName);
}

function pagingContent(pageNo,cur){
  if (pageNo==cur){
    return jQuery("<span class=\"current\">"+pageNo+"</span>");
  }else{
    return jQuery("<a href=\"#\" id=\"_p"+pageNo+"\">"+pageNo+"</a>");
  }
}

function viewPage(cur,sumPage,sumCount,type,theForm){
var arr=new Array();
arr.push('<div id="turn-page">');
arr.push('总计<span id="totalRecords"></span>');
arr.push('个记录分为<span id="totalPages"></span>');
arr.push('页当前第<span id="pageCurrent">1</span>');
arr.push('页,每页<input type="text" value="15" id="_pageSize" name="_pageSize" size="3">');
arr.push('<span id="page-link">');
arr.push('</span>');
arr.push('</div>');

var pageDiv=jQuery(arr.join(""));
pageDiv.appendTo(theForm);

jQuery('#totalRecords').text(sumCount);
jQuery('#totalPages').text(sumPage);
jQuery('#pageCurrent').text(cur);
jQuery('#_pageSize').val(theForm.pageSize.value);


var plink=jQuery("#page-link");
jQuery('<a href="#" id="__pre">第一页</a>').appendTo(plink);
jQuery('<span>&nbsp;</span>').appendTo(plink);
jQuery('<a href="#" id="_pre">上一页</a> ').appendTo(plink);
jQuery('<span>&nbsp;</span>').appendTo(plink);
jQuery('<a href="#" id="_next">下一页</a> ').appendTo(plink);
jQuery('<span>&nbsp;</span>').appendTo(plink);
jQuery('<a href="#" id="__next">最末页</a> ').appendTo(plink);
jQuery('<select id="_gotoPage"></select>').appendTo(plink);

jQuery("#page-link a").click(function(){
  var pid=jQuery(this).attr("id");
  if (pid=="_next") {
  	pid="_p"+(cur+1);
  	if (cur>=sumPage) return false;
  }
  if (pid=="_pre"){
  	 pid="_p"+(cur-1);
  		if (cur<=1) return false;
  	}
  if (pid=="__next"){
  	 pid="_p"+sumPage;
  	 if (cur>=sumPage) return false;
   }
  if (pid=="__pre"){
  	 pid="_p"+1;
  	 if (cur<=1) return false;
  }
  pid=pid.substring(2);
  gotoPage(pid,theForm);
  return false;	
});

	var _gotoPage=jQuery('#_gotoPage');
	for (i=1;i<=sumPage;i++){
		jQuery('<option value='+i+'>'+i+'</option>').appendTo(_gotoPage);
	}
	
	_gotoPage.val(cur);

	_gotoPage.change(function(){
		gotoPage(_gotoPage.val(),theForm);
	});
	
	theForm.onsubmit=function(){
		theForm.pageSize.value=jQuery('#_pageSize').val();
		return true;
	}

}
