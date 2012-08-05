package com.swjsj.searcher;

import org.junit.Test;

public class QueryParseTest {

	@Test
	public void queryParseTest(){
		CustomQueryUtils cqu = new CustomQueryUtils();
//		cqu.SearchByCustQueryParse("public");
//		cqu.SearchByCustQueryParse("size:[1000 TO 5000]");
		cqu.SearchByCustQueryParse("date:[20120512 TO 20121725]");
	}
}
