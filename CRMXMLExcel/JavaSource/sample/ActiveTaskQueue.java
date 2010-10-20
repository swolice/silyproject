/*
 * 创建日期：2010-06-10
 * ActiveTaskQueue.jav
 */
package sample;

import java.util.Stack;

/**
 * 用于并行处理任务队列管理
 * 
 * @author 蒋峰
 */
public class ActiveTaskQueue {
	/**
	 * 任务队列堆栈
	 */
	private Stack _queue;
	/**
	 * 
	 */
	public ActiveTaskQueue() {
        _queue = new Stack();
    }
	/**
	 * 增加并行任务队列
	 * @param work 工作队列
	 */
    public  void AddQueue(Runnable work) {
    	synchronized(_queue) {
	        _queue.push(work);
	        _queue.notifyAll();
    	}
    }
    /**
     * 获取工作队列
     * @return
     */
    public  Runnable PopQueue() {
    	Runnable workrun;
    	synchronized(_queue)  {
	        while(_queue.empty()) {
	            try {
	            	_queue.wait();
	            }catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	        workrun = (Runnable)_queue.pop();
	        _queue.notifyAll();
    	}
    	return workrun;
    }   
}
