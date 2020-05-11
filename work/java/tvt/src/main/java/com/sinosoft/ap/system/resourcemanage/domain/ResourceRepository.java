package com.sinosoft.ap.system.resourcemanage.domain;

import java.util.List;
import org.springframework.stereotype.Repository;

/***
 * @since 2017 年  04 月 07 日 01:13:01 
 */
@Repository
public interface ResourceRepository{

	 /**
     * 根据给定的参数新增一条数据
     * @param ResourceEntity
     */
	void insert( ResourceEntity resourceEntity ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param ResourceEntity
     */
	void delete( ResourceEntity resourceEntity ) throws Exception ;
	
    /**
     * 指定主键，修改给定信息项
     * @param ResourceEntity
     */
	void update( ResourceEntity resourceEntity ) throws Exception ;
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param ResourceEntity
     * @return List<ResourceEntity>
     */
	List<ResourceEntity> select( ResourceEntity resourceEntity ) throws Exception ;
	
	/**
	 * 查询所有资源
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	List<ResourceVO> selectResourceIcon( List<String> list ) throws Exception ;
	/**
	 * 查询资源树
	 * @return
	 * @throws Exception
	 */
	List<ResourceOperationTree> selectResourceTree() throws Exception ;
	/**
	 * 查询操作树
	 * @return
	 * @throws Exception
	 */
	List<ResourceOperationTree> selectOperationTree() throws Exception ;
	
}