package com.sinosoft.ap.system.operationmanage.domain;

import java.util.List;
import org.springframework.stereotype.Repository;

/***
 * @since 2017 年  04 月 07 日 01:11:50 
 */
@Repository
public interface OperationRepository{

	 /**
     * 根据给定的参数新增一条数据
     * @param OperationEntity
     */
	void insert( OperationEntity operationEntity ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param OperationEntity
     */
	void delete( OperationEntity operationEntity ) throws Exception ;
	
    /**
     * 指定主键，修改给定信息项
     * @param OperationEntity
     */
	void update( OperationEntity operationEntity ) throws Exception ;
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param OperationEntity
     * @return List<OperationEntity>
     */
	List<OperationEntity> select( OperationEntity operationEntity ) throws Exception ;
	
	/**
	 * 根据许可id查询全部操作
	 * @return
	 * @throws Exception
	 */
	List<OperationEntity> selectByPermissionId(List<String> list) throws Exception;
}