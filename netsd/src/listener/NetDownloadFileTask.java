package listener;

import java.util.TimerTask;

import javax.servlet.ServletContext;

import logic.JsoupLogic;

public class NetDownloadFileTask extends TimerTask {

	private static boolean isRunning = false;
	
	private ServletContext context = null;
	
	@Override
	public void run() {
		if (!isRunning) {
			//设置时间段执行
				isRunning = true;
				context.log("------网页图片抓取开始------");
				JsoupLogic.appStart();
				context.log("------网页图片抓取结束------");
				isRunning = false;
		} else {
			context.log("上一次任务执行还未结束");
		}
	}
	
	public NetDownloadFileTask(ServletContext context) {
		this.context = context;
	}

}
