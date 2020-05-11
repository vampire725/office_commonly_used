package com.sinosoft.ap.system.userorganizationrel.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.sinosoft.ap.system.userorganizationrel.domain.UserOrganizationRelEntity;
import com.sinosoft.ap.system.userorganizationrel.domain.UserOrganizationRelRepository;

import org.springframework.beans.factory.annotation.Autowired;

/***
 * @since 2017 年  04 月 07 日 01:39:43 
 */
 @Service
public class UserOrganizationRelServiceImpl implements UserOrganizationRelService{

	@Autowired
	private UserOrganizationRelRepository userOrganizationRelRepository;

	 /**
     * 根据给定的参数新增一条数据
     * @param UserOrganizationRelEntity
     */
	@Override
	public void save(UserOrganizationRelEntity userOrganizationRelEntity) throws Exception {
		this.userOrganizationRelRepository.insert(userOrganizationRelEntity);
		
	}
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param UserOrganizationRelEntity
     */
	@Override
	public void remove(UserOrganizationRelEntity userOrganizationRelEntity) throws Exception {
		this.userOrganizationRelRepository.delete(userOrganizationRelEntity);
		
	}
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param UserOrganizationRelEntity
     * @return List<UserOrganizationRelEntity>
     */
	@Override
	public List<UserOrganizationRelEntity> find(UserOrganizationRelEntity userOrganizationRelEntity) throws Exception {
		
		return this.userOrganizationRelRepository.select(userOrganizationRelEntity);
	}
	
}