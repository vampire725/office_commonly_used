package com.sinosoft.ap.system.resourcemanage.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.sinosoft.ap.system.resourcemanage.domain.ResourceIconRelEntity;
import com.sinosoft.ap.system.resourcemanage.domain.ResourceIconRelRepository;

import org.springframework.beans.factory.annotation.Autowired;

/***
 * @since 2017 年  04 月 07 日 01:39:43 
 */
 @Service
public class ResourceIconRelServiceImpl implements ResourceIconRelService{

	@Autowired
	private ResourceIconRelRepository resourceIconRelRepository;

	 /**
     * 根据给定的参数新增一条数据
     * @param ResourceIconRelEntity
     */
	@Override
	public void save(ResourceIconRelEntity resourceIconRelEntity) throws Exception {
		this.resourceIconRelRepository.insert(resourceIconRelEntity);
		
	}
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param ResourceIconRelEntity
     */
	@Override
	public void remove(ResourceIconRelEntity resourceIconRelEntity) throws Exception {
		this.resourceIconRelRepository.delete(resourceIconRelEntity);
		
	}
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param ResourceIconRelEntity
     * @return List<ResourceIconRelEntity>
     */
	@Override
	public List<ResourceIconRelEntity> find(ResourceIconRelEntity resourceIconRelEntity) throws Exception {
		
		return this.resourceIconRelRepository.select(resourceIconRelEntity);
	}
	
}