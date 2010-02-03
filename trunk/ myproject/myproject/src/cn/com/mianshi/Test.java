package cn.com.mianshi;

import java.math.BigInteger;

public class Test {
	public int[] sort(int[] a){
		for(int i=0;i<a.length-1;i++){
			for (int j = i+1; j < a.length; j++) {
				if(a[i]>a[j]){
					int temp = a[j];
					a[j]= a[i];
					a[i] = temp;
				}
			}
		}
		return a;
	}
	
	public Object[] concat(Object[] a, Object[] b){
		if(a==null&&b==null){
			return new Object[0];
		}
		if(a==null){
			return b;
		}
		if(b==null){
			return a;
		}
		int a1 = a.length;
		int b1 = b.length;
		
		Object[] c = new Object[a1+b1];
		System.arraycopy(a, 0, c, 0, a1);
		System.arraycopy(b, 0, c, a1, b1);
		return c;
	}
	
	public BigInteger operate(long n){
		BigInteger sum = new BigInteger("0");
		for (int i = 0; i < n; i++) {
			BigInteger num = new BigInteger(i + "");
			if(i%2 == 0){
				num = num.negate();
			}
			sum = sum.add(num);
		}
		
		return sum;
	}
	
	public static void main(String[] args) {
		Object[] a = {"a1","a2","a3"};
		Object[] b = {"b1","b2","b3"};
		Test t = new Test();
		Object[] c = new Test().concat(a,b);
		for (int i = 0; i < c.length; i++) {
			//System.out.println((String)c[i]);
		}
		//System.out.println(t.operate(999999999));
		Singleton1 s1 = Singleton1.getInstance();
		System.out.println(s1.c1);
		System.out.println(s1.c2);
		
	}
}
