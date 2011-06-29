package com.sily.util;

import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * <p/>
 * Title: 工具包
 * </p>
 * <p>Description:处理String通用工具类</p>
 * <p/>
 * Copyright: Copyright (c) 2008-04-17
 * </p>
 * <p/>
 * Company: 上海理想信息产业(集团)有限公司
 * </p>
 *
 * @author yezhiyong
 * @version 1.0
 */
public class StringUtils {

    /**
     * 路径分割符号
     */
    private static final String CHANGE_PATH = "/"; // folder sep.

    private static final String WIN_CHANGE_PATH = "\\"; // Windows folder sep.
    /**
     * 路径位置符
     */
    private static final String TOP_PATH = ".."; // Top folder

    private static final String CURRENT_PATH = "."; // Current folder
    /**
     * 字符串分割符号
     */
    private static final String DELIM = "#";

    /**
     * 将Ascii转换为String
     *
     * @param str
     * @return
     */
    public static String asciiToString(String str) {
        if (isNotNull(str)) {
            return String.valueOf((char) (Integer.parseInt(str) + 64));
        } else {
            return String.valueOf((char) 65);
        }
    }

    /**
     * 效验字符串是否不为空
     *
     * @param value
     * @return true 表示 value不为空(value!=null && !"".equals(value))
     */
    public static boolean isNotNull(String value) {
        return hasText(value);
    }

    public static boolean isNull(String value) {
        return !isNotNull(value);
    }

    /**
     * 效验字符串是否不为空
     *
     * @param value
     * @return true 表示 value不为空(value!=null && !"".equals(value))
     */
    public static boolean isNotEmpty(java.util.Collection col) {
        if (col != null && !col.isEmpty())
            return true;
        else return false;
    }

    /**
     * Removes all whitespace from the given String. We use this to make escape
     * token comparison white-space ignorant.
     *
     * @param toCollapse the string to remove the whitespace from
     * @return a string with _no_ whitespace.
     */
    private static String removeWhitespace(String toCollapse) {
        if (toCollapse == null) {
            return null;
        }

        int length = toCollapse.length();

        StringBuffer collapsed = new StringBuffer(length);

        for (int i = 0; i < length; i++) {
            char c = toCollapse.charAt(i);

            if (!Character.isWhitespace(c)) {
                collapsed.append(c);
            }
        }
        return collapsed.toString();
    }


    /**
     * 验证字符串是否有长度
     * 当字符串为"null"或"NULL"返回值是false
     * 当字符串为空格返回值是true
     * Check if a String has length.
     * <p><pre>
     * StringUtils.hasLength(null) = false
     * StringUtils.hasLength("") = false
     * StringUtils.hasLength("null") = false
     * StringUtils.hasLength("NULL") = false
     * StringUtils.hasLength(" ") = true
     * StringUtils.hasLength("Hello") = true
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is not null and has length
     */
    public static boolean hasLength(String str) {
        return (str != null && str.length() > 0 &&
                !"null".equals(str.toLowerCase()));
    }

