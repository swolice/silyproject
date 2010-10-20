package form;

import org.apache.struts.action.ActionForm;

public class AddressForm extends ActionForm{
	private Long id;
	
	   private Integer infoId;

	    /** nullable persistent field */
	    private String address;

	    /** nullable persistent field */
	    private Integer lon;

	    /** nullable persistent field */
	    private Integer lat;

	    private String viewName;

	/**
		 * @return the viewName
		 */
		public String getViewName() {
			return viewName;
		}

		/**
		 * @param viewName the viewName to set
		 */
		public void setViewName(String viewName) {
			this.viewName = viewName;
		}

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
		 * @return the address
		 */
		public String getAddress() {
			return address;
		}

		/**
		 * @param address the address to set
		 */
		public void setAddress(String address) {
			this.address = address;
		}

		/**
		 * @return the lon
		 */
		public Integer getLon() {
			return lon;
		}

		/**
		 * @param lon the lon to set
		 */
		public void setLon(Integer lon) {
			this.lon = lon;
		}

		/**
		 * @return the lat
		 */
		public Integer getLat() {
			return lat;
		}

		/**
		 * @param lat the lat to set
		 */
		public void setLat(Integer lat) {
			this.lat = lat;
		}

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

}
