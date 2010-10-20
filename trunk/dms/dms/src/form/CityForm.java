package form;

public class CityForm  extends org.apache.struts.action.ActionForm{
	private Long id;

	/** nullable persistent field */
	private String cityName;

	/** nullable persistent field */
	private Integer sortOrder;
	private Integer hot;

	/** nullable persistent field */
	private String cityGroup;

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
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the sortOrder
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * @return the cityGroup
	 */
	public String getCityGroup() {
		return cityGroup;
	}

	/**
	 * @param cityGroup the cityGroup to set
	 */
	public void setCityGroup(String cityGroup) {
		this.cityGroup = cityGroup;
	}

	/**
	 * @return the hot
	 */
	public Integer getHot() {
		return hot;
	}

	/**
	 * @param hot the hot to set
	 */
	public void setHot(Integer hot) {
		this.hot = hot;
	}

}
