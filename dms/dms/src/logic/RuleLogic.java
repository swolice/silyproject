package logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import util.DbUtil;
import util.XmlUtil;
import dms.bean.Analyzer;
import dms.data.City;
import dms.data.Info;
import dms.data.Rules;
import dms.data.Sites;
import form.RuleForm;

public class RuleLogic {
	/**
	 * 新增修改
	 * 
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public Rules edit(RuleForm form) throws Exception {
		Rules rule;

		if (form.getId() == null || form.getId().intValue() < 1) {
			rule = new Rules();
		} else {
			rule = get(form.getId());
			if (rule == null) {
				throw new Exception("没有找到该规则！");
			}
		}

		// 复制属性
		try {
			BeanUtils.copyProperties(rule, form);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("规则创建过程中发生错误！");
		}

		Date now = new Date();
		if (form.getId() == null || form.getId().intValue() < 1) {
			rule.setCreateTime(now);
		}
		rule.setEditTime(now);

		if (form.getId() == null || form.getId().intValue() < 1) {
//		StringBuffer xmlBuf = new StringBuffer();
//		xmlBuf.append("<xml>");
//
//		xmlBuf.append("<domain>").append(
//				rule.getDomainId() == null ? "" : rule.getDomainId()).append(
//				"</domain>");
//
//		xmlBuf.append("<city>").append(
//				rule.getCity() == null ? "" : rule.getCity()).append("</city>");
//
//		xmlBuf.append("</xml>");
			
	    Document document=XmlUtil.createDocument();
	    Element xml = document.addElement("xml");
		Element domain = xml.addElement("domain");
		domain.addAttribute("ID","domain");
		domain.setText(rule.getDomainId() == null ? "" : ""+rule.getDomainId());
		Element city = xml.addElement("city");
		city.setText(rule.getCity() == null ? "" : ""+rule.getCity());
		city.addAttribute("ID","city");
		rule.setRuleXml(XmlUtil.toString(document.getRootElement()));
		}

		if (form.getId() == null || form.getId().intValue() < 1) {
			DbUtil.getDao().save(rule);
		} else {
			DbUtil.getDao().update(rule);
		}
		return rule;
	}
	
	
	public Rules copy(Long id) throws Exception {
		Rules rule;

		rule = get(id);
		if (rule == null) {
			throw new Exception("没有找到该规则！");
		}

		rule.setActiveFlag(1);
		DbUtil.getDao().save(rule);
		return rule;
	}
	
	
	public Rules editRule(Long id,Long domainId,Long cityId,String url,String charset) throws Exception{
		Rules rules=get(id);
		
		if (rules==null) throw new Exception("没有找到规则");

		rules.setCity(cityId.intValue());
		rules.setDomainId(domainId.intValue());
		
		String xml=rules.getRuleXml();
		
		Element root=XmlUtil.parse(xml);

		if (root==null) {
			Document document=XmlUtil.createDocument();
			root=document.addElement("xml");
		}

		Element domain=root.elementByID("domain");
		if (domain==null){
			domain=root.addElement("domain");
			domain.addAttribute("ID","domain");
		}
		domain.setText(""+domainId);
		
		Element city=root.elementByID("city");
		if (city==null){
			city=root.addElement("city");
			city.addAttribute("ID","city");
		}
		city.setText(""+cityId);
		
		Element urle=root.elementByID("url");
		if (urle==null){
			urle=root.addElement("url");
			urle.addAttribute("ID","url");
		}
		urle.setText(url);
		
		Element chare=root.elementByID("charset");
		if (chare==null){
			chare=root.addElement("charset");
			chare.addAttribute("ID","charset");
		}
		chare.setText(charset);
		
		rules.setRuleXml(XmlUtil.toString(root));
		
		DbUtil.getDao().update(rules);
		return rules;
	}

	/**
	 * 通过ID获取
	 * 
	 * @param id
	 * @return
	 */
	public Rules get(Long id) {
		try {
			Rules rules = (Rules) DbUtil.getDao().load(Rules.class, id);
			return rules;
		} catch (Exception e) {
			return null;
		}
	}

