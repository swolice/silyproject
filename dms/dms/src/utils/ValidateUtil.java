package utils;

import net.sf.json.*;

public class ValidateUtil {
    public static int STATE_SUCCESS=1;
    public static int STATE_FAILURE=0;
    private int state;
    private String message;
    private String func;
    private Object obj;
    public ValidateUtil(int state,String message) {
        this.state=state;
        this.message=message;
    }

    public static String help(){
        StringBuffer buf=new StringBuffer();
        buf.append("\r\n");
        buf.append("state : 返回状态 （0失败1成功）\r\n");
        buf.append("message : 返回提示信息 \r\n");
        buf.append("func : 返回后执行的函数名称,类似 functionName(json),json为此ValidateUtil对象 \r\n");
        buf.append("obj : 返回的其它数据对象，必须为json可处理的对象。\r\n");
        return buf.toString();
    }

    public ValidateUtil(int state, String message,String function,Object obj) {
        this.state = state;
        this.message = message;
        this.func=function;
        this.obj=obj;
    }


    public void setState(int state) {
        this.state = state;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setFunc(String func) {

        this.func = func;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public int getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }

    public String getFunc() {

        return func;
    }

    public Object getObj() {
        return obj;
    }

    public String toString() {
        JSONObject obj = JSONObject.fromObject(this);
        return obj.toString();
    }
}
