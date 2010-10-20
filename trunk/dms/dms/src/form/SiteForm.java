package form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class SiteForm extends ActionForm {
	/** identifier field */
	private Long id;

	/** nullable persistent field */
	private String siteName;

	/** nullable persistent field */
	private String webPage;

	/** nullable persistent field */
	private String domain;

	/** nullable persistent field */
	private String onlineTime;

	/** nullable persistent field */
	private String purchaseType;

	/** nullable persistent field */
	private String siteType;

	/** nullable persistent field */
	private String frequency;

	/** nullable persistent field */
	private String averagePurchaser;

	/** nullable persistent field */
	private String brief;

	/** nullable persistent field */
	private String email;

	/** nullable persistent field */
	private String qq;

	/** nullable persistent field */
	private String msn;

	/** nullable persistent field */
	private String phone;

	/** nullable persistent field */
	private Integer viewFlag;
	
	private FormFile pic;
	
	  private String address;

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the pic
	 */
	public FormFile getPic() {
		return pic;
	}

	/**
	 * @param pic the pic to set
	 */
	public void setPic(FormFile pic) {
		this.pic = pic;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the siteName
	 */
	public String getSiteName() {
		return siteName;
	}

	/**
	 * @param siteName the siteName to set
	 */
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	/**
	 * @return the webPage
	 */
	public String getWebPage() {
		return webPage;
	}

	/**
	 * @param webPage the webPage to set
	 */
	public void setWebPage(String webPage) {
		this.webPage = webPage;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @return the onlineTime
	 */
	public String getOnlineTime() {
		return onlineTime;
	}

	/**
	 * @param onlineTime the onlineTime to set
	 */
	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}

	/**
	 * @return the purchaseType
	 */
	public String getPurchaseType() {
		return purchaseType;
	}

	/**
	 * @param purchaseType the purchaseType to set
	 */
	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	/**
	 * @return the siteType
	 */
	public String getSiteType() {
		return siteType;
	}

	/**
	 * @param siteType the siteType to set
	 */
	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	/**
	 * @return the frequency
	 */
	public String getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	/**
	 * @return the averagePurchaser
	 */
	public String getAveragePurchaser() {
		return averagePurchaser;
	}

	/**
	 * @param averagePurchaser the averagePurchaser to set
	 */
	public void setAveragePurchaser(String averagePurchaser) {
		this.averagePurchaser = averagePurchaser;
	}

	/**
	 * @return the brief
	 */
	public String getBrief() {
		return brief;
	}

	/**
	 * @param brief the brief to set
	 */
	public void setBrief(String brief) {
		this.brief = brief;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the qq
	 */
	public String getQq() {
		return qq;
	}

	/**
	 * @param qq the qq to set
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	/**
	 * @return the msn
	 */
	public String getMsn() {
		return msn;
	}

	/**
	 * @param msn the msn to set
	 */
	public void setMsn(String msn) {
		this.msn = msn;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the viewFlag
	 */
	public Integer getViewFlag() {
		return viewFlag;
	}

	/**
	 * @param viewFlag the viewFlag to set
	 */
	public void setViewFlag(Integer viewFlag) {
		this.viewFlag = viewFlag;
	}

}
