package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import com.sun.mail.util.LineInputStream;
import comm.Ini;

public class ItemConfiger extends Properties {
	private static ItemConfiger instance;
	private static FileInputStream in;

	private ItemConfiger() {
		String path = this.getClass().getClassLoader().getResource(
				"item.properties").getPath();
		Logger.getLogger().debug("�����ļ���·��=" + path);
		File file = new File(path);

		try {
			in = new FileInputStream(file);
			// in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized static ItemConfiger getInstance() {
		if (instance == null) {
			instance = new ItemConfiger();
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
		Logger.getLogger().debug(ItemConfiger.getInstance().getProperty("upload_temp"));
	}
}
