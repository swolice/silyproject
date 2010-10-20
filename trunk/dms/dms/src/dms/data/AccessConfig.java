package dms.data;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class AccessConfig implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String name;

    /** nullable persistent field */
    private String url;

    /** nullable persistent field */
    private Byte activeFlag;

    /** nullable persistent field */
    private String describer;

    /** nullable persistent field */
    private String operator;

    /** nullable persistent field */
    private Date createTime;

    /** full constructor */
    public AccessConfig(String name, String url, Byte activeFlag, String describer, String operator, Date createTime) {
        this.name = name;
        this.url = url;
        this.activeFlag = activeFlag;
        this.describer = describer;
        this.operator = operator;
        this.createTime = createTime;
    }

    /** default constructor */
    public AccessConfig() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Byte getActiveFlag() {
        return this.activeFlag;
    }

    public void setActiveFlag(Byte activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getDescriber() {
        return this.describer;
    }

    public void setDescriber(String describer) {
        this.describer = describer;
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof AccessConfig) ) return false;
        AccessConfig castOther = (AccessConfig) other;
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
