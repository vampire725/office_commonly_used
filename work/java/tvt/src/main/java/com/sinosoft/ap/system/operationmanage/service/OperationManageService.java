package com.sinosoft.ap.system.operationmanage.service;

import com.sinosoft.ap.system.operationmanage.domain.OperationEntity;

/***
 * @since 2017 年  04 月 07 日 01:11:50 
 */
public interface OperationManageService {

	 /**
     * 根据给定的参数新增一条数据
     * @param OperationEntity
     */
	OperationEntity save( OperationEntity operationEntity ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param OperationEntity
     */
	void remove( OperationEntity operationEntity ) throws Exception ;
	
    /**
     * 指定主键，修改给定信息项
     * @param OperationEntity
     */
	void modify( OperationEntity operationEntity ) throws Exception ;
	
}