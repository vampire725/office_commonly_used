package com.sinosoft.ap.system.permissionrolerel.domain;

import java.util.List;
import org.springframework.stereotype.Repository;

/***
 * @since 2017 年  04 月 07 日 01:39:42 
 */
@Repository
public interface PermissionOperationRelRepository{

	 /**
     * 根据给定的参数新增一条数据
     * @param PermissionOperationRelEntity
     */
	void insert( PermissionOperationRelEntity permissionOperationRelEntity ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param PermissionOperationRelEntity
     */
	void delete( PermissionOperationRelEntity permissionOperationRelEntity ) throws Exception ;
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param PermissionOperationRelEntity
     * @return List<PermissionOperationRelEntity>
     */
	List<PermissionOperationRelEntity> select( PermissionOperationRelEntity permissionOperationRelEntity ) throws Exception ;
	
	/**
	 * 批量新增权限信息
	 * @param permissionOperationRelEntity
	 * @throws Exception
	 */
	public void saveList(List<PermissionOperationRelEntity> permissionOperationRelEntity) throws Exception;
}