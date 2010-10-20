package comm.listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import comm.bean.ClientInfo;
import util.Logger;
import comm.Ini;
import java.util.HashMap;

public class SessionListener implements HttpSessionListener,
        ServletContextListener {
    private static ServletContext sc;
    private static int count = 0;

    public void sessionCreated(HttpSessionEvent arg0) {
        count++;
        sc.setAttribute("count", count);
    }

    public void sessionDestroyed(HttpSessionEvent arg0) {
        count--;
        sc.setAttribute("count", count);

        if (arg0.getSession() != null) {
            try {
//                UserInfo info = (UserInfo) arg0.getSession().getAttribute(Ini.
//                        SESSION_USER);
//                if (info != null) {
//                    SessionManager.userLogout(info.getMember().getId());
//                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        Logger.getLogger().warn("Context has colse...");
//        CacheUtil.putInCache(MsgConfig.CACHE_ONLINE, new HashMap(), 0);
//        CacheUtil.putInCache(MsgConfig.CACHE_FAMILY_ONLINE, new HashMap(), 0);
    }

    public void contextInitialized(ServletContextEvent sce) {
        Logger.getLogger().warn("Context has startup...");
        System.out.println("Context has startup...");
        sc = sce.getServletContext();

//        CacheUtil.putInCache(MsgConfig.CACHE_ONLINE, new HashMap(), 0);
//        CacheUtil.putInCache(MsgConfig.CACHE_FAMILY_ONLINE, new HashMap(), 0);

        Logger.getLogger().info("online cache cleared .");
        System.out.println("online cache cleared .");

    }

    public ServletContext getServletContext() {
        return sc;
    }
}
