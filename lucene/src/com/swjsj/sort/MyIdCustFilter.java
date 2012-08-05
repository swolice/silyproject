package com.swjsj.sort;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.Filter;
import org.apache.lucene.util.OpenBitSet;

public class MyIdCustFilter extends Filter {

	// private String[] ids = {"1","2","3","4","7"};

	private FilterAccess fa;

	public MyIdCustFilter(FilterAccess fa) {
		super();
		this.fa = fa;
	}

	@Override
	public DocIdSet getDocIdSet(IndexReader reader) throws IOException {
		OpenBitSet obs = new OpenBitSet(reader.maxDoc());
		if(fa.isSet()){
			set(reader, obs);
		}else{
			clear(reader,obs);
		}
		
		return obs;
	}
	
	public void set(IndexReader reader,OpenBitSet obs){
		try {
			int[] docs = new int[1];
			int[] freqs = new int[1];

			for (String id : fa.getValues()) {
				TermDocs td = reader.termDocs(new Term(fa.getField(), id));
				int count = td.read(docs, freqs);
				if (count == 1) {
					obs.set(td.doc());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void clear(IndexReader reader,OpenBitSet obs){
		try {
			obs.set(0, reader.maxDoc());
			int[] docs = new int[1];
			int[] freqs = new int[1];

			for (String id : fa.getValues()) {
				TermDocs td = reader.termDocs(new Term(fa.getField(), id));
				int count = td.read(docs, freqs);
				if (count == 1) {
					obs.clear(td.doc());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
