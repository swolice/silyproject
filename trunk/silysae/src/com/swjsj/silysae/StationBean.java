package com.swjsj.silysae;

public class StationBean {
	
	private String scode,sname,pcode,pinyin;
	private int stype;
	
	public StationBean(String scode,String sname,String pcode,int stype,String pinyin){
		this.scode=scode;//站点编号
		this.sname=sname;//站点名称
		this.pcode=pcode;//省编号
		this.setStype(stype);//站点行政区域划分类型:0-市级城市；1-省会城市；3-县级城市
		this.pinyin=pinyin;
	}

	public String getScode() {
		return scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public void setStype(int stype) {
		this.stype = stype;
	}

	public int getStype() {
		return stype;
	}

}
