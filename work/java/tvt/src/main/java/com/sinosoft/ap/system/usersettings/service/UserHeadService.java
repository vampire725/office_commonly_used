package com.sinosoft.ap.system.usersettings.service;

import java.util.List;

import com.sinosoft.ap.system.usersettings.domain.UserHeadEntity;

/***
 * @since 2017 年  04 月 07 日 01:14:51 
 */
public interface UserHeadService {

	 /**
     * 根据给定的参数新增一条数据
     * @param UserHeadEntity
     */
	void save( UserHeadEntity userHeadEntity ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param UserHeadEntity
     */
	void remove( UserHeadEntity userHeadEntity ) throws Exception ;
	
    /**
     * 指定主键，修改给定信息项
     * @param UserHeadEntity
     */
	void modify( UserHeadEntity userHeadEntity ) throws Exception ;
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param UserHeadEntity
     * @return List<UserHeadEntity>
     */
	List<UserHeadEntity> find( UserHeadEntity userHeadEntity) throws Exception ;
	
}