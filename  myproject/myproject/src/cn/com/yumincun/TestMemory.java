package cn.com.yumincun;

public class TestMemory {
	public static void main(String[] args) {
		System.out.println("JVM MAX FREEMEMORY: "
				+ Runtime.getRuntime().freeMemory() / 1024 / 1024 + "M");
		System.out.println("JVM MAX MEMORY: "
				+ Runtime.getRuntime().maxMemory() / 1024 / 1024 + "M");
		System.out.println("JVM IS USING MEMORY:"
				+ Runtime.getRuntime().totalMemory() / 1024 / 1024 + "M");
	}
}
