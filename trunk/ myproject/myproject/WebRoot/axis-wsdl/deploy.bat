set Axis_Lib=E:\myeclipseworkspace\Account_0304\WebContent\WEB-INF\lib
set Java_Cmd=java -Djava.ext.dirs=%Axis_Lib%
set Axis_Servlet=http://localhost:8080/Account/servlet/AxisServlet
%Java_Cmd% org.apache.axis.client.AdminClient -l%Axis_Servlet% deploy.wsdd