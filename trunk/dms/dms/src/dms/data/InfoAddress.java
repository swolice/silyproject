package dms.data;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class InfoAddress implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Date createTime;

    /** nullable persistent field */
    private Date editTime;

    /** nullable persistent field */
    private Integer infoId;

    /** nullable persistent field */
    private String address;

    /** nullable persistent field */
    private Integer lon;

    /** nullable persistent field */
    private Integer lat;
    
    private String viewName;

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

	/** full constructor */
    public InfoAddress(Date createTime, Date editTime, Integer infoId, String address, Integer lon, Integer lat) {
        this.createTime = createTime;
        this.editTime = editTime;
        this.infoId = infoId;
        this.address = address;
        this.lon = lon;
        this.lat = lat;
    }

    /** default constructor */
    public InfoAddress() {
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

    public Integer getInfoId() {
        return this.infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public String getAddress() {
    	if (this.address!=null){
    		try{
    			return this.address.replaceAll("\\s","");
    		}catch(Exception e){}
    	}
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getLon() {
        return this.lon;
    }

    public void setLon(Integer lon) {
        this.lon = lon;
    }

    public Integer getLat() {
        return this.lat;
    }

    public void setLat(Integer lat) {
        this.lat = lat;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof InfoAddress) ) return false;
        InfoAddress castOther = (InfoAddress) other;
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
