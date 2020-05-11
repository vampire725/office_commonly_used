package com.sinosoft.ap.system.operationmanage.service;

import java.util.Date;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.ap.common.exception.NullAttributeException;
import com.sinosoft.ap.system.operationmanage.domain.OperationEntity;
import com.sinosoft.ap.system.operationmanage.domain.OperationRepository;
import com.sinosoft.ap.system.permissionrolerel.domain.PermissionOperationRelEntity;
import com.sinosoft.ap.system.permissionrolerel.service.PermissionOperationRelService;
import com.sinosoft.ap.util.id.PrimaryKeyUtil;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;

/***
 * @since 2017 年  04 月 07 日 01:11:50 
 */
 @Service
public class OperationManageServiceImpl implements OperationManageService{

	@Autowired
	private OperationRepository operationRepository;
	
	@Autowired
	private PermissionOperationRelService permissionOperationRelService;

	 /**
     * 根据给定的参数新增一条数据
     * @param OperationEntity
     */
	@Override
	public OperationEntity save(OperationEntity operationEntity) throws Exception {
		if(StringUtil.checkNull(operationEntity.getOperationCode())) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		operationEntity.setOperationId(PrimaryKeyUtil.create());
		operationEntity.setOperationTime(new Date());
		this.operationRepository.insert(operationEntity);
		return operationEntity;
	}
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param OperationEntity
     */
	@Override
//	@Transactional(readOnly=false,transactionManager="apsystemTransactionManager",rollbackFor = {Exception.class})
	public void remove(OperationEntity operationEntity) throws Exception {
		if(StringUtil.checkNull(operationEntity.getOperationId())&&StringUtil.checkNull(operationEntity.getPermissionId())&&StringUtil.checkNull(operationEntity.getResourceId())) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		String operationId = operationEntity.getOperationId();
		PermissionOperationRelEntity permissionOperationRelEntity = new PermissionOperationRelEntity();
		permissionOperationRelEntity.setOperetionId(operationId);
		if(!StringUtil.checkNull(operationId)) {
			this.permissionOperationRelService.remove(permissionOperationRelEntity);			
		}
		this.operationRepository.delete(operationEntity);
		
	}
	
    /**
     * 指定主键，修改给定信息项
     * @param OperationEntity
     */
	@Override
	public void modify(OperationEntity operationEntity) throws Exception {
		if(StringUtil.checkNull(operationEntity.getOperationId())||StringUtil.checkNull(operationEntity.getOperationCode())) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		this.operationRepository.update(operationEntity);
	}

	
}