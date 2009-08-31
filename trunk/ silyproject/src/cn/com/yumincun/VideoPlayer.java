package cn.com.yumincun;

import java.awt.BorderLayout;

import java.awt.Component;

import java.awt.Dimension;

import java.awt.Frame;

import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;

import java.io.IOException;

import java.net.URL;

import javax.media.ControllerEvent;

import javax.media.ControllerListener;

import javax.media.EndOfMediaEvent;

import javax.media.Manager;

import javax.media.NoPlayerException;

import javax.media.Player;

import javax.media.PrefetchCompleteEvent;

import javax.media.RealizeCompleteEvent;

import javax.media.Time;

public class VideoPlayer extends Frame implements ControllerListener {

    private int videoWidth = 0;

    private int videoHeight = 0;

    private int controlHeight = 30;

    private int insetWidth = 10;

    private int insetHeight = 30;

    private Player player;

    private Component visual;               //播放组件

    private Component control = null;       //控制组件

    public VideoPlayer() {

        super("视频播放器");

        setSize(500, 400);

        setVisible(true);

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {

                if (player != null) {

                    player.close();

                }

                System.exit(0);

            }

        });

    }

    public void play() {

        URL url = null;

        try {

            //准备一个要播放的视频文件的URL

            url = new URL("file:/D:/MOV01427.MPG");

            //通过调用Manager的createPlayer方法来创建一个Player的对象

            //这个对象是媒体播放的核心控制对象

            player = Manager.createPlayer(url);

        } catch (NoPlayerException e1) {

            e1.printStackTrace();

        } catch (IOException e1) {

            e1.printStackTrace();

        }

        //对Player对象注册监听器，能在相关事件发生的时候执行相关的动作

        player.addControllerListener(this);

        //让Player对象进行相关的资源分配

        player.realize();

    }

    //监听Player的相关事件

    public void controllerUpdate(ControllerEvent ce) {

        if (ce instanceof RealizeCompleteEvent) {

            //Player实例化完成后进行Player播放前预处理

            player.prefetch();

        } else if (ce instanceof PrefetchCompleteEvent) {

            if (visual != null)

                return;

            //取得Player中的播放视频的组件，并得到视频窗口的大小

            //然后把视频窗口的组件添加到Frame窗口中，

            if ((visual = player.getVisualComponent()) != null) {

                Dimension size = visual.getPreferredSize();

                videoWidth = size.width;

                videoHeight = size.height;

                add(visual);

            } else {

                videoWidth = 320;

            }

            //取得Player中的视频播放控制条组件，并把该组件添加到Frame窗口中

            if ((control = player.getControlPanelComponent()) != null) {

                controlHeight = control.getPreferredSize().height;

                add(control, BorderLayout.SOUTH);

            }

            //设定Frame窗口的大小，使得满足视频文件的默认大小

            setSize(videoWidth + insetWidth, videoHeight + controlHeight

                    + insetHeight);

            validate();

            //启动视频播放组件开始播放

            player.start();

        } else if (ce instanceof EndOfMediaEvent) {

            //当播放视频完成后，把时间进度条恢复到开始，并再次重新开始播放

            player.setMediaTime(new Time(0));

            player.start();

        }

    }

    public static void main(String[] args) {

        VideoPlayer vp = new VideoPlayer();

        vp.play();

    }

}

