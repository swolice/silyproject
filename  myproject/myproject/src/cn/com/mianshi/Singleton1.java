package cn.com.mianshi;

public class Singleton1 {

	private static Singleton1 instance = new Singleton1();
	
	public static int c1;

	public static int c2 = 0;
	
	private Singleton1(){
		c1++;
		c2++;
	}
	
	public static Singleton1 getInstance(){
		return instance;
	}
	
}
