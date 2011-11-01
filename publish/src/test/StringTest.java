package test;

import java.io.IOException;

import com.sily.util.HtmlUtils;

import sun.misc.BASE64Decoder;

public class StringTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String sssString = "f:/test.xml";
//		System.out.println(sssString.lastIndexOf("."));
//		System.out.println(sssString.substring(sssString.lastIndexOf(".")));
		
		
//		String ssString = "vbG98LLp0a8ueGxz";
//		BASE64Decoder decoder = new BASE64Decoder();
//		try {
//			byte[] zw = decoder.decodeBuffer(ssString);
//			String ssString2 = new String(zw,"GBK");
//			System.out.println(ssString2);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		String str = "From Evernote: 完美支持10.10，亲测----我的Ubuntu10.04.1 LTS 完美\"苹果\"体验 [迄今为止我最满意的] Clipped from: http://forum.ubuntu.org.cn/viewtopic.php?f=94&t=293842 在10.10下的完美方案，亲测。 PS：神马一键安装的都是浮云,有些小问题要待解决，参见以下步骤 >下载 http://sourceforge.net/projects/macbuntu/files/macbuntu-10.10/v2.3/Macbuntu-10.10.tar.gz/download ，这个是MacBuntu For 10.10安装包，下载解压，得到Macbuntu-10.10目录 >下载 http://sourceforge.net/projects/macbuntu/files/macbuntu-10.10/v2.3/g2gm.tar.gz/download 这个很重要，是Gnome GlobalMenu Patch即全局菜单的补丁包。解压，得到g2gm目录 安装步骤： 1、进入Macbuntu-10.10目录，执行 代码: ./install.sh 注意：不要带sudo , 安装过程中会提示你输入root密码的，字符集默认，Mobbly窗口N，什么firefox,chrome皮肤一切选N（自带的不一定好），完成后如果提示restart重启，可选择N，你要觉得保险一点，可以选择Y 2、进入g2gm目录 ，执行 代码: ./g2gm.sh 注意：不要带sudo ,安装过程中会提示你输入root密码，自动化完成。如果提示restart重启，选择N，可选择N，你要觉得保险一点，可以选择Y 如果安装完成后，发现菜单样式、部份框口的滚动条不是MACOS样式，找到家目录下的.gtkrc-2";
		
		String reStr = HtmlUtils.htmlEscape(str);
		
		System.out.println(reStr);
	}

}
