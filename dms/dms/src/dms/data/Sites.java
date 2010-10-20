package dms.data;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Sites implements Serializable {

    /** identifier field */
    private Long id;
    private Integer positiveCount;
    private Integer moderateCount;
    private Integer negativeCount;
    private Integer clickCount;
    
    /** nullable persistent field */
    private Date createTime;

    /** nullable persistent field */
    private Date editTime;

    /** nullable persistent field */
    private String siteName;
    private String picUrl;

    /** nullable persistent field */
    private String webPage;

    /** nullable persistent field */
    private String domain;

    private String address;
    /** nullable persistent field */
    private String configXml;

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

    /** nullable persistent field */
    private Integer deleteFlag;

    private Integer promoteCount;
    private Integer commentCount;
    
    
    /**
	 * @return the promoteCount
	 */
	public Integer getPromoteCount() {
		return promoteCount;
	}

	/**
	 * @param promoteCount the promoteCount to set
	 */
	public void setPromoteCount(Integer promoteCount) {
		this.promoteCount = promoteCount;
	}

	/**
	 * @return the commentCount
	 */
	public Integer getCommentCount() {
		return commentCount;
	}

	/**
	 * @param commentCount the commentCount to set
	 */
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	/** full constructor */
    public Sites(Date createTime, Date editTime, String siteName, String webPage, String domain, String configXml, String onlineTime, String purchaseType, String siteType, String frequency, String averagePurchaser, String brief, String email, String qq, String msn, String phone, Integer viewFlag, Integer deleteFlag) {
        this.createTime = createTime;
        this.editTime = editTime;
        this.siteName = siteName;
        this.webPage = webPage;
        this.domain = domain;
        this.configXml = configXml;
        this.onlineTime = onlineTime;
        this.purchaseType = purchaseType;
        this.siteType = siteType;
        this.frequency = frequency;
        this.averagePurchaser = averagePurchaser;
        this.brief = brief;
        this.email = email;
        this.qq = qq;
        this.msn = msn;
        this.phone = phone;
        this.viewFlag = viewFlag;
        this.deleteFlag = deleteFlag;
    }

    /** default constructor */
    public Sites() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEditTime() {
        return this.editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getSiteName() {
        return this.siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getWebPage() {
        return this.webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getConfigXml() {
        return this.configXml;
    }

    public void setConfigXml(String configXml) {
        this.configXml = configXml;
    }

    public String getOnlineTime() {
        return this.onlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

    public String getPurchaseType() {
        return this.purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getSiteType() {
        return this.siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    public String getFrequency() {
        return this.frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getAveragePurchaser() {
        return this.averagePurchaser;
    }

    public void setAveragePurchaser(String averagePurchaser) {
        this.averagePurchaser = averagePurchaser;
    }



    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
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

	public String getQq() {
        return this.qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMsn() {
        return this.msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getViewFlag() {
        return this.viewFlag;
    }

    public void setViewFlag(Integer viewFlag) {
        this.viewFlag = viewFlag;
    }

    public Integer getDeleteFlag() {
        return this.deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Sites) ) return false;
        Sites castOther = (Sites) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	/**
	 * @return the picUrl
	 */
	public String getPicUrl() {
		return picUrl;
	}

	/**
	 * @param picUrl the picUrl to set
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

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
	 * @return the positiveCount
	 */
	public Integer getPositiveCount() {
		return positiveCount;
	}

	/**
	 * @param positiveCount the positiveCount to set
	 */
	public void setPositiveCount(Integer positiveCount) {
		this.positiveCount = positiveCount;
	}

	/**
	 * @return the moderateCount
	 */
	public Integer getModerateCount() {
		return moderateCount;
	}

	/**
	 * @param moderateCount the moderateCount to set
	 */
	public void setModerateCount(Integer moderateCount) {
		this.moderateCount = moderateCount;
	}

	/**
	 * @return the negativeCount
	 */
	public Integer getNegativeCount() {
		return negativeCount;
	}

	/**
	 * @param negativeCount the negativeCount to set
	 */
	public void setNegativeCount(Integer negativeCount) {
		this.negativeCount = negativeCount;
	}

	/**
	 * @return the clickCount
	 */
	public Integer getClickCount() {
		return clickCount;
	}

	/**
	 * @param clickCount the clickCount to set
	 */
	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

}
