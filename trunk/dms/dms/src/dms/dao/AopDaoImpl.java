package dms.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.hibernate.Hibernate;
import java.util.Iterator;


public class AopDaoImpl extends HibernateDaoSupport implements BaseDao {

    public int bulkUpdate(String queryString) throws DataAccessException {
        return getHibernateTemplate().bulkUpdate(queryString);
    }

    public int bulkUpdate(String queryString, Object value) throws
            DataAccessException {
        return getHibernateTemplate().bulkUpdate(queryString, value);
    }

    public int bulkUpdate(String queryString, Object[] values) throws
            DataAccessException {
        return getHibernateTemplate().bulkUpdate(queryString, values);
    }

    public void delete(Object entity) throws DataAccessException {
        getHibernateTemplate().delete(entity);

    }

    public void delete(Object entity, LockMode lockMode) throws
            DataAccessException {
        getHibernateTemplate().delete(entity, lockMode);

    }

    public void deleteAll(Collection entities) throws DataAccessException {
        getHibernateTemplate().deleteAll(entities);

    }

    public Object execute(HibernateCallback action) throws DataAccessException {
        return getHibernateTemplate().execute(action);

    }

    public List find(String queryString) throws DataAccessException {
        return getHibernateTemplate().find(queryString);
    }

    public List find(String queryString, Object value) throws
            DataAccessException {
        return getHibernateTemplate().find(queryString, value);
    }

    public List find(String queryString, Object[] values) throws
            DataAccessException {
        return getHibernateTemplate().find(queryString, values);
    }

    public List findByNamedParam(String queryString, String paramName,
                                 Object value) throws DataAccessException {
        return getHibernateTemplate().findByNamedParam(queryString, paramName,
                value);
    }

    public List findByNamedParam(String queryString, String[] paramNames,
                                 Object[] values) throws DataAccessException {
        return getHibernateTemplate().findByNamedParam(queryString, paramNames,
                values);
    }

    public List findByNamedQuery(String queryName) throws DataAccessException {
        return getHibernateTemplate().findByNamedQuery(queryName);
    }

    public List findByNamedQuery(String queryName, Object value) throws
            DataAccessException {
        return getHibernateTemplate().findByNamedQuery(queryName, value);
    }

    public List findByNamedQuery(String queryName, Object[] values) throws
            DataAccessException {
        return getHibernateTemplate().findByNamedQuery(queryName, values);
    }

