package alltest;

import java.text.DecimalFormat;
import java.text.Format;

public class DF {

	public static void main(String[] argv) throws Exception {

		DecimalFormat formatter = new DecimalFormat("#");
		String s = formatter.format(1234.00);
		System.out.println(s);
	}
}
