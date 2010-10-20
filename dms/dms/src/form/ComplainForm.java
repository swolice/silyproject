package form;

import org.apache.struts.action.ActionForm;

public class ComplainForm extends ActionForm {
    /** nullable persistent field */
    private Integer infoId;

    /** nullable persistent field */
    private String content;
    /** nullable persistent field */
    private Integer commentType;

    /** nullable persistent field */
    private Integer viewType;


    /** nullable persistent field */
    private String username;

    /** nullable persistent field */
    private String email;

    /** nullable persistent field */
    private String phone;

	/**
	 * @return the infoId
	 */
	public Integer getInfoId() {
		return infoId;
	}

	/**
	 * @param infoId the infoId to set
	 */
	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the commentType
	 */
	public Integer getCommentType() {
		return commentType;
	}

	/**
	 * @param commentType the commentType to set
	 */
	public void setCommentType(Integer commentType) {
		this.commentType = commentType;
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

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
