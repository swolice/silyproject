function $setValue(name,value)
{
var theForm="document.forms[0]";
if(arguments.length==3){
theForm = arguments[2];
theForm=eval(theForm);
}
//alert(name.type);
for(var i=0;i<theForm.elements.length;i++)
{

if(theForm.elements[i].name==name)
{
//复选框
if(theForm.elements[i].type=="checkbox")
{
	var b=value;
	for (var j=0;j<b.length;j++){
if(theForm.elements[i].value==b[j])
{
theForm.elements[i].checked=true; 
}
}
}

//单选按钮
if(theForm.elements[i].type=="radio")
{ 
if(theForm.elements[i].value==value)
{
theForm.elements[i].checked=true; 
}
}
//输入框
if(theForm.elements[i].type=="text"||theForm.elements[i].type=="password"||theForm.elements[i].type=="hidden"||theForm.elements[i].type=="textarea")
{
theForm.elements[i].value=value;
}

//下拉列表
if(theForm.elements[i].type=="select-one")
{
for(j=0;j<theForm.elements[i].length;j++)
{
if(theForm.elements[i][j].value==value)
{
theForm.elements[i][j].selected=true;
}
}
}

if(theForm.elements[i].type=="select-multiple")
{
	for (var k=0;k<value.length;k++){
for(j=0;j<theForm.elements[i].length;j++)
{
if(theForm.elements[i][j].value==value[k])
{
theForm.elements[i][j].selected=true;
}
}
}
}

//--
}
}
return;
}

function $form($form){
var theForm=document.forms[0];
if(arguments.length==2){
theForm = arguments[1];
theForm=eval(theForm);
}
	var ps=$form.parameters;
	var _k;
	for (_k in ps){
		$setValue(_k,ps[_k],theForm);
	}
}

function submitForm(page,maxPage){
var form=document.forms[0];
if(arguments.length==3){
form = arguments[2];
form=eval(form);
}

if (page=='') page=1;
	
    if (page<1){
    alert("请输入正确的页码！");
    form.page.focus();
    return;
  }
  
  if (maxPage){
  if (page>maxPage){
  alert("页码超过最大值，请重新输入！");
  form.page.focus();
  return;
  }
 }

  form.page.value=page;
  form.submit();
}
