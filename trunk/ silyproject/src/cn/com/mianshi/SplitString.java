package cn.com.mianshi;

public class SplitString {
	String splitStr;
	int splitByte;

	public SplitString(String str, int bytes) {
		splitStr = str;
		splitByte = bytes;
		System.out.println("this String is " + splitStr + "and splitByte is "
				+ splitByte);
	}

	public void Split() {
		byte[] b = splitStr.getBytes();
		char[] c = splitStr.toCharArray();
		int temp = 0;
		int tt[] = new int[splitByte];
		for (int i = 0; i < splitByte; i++) {
			byte[] bytes = (""+c[i]).getBytes();
			 if(bytes.length==2){ 
                 int[] ints=new int[2]; 
                 ints[0]=bytes[0]& 0xff; 
                 ints[1]=bytes[1]& 0xff; 
                 if(ints[0]>=0x81 && ints[0]<=0xFE && ints[1]>=0x40 && ints[1]<=0xFE){
                	 tt[temp] = temp + i;
                	 temp++;
                 }
			 }
		}
		for (int i = 0; i < tt.length; i++) {
			if(tt[i] == splitByte-1){
				splitByte -= 1;
				break;
			}
		}
		byte[] a = new byte[splitByte];
		for (int i = 0; i < splitByte; i++) {
			a[i] = b[i];
		}
		System.out.println(new String(a));
	}

	public static void main(String[] args) {
		SplitString ss = new SplitString("test中dd文dsaf中男大3443n中国43中国人0ewldfls=103", 9);
		
		ss.Split();
	}
}
