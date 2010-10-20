package test;

import java.util.List;

import util.DbUtil;
import dms.bean.Analyzer;
import dms.data.Rules;

public class TestCrawl {
	public static void main(String[] args) {

		String sql = "select * from Rules where active_Flag=0 and id=3";
		List<Rules> list = DbUtil.getDao().list(sql, null, -1, -1, new Rules());

		for (Rules rules : list) {
			Analyzer analyzer = new Analyzer(rules.getRuleXml(), rules
					.getDomainId().longValue(), rules.getCity().longValue(),
					rules.getId());
			analyzer.analyze();

			analyzer.saveDB();
		}

	}

}
