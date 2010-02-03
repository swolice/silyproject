package cn.com.yumincun;

import java.awt.Button;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.Buffer;
import javax.media.CaptureDeviceManager;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;

public class VideoCamera extends Frame implements ActionListener {

    private MediaLocator mediaLocator = null;

    private Player player = null;

    private ImagePanel imagePanel = null;

    private Button ok, cancel;

    public VideoCamera() {

        //主窗口

        super("视频捕捉");

        setSize(200, 400);

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {

                player.close();

                System.exit(0);

            }

        });

        //按钮面板

        Panel panel = new Panel();

        ok = new Button("拍 照");

        cancel = new Button("重拍");

        ok.addActionListener(this);

        cancel.addActionListener(this);

        panel.add(ok);

        panel.add(cancel);

        add(panel, "Center");

        //图片面板

        imagePanel = new ImagePanel();

        add(imagePanel, "South");

        //视频面板

        CaptureDeviceManager.getDevice("vfw:Microsoft WDM Image Capture (Win32):0");

        mediaLocator = new MediaLocator("vfw://0");

        try {

            player = Manager.createRealizedPlayer(mediaLocator);

            player.start();

            Component comp;

            if ((comp = player.getVisualComponent()) != null)

                comp.setBounds(0, 0, 300, 250);

            add(comp, "North");

        } catch (Exception e) {

            e.printStackTrace();

        }

        setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == ok) {

            FrameGrabbingControl fgc = (FrameGrabbingControl) player

                    .getControl("javax.media.control.FrameGrabbingControl");

            Buffer buffer = fgc.grabFrame();

            BufferToImage bufferToImage = new BufferToImage(

                    (VideoFormat) buffer.getFormat());

            Image image = bufferToImage.createImage(buffer);

            imagePanel.setImage(image);

            imagePanel.saveImage(image, "D:/app/"+System.currentTimeMillis()+".jpg");

        } else if (e.getSource() == cancel) {

            imagePanel.setImage(null);

        }

    }

    public static void main(String[] args) {

        new VideoCamera();

    }

}

