/** 
 * 文件名：		IndexFiles.java 
 * 
 * 版本信息: 	v1.0
 * 日期：		2011-6-9 
 * Copyright:  	Copyright(c) 2010
 * Corporation:	2011 
 * Company：		广州正道科技有限公司  
 */
package lucene;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

/**
 * 名称： IndexFiles 描述： 创建人： 吉仕军 创建时间： 2011-6-9 下午06:03:59 修改人： 修改时间： 修改备注：
 * 
 * @version 1.0
 */
public class IndexFiles1 {

	public static void main(String[] args) throws CorruptIndexException,
			LockObtainFailedException, IOException {
		String filePath = "E:/文件夹ftp/JTBNET_BILL_001_BK";
		String indexPath = "E:/index";
		IndexWriter writer;
		// 用指定的语言分析器构造一个新的写索引器（第3个参数表示是否为追加索引）
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_32,
				new SimpleAnalyzer());
		SimpleFSDirectory dereDirectory = new SimpleFSDirectory(new File(
				indexPath));
		writer = new IndexWriter(dereDirectory, iwc);
		
		File file = new File(filePath);
		File[] files = file.listFiles();
		for (int i = 1; i < files.length; i++) {
			if(files[i].isDirectory()){
				continue;
			}
			System.out.println("Indexing file " + files[i]);
			InputStream is = new FileInputStream(files[i]);

			// 构造包含2个字段Field的Document对象
			// 一个是路径path字段，不索引，只存储
			// 一个是内容body字段，进行全文索引，并存储
			Document doc = new Document();
			doc.add(new Field("path", files[i].getAbsolutePath().getBytes()));
			doc.add(new Field("body", (Reader) new InputStreamReader(is)));
			// 将文档写入索引
			writer.addDocument(doc);
			is.close();
		}
		// 关闭写索引器
		writer.close();
	}

	public void search(File indexDir, String q) throws Exception {

		Directory fsDir = FSDirectory.open(indexDir);
		IndexSearcher is = new IndexSearcher(fsDir);
		QueryParser parser = new QueryParser(Version.LUCENE_32, "body", new SimpleAnalyzer());
		Query  query = parser.parse("001");

		long start = new Date().getTime();
		TopDocs  tdocs = is.search(query, 0);
		ScoreDoc[] scoreDocs = tdocs.scoreDocs;
		long end = new Date().getTime();
		for (int i = 0; i < scoreDocs.length; i++) {
//			Document doc = scoreDocs[i];
//			System.out.println(doc.get("filename"));
		}
	}
}
