package com.swjsj.advancedsearch;

public class Test {
	
	@org.junit.Test
	public void testDelDoc(){
		IndexUtil iu = new IndexUtil();
		iu.delDoc();
	}
	
	@org.junit.Test
	public void testIndex(){
		IndexUtil iu = new IndexUtil();
		iu.index();
	}
	
	
	@org.junit.Test
	public void querySearch(){
		IndexUtil iu = new IndexUtil();
		for (int i = 0; i < 6; i++) {
			iu.search();
			System.out.println("=================================");
			if(i==1){
				iu.delDoc();
			}
			if(i==2){
				iu.mayBeRefresh();
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
