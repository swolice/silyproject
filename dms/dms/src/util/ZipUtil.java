package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.tools.bzip2.CBZip2InputStream;
import org.apache.tools.bzip2.CBZip2OutputStream;

import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZInputStream;
import com.jcraft.jzlib.ZOutputStream;
import java.io.FileInputStream;

public class ZipUtil {

    /***
     * ѹ��GZip
     *
     * @param data
     * @return
     */
    public static byte[] gZip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(bos);
            gzip.write(data);
            gzip.finish();
            gzip.close();
            b = bos.toByteArray();
            bos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /***
     * ��ѹGZip
     *
     * @param data
     * @return
     */
    public static byte[] unGZip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            GZIPInputStream gzip = new GZIPInputStream(bis);
            byte[] buf = new byte[1024];
            int num = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((num = gzip.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, num);
            }
            b = baos.toByteArray();
            baos.flush();
            baos.close();
            gzip.close();
            bis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /***
     * ѹ��Zip
     *
     * @param data
     * @return
     */
    public static byte[] zip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ZipOutputStream zip = new ZipOutputStream(bos);
            ZipEntry entry = new ZipEntry("zip");
            entry.setSize(data.length);
            zip.putNextEntry(entry);
            zip.write(data);
            zip.closeEntry();
            zip.close();
            b = bos.toByteArray();
            bos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /***
     * ��ѹZip
     *
     * @param data
     * @return
     */
    public static byte[] unZip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            ZipInputStream zip = new ZipInputStream(bis);
            while (zip.getNextEntry() != null) {
                byte[] buf = new byte[1024];
                int num = -1;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while ((num = zip.read(buf, 0, buf.length)) != -1) {
                    baos.write(buf, 0, num);
                }
                b = baos.toByteArray();
                baos.flush();
                baos.close();
            }
            zip.close();
            bis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /***
     * ѹ��BZip2
     *
     * @param data
     * @return
     */
    public static byte[] bZip2(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            CBZip2OutputStream bzip2 = new CBZip2OutputStream(bos);
            bzip2.write(data);
            bzip2.flush();
            bzip2.close();
            b = bos.toByteArray();
            bos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /***
     * ��ѹBZip2
     *
     * @param data
     * @return
     */
    public static byte[] unBZip2(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            CBZip2InputStream bzip2 = new CBZip2InputStream(bis);
            byte[] buf = new byte[1024];
            int num = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((num = bzip2.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, num);
            }
            b = baos.toByteArray();
            baos.flush();
            baos.close();
            bzip2.close();
            bis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /**
     * ���ֽ�����ת����16�����ַ���
     *
     * @param bArray
     * @return
     */
    public static String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * ѹ������
     *
     * @param object
     * @return
     * @throws IOException
     */
  public static byte[] jzlib(byte[] object) {

    byte[] data = null;
    try {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      ZOutputStream zOut = new ZOutputStream(out,
                                             JZlib.Z_DEFAULT_COMPRESSION);
      DataOutputStream objOut = new DataOutputStream(zOut);
      objOut.write(object);
      objOut.flush();
      zOut.close();
      data = out.toByteArray();
      out.close();

    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return data;
  }

    /**
     * ��ѹ��ѹ��������
     *
     * @param object
     * @return
     * @throws IOException
     */
    public static byte[] unjzlib(byte[] object) {

        byte[] data = null;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(object);
            ZInputStream zIn = new ZInputStream(in);
            byte[] buf = new byte[1024];
            int num = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((num = zIn.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, num);
            }
            data = baos.toByteArray();
            baos.flush();
            baos.close();
            zIn.close();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void main(String[] args) throws Exception {
//  String s = "this is a test";
        long s;
        FileInputStream fin = new FileInputStream("c:/test.gif");

        byte[] src = new byte[fin.available()];
        fin.read(src);
        fin.close();
        System.out.println("org length : " + src.length);

        s = System.currentTimeMillis();
        byte[] b1 = zip(src);
//  System.out.println("zip:" + bytesToHexString(b1));
        System.out.println("zip length : " + b1.length);
        byte[] b2 = unZip(b1);
//  System.out.println("unZip:" + new String(b2));
        System.out.println("unzip length : " + b2.length);
        System.out.println("*************ziptime : " +
                           (System.currentTimeMillis() - s));

        s = System.currentTimeMillis();
        byte[] b3 = bZip2(src);
//  System.out.println("bZip2:" + bytesToHexString(b3));
        System.out.println("zip length : " + b3.length);
        byte[] b4 = unBZip2(b3);
//  System.out.println("unBZip2:" + new String(b4));
        System.out.println("unzip length : " + b4.length);
        System.out.println("*************ziptime : " +
                           (System.currentTimeMillis() - s));

        s = System.currentTimeMillis();
        byte[] b5 = gZip(src);
//  System.out.println("bZip2:" + bytesToHexString(b5));
        System.out.println("zip length : " + b5.length);
        byte[] b6 = unGZip(b5);
//  System.out.println("unBZip2:" + new String(b6));
        System.out.println("unzip length : " + b6.length);
        System.out.println("*************ziptime : " +
                           (System.currentTimeMillis() - s));

        s = System.currentTimeMillis();
        byte[] b7 = jzlib(src);
//  System.out.println("jzlib:" + bytesToHexString(b7));
        System.out.println("zip length : " + b7.length);
        byte[] b8 = unjzlib(b7);
        System.out.println("unzip length : " + b8.length);
        System.out.println("*************ziptime : " +
                           (System.currentTimeMillis() - s));

//  System.out.println("unjzlib:" + new String(b8));
    }
}