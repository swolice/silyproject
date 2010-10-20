/*
 * WorkerRunable.java
 * 创建日期：2008/12/16
 */
package sample;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * @author 蒋峰
 * 工作业务流程执行线程
 */
public class WorkerRunable implements Runnable {
	//系统日志文件
	private Logger syslogger = LogManager.getRootLogger();//TraceDebugLogger.getLogger(LogLabConst.CRM_THREAD);
	//业务系统处理逻辑
	private ILogic processLogic;
	/**
	 * 
	 */
	public WorkerRunable() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 设置即将要执行的业务逻辑接口
	 * @param processLogic
	 */
    public void setBussinessLogic(ILogic processLogic) {
        this.processLogic = processLogic;
     }
    public ILogic getBussinessLogic() {
        return this.processLogic;
     }
    /**
     * 
     */
	public void run() {
		syslogger.info(" run() start");
    	try {
    		syslogger.info(processLogic);
    		processLogic.process();
    	} catch(Exception ex) {
    		syslogger.error(ex.getMessage(), ex);
    	}
    	syslogger.info(" run() end");
	}
}
