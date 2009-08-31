package cn.com.yumincun;

import java.io.ByteArrayOutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

class CaptureThread extends Thread {

	AudioFormat audioFormat;                   //音频格式

	ByteArrayOutputStream byteArrayOutputStream;    //录音数据对象

	int totaldatasize = 0;                          //录音数据大小

	TargetDataLine targetDataLine;                  //输入设备

	AudioInputStream audioInputStream;              //播放数据对象

	SourceDataLine sourceDataLine;             //输出设备

	boolean stopCapture = false;
	//临时数组

    byte tempBuffer[] = new byte[10000];

    public void run() {

        byteArrayOutputStream = new ByteArrayOutputStream();

        totaldatasize = 0;

        stopCapture = false;

        try {//循环执行，直到按下停止录音按钮

            while (!stopCapture) {

                //读取10000个数据

                int cnt = targetDataLine.read(tempBuffer, 0,tempBuffer.length);

                if (cnt > 0) {

                    //保存该数据

                    byteArrayOutputStream.write(tempBuffer, 0, cnt);

                    totaldatasize += cnt;

                }

            }

            byteArrayOutputStream.close();

        } catch (Exception e) {

            e.printStackTrace();

            System.exit(0);

        }

    }

}
