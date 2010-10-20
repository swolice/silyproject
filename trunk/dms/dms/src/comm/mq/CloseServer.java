package comm.mq;

import comm.SpringFactory;
import com.lr.lightmessage.MessageToolFactory;

public class CloseServer {
    public CloseServer() {
    }

    public static void main(String[] args) {
        MessageToolFactory tool = (MessageToolFactory) SpringFactory.
                                  getInstance().getBean("msgTool");

        tool.getTool("SystemAdmin").send("SystemExit");

    }

}
