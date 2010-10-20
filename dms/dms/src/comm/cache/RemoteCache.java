package comm.cache;

import java.util.Collection;
import java.util.Date;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import util.Configer;

/**
 * 1、解压二进制包到你希望的目录（如：c:memcached）
 *
 * 2、用Win的CMD安装服务，在命令行下输入 c:\memcached\memcached.exe -d install
 *
 * 3、命令行下启动memcached 服务 c:\memcached\memcached.exe -d start
 *
 * 4、使用默认的11211端口监听。
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

                // 创建一个实例对象SockIOPool
                SockIOPool pool = SockIOPool.getInstance();

                // set the servers and the weights
                // 设置Memcached Server
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

                // Tcp的规则就是在发送一个包之前，本地机器会等待远程主机
                // 对上一次发送的包的确认信息到来；这个方法就可以关闭套接字的缓存，
                // 以至这个包准备好了就发；
                pool.setNagle(false);
                // 连接建立后对超时的控制
                pool.setSocketTO(3000);
                // 连接建立时对超时的控制
                pool.setSocketConnectTO(0);

                // initialize the connection pool
                // 初始化一些值并与MemcachedServer段建立连接
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
