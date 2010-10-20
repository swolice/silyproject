package comm.cache;

import java.util.Collection;
import java.util.Date;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import util.Configer;

/**
 * 1����ѹ�����ư�����ϣ����Ŀ¼���磺c:memcached��
 *
 * 2����Win��CMD��װ������������������ c:\memcached\memcached.exe -d install
 *
 * 3��������������memcached ���� c:\memcached\memcached.exe -d start
 *
 * 4��ʹ��Ĭ�ϵ�11211�˿ڼ�����
 */
public class RemoteCache implements MemCache {
        private static MemCachedClient mcc = new MemCachedClient();
        static {
                String ip = Configer.getInstance().getProperty("mem_ip");
                int weight = Integer.parseInt(Configer.getInstance().getProperty(
                                "mem_weights"));
                int initConn = Integer.parseInt(Configer.getInstance().getProperty(
                                "mem_initConn"));
                int maxConn = Integer.parseInt(Configer.getInstance().getProperty(
                                "mem_maxConn"));
                String[] servers = { ip };

                Integer[] weights = { weight };

                // ����һ��ʵ������SockIOPool
                SockIOPool pool = SockIOPool.getInstance();

                // set the servers and the weights
                // ����Memcached Server
                pool.setServers(servers);
                pool.setWeights(weights);

                // set some basic pool settings
                // 5 initial, 5 min, and 250 max conns
                // and set the max idle time for a conn
                // to 6 hours
                pool.setInitConn(initConn);
                pool.setMinConn(initConn);
                pool.setMaxConn(maxConn);
                pool.setMaxIdle(1000 * 60 * 60 * 6);

                // set the sleep for the maint thread
                // it will wake up every x seconds and
                // maintain the pool size
                pool.setMaintSleep(30);

                // Tcp�Ĺ�������ڷ���һ����֮ǰ�����ػ�����ȴ�Զ������
                // ����һ�η��͵İ���ȷ����Ϣ��������������Ϳ��Թر��׽��ֵĻ��棬
                // ���������׼�����˾ͷ���
                pool.setNagle(false);
                // ���ӽ�����Գ�ʱ�Ŀ���
                pool.setSocketTO(3000);
                // ���ӽ���ʱ�Գ�ʱ�Ŀ���
                pool.setSocketConnectTO(0);

                // initialize the connection pool
                // ��ʼ��һЩֵ����MemcachedServer�ν�������
                pool.initialize();
                // lets set some compression on for the client
                // compress anything larger than 64k
                mcc.setCompressEnable(true);
                mcc.setCompressThreshold(64 * 1024);
        }

        public void clear() {
                mcc.flushAll();

        }

        public void delete(String key) {
                mcc.delete(key);

        }

        public Object[] values(String[] keys) {
                return mcc.getMultiArray(keys);
        }


        public Object get(String key) {
                return mcc.get(key);
        }

        public void set(String key, Object obj) {
                mcc.set(key, obj);

        }

        public void set(String key, Object obj, long timeout) {
                mcc.set(key, obj, new Date(timeout * 1000));
        }

        public Object[] keys() {
                // TODO Auto-generated method stub
                return null;
        }
}
