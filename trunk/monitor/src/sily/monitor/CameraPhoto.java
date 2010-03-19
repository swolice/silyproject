package sily.monitor;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.media.Buffer;
import javax.media.CaptureDeviceManager;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;

import org.apache.log4j.Logger;

import sily.util.MonitorResourceBundle;
import sily.util.PropertyWrapper;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class CameraPhoto {

	Logger syslogger = Logger.getLogger(CameraPhoto.class.getName());

	private MediaLocator mediaLocator = null;

	private Player player = null;

	private static final CameraPhoto cameraPhone = new CameraPhoto();

	PropertyWrapper propWrapper = new PropertyWrapper();

	private CameraPhoto() {
		this.initCameraPhoto();
	}

	public static CameraPhoto getInstance() {
		return cameraPhone;
	}

	private void initCameraPhoto() {
		syslogger.info("initCameraPhoto start");
		CaptureDeviceManager
				.getDevice("vfw:Microsoft WDM Image Capture (Win32):0");

		mediaLocator = new MediaLocator("vfw://0");

		try {

			player = Manager.createRealizedPlayer(mediaLocator);

			player.start();

			Component comp;

			if ((comp = player.getVisualComponent()) != null) {

				comp.setBounds(0, 0, 250, 300);
			}
		} catch (Exception e) {

			syslogger.error(e.getMessage(), e);

		}
		try {
			propWrapper.loadProperty(MonitorResourceBundle
					.getResourseFilePath("monitor.properties"));
		} catch (Exception ex) {
			syslogger.error(ex.getMessage(), ex);
		}
		syslogger.info("initCameraPhoto end");
	}

	public void createPhoto() {
		FrameGrabbingControl fgc = (FrameGrabbingControl) player
				.getControl("javax.media.control.FrameGrabbingControl");

		Buffer buffer = fgc.grabFrame();

		BufferToImage bufferToImage = new BufferToImage((VideoFormat) buffer
				.getFormat());
		Image image = bufferToImage.createImage(buffer);

		if (null != image) {
			String filePath = propWrapper.getProperty("ftpSendFileDir")
					+ "/" + System.currentTimeMillis() + ".jpg";
			File file = new File(propWrapper
					.getProperty("FTP_UPLOAD_FILE_PATH"));
			if (!file.exists()) {
				file.mkdirs();
			}
			saveImage(image, filePath);
		}
	}

	// 保存图片

	private void saveImage(Image image, String path) {
		syslogger.info("保存图片 start" + path);

		// 图片缓存

		int width = image.getWidth(null);

		int height = image.getHeight(null);

		BufferedImage bi = new BufferedImage(width, height,

		BufferedImage.TYPE_INT_RGB);

		Graphics2D g2 = bi.createGraphics();

		g2.drawImage(image, null, null);

		// 写入文件

		try {

			// 打开文件

			FileOutputStream fos = new FileOutputStream(path);

			// JPG编码

			JPEGImageEncoder je = JPEGCodec.createJPEGEncoder(fos);

			JPEGEncodeParam jp = je.getDefaultJPEGEncodeParam(bi);

			jp.setQuality(1f, false);

			je.setJPEGEncodeParam(jp);

			je.encode(bi);

			fos.close();

			syslogger.info("保存图片 end");
		} catch (Exception e) {

			syslogger.error(e.getMessage(), e);

		}

	}
}
