package dms.data;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Comment implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Integer userId;

    /** nullable persistent field */
    private Integer infoId;

    /** nullable persistent field */
    private String content;

    private Integer auditFlag;
    /** nullable persistent field */
    private Integer commentType;//0 团购评论1网站评论

    private Integer viewType;//0中评 1差评 2好评
    /** nullable persistent field */
    private Date createTime;

    /** nullable persistent field */
    private Date editTime;

    /** full constructor */
    public Comment(Integer userId, Integer infoId, String content, Integer commentType, Date createTime, Date editTime) {
        this.userId = userId;
        this.infoId = infoId;
        this.content = content;
        this.commentType = commentType;
        this.createTime = createTime;
        this.editTime = editTime;
    }

    /** default constructor */
    public Comment() {
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

    public Integer getCommentType() {
        return this.commentType;
    }

    public void setCommentType(Integer commentType) {
        this.commentType = commentType;
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
        if ( !(other instanceof Comment) ) return false;
        Comment castOther = (Comment) other;
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
	 * @return the auditFlag
	 */
	public Integer getAuditFlag() {
		return auditFlag;
	}

	/**
	 * @param auditFlag the auditFlag to set
	 */
	public void setAuditFlag(Integer auditFlag) {
		this.auditFlag = auditFlag;
	}

	/**
	 * @return the viewType
	 */
	public Integer getViewType() {
		return viewType;
	}

	/**
	 * @param viewType the viewType to set
	 */
	public void setViewType(Integer viewType) {
		this.viewType = viewType;
	}

}
