package cn.com.mianshi;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Array;

public class Reflection {
	private static int[] a = { 3, 2, 8, 11, 20, 5, 6, 18, 7, 12 };

	public static void main(String[] args) {
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
	public static String print(int a[]){
		String str = "";
		for (int i = 0; i < a.length; i++) {
			str += a[i]+"\t";
		}
		return str;
	}
}
