package comm.cache;

import java.util.*;

public class LocalCache implements MemCache {
        private static Map memMap = new HashMap();
        private static Map timeMap = new HashMap();
        private static LocalCache instance;

        private LocalCache() {
                new Thread(new TimeoutTask(timeMap, memMap)).start();
        }

        public static synchronized LocalCache getInstance() {
                if (instance == null) {
                        instance = new LocalCache();
                }
                return instance;
        }

        public void clear() {
                timeMap.clear();
                memMap.clear();
        }

        public void delete(String key) {
                timeMap.remove(key);
                memMap.remove(key);
        }

        public Object get(String key) {
                TimeoutData data = (TimeoutData) timeMap.get(key);
                if (data==null) return null;
                long time = (new Date().getTime() - data.getDate().getTime()) / 1000;
                //System.out.println(time+"&"+data.getTimeout());
                if (time > data.getTimeout() && data.getTimeout() != 0) {
                        timeMap.remove(key);
                        memMap.remove(key);
                }
                return memMap.get(key);
        }

        public void set(String key, Object obj) {
                memMap.put(key, obj);
                timeMap.put(key, new TimeoutData(new Date(), 0));
        }

        public void set(String key, Object obj, long timeout) {
                memMap.put(key, obj);
                timeMap.put(key, new TimeoutData(new Date(), timeout));
        }

}

class TimeoutTask implements Runnable {
        private Map timeMap;
        private Map emeMap;

        public TimeoutTask(Map timeMap, Map emeMap) {
                this.timeMap = timeMap;
                this.emeMap = emeMap;
        }

        public void run() {
                while (true) {
                        Set set = timeMap.keySet();
                        Object[] objs = set.toArray(new Object[set.size()]);
                        for (Object obj : objs) {
                                TimeoutData data = (TimeoutData) timeMap.get(obj);
                                long time = (new Date().getTime() - data.getDate().getTime()) / 1000;
                                if (time > data.getTimeout() && data.getTimeout() != 0) {
                                        timeMap.remove(obj);
                                        emeMap.remove(obj);
                                }
                        }
                        try {
                                Thread.sleep(1000 * 60);
                        } catch (InterruptedException e) {
                                e.printStackTrace();
                                break;
                        }
                }
        }
}

class TimeoutData {
        private Date date;
        private long timeout;

        public TimeoutData(Date date, long timeout) {
                this.date = date;
                this.timeout = timeout;
        }

        public Date getDate() {
                return date;
        }

        public void setDate(Date date) {
                this.date = date;
        }

        public long getTimeout() {
                return timeout;
        }

        public void setTimeout(long timeout) {
                this.timeout = timeout;
        }

}
