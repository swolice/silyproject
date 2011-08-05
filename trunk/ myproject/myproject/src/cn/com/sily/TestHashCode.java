/** 
 * 文件名：		TestHashCode.java 
 * 
 * 版本信息: 	v1.0
 * 日期：		2011-7-11 
 * Copyright:  	Copyright(c) 2010
 * Corporation:	2011 
 * Company：		广州正道科技有限公司  
 */
package cn.com.sily;

/** 
 * 名称：	TestHashCode 
 * 描述： 
 * 创建人：	sily
 * 创建时间：	2011-7-11 上午11:01:58
 * 修改人：	
 * 修改时间：	
 * 修改备注： 
 * @version 1.0
 */
import java.util.HashMap;

public class TestHashCode {

    static HashMap map = new HashMap();

    private static char startChar = 'A';

    private static char endChar = 'z';

    private static int offset = endChar - startChar + 1;

    private static int dup = 0;

    public static void main(String[] args) {
        int len = 3;
        char[] chars = new char[len];
        tryBit(chars, len);
        System.out.println((int)Math.pow(offset, len) + ":" + dup);
    }

    private static void tryBit(char[] chars, int i) {
        for (char j = startChar; j <= endChar; j++) {
            chars[i - 1] = j;
            if (i > 1)
                tryBit(chars, i - 1);
            else
                test(chars);
        }
    }

    private static void test(char[] chars) {

        String str = new String(chars).replaceAll("[^a-zA-Z_]", "").toUpperCase();// 195112:0
        //String str = new String(chars).toLowerCase();//195112:6612
        //String str = new String(chars).replaceAll("[^a-zA-Z_]","");//195112:122500
        //String str = new String(chars);//195112:138510
        Object obj = new Object();
        obj.hashCode();
        
        int hash = str.hashCode();
      
        if (map.containsKey(hash)) {
            String s = (String) map.get(hash);
            if (!s.equals(str)) {
                dup++;
                System.out.println(s + ":" + str);
            }
        } else {
            map.put(hash, str);
            // System.out.println(str);
        }
    }
} 

