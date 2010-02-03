package cn.com.yumincun;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

class ImagePanel extends Panel {

    private static final long serialVersionUID = 1L;

    public Image myimg = null;

    public ImagePanel() {

        setLayout(null);

        setSize(240, 180);

    }

    public void setImage(Image img) {

        this.myimg = img;

        repaint();

    }

    //显示图片

    public void paint(Graphics g) {

        if (myimg != null) {

            g.drawImage(myimg, 0, 0, this);

        }

    }

    

    //保存图片

    public void saveImage(Image image, String path) {

        //图片缓存

        int width = image.getWidth(null);

        int height = image.getHeight(null);

        BufferedImage bi = new BufferedImage(width, height,

                BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = bi.createGraphics();

        g2.drawImage(image, null, null);

        //写入文件

        try {

            //打开文件

            FileOutputStream fos = new FileOutputStream(path);

            //JPG编码

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

