/** 
 * 文件名：		Compiler.java 
 * 
 * 版本信息: 	v1.0
 * 日期：		2011-8-5 
 * Copyright:  	Copyright(c) 2010
 * Corporation:	2011 
 * Company：		广州正道科技有限公司  
 */
package cn.sily.java6;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;


/** 
 * 名称：	Compiler 
 * 描述： 	动态编译文件，执行对应的方法
 * 创建人：	sily
 * 创建时间：	2011-8-5 上午11:46:06
 * 修改人：	
 * 修改时间：	
 * 修改备注： 
 * @version 1.0
 */
public class Compiler {

	public static void main(String[] args) {
		try {
			JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
			
			StandardJavaFileManager sjfm = jc.getStandardFileManager(null, null, null);
			
			File file = new File("d:/我的桌面/cn/sily/java6/Hello.java");
			
			Iterable fileObjects = sjfm.getJavaFileObjects(file);
			
			CompilationTask ct = jc.getTask(null, sjfm, null, null, null, fileObjects);
			
			ct.call();

			sjfm.close();
			
			//直接通过URLClassLoader
			/**
			 * 使用默认的委托父 ClassLoader 为指定的 URL 构造一个新 URLClassLoader。
			 * 首先在父类加载器中搜索 URL，然后按照为类和资源指定的顺序搜索 URL。
			 * 这里假定任何以 '/' 结束的 URL 都是指向目录的。如果不是以该字符结束，
			 * 则认为该 URL 指向一个将根据需要下载和打开的 JAR 文件。 
			 * 
			 * 使用本地文件路径必须要用到file:/开头  URL才能找到资源
			 * URL[] urls = new URL[]{new URL("file:/d:/我的桌面/")};
			 *  <==>
			 * File cfile= new File("d:/我的桌面/");
			 * URL[] urls = new URL[]{cfile.toURL()};
			 */
			
			URL[] urls = new URL[]{new URL("file:/d:/我的桌面/")};
			URLClassLoader urlc = new URLClassLoader(urls);
			Class c = urlc.loadClass("cn.sily.java6.Hello");
			
			//通过继承ClassLoader来动态加载类，路径可以是网络路径，本地文件路径
//			MyClassLoader mycl = new MyClassLoader();
//			File cfile= new File("F:/myeclipseworkspace/myproject/src/cn/sily/java6/Hello.class");
//			System.out.println(cfile.toURL());
//			Class c = mycl.loadClass(cfile.toURL().toString(),true);
			
			 Object o = c.newInstance();
			 
			 Method m = o.getClass().getMethod("getHelloWorld", null);
			 
			 Object str = m.invoke(o, null);
			 
			 System.out.println(str);
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
