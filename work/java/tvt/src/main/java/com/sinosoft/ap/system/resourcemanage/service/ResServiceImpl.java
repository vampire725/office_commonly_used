package com.sinosoft.ap.system.resourcemanage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinosoft.ap.common.exception.NullAttributeException;
import com.sinosoft.ap.system.operationmanage.domain.OperationEntity;
import com.sinosoft.ap.system.operationmanage.service.OperationManageService;
import com.sinosoft.ap.system.resourcemanage.domain.ResourceEntity;
import com.sinosoft.ap.system.resourcemanage.domain.ResourceIconEntity;
import com.sinosoft.ap.system.resourcemanage.domain.ResourceIconRelEntity;
import com.sinosoft.ap.system.resourcemanage.domain.ResourceOperationTree;
import com.sinosoft.ap.system.resourcemanage.domain.ResourceVO;
import com.sinosoft.ap.util.id.PrimaryKeyUtil;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;

@Service
public class ResServiceImpl implements ResService {
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private OperationManageService operationManageService;
	
	@Autowired
	private ResourceIconRelService resourceIconRelService;
	
	@Autowired
	private ResourceIconService resourceIconService;

	@Override
	public ResourceEntity save(ResourceEntity resourceEntity,ResourceIconEntity resourceIconEntity) throws Exception {
		ResourceEntity temp = resourceService.save(resourceEntity);
		ResourceIconRelEntity resourceIconRelEntity = new ResourceIconRelEntity();
		resourceIconRelEntity.setResourceId(temp.getResourceId());
		String iconId =  this.resourceIconService.save(resourceIconEntity).getResourceIconId();
		resourceIconRelEntity.setResourceIconId(iconId);
		this.resourceIconRelService.save(resourceIconRelEntity);
		return temp;
	}

	@Override
	public void remove(ResourceEntity resourceEntity) throws Exception {
		String resourceId = resourceEntity.getResourceId();
		if(StringUtil.checkNull(resourceId)) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		this.resourceService.remove(resourceEntity);
		OperationEntity operationEntity = new OperationEntity();
		operationEntity.setResourceId(resourceId);
		this.operationManageService.remove(operationEntity);
		ResourceIconRelEntity resourceIconRelEntity = new ResourceIconRelEntity();
		resourceIconRelEntity.setResourceId(resourceId);
		this.resourceIconRelService.remove(resourceIconRelEntity);
		
	}

	@Override
	public void modify(ResourceEntity resourceEntity,ResourceIconEntity resourceIconEntity) throws Exception {
		String resourceId = resourceEntity.getResourceId();
		ResourceIconRelEntity resourceIconRelEntity = new ResourceIconRelEntity();
		if(StringUtil.checkNull(resourceId)) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		resourceIconRelEntity.setResourceId(resourceId);
		List<ResourceIconRelEntity> list = this.resourceIconRelService.find(resourceIconRelEntity);
		String resourceIconId = "";
		if(null==list||list.size()>0) {
			ResourceIconEntity resourceIconEntity2 = new ResourceIconEntity(); 
			resourceIconId = list.get(0).getResourceIconId();
			resourceIconEntity2.setResourceIconId(resourceIconId);
			this.resourceIconService.remove(resourceIconEntity2);
		}
		this.resourceIconRelService.remove(resourceIconRelEntity);
		resourceIconId = PrimaryKeyUtil.create();
		resourceIconEntity.setResourceIconId(resourceIconId);
		resourceIconRelEntity.setResourceIconId(resourceIconId);
		resourceIconRelEntity.setResourceId(resourceId);
		this.resourceIconRelService.save(resourceIconRelEntity);
		this.resourceIconService.save(resourceIconEntity);
		this.resourceService.modify(resourceEntity);
		
	}

	@Override
	public List<ResourceEntity> find(ResourceEntity resourceEntity) throws Exception {
		
		return this.resourceService.find(resourceEntity);
	}

	@Override
	public List<ResourceVO> findResourceIcon(List<String> ids) throws Exception {
		
		return this.resourceService.findResourceIcon(ids);
	}

	@Override
	public List<ResourceOperationTree> findResourceTree() throws Exception {
		
		return this.resourceService.findResourceTree();
	}

	@Override
	public List<ResourceOperationTree> findOperationTree() throws Exception {
		
		return this.resourceService.findOperationTree();
	}

	@Override
	public void sortResource(String upId, String downId) throws Exception {
		ResourceEntity resourceEntityUp = new ResourceEntity();
		resourceEntityUp.setResourceId(upId);
		List<ResourceEntity> resourceEntitiesUp = new ArrayList<>();
		resourceEntitiesUp = this.resourceService.find(resourceEntityUp);
		ResourceEntity resourceEntityDown = new ResourceEntity();
		resourceEntityDown.setResourceId(downId);
		List<ResourceEntity> resourceEntitiesDown = new ArrayList<>();
		resourceEntitiesDown = this.resourceService.find(resourceEntityDown);
		if(null==resourceEntitiesUp||null==resourceEntitiesDown||resourceEntitiesUp.size()<0||resourceEntitiesDown.size()<0){
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		/**
		 * 交换排序信息并更新信息
		 */
		Integer sort = resourceEntitiesUp.get(0).getResourceSort();
		resourceEntitiesUp.get(0).setResourceSort(resourceEntitiesDown.get(0).getResourceSort());
		resourceEntitiesDown.get(0).setResourceSort(sort);
		this.resourceService.modify(resourceEntitiesUp.get(0));
		this.resourceService.modify(resourceEntitiesDown.get(0));
		
	}

}
