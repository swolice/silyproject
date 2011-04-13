set Axis_Lib=F:\myeclipseworkspace\aaa\WebRoot\WEB-INF\lib
set Java_Cmd=java -Djava.ext.dirs=%Axis_Lib%
set Output_Path=F:\myeclipseworkspace\aaa\src
set Package=cn.chinatelecom.www
set wsdl_path=d:\ÎÒµÄ×ÀÃæ\webservice_config\SGWService.wsdl
%Java_Cmd% org.apache.axis.wsdl.WSDL2Java -o%Output_Path% -p%Package% %wsdl_path%
