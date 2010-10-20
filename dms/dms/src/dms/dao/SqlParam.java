package dms.dao;

import java.util.*;

public class SqlParam {
	private Map entity;
	private Map join;
	private Map preparParam;

	public SqlParam() {
		entity = new HashMap();
		join = new HashMap();
		preparParam = new HashMap();
	}

	// obj仅String和Class
	public void addEntity(String alias, Object obj) {
		entity.put(alias, obj);
	}

	public void addJoin(String alias, String relation) {
		join.put(alias, relation);
	}

	public Map getEntity() {
		return entity;
	}

	public Map getJoin() {
		return join;
	}

	public Map getPreparParam() {
		return preparParam;
	}
	// indexOrName仅String和Integer
	public void addPreparParam(Object indexOrName, Object value) {
		preparParam.put(indexOrName, value);
	}

}
