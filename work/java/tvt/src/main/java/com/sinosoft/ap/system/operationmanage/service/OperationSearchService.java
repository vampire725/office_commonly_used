package com.sinosoft.ap.system.operationmanage.service;

import java.util.List;

import com.sinosoft.ap.system.operationmanage.domain.OperationEntity;

public interface OperationSearchService {

	/**
	 * 根据给定参数，查找相关数据，支持多条件查询
	 * @param OperationEntity
	 * @return List<OperationEntity>
	 */
	List<OperationEntity> find( OperationEntity operationEntity) throws Exception ;
	
	/**
	 * 根据许可id查询全部操作
	 * @return
	 * @throws Exception
	 */
	List<OperationEntity> findByPermissionId(List<String> list) throws Exception;

}
