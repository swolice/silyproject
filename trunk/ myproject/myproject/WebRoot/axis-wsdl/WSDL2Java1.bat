set Axis_Lib=E:\myeclipseworkspace\testaxis\WebRoot\WEB-INF\lib
set Java_Cmd=java -Djava.ext.dirs=%Axis_Lib%
set Output_Path=E:\myeclipseworkspace\testaxis\src
set Package=com.jtcrm2acct.syn.service
set wsdl_path=D:\ÎÒµÄ×ÀÃæ\JTCRM2ACCTService.wsdl
%Java_Cmd% org.apache.axis.wsdl.WSDL2Java -o%Output_Path% -p%Package% %wsdl_path%
