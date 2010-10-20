package dms.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import java.util.HashMap;

public interface BaseDao {

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#setQueryCacheRegion(java.lang.String)
	 */
	public abstract void setQueryCacheRegion(String queryCacheRegion);

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#getQueryCacheRegion()
	 */
	public abstract String getQueryCacheRegion();

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#setFetchSize(int)
	 */
	public abstract void setFetchSize(int fetchSize);

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#getFetchSize()
	 */
	public abstract int getFetchSize();

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#setMaxResults(int)
	 */
	public abstract void setMaxResults(int maxResults);

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#getMaxResults()
	 */
	public abstract int getMaxResults();

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#execute(org.springframework.orm.hibernate3.HibernateCallback)
	 */
	public abstract Object execute(HibernateCallback action)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#get(java.lang.Class,
	 *      java.io.Serializable)
	 */
	public abstract Object get(Class entityClass, Serializable id)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#get(java.lang.Class,
	 *      java.io.Serializable, org.hibernate.LockMode)
	 */
	public abstract Object get(final Class entityClass, final Serializable id,
			final LockMode lockMode) throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#load(java.lang.Class,
	 *      java.io.Serializable)
	 */
	public abstract Object load(Class entityClass, Serializable id)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#load(java.lang.Class,
	 *      java.io.Serializable, org.hibernate.LockMode)
	 */
	public abstract Object load(final Class entityClass, final Serializable id,
			final LockMode lockMode) throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#load(java.lang.String,
	 *      java.io.Serializable)
	 */
	public abstract Object load(String entityName, Serializable id)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#load(java.lang.String,
	 *      java.io.Serializable, org.hibernate.LockMode)
	 */
	public abstract Object load(final String entityName, final Serializable id,
			final LockMode lockMode) throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#loadAll(java.lang.Class)
	 */
	public abstract List loadAll(final Class entityClass)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#load(java.lang.Object,
	 *      java.io.Serializable)
	 */
	public abstract void load(final Object entity, final Serializable id)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#lock(java.lang.Object,
	 *      org.hibernate.LockMode)
	 */
	public abstract void lock(final Object entity, final LockMode lockMode)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#lock(java.lang.String,
	 *      java.lang.Object, org.hibernate.LockMode)
	 */
	public abstract void lock(final String entityName, final Object entity,
			final LockMode lockMode) throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#save(java.lang.Object)
	 */
	public abstract Serializable save(final Object entity)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#save(java.lang.String,
	 *      java.lang.Object)
	 */
	public abstract Serializable save(final String entityName,
			final Object entity) throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#update(java.lang.Object)
	 */
	public abstract void update(Object entity) throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#update(java.lang.Object,
	 *      org.hibernate.LockMode)
	 */
	public abstract void update(final Object entity, final LockMode lockMode)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#update(java.lang.String,
	 *      java.lang.Object)
	 */
	public abstract void update(String entityName, Object entity)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#update(java.lang.String,
	 *      java.lang.Object, org.hibernate.LockMode)
	 */
	public abstract void update(final String entityName, final Object entity,
			final LockMode lockMode) throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#saveOrUpdate(java.lang.Object)
	 */
	public abstract void saveOrUpdate(final Object entity)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#saveOrUpdate(java.lang.String,
	 *      java.lang.Object)
	 */
	public abstract void saveOrUpdate(final String entityName,
			final Object entity) throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#saveOrUpdateAll(java.util.Collection)
	 */
	public abstract void saveOrUpdateAll(final Collection entities)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#delete(java.lang.Object)
	 */
	public abstract void delete(Object entity) throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#delete(java.lang.Object,
	 *      org.hibernate.LockMode)
	 */
	public abstract void delete(final Object entity, final LockMode lockMode)
			throws DataAccessException;

	public abstract void flush() throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#deleteAll(java.util.Collection)
	 */
	public abstract void deleteAll(final Collection entities)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#find(java.lang.String)
	 */
	public abstract List find(String queryString) throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#find(java.lang.String,
	 *      java.lang.Object)
	 */
	public abstract List find(String queryString, Object value)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#find(java.lang.String,
	 *      java.lang.Object[])
	 */
	public abstract List find(final String queryString, final Object[] values)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#findByNamedParam(java.lang.String,
	 *      java.lang.String, java.lang.Object)
	 */
	public abstract List findByNamedParam(String queryString, String paramName,
			Object value) throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#findByNamedParam(java.lang.String,
	 *      java.lang.String[], java.lang.Object[])
	 */
	public abstract List findByNamedParam(final String queryString,
			final String[] paramNames, final Object[] values)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#findByValueBean(java.lang.String,
	 *      java.lang.Object)
	 */
	public abstract List findByValueBean(final String queryString,
			final Object valueBean) throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#findByNamedQuery(java.lang.String)
	 */
	public abstract List findByNamedQuery(String queryName)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#findByNamedQuery(java.lang.String,
	 *      java.lang.Object)
	 */
	public abstract List findByNamedQuery(String queryName, Object value)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#findByNamedQuery(java.lang.String,
	 *      java.lang.Object[])
	 */
	public abstract List findByNamedQuery(final String queryName,
			final Object[] values) throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#findByNamedQueryAndNamedParam(java.lang.String,
	 *      java.lang.String, java.lang.Object)
	 */
	public abstract List findByNamedQueryAndNamedParam(String queryName,
			String paramName, Object value) throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#findByNamedQueryAndNamedParam(java.lang.String,
	 *      java.lang.String[], java.lang.Object[])
	 */
	public abstract List findByNamedQueryAndNamedParam(final String queryName,
			final String[] paramNames, final Object[] values)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#findByNamedQueryAndValueBean(java.lang.String,
	 *      java.lang.Object)
	 */
	public abstract List findByNamedQueryAndValueBean(final String queryName,
			final Object valueBean) throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#bulkUpdate(java.lang.String)
	 */
	public abstract int bulkUpdate(String queryString)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#bulkUpdate(java.lang.String,
	 *      java.lang.Object)
	 */
	public abstract int bulkUpdate(String queryString, Object value)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#bulkUpdate(java.lang.String,
	 *      java.lang.Object[])
	 */
	public abstract int bulkUpdate(final String queryString,
			final Object[] values) throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#get(java.lang.String,
	 *      java.io.Serializable)
	 */
	public abstract Object get(String entityName, Serializable id)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#get(java.lang.String,
	 *      java.io.Serializable, org.hibernate.LockMode)
	 */
	public abstract Object get(final String entityName, final Serializable id,
			final LockMode lockMode) throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#persist(java.lang.Object)
	 */
	public abstract void persist(final Object entity)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#persist(java.lang.String,
	 *      java.lang.Object)
	 */
	public abstract void persist(final String entityName, final Object entity)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#findByCriteria(org.hibernate.criterion.DetachedCriteria)
	 */
	public abstract List findByCriteria(DetachedCriteria criteria)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#findByCriteria(org.hibernate.criterion.DetachedCriteria,
	 *      int, int)
	 */
	public abstract List findByCriteria(final DetachedCriteria criteria,
			final int firstResult, final int maxResults)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#findByExample(java.lang.Object)
	 */
	public abstract List findByExample(Object exampleEntity)
			throws DataAccessException;

	/*
	 * (non-Javadoc)
	 *
	 * @see comm.AopDaoInterface#findByExample(java.lang.Object, int,
	 *      int)
	 */
	public abstract List findByExample(Object exampleEntity, int firstResult,
			int maxResults) throws DataAccessException;

	public abstract Object execute(DaoCallback dao) throws DataAccessException;

	public abstract List find(final String hql, final int offset,
			final int length) throws DataAccessException;

	public abstract Object findUniqueResult(final String hql)
			throws DataAccessException;

	public abstract List findBySql(final String sql, final SqlParam param)
			throws DataAccessException;

	// 通过原始SQL查询
	public abstract ResultSet findBySql(final String sql, final int startRow,
			final int maxRow) throws DataAccessException;

	/**
	 * @see 通过原始SQL查询,返回List对象，对象中元素为Map，得到其中的对象需要用map.get("字段名")；
	 * @param sql
	 *            使用原始sql，sql中的所有select都要制定字段名称
	 */
	public abstract List findBySql(final String sql) throws DataAccessException;

	/**
	 * @see 通过原始SQL查询,返回List对象，对象中元素为Map，得到其中的对象需要用map.get("字段名")；
	 * @param sql
	 *            使用原始sql，sql中的所有select都要制定字段名称
	 */
	public abstract List findBySql(final String sql, final Object obj[])
			throws DataAccessException;


	/**
	 * update 根据sql执行数据库的update,delete操作
	 *
	 * @param sql
	 *            String
	 * @return Integer
	 */
	public abstract Integer update(final String sql, final Object[] values);

	/**
	 * list 根据sql执行数据库的查询操作，返回一个Object[]的list
	 *
	 * @param sql
	 *            String
	 * @return List
	 */
	public abstract List list(final String sql, final Object[] values,
			final int offset, final int size);

	/**
	 * list 根据sql执行数局库查询操作，返回obj类型的list 必须保证对象能转换成功
	 *
	 * @param sql
	 *            String
	 * @param obj
	 *            Object
	 * @return List
	 */
	public abstract List list(final String sql, final Object[] values,
			final int offset, final int size, final Object obj);


        /**
         * list 根据sql执行数局库查询操作，返回obj类型的list 必须保证对象能转换成功
         *
         * @param sql
         *            String
         * @param obj
         *            Object
         * @return List
         */
        public abstract List listByHql(final String hql, final Object[] values,
                                  final int offset, final int size);



	public abstract int count(final String sql, final Object[] values);

	public void saveList(final List list) throws DataAccessException;

    /**
     * 批量更新操作
     * 形如 update Table set column1=? where column2=?
     *
     * @param sql String
     * @param values Object[][]
     * @return int[]
     */
    public abstract int[] updateBatch(final String sql,final Object[][]values);

    /**
     * 通过数据库连接直接操作数据库
     * 注意连接千万不能关闭！！！！
     *
     * @param dc DaoConnection
     * @return Object
     */
    public abstract Object updateByConnection(final DaoConnection dc);


    /**
     * 根据sql返回多对象
     *
     * @param sql String
     * @param values Object[]
     * @param offset int
     * @param size int
     * @param map HashMap
     * @return List
     */
    public abstract List list1(final String sql, final Object[] values,
                               final int offset, final int size,
                               final List entryList);

}
