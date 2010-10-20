package dms.data;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class SiteCity implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Integer siteId;

    /** nullable persistent field */
    private Integer sortOrder;

    /** nullable persistent field */
    private Integer city;

    /** nullable persistent field */
    private String url;

    /** nullable persistent field */
    private Date createTime;

    /** nullable persistent field */
    private Date editTime;
    
    private Integer service;

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

	/** full constructor */
    public SiteCity(Integer siteId, Integer sortOrder, Integer city, String url, Date createTime, Date editTime) {
        this.siteId = siteId;
        this.sortOrder = sortOrder;
        this.city = city;
        this.url = url;
        this.createTime = createTime;
        this.editTime = editTime;
    }

    /** default constructor */
    public SiteCity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSiteId() {
        return this.siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getCity() {
        return this.city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof SiteCity) ) return false;
        SiteCity castOther = (SiteCity) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}
