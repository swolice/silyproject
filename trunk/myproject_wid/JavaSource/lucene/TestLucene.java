/*
 * 创建日期： 2009-11-26
 *
 */
package lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * @author 吉仕军
 *	
 */
public class TestLucene {

//	src要创建索引的文件，destDir索引存放的目录   
	public static void createIndex(File src, File destDir){   
	 Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT); //创建一个语法分析器   
	 IndexWriter iwriter = null;   
	 Directory directory = null;   
	 try {   
	  directory = FSDirectory.open(destDir); //把索引文件存储到磁盘目录   
	  //创建一个IndexWriter(存放索引文件的目录,分析器,Field的最大长度)   
	  iwriter = new IndexWriter(directory, analyzer,true, IndexWriter.MaxFieldLength.UNLIMITED);   
	  //iwriter.setUseCompoundFile(true);//使用复合文件   
	        
	  Document doc = new Document(); //创建一个Document对象   
	  //把文件路径作为"path"域：不分词,索引,保存   
	  doc.add(new Field("path", src.getCanonicalPath(), Field.Store.YES, Field.Index.NOT_ANALYZED));   
	     
	  StringBuffer sb = new StringBuffer();   
	  BufferedReader br = new BufferedReader(new FileReader(src));   
	  for(String str = null; (str = br.readLine())!=null;){   
	   sb.append(str).append(System.getProperty("line.separator"));    
	  }   
	  //文件内容作为"content"域：分词,索引,保存   
	  doc.add(new Field("contents", sb.toString(), Field.Store.YES, Field.Index.ANALYZED));    
	     
	  iwriter.addDocument(doc); //把Document存放到IndexWriter中   
	  iwriter.optimize();  //对索引进行优化     
	 } catch (IOException e) {   
	  e.printStackTrace();   
	 } finally {    
	  if (iwriter != null) {   
	   try {   
	    iwriter.close(); //关闭IndexWriter时,才把内存中的数据写到文件    
	   } catch (IOException e) {   
	    e.printStackTrace();   
	   }    
	  }   
	  if (directory != null) {   
	   try {   
	    directory.close(); //关闭索引存放目录   
	   } catch (IOException e) {   
	    e.printStackTrace();   
	   }   
	  }   
	 }   
	} 
	
//	keyword要搜索的关键字。indexDir索引存放的目录   
	public static void searcher(String keyword, File indexDir){   
	 IndexSearcher isearcher = null;   
	 Directory directory = null;   
	 try{   
	  Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);   
	  directory = FSDirectory.open(indexDir);   
	     
	  //创建解析器   
	  QueryParser parser = new QueryParser(Version.LUCENE_CURRENT, "contents", analyzer);   
	  Query query = parser.parse(keyword);//获取查询对象   
	        
//	  Query query1 = new TermQuery(new Term("contents", keyword));   
//	  Query query2 = new TermQuery(new Term("contents", keyword2));   
//	  BooleanQuery query = new BooleanQuery();   
//	  query.add(query1, Occur.SHOULD);   
//	  query.add(query2, Occur.SHOULD);   
	     
//	  QueryParser parser = new MultiFieldQueryParser(Version.LUCENE_CURRENT, new String[]{"path", "contents"}, analyzer);   
//	  Query query = parser.parse(keyword);   
	        
	  isearcher = new IndexSearcher(directory, true);  //创建索引搜索器   
	  TopDocs ts = isearcher.search(query, null, 100);  //执行搜索，获取查询结果集对象   
	        
	  int totalHits = ts.totalHits;  //获取命中数   
	  System.out.println("命中数：" + totalHits);   
	        
	  ScoreDoc[] hits = ts.scoreDocs;  //获取命中的文档信息对象   
	  for (int i = 0; i < hits.length; i++) {   
	       Document hitDoc = isearcher.doc(hits[i].doc); //根据命中的文档的内部编号获取该文档    
	       System.out.println(hitDoc.getField("contents").stringValue()); //输出该文档指定域的值   
	  }   
	 } catch (IOException e) {   
	  e.printStackTrace();     
	 } catch (ParseException e) {   
	  e.printStackTrace();   
	 } finally {      
	  if (isearcher != null) {      
	   try {      
	    isearcher.close(); //关闭搜索器     
	   } catch (IOException e) {   
	    e.printStackTrace();   
	   }   
	  }   
	  if (directory != null) {      
	   try {      
	    directory.close(); //关闭索引存放目录    
	   } catch (IOException e) {   
	    e.printStackTrace();   
	   }   
	  }   
	 }   
	}  
	
	public static void main(String[] args) {
		//createIndex(new File("e:\\nrnr.txt"),new File("e:\\index"));
		searcher("男人从火星来",new File("e:\\index"));
	}
}