    /**
     * 验证字符串是否有文本
     * 当字符串为"null"或"NULL"返回值是false
     * 当字符串为空格返回值是false
     * Check if a String has text. More specifically, returns <code>true</code>
     * if the string not <code>null<code>, it's <code>length is > 0</code>, and
     * it has at least one non-whitespace character.
     * <p><pre>
     * StringUtils.hasText(null) = false
     * StringUtils.hasText("") = false
     * StringUtils.hasText(" ") = false
     * StringUtils.hasText("12345") = true
     * StringUtils.hasText(" 12345 ") = true
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is not null, length > 0,
     *         and not whitespace only
     */
    public static boolean hasText(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0 ||
                "null".equals(str.toLowerCase())) {
            return false;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 返回字符串中包含子字符串的数量
     *
     * @param s   string to search in. Return 0 if this is null.
     * @param sub string to search for. Return 0 if this is null.
     */
    public static int countSubString(String s, String sub) {
        if (s == null || sub == null || "".equals(sub)) {
            return 0;
        }
        int count = 0, pos = 0, idx = 0;
        while ((idx = s.indexOf(sub, pos)) != -1) {
            ++count;
            pos = idx + sub.length();
        }
        return count;
    }

    /**
     * 字符串替换
     * Replace all occurences of a substring within a string with
     * another string.
     *
     * @param inString   String to examine       输入字符串
     * @param oldPattern String to replace     替换目标
     * @param newPattern String to insert      替换值
     * @return a String with the replacements  替换结果
     */
    public static String replace(String inString, String oldPattern,
                                 String newPattern) {
        if (inString == null) {
            return null;
        }
        if (oldPattern == null || newPattern == null) {
            return inString;
        }

        StringBuffer sbuf = new StringBuffer();
        // output StringBuffer we'll build up
        int pos = 0; // Our position in the old string
        int index = inString.indexOf(oldPattern);
        // the index of an occurrence we've found, or -1
        int patLen = oldPattern.length();
        while (index >= 0) {
            sbuf.append(inString.substring(pos, index));
            sbuf.append(newPattern);
            pos = index + patLen;
            index = inString.indexOf(oldPattern, pos);
        }
        sbuf.append(inString.substring(pos));

        // remember to append any characters to the right of a match
        return sbuf.toString();
    }

    /**
     * 删除字符串
     * Delete all occurrences of the given substring.
     *
     * @param pattern the pattern to delete all occurrences of
     */
    public static String delete(String inString, String pattern) {
        return replace(inString, pattern, "");
    }

    public static String deleteRight(StringBuffer buff, String right) {
        if (buff != null && buff.length() > 0)
            return buff.substring(0, buff.length() - right.length());
        else return "";
    }

    public static String deleteRight(String inString, String right) {
        if (inString != null && inString.length() > 0)
            return inString.substring(0, inString.length() - right.length());
        else return "";
    }

    /**
     * 删除字符串中指定的字符集.
     * 例如:deleteAny("abcdefghijklmnnxxyyx","abfkx")="cdeghijlmnnyy"
     *
     * @param chars characters to delete.
     *              E.g. az\n will delete as, zs and new lines.
     */
    public static String deleteAny(String inString, String chars) {
        if (inString == null || chars == null) {
            return inString;
        }
        StringBuffer out = new StringBuffer();
        for (int i = 0; i < inString.length(); i++) {
            char c = inString.charAt(i);
            if (chars.indexOf(c) == -1) {
                out.append(c);
            }
        }
        return out.toString();
    }

    /**
     * 将字符串按指定的"分割符"分割成字符串数组(忽略连续出现分割符部分),同时可设定是否进行去空格处理,是否忽略空字符串
     * Tokenize the given String into a String array via a StringTokenizer.
     *
     * @param s                 the String to tokenize
     * @param delimiters        the delimiter characters, assembled as String
     * @param trimTokens        trim the tokens via String.trim    是否进行去除空格操作
     * @param ignoreEmptyTokens omit empty tokens from the result array  是否忽略空字符串
     * @return an array of the tokens
     *         忽略连续出现分割符部分
     *         split("a;b;c;;",true,true)={'a','b','c'}
     *         split("a;b;c;;",false,true)={'a','b','c'}
     *         <p/>
     *         split("a;b;c;;",false,false)={'a','b','c'}
     *         split("a;b;c; ; ",false,true)={'a','b','c'}
     *         <p/>
     *         split(" a;b;c; ; ",false,false)={' a','b','c',' ',' '}
     *         split(" a;b;c; ; ",true,false)={'a','b','c','',''}
     * @see java.util.StringTokenizer
     * @see java.lang.String#trim
     */
    public static String[] split(String s,
                                 String delimiters, boolean trimTokens,
                                 boolean ignoreEmptyTokens) {

        StringTokenizer st = new StringTokenizer(s, delimiters);
        List tokens = new ArrayList();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (trimTokens) {
                token = token.trim();
            }
            if (!(ignoreEmptyTokens && token.length() == 0)) {
                tokens.add(token);
            }
        }
        return (String[]) tokens.toArray(new String[tokens.size()]);
    }

