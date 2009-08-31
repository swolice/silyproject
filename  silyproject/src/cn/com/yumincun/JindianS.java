package cn.com.yumincun;

public class JindianS {

	public static void main(String[] args) {
		int a = new JindianS().factorial(3);
		int aa = (int)(Math.random()*1000);
		System.out.println(aa);
	}
	public int factorial(int n){
		if(n==1){
			return 1;
		}else{
			return n*factorial(n-1);
		}
		
	}
}
