package form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class ServiceForm extends ActionForm {
	   /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String name;

    /** nullable persistent field */
    private FormFile picFile;

    /** nullable persistent field */
    private String remark;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the picFile
	 */
	public FormFile getPicFile() {
		return picFile;
	}

	/**
	 * @param picFile the picFile to set
	 */
	public void setPicFile(FormFile picFile) {
		this.picFile = picFile;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}


}
