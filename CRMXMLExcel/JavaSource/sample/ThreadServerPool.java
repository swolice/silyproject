package sample;

import java.util.Iterator;
import java.util.Stack;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
/**
 * 
 * @author 蒋峰
 * 系统并行处理线程连接池
 */
public class ThreadServerPool {
	private Logger syslogger = LogManager.getRootLogger();
	//线程连接池
    private  Stack threadPool = new Stack();
    private  Stack readyThreadPool = new Stack();
    //最大线程连接池树
    private static final int maxSize = 30;
    private boolean enabled;
    private int _activeThreads = 0;
    private static ThreadServerPool instance =null;
    private ActiveTaskQueue activeQueue ;
    public static ThreadServerPool getInstance() {
    	return instance;
    }
    public ThreadServerPool(){
        	if (instance != null) {
        		return;
        	}
        	activeQueue = new ActiveTaskQueue();;
        	instance = this;
        	createThread();
    }	
    
    private void createThread() {
    	//创建工作线程
    	for(int i=0;i<maxSize; i++) {
    		Worker workerThread = new Worker();
    		workerThread.setWorkerPool(instance);
    		workerThread.startup();
    		threadPool.push(workerThread);
    	}
    	MainWorker mainWorker  = new MainWorker();
    	mainWorker.serverPool = this;
    	mainWorker.setDaemon(true);
    	mainWorker.start();
    }
    /**
     * 获取可以执行的工作
     */
    private  Runnable getWorker() {
    	return activeQueue.PopQueue();
    }
    /**
     * 设置任务线程执行任务
     * @param work
     */
    public    void  run(Runnable work){
    	activeQueue.AddQueue(work);
    }
   
    /**
     * 
     * @param size
     */
    public void setMaximumSize(int size) {
//        maxSize = size;
    }  
    /**
     * 
     *
     */
    public synchronized void enable(){
        enabled = true;
    }
    protected void release() {
    }
    
    public synchronized void finishedWork(Worker t) {
    	threadPool.push(t);
    }    
    /**
     * 释放工作线程
     *
     */
    protected void dispose(){
        for (Iterator i = threadPool.iterator(); i.hasNext(); ) {
            Worker w = (Worker)i.next();
            w.terminate();
        }
    }
    /**
     * 删除线程池中的线程
     */
    public synchronized void disable(){}    
    
    private class MainWorker extends Thread {
    	ThreadServerPool serverPool;
    	MainWorker(){};
    	 public void run() {
	        Runnable workRun = null;
	        do {
	        	
	        	workRun = serverPool.getWorker();
	        	
		        if (workRun != null) {
		        	if (threadPool.isEmpty()) {
		        		instance.run(workRun);
		        	} else {
		        		Worker worker = (Worker) threadPool.pop();
		        		if (worker.isWorking()) {
		        			worker.noticeWorker();
		        			instance.run(workRun);
		        			syslogger.info("工作回归");
		        		} else {
		        			worker.execute(workRun);
		        		}
		        		
		        	}
		        }
		       
	        } while (true);
    	 }
    }

}
