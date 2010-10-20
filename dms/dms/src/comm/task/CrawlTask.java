package comm.task;

import java.util.List;

import dms.bean.Analyzer;
import dms.data.Info;
import dms.data.Rules;

import util.DbUtil;

public class CrawlTask extends java.util.TimerTask {

	public void crawlNow() {// 根据规则表的定义抓取各网站首页团购
		try {
			String sql = "select * from rules where active_Flag=0";
			List<Rules> list = DbUtil.getDao().list(sql, null, -1, -1,
					new Rules());

			for (Rules rules : list) {
				try {
					Analyzer analyzer = new Analyzer(rules.getRuleXml(), rules
							.getDomainId().longValue(), rules.getCity()
							.longValue(), rules.getId());
					analyzer.analyze();

					analyzer.saveDB();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void crawlHistory() {// 抓取往期未结束团购
		try {
			String sql = "select * from info t where t.rule_id is not null and t.url1 is not null and ( (t.end_time is not null and t.end_time>now()) or t.end_flag=1 )";

			List<Info> list = DbUtil.getDao().list(sql, null, -1, -1,
					new Info());
			if (list != null && list.size() > 0) {
				for (Info info : list) {
					try {
						String url = info.getUrl1();
						Rules rules = (Rules) DbUtil.getDao().load(Rules.class,
								info.getRuleId().longValue());

						Analyzer analyzer = new Analyzer(rules.getRuleXml(),
								rules.getDomainId().longValue(), rules
										.getCity().longValue(), rules.getId());
						analyzer.setUrl1(url);
						analyzer.analyze();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void run() {
		crawlNow();
		crawlHistory();
	}
}
