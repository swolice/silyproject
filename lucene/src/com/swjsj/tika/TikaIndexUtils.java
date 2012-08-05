package com.swjsj.tika;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.ParsingReader;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import ucar.nc2.util.xml.Parse;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;

public class TikaIndexUtils {
	private Directory dir;
	
	public TikaIndexUtils() {
		try {
			dir = FSDirectory.open(new File(
					"H:/workspace2/lucene/indexs03"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void index() {
		try {
			
			IndexWriter wirter = new IndexWriter(dir,
					new IndexWriterConfig(Version.LUCENE_36, new MMSegAnalyzer()));
			wirter.deleteAll();
			File[] files = new File("H:/python").listFiles();
			Document doc = null;
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				doc =  new Document();
				if(file.getName().endsWith(".pdf")){
					Reader reader = file2txt(file);
					if(null != reader){
						doc.add(new Field("content",reader));
						doc.add(new Field("filename", file.getName(), Field.Store.YES,
								Field.Index.NOT_ANALYZED));
						System.out.println( file.getAbsolutePath());
						doc.add(new Field("path", file.getAbsolutePath(),
								Field.Store.YES, Field.Index.NOT_ANALYZED));
						doc.add(new NumericField("size",Field.Store.YES,true).setDoubleValue(file.length()));
						doc.add(new NumericField("date",Field.Store.YES,true).setLongValue(file.lastModified()));
					}
				}else{
					doc.add(new Field("content", new FileReader(file)));
					doc.add(new Field("filename", file.getName(), Field.Store.YES,
							Field.Index.NOT_ANALYZED));
					doc.add(new Field("path", file.getAbsolutePath(),
							Field.Store.YES, Field.Index.NOT_ANALYZED));
					doc.add(new NumericField("size",Field.Store.YES,true).setDoubleValue(file.length()));
					doc.add(new NumericField("date",Field.Store.YES,true).setLongValue(file.lastModified()));
				}
				wirter.addDocument(doc);
			}
			wirter.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public Reader tikaTool(File f){
		Tika tk = new Tika();
		try {
			return tk.parse(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Reader file2txt(File f){
		AutoDetectParser parser = new AutoDetectParser();
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(f);
			Metadata metadata=  new Metadata();
			ParseContext context = new ParseContext();
			context.set(Parser.class, parser);
			return new ParsingReader(parser, stream, metadata, context);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(null != stream)stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	

}
