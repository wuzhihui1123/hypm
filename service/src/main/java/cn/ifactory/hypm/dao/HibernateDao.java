package cn.ifactory.hypm.dao;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hibernate基类Dao
 * @author yaha
 *
 */
public abstract class  HibernateDao<T> extends BaseDao<T>{

	@Resource(name="hibernateSessionFactory")
	protected SessionFactory sessionFactory;
	
	protected Class<?> entityClass = this.getSuperClassGenricType();
	
	@Override
	public T save(T t) {
		String id = (String)getSession().save(t);
		return get(id);
		
	}

	@Override
	public void delete(T t) {
		getSession().delete(t);
		
	}

	@Override
	public void delete(String id) {
		getSession().delete(get(id));
		
	}
	@Override
	public void delete(String[] ids) {
		if(ArrayUtils.isNotEmpty(ids)) {
			StringBuffer hql = new StringBuffer();
			hql.append("delete ").append(entityClass.getSimpleName()).append(" where id in ( ");
			for(int i=0;i<ids.length;i++) {
				if(i == 0) {
					hql.append(":id" + i);
				}else {
					hql.append(", :id" + i);
				}
			}
			hql.append(" )");
			Query query = getSession().createQuery(hql.toString());
			for(int i=0;i<ids.length;i++) {
				query.setParameter("id" + i, ids[i]);
			}
			query.executeUpdate();
		}
	}

	@Override
	public T get(String id) {
		if(StringUtils.isBlank(id)) {
			return null;
		}else {
			return (T)getSession().get(entityClass, id);
		}
	}
	@Override
	public List<T> get(String[] ids) {
		List<T> ret = new ArrayList<T>();
		if(ArrayUtils.isNotEmpty(ids)) {
			StringBuffer hql = new StringBuffer();
			hql.append("from ").append(entityClass.getSimpleName()).append(" where id in ( ");
			for(int i=0;i<ids.length;i++) {
				if(i == 0) {
					hql.append(":id" + i);
				}else {
					hql.append(", :id" + i);
				}
			}
			hql.append(" )");
			Query query = getSession().createQuery(hql.toString());
			for(int i=0;i<ids.length;i++) {
				query.setParameter("id" + i, ids[i]);
			}
			ret = query.list();
		}
		return ret;
	}

	@Override
	public void update(T t) {
		getSession().update(t);
		
	}
	
	@Override
	public T merge(T t) {
		return (T)getSession().merge(t);
	}

	
	@Override
	public List<T> findByParams(Map<String,Object> paramMap,String...orderBy) {
		Query query = createHqlQueryByParams(paramMap,orderBy);
		return (List<T>)query.list();
	}
	
	@Override
	public T findOneByParams(Map<String,Object> paramMap) {
		List<T> list = this.findByParams(paramMap);
		return (list != null && list.size() > 0) ? list.get(0) : null;
	}
	
	@Override
	public int getCountByParams(Map<String,Object> paramMap) {
		Query query = createHqlQueryByParams(paramMap,true);
		return ((Long)query.uniqueResult()).intValue();
	}
	
	@Override
	public List<T> findByPage(Map<String,Object> paramMap,int firstResult, int maxResults,String...orderBy) {
		Query query = createHqlQueryByParams(paramMap,orderBy);
		query.setFirstResult(firstResult).setMaxResults(maxResults);
		return (List<T>)query.list();
	}
	
	/**
	 * 用给定变量参数值创建Query
	 * @param paramMap
	 * @return
	 */
	private Query createHqlQueryByParams(Map<String,Object> paramMap,String...orderBy) {
		return createHqlQueryByParams(paramMap, false,orderBy);
	}
	/**
	 * 用给定变量参数值创建Query
	 * @param paramMap
	 * @param isCount 是否查询记录数
	 * @return
	 */
	private Query createHqlQueryByParams(Map<String,Object> paramMap,boolean queryCount,String...orderBy) {
		paramMap = (paramMap == null ? new HashMap<String, Object>() : paramMap);
		StringBuffer hql = new StringBuffer();
		if(queryCount) {
			hql.append("select count(*) ");
		}
		hql.append("from ").append(entityClass.getSimpleName()).append(" ");
		//条件参数引入
		if(!paramMap.isEmpty()) {
			hql.append("where ");
			for(String param : paramMap.keySet()) {
				hql.append(param).append(" = :").append(param).append(" and ");
			}
			hql = new StringBuffer(hql.subSequence(0, hql.length()-4));
		}
		
		//排序参数引入
		if(orderBy != null && orderBy.length > 0 && !queryCount) {
			hql.append("order by ");
			for(String order : orderBy) {
				hql.append(order).append(" , ");
			}
			hql = new StringBuffer(hql.subSequence(0, hql.length()-2));
		}
		
		Query query = getSession().createQuery(hql.toString());
		for(String param : paramMap.keySet()) {
			query.setParameter(param, paramMap.get(param));
		}
		
		query.setCacheable(true); //开启查询缓存
		return query;
		
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * 获取父类的第一个类泛型参数的类型
	 * @return
	 */
	private <T> Class<T> getSuperClassGenricType() {
		return getSuperClassGenricType(this.getClass(), 0);
	}

	/**
	 * 获取父类的指定序号的类泛型参数的类型,如无法找到, 返回Object.class
	 * 序号从0开始，即第一个为0
	 * @param clazz
	 * @param index
	 * @return
	 */
	private Class getSuperClassGenricType(Class clazz, int index) {
		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			// logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if ((index >= params.length) || (index < 0)) {
			// logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);

			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			// logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}
	
	
}
