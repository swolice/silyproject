package dms.data;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class IpAction implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Integer actionType;

    /** nullable persistent field */
    private Integer targetId;

    /** nullable persistent field */
    private String ip;

    /** nullable persistent field */
    private Date createTime;

    /** full constructor */
    public IpAction(Integer actionType, Integer targetId, String ip, Date createTime) {
        this.actionType = actionType;
        this.targetId = targetId;
        this.ip = ip;
        this.createTime = createTime;
    }

    /** default constructor */
    public IpAction() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getActionType() {
        return this.actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public Integer getTargetId() {
        return this.targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
        if ( !(other instanceof IpAction) ) return false;
        IpAction castOther = (IpAction) other;
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
