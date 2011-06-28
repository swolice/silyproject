/** 
 * 文件名：		TestQuery.java 
 * 
 * 版本信息: 	v1.0
 * 日期：		2011-6-15 
 * Copyright:  	Copyright(c) 2010
 * Corporation:	2011 
 * Company：		广州正道科技有限公司  
 */
package lucene;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

/**
 * 名称： TestQuery 描述： 创建人： 吉仕军 创建时间： 2011-6-15 上午09:52:57 修改人： 修改时间： 修改备注：
 * 
 * @version 1.0
 */
public class TestQuery {
	/**
	 * @param args
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws IOException, ParseException {
		TopDocs tdoc = null;
		String queryString = "SETTLE_ORG_002";
		Query query = null;
		SimpleFSDirectory dereDirectory = new SimpleFSDirectory(new File(
				"e:\\index"));
		IndexSearcher searcher = new IndexSearcher(dereDirectory);
		Analyzer analyzer = new SimpleAnalyzer();
		try {
			QueryParser qp = new QueryParser(Version.LUCENE_32,"body", analyzer);
			query = qp.parse(queryString);
		} catch (ParseException e) {
		}
		if (searcher != null) {
			tdoc = searcher.search(query,100);
			ScoreDoc[] scoreDocs = tdoc.scoreDocs;
			if (scoreDocs.length > 0) {
				System.out.println(" 找到: " + scoreDocs.length + "  个结果! ");
				for (int i = 0; i < scoreDocs.length; i++) {
					ScoreDoc d = scoreDocs[i];
					Document doc = searcher.doc(d.doc);
					Field pfield = doc.getField("path");
					String name = new String(pfield.getBinaryValue(),"UTF-8");
					System.out.println(name);
				}
			}
		}
	}
}
