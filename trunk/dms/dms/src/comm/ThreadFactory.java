package comm;

import java.util.concurrent.*;

import util.Configer;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class ThreadFactory extends ThreadPoolExecutor {
	private static ThreadFactory instance;

	public ThreadFactory(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				handler);
	}

	public synchronized static ThreadFactory getThreadPool() {
		if (instance == null) {
			Configer confinger = Configer.getInstance();
			int core = Integer.parseInt(confinger.getProperty("thread_core"));
			int max = Integer.parseInt(confinger.getProperty("thread_max"));
			int keepAliveTime = Integer.parseInt(confinger
					.getProperty("thread_keepAliveTime"));
			int workQueue = Integer.parseInt(confinger
					.getProperty("thread_workQueue"));
			instance = new ThreadFactory(core, max, keepAliveTime,
					TimeUnit.SECONDS, new ArrayBlockingQueue(workQueue),
					new ThreadPoolExecutor.CallerRunsPolicy());
		}
		return instance;
	}

	public static void main(String[] args) {
		ThreadFactory.getThreadPool();
	}
}
