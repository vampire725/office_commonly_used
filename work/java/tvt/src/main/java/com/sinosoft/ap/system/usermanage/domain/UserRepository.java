package com.sinosoft.ap.system.usermanage.domain;

import java.util.List;
import org.springframework.stereotype.Repository;

/***
 * @since 2017 年  04 月 07 日 01:14:51 
 */
@Repository
public interface UserRepository{

	 /**
     * 根据给定的参数新增一条数据
     * @param UserEntity
     */
	void insert( UserEntity userEntity ) throws Exception ;
	
	void insertD( UserDetailsEntity userDetailsEntity ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param UserEntity
     */
	void delete( UserEntity userEntity ) throws Exception ;
	
    /**
     * 指定主键，修改给定信息项
     * @param UserEntity
     */
	void update( UserEntity userEntity ) throws Exception ;
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param UserEntity
     * @return List<UserEntity>
     */
	List<UserEntity> select( UserEntity userEntity ) throws Exception ;
	
	List<UserEntity> selectUserOrganRel( String organId ) throws Exception ;
	

	/**
	 * 根据用户id查询机构编码
	 */
	String findOrganCodeByUserId(String userId) throws Exception;
	
	
	List<UserEntity> findUserByOrganCodeAndPermissionName(String organCode,String permissionName) throws Exception;
	//搜索机构下符合多个许可的用户
	List<UserEntity> findUserLikeOrganCodeAndPermissionName(String organCode,String permissionName) throws Exception;

}