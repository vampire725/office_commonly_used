package com.sinosoft.ap.system.resourcemanage.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.sinosoft.ap.common.exception.NullAttributeException;
import com.sinosoft.ap.system.resourcemanage.domain.ResourceIconEntity;
import com.sinosoft.ap.system.resourcemanage.domain.ResourceIconRepository;
import com.sinosoft.ap.util.id.PrimaryKeyUtil;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;

/***
 * @since 2017 年  04 月 07 日 01:13:13 
 */
 @Service
public class ResourceIconServiceImpl implements ResourceIconService{

	@Autowired
	private ResourceIconRepository resourceIconRepository;

	 /**
     * 根据给定的参数新增一条数据
     * @param ResourceIconEntity
     */
	@Override
	public ResourceIconEntity save(ResourceIconEntity resourceIconEntity) throws Exception {
		if(StringUtil.checkNull(resourceIconEntity.getResourceIconId())) {
			resourceIconEntity.setResourceIconId(PrimaryKeyUtil.create());
			this.resourceIconRepository.insert(resourceIconEntity);
			return resourceIconEntity;		
		}else{
			this.resourceIconRepository.insert(resourceIconEntity);
			return resourceIconEntity;
		}
	}
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param ResourceIconEntity
     */
	@Override
	public void remove(ResourceIconEntity resourceIconEntity) throws Exception {
		String resouceIconId = resourceIconEntity.getResourceIconId();
		if(StringUtil.checkNull(resouceIconId)) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		this.resourceIconRepository.delete(resourceIconEntity);
		
	}
	
    /**
     * 指定主键，修改给定信息项
     * @param ResourceIconEntity
     */
	@Override
	public void modify(ResourceIconEntity resourceIconEntity) throws Exception {
		String resouceIconId = resourceIconEntity.getResourceIconId();
		if(StringUtil.checkNull(resouceIconId)) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		this.resourceIconRepository.update(resourceIconEntity);
		
	}
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param ResourceIconEntity
     * @return List<ResourceIconEntity>
     */
	@Override
	public List<ResourceIconEntity> find(ResourceIconEntity resourceIconEntity) throws Exception {
		
		return this.resourceIconRepository.select(resourceIconEntity);
	}
	
}