    public List findByNamedQueryAndNamedParam(String queryName,
                                              String paramName, Object value) throws
            DataAccessException {
        return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName,
                paramName, value);
    }

    public List findByNamedQueryAndNamedParam(String queryName,
                                              String[] paramNames,
                                              Object[] values) throws
            DataAccessException {
        return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName,
                paramNames, values);
    }

    public List findByNamedQueryAndValueBean(String queryName, Object valueBean) throws
            DataAccessException {
        return getHibernateTemplate().findByNamedQueryAndValueBean(queryName,
                valueBean);
    }

    public List findByValueBean(String queryString, Object valueBean) throws
            DataAccessException {
        return getHibernateTemplate().findByValueBean(queryString, valueBean);
    }

    public Object get(Class entityClass, Serializable id) throws
            DataAccessException {
        return getHibernateTemplate().get(entityClass, id);
    }

    public Object get(Class entityClass, Serializable id, LockMode lockMode) throws
            DataAccessException {
        return getHibernateTemplate().get(entityClass, id, lockMode);
    }

    public int getFetchSize() {
        return getHibernateTemplate().getFetchSize();
    }

    public int getMaxResults() {
        return getHibernateTemplate().getMaxResults();
    }

    public String getQueryCacheRegion() {
        return getHibernateTemplate().getQueryCacheRegion();
    }

    public Object load(Class entityClass, Serializable id) throws
            DataAccessException {
        Object obj = getHibernateTemplate().load(entityClass, id);
        if (!Hibernate.isInitialized(obj)) {
            Hibernate.initialize(obj);
        }
        return obj;
    }

    public Object load(Class entityClass, Serializable id, LockMode lockMode) throws
            DataAccessException {
        return getHibernateTemplate().load(entityClass, id, lockMode);
    }

    public Object load(String entityName, Serializable id) throws
            DataAccessException {
        return getHibernateTemplate().load(entityName, id);
    }

    public Object load(String entityName, Serializable id, LockMode lockMode) throws
            DataAccessException {
        return getHibernateTemplate().load(entityName, id, lockMode);
    }

    public void load(Object entity, Serializable id) throws DataAccessException {
        getHibernateTemplate().load(entity, id);

    }

    public List loadAll(Class entityClass) throws DataAccessException {
        return getHibernateTemplate().loadAll(entityClass);
    }

    public void lock(Object entity, LockMode lockMode) throws
            DataAccessException {
        getHibernateTemplate().lock(entity, lockMode);

    }

    public void lock(String entityName, Object entity, LockMode lockMode) throws
            DataAccessException {
        getHibernateTemplate().lock(entityName, entity, lockMode);

    }

    public Serializable save(Object entity) throws DataAccessException {
        try{
            return getHibernateTemplate().save(entity);
        }finally{
            AopDaoAction.update(entity);
        }
    }

    public Serializable save(String entityName, Object entity) throws
            DataAccessException {
        try{
            return getHibernateTemplate().save(entityName, entity);
         }finally{
            AopDaoAction.update(entity);
        }

    }

    public void saveOrUpdate(Object entity) throws DataAccessException {
        try {
            getHibernateTemplate().saveOrUpdate(entity);
        } finally {
            AopDaoAction.update(entity);
        }
    }

    public void saveOrUpdate(String entityName, Object entity) throws
            DataAccessException {
        try {
            getHibernateTemplate().saveOrUpdate(entityName, entity);
        } finally {
            AopDaoAction.update(entity);
        }
    }

    public void saveOrUpdateAll(Collection entities) throws DataAccessException {
        getHibernateTemplate().saveOrUpdateAll(entities);

    }

    public void setFetchSize(int fetchSize) {
        getHibernateTemplate().setFetchSize(fetchSize);

    }

    public void setMaxResults(int maxResults) {
        getHibernateTemplate().setMaxResults(maxResults);

    }

    public void setQueryCacheRegion(String queryCacheRegion) {
        getHibernateTemplate().setQueryCacheRegion(queryCacheRegion);
    }

    public void update(Object entity) throws DataAccessException {
        try {
            getHibernateTemplate().update(entity);
        } finally {
            AopDaoAction.update(entity);
        }
    }

    public void update(Object entity, LockMode lockMode) throws
            DataAccessException {
        try{
            getHibernateTemplate().update(entity, lockMode);
       }finally{
            AopDaoAction.update(entity);
        }
    }

    public void update(String entityName, Object entity) throws
            DataAccessException {
        try {
            getHibernateTemplate().update(entityName, entity);
        } finally {
            AopDaoAction.update(entity);
        }

    }

    public void update(String entityName, Object entity, LockMode lockMode) throws
            DataAccessException {
        try {
            getHibernateTemplate().update(entityName, entity, lockMode);
        } finally {
            AopDaoAction.update(entity);
        }
    }

    public void flush() throws DataAccessException {
        getHibernateTemplate().flush();

    }

    public List findByCriteria(DetachedCriteria criteria) throws
            DataAccessException {
        return getHibernateTemplate().findByCriteria(criteria);
    }

    public List findByCriteria(DetachedCriteria criteria, int firstResult,
                               int maxResults) throws DataAccessException {
        return getHibernateTemplate().findByCriteria(criteria, firstResult,
                maxResults);
    }

    public List findByExample(Object exampleEntity) throws DataAccessException {
        return getHibernateTemplate().findByExample(exampleEntity);
    }

    public List findByExample(Object exampleEntity, int firstResult,
                              int maxResults) throws DataAccessException {
        return getHibernateTemplate().findByExample(exampleEntity, firstResult,
                maxResults);
    }

    public Object get(String entityName, Serializable id) throws
            DataAccessException {
        return getHibernateTemplate().get(entityName, id);
    }

    public Object get(String entityName, Serializable id, LockMode lockMode) throws
            DataAccessException {
        return getHibernateTemplate().get(entityName, id, lockMode);
    }

    public void persist(Object entity) throws DataAccessException {
        getHibernateTemplate().persist(entity);
    }

    public void persist(String entityName, Object entity) throws
            DataAccessException {
        getHibernateTemplate().persist(entityName, entity);
    }

    // 自定义事务回调函数
    public Object execute(DaoCallback dao) throws DataAccessException {
        return dao.doTrascation(this);
    }

    // 自定义分页函数
    public List find(final String hql, final int offset, final int length) throws
            DataAccessException {
        List list = getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws
                    HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(offset);
                query.setMaxResults(length);
                List list = query.list();
                return list;
            }
        });
        return list;
    }

    // 通过HQL获得唯一值
    public Object findUniqueResult(final String hql) throws DataAccessException {
        Object obj = getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws
                    HibernateException, SQLException {
                Object obj = session.createQuery(hql).uniqueResult();
                return obj;
            }
        });
        return obj;
    }

    // 通过Hibernate SQL查询
    public List findBySql(final String sql, final SqlParam param) throws
            DataAccessException {
        List list = (List) getHibernateTemplate().execute(
                new HibernateCallback() {
            public Object doInHibernate(Session session) throws
                    HibernateException, SQLException {
                SQLQuery query = session.createSQLQuery(sql);
                if (param != null) {
                    Map preparParam = param.getPreparParam();
                    for (Object indexOrName : preparParam.keySet()) {
                        Object value = preparParam.get(indexOrName);
                        String cname = indexOrName.getClass().getName();
                        if (cname.contains("Integer")) {
                            query.setParameter((Integer) indexOrName,
                                               value);
                        } else if (cname.contains("String")) {
                            query.setParameter((String) indexOrName,
                                               value);
                        }
                    }
                    Map entity = param.getEntity();
                    for (Object name : entity.keySet()) {
                        Object obj = entity.get(name);
                        String cname = obj.getClass().getName();
                        if (cname.contains("String")) {
                            query.addEntity((String) name,
                                            (String) entity.get(name));
                        } else if (cname.contains("Class")) {
                            query.addEntity((String) name,
                                            (Class) entity.get(name));
                        }
                    }
                    Map join = param.getJoin();
                    for (Object name : join.keySet()) {
                        query.addJoin((String) name, (String) entity
                                      .get(name));
                    }
                }
                List list = query.list();
                return list;
            }
        });
        return list;
    }

    // 通过原始SQL查询,sql,开始行数与最大行数
    public ResultSet findBySql(final String sql, final int startRow,
                               final int maxRow) throws DataAccessException {
        ResultSet rs = (ResultSet) getHibernateTemplate().execute(
                new HibernateCallback() {
            public Object doInHibernate(Session session) throws
                    HibernateException, SQLException {
                Connection conn = session.connection();
                Statement stmt = conn.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery(sql);
                stmt.addBatch("");
                return rs;
            }
        });
        return rs;
    }

    // 通过原始SQL查询,sql,开始行数与最大行数
    public ResultSet findBySql(final String sql, final int startRow,
                               final int maxRow, final Object obj[]) throws
            DataAccessException {
        ResultSet rs = (ResultSet) getHibernateTemplate().execute(
                new HibernateCallback() {
            public Object doInHibernate(Session session) throws
                    HibernateException, SQLException {
                Connection conn = session.connection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                for (int j = 0; j < obj.length; j++) {
                    stmt.setObject(j + 1, obj[j]);
                }
                ResultSet rs = stmt.executeQuery();
                return rs;
            }
        });
        return rs;
    }

    /**
     * @see 通过原始SQL查询,返回List对象，对象中元素为Map，得到其中的对象需要用map.get("字段名")；
     * @param sql
     *            使用原始sql，sql中的所有select都要制定字段名称
     */
    public List findBySql(final String sql) throws DataAccessException {
        ResultSet rs = findBySql(sql, 1, 1);
        List list = new ArrayList();
        try {
            ResultSetMetaData rsm = rs.getMetaData();
            int colCount = rsm.getColumnCount();
            Map map;
            for (; rs.next(); list.add(map)) {
                map = new HashMap();
                for (int i = 1; i <= colCount; i++) {
                    map
                            .put(rsm.getColumnName(i).toLowerCase(), rs
                                 .getObject(i));
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;

    }

    /**
     * @see 通过原始SQL查询,返回List对象，对象中元素为Map，得到其中的对象需要用map.get("字段名")；
     * @param sql
     *            使用原始sql，sql中的所有select都要制定字段名称
     */
    public List findBySql(final String sql, Object obj[]) throws
            DataAccessException {
        ResultSet rs = findBySql(sql, 1, 1, obj);
        List list = new ArrayList();
        try {
            ResultSetMetaData rsm = rs.getMetaData();
            int colCount = rsm.getColumnCount();
            Map map;
            for (; rs.next(); list.add(map)) {
                map = new HashMap();
                for (int i = 1; i <= colCount; i++) {
                    map
                            .put(rsm.getColumnName(i).toLowerCase(), rs
                                 .getObject(i));
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;

    }

    /**
     * update 根据sql执行数据库的update,delete操作
     *
     * @param sql
     *            String
     * @return Integer
     */
    public Integer update(final String sql, final Object[] values) {
        Integer result = 0;
        result = (Integer) getHibernateTemplate().execute(
                new HibernateCallback() {
            public Object doInHibernate(Session session) throws
                    HibernateException, SQLException {
                Query query = session.createSQLQuery(sql);
                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                        query.setParameter(i, values[i]);
                    }
                }

                return query.executeUpdate();
            }
        });
        return result;
    }

    /**
     * list 根据sql执行数据库的查询操作，返回一个Object[]的list
     *
     * @param sql
     *            String
     * @return List
     */

    public List list(final String sql, final Object[] values,
                     final int offset, final int size) {
        List list = null;
        list = (List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws
                    HibernateException, SQLException {
                Query query = session.createSQLQuery(sql);
                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                        query.setParameter(i, values[i]);
                    }
                }
                if (offset >= 0)
                    query.setFirstResult(offset);
                if (size > 0)
                    query.setMaxResults(size);

                return query.list();
            }
        });
        return list;
    }

    /**
     * list 根据sql执行数局库查询操作，返回obj类型的list 必须保证对象能转换成功
     *
     * @param sql
     *            String
     * @param obj
     *            Object
     * @return List
     */


    public List list(final String sql, final Object[] values,
                     final int offset, final int size, final Object obj) {
        List list = null;
        list = (List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws
                    HibernateException, SQLException {

                Query query = session.createSQLQuery(sql).addEntity(
                        obj.getClass());
                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                        query.setParameter(i, values[i]);
                    }
                }
                if (offset >= 0)
                    query.setFirstResult(offset);
                if (size > 0)
                    query.setMaxResults(size);

                return query.list();
            }
        });

        return list;
    }


    public List listByHql(final String hql, final Object[] values,
                          final int offset, final int size) {
        List list = null;
        list = (List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws
                    HibernateException, SQLException {

                Query query = session.createQuery(hql);
                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                        query.setParameter(i, values[i]);
                    }
                }

                if (offset >= 0)
                    query.setFirstResult(offset);
                if (size > 0)
                    query.setMaxResults(size);

                return query.list();
            }
        });

        return list;
    }

    //批量插入数据
    public void saveList(final List list) throws DataAccessException {
        getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws
                    HibernateException, SQLException {
                for (int i = 0; i < list.size(); i++) {
                    //session.save(list.get(i));
                    session.save(list.get(i));
                }
                session.flush();
                session.clear();
                System.out.println(list.size());
                return null;
            }
        });
    }

    public int count(final String sql, final Object[] values) {
        Integer count = null;
        count = (Integer) getHibernateTemplate().execute(
                new HibernateCallback() {
            public Object doInHibernate(Session session) throws
                    HibernateException, SQLException {
                try {
                    Query query = session
                                  .createSQLQuery("select count(1) from ("
                                                  + sql + ") _countTable ");
                    if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                            query.setParameter(i, values[i]);
                        }
                    }
                    
                    Number count = (Number) query
                                       .uniqueResult();

                    return count.intValue();
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });
        return count.intValue();
    }


    /**
     * 批量更新操作
     * 形如 update Table set column1=? where column2=?
     *
     * @param sql String
     * @param values Object[][]
     * @return int
     */
    public int[] updateBatch(final String sql, final Object[][] values) {
        int[] o = (int[]) getHibernateTemplate().execute(
                new HibernateCallback() {
            public Object doInHibernate(Session session) throws
                    HibernateException, SQLException {
                Connection conn = session.connection();
                PreparedStatement pstmt = conn.prepareStatement(sql);

                for (Object[] objs : values) {
                    for (int i = 0; i < objs.length; i++) {
                        pstmt.setObject(i + 1, objs[i]);
                    }
                    pstmt.addBatch();
                }
                int[] rs = pstmt.executeBatch();
//                for (int r1 : rs) {
//                    System.out.println("r : " + r1);
//                }

                return rs;
            }
        });
        return o;
    }


    public Object updateByConnection(final DaoConnection dc) {
        Object o = getHibernateTemplate().execute(
                new HibernateCallback() {
            public Object doInHibernate(Session session) throws
                    HibernateException, SQLException {
                Connection conn = session.connection();
                return dc.execute(conn);
            }

        });
        return o;
    }


    public List list1(final String sql, final Object[] values,
                      final int offset, final int size,
                      final List entryList) {
        List list = null;
        list = (List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws
                    HibernateException, SQLException {

                SQLQuery query = session.createSQLQuery(sql);

                if (entryList != null) {
                    for (Object obj1:entryList){
                        Object[] os=(Object[])obj1;
                        String key=(String)os[0];
                        Class value=(Class)os[1];
                        query.addEntity(key, value);
                    }

//                    Iterator<String> it = map.keySet().iterator();
//                    while (it.hasNext()) {
//                        String key = it.next();
//                        Class value = map.get(key);
//                        query.addEntity(key, value);
//                    }
                }

                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                        query.setParameter(i, values[i]);
                    }
                }
                if (offset >= 0)
                    query.setFirstResult(offset);
                if (size > 0)
                    query.setMaxResults(size);

                return query.list();
            }
        });

        return list;
    }


}
