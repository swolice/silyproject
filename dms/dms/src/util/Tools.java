package util;

import java.util.Date;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import java.io.*;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import comm.SpringFactory;
import comm.ThreadFactory;
import java.util.concurrent.locks.Condition;

public class Tools {

    // ����Զ����ʽ�ĵ�ǰʱ��
    public static String getFormatTime(String format) {
        Date currentDate = new Date();
        DateFormat df = new SimpleDateFormat(format);
        String date = df.format(currentDate);
        return date;
    }

    // ��õ�ǰʱ��yyyy-MM-dd HH:mm:ss
    public static String getCurrentTime() {
        Date currentDate = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(currentDate);
        return date;
    }

    // ��õ�ǰ����yyyy-MM-dd HH:mm:ss
    public static String getCurrentDate() {
        Date currentDate = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(currentDate);
        return date;
    }

    // ��õ�ǰʱ��yyyyMMddHHmmss
    public static String getGeneralTime() {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(
                "yyyyMMddHHmmss", Locale.ENGLISH);
        Calendar localtime = Calendar.getInstance();
        String s = bartDateFormat.format(localtime.getTime());
        return s;

    }

    public static String getRandom() {
        Random r = new Random();
        String rand = String.valueOf(r.nextInt(10000000));
        return rand;
    }

