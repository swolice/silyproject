package dms.data;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Rules implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Date createTime;

    /** nullable persistent field */
    private Date editTime;

    /** nullable persistent field */
    private Integer domainId;

    /** nullable persistent field */
    private Integer city;

    /** nullable persistent field */
    private String ruleXml;

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

    /** nullable persistent field */
    private Integer activeFlag;

    /** nullable persistent field */
    private Integer deleteFlag;

    /** full constructor */
    public Rules(Date createTime, Date editTime, Integer domainId, Integer city, String ruleXml, String pro1, String pro2, String pro3, String pro4, String pro5, Integer activeFlag, Integer deleteFlag) {
        this.createTime = createTime;
        this.editTime = editTime;
        this.domainId = domainId;
        this.city = city;
        this.ruleXml = ruleXml;
        this.pro1 = pro1;
        this.pro2 = pro2;
        this.pro3 = pro3;
        this.pro4 = pro4;
        this.pro5 = pro5;
        this.activeFlag = activeFlag;
        this.deleteFlag = deleteFlag;
    }

    /** default constructor */
    public Rules() {
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

    public Integer getDomainId() {
        return this.domainId;
    }

    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }

    public Integer getCity() {
        return this.city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public String getRuleXml() {
        return this.ruleXml;
    }

    public void setRuleXml(String ruleXml) {
        this.ruleXml = ruleXml;
    }

    public String getPro1() {
        return this.pro1;
    }

    public void setPro1(String pro1) {
        this.pro1 = pro1;
    }

    public String getPro2() {
        return this.pro2;
    }

    public void setPro2(String pro2) {
        this.pro2 = pro2;
    }

    public String getPro3() {
        return this.pro3;
    }

    public void setPro3(String pro3) {
        this.pro3 = pro3;
    }

    public String getPro4() {
        return this.pro4;
    }

    public void setPro4(String pro4) {
        this.pro4 = pro4;
    }

    public String getPro5() {
        return this.pro5;
    }

    public void setPro5(String pro5) {
        this.pro5 = pro5;
    }

    public Integer getActiveFlag() {
        return this.activeFlag;
    }

    public void setActiveFlag(Integer activeFlag) {
        this.activeFlag = activeFlag;
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
        if ( !(other instanceof Rules) ) return false;
        Rules castOther = (Rules) other;
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
