package comm.task;

import comm.bean.ClientInfo;
import util.Logger;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.IOException;

public class ClientInfoTask implements Runnable {
    private ClientInfo clientInfo;
    public ClientInfoTask(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public void run() {
        String mac = getMACAddress(clientInfo.getIp());
        Logger.getLogger().debug("mac=" + mac);
        clientInfo.setMac(mac);
    }

    // 获得IP的MAC地址
    public static String getMACAddress(String ipAddress) {
        String str = "", strMAC = "", macAddress = "";
        try {
            Process pp = Runtime.getRuntime().exec("nbtstat -a " + ipAddress);
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    if (str.indexOf("MAC Address") > 1) {
                        strMAC = str.substring(str.indexOf("MAC Address") + 14,
                                               str.length());
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            return "Can't Get MAC Address!";
        }
        //
        if (strMAC.length() < 17) {
            // Logger.getLogger().debug(strMAC);
            return "Error!";
        }

        macAddress = strMAC.substring(0, 2) + ":" + strMAC.substring(3, 5)
                     + ":" + strMAC.substring(6, 8) + ":" +
                     strMAC.substring(9, 11)
                     + ":" + strMAC.substring(12, 14) + ":"
                     + strMAC.substring(15, 17);
        //
        return macAddress;
    }


}
