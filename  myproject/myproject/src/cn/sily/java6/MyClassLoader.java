/** 
 * 文件名：		MyClassLoader.java 
 * 
 * 版本信息: 	v1.0
 * 日期：		2011-8-5 
 * Copyright:  	Copyright(c) 2010
 * Corporation:	2011 
 * Company：		广州正道科技有限公司  
 */
package cn.sily.java6;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;

/**
 * 名称： MyClassLoader 描述： 创建人： sily 创建时间： 2011-8-5 下午01:38:45 修改人： 修改时间： 修改备注：
 * 
 * @version 1.0
 */
public class MyClassLoader extends ClassLoader {
	// 定义哈希表（Hashtable）类型的变量，
	// 用于保存被载入的类数据。
	Hashtable loadedClasses;

	public MyClassLoader() {
		loadedClasses = new Hashtable();
	}

	public synchronized Class loadClass(String className, boolean resolve)
			throws ClassNotFoundException {
		Class newClass;
		byte[] classData;

		// 检查要载入的类数据是否已经被保存在哈希表中。
		newClass = (Class) loadedClasses.get(className);
		// 如果类数据已经存在且resolve值为true，则解析它。
		if (newClass != null) {
			if (resolve)
				resolveClass(newClass);
			return newClass;
		}

		/*
		 * 首 先 试 图 从 本 地 系 统 类 组 中 载 入 指 定 类。 这 是 必 须 的， 因 为 虚 拟 机 将 这 个 类 载 入
		 * 后， 在 解 析 和 执 行 它 时 所 用 到 的 任 何 其 他 类， 如JAVA.lang.System 类 等， 均 不 再 使
		 * 用 虚 拟 机 的 类 载 入 器， 而 是 调 用 我 们 自 制 的 类 载 入 器 来 加 载。
		 */

		try {
			newClass = findSystemClass(className);
			return newClass;
		} catch (ClassNotFoundException e) {
			System.out.println(className + " is not a system class!");
		}

		// 如果不是系统类，
		// 则试图从网络中指定的URL地址载入类。
		try {
			// 用自定义方法载入类数据，
			// 存放于字节数组classData中。
			classData = getClassData(className);
			// 由字节数组所包含的数据建立一个class类型的对象。
			newClass = defineClass(classData, 0, classData.length);
			if (newClass == null)
				throw new ClassNotFoundException(className);
		} catch (Exception e) {
			throw new ClassNotFoundException(className);
		}

		// 如果类被正确载入， 则将类数据保存在哈希表中，以备再次使用。
		loadedClasses.put(className, newClass);
		// 如果resolve值为true，则解析类数据。
		if (resolve) {
			resolveClass(newClass);
		}
		return newClass;
	}

	// 这个方法从网络中载入类数据。
	protected byte[] getClassData(String className) throws IOException {
		byte[] data;
		int length;
		try {
			// 从网络中采用URL类的方法 载入指定URL地址的类的数据。
			URL url = new URL(className.endsWith(".class") ? className
					: className + ".class");
			URLConnection connection = url.openConnection();
			InputStream inputStream = connection.getInputStream();
			length = connection.getContentLength();

			data = new byte[length];
			inputStream.read(data);
			inputStream.close();
			return data;
		} catch (Exception e) {
			throw new IOException(className);
		}
	}
}
