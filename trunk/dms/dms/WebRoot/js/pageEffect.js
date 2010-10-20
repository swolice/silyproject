function gotoPage(pageNum,theForm){
   if (!theForm)
   theForm=document.pageForm;

   theForm.pageNo.value = pageNum;
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
arr.push('<div class="technorati" id="_paging">');
arr.push('</div>');
//document.write(arr.join(""));
jQuery(arr.join("")).appendTo(theForm);

if (cur>1) jQuery("<a href=\"#\" id=\"_pre\">&lt;</a>").appendTo(jQuery("#_paging"));
if (sumPage<10){
 for (var _p=1;_p<=sumPage;_p++){
    pagingContent(_p,cur).appendTo(jQuery("#_paging"));
 }
}else{
 pagingContent(1,cur).appendTo(jQuery("#_paging"));
 pagingContent(2,cur).appendTo(jQuery("#_paging"));
 if (cur>5){
  jQuery("<span>...</span>").appendTo(jQuery("#_paging"));
 }
 var _p=(cur>5)?(cur-2):3;
 if (cur+4>sumPage) _p=sumPage-6;
 for (var count=0;count<5;_p++,count++){
  pagingContent(_p,cur).appendTo(jQuery("#_paging"));
 }
  if (cur+4<sumPage){
  jQuery("<span>...</span>").appendTo(jQuery("#_paging"));
 }
 pagingContent(sumPage-1,cur).appendTo(jQuery("#_paging"));
 pagingContent(sumPage,cur).appendTo(jQuery("#_paging"));
 if (cur<sumPage) jQuery("<a href=\"#\" id=\"_next\">&gt;</a>").appendTo(jQuery("#_paging"));
 }

jQuery("div.technorati a").click(function(){
  var pid=jQuery(this).attr("id");
  if (pid=="_next") pid="_p"+(cur+1);
  if (pid=="_pre") pid="_p"+(cur-1);
  pid=pid.substring(2);
  gotoPage(pid,theForm);
  return false;
});

}
