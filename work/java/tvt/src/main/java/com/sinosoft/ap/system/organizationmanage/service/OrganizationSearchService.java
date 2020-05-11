package com.sinosoft.ap.system.organizationmanage.service;

import java.util.List;

import com.sinosoft.ap.system.organizationmanage.domain.OrganizationEntity;
import com.sinosoft.ap.system.organizationmanage.domain.UserOrganizationTree;

public interface OrganizationSearchService {

	/**
	 * 根据给定参数，查找相关数据，支持多条件查询
	 * @param OrganizationEntity
	 * @return List<OrganizationEntity>
	 */
	List<OrganizationEntity> find( OrganizationEntity organizationEntity) throws Exception ;

	/**
	 * 查询用户组织树
	 * @return
	 * @throws Exception
	 */
	List<UserOrganizationTree> findUserOrganTree(String organId) throws Exception;
	/**
	 * 根据用户ID查询所属组织机构信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	OrganizationEntity findByUser(String userId) throws Exception;

}
