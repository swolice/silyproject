package cn.com.mianshi;

public class TreeBean {
	
	private String rowId;
	/** 节点ID*/
	private String id = null;
	/** 节点名称*/
	private String text = null;
	/** 是否叶子节点*/
	
	private String isLeaf;
	/** 节点图标*/
	private String icon = null;
	/** 节点样式*/
	private String cls = null;
	/** 节点对应的链接地址*/
	private String linkTo = null;
	
	/** 是否带复选框 */
	private boolean hasCheckBox = false;
	/** 是否被选中 */
	private boolean isChecked = false;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getCls() {
		return cls;
	}
	public void setCls(String cls) {
		this.cls = cls;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public String getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	public boolean isHasCheckBox() {
		return hasCheckBox;
	}
	public void setHasCheckBox(boolean hasCheckBox) {
		this.hasCheckBox = hasCheckBox;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public String getLinkTo() {
		return linkTo;
	}
	public void setLinkTo(String linkTo) {
		this.linkTo = linkTo;
	}
	
	

}
