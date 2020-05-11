package com.sinosoft.ap.system.resourcemanage.domain;

import java.util.List;
import org.springframework.stereotype.Repository;

/***
 * @since 2017 年  04 月 07 日 01:13:13 
 */
@Repository
public interface ResourceIconRepository{

	 /**
     * 根据给定的参数新增一条数据
     * @param ResourceIconEntity
     */
	void insert( ResourceIconEntity resourceIconEntity ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param ResourceIconEntity
     */
	void delete( ResourceIconEntity resourceIconEntity ) throws Exception ;
	
    /**
     * 指定主键，修改给定信息项
     * @param ResourceIconEntity
     */
	void update( ResourceIconEntity resourceIconEntity ) throws Exception ;
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param ResourceIconEntity
     * @return List<ResourceIconEntity>
     */
	List<ResourceIconEntity> select( ResourceIconEntity resourceIconEntity ) throws Exception ;
	
}