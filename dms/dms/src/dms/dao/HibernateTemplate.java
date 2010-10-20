/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dms.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.FlushMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.event.EventSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.hibernate3.HibernateAccessor;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateOperations;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.util.Assert;

/**
 * Helper class that simplifies Hibernate data access code. Automatically
 * converts HibernateExceptions into DataAccessExceptions, following the
 * <code>org.springframework.dao</code> exception hierarchy.
 * 
 * <p>
 * The central method is <code>execute</code>, supporting Hibernate access
 * code implementing the {@link HibernateCallback} interface. It provides
 * Hibernate Session handling such that neither the HibernateCallback
 * implementation nor the calling code needs to explicitly care about
 * retrieving/closing Hibernate Sessions, or handling Session lifecycle
 * exceptions. For typical single step actions, there are various convenience
 * methods (find, load, saveOrUpdate, delete).
 * 
 * <p>
 * Can be used within a service implementation via direct instantiation with a
 * SessionFactory reference, or get prepared in an application context and given
 * to services as bean reference. Note: The SessionFactory should always be
 * configured as bean in the application context, in the first case given to the
 * service directly, in the second case to the prepared template.
 * 
 * <p>
 * <b>NOTE: As of Hibernate 3.0.1, transactional Hibernate access code can also
 * be coded in plain Hibernate style. Hence, for newly started projects,
 * consider adopting the standard Hibernate3 style of coding data access objects
 * instead, based on {@link org.hibernate.SessionFactory#getCurrentSession()}.</b>
 * 
 * <p>
 * This class can be considered as direct alternative to working with the raw
 * Hibernate3 Session API (through
 * <code>SessionFactory.getCurrentSession()</code>). The major advantage is
 * its automatic conversion to DataAccessExceptions as well as its capability to
 * fall back to 'auto-commit' style behavior when used outside of transactions.
 * <b>Note that HibernateTemplate will perform its own Session management, not
 * participating in a custom Hibernate CurrentSessionContext unless you
 * explicitly switch {@link #setAllowCreate "allowCreate"} to "false".</b>
 * 
 * <p>
 * {@link LocalSessionFactoryBean} is the preferred way of obtaining a reference
 * to a specific Hibernate SessionFactory, at least in a non-EJB environment.
 * The Spring application context will manage its lifecycle, initializing and
 * shutting down the factory as part of the application.
 * 
 * <p>
 * Note that operations that return an Iterator (i.e. <code>iterate</code>)
 * are supposed to be used within Spring-driven or JTA-driven transactions (with
 * HibernateTransactionManager, JtaTransactionManager, or EJB CMT). Else, the
 * Iterator won't be able to read results from its ResultSet anymore, as the
 * underlying Hibernate Session will already have been closed.
 * 
 * <p>
 * Lazy loading will also just work with an open Hibernate Session, either
 * within a transaction or within OpenSessionInViewFilter/Interceptor.
 * Furthermore, some operations just make sense within transactions, for
 * example: <code>contains</code>, <code>evict</code>, <code>lock</code>,
 * <code>flush</code>, <code>clear</code>.
 * 
 * @author Juergen Hoeller
 * @since 1.2
 * @see #setSessionFactory
 * @see HibernateCallback
 * @see org.hibernate.Session
 * @see LocalSessionFactoryBean
 * @see HibernateTransactionManager
 * @see org.springframework.transaction.jta.JtaTransactionManager
 * @see org.springframework.orm.hibernate3.support.OpenSessionInViewFilter
 * @see org.springframework.orm.hibernate3.support.OpenSessionInViewInterceptor
 */
