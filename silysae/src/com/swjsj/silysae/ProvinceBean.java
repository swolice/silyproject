package com.swjsj.silysae;

import java.util.ArrayList;
import java.util.List;

public class ProvinceBean {

	private String pcode;
	
	public ProvinceBean(String pcode, String pname, String pnameAb) {
		this.pcode = pcode;
		this.pname = pname;
		this.pnameAb = pnameAb;
	}
	
	private String pname;
	private String pnameAb;
	
	private List<StationBean> stationlist = new ArrayList<StationBean>();

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public String getPcode() {
		return pcode;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public void setStationlist(List<StationBean> stationlist) {
		this.stationlist = stationlist;
	}
	public List<StationBean> getStationlist() {
		return stationlist;
	}
	public void setPnameAb(String pnameAb) {
		this.pnameAb = pnameAb;
	}
	public String getPnameAb() {
		return pnameAb;
	}
	
}
