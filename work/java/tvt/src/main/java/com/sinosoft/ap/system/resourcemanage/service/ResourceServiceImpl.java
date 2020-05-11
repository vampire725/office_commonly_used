package com.sinosoft.ap.system.resourcemanage.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.ap.common.exception.AlreadyExistException;
import com.sinosoft.ap.common.exception.NullAttributeException;
import com.sinosoft.ap.system.operationmanage.domain.OperationEntity;
import com.sinosoft.ap.system.operationmanage.service.OperationManageService;
import com.sinosoft.ap.system.resourcemanage.domain.ResourceEntity;
import com.sinosoft.ap.system.resourcemanage.domain.ResourceIconRelEntity;
import com.sinosoft.ap.system.resourcemanage.domain.ResourceOperationTree;
import com.sinosoft.ap.system.resourcemanage.domain.ResourceRepository;
import com.sinosoft.ap.system.resourcemanage.domain.ResourceVO;
import com.sinosoft.ap.util.id.PrimaryKeyUtil;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;

/***
 * @since 2017 年  04 月 07 日 01:13:01 
 */
 @Service
public class ResourceServiceImpl implements ResourceService{

	@Autowired
	private ResourceRepository resourceRepository;
	
	@Autowired
	private ResourceIconRelService resourceIconRelService;
	
	@Autowired
	private OperationManageService operationManageService;

	 /**
     * 根据给定的参数新增一条数据
     * @param ResourceEntity
     */
	@Override
	public ResourceEntity save(ResourceEntity resourceEntity) throws Exception {
		String resourceId = PrimaryKeyUtil.create();
		String resourcePath = resourceEntity.getResourcePath();
		if(resourcePath.equals("0")){
			resourceEntity.setResourcePath(resourceId);
		}else{
			resourceEntity.setResourcePath(resourcePath+"_"+resourceId);
		}
		resourceEntity.setResourceId(resourceId);
		resourceEntity.setResourceCreatetime(new Date());
		this.resourceRepository.insert(resourceEntity);
		return resourceEntity;
	}
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param ResourceEntity
     */
	@Override
//	@Transactional(readOnly=false,transactionManager="apsystemTransactionManager",rollbackFor = {Exception.class})
	public void remove(ResourceEntity resourceEntity) throws Exception {
		String resourceId = resourceEntity.getResourceId();
		if(StringUtil.checkNull(resourceId)) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		ResourceIconRelEntity resourceIconRelEntity = new ResourceIconRelEntity();
		resourceIconRelEntity.setResourceId(resourceId);
		OperationEntity operationEntity = new OperationEntity();
		operationEntity.setResourceId(resourceId);
		this.operationManageService.remove(operationEntity);
		this.resourceIconRelService.remove(resourceIconRelEntity);
		this.resourceRepository.delete(resourceEntity);
		
	}
	
    /**
     * 指定主键，修改给定信息项
     * @param ResourceEntity
     */
	@Override
	public void modify(ResourceEntity resourceEntity) throws Exception {
		String resourceId = resourceEntity.getResourceId();
		if(StringUtil.checkNull(resourceId)) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		List<ResourceEntity> resourceEntities = new ArrayList<>();
		resourceEntities = this.resourceRepository.select(resourceEntity);
		if (resourceEntities!=null&&resourceEntities.size()>1) {
			throw new AlreadyExistException(ResultConstant.ALREADY_EXIST);
		}
		this.resourceRepository.update(resourceEntity);
		
	}
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param ResourceEntity
     * @return List<ResourceEntity>
     */
	@Override
	public List<ResourceEntity> find(ResourceEntity resourceEntity) throws Exception {
		
		return this.resourceRepository.select(resourceEntity);
	}

	/**
	 * 查询所有资源+图标
	 */
	@Override
	public List<ResourceVO> findResourceIcon(List<String> ids) throws Exception {
		
		return this.resourceRepository.selectResourceIcon(ids);
	}

	@Override
	public List<ResourceOperationTree> findResourceTree() throws Exception {
		
		return this.resourceRepository.selectResourceTree();
	}

	@Override
	public List<ResourceOperationTree> findOperationTree() throws Exception {
		
		return this.resourceRepository.selectOperationTree();
	}
	
}