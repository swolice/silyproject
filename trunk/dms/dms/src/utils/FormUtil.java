package utils;

import java.util.*;

import javax.servlet.http.*;

import net.sf.json.*;

public class FormUtil {
    private static String key="formUtil";

    public Map parameters=null;
    public Object obj=null;
    public FormUtil(HttpServletRequest request) {
        parameters=new HashMap();
        parameters.putAll(request.getParameterMap());

        request.setAttribute(key,this);
    }

    public void put(Object key,Object value) throws Exception{
        if (parameters==null){
            throw new Exception("没有初始化！");
        }

        parameters.put(key,value);
    }

    public void remove(Object key) throws Exception{
        if (parameters==null){
            throw new Exception("没有初始化！");
        }

        parameters.remove(key);
    }

    public String toString(){
        JSONObject obj = JSONObject.fromObject(this);
        return obj.toString();
    }

    public static String json(HttpServletRequest request){
        Object obj=request.getAttribute(key);
        if (obj==null){
            return new FormUtil(request).toString();
        }else{
            return obj.toString();
        }
    }

    public Object getObj() {
        return obj;
    }

    public Map getParameters() {
        return parameters;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

}
