package com.swjsj.searcher;

import org.junit.Test;

public class QueryParseTest {

	@Test
	public void queryParseTest(){
		CustomQueryUtils cqu = new CustomQueryUtils();
		cqu.SearchByCustQueryParse("public");
	}
}
