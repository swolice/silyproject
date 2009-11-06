/*
 * Created on 2009-11-3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package webservice;

/**
 * @author sily
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class UserManager {
	/**
	 * 应用系统验证
	 * 
	 * @param token
	 *            应用系统管理员令牌字符串
	 * @return true - 有对应服务的访问权限 <br>
	 *         false - 无
	 */
	public boolean authenticate(String token) {
		return true;
	}

	/**
	 * 获取用户的详细资料
	 * 
	 * @param user2orgid
	 *            与组织关联的用户ID
	 * @return 用户数据类对象
	 */
	public UserInfo getUserInfo(int user2orgid) {
		UserInfo ui = new UserInfo();
		ui.setAddress("address");
		ui.setAppid(128);
		ui.setBirthday(new java.util.Date());
		ui.setGender(1);
		ui.setLoginname("loginname");
		ui.setNickname("nickname");
		ui.setUser2Orgid(user2orgid);
		ui.setUsername("username");
		ui.setZip("232323");
		return ui;
	}
}
