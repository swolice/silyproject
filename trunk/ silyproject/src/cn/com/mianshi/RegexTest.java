package cn.com.mianshi;

public class RegexTest {

	public static void main(String[] args) {
		String aa = "test中dd文dsaf中男大3443n中国43中国人0ewldfls=103";
		byte[] b = aa.getBytes();
		int bytesplit = 100;
		int[] ii = new int[bytesplit];
		int temp = 0;
		if (b.length > bytesplit) {

			for (int i = 0; i < bytesplit; i++) {
				String bb = aa.substring(i, i + 1);
				boolean cc = java.util.regex.Pattern.matches("[\u4E00-\u9FA5]",
						bb);
				if (cc) {
					ii[temp] = temp + i;
					temp++;
				}
			}
			for (int i = 0; i < ii.length; i++) {
				if (ii[i] == bytesplit - 1) {
					bytesplit--;
					break;
				}
			}
			System.out.println(new String(b, 0, bytesplit));
		}else{
			System.out.println(new String(b, 0, b.length));
		}

		
	}
}
