package comm.cache;

import comm.cache.MemCache;
import comm.SpringFactory;

public class CacheUtil {
    public CacheUtil() {
    }
    
    public static final long CACHE_TIMEOUT=60*10;

    public static Object getFromCache(String key) {
        MemCache cache = (MemCache) SpringFactory.getInstance().getBean(
                "memCache");
        Object obj = cache.get(key);
        return obj;
    }

    public static Object getFromCache(String key, DataInit init) {
        Object obj = getFromCache(key);
        if (obj != null)
            return obj;
        obj = init.init(key);
        if (obj != null) {
            putInCache(key, obj, CACHE_TIMEOUT);
        }

        return obj;
    }


    public static Object putInCache(String key, Object obj, long timeout) {
        MemCache cache = (MemCache) SpringFactory.getInstance().getBean(
                "memCache");
        if (timeout>-1){
            if (timeout==0){
                cache.delete(key);
            }else{
                cache.set(key, obj, timeout);
            }
        }else{
            cache.set(key, obj);
        }
        return obj;
    }


}
