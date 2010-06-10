package sily.listener;

import java.util.TimerTask;

import javax.servlet.ServletContext;

import sily.monitor.FtpServerLogic;
import sily.monitor.SendEmail;

public class FtpTask extends TimerTask {
//	private static final int C_SCHEDULE_HOUR = 0;
	private static boolean isRunning = false;
	private ServletContext context = null;
	private static final FtpServerLogic ftpServ = new FtpServerLogic();
	
	public FtpTask(ServletContext context) {
		this.context = context;
	}

	public void run() {
		//Calendar cal = Calendar.getInstance();
		if (!isRunning) {
			//设置时间段执行
//			if (C_SCHEDULE_HOUR == cal.get(Calendar.HOUR_OF_DAY)) {
				isRunning = true;
				//context.log("开始执行指定任务");
				// TODO 添加自定义的详细任务，以下只是示例
				// 系统定时发送邮件
				context.log("------上传文件开始------");
				new SendEmail().sendMonitorPhoto();
//				ftpServ.uploadMonitorImg();
				context.log("------上传文件结束------");

				isRunning = false;
				//context.log("指定任务执行结束");
//			}
		} else {
			context.log("上一次任务执行还未结束");
		}
	}

}
