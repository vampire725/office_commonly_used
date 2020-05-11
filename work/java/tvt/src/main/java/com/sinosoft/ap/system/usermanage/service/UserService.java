package com.sinosoft.ap.system.usermanage.service;

import com.sinosoft.ap.system.usermanage.domain.UserEntity;

/***
 * @since 2017 年  04 月 07 日 01:14:51 
 */
public interface UserService {

	 /**
     * 根据给定的参数新增一条数据
     * @param UserEntity
     */
	UserEntity save( UserEntity userEntity ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param UserEntity
     */
	void remove( UserEntity userEntity ) throws Exception ;
	
    /**
     * 指定主键，修改给定信息项
     * @param UserEntity
     */
	void modify( UserEntity userEntity ) throws Exception ;
	
}