    /**
     * 将字符串按指定的"分割符"分割成字符串数组(接受连续出现分割符部分)
     * Take a String which is a delimited list and convert it to a String array.
     *
     * @param s     String
     * @param delim delim (this will not be returned)
     * @return an array of the tokens in the list
     *         split("a;b;c;;")={'a','b','c','',''}
     *         split(" a;b;c; ; ")={' a','b','c',' ',' '}
     */
    public static String[] split(String s, String delim) {
        if (s == null) {
            return new String[0];
        }
        if (delim == null) {
            return new String[]{
                    s};
        }

        List l = new LinkedList();
        int pos = 0;
        int delPos = 0;
        while ((delPos = s.indexOf(delim, pos)) != -1) {
            l.add(s.substring(pos, delPos));
            pos = delPos + delim.length();
        }
        if (pos <= s.length()) {
            // add rest of String
            l.add(s.substring(pos));
        }
        return (String[]) l.toArray(new String[l.size()]);
    }

    /**
     * 将字符串按指定的"分割符"分割成数值数组
     *
     * @param s
     * @param delim
     * @return
     */
    public static int[] splitToIntArray(String s, String delim) {
        String[] stringValueArray = split(s, delim);
        int[] intValueArray = new int[stringValueArray.length];
        for (int i = 0; i < intValueArray.length; i++) {
            intValueArray[i] = Integer.parseInt(stringValueArray[i]);
        }
        return intValueArray;
    }

    /**
     * Convenience method to convert a string list to a Set.
     * Note that this will suppress duplicates.
     *
     * @param s String that 'value1,value2,value3'
     * @return a Set of String entries in the list
     */
    public static Set splitToSet(String s, String delim) {
        Set set = new TreeSet();
        String[] tokens = split(s, delim);
        for (int i = 0; i < tokens.length; i++) {
            set.add(tokens[i]);
        }
        return set;
    }

    /**
     * CSV格式字符串转换为字符串数组
     * Convert a CSV list into an array of Strings.
     *
     * @param s CSV list
     * @return an array of Strings, or the empty array if s is null
     */
    public static String[] csvStringToArray(String s) {
        return split(s, ",");
    }

    /**
     * CSV格式字符串转换为Set数据结构
     * Convenience method to convert a CSV string list to a set.
     * Note that this will suppress duplicates.
     *
     * @param s CSV String
     * @return a Set of String entries in the list
     */
    public static Set csvStringToSet(String s) {
        Set set = new TreeSet();
        String[] tokens = csvStringToArray(s);
        for (int i = 0; i < tokens.length; i++) {
            set.add(tokens[i]);
        }
        return set;
    }

    /**
     * Convenience method to return a String array as a CSV String.
     * E.g. useful for toString() implementations.
     *
     * @param arr array to display. Elements may be of any type (toString
     *            will be called on each element).
     */
    public static String joinCsvString(Object[] arr) {
        return join(arr, ",");
    }

    /**
     * Convenience method to return a Collection as a CSV String.
     * E.g. useful for toString() implementations.
     *
     * @param c Collection to display
     */
    public static String joinCsvString(Collection c) {
        return join(c, ",");
    }

    /**
     * Convenience method to return a Map as a CSV String.
     * E.g. useful for toString() implementations.
     *
     * @param c Collection to display
     */
    public static String joinCsvString(Map map) {
        return join(map, ",");
    }

