	function rangeSearch(type,range){
		if (type==0){
			//location.href='index.do?method=list&key='+jQuery("#key").val()+'&category='+jQuery("#category").val()+"&dd="+range;
			document.searchForm.dd.value=range;
			document.searchForm.pd.value=0;
			document.searchForm.submit();
		}else{
			document.searchForm.pd.value=range;
			document.searchForm.dd.value=0;
			document.searchForm.submit();
			//location.href='index.do?method=list&key='+jQuery("#key").val()+'&category='+jQuery("#category").val()+"&pd="+range;
		}
	}
	
	function groupSearch(obj){
		var g=jQuery(obj).val();
		
		var ks=document.searchForm.group.value;
		var ss=ks.split(",");
		var b=false;
		var i=0;
		for (i=0;i<ss.length;i++){
			if (ss[i]==g) {
				b=true;
				break;
			}
		}
	  if (!b){
	  	if (jQuery(obj).attr("checked")==true){
	  		if (document.searchForm.group.value.length>0){
	  		document.searchForm.group.value=document.searchForm.group.value+","+g;
	  	}else{
	  		document.searchForm.group.value=g;
	  	}
	  		
	    }
	  }else{
	  	if (jQuery(obj).attr("checked")==false){
				ss.splice(i,1);
				document.searchForm.group.value=ss.join(",");
	  	}
	  }
	  
	  
		document.searchForm.submit();
	}
	
	function submitForm(){
			document.searchForm.dd.value=0;
			document.searchForm.pd.value=0;
			document.searchForm.submit();
	}
	
	function sortSearch(sort){
		 var sortType=document.searchForm.sortType.value;
		 if (sortType==0) sortType=1;
		 else sortType=0;
		
			document.searchForm.sort.value=sort;
			document.searchForm.sortType.value=sortType;
			document.searchForm.submit();
	}
	
	function promote(id,div){
		jQuery.ajax({
			url:'index.do?method=promote',
			dataType : "text" ,
			data:{'id':id},
			success:function(text){
				if (text!=null&&text!=''){
					jQuery("#"+div).text(text);
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown) {
									try{
									alert(XMLHttpRequest.responseText);
									}catch(e){
									alert("对不起，您已经投票！");
								 }
			}
		});
	}