public class HibernateTemplate extends HibernateAccessor implements
		HibernateOperations {

	private boolean allowCreate = true;

	private boolean alwaysUseNewSession = false;

	private boolean exposeNativeSession = false;

	private boolean checkWriteOperations = true;

	private boolean cacheQueries = false;

	private String queryCacheRegion;

	private int fetchSize = 0;

	private int maxResults = 0;

	/**
	 * Create a new HibernateTemplate instance.
	 */
	public HibernateTemplate() {
	}

	/**
	 * Create a new HibernateTemplate instance.
	 * 
	 * @param sessionFactory
	 *            SessionFactory to create Sessions
	 */
	public HibernateTemplate(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
		afterPropertiesSet();
	}

	/**
	 * Create a new HibernateTemplate instance.
	 * 
	 * @param sessionFactory
	 *            SessionFactory to create Sessions
	 * @param allowCreate
	 *            if a non-transactional Session should be created when no
	 *            transactional Session can be found for the current thread
	 */
	public HibernateTemplate(SessionFactory sessionFactory, boolean allowCreate) {
		setSessionFactory(sessionFactory);
		setAllowCreate(allowCreate);
		afterPropertiesSet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#setAllowCreate(boolean)
	 */
	public void setAllowCreate(boolean allowCreate) {
		this.allowCreate = allowCreate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#isAllowCreate()
	 */
	public boolean isAllowCreate() {
		return this.allowCreate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#setAlwaysUseNewSession(boolean)
	 */
	public void setAlwaysUseNewSession(boolean alwaysUseNewSession) {
		this.alwaysUseNewSession = alwaysUseNewSession;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#isAlwaysUseNewSession()
	 */
	public boolean isAlwaysUseNewSession() {
		return this.alwaysUseNewSession;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#setExposeNativeSession(boolean)
	 */
	public void setExposeNativeSession(boolean exposeNativeSession) {
		this.exposeNativeSession = exposeNativeSession;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#isExposeNativeSession()
	 */
	public boolean isExposeNativeSession() {
		return this.exposeNativeSession;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#setCheckWriteOperations(boolean)
	 */
	public void setCheckWriteOperations(boolean checkWriteOperations) {
		this.checkWriteOperations = checkWriteOperations;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#isCheckWriteOperations()
	 */
	public boolean isCheckWriteOperations() {
		return this.checkWriteOperations;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#setCacheQueries(boolean)
	 */
	public void setCacheQueries(boolean cacheQueries) {
		this.cacheQueries = cacheQueries;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#isCacheQueries()
	 */
	public boolean isCacheQueries() {
		return this.cacheQueries;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#setQueryCacheRegion(java.lang.String)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#setQueryCacheRegion(java.lang.String)
	 */
	public void setQueryCacheRegion(String queryCacheRegion) {
		this.queryCacheRegion = queryCacheRegion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#getQueryCacheRegion()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#getQueryCacheRegion()
	 */
	public String getQueryCacheRegion() {
		return this.queryCacheRegion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#setFetchSize(int)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#setFetchSize(int)
	 */
	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#getFetchSize()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#getFetchSize()
	 */
	public int getFetchSize() {
		return this.fetchSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#setMaxResults(int)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#setMaxResults(int)
	 */
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#getMaxResults()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#getMaxResults()
	 */
	public int getMaxResults() {
		return this.maxResults;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#execute(org.springframework.orm.hibernate3.HibernateCallback)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#execute(org.springframework.orm.hibernate3.HibernateCallback)
	 */
	public Object execute(HibernateCallback action) throws DataAccessException {
		return doExecute(action, false, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#executeFind(org.springframework.orm.hibernate3.HibernateCallback)
	 */
	public List executeFind(HibernateCallback action)
			throws DataAccessException {
		Object result = doExecute(action, false, false);
		if (result != null && !(result instanceof List)) {
			throw new InvalidDataAccessApiUsageException(
					"Result object returned from HibernateCallback isn't a List: ["
							+ result + "]");
		}
		return (List) result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#executeWithNewSession(org.springframework.orm.hibernate3.HibernateCallback)
	 */
	public Object executeWithNewSession(HibernateCallback action) {
		return doExecute(action, true, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#executeWithNativeSession(org.springframework.orm.hibernate3.HibernateCallback)
	 */
	public Object executeWithNativeSession(HibernateCallback action) {
		return doExecute(action, false, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#execute(org.springframework.orm.hibernate3.HibernateCallback,
	 *      boolean)
	 */
	public Object execute(HibernateCallback action, boolean enforceNativeSession)
			throws DataAccessException {
		return doExecute(action, false, enforceNativeSession);
	}

	/**
	 * Execute the action specified by the given action object within a Session.
	 * 
	 * @param action
	 *            callback object that specifies the Hibernate action
	 * @param enforceNewSession
	 *            whether to enforce a new Session for this template even if
	 *            there is a pre-bound transactional Session
	 * @param enforceNativeSession
	 *            whether to enforce exposure of the native Hibernate Session to
	 *            callback code
	 * @return a result object returned by the action, or <code>null</code>
	 * @throws org.springframework.dao.DataAccessException
	 *             in case of Hibernate errors
	 */
	protected Object doExecute(HibernateCallback action,
			boolean enforceNewSession, boolean enforceNativeSession)
			throws DataAccessException {

		Assert.notNull(action, "Callback object must not be null");

		Session session = (enforceNewSession ? SessionFactoryUtils
				.getNewSession(getSessionFactory(), getEntityInterceptor())
				: getSession());
		boolean existingTransaction = (!enforceNewSession && (!isAllowCreate() || SessionFactoryUtils
				.isSessionTransactional(session, getSessionFactory())));
		if (existingTransaction) {
			logger.debug("Found thread-bound Session for HibernateTemplate");
		}

		FlushMode previousFlushMode = null;
		try {
			previousFlushMode = applyFlushMode(session, existingTransaction);
			enableFilters(session);
			Session sessionToExpose = (enforceNativeSession
					|| isExposeNativeSession() ? session
					: createSessionProxy(session));
			Object result = action.doInHibernate(sessionToExpose);
			flushIfNecessary(session, existingTransaction);
			return result;
		} catch (HibernateException ex) {
			throw convertHibernateAccessException(ex);
		} catch (SQLException ex) {
			throw convertJdbcAccessException(ex);
		} catch (RuntimeException ex) {
			// Callback code threw application exception...
			throw ex;
		} finally {
			if (existingTransaction) {
				logger
						.debug("Not closing pre-bound Hibernate Session after HibernateTemplate");
				disableFilters(session);
				if (previousFlushMode != null) {
					session.setFlushMode(previousFlushMode);
				}
			} else {
				// Never use deferred close for an explicitly new Session.
				if (isAlwaysUseNewSession()) {
					SessionFactoryUtils.closeSession(session);
				} else {
					// SessionFactoryUtils.closeSessionOrRegisterDeferredClose(session,
					// getSessionFactory());
				}
			}
		}
	}

	/**
	 * Return a Session for use by this template.
	 * <p>
	 * Returns a new Session in case of "alwaysUseNewSession" (using the same
	 * JDBC Connection as a transactional Session, if applicable), a pre-bound
	 * Session in case of "allowCreate" turned off, and a pre-bound or new
	 * Session otherwise (new only if no transactional or otherwise pre-bound
	 * Session exists).
	 * 
	 * @return the Session to use (never <code>null</code>)
	 * @see SessionFactoryUtils#getSession
	 * @see SessionFactoryUtils#getNewSession
	 * @see #setAlwaysUseNewSession
	 * @see #setAllowCreate
	 */
	protected Session getSession() {
		if (isAlwaysUseNewSession()) {
			return SessionFactoryUtils.getNewSession(getSessionFactory(),
					getEntityInterceptor());
		} else if (isAllowCreate()) {
			return SessionFactoryUtils.getSession(getSessionFactory(),
					getEntityInterceptor(), getJdbcExceptionTranslator());
		} else {
			try {
				return getSessionFactory().getCurrentSession();
			} catch (HibernateException ex) {
				throw new DataAccessResourceFailureException(
						"Could not obtain current Hibernate Session", ex);
			}
		}
	}

	/**
	 * Create a close-suppressing proxy for the given Hibernate Session. The
	 * proxy also prepares returned Query and Criteria objects.
	 * 
	 * @param session
	 *            the Hibernate Session to create a proxy for
	 * @return the Session proxy
	 * @see org.hibernate.Session#close()
	 * @see #prepareQuery
	 * @see #prepareCriteria
	 */
	protected Session createSessionProxy(Session session) {
		Class[] sessionIfcs = null;
		Class mainIfc = (session instanceof org.hibernate.classic.Session ? org.hibernate.classic.Session.class
				: Session.class);
		if (session instanceof EventSource) {
			sessionIfcs = new Class[] { mainIfc, EventSource.class };
		} else if (session instanceof SessionImplementor) {
			sessionIfcs = new Class[] { mainIfc, SessionImplementor.class };
		} else {
			sessionIfcs = new Class[] { mainIfc };
		}
		return (Session) Proxy.newProxyInstance(getClass().getClassLoader(),
				sessionIfcs, new CloseSuppressingInvocationHandler(session));
	}

	// -------------------------------------------------------------------------
	// Convenience methods for loading individual objects
	// -------------------------------------------------------------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#get(java.lang.Class,
	 *      java.io.Serializable)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#get(java.lang.Class, java.io.Serializable)
	 */
	public Object get(Class entityClass, Serializable id)
			throws DataAccessException {
		return get(entityClass, id, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#get(java.lang.Class,
	 *      java.io.Serializable, org.hibernate.LockMode)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#get(java.lang.Class, java.io.Serializable,
	 *      org.hibernate.LockMode)
	 */
	public Object get(final Class entityClass, final Serializable id,
			final LockMode lockMode) throws DataAccessException {

		return executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				if (lockMode != null) {
					return session.get(entityClass, id, lockMode);
				} else {
					return session.get(entityClass, id);
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#get(java.lang.String,
	 *      java.io.Serializable)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see dms.dao.aa#get(java.lang.String, java.io.Serializable)
	 */
	public Object get(String entityName, Serializable id)
			throws DataAccessException {
		return get(entityName, id, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#get(java.lang.String,
	 *      java.io.Serializable, org.hibernate.LockMode)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see dms.dao.aa#get(java.lang.String, java.io.Serializable,
	 *      org.hibernate.LockMode)
	 */
	public Object get(final String entityName, final Serializable id,
			final LockMode lockMode) throws DataAccessException {

		return executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				if (lockMode != null) {
					return session.get(entityName, id, lockMode);
				} else {
					return session.get(entityName, id);
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#load(java.lang.Class,
	 *      java.io.Serializable)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#load(java.lang.Class, java.io.Serializable)
	 */
	public Object load(Class entityClass, Serializable id)
			throws DataAccessException {
		return load(entityClass, id, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#load(java.lang.Class,
	 *      java.io.Serializable, org.hibernate.LockMode)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#load(java.lang.Class, java.io.Serializable,
	 *      org.hibernate.LockMode)
	 */
	public Object load(final Class entityClass, final Serializable id,
			final LockMode lockMode) throws DataAccessException {

		return executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				if (lockMode != null) {
					return session.load(entityClass, id, lockMode);
				} else {
					return session.load(entityClass, id);
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#load(java.lang.String,
	 *      java.io.Serializable)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#load(java.lang.String, java.io.Serializable)
	 */
	public Object load(String entityName, Serializable id)
			throws DataAccessException {
		return load(entityName, id, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#load(java.lang.String,
	 *      java.io.Serializable, org.hibernate.LockMode)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#load(java.lang.String, java.io.Serializable,
	 *      org.hibernate.LockMode)
	 */
	public Object load(final String entityName, final Serializable id,
			final LockMode lockMode) throws DataAccessException {

		return executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				if (lockMode != null) {
					return session.load(entityName, id, lockMode);
				} else {
					return session.load(entityName, id);
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#loadAll(java.lang.Class)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#loadAll(java.lang.Class)
	 */
	public List loadAll(final Class entityClass) throws DataAccessException {
		return (List) executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Criteria criteria = session.createCriteria(entityClass);
				prepareCriteria(criteria);
				return criteria.list();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#load(java.lang.Object,
	 *      java.io.Serializable)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#load(java.lang.Object, java.io.Serializable)
	 */
	public void load(final Object entity, final Serializable id)
			throws DataAccessException {
		executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				session.load(entity, id);
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#refresh(java.lang.Object)
	 */
	public void refresh(final Object entity) throws DataAccessException {
		refresh(entity, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#refresh(java.lang.Object,
	 *      org.hibernate.LockMode)
	 */
	public void refresh(final Object entity, final LockMode lockMode)
			throws DataAccessException {
		executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				if (lockMode != null) {
					session.refresh(entity, lockMode);
				} else {
					session.refresh(entity);
				}
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#contains(java.lang.Object)
	 */
	public boolean contains(final Object entity) throws DataAccessException {
		Boolean result = (Boolean) executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				return (session.contains(entity) ? Boolean.TRUE : Boolean.FALSE);
			}
		});
		return result.booleanValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#evict(java.lang.Object)
	 */
	public void evict(final Object entity) throws DataAccessException {
		executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				session.evict(entity);
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#initialize(java.lang.Object)
	 */
	public void initialize(Object proxy) throws DataAccessException {
		try {
			Hibernate.initialize(proxy);
		} catch (HibernateException ex) {
			throw SessionFactoryUtils.convertHibernateAccessException(ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#enableFilter(java.lang.String)
	 */
	public Filter enableFilter(String filterName) throws IllegalStateException {
		Session session = SessionFactoryUtils.getSession(getSessionFactory(),
				false);
		Filter filter = session.getEnabledFilter(filterName);
		if (filter == null) {
			filter = session.enableFilter(filterName);
		}
		return filter;
	}

	// -------------------------------------------------------------------------
	// Convenience methods for storing individual objects
	// -------------------------------------------------------------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#lock(java.lang.Object,
	 *      org.hibernate.LockMode)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#lock(java.lang.Object,
	 *      org.hibernate.LockMode)
	 */
	public void lock(final Object entity, final LockMode lockMode)
			throws DataAccessException {
		executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				session.lock(entity, lockMode);
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#lock(java.lang.String,
	 *      java.lang.Object, org.hibernate.LockMode)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#lock(java.lang.String, java.lang.Object,
	 *      org.hibernate.LockMode)
	 */
	public void lock(final String entityName, final Object entity,
			final LockMode lockMode) throws DataAccessException {

		executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				session.lock(entityName, entity, lockMode);
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#save(java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#save(java.lang.Object)
	 */
	public Serializable save(final Object entity) throws DataAccessException {
		return (Serializable) executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				checkWriteOperationAllowed(session);
				return session.save(entity);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#save(java.lang.String,
	 *      java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#save(java.lang.String, java.lang.Object)
	 */
	public Serializable save(final String entityName, final Object entity)
			throws DataAccessException {
		return (Serializable) executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				checkWriteOperationAllowed(session);
				return session.save(entityName, entity);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#update(java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#update(java.lang.Object)
	 */
	public void update(Object entity) throws DataAccessException {
		update(entity, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#update(java.lang.Object,
	 *      org.hibernate.LockMode)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#update(java.lang.Object,
	 *      org.hibernate.LockMode)
	 */
	public void update(final Object entity, final LockMode lockMode)
			throws DataAccessException {
		executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				checkWriteOperationAllowed(session);
				session.update(entity);
				if (lockMode != null) {
					session.lock(entity, lockMode);
				}
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#update(java.lang.String,
	 *      java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#update(java.lang.String, java.lang.Object)
	 */
	public void update(String entityName, Object entity)
			throws DataAccessException {
		update(entityName, entity, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#update(java.lang.String,
	 *      java.lang.Object, org.hibernate.LockMode)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#update(java.lang.String, java.lang.Object,
	 *      org.hibernate.LockMode)
	 */
	public void update(final String entityName, final Object entity,
			final LockMode lockMode) throws DataAccessException {

		executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				checkWriteOperationAllowed(session);
				session.update(entityName, entity);
				if (lockMode != null) {
					session.lock(entity, lockMode);
				}
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#saveOrUpdate(java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#saveOrUpdate(java.lang.Object)
	 */
	public void saveOrUpdate(final Object entity) throws DataAccessException {
		executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				checkWriteOperationAllowed(session);
				session.saveOrUpdate(entity);
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#saveOrUpdate(java.lang.String,
	 *      java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#saveOrUpdate(java.lang.String,
	 *      java.lang.Object)
	 */
	public void saveOrUpdate(final String entityName, final Object entity)
			throws DataAccessException {
		executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				checkWriteOperationAllowed(session);
				session.saveOrUpdate(entityName, entity);
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#saveOrUpdateAll(java.util.Collection)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#saveOrUpdateAll(java.util.Collection)
	 */
	public void saveOrUpdateAll(final Collection entities)
			throws DataAccessException {
		executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				checkWriteOperationAllowed(session);
				for (Iterator it = entities.iterator(); it.hasNext();) {
					session.saveOrUpdate(it.next());
				}
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#replicate(java.lang.Object,
	 *      org.hibernate.ReplicationMode)
	 */
	public void replicate(final Object entity,
			final ReplicationMode replicationMode) throws DataAccessException {

		executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				checkWriteOperationAllowed(session);
				session.replicate(entity, replicationMode);
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#replicate(java.lang.String,
	 *      java.lang.Object, org.hibernate.ReplicationMode)
	 */
	public void replicate(final String entityName, final Object entity,
			final ReplicationMode replicationMode) throws DataAccessException {

		executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				checkWriteOperationAllowed(session);
				session.replicate(entityName, entity, replicationMode);
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#persist(java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see dms.dao.aa#persist(java.lang.Object)
	 */
	public void persist(final Object entity) throws DataAccessException {
		executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				checkWriteOperationAllowed(session);
				session.persist(entity);
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#persist(java.lang.String,
	 *      java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see dms.dao.aa#persist(java.lang.String, java.lang.Object)
	 */
	public void persist(final String entityName, final Object entity)
			throws DataAccessException {
		executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				checkWriteOperationAllowed(session);
				session.persist(entityName, entity);
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#merge(java.lang.Object)
	 */
	public Object merge(final Object entity) throws DataAccessException {
		return executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				checkWriteOperationAllowed(session);
				return session.merge(entity);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#merge(java.lang.String,
	 *      java.lang.Object)
	 */
	public Object merge(final String entityName, final Object entity)
			throws DataAccessException {
		return executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				checkWriteOperationAllowed(session);
				return session.merge(entityName, entity);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#delete(java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#delete(java.lang.Object)
	 */
	public void delete(Object entity) throws DataAccessException {
		delete(entity, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#delete(java.lang.Object,
	 *      org.hibernate.LockMode)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#delete(java.lang.Object,
	 *      org.hibernate.LockMode)
	 */
	public void delete(final Object entity, final LockMode lockMode)
			throws DataAccessException {
		executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				checkWriteOperationAllowed(session);
				if (lockMode != null) {
					session.lock(entity, lockMode);
				}
				session.delete(entity);
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#delete(java.lang.String,
	 *      java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#delete(java.lang.String, java.lang.Object)
	 */
	public void delete(String entityName, Object entity)
			throws DataAccessException {
		delete(entityName, entity, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#delete(java.lang.String,
	 *      java.lang.Object, org.hibernate.LockMode)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#delete(java.lang.String, java.lang.Object,
	 *      org.hibernate.LockMode)
	 */
	public void delete(final String entityName, final Object entity,
			final LockMode lockMode) throws DataAccessException {

		executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				checkWriteOperationAllowed(session);
				if (lockMode != null) {
					session.lock(entityName, entity, lockMode);
				}
				session.delete(entityName, entity);
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#deleteAll(java.util.Collection)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#deleteAll(java.util.Collection)
	 */
	public void deleteAll(final Collection entities) throws DataAccessException {
		executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				checkWriteOperationAllowed(session);
				for (Iterator it = entities.iterator(); it.hasNext();) {
					session.delete(it.next());
				}
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#flush()
	 */
	public void flush() throws DataAccessException {
		executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				session.flush();
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#clear()
	 */
	public void clear() throws DataAccessException {
		executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				session.clear();
				return null;
			}
		});
	}

	// -------------------------------------------------------------------------
	// Convenience finder methods for HQL strings
	// -------------------------------------------------------------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#find(java.lang.String)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#find(java.lang.String)
	 */
	public List find(String queryString) throws DataAccessException {
		return find(queryString, (Object[]) null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#find(java.lang.String,
	 *      java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#find(java.lang.String, java.lang.Object)
	 */
	public List find(String queryString, Object value)
			throws DataAccessException {
		return find(queryString, new Object[] { value });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#find(java.lang.String,
	 *      java.lang.Object[])
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#find(java.lang.String, java.lang.Object[])
	 */
	public List find(final String queryString, final Object[] values)
			throws DataAccessException {
		return (List) executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query queryObject = session.createQuery(queryString);
				prepareQuery(queryObject);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						queryObject.setParameter(i, values[i]);
					}
				}
				return queryObject.list();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#findByNamedParam(java.lang.String,
	 *      java.lang.String, java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#findByNamedParam(java.lang.String,
	 *      java.lang.String, java.lang.Object)
	 */
	public List findByNamedParam(String queryString, String paramName,
			Object value) throws DataAccessException {

		return findByNamedParam(queryString, new String[] { paramName },
				new Object[] { value });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#findByNamedParam(java.lang.String,
	 *      java.lang.String[], java.lang.Object[])
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#findByNamedParam(java.lang.String,
	 *      java.lang.String[], java.lang.Object[])
	 */
	public List findByNamedParam(final String queryString,
			final String[] paramNames, final Object[] values)
			throws DataAccessException {

		if (paramNames.length != values.length) {
			throw new IllegalArgumentException(
					"Length of paramNames array must match length of values array");
		}
		return (List) executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query queryObject = session.createQuery(queryString);
				prepareQuery(queryObject);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						applyNamedParameterToQuery(queryObject, paramNames[i],
								values[i]);
					}
				}
				return queryObject.list();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#findByValueBean(java.lang.String,
	 *      java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#findByValueBean(java.lang.String,
	 *      java.lang.Object)
	 */
	public List findByValueBean(final String queryString, final Object valueBean)
			throws DataAccessException {

		return (List) executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query queryObject = session.createQuery(queryString);
				prepareQuery(queryObject);
				queryObject.setProperties(valueBean);
				return queryObject.list();
			}
		});
	}

	// -------------------------------------------------------------------------
	// Convenience finder methods for named queries
	// -------------------------------------------------------------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#findByNamedQuery(java.lang.String)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#findByNamedQuery(java.lang.String)
	 */
	public List findByNamedQuery(String queryName) throws DataAccessException {
		return findByNamedQuery(queryName, (Object[]) null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#findByNamedQuery(java.lang.String,
	 *      java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#findByNamedQuery(java.lang.String,
	 *      java.lang.Object)
	 */
	public List findByNamedQuery(String queryName, Object value)
			throws DataAccessException {
		return findByNamedQuery(queryName, new Object[] { value });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#findByNamedQuery(java.lang.String,
	 *      java.lang.Object[])
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#findByNamedQuery(java.lang.String,
	 *      java.lang.Object[])
	 */
	public List findByNamedQuery(final String queryName, final Object[] values)
			throws DataAccessException {
		return (List) executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query queryObject = session.getNamedQuery(queryName);
				prepareQuery(queryObject);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						queryObject.setParameter(i, values[i]);
					}
				}
				return queryObject.list();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#findByNamedQueryAndNamedParam(java.lang.String,
	 *      java.lang.String, java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#findByNamedQueryAndNamedParam(java.lang.String,
	 *      java.lang.String, java.lang.Object)
	 */
	public List findByNamedQueryAndNamedParam(String queryName,
			String paramName, Object value) throws DataAccessException {

		return findByNamedQueryAndNamedParam(queryName,
				new String[] { paramName }, new Object[] { value });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#findByNamedQueryAndNamedParam(java.lang.String,
	 *      java.lang.String[], java.lang.Object[])
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#findByNamedQueryAndNamedParam(java.lang.String,
	 *      java.lang.String[], java.lang.Object[])
	 */
	public List findByNamedQueryAndNamedParam(final String queryName,
			final String[] paramNames, final Object[] values)
			throws DataAccessException {

		if (paramNames != null && values != null
				&& paramNames.length != values.length) {
			throw new IllegalArgumentException(
					"Length of paramNames array must match length of values array");
		}
		return (List) executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query queryObject = session.getNamedQuery(queryName);
				prepareQuery(queryObject);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						applyNamedParameterToQuery(queryObject, paramNames[i],
								values[i]);
					}
				}
				return queryObject.list();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#findByNamedQueryAndValueBean(java.lang.String,
	 *      java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#findByNamedQueryAndValueBean(java.lang.String,
	 *      java.lang.Object)
	 */
	public List findByNamedQueryAndValueBean(final String queryName,
			final Object valueBean) throws DataAccessException {

		return (List) executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query queryObject = session.getNamedQuery(queryName);
				prepareQuery(queryObject);
				queryObject.setProperties(valueBean);
				return queryObject.list();
			}
		});
	}

	// -------------------------------------------------------------------------
	// Convenience finder methods for detached criteria
	// -------------------------------------------------------------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#findByCriteria(org.hibernate.criterion.DetachedCriteria)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see dms.dao.aa#findByCriteria(org.hibernate.criterion.DetachedCriteria)
	 */
	public List findByCriteria(DetachedCriteria criteria)
			throws DataAccessException {
		return findByCriteria(criteria, -1, -1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#findByCriteria(org.hibernate.criterion.DetachedCriteria,
	 *      int, int)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see dms.dao.aa#findByCriteria(org.hibernate.criterion.DetachedCriteria,
	 *      int, int)
	 */
	public List findByCriteria(final DetachedCriteria criteria,
			final int firstResult, final int maxResults)
			throws DataAccessException {

		Assert.notNull(criteria, "DetachedCriteria must not be null");
		return (List) executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Criteria executableCriteria = criteria
						.getExecutableCriteria(session);
				prepareCriteria(executableCriteria);
				if (firstResult >= 0) {
					executableCriteria.setFirstResult(firstResult);
				}
				if (maxResults > 0) {
					executableCriteria.setMaxResults(maxResults);
				}
				return executableCriteria.list();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#findByExample(java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see dms.dao.aa#findByExample(java.lang.Object)
	 */
	public List findByExample(Object exampleEntity) throws DataAccessException {
		return findByExample(null, exampleEntity, -1, -1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#findByExample(java.lang.String,
	 *      java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see dms.dao.aa#findByExample(java.lang.String,
	 *      java.lang.Object)
	 */
	public List findByExample(String entityName, Object exampleEntity)
			throws DataAccessException {
		return findByExample(entityName, exampleEntity, -1, -1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#findByExample(java.lang.Object, int,
	 *      int)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see dms.dao.aa#findByExample(java.lang.Object, int, int)
	 */
	public List findByExample(Object exampleEntity, int firstResult,
			int maxResults) throws DataAccessException {
		return findByExample(null, exampleEntity, firstResult, maxResults);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#findByExample(java.lang.String,
	 *      java.lang.Object, int, int)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see dms.dao.aa#findByExample(java.lang.String,
	 *      java.lang.Object, int, int)
	 */
	public List findByExample(final String entityName,
			final Object exampleEntity, final int firstResult,
			final int maxResults) throws DataAccessException {

		Assert.notNull(exampleEntity, "Example entity must not be null");
		return (List) executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Criteria executableCriteria = (entityName != null ? session
						.createCriteria(entityName) : session
						.createCriteria(exampleEntity.getClass()));
				executableCriteria.add(Example.create(exampleEntity));
				prepareCriteria(executableCriteria);
				if (firstResult >= 0) {
					executableCriteria.setFirstResult(firstResult);
				}
				if (maxResults > 0) {
					executableCriteria.setMaxResults(maxResults);
				}
				return executableCriteria.list();
			}
		});
	}

	// -------------------------------------------------------------------------
	// Convenience query methods for iteration and bulk updates/deletes
	// -------------------------------------------------------------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#iterate(java.lang.String)
	 */
	public Iterator iterate(String queryString) throws DataAccessException {
		return iterate(queryString, (Object[]) null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#iterate(java.lang.String,
	 *      java.lang.Object)
	 */
	public Iterator iterate(String queryString, Object value)
			throws DataAccessException {
		return iterate(queryString, new Object[] { value });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#iterate(java.lang.String,
	 *      java.lang.Object[])
	 */
	public Iterator iterate(final String queryString, final Object[] values)
			throws DataAccessException {
		return (Iterator) executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query queryObject = session.createQuery(queryString);
				prepareQuery(queryObject);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						queryObject.setParameter(i, values[i]);
					}
				}
				return queryObject.iterate();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#closeIterator(java.util.Iterator)
	 */
	public void closeIterator(Iterator it) throws DataAccessException {
		try {
			Hibernate.close(it);
		} catch (HibernateException ex) {
			throw SessionFactoryUtils.convertHibernateAccessException(ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#bulkUpdate(java.lang.String)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#bulkUpdate(java.lang.String)
	 */
	public int bulkUpdate(String queryString) throws DataAccessException {
		return bulkUpdate(queryString, (Object[]) null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#bulkUpdate(java.lang.String,
	 *      java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#bulkUpdate(java.lang.String,
	 *      java.lang.Object)
	 */
	public int bulkUpdate(String queryString, Object value)
			throws DataAccessException {
		return bulkUpdate(queryString, new Object[] { value });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.AopDaoInterface#bulkUpdate(java.lang.String,
	 *      java.lang.Object[])
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see comm.BaseDao#bulkUpdate(java.lang.String,
	 *      java.lang.Object[])
	 */
	public int bulkUpdate(final String queryString, final Object[] values)
			throws DataAccessException {
		Integer updateCount = (Integer) executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query queryObject = session.createQuery(queryString);
				prepareQuery(queryObject);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						queryObject.setParameter(i, values[i]);
					}
				}
				return new Integer(queryObject.executeUpdate());
			}
		});
		return updateCount.intValue();
	}

	// -------------------------------------------------------------------------
	// Helper methods used by the operations above
	// -------------------------------------------------------------------------

	/**
	 * Check whether write operations are allowed on the given Session.
	 * <p>
	 * Default implementation throws an InvalidDataAccessApiUsageException in
	 * case of <code>FlushMode.NEVER/MANUAL</code>. Can be overridden in
	 * subclasses.
	 * 
	 * @param session
	 *            current Hibernate Session
	 * @throws InvalidDataAccessApiUsageException
	 *             if write operations are not allowed
	 * @see #setCheckWriteOperations
	 * @see #getFlushMode()
	 * @see #FLUSH_EAGER
	 * @see org.hibernate.Session#getFlushMode()
	 * @see org.hibernate.FlushMode#NEVER
	 * @see org.hibernate.FlushMode#MANUAL
	 */
	protected void checkWriteOperationAllowed(Session session)
			throws InvalidDataAccessApiUsageException {
		if (isCheckWriteOperations() && getFlushMode() != FLUSH_EAGER
				&& session.getFlushMode().lessThan(FlushMode.COMMIT)) {
			throw new InvalidDataAccessApiUsageException(
					"Write operations are not allowed in read-only mode (FlushMode.NEVER/MANUAL): "
							+ "Turn your Session into FlushMode.COMMIT/AUTO or remove 'readOnly' marker from transaction definition.");
		}
	}

	/**
	 * Prepare the given Query object, applying cache settings and/or a
	 * transaction timeout.
	 * 
	 * @param queryObject
	 *            the Query object to prepare
	 * @see #setCacheQueries
	 * @see #setQueryCacheRegion
	 * @see SessionFactoryUtils#applyTransactionTimeout
	 */
	protected void prepareQuery(Query queryObject) {
		if (isCacheQueries()) {
			queryObject.setCacheable(true);
			if (getQueryCacheRegion() != null) {
				queryObject.setCacheRegion(getQueryCacheRegion());
			}
		}
		if (getFetchSize() > 0) {
			queryObject.setFetchSize(getFetchSize());
		}
		if (getMaxResults() > 0) {
			queryObject.setMaxResults(getMaxResults());
		}
		SessionFactoryUtils.applyTransactionTimeout(queryObject,
				getSessionFactory());
	}

	/**
	 * Prepare the given Criteria object, applying cache settings and/or a
	 * transaction timeout.
	 * 
	 * @param criteria
	 *            the Criteria object to prepare
	 * @see #setCacheQueries
	 * @see #setQueryCacheRegion
	 * @see SessionFactoryUtils#applyTransactionTimeout
	 */
	protected void prepareCriteria(Criteria criteria) {
		if (isCacheQueries()) {
			criteria.setCacheable(true);
			if (getQueryCacheRegion() != null) {
				criteria.setCacheRegion(getQueryCacheRegion());
			}
		}
		if (getFetchSize() > 0) {
			criteria.setFetchSize(getFetchSize());
		}
		if (getMaxResults() > 0) {
			criteria.setMaxResults(getMaxResults());
		}
		SessionFactoryUtils.applyTransactionTimeout(criteria,
				getSessionFactory());
	}

	/**
	 * Apply the given name parameter to the given Query object.
	 * 
	 * @param queryObject
	 *            the Query object
	 * @param paramName
	 *            the name of the parameter
	 * @param value
	 *            the value of the parameter
	 * @throws HibernateException
	 *             if thrown by the Query object
	 */
	protected void applyNamedParameterToQuery(Query queryObject,
			String paramName, Object value) throws HibernateException {

		if (value instanceof Collection) {
			queryObject.setParameterList(paramName, (Collection) value);
		} else if (value instanceof Object[]) {
			queryObject.setParameterList(paramName, (Object[]) value);
		} else {
			queryObject.setParameter(paramName, value);
		}
	}

	/**
	 * Invocation handler that suppresses close calls on Hibernate Sessions.
	 * Also prepares returned Query and Criteria objects.
	 * 
	 * @see org.hibernate.Session#close
	 */
	private class CloseSuppressingInvocationHandler implements
			InvocationHandler {

		private final Session target;

		public CloseSuppressingInvocationHandler(Session target) {
			this.target = target;
		}

		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			// Invocation on Session interface coming in...

			if (method.getName().equals("equals")) {
				// Only consider equal when proxies are identical.
				return (proxy == args[0] ? Boolean.TRUE : Boolean.FALSE);
			} else if (method.getName().equals("hashCode")) {
				// Use hashCode of Session proxy.
				return new Integer(hashCode());
			} else if (method.getName().equals("close")) {
				// Handle close method: suppress, not valid.
				return null;
			}

			// Invoke method on target Session.
			try {
				Object retVal = method.invoke(this.target, args);

				// If return value is a Query or Criteria, apply transaction
				// timeout.
				// Applies to createQuery, getNamedQuery, createCriteria.
				if (retVal instanceof Query) {
					prepareQuery(((Query) retVal));
				}
				if (retVal instanceof Criteria) {
					prepareCriteria(((Criteria) retVal));
				}

				return retVal;
			} catch (InvocationTargetException ex) {
				throw ex.getTargetException();
			}
		}
	}

}
