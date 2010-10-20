package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Double kk = new Double("123456789012312.98");
		java.math.BigDecimal kl0 = new java.math.BigDecimal("123456789012312.98");
		java.math.BigDecimal kl1 = new java.math.BigDecimal("123456789012312.98");
		double kl2 = kl0.doubleValue() + kl0.doubleValue();
		System.out.println(kk.doubleValue());
		
//		System.out.println(new java.math.BigDecimal("SAdfasdfADSsadfasd1111111111"));
		
//		Currency : /^\d+(\.\d+)?$/,
//				Number : /^\d+$/,
//				Zip : /^[1-9]\d{5}$/,
//				QQ : /^[1-9]\d{4,8}$/,
//				Integer : /^[-\+]?\d+$/,
//				Double : /^[-\+]?\d+(\.\d+)?$/,
		String regEX = "^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)([eE]([-+]?([012]?\\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))?[dD]?$";
		Pattern pattern = Pattern.compile(regEX);
		Matcher match = pattern.matcher(String.valueOf(kk.doubleValue()));
		System.out.println(match.find());

	}

}
