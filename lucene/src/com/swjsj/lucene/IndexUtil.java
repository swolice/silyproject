package com.swjsj.lucene;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class IndexUtil {

	private String[] ids = { "1", "2", "3", "4", "5", "6" };
	private String[] emails = { "aa@itat.org", "bb@itat.org", "cc@cc.org",
			"dd@sina.org", "ee@zttc.edu", "ff@itat.org" };
	private String[] contents = { "welcome to visited the space,I like book",
			"hello boy, I like pingpeng ball", "my name is cc I like game",
			"I like football", "I like football and I like basketball too",
			"I like movie and swim" };

	private int[] attachs = { 2, 3, 1, 4, 5, 6 };
	private String[] names = { "zhangsan", "lisi", "john", "jetty", "mike",
			"jake" };
	private Directory directory = null;
	private Map<String, Float> scoreMap = new HashMap<String, Float>();
	private Date[] ldates = new Date[6];
	
	private static IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_36,	new SimpleAnalyzer(Version.LUCENE_36));
	
	private static IndexReader reader = null;
	private static IndexWriter write = null;


	public String getLong2DateStr(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Long l = Long.parseLong(str);
		Date date = new Date();
		date.setTime(l);
		return sdf.format(date);
	}

	public IndexUtil() {
		try {
			initDate();
			scoreMap.put("itat.org", 2.0f);
			scoreMap.put("cc.org", 1.5f);
			directory = FSDirectory.open(new File("H:/workspace2/lucene/indexs01"));
			reader = IndexReader.open(directory);
			write = new IndexWriter(directory, config);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initDate() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 2);
		ldates[0] = c.getTime();
		c.add(Calendar.DAY_OF_MONTH, 2);
		ldates[1] = c.getTime();
		c.add(Calendar.DAY_OF_MONTH, 2);
		ldates[2] = c.getTime();
		c.add(Calendar.DAY_OF_MONTH, 2);
		ldates[3] = c.getTime();
		c.add(Calendar.DAY_OF_MONTH, 2);
		ldates[4] = c.getTime();
		c.add(Calendar.DAY_OF_MONTH, 2);
		ldates[5] = c.getTime();
	}
	
	
	

	public void index() {
		try {
			write.deleteAll();
			Document doc = null;
			for (int i = 0; i < ids.length; i++) {
				doc = new Document();
				doc.add(new Field("id", ids[i], Field.Store.YES,
						Field.Index.NOT_ANALYZED_NO_NORMS));
				doc.add(new Field("email", emails[i], Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				doc.add(new Field("content", contents[i], Field.Store.NO,
						Field.Index.ANALYZED));
				doc.add(new Field("name", names[i], Field.Store.YES,
						Field.Index.NOT_ANALYZED_NO_NORMS));
				doc.add(new NumericField("attach", Field.Store.YES, true)
						.setIntValue(attachs[i]));
				doc.add(new NumericField("ldate", Field.Store.YES, true)
						.setLongValue(ldates[i].getTime()));
				 String et = emails[i].substring(emails[i].indexOf("@") + 1);
				 if (scoreMap.containsKey(et)) {
					 doc.setBoost(scoreMap.get(et));
				 } else {
					 doc.setBoost(0.5f);
				 }
				write.addDocument(doc);
			}
			write.commit();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void delDoc(){
		try {
			write.deleteDocuments(new Term("id","1"));
			write.commit();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void delForceDoc(){
		try {
			write.forceMergeDeletes();
			write.commit();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void query() {
		System.out.println("maxDoc:" + reader.maxDoc());
		System.out.println("numDocs:" + reader.numDocs());
		System.out.println("numDeletedDocs:" + reader.numDeletedDocs());
	}
	
	public IndexSearcher getIndexSearcher(){
		try {
			if(reader == null){
				reader = IndexReader.open(directory);
			}else{
				IndexReader ir = reader.openIfChanged(reader);
				if(ir != null){
					if(reader != null){
						reader.close();
					}
					reader = ir;
				}
			}
			IndexSearcher is = new IndexSearcher(reader);
			return is;
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void search() {
		IndexSearcher is = null;
		try {
			TermQuery tq = new TermQuery(new Term("content", "like"));
			is = getIndexSearcher();
			TopDocs td = is.search(tq, 10);
			ScoreDoc[] sds = td.scoreDocs;
			for (ScoreDoc sd : sds) {
				Document doc = is.doc(sd.doc);
				System.out.println(" doc.id = " + doc.get("id") + " doc.boost :"
						+ doc.getBoost() + "--> sd :" + sd.score
						+ "--> email = " + doc.get("email") + "---->"
						+ doc.get("id") + "--> attach:" + doc.get("attach")
						+ " --> ldate: " + getLong2DateStr(doc.get("ldate")));
			}
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
