package lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;

/**
 * This class demonstrate the process of creating index with Lucene for text
 * files
 */
public class TxtFileIndexer {
	public static void main(String[] args) throws Exception {
		//indexDir is the directory that hosts Lucene's index files
		File indexDir = new File("E:\\luceneIndex");
		//dataDir is the directory that hosts the text files that to be indexed
		File dataDir = new File("E:\\sily.file");
		Analyzer luceneAnalyzer = new StandardAnalyzer();
		File[] dataFiles = dataDir.listFiles();
		IndexWriter indexWriter = new IndexWriter(indexDir, luceneAnalyzer,
				true);
		long startTime = new Date().getTime();
		for (int i = 0; i < dataFiles.length; i++) {
			if (dataFiles[i].isFile()
					&& dataFiles[i].getName().endsWith(".txt")) {
				System.out.println("Indexing file "
						+ dataFiles[i].getCanonicalPath());
				Document document = new Document();
				try {
					StringBuffer sb = new StringBuffer();
					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(dataFiles[i]),"GB2312"));
					for (String str = null; (str = br.readLine()) != null;) {
						sb.append(str).append(System.getProperty("line.separator"));
					} 
					document.add(new Field("path", dataFiles[i].getCanonicalPath(), Field.Store.YES, Field.Index.NOT_ANALYZED));
					document.add(new Field("contents", sb.toString(), Field.Store.YES, Field.Index.ANALYZED));
					indexWriter.addDocument(document);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		indexWriter.optimize();
		indexWriter.close();
		long endTime = new Date().getTime();

		System.out.println("It takes " + (endTime - startTime)
				+ " milliseconds to create index for the files in directory "
				+ dataDir.getPath());
	}
}
