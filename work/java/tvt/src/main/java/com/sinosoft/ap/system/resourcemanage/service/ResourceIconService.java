package com.sinosoft.ap.system.resourcemanage.service;

import java.util.List;

import com.sinosoft.ap.system.resourcemanage.domain.ResourceIconEntity;

/***
 * @since 2017 年  04 月 07 日 01:13:13 
 */
public interface ResourceIconService {

	 /**
     * 根据给定的参数新增一条数据
     * @param ResourceIconEntity
     */
	ResourceIconEntity save( ResourceIconEntity resourceIconEntity ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param ResourceIconEntity
     */
	void remove( ResourceIconEntity resourceIconEntity ) throws Exception ;
	
    /**
     * 指定主键，修改给定信息项
     * @param ResourceIconEntity
     */
	void modify( ResourceIconEntity resourceIconEntity ) throws Exception ;
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param ResourceIconEntity
     * @return List<ResourceIconEntity>
     */
	List<ResourceIconEntity> find( ResourceIconEntity resourceIconEntity) throws Exception ;
	
}