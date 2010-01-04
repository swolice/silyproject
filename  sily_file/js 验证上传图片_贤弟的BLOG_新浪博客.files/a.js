(function (){
    function ld(url){
      document.createElement("img").src = url;
    }

	// ld("http://counter2.blog.sina.com.cn:8012/key="+uid.substr(1,3));
	// ld("http://counter2.blog.sina.com.cn:8012/key="+uid.substr(1,3));
	// ld("http://counter2.blog.sina.com.cn:8012/key="+uid.substr(1,3));
	// ld("http://counter2.blog.sina.com.cn:8012/key="+uid.substr(1,3));
	 ld("http://counter1.blog.sina.com.cn:9080/t_n_cahce.xml?uid="+uid);
	// ld("http://counter1.blog.sina.com.cn:9080/t_n_cahce.xml?uid="+uid+"&test=1");
	// ld("http://counter1.blog.sina.com.cn:9080/t_n_cahce.xml?uid="+uid+"&test=2");
    // /*ld("http://counter2.blog.sina.com.cn:8888/bpv?t=n&"+uid); */

	/* space fbml test */
	//ld("http://218.30.114.160/openapi/test/test_fbml.php");

	// /* test of new bpv module */
	// /* nginx module */
    // ld("http://counter2.blog.sina.com.cn:8078/h2t?uid="+uid.replace(/_/g,""));
	// var ranuid="abc"+Math.floor(Math.random()*1000);
	// if (Math.random()>0.9) ld("http://counter2.blog.sina.com.cn:8075/uic?type=dom&uid=1255936364&dom=" + ranuid + "dom");
})();
