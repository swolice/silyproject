package dms.data;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Complain implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Integer userId;

    /** nullable persistent field */
    private Integer infoId;

    /** nullable persistent field */
    private String content;
    private String reply;

    /** nullable persistent field */
    private Integer commentType;

    /** nullable persistent field */
    private Integer viewType;

    /** nullable persistent field */
    private Date createTime;

    /** nullable persistent field */
    private Date editTime;

    /** nullable persistent field */
    private Integer auditFlag;

    /** nullable persistent field */
    private String username;

    /** nullable persistent field */
    private String email;

    /** nullable persistent field */
    private String phone;

    /** full constructor */
    public Complain(Integer userId, Integer infoId, String content, Integer commentType, Integer viewType, Date createTime, Date editTime, Integer auditFlag, String username, String email, String phone) {
        this.userId = userId;
        this.infoId = infoId;
        this.content = content;
        this.commentType = commentType;
        this.viewType = viewType;
        this.createTime = createTime;
        this.editTime = editTime;
        this.auditFlag = auditFlag;
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    /** default constructor */
    public Complain() {
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

    public Integer getViewType() {
        return this.viewType;
    }

    public void setViewType(Integer viewType) {
        this.viewType = viewType;
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

    public Integer getAuditFlag() {
        return this.auditFlag;
    }

    public void setAuditFlag(Integer auditFlag) {
        this.auditFlag = auditFlag;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Complain) ) return false;
        Complain castOther = (Complain) other;
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
	 * @return the reply
	 */
	public String getReply() {
		return reply;
	}

	/**
	 * @param reply the reply to set
	 */
	public void setReply(String reply) {
		this.reply = reply;
	}

}