	public Sites getSite(Long id) {
		try {
			Sites site = (Sites) DbUtil.getDao().load(Sites.class, id);
			return site;
		} catch (Exception e) {
			return null;
		}
	}

	public City getCity(Long id) {
		try {
			City city = (City) DbUtil.getDao().load(City.class, id);
			return city;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 列表
	 * 
	 * @param offset
	 * @param size
	 * @return
	 */
	public List list(int domainId,int city,int activeFlag,int offset, int size) {
		String sql = "select * from rules where 1=1 ";
		List params=new ArrayList();
		
		if (domainId>0){
			sql+=" and domain_id=? ";
			params.add(domainId);
		}
		if (city>0){
			sql+=" and city=? ";
			params.add(city);
		}
		
		if (activeFlag>-1){
			sql+=" and active_flag=? ";
			params.add(activeFlag);
		}
		
		
		sql+=" order by domain_id,city ";

		List<Rules> list = DbUtil.getDao().list(sql, params.toArray(), offset, size,
				new Rules());

		return list;
	}

	/**
	 * 总数
	 * 
	 * @return
	 */
	public Integer count(int domainId,int city,int activeFlag) {
		String sql = "select id from rules where 1=1 ";
		List params=new ArrayList();
		if (domainId>0){
			sql+=" and domain_id=? ";
			params.add(domainId);
		}
		if (city>0){
			sql+=" and city=? ";
			params.add(city);
		}
		if (activeFlag>-1){
			sql+=" and active_flag=? ";
			params.add(activeFlag);
		}
		
		
		return DbUtil.getDao().count(sql, params.toArray());
	}

	public Rules delete(Long id) throws Exception {
		Rules rules = get(id);
		if (rules == null) {
			throw new Exception("没有找到规则");
		}
		DbUtil.getDao().delete(rules);
		return rules;
	}
	
	public Rules deleteRule(Long id,String fid) throws Exception {
		Rules rules = get(id);
		if (rules == null) {
			throw new Exception("没有找到规则");
		}

	String xml=rules.getRuleXml();
		
		Element root=XmlUtil.parse(xml);

		if (root==null) {
			Document document=XmlUtil.createDocument();
			root=document.addElement("xml");
		}
		
		Element src=root.elementByID(fid);

		if (src!=null){
			src.getParent().remove(src);
		}
		
		rules.setRuleXml(XmlUtil.toString(root));
		DbUtil.getDao().update(rules);
		return rules;
	}
	

	public String generateId(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyMMddHHmmss");
		return "i"+sdf.format(new Date());
	}
	
	public Rules addSeg(Long id,String fid,Integer ftype,Integer isNew,Integer isLoop,String start,String end) throws Exception {
		Rules rules = get(id);
		if (rules==null) throw new Exception("没有找到规则");

		String xml=rules.getRuleXml();
		
		Element root=XmlUtil.parse(xml);

		if (root==null) {
			Document document=XmlUtil.createDocument();
			root=document.addElement("xml");
		}
		
		Element src=root.elementByID(fid);
		if (fid.equals("0")){
			src=root;
			ftype=0;
		}
		
		if (src!=null){
		Element seg=XmlUtil.createElment("seg");
		
		seg.addAttribute("new",""+isNew);
		seg.addAttribute("loop",""+isLoop);
		seg.addAttribute("ID", generateId());
		Element s=seg.addElement("start");
		s.addCDATA(start);
		Element e=seg.addElement("end");
		e.addCDATA(end);
		
		XmlUtil.addElement(src, seg, ftype);
		}
		
		rules.setRuleXml(XmlUtil.toString(root));
		
		DbUtil.getDao().update(rules);
		return rules;
	}
	
	public Rules editSeg(Long id,String fid,Integer isNew,Integer isLoop,String start,String end) throws Exception {
		Rules rules = get(id);
		if (rules==null) throw new Exception("没有找到规则");

		String xml=rules.getRuleXml();
		
		Element root=XmlUtil.parse(xml);

		if (root==null) {
			Document document=XmlUtil.createDocument();
			root=document.addElement("xml");
		}
		
		Element src=root.elementByID(fid);
		
		if (src!=null){
		src.addAttribute("new",""+isNew);
		src.addAttribute("loop",""+isLoop);
		Element se=src.element("start");
		se.clearContent();
		se.addCDATA(start);
		Element ee=src.element("end");
		ee.clearContent();
		ee.addCDATA(end);
		}
		
		rules.setRuleXml(XmlUtil.toString(root));
		
		DbUtil.getDao().update(rules);
		return rules;
	}
	
	
	public Rules addValue(Long id,String fid,Integer ftype,String name,String reg) throws Exception {
		Rules rules = get(id);
		if (rules==null) throw new Exception("没有找到规则");

		String xml=rules.getRuleXml();
		
		Element root=XmlUtil.parse(xml);

		if (root==null) {
			Document document=XmlUtil.createDocument();
			root=document.addElement("xml");
		}
		
		Element src=root.elementByID(fid);
		if (fid.equals("0")){
			src=root;
			ftype=0;
		}
		
		if (src!=null){
		Element seg=XmlUtil.createElment("value");
		
		seg.addAttribute("ID", generateId());
		Element s=seg.addElement("name");
		s.addCDATA(name);
		Element e=seg.addElement("reg");
		e.addCDATA(reg);
		
		XmlUtil.addElement(src, seg, ftype);
		}
		
		rules.setRuleXml(XmlUtil.toString(root));
		DbUtil.getDao().update(rules);
		return rules;
	}
	
	public Rules editValue(Long id,String fid,String name,String reg) throws Exception {
		Rules rules = get(id);
		if (rules==null) throw new Exception("没有找到规则");

		String xml=rules.getRuleXml();
		
		Element root=XmlUtil.parse(xml);

		if (root==null) {
			Document document=XmlUtil.createDocument();
			root=document.addElement("xml");
		}
		
		Element src=root.elementByID(fid);

		if (src!=null){
		Element ne=src.element("name");
		ne.clearContent();
		ne.addCDATA(name);
		Element re=src.element("reg");
		re.clearContent();
//		System.out.println("reg : "+reg);
		
		
		re.addCDATA(reg);
		}
		
		rules.setRuleXml(XmlUtil.toString(root));
		DbUtil.getDao().update(rules);
		return rules;
	}
	
	public Rules addSpider(Long id,String fid,Integer ftype,Integer isNew,String reg) throws Exception {
		Rules rules = get(id);
		if (rules==null) throw new Exception("没有找到规则");

		String xml=rules.getRuleXml();
		
		Element root=XmlUtil.parse(xml);

		if (root==null) {
			Document document=XmlUtil.createDocument();
			root=document.addElement("xml");
		}
		
		Element src=root.elementByID(fid);
		if (fid.equals("0")){
			src=root;
			ftype=0;
		}
		if (src!=null){
		Element seg=XmlUtil.createElment("spider");
		
		seg.addAttribute("ID", generateId());
		seg.addAttribute("new",""+isNew);
		Element e=seg.addElement("reg");
		e.addCDATA(reg);
		
		XmlUtil.addElement(src, seg, ftype);
		}
		
		rules.setRuleXml(XmlUtil.toString(root));
		DbUtil.getDao().update(rules);
		return rules;
	}
	
	public Rules editSpider(Long id,String fid,Integer isNew,String reg) throws Exception {
		Rules rules = get(id);
		if (rules==null) throw new Exception("没有找到规则");

		String xml=rules.getRuleXml();
		
		Element root=XmlUtil.parse(xml);

		if (root==null) {
			Document document=XmlUtil.createDocument();
			root=document.addElement("xml");
		}
		
		Element src=root.elementByID(fid);
	
		if (src!=null){
		src.addAttribute("new",""+isNew);
		Element re=src.element("reg");
		re.clearContent();
		re.addCDATA(reg);
		
		}
		
		rules.setRuleXml(XmlUtil.toString(root));
		DbUtil.getDao().update(rules);
		return rules;
	}
	
	public List<Info> crawl(Long id){
		Rules rules=get(id);
		
		Analyzer analyzer=new Analyzer(rules.getRuleXml(),rules.getDomainId().longValue(),rules.getCity().longValue(),rules.getId());
		analyzer.analyze();
		
		List<Info> list=analyzer.getInfoList();
		return list;
	}
	
}
