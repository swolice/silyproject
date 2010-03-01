package cn.com.mianshi;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflection {
	private static int[] a = { 3, 2, 8, 11, 20, 5, 6, 18, 7, 12 };

	public static void main(String[] args) {
		getProperts();
//		try {
//			Class c = Class.forName("Test");
//			Constructor ct = c.getConstructor();
//			Test o = (Test) ct.newInstance();
//			Method method = c.getDeclaredMethod("sort", int[].class);
//			int[] a1 = (int[]) method.invoke(o,a);
//			System.out.println(print(a1));
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			e.printStackTrace();
//		}
		
	}
	
	
	public static String printTest(int a[]){
		Test t = new Test();
		Class<? extends Test> c = t.getClass();
		try {
			Method method = c.getDeclaredMethod("sort", int[].class);
			System.out.println(print(a));
			int[] a1 = (int[]) method.invoke(t,a);
			System.out.println(print(a1));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
		}
		return null;
	}
	public static String print(int a[]){
		String str = "";
		for (int i = 0; i < a.length; i++) {
			str += a[i]+"\t";
		}
		return str;
	}
	
	
	public static String[] getProperts(){
		TreeBean tb = new TreeBean();
		Class<? extends TreeBean> c = tb.getClass();
		
		try {
			Field[] fields = c.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				try {
					StringBuffer sb = new StringBuffer();
					
					String fieldName = fields[i].getName();
					if("boolean".equals(fields[i].getGenericType().toString())){
						if(fieldName.startsWith("is")){
							sb.append(fieldName);
						}else{
							sb.append("is");
							sb.append(fieldName.substring(0,1).toUpperCase());
						    sb.append(fieldName.substring(1));
						}
					}else{
						sb.append("get");
						sb.append(fieldName.substring(0,1).toUpperCase());
					    sb.append(fieldName.substring(1));
					}
				    
					System.out.println(c.getDeclaredMethod(sb.toString(),null));
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
				
			}
//			Method method = c.getDeclaredMethod("sort", int[].class);
//			System.out.println(print(a));
//			int[] a1 = (int[]) method.invoke(t,a);
//			System.out.println(print(a1));
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
