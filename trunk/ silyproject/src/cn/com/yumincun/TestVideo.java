package cn.com.yumincun;

import javax.media.bean.playerbean.*;
import javax.media. *;
import java.awt.*;
 
public class TestVideo extends Frame
{
 
	public static Player player, player1;
	public Component comp, comp1;
	private boolean isend;
	MediaLocator ml,ml1;
 
    public  TestVideo()
    {
        setSize(new Dimension(600,400));
        setBackground(Color.white);
        setVisible(true);
        setLocation(20,70);
 	    TestVideo1();
   }
 
    public void paint(Graphics g){
	}
 
    public void TestVideo1()
    {
        try
	    {
	        ml = new MediaLocator("file:/D:/Program Files/Tencent/QQ2009/Users/16009413/FileRecv/Rachel.Stevens.-.[Knock.On.Wood].Live.avi");
		    ml1 = new MediaLocator("file:/F:/yanxue/bill/yanxue/相片/石林Ddl/MOV01427.MPG");
 
	        player = Manager.createRealizedPlayer(ml);
			player1 = Manager.createRealizedPlayer(ml1);
			player1.prefetch();
			player.prefetch();
 
			comp = player.getVisualComponent();
			comp1 = player1.getVisualComponent();
 
			add(comp);
            validate();
            add(comp1);
			validate();
 
		    player.start();
 
	   		try
		    {
				Thread.sleep(10420);
			} catch(Exception e) {}
 
	    } catch(Exception e) {}
		player1.start();
        comp1.setVisible(true);
        comp.setVisible(false);
        player.stop();
    }
 
    public static void main(String[] args)
    {
	   	new TestVideo();
	}
}
 
