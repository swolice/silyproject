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

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class CameraPhoto {

	private MediaLocator mediaLocator = null;

	private Player player = null;

	public static void main(String[] args) {
		new CameraPhoto().createPhoto();
	}

	public void createPhoto() {
		CaptureDeviceManager
				.getDevice("vfw:Microsoft WDM Image Capture (Win32):0");

		mediaLocator = new MediaLocator("vfw://0");

		try {

			player = Manager.createRealizedPlayer(mediaLocator);

			player.start();

			Component comp;

			if ((comp = player.getVisualComponent()) != null) {

				comp.setBounds(0, 0, 250, 300);

				while (true) {

					FrameGrabbingControl fgc = (FrameGrabbingControl) player
							.getControl("javax.media.control.FrameGrabbingControl");

					Buffer buffer = fgc.grabFrame();

					BufferToImage bufferToImage = new BufferToImage(
							(VideoFormat) buffer.getFormat());
					Image image = bufferToImage.createImage(buffer);
					
					if (null == image) {
						Thread.sleep(1000);
						// System.out.println("length " + buffer.getLength());
					} else {
						String filePath = "f:/photo/"+ System.currentTimeMillis() + ".jpg";
						File file = new File("f:/photo");
						if(!file.exists()){
							file.mkdirs();
						}
						saveImage(image, filePath);
					}
					try {
						Thread.sleep(10*1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	// 保存图片

	public void saveImage(Image image, String path) {

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

		} catch (Exception e) {

			e.printStackTrace();

		}

	}
}
