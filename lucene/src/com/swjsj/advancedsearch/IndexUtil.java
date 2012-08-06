package com.swjsj.advancedsearch;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NRTManager;
import org.apache.lucene.search.NRTManager.TrackingIndexWriter;
import org.apache.lucene.search.NRTManagerReopenThread;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.SearcherFactory;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
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

	private static IndexWriterConfig config = new IndexWriterConfig(
			Version.LUCENE_36, new SimpleAnalyzer(Version.LUCENE_36));

	private TrackingIndexWriter tiw;
	private NRTManager nm;
	private SearcherManager sm;

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
			directory = FSDirectory.open(new File("F:/myeclipseblue/lucene/index01"));
			IndexWriter writer = new IndexWriter(directory, config);
			tiw = new TrackingIndexWriter(writer);
			nm = new NRTManager(tiw, new SearcherFactory(),true);
			sm = new SearcherManager(writer,true, new SearcherFactory()); 
			NRTManagerReopenThread nmthread = new NRTManagerReopenThread(nm, 0.5, 0.025);
			nmthread.setDaemon(true);
			nmthread.setName( " NRTManager Thread " );
			nmthread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void commit(){
		try {
			tiw.getIndexWriter().commit();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
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
			tiw.deleteAll();
			commit();
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
				tiw.addDocument(doc);
			}
			commit();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void delDoc() {
		try {
			tiw.deleteDocuments(new Term("id", "1"));
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void mayBeRefresh(){
		try {
			sm.maybeRefresh();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public void search() {
//		IndexSearcher is =  nm.acquire();
		IndexSearcher is =  sm.acquire();
		try {
			TermQuery tq = new TermQuery(new Term("content", "like"));
			TopDocs td = is.search(tq, 10);
			ScoreDoc[] sds = td.scoreDocs;
			for (ScoreDoc sd : sds) {
				Document doc = is.doc(sd.doc);
				System.out.println(" doc.id = " + doc.get("id")
						+ " doc.boost :" + doc.getBoost() + "--> sd :"
						+ sd.score + "--> email = " + doc.get("email")
						+ "---->" + doc.get("id") + "--> attach:"
						+ doc.get("attach") + " --> ldate: "
						+ getLong2DateStr(doc.get("ldate")));
			}
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				nm.release(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
