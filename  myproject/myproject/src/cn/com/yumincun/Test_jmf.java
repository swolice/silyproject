package cn.com.yumincun;

import javax.media.CaptureDeviceInfo;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.format.AudioFormat;

import jmapps.util.StateHelper;

import java.io.IOException;
import java.util.Vector;

public class Test_jmf {
    
    public Test_jmf()
    {
    
    }
    
    public static void main(String[] args)
    {
        CaptureDeviceInfo di =null;
        javax.media.Processor pro =null;
        javax.media.Player player=null;
        StateHelper sh =null;
        
        Vector<CaptureDeviceInfo> driverList = javax.media.CaptureDeviceManager.getDeviceList(new AudioFormat(AudioFormat.LINEAR,44100,16,2));
        
        if(driverList.size()>0)
        {
            di = driverList.lastElement();
            System.out.println(di.getName());
        }
        else
        {
            System.out.println("检查不到耳麦");
            System.exit(-1);
        }
        
        try 
        {
            player = Manager.createPlayer(di.getLocator());
            player.start();
        } catch (NoPlayerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}