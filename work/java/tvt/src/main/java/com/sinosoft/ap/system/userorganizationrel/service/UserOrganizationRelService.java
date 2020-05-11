package com.sinosoft.ap.system.userorganizationrel.service;

import java.util.List;

import com.sinosoft.ap.system.userorganizationrel.domain.UserOrganizationRelEntity;

/***
 * @since 2017 年  04 月 07 日 01:39:43 
 */
public interface UserOrganizationRelService {

	 /**
     * 根据给定的参数新增一条数据
     * @param UserOrganizationRelEntity
     */
	void save( UserOrganizationRelEntity userOrganizationRelEntity ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param UserOrganizationRelEntity
     */
	void remove( UserOrganizationRelEntity userOrganizationRelEntity ) throws Exception ;
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param UserOrganizationRelEntity
     * @return List<UserOrganizationRelEntity>
     */
	List<UserOrganizationRelEntity> find( UserOrganizationRelEntity userOrganizationRelEntity) throws Exception ;
	
}