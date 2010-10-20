package dms.data;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class City implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Date createTime;

    /** nullable persistent field */
    private Date editTime;

    /** nullable persistent field */
    private String cityName;

    /** nullable persistent field */
    private Integer sortOrder;
    
    private Integer hot;

    /** nullable persistent field */
    private String cityGroup;

    /** full constructor */
    public City(Date createTime, Date editTime, String cityName, Integer sortOrder, String cityGroup) {
        this.createTime = createTime;
        this.editTime = editTime;
        this.cityName = cityName;
        this.sortOrder = sortOrder;
        this.cityGroup = cityGroup;
    }

    /** default constructor */
    public City() {
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

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getCityGroup() {
        return this.cityGroup;
    }

    public void setCityGroup(String cityGroup) {
        this.cityGroup = cityGroup;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof City) ) return false;
        City castOther = (City) other;
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
	 * @return the hot
	 */
	public Integer getHot() {
		return hot;
	}

	/**
	 * @param hot the hot to set
	 */
	public void setHot(Integer hot) {
		this.hot = hot;
	}

}
