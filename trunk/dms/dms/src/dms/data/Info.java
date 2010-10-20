package dms.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import dms.bean.Analyzer;

import util.DbUtil;

/** @author Hibernate CodeGenerator */
public class Info implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Date createTime;

    /** nullable persistent field */
    private Date editTime;

    /** nullable persistent field */
    private Integer category;

    /** nullable persistent field */
    private Integer city;

    /** nullable persistent field */
    private Date startTime;

    private String viewName;
    /** nullable persistent field */
    private Date endTime;

    /** nullable persistent field */
    private Integer orgPrice;

    /** nullable persistent field */
    private Integer sellPrice;
    
    private Integer ruleId;
    private Integer endFlag;

    /** nullable persistent field */
    private Integer discount;

    /** nullable persistent field */
    private String title;
    private String picUrl;
    private String url;
    private String url1;

    /** nullable persistent field */
    private Integer siteId;

    /** nullable persistent field */
    private String detail;

    /** nullable persistent field */
    private String address;

    /** nullable persistent field */
    private Integer userCount;

    /** nullable persistent field */
    private Integer viewFlag;

    private Integer promoteCount;
    private Integer commentCount;
    private Integer clickCount;
    
    /** nullable persistent field */
    private Integer deleteFlag;

    /** nullable persistent field */
    private String phone;

    /** nullable persistent field */
    private String webSite;

    /** nullable persistent field */
    private String trafficInfo;

    /** nullable persistent field */
    private String biefe;

    /** nullable persistent field */
    private String latlon;

    /** nullable persistent field */
    private String area;
    
    private Integer ifTest;
    /** nullable persistent field */
    private String pro1;

    /** nullable persistent field */
    private String pro2;

    /** nullable persistent field */
    private String pro3;

    /** nullable persistent field */
    private String pro4;

    /** nullable persistent field */
    private String pro5;

    
    private Integer positiveCount;
    private Integer moderateCount;
    private Integer negativeCount;
    
    
    private Integer service;
    private Integer group;
    
    private List<InfoAddress> addressList;
    
    
    public List<InfoAddress> getAddressList() {
		try {
			String sql = "select * from info_address t where t.info_id=? ";

			return DbUtil.getDao().list(sql, new Object[] { this.getId() }, -1,
					-1, new InfoAddress());
		} catch (Exception e) {
			return new ArrayList<InfoAddress>();
		}
	}

    /**
	 * @return the ifTest
	 */
	public Integer getIfTest() {
		return ifTest;
	}

	/**
	 * @param ifTest the ifTest to set
	 */
	public void setIfTest(Integer ifTest) {
		this.ifTest = ifTest;
	}

	/**
	 * @return the pro1
	 */
	public String getPro1() {
		return pro1;
	}

	/**
	 * @param pro1 the pro1 to set
	 */
	public void setPro1(String pro1) {
		this.pro1 = pro1;
	}

	/**
	 * @return the pro2
	 */
	public String getPro2() {
		return pro2;
	}

	/**
	 * @param pro2 the pro2 to set
	 */
	public void setPro2(String pro2) {
		this.pro2 = pro2;
	}

	/**
	 * @return the pro3
	 */
	public String getPro3() {
		return pro3;
	}

	/**
	 * @param pro3 the pro3 to set
	 */
	public void setPro3(String pro3) {
		this.pro3 = pro3;
	}

	/**
	 * @return the pro4
	 */
	public String getPro4() {
		return pro4;
	}

	/**
	 * @param pro4 the pro4 to set
	 */
	public void setPro4(String pro4) {
		this.pro4 = pro4;
	}

	/**
	 * @return the pro5
	 */
	public String getPro5() {
		return pro5;
	}

	/**
	 * @param pro5 the pro5 to set
	 */
	public void setPro5(String pro5) {
		this.pro5 = pro5;
	}

	/** full constructor */
    public Info(Date createTime, Date editTime, Integer category, Integer city, Date startTime, Date endTime, Integer orgPrice, Integer sellPrice, Integer discount, String title, Integer siteId, String detail, String address, Integer userCount, Integer viewFlag, Integer deleteFlag, String phone, String webSite, String trafficInfo, String biefe, String latlon, String area) {
        this.createTime = createTime;
        this.editTime = editTime;
        this.category = category;
        this.city = city;
        this.startTime = startTime;
        this.endTime = endTime;
        this.orgPrice = orgPrice;
        this.sellPrice = sellPrice;
        this.discount = discount;
        this.title = title;
        this.siteId = siteId;
        this.detail = detail;
        this.address = address;
        this.userCount = userCount;
        this.viewFlag = viewFlag;
        this.deleteFlag = deleteFlag;
        this.phone = phone;
        this.webSite = webSite;
        this.trafficInfo = trafficInfo;
        this.biefe = biefe;
        this.latlon = latlon;
        this.area = area;
    }

    /** default constructor */
    public Info() {
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

    public Integer getCategory() {
        return this.category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getCity() {
        return this.city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public long getEndTime1() {
    	if (this.endTime==null) return new Date().getTime();
        return this.endTime.getTime();
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getOrgPrice() {
        return this.orgPrice;
    }

    public void setOrgPrice(Integer orgPrice) {
        this.orgPrice = orgPrice;
    }

    public Integer getSellPrice() {
        return this.sellPrice;
    }

    public void setSellPrice(Integer sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Integer getDiscount() {
        return this.discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getTitle() {
    	if (this.title!=null){
    		return this.title.replaceAll("\\s","").replaceAll("<[^>]*>","");
    	}
        return this.title;
    }

    public String getTitle1() {
    	if (this.title!=null){
//    		replaceAll("\\s","");
    		
    	}
    	
    	
    	
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSiteId() {
        return this.siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAddress() {
        return this.address;
    }
    
    public String getAddress1() {
    	if (this.address!=null&&this.address.indexOf(Analyzer.seperator)>0){
    		return this.address.substring(0,this.address.indexOf(Analyzer.seperator));
    	}
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getUserCount() {
        return this.userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
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

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebSite() {
        return this.webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getTrafficInfo() {
        return this.trafficInfo;
    }

    public void setTrafficInfo(String trafficInfo) {
        this.trafficInfo = trafficInfo;
    }

    public String getBiefe() {
        return this.biefe;
    }

    public void setBiefe(String biefe) {
        this.biefe = biefe;
    }

    public String getLatlon() {
        return this.latlon;
    }

    public void setLatlon(String latlon) {
        this.latlon = latlon;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Info) ) return false;
        Info castOther = (Info) other;
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

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the viewName
	 */
	public String getViewName() {
		return viewName;
	}

	/**
	 * @param viewName the viewName to set
	 */
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	/**
	 * @return the url1
	 */
	public String getUrl1() {
		return url1;
	}

	/**
	 * @param url1 the url1 to set
	 */
	public void setUrl1(String url1) {
		this.url1 = url1;
	}

	/**
	 * @return the ruleId
	 */
	public Integer getRuleId() {
		return ruleId;
	}

	/**
	 * @param ruleId the ruleId to set
	 */
	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	/**
	 * @return the endFlag
	 */
	public Integer getEndFlag() {
		return endFlag;
	}

	/**
	 * @param endFlag the endFlag to set
	 */
	public void setEndFlag(Integer endFlag) {
		this.endFlag = endFlag;
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
	 * @return the service
	 */
	public Integer getService() {
		return service;
	}

	/**
	 * @param service the service to set
	 */
	public void setService(Integer service) {
		this.service = service;
	}

	/**
	 * @return the group
	 */
	public Integer getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(Integer group) {
		this.group = group;
	}

}
