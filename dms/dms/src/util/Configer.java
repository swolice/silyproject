package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.io.InputStream;


public class Configer extends Properties {
    private static Configer instance;
    private static InputStream in;

    private Configer() {
        String path = this.getClass().getClassLoader().getResource(
                "config.properties").getPath();
        Logger.getLogger().debug("�����ļ���·��=" + path);

        try {
            in = Configer.class.getClassLoader().getResourceAsStream(
                    "config.properties");
            // in.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public synchronized static Configer getInstance() {
        if (instance == null) {
            instance = new Configer();
            // �����ļ�
            try {
                instance.load(in);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return instance;
    }

    public static void main(String[] arg) {
        Logger.getLogger().debug(Configer.getInstance().get("upload_path"));
    }
}
