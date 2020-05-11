package com.sinosoft.ap.system.operationmanage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinosoft.ap.common.exception.NullAttributeException;
import com.sinosoft.ap.system.operationmanage.domain.OperationEntity;
import com.sinosoft.ap.system.operationmanage.domain.OperationRepository;
import com.sinosoft.ap.util.list.ListUtil;
import com.sinosoft.ap.util.result.ResultConstant;

@Service
public class OperationSearchServiceImpl implements OperationSearchService{

	@Autowired
	private OperationRepository operationRepository;
    /**
     * 根据给定参数，查找相关数据
     * @param OperationEntity
     * @return List<OperationEntity>
     */
	@Override
	public List<OperationEntity> find(OperationEntity operationEntity) throws Exception {
//		if(StringUtil.checkNull(operationEntity.getOperationId())&&StringUtil.checkNull(operationEntity.getPermissionId())&&StringUtil.checkNull(operationEntity.getResourceId())) {
//			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
//		}
		return this.operationRepository.select(operationEntity);
	}
	@Override
	public List<OperationEntity> findByPermissionId(List<String> list) throws Exception {
		if (ListUtil.checkNull(list)) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		return this.operationRepository.selectByPermissionId(list);
	}
}
