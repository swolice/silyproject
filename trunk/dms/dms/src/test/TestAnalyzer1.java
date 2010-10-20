package test;

import java.io.InputStream;

import dms.bean.Analyzer;

public class TestAnalyzer1 {
	public static void main(String[] args) throws Exception {
		String rule = "";
		InputStream in = TestAnalyzer1.class.getClassLoader()
				.getResourceAsStream("conf/n1.xml");

		byte[] bytes = new byte[in.available()];
		in.read(bytes);
		in.close();

		rule = new String(bytes,"utf-8");
		System.out.println(rule);

		Analyzer analyzer = new Analyzer(rule, 1l, 1l,1l);

		analyzer.analyze();

	}

}
