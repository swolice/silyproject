package cn.com.mianshi;

public class Thread_test1 {
	static int num = 0;

	public static void main(String[] args) {
		new Thread_test1().start();
		System.out.println("now:" + num);
	}

	class Thd implements Runnable {
		public void run() {
			for (int i = 0; i < 100; i++) {
				num++;
				System.out.println("add-------------------"+num + "\n");
			}
		}
	}

	class Thd1 implements Runnable {
		public void run() {
			for (int i = 0; i < 100; i++) {
				num--;
				System.out.println("reduce-----------------"+num + "\n");	
			}
		}
	}

	public void start() {
		for (int i = 0; i < 2; i++) {
			Thd th1 = new Thd();
			new Thread(th1).start();
		}
		for (int i = 0; i < 2; i++) {
			Thd1 th = new Thd1();
			new Thread(th).start();
		}
	}
}