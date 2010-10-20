package comm.task;

import comm.SpringFactory;
import dms.dao.BaseDao;
import java.util.List;

public class LogTask implements Runnable {
    private List logList;
    public LogTask() {
    }

    public void run() {
        if (logList != null) {
            BaseDao dao = (BaseDao) SpringFactory.getInstance().getBean(
                    "baseDao");
            for (Object log : logList) {
                dao.save(log);
            }
        }
    }

    public void setLogList(List logList) {
        this.logList = logList;
    }

    public List getLogList() {
        return logList;
    }

}
