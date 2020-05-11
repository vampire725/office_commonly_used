package com.sinosoft.ap.system.resourcemanage.service;

import java.util.List;

import com.sinosoft.ap.system.resourcemanage.domain.ResourceEntity;
import com.sinosoft.ap.system.resourcemanage.domain.ResourceIconEntity;
import com.sinosoft.ap.system.resourcemanage.domain.ResourceOperationTree;
import com.sinosoft.ap.system.resourcemanage.domain.ResourceVO;

public interface ResService {

	 /**
    * 根据给定的参数新增一条数据
    * @param ResourceEntity
    */
	ResourceEntity save( ResourceEntity resourceEntity ,ResourceIconEntity resourceIconEntity) throws Exception ;
	
   /**
    * 根据给定条件删除一条数据，条件可以有多个
    * @param ResourceEntity
    */
	void remove( ResourceEntity resourceEntity ) throws Exception ;
	
   /**
    * 指定主键，修改给定信息项
    * @param ResourceEntity
    */
	void modify( ResourceEntity resourceEntity,ResourceIconEntity resourceIconEntity) throws Exception ;
	
   /**
    * 根据给定参数，查找相关数据，支持多条件查询
    * @param ResourceEntity
    * @return List<ResourceEntity>
    */
	List<ResourceEntity> find( ResourceEntity resourceEntity) throws Exception ;
	
	/**
	 * 查询所有原+图标
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	List<ResourceVO> findResourceIcon(List<String> ids)throws Exception;
	/**
	 * 查询资源树
	 * @return
	 * @throws Exception
	 */
	List<ResourceOperationTree> findResourceTree() throws Exception ;
	/**
	 * 查询操作树
	 * @return
	 * @throws Exception
	 */
	List<ResourceOperationTree> findOperationTree() throws Exception ;
	/**
	 * 资源排序
	 * @throws Exception
	 */
	void sortResource(String upId,String downId) throws Exception;
}
