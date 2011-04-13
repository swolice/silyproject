set Axis_Lib=E:\myeclipseworkspace\testaxis\WebRoot\WEB-INF\lib
set Java_Cmd=java -Djava.ext.dirs=%Axis_Lib%
set Output_Path=E:\myeclipseworkspace\Account_0304\JavaSource\interface
set Package=com.jtcrm.acct.interfaces.webservice.bussiness
set wsdl_path=http://172.16.1.251:9080/CRM-DEP/services/CRMOrderSpsWebService?wsdl
%Java_Cmd% org.apache.axis.wsdl.WSDL2Java -o%Output_Path% -p%Package% %wsdl_path%
