package com.sinosoft.ap.system.organizationmanage.domain;

import java.util.List;
import org.springframework.stereotype.Repository;

/***
 * @since 2017 年  04 月 07 日 01:12:06 
 */
@Repository
public interface OrganizationRepository{

	 /**
     * 根据给定的参数新增一条数据
     * @param OrganizationEntity
     */
	void insert( OrganizationEntity organizationEntity ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据
     * @param OrganizationEntity
     */
	void delete( String organId ) throws Exception ;
	
    /**
     * 指定主键，修改给定信息项
     * @param OrganizationEntity
     */
	void update( OrganizationEntity organizationEntity ) throws Exception ;
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param OrganizationEntity
     * @return List<OrganizationEntity>
     */
	List<OrganizationEntity> select( OrganizationEntity organizationEntity ) throws Exception ;
	/**
	 * 查询用户树
	 * @return
	 * @throws Exception
	 */
	List<UserOrganizationTree> selectUserTree(String organId) throws Exception;
	/**
	 * 查询组织树
	 * @return
	 * @throws Exception
	 */
	List<UserOrganizationTree> selectOrganTree(String organId) throws Exception;
	/**
	 * 查询用户组织机构信息
	 * @param UserId
	 * @return
	 * @throws Exception
	 */
	OrganizationEntity selectByUserId(String UserId) throws Exception;
	/**
	 * 根据用户主键查询其所在组织机构及其子机构信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<OrganizationEntity> selectOrganListByUserId(String userId) throws Exception;
	/**
	 * 根据组织机构编码模糊查询
	 * @param code
	 * @return
	 * @throws Exception
	 */
	List<OrganizationEntity> selectByCodeLike(String code) throws Exception;
}