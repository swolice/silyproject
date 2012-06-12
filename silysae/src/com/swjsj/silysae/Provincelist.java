package com.swjsj.silysae;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Provincelist {

	private List<ProvinceBean> provinceList = new ArrayList<ProvinceBean>();
	
	public void add(String pcode,String pname,String pname_ab){
		ProvinceBean d = new ProvinceBean(pcode,pname,pname_ab);
		getProvinceList().add(d);
	}
	
	public void addStation(String scode,String sname,String pcode,int stype,String pinyin){
		List<StationBean> sblist = this.getStationBeanList(pcode);
		if(sblist == null){
			return;
		}
		StationBean sb = new StationBean(scode, sname, pcode, stype, pinyin);
		sblist.add(sb);
	}
	
	public List<StationBean> getStationBeanList(String pcode){
		for(ProvinceBean pb : getProvinceList()){
			if(pcode.equals(pb.getPcode())){
				return pb.getStationlist();
			}
		}
		return null;
	}

	public void setProvinceList(List<ProvinceBean> provinceList) {
		this.provinceList = provinceList;
	}

	public List<ProvinceBean> getProvinceList() {
		return provinceList;
	}
}
