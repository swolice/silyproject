package cn.com.crm;

public class TestSplit {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		String str = "BILL.ZD.201004.004.999";
		String aa = str.substring(str.lastIndexOf(".")+1);
		System.out.println(aa);
	}	

}
