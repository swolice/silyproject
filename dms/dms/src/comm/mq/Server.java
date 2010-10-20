package comm.mq;

import comm.SpringFactory;
import com.lr.lightmessage.MessageToolFactory;

public class Server {
    public Server() {
    }

    public static void main(String[] args) {
        while (true) {
            try{

                MessageToolFactory tool = (MessageToolFactory) SpringFactory.
                                          getInstance().getBean("msgTool");

                Object obj = tool.getTool("SystemAdmin").receive();
                if (obj != null && obj.toString().equals("SystemExit")) {
                    System.exit(0);
                }



                Thread.sleep(1000);


            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

};