    // public static String getSequence() {
    // String current_time = Tools.getGeneralTime();
    // Random r = new Random();
    // String random = String.valueOf(r.nextInt(9000) + 1000);
    // current_time = current_time + random;
    // int times = 30 - current_time.length();
    // for (int i = 0; i < times; i++) {
    // current_time = current_time + "0";
    // }
    // return current_time;
    // }
    // ��ñ���IP
    private String getHostIp() {
        InetAddress inet = null;
        try {
            inet = InetAddress.getLocalHost();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return inet.getHostAddress();
    }

    public static String getStringISO(String str) {
        try {
            if (str == null || str.trim().equals("")
                || str.trim().equals("null")) {
                str = "";
            } else {
                str = new String(str.trim().getBytes("iso_8859_1"), "gb2312");
            }
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return str;
    }

    // �������к�
    public static synchronized String getSequence() {
        String valueBeforeMD5 = "";
        String valueAfterMD5 = "";
        MessageDigest md5 = null;
        StringBuffer sbValueBeforeMD5 = new StringBuffer();
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error: " + e);
            return valueBeforeMD5;
        }
        Random myRand = new Random();
        long time = System.currentTimeMillis();
        long rand = 0L;
        rand = myRand.nextLong(); // �������ʱ��������,�õ��ľͲ�һ��.
        sbValueBeforeMD5.append(Long.toString(time));
        sbValueBeforeMD5.append(":");
        sbValueBeforeMD5.append(Long.toString(rand));
        valueBeforeMD5 = sbValueBeforeMD5.toString();
        md5.update(valueBeforeMD5.getBytes());
        byte array[] = md5.digest();
        String strTemp = "";
        for (int i = 0; i < array.length; i++) {
            strTemp = Integer.toHexString(array[i] & 0xff);
            if (strTemp.length() == 1) {
                valueAfterMD5 = valueAfterMD5 + "0" + strTemp;
            } else {
                valueAfterMD5 = valueAfterMD5 + strTemp;
            }
        }
        return valueAfterMD5.toUpperCase();
    }

    // ����Ŀ¼
    public static void createDir(String path) {
        File dir = new File(path);
        if (!dir.exists())
            dir.mkdirs();
    }

    // ɾ��Ŀ¼
    public static void delDir(String path) {
        // System.out.println(path);
        File directory = new File(path);
        if (!directory.exists()) {
            Logger.getLogger().error(directory.toString() + " do not exist!");
        }
        String[] filelist = directory.list();
        File tmpFile = null;
        for (int i = 0; i < filelist.length; i++) {
            tmpFile = new File(directory.getAbsolutePath(), filelist[i]);
            if (tmpFile.isDirectory()) {
                delDir(tmpFile.getPath());
            } else if (tmpFile.isFile()) {
                tmpFile.delete();
            }
        }
        directory.delete();

    }

    // ɾ���ļ�
    public static void delFile(String path, String fileName) {
        File file = new File(path + fileName);
        if (!file.exists()) {
            Logger.getLogger().error(file.toString() + " do not exist!");
            return;
        }
        file.delete();
    }

    // ���IP��MAC��ַ
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

//        // ת����ý���ʽ��flv�����̵߳���
//        public static boolean formatMedia(String source, String destination,
//                        MediaConfig config) throws Exception {
//                MediaTask task = new MediaTask(source, destination, config);
//
//                try {
//                        ThreadFactory.getThreadPool().execute(task);
//                        Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                } catch (Exception ex) {
//                        ex.printStackTrace();
//                        throw ex;
//                }
//                return task.isSuccess();
//        }



    // ת����ý���ʽ��flv
    /**
     * -b ������ �����ļ������� (��λ��kbit/s) -r �ٶ� ���ٶ� (Hz value) -s ��С ���ô�С����˸� -ab
     * bitrate �������ֵı�����(��λ��kbit/s) -ar ������ ������Ƶ������ (��λ��Hz)
     * mov,swf,avi,mp4,wav,rm
     *
     * @throws Exception
     */
    public static boolean formatMedia(String source, String destination,
                                      String ab, String ar, String bit,
                                      String rate, String size) throws
            Exception {
        String path = new Tools().getClass().getClassLoader().getResource("")
                      .getPath()
                      + "bin/";
        // String path = MjConfiger.getInstance().getProperty("ffmepg_path","");

        Boolean isSuccess = false;
        File sourceFile = new File(source);
        if (!sourceFile.exists()) {
            isSuccess = false;
            throw new Exception("û��Դ�ļ���");
        }
        File file = new File(destination);
        // ���Ŀ���ļ�Ŀ¼������
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }

        // �����ļ�����
        Pattern pattern = Pattern.compile("(gif|swf|wav|avi|mp4)",
                                          Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(source);
        if (!matcher.find()) {
            throw new Exception(source + "��֧���ļ���ʽ��");
        }

        StringBuffer sb = new StringBuffer();

        String OSType = System.getProperty("os.name", "");
        int index = -1;
        index = OSType.toLowerCase().indexOf("windows");
        if (index > -1) {
            sb.append(path);
        }
        sb.append("ffmpeg -y"); // ����ͬ���ļ�
        sb.append(" -i " + source);
        sb.append(" -ab " + ab);
        sb.append(" -ar " + ar);
        sb.append(" -b " + bit);
        sb.append(" -r " + rate);
        sb.append(" -s " + size);
        sb.append(" " + destination);
        Logger.getLogger().debug(sb.toString());

        try {
            String str = "";
            Process pp = Runtime.getRuntime().exec(sb.toString());
            pp.getInputStream();
            InputStreamReader ir = new InputStreamReader(pp.getErrorStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (int i = 1; i < 50; i++) {
                str = input.readLine();
                if (str != null) {
                    str = str.toLowerCase();
                    // System.out.println(str);
                    if (str.indexOf("unsupported") != -1
                        || str.indexOf("error") != -1
                        || str.indexOf("unable") != -1) {
                        return false;
                    }
                }
            }
            isSuccess = true;
        } catch (IOException ex) {
            ex.printStackTrace();
            isSuccess = false;
            throw new RuntimeException(ex.getMessage());
        } finally {
            sourceFile.delete();
            sourceFile = null;
            return isSuccess;
        }
    }

    // �����ļ����÷������ر�in
    public static boolean saveFile(InputStream in, String path, String fileName) {
        boolean isSuccess = true;
        // //����·��
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(path + fileName);
            int readBytes = 0;
            byte buffer[] = new byte[8192];
            while ((readBytes = in.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, readBytes);
            }

        } catch (Exception e) {
            isSuccess = false;
            Logger.getLogger().error( e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return isSuccess;
    }

    // ��ȡ�ı��ļ�����
    public static String readFile(String filePath) {
        String content = "";
        try {
            File file = new File(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file)));
            String data = null;
            while ((data = br.readLine()) != null) {
                content += data;
            }
            br.close();
        } catch (Exception e) {
            Logger.getLogger().error("��ȡ�ļ�ʱ�������" + e.getMessage());
            e.printStackTrace();
        }
        return content;
    }

    /**
     * ����ת������
     *
     * @param s
     *            String
     * @return Date
     */
    public static Date stringToDate(String s) {
        String pattern = "yyyy-MM-dd";
        return stringToDate(s, pattern);
    }

    public static Date stringToDate(String s, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(s);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String dateToString(Date date) {
        String pattern = "yyyy-MM-dd";
        return dateToString(date, pattern);
    }

    public static String dateToString(Date date, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // �ַ�ȥ��
    public static String dn(String str) {
        return (str == null || str.equals("null")) ? "" : str;
    }

    public static int mn(String num) {
        return (num == null || num.equals("")) ? 0 : Integer.valueOf(num);
    }

    public static String[] str2Arr(String str) {
        StringTokenizer tk = new StringTokenizer(str, ",");
        String[] arr = new String[tk.countTokens()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = tk.nextToken();
        }
        return arr;
    }

    public static int caculateDay(Date startday, Date endday) {

        // if(startday.after(endday)){
        // Date cal=startday;
        // startday=endday;
        // endday=cal;
        // }
        long sl = startday.getTime();
        long el = endday.getTime();
        long ei = el - sl;
        return (int) (ei / (1000 * 60 * 60 * 24));

    }


//        public static Boolean convertMedia(InputStream is, String suffix,
//                                           String destPath,
//                                           String destName, MediaConfig config) throws
//                Exception {
//            String tmpName = Tools.getSequence() + suffix;
//            String relativePath = Configer.getInstance().getProperty("upload_temp",
//                    "/upload/temp/");
//            String tempPath = new Tools().getClass().getClassLoader().getResource(
//                    "")
//                              .getPath();
//            try {
//                tempPath = tempPath.substring(0, tempPath.length() - 1) +
//                           relativePath;
//            } catch (Exception ex1) {
//                tempPath = relativePath;
//            }
//
//            final File unFormatedFile = new File(tempPath + tmpName);
//            Logger.getLogger().debug("tmpPath: " + tempPath);
//            Logger.getLogger().debug("tmpName: " + tmpName);
//            boolean tmpFileSaveSuccess = false;
//            try {
//                tmpFileSaveSuccess = Tools.saveFile(is, tempPath, tmpName);
//            } catch (Exception ex) {
//                Logger.getLogger().debug("�����ϴ��ļ���?", ex);
//                throw new Exception("�����ϴ��ļ���? " + ex.getMessage());
//            }
//            if (tmpFileSaveSuccess) {
//                File formatedFile = new File(destPath + destName);
//                boolean formatFlag = false;
//                try {
//                    formatFlag = Tools.formatMedia(unFormatedFile.
//                                                   getAbsolutePath(),
//                                                   formatedFile.getAbsolutePath(),
//                                                   config);
//                } catch (Exception ex) {
//                    Logger.getLogger().debug("ת���ļ���ʽ��?", ex);
//                    throw new Exception("ת���ļ���ʽ��? " + ex.getMessage());
//                }
//                return formatFlag;
//            } else {
//                Logger.getLogger().debug("�����ϴ��ļ���?");
//                throw new Exception("�����ϴ��ļ���?");
//            }
// }


//    public static void writeBusinessLog(String logType, String result,
//                    String typeCode, String ip, String sysId, String operatorId,
//                    String content) throws Exception {
//            BusinessLogger logger = (BusinessLogger) SpringFactory.getInstance()
//                            .getBean("businessLogger");
//            logger.setContent(content);
//            logger.setLogType(Integer.valueOf(logType));
//            logger.setResult(Integer.valueOf(result));
//            logger.setOperatorId(new Long(operatorId));
//            logger.setTypeCode(typeCode);
//            logger.setIp(ip);
//            logger.setSysId(Integer.valueOf(sysId));
//            logger.save();
//	}

    public static void main(String[] arg) throws Exception {
        // //System.out.println(Tools.getTimeStamp().length());
        // System.out.println(Tools.getSpeaceTime(0));
        // System.out.println(Tools.getPerviousDay());
        // System.out.println(Tools.getMACAddress("192.168.16.108"));
        // System.out.println(Tools.getRandom());
        // try {
        // boolean b = Tools.formatMedia("c:\\load.avi", "c:\\a.flv",
        // "56",
        // "22050", "500", "15", "320x240");
        // System.out.println(b);
        // } catch (Exception e) {
        // // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // Date startday = new Date();
        // Date endday = Tools.stringToDate("2008-05-24");
        // int days = Tools.caculateDay(startday, endday);
        // System.out.println(days);
    }
}
