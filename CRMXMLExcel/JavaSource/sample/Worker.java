/*
 * 创建日期： 2010-6-10
 * Worker.java
 */
package sample;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 处理工作任务的工作线程
 * @author 蒋峰
 */
final class Worker extends Thread {
	private Logger syslogger = LogManager.getRootLogger();
  //线程工作标示
  private boolean stopFlag = false;
  //工作任务对象
  private Runnable workTask = null;
  //工作线程池
  private ThreadServerPool workerPool;

  /**
   * 初始化构造函数
   */
  public Worker (){

  }
  public void startup(){
    this.setDaemon(true);
    start();
  }
  /**
   * 设定工作任务
   * @param workrun  工作任务
   */
  public void setWorkTask(final Runnable workrun){
    synchronized (this){
      workTask = workrun;
      this.notifyAll();
    }
  }
  /**
   * 设定工作线程池
   * @param workerPool
   */
  public void setWorkerPool (final ThreadServerPool _workerPool) {
  	syslogger.info(_workerPool);
    this.workerPool = _workerPool;
  }
  public boolean isWorking() {
  	 if (workTask == null) {
  	 	return false;
  	 } 
  	 return true;
  }
  public void noticeWorker(){
    synchronized (this) {
      	notify();
      }
  }
  /**
   * 执行工作任务
   * @param runnable
   */
  public void execute(Runnable runnable) {
      this.workTask = runnable;
      if (!isAlive()) {
          start();
      } else {
          synchronized (this) {
          	notify();
          }
      }
  }
  /**
   * 终止工作线程
   */
  public void terminate() {
      stopFlag = true;
      interrupt();
  }
  /**
   * 运行工作任务
   */
  public void run() {
    for (; !stopFlag; ) {
    	if (workTask != null) {
    		 try {
    	        	workTask.run();
    	        } catch (Throwable e) {
    	        	e.printStackTrace();
    	            if (e instanceof ThreadDeath) {
    	                throw (ThreadDeath)e;
    	            }
    	        }
    	}
       
        workTask = null;
        //将工作线程回池
        workerPool.finishedWork(this);
        if (stopFlag) break;
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
    }
}

}
