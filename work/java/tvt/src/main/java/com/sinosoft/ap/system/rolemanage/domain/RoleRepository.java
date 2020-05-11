package com.sinosoft.ap.system.rolemanage.domain;

import java.util.List;
import org.springframework.stereotype.Repository;

/***
 * @since 2017 年  04 月 07 日 01:14:00 
 */
@Repository
public interface RoleRepository{

	 /**
     * 根据给定的参数新增一条数据
     * @param RoleEntity
     */
	void insert( RoleEntity roleEntity ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param RoleEntity
     */
	void delete( RoleEntity roleEntity ) throws Exception ;
	
    /**
     * 指定主键，修改给定信息项
     * @param RoleEntity
     */
	void update( RoleEntity roleEntity ) throws Exception ;
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param RoleEntity
     * @return List<RoleEntity>
     */
	List<RoleEntity> select( RoleEntity roleEntity ) throws Exception ;
	
    /**
     * 根据用户查询角色
     * @param RoleEntity
     * @return List<RoleEntity>
     */
	List<RoleEntity> selectUserRole( String userId ) throws Exception ;
	
	
}