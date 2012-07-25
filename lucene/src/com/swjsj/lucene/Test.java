package com.swjsj.lucene;

public class Test {

	@org.junit.Test
	public void testLucene(){
		HelloLucene hl = new HelloLucene();
		hl.index();
	}
	
	@org.junit.Test
	public void testSearch(){
		HelloLucene hl = new HelloLucene();
		hl.search();
	}
	
	@org.junit.Test
	public void testDelDoc(){
		IndexUtil iu = new IndexUtil();
		iu.delDoc();
	}
	
	@org.junit.Test
	public void testIndex(){
		IndexUtil iu = new IndexUtil();
		iu.index();
		iu.delDoc();
		iu.query();
	}
	
	@org.junit.Test
	public void queryIndex(){
		IndexUtil iu = new IndexUtil();
		iu.query();
	}
	
	@org.junit.Test
	public void querySearch(){
		IndexUtil iu = new IndexUtil();
		iu.search();
		
	}
}
