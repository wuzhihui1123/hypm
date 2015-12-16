package cn.ifactory.hypm.dao;

import java.util.List;
import java.util.Map;

/**
 * 基类Dao
 * @author yaha
 * 
 */
public abstract class BaseDao<T> {

	/**
	 * 保存实体
	 * 
	 * @param t
	 */
	public abstract void save(T t);

	/**
	 * 删除实体
	 * 
	 * @param t
	 */
	public abstract void delete(T t);

	/**
	 * 通过主键id删除实体
	 * 
	 * @param entityClass
	 * @param id
	 */
	public abstract void delete(String id);
	
	/**
	 * 删除数组中包含id的所有实体
	 * @param ids
	 */
	public abstract void delete(String[] ids);

	/**
	 * 通过主键id获取实体实例
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public abstract T get(String id);
	
	/**
	 * 获取数组中包含id的所有实体
	 * @param ids
	 * @return
	 */
	public abstract List<T> get(String[] ids);

	/**
	 * 更新实体信息
	 * 
	 * @param t
	 */
	public abstract void update(T t);
	
	/**
	 * @param t
	 */
	public abstract T merge(T t);

	/**
	 * 通过给定参数查询实体数据
	 * @param paramMap 变量名-变量值 键值对： 为null时查询所有记录数
	 * @param orderBy  排序参数  eg: name desc
	 * @return
	 */
	public abstract List<T> findByParams(Map<String,Object> paramMap,String...orderBy);
	
	/**
	 * 通过给定参数查询第一个实体数据
	 * @param paramMap
	 * @return
	 */
	public abstract T findOneByParams(Map<String, Object> paramMap);
	
	/**
	 * 通过给定参数得到实体数据记录数
	 * @param paramMap 变量名-变量值 键值对: 为null时查询所有记录数
	 * @return
	 */
	public abstract int getCountByParams(Map<String,Object> paramMap);
	
	/**
	 * 通过给定参数分页查询实体数据
	 * @param paramMap 变量名-变量值 键值对：为null时查询所有
	 * @param firstResult 第一条数据位置
	 * @param maxResults  查询的总数据数目
	 * @param orderBy  排序参数  eg: name desc
	 * @return
	 */
	public abstract List<T> findByPage(Map<String,Object> paramMap, int firstResult, int maxResults,String...orderBy);

}
