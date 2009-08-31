package cn.com.yumincun;

import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.parserapplications.StringExtractor;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * 使用HtmlParser抓去网页内容: 要抓去页面的内容最方便的方法就是使用StringBean. 里面有几个控制页面内容的几个参数.
 * 在后面的代码中会有说明. Htmlparser包中还有一个示例StringExtractor 里面有个直接得到内容的方法,
 * 其中也是使用了StringBean . 另外直接解析Parser的每个标签也可以的.
 * 
 * @author chenguoyong
 * 
 */
public class GetContent {
	
	public static void main(String[] args) {
		new GetContent().getContentUsingParser("http://www.baidu.com");
	}
	
	public void getContentUsingStringBean(String url) {
		StringBean sb = new StringBean();
		sb.setLinks(true); // 是否显示web页面的连接(Links)
		// 为了取得页面的整洁美观一般设置上面两项为true , 如果要保持页面的原有格式, 如代码页面的空格缩进 可以设置为false
		sb.setCollapse(true); // 如果是true的话把一系列空白字符用一个字符替代.
		sb.setReplaceNonBreakingSpaces(true);// If true regular space
		sb.setURL(url);
		System.out.println("The Content is :\n" + sb.getStrings());

	}

	public void getContentUsingStringExtractor(String url, boolean link) {
		// StringExtractor内部机制和上面的一样.做了一下包装
		StringExtractor se = new StringExtractor(url);
		String text = null;
		try {
			text = se.extractStrings(link);
			System.out.println("The content is :\n" + text);
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	public void getContentUsingParser(String url) {
		NodeList nl;
		try {
			Parser p = new Parser(url);
			nl = p.parse(new NodeClassFilter(BodyTag.class));
			BodyTag bt = (BodyTag) nl.elementAt(0);
			System.out.println(bt.toPlainTextString()); // 保留原来的内容格式. 包含js代码
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}
}