package logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.MyResourceBundle;
import utils.StringUtils;
import dao.JsoupDao;

public class JsoupLogic {

	private static Logger logger = Logger.getLogger("log");
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			try {
				Thread.currentThread().sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			new Thread(new JsoupThread()).start();
			new Thread(new DownloadTread()).start();
		}
	}
	
	public static void appStart(){
		for (int i = 0; i < 10; i++) {
			try {
				Thread.currentThread().sleep(10000);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(),e);
			}
			new Thread(new JsoupThread()).start();
			new Thread(new DownloadTread()).start();
		}
	}
	

	public static void process() {
		String starturl = getCommonProp("starturl");
		String urls[] = starturl.split(";");
		
		for (int i = 0; i < urls.length; i++) {
			
			String starturl1 = urls[i];
		//if (!JsoupDao.hasUrlHref(starturl, "1")){
			Document doc = null;
			try {
				doc = getDocument(starturl1);
			} catch (SocketTimeoutException e) {
				logger.error(e.getMessage(),e);
			}
			//JsoupDao.insertUrlHref(starturl, "1", doc.title(), starturl);
			
			Elements links = doc.select("a[href]");
			for (Element link : links) {
				String absHref = link.attr("abs:href");
				if (JsoupDao.hasUrlHref(absHref, "1"))
					continue;
				if (!absHref.startsWith("http://"))
					continue;

				Document doc1 = null;
				try {
					doc1 = getDocument(absHref);
				} catch (SocketTimeoutException e) {
					logger.error(e.getMessage(),e);
				}
				
				
				if (null == doc1)
					continue;
				JsoupDao.insertUrlHref(absHref, "1", doc.title(), starturl1);
				Elements imgs = doc1.select("[src]");
				for (Element img : imgs) {
					if (img.attr("type").equals("image")) {
						String imgurl = img.attr("abs:src");
						if (JsoupDao.hasUrlHref(imgurl, "2")) {
							continue;
						}
						JsoupDao.insertUrlHref(imgurl, "2", doc1.title(), absHref);
					}
				}
			}
			JsoupDao.updateVflag(starturl1);
		//}
		}
		processDBhref();
		
	}

	public static void processDBhref() {

		String dbhrefurl = JsoupDao.getDownUrlHref();
		if (!StringUtils.isEmpty(dbhrefurl)) {
			Document doc = null;
			try {
				doc = getDocument(dbhrefurl);
			} catch (SocketTimeoutException e) {
				logger.error(e.getMessage(),e);
			}
			
			Elements links = doc.select("a[href]");
			for (Element link : links) {
				String absHref = link.attr("abs:href");
				if (JsoupDao.hasUrlHref(absHref, "1"))
					continue;
				if (!absHref.startsWith("http://"))
					continue;
				Document doc1 = null;
				try {
					doc1 = getDocument(absHref);
				} catch (SocketTimeoutException e) {
					logger.error(e.getMessage(),e);
				}
				if (null == doc1)
					continue;
				JsoupDao.insertUrlHref(absHref, "1", doc.title(), dbhrefurl);
				Elements imgs = doc1.select("[src]");
				for (Element img : imgs) {
					if (img.attr("type").equals("image")) {
						String imgurl = img.attr("abs:src");
						if (JsoupDao.hasUrlHref(imgurl, "2")) {
							continue;
						}
						JsoupDao.insertUrlHref(imgurl, "2", doc1.title(),
								absHref);
					}
				}
			}
			JsoupDao.updateVflag(dbhrefurl);
		}
		processDBhref();
	}

	private static Document getDocument(String urlStr) throws SocketTimeoutException {
		if (StringUtils.isEmpty(urlStr))
			return null;

		logger.debug(urlStr);

		URL url = null;
		Document doc = null;
		try {
			url = new URL(urlStr);
			doc = Jsoup.parse(url, 3 * 2000);
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(),e);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}

		return doc;
	}

	public static void downFileByList() {
		List<String> list = JsoupDao.getFileList();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
			try {
				downloadFile(list.get(i));
			} catch (ConnectException e) {
				logger.error(e.getMessage(),e);
			}
		}
		downFileByList();
	}

	public static void downloadFile(String url) throws ConnectException {
		if (StringUtils.isEmpty(url))
			return;
		try {
			URL u = new URL(url);
			URLConnection c = u.openConnection();
			c.connect();
			InputStream is = c.getInputStream();
			if (url.indexOf("?") > -1) {
				url = url.substring(0, url.indexOf("?"));
			}
			String filename = url.substring(url.lastIndexOf("/") + 1);
			filename = filename.replaceAll("[\\-\\:：\\<>|\\?]", "_")
					.replaceAll("\\s", "");
			filename = getCommonProp("downloadpath") + filename;
			OutputStream os = new FileOutputStream(filename);
			int tmp = 0;
			while ((tmp = is.read()) != -1) {
				os.write(tmp);
			}
			os.close();
			is.close();
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}  catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	public static String getCommonProp(String key) {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(MyResourceBundle
					.getResourcesAbsolutePath("conf/common.properties")));
			return prop.get(key).toString();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(),e);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		return "";
	}
}
