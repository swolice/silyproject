package cn.com.yumincun;

import java.io.ByteArrayOutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

class PlayThread extends Thread {

	AudioFormat audioFormat;                   //音频格式

	ByteArrayOutputStream byteArrayOutputStream;    //录音数据对象

	int totaldatasize = 0;                          //录音数据大小

	TargetDataLine targetDataLine;                  //输入设备

	AudioInputStream audioInputStream;              //播放数据对象

	SourceDataLine sourceDataLine;             //输出设备

	boolean stopCapture = false;                    //控制录音标志


	byte tempBuffer[] = new byte[10000];

    public void run() {

        try {

            int cnt;

            //读取数据到缓存数据

            while ((cnt = audioInputStream.read(tempBuffer, 0,

                    tempBuffer.length)) != -1) {

                if (cnt > 0) {

                    //写入缓存数据

                    sourceDataLine.write(tempBuffer, 0, cnt);

                }

            }

            //Block等待临时数据被输出为空

            sourceDataLine.drain();

            sourceDataLine.close();

        } catch (Exception e) {

            e.printStackTrace();

            System.exit(0);

        }

    }

}

