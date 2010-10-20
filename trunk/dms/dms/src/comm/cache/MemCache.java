package comm.cache;

public interface MemCache {
          public void set(String key, Object obj);
          public void set(String key, Object obj, long timeout);
//	  public void replace(String key, Object obj, long timeout);
          public Object get(String key);
          public void delete(String key);
          public void clear() ;
}
