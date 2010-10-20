package comm.filter.counter;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;

import comm.bean.ClientInfo;
import dms.data.AccessLog;
import comm.Ini;
import comm.SpringFactory;
import dms.dao.BaseDao;

public class LogTask implements Runnable {
	private Long accessId;
	private HttpSession hs;
	private static List<AccessLog> list = new ArrayList();
	private static int count = 0;
	private static final int max = 5;
	private static final int times = 2;

	public LogTask(Long accessId, HttpSession hs) {
		this.accessId = accessId;
		this.hs = hs;
	}

	public void run() {
		try {
			process(hs, accessId);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static synchronized void process(HttpSession hs, Long accessId)
			throws InterruptedException {
		for (int i = 0; i < times; i++) {
			ClientInfo info = (ClientInfo) hs
					.getAttribute(Ini.SESSION_CLIENTINFO);
			if (info != null && info.getMac() != null) {
				AccessLog log = new AccessLog();
				log.setAccessId(accessId);
				log.setIp(info.getIp());
				log.setMac(info.getMac());
				log.setColor(info.getColor());
				log.setBrowserLanguage(info.getBrowserLanguage());
				log.setBrowserType(info.getBrowserType());
				log.setSystemLanguage(info.getSystemLanguage());
				log.setSystemType(info.getSystemType());
				log.setCpuClass(info.getCpuClass());
				log.setResolution(info.getResolution());
				log.setCreateTime(new Date());
				list.add(log);
				count++;
				if (count >= max) {
					save();
				}
				return;
			} else {
				Thread.sleep(5000);
			}
		}
	}

	private static void save() {
		try {
                    BaseDao dao = (BaseDao) SpringFactory.getInstance().getBean(
                    "baseDao");
			for (AccessLog log : list) {
				dao.save(log);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		list = new ArrayList();
		count = 0;
	}
}
