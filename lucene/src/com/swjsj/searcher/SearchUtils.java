package com.swjsj.searcher;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SearchUtils {
	private static IndexWriterConfig config = new IndexWriterConfig(
			Version.LUCENE_36, new SimpleAnalyzer(Version.LUCENE_36));

	private Directory directory = null;

	private static IndexReader reader = null;

	public void index() {
		IndexWriter iw = null;
		try {
			iw = new IndexWriter(directory, config);
			iw.deleteAll();
			Document doc = null;
			File[] files = new File("h:/logs").listFiles();
			for (File file : files) {
				doc = new Document();
				doc.add(new Field("content", new FileReader(file)));
				doc.add(new Field("filename", file.getName(), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				doc.add(new Field("path", file.getAbsolutePath(),
						Field.Store.YES, Field.Index.NOT_ANALYZED));
				iw.addDocument(doc);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				iw.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public SearchUtils() {
		try {
			directory = FSDirectory.open(new File(
					"H:/workspace2/lucene/indexs02"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public SearchUtils(Directory directory) {
			this.directory = directory;
	}

	public IndexSearcher getIndexSearcher() {
		try {
			if (reader == null) {
				reader = IndexReader.open(directory);
			} else {
				IndexReader ir = reader.openIfChanged(reader);
				if (ir != null) {
					if (reader != null) {
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

	public void SearchByQueryParse1() {
		IndexSearcher is = getIndexSearcher();
		try {
			QueryParser qp = new QueryParser(Version.LUCENE_36, "content",
					new SimpleAnalyzer(Version.LUCENE_36));
			Query query = qp.parse("jishijun204");
			TopDocs tp = is.search(query, 10);
			ScoreDoc[] sd = tp.scoreDocs;
			for (ScoreDoc scoreDoc : sd) {
				Document doc = is.doc(scoreDoc.doc);
				System.out.println(doc.get("path") + " 文件名："
						+ doc.get("filename"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void SearchByQueryParse2(Query query) {
		IndexSearcher is = getIndexSearcher();
		try {
			TopDocs tp = is.search(query, 100);
			ScoreDoc[] sd = tp.scoreDocs;
			for (ScoreDoc scoreDoc : sd) {
				Document doc = is.doc(scoreDoc.doc);
				System.out.println(doc.get("path") + " 文件名："
						+ doc.get("filename"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void searchNoPage(Query query) {
		IndexSearcher is = getIndexSearcher();
		try {
			TopDocs tp = is.search(query, 100);
			System.out.println("多少个索引:" + tp.totalHits);
			ScoreDoc[] sd = tp.scoreDocs;
			for (int i = 0; i < sd.length; i++) {
				Document doc = is.doc(sd[i].doc);
				System.out.println("doc.id" + sd[i].doc + "----->"
						+ doc.get("path") + " 文件名：" + doc.get("filename"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void searchByPage1(Query query, int pageIndex, int pageSize) {
		IndexSearcher is = getIndexSearcher();
		try {
			TopDocs tp = is.search(query, 100);
			ScoreDoc[] sd = tp.scoreDocs;
			int end = sd.length < pageIndex * pageSize ? sd.length : pageIndex
					* pageSize;
			int start = (pageIndex - 1) * pageSize;
			if (end < start) {
				throw new Exception("索引的数据小于要查询的数据数");
			}
			for (int i = start; i < end; i++) {
				Document doc = is.doc(sd[i].doc);
				System.out.println("doc.id" + sd[i].doc + "----->"
						+ doc.get("path") + " 文件名：" + doc.get("filename"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public ScoreDoc getScoreDoc(Query query, int pageIndex, int pageSize) {
		if (1 == pageIndex) {
			return null;
		}
		IndexSearcher is = getIndexSearcher();
		int req_num = (pageIndex - 1) * pageSize;
		TopDocs tp;
		try {
			tp = is.search(query, req_num);
			if (tp.totalHits<=req_num) {
				throw new Exception("索引的数据小于要查询的数据数");
			}
			ScoreDoc[] sds = tp.scoreDocs;
			return sds[req_num - 1];
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void searchByPage2(Query query, int pageIndex, int pageSize) {
		IndexSearcher is = getIndexSearcher();
		try {
			TopDocs tp = is.searchAfter(getScoreDoc(query,pageIndex,pageSize), query, pageSize);
			ScoreDoc[] sd = tp.scoreDocs;
			int end = sd.length < pageIndex * pageSize ? sd.length : pageIndex
					* pageSize;
			int start = (pageIndex - 1) * pageSize;
			if (end < start) {
				throw new Exception("索引的数据小于要查询的数据数");
			}
			for (int i = start; i < end; i++) {
				Document doc = is.doc(sd[i].doc);
				System.out.println("doc.id" + sd[i].doc + "----->"
						+ doc.get("path") + " 文件名：" + doc.get("filename"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
