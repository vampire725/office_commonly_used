package com.sinosoft.ap.system.loginmanage.service;

import java.util.List;

import com.sinosoft.ap.system.loginmanage.domain.UserPasswordEntity;

/***
 * @since 2017 年  04 月 07 日 01:14:52 
 */
public interface UserPasswordService {

	 /**
     * 根据给定的参数新增一条数据
     * @param UserPasswordEntity
     */
	void save( UserPasswordEntity userPasswordEntity ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param UserPasswordEntity
     */
	void remove( UserPasswordEntity userPasswordEntity ) throws Exception ;
	
    /**
     * 指定主键，修改给定信息项
     * @param UserPasswordEntity
     */
	void modify( UserPasswordEntity userPasswordEntity ) throws Exception ;
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param UserPasswordEntity
     * @return List<UserPasswordEntity>
     */
	List<UserPasswordEntity> find( UserPasswordEntity userPasswordEntity) throws Exception ;
	
}