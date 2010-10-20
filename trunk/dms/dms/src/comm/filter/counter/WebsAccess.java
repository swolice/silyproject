package comm.filter.counter;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import comm.SpringFactory;
import comm.ThreadFactory;
import comm.bean.ClientInfo;
import util.Configer;
import util.Logger;
import dms.data.AccessConfig;
import dms.dao.BaseDao;

public class WebsAccess {
    private static WebsAccess instance;
    private Map<String, AccessConfig> map;

    private WebsAccess() {
        map = new HashMap();
        init();
    }

    public synchronized static WebsAccess getInstance() {
        if (instance == null) {
            instance = new WebsAccess();
        }
        return instance;
    }

    public void init() {
        try {

            String hql = "from AccessConfig as a where a.activeFlag=0";
            BaseDao dao = (BaseDao) SpringFactory.getInstance().getBean(
                    "baseDao");

            List<AccessConfig> list = dao.listByHql(hql, null, -1, -1);

            for (AccessConfig config : list) {
                String url = config.getUrl();
                Logger.getLogger().debug("Access Configer url " + url);
                map.put(url, config);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void process(HttpServletRequest req) {
        String uri = req.getRequestURI();
        if (uri.matches("(.+/)")) {
            uri += "index.jsp";
        }
        if (req.getQueryString() != null) {
            uri += "?" + req.getQueryString();
        }
        // Logger.getLogger().debug("URI=" + uri);
        if (map.get(uri) != null) {
            ThreadFactory.getThreadPool().execute(
                    new LogTask(map.get(uri).getId(), req.getSession()));
        }
    }

    public static void main(String[] args) {
        WebsAccess.getInstance();
    }
}
