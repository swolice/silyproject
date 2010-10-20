package comm.cache;

import java.lang.reflect.Method;

public abstract class MethodCache {
    public MethodCache() {
    }

    public Object execute(String method, Object ...parameters) {
        try {
            Class[] cs =new Class[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                cs[i] = parameters[i].getClass();
            }
            Method m = getClass().getDeclaredMethod(method, cs);
            if (m==null) return null;

            StringBuffer keyBuf=new StringBuffer().append(getClass().getName()).append(method).append('_');
            for (Object obj:parameters){
                keyBuf.append(val(obj)).append('_');
            }

            Object obj1=CacheUtil.getFromCache(keyBuf.toString());
            if (obj1==null){
                obj1=m.invoke(this,parameters);
                CacheUtil.putInCache(keyBuf.toString(),obj1,CacheUtil.CACHE_TIMEOUT);
                return obj1;
            }else{
                return obj1;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String val(Object obj){
        if (obj instanceof Number){
            return ""+((Number)obj).longValue();
        }else{
            return obj.toString();
        }
    }
}
