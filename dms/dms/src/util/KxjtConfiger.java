package util;

import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import java.util.HashMap;


public class KxjtConfiger extends Properties {
    private static HashMap<String,
            KxjtConfiger> map = new HashMap<String, KxjtConfiger>();
//    private static KxjtConfiger instance;
    private static InputStream in;

    private KxjtConfiger(String conf) {
        String path = this.getClass().getClassLoader().getResource(
                conf).getPath();
        Logger.getLogger().debug("配置文件的路径=" + path);

        try {
            in = KxjtConfiger.class.getClassLoader().getResourceAsStream(
                    conf);
            // in.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public synchronized static KxjtConfiger getInstance(String conf) {
        KxjtConfiger instance;
        if (map.get(conf) == null) {
            instance = new KxjtConfiger(conf);
            // 载入文件
            try {
                instance.load(in);
                map.put(conf, instance);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            instance = map.get(conf);
        }
        return instance;
    }

    public static void main(String[] arg) {
        System.out.println(KxjtConfiger.getInstance("test.properties").get("zzz"));
    }
}
