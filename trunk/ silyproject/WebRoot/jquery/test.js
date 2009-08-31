function delUser(){
	var s_len = select_array.length;
	if(s_len == 0)return;
	var l = Math.ceil(user_array.length/4);
	for(var i=0;i<s_len;i++){
		for(var j=0;j<user_array.length;j++){
			if(select_array[0].innerHTML == user_array[j].value){
				user_array.remove(user_array[j]);
				break;
			}
		}
		select_array.remove(select_array[0]);
	}
	var user_table = document.frames["jsztj"].document.getElementById("user_table");
	
	if(l == 0){
		user_table.deleteRow(0);
	}else{
		for(var i=0;i<l;i++){
			user_table.deleteRow(0);
		}
	}
	var tr = null;
	for(var i=0;i<user_array.length;i++){
		if(i%4 == 0){
			tr = user_table.insertRow(-1);
		}
//		var td1 = tr.insertCell(-1);
//		td1.onclick = function(){
//			if(this.bgColor.toUpperCase() == "#EBFFFD"){
//				this.bgColor = "";
//				this.nextSibling.bgColor = "";
//				select_array.remove(this.nextSibling);
//			}else{
//				this.bgColor = "#EBFFFD";
//				this.nextSibling.bgColor = "#EBFFFD";
//				select_array.push(this.nextSibling);
//			}
//		}
//		td1.innerHTML = '<img src="images/jsz_131.gif" width="14" height="17" alt="" />'
//		td1.setAttribute("width",25);
//		td1.setAttribute("align","left");
		var td2 = tr.insertCell(-1);
		td2.onclick = function(){
			if(this.bgColor.toUpperCase() == "#EBFFFD"){
				this.bgColor = "";
				this.previousSibling.bgColor = "";
				select_array.remove(this);
			}else{
				this.bgColor = "#EBFFFD";
				this.previousSibling.bgColor = "#EBFFFD";
				select_array.push(this);
			}
		}
		td2.innerHTML = user_array[i].value;
		td2.setAttribute("width",40);
		td2.setAttribute("align","left");
	}
}