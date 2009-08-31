package cn.com.mianshi;

public class GetMaxNum {

	static int s[] = {1,-1,3,4};
	
	public static void main(String[] args) {
		System.out.println(new GetMaxNum().getMax(s));
	}
	
	public int getMax(int[] s){
		int max = 0;
		for (int i = 0; i < s.length; i++) {
			int temp = 0;
			for (int j = i; j < s.length; j++) {
				temp += s[j];
				if(max < temp){
					max = temp;
				}
			}
		}
		return max;
	}
}