    /**
     * 将对象数组值按指定分割符转化为字符串
     * 转换的字符串为：value1,value2,value3 分隔符为：","
     *
     * @param arr   array to display. Elements may be of any type (toString
     *              will be called on each element).
     * @param delim delimiter to use (probably a ,)
     */
    public static String join(Object[] arr, String delim) {
        if (arr == null) {
            return null;
        } else {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < arr.length; i++) {
                if (i > 0) {
                    sb.append(delim);
                }
                sb.append(arr[i]);
            }
            return sb.toString();
        }
    }

    /**
     * 将对象数组值按指定分割符转化为字符串
     * 转换的字符串为：#value1##value2##value3# 分隔符为："#"
     *
     * @param arr   array to display. Elements may be of any type (toString
     *              will be called on each element).
     * @param delim delimiter to use (probably a ,)
     */
    public static String joinGroup(Object[] arr) {
        if (arr == null) {
            return null;
        } else {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < arr.length; i++) {
                sb.append(DELIM);
                sb.append(arr[i]);
                sb.append(DELIM);
            }
            return sb.toString();
        }
    }

    /**
     * 将Collection值按指定分割符转化为字符串
     * 转换的字符串为：value1,value2,value3 分隔符为：","
     *
     * @param c     Collection to display
     * @param delim delimiter to use (probably a ",")
     */
    public static String join(Collection c, String delim) {
        if (c == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        Iterator it = c.iterator();
        int i = 0;
        Object objValue = null;
        while (it.hasNext()) {
            if (i++ > 0) {
                sb.append(delim);
            }
            objValue = it.next();
            sb.append(objValue == null ? " " : objValue);
        }
        return sb.toString();
    }

    /**
     * 将Map值按指定分割符转化为字符串
     * 转换的字符串为：key1=value1,key2=value2,key3=value3 分隔符为：","
     *
     * @param c     HashMap to display
     * @param delim delimiter to use (probably a ",")
     */
    public static String join(Map map, String delim) {
        if (map == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        Iterator itrKeys = map.keySet().iterator();
        int i = 0;
        String strKey = "";
        String strValue = "";
        while (itrKeys.hasNext()) {
            strKey = (String) itrKeys.next();
            strValue = (String) map.get(strKey);
            if (i++ > 0) {
                sb.append(delim);
            }
            sb.append(strKey + "=" + strValue);
        }
        return sb.toString();
    }

    /**
     * 将格式为：key1=value1,key2=value2,key3=value3 分隔符为："," 转换为Map对象
     * 参考 String join(Map map, String delim)
     * Convenience method to convert a string list to a Map.
     * Note that this will suppress duplicates.
     *
     * @param s String that 'name1=value1,name2=value2'
     * @return a Set of String entries in the list
     */
    public static Map splitToMap(String s, String delim) {
        Map map = new HashMap();
        String[] tokens = split(s, delim);
        String str = null;
        for (int j = 0; j < tokens.length; j++) {
            str = tokens[j];
            String[] arryTokens = split(str, "=");
            map.put(arryTokens[0], (arryTokens.length > 1 ? arryTokens[1] : null));
        }
        return map;
    }

    /**
     * 追加字符串到String数组里面
     *
     * @param arr 数组
     * @param s   要追加的字符串
     * @return 新数组
     */
    public static String[] addToArray(String[] arr, String s) {
        String[] newArr = new String[arr.length + 1];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        newArr[arr.length] = s;
        return newArr;
    }

    /**
     * 获得字符串中,指定分割符号的最后一段
     * 可以用于获得类名
     * Unqualify a string qualified by a '.' dot character. For example,
     * "this.name.is.qualified", returns "qualified".
     *
     * @param qualifiedName the qualified name
     */
    public static String unqualify(String qualifiedName) {
        return unqualify(qualifiedName, '.');
    }

    /**
     * 获得字符串中,指定分割符号的最后一段
     * 可以用于获得文件路径中的路径名称
     * Unqualify a string qualified by a separator character. For example,
     * "this:name:is:qualified" returns "qualified" if using a ':' separator.
     *
     * @param qualifiedName the qualified name
     * @param separator     the separator
     */
    public static String unqualify(String qualifiedName, char separator) {
        return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);
    }

    /**
     * 把第一个字符变大写
     * Capitalize a <code>String</code>, changing the first letter to
     * upper case as per {@link Character#toLowerCase(char)}.
     * No other letters are changed.
     *
     * @param str the String to capitalize, may be null
     * @return the capitalized String, <code>null</code> if null
     */

    public static String capitalizeFirst(String str) {
        return changeFirstCharacterCase(true, str);
    }

    /**
     * 把第一个字符变小写
     * Uncapitalize a <code>String</code>, changing the first letter to
     * lower case as per {@link Character#toLowerCase(char)}.
     * No other letters are changed.
     *
     * @param str the String to uncapitalize, may be null
     * @return the uncapitalized String, <code>null</code> if null
     */
    public static String uncapitalizeFirst(String str) {
        return changeFirstCharacterCase(false, str);
    }

    private static String changeFirstCharacterCase(boolean capitalize,
                                                   String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        StringBuffer buf = new StringBuffer(strLen);
        if (capitalize) {
            buf.append(Character.toUpperCase(str.charAt(0)));
        } else {
            buf.append(Character.toLowerCase(str.charAt(0)));
        }
        buf.append(str.substring(1));
        return buf.toString();
    }

    /**
     * 统一路径格式为"a/b/c"
     * Normalize the path by suppressing sequences like "path/.." and
     * inner simple dots folders.
     * <p>The result is convenient for path comparison. For other uses,
     * notice that Windows separators ("\") are replaced by simple dashes.
     *
     * @param path The original path
     * @return The normalized path
     */

    public static String cleanPath(String path) {
        String p = replace(path, WIN_CHANGE_PATH, CHANGE_PATH);
        String[] pArray = split(p, CHANGE_PATH);
        List pList = new LinkedList();
        int tops = 0;
        for (int i = pArray.length - 1; i >= 0; i--) {
            if (CURRENT_PATH.equals(pArray[i])) {
                // Do nothing
            } else if (TOP_PATH.equals(pArray[i])) {
                tops++;
            } else {
                if (tops > 0) {
                    tops--;
                } else {
                    pList.add(0, pArray[i]);
                }
            }
        }
        return join(pList, CHANGE_PATH);
    }

    /**
     * 从Blob大对象中获得字符串信息
     *
     * @param blob
     * @return
     */
    public static StringBuffer getStrByBlob(java.sql.Blob blob) throws
            IOException, java.sql.SQLException {
        StringBuffer buf = new StringBuffer();
        BufferedReader in = new BufferedReader(new InputStreamReader(blob.
                getBinaryStream()));
        String tmp = "";
        while (in != null && (tmp = in.readLine()) != null) {
            buf.append(tmp);

        }
        in = null;
        tmp = null;
        return buf;
    }

    /**
     * 从Clob大对象中获得字符串信息
     *
     * @param clob
     * @return
     */
    public static StringBuffer getStrByClob(java.sql.Clob clob) throws
            IOException, java.sql.SQLException {
        StringBuffer buf = new StringBuffer();
        BufferedReader in = new BufferedReader(new InputStreamReader(clob.
                getAsciiStream()));
        String tmp = "";
        while (in != null && (tmp = in.readLine()) != null) {
            buf.append(tmp);
        }
        in = null;
        tmp = null;
        return buf;
    }

    /**
     * 压缩字符串
     *
     * @param str 源字符串
     * @return
     */
    public static String compress(String str) throws Exception {
        ByteArrayOutputStream data_ = null;
        String base64Src = "";
        // Create byte array outputstream
        data_ = new ByteArrayOutputStream();
        //Create gZip compressing inputstream
        DataOutputStream out = new DataOutputStream(new GZIPOutputStream(data_));
        out.write(str.getBytes());
        out.close();
        // Return compressint byte stream
        data_.close();
        base64Src = new String(new sun.misc.BASE64Encoder().encode(data_.
                toByteArray()));
        return base64Src;
    }

    /**
     * 解压缩字符串
     *
     * @param str
     * @return
     */
    public static String deCompress(String str) throws Exception {
        String object_ = null;
        byte[] byteSrc64 = new sun.misc.BASE64Decoder().decodeBuffer(str);
        // Create byte array inputstream
        ByteArrayInputStream bi = new ByteArrayInputStream(byteSrc64);
        // Create gZip decompressing inputstream
        GZIPInputStream gzIn = new GZIPInputStream(new ByteArrayInputStream(
                byteSrc64));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len;
        while ((len = gzIn.read(buf)) >= 0) {
            baos.write(buf, 0, len);
        }
        object_ = new String(baos.toByteArray());
        baos.close();
        gzIn.close();
        return object_;
    }

    /**
     * BASE64 的编码
     * 将字符串进行 BASE64 编码
     *
     * @param s
     * @return
     */
    public static String encodedBase64(String s) {
        if (s == null) {
            return null;
        }
        return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
    }

    /**
     * BASE64 的解码
     * 将 BASE64 编码的字符串进行解码
     *
     * @param s
     * @return
     */
    public static String decodedBase64(String s) {
        if (s == null) {
            return null;
        }
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 数据库到对象中文转码处理
     *
     * @param 需转码的对象，一般为字符串
     * @return 返回ISO8859-1字符串
     */
    public static String app2DbEncode(String value) {
        if (value == null || value.equals("null")) {
            value = "";
            //return value;
        }
        try {
            return new String(value.getBytes("ISO8859-1"));
        } catch (Exception e) {
            return value + "";
        }
    }

    /**
     * 数据库到对象中文转码处理
     *
     * @param 需转码字符串
     * @return 返回ISO8859-1字符串
     */
    public static String db2AppEncode(String value) {
        if (value == null || value.equals("null")) {
            value = "";
            //return value;
        }
        try {
            return new String(value.getBytes("ISO8859-1"));
        } catch (Exception e) {
            return value + "";
        }
    }


    /**
     * unicodeToGB
     *
     * @param strIn
     * @return
     */
    public static String unicodeToGB(String strIn) {
        if (strIn == null || strIn.equals("")) {
            return strIn;
        }

        String strOut;
        try {
            strOut = new String(strIn.getBytes("GBK"), "ISO8859_1");
            return strOut;
        } catch (UnsupportedEncodingException e) {
            return strIn;
        }
    }

    /**
     * GBToUnicode
     *
     * @param strIn
     * @return
     */
    public static String GBToUnicode(String strIn) {
        if (strIn == null || strIn.equals("")) {
            return strIn;
        }

        String strOut;
        try {
            strOut = new String(strIn.getBytes("ISO8859_1"), "GBK");
            return strOut;
        } catch (UnsupportedEncodingException e) {
            return strIn;
        }
    }

    /**
     * html字符串编码转换
     *
     * @param str
     * @return
     */
    public static String htmlEncode(String str) {
        if (!isNotNull(str)) return "";
        str = str.replaceAll("&", "&amp;");
        str = str.replaceAll(">", "&gt;");
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll("\"", "&quot;");
        str = str.replaceAll("'", "&apos;");
        return str;
    }

    /**
     * 将null值转成Empty
     */
    public static Object nullToEmpty(Object obj) {
        if (obj != null) {
            return obj;
        } else {
            return "";
        }
    }

    /**
     * 将null值转成Empty
     */
    public static String nullToEmpty(String obj) {
        if (obj != null) {
            return obj;
        } else {
            return "";
        }
    }

    /**
     * 判断字符串是否是null 或者是"",如果是则返回真否则返回假.
     *
     * @param str 字符串参数
     * @return boolean
     */
    public static boolean isNullOrEmpty(String str) {
        if (str == null || str.trim().equalsIgnoreCase(""))
            return true;
        else if ("null".equals(str.toLowerCase()))
            return true;
        else
            return false;

    }

    /**
     * 返回小数点前的字符串
     *
     * @param str
     */
    public static String beforePoint(Object obj) {
        if (null == obj) return "";
        String str = obj.toString();
        int index = str.indexOf(".");
        if (index == -1) return str;
        else
            return str.substring(0, index);
    }
    public final static String conversionNullToBlank(String str) {
        if (str == null) {
            return "";
        }
        return str;
    }

    public final static boolean isInArray(long lg, long[] lgArray) {
        if (lgArray.length < 1) return false;
        for (int i = 0; i < lgArray.length; i++) {
            if (lg == lgArray[i])
                return true;
        }
        return false;
    }

    public final static boolean isInArray(String str, String[] strArray) {
        if (strArray == null || strArray.length < 1) return false;
        for (int i = 0; i < strArray.length; i++) {
            if (strArray[i] == null) continue;
            if (strArray[i].equalsIgnoreCase(str))
                return true;
        }
        return false;
    }

    public final static boolean isArrayInArray(String[] currentRowArrayValues, String[] strArray) {
        if (strArray == null || strArray.length < 1) return false;
        if (currentRowArrayValues == null || currentRowArrayValues.length < 1) return false;
        for (int i = 0; i < strArray.length; i++) {
            for (int j = 0; j < currentRowArrayValues.length; j++) {
                if (strArray[i].equalsIgnoreCase(currentRowArrayValues[j]))
                    return true;
            }
        }
        return false;
    }

    public final static void ps(String str) {
       
    }
    
    public final static String getLikeQueryValue(String str){
    	if(isNullOrEmpty(str)) str = "";
    	return "%" + str + "%";
    }
    
    public static void main(String[] args) {
    	String[] s = split(null, ",", true, true);
    }
    public static final String trim(String str) {
        return str == null ? null : str.trim();
    }

}
