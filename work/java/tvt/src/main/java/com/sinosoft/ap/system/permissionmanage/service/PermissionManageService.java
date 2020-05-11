package com.sinosoft.ap.system.permissionmanage.service;

import com.sinosoft.ap.system.operationmanage.domain.OperationEntity;
import com.sinosoft.ap.system.permissionmanage.domain.PermissionEntity;

import java.util.List;

/***
 * @since 2017 年  04 月 07 日 01:12:20 
 */
public interface PermissionManageService {

	 /**
     * 根据给定的参数新增一条数据
     * @param PermissionEntity
     */
	PermissionEntity save( PermissionEntity permissionEntity ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param PermissionEntity
     */
	void remove( PermissionEntity permissionEntity ) throws Exception ;
	
    /**
     * 指定主键，修改给定信息项
     * @param PermissionEntity
     */
	void modify( PermissionEntity permissionEntity ) throws Exception ;

	List<OperationEntity> userAuth (String userId) throws Exception;
	
	List<String> getResourceId(String userId)throws Exception;
}