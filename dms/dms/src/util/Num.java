package util;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class Num extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 response.setHeader("Pragma", "no-cache");
	     response.setHeader("Cache-Control", "no-cache");
	     HttpSession session = request.getSession(true);
	     response.setContentType("image/jpeg");
	     ServletOutputStream out = response.getOutputStream();
	     BufferedImage image = new BufferedImage(50, 20, 1);
	     Graphics g = image.getGraphics();
	     g.setColor(new Color(255,255,255));
	     g.fillRect(0, 0, 50, 25);
	   //g.setFont(new Font("Times", 0, 15));
	   //g.setColor(new Color(0,0,0));
	     Random random;
   	     random = new Random();
   	     String num="";
   	         Dr(g,random);
	         Font f= new Font("Times",1,15);
	         g.setFont(f);

	         for(int i=0;i<4;i++){
	        	 g.setColor(new Color(random.nextInt(150),random.nextInt(150),random.nextInt(150)));
	        	 int s=random.nextInt(10);

	         g.drawString(String.valueOf(s),3+12*i,20);
	         num+=String.valueOf(s);
	         }




	    session.setAttribute("CheckCode", num);
	     g.dispose();
	     JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
	     encoder.encode(image);
	     out.close();
	}


	 public void doPost(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException
 {
     doGet(request, response);
 }
private  void Dr(Graphics g,Random r){

// for(int i=0;i<50;i++){
//	  g.setColor(new Color(r.nextInt(100)+155,r.nextInt(100)+155,r.nextInt(100)+155));
//   	  g.drawRect(r.nextInt(50),r.nextInt(80),r.nextInt(50),r.nextInt(80));
//   	 g.drawLine(r.nextInt(50),r.nextInt(80),r.nextInt(50),r.nextInt(80));
// }
//    g.setColor(new Color(100,100,100));
//             g.drawLine(0,12,50,12);
//             g.drawLine(0,14,50,14);
//               g.drawLine(0,16,50,16);

}

}
