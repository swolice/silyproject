package dms.data;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Vote implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Integer userId;

    /** nullable persistent field */
    private Integer infoId;

    /** nullable persistent field */
    private String content;

    /** nullable persistent field */
    private Integer voteType;

    /** nullable persistent field */
    private Date createTime;

    /** nullable persistent field */
    private Date editTime;

    /** full constructor */
    public Vote(Integer userId, Integer infoId, String content, Integer voteType, Date createTime, Date editTime) {
        this.userId = userId;
        this.infoId = infoId;
        this.content = content;
        this.voteType = voteType;
        this.createTime = createTime;
        this.editTime = editTime;
    }

    /** default constructor */
    public Vote() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getInfoId() {
        return this.infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getVoteType() {
        return this.voteType;
    }

    public void setVoteType(Integer voteType) {
        this.voteType = voteType;
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
        if ( !(other instanceof Vote) ) return false;
        Vote castOther = (Vote) other;
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
