package com.sinosoft.ap.system.loginmanage.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sinosoft.ap.system.loginmanage.domain.UserLoginLogEntity;

/***
 * @since 2017 年  04 月 07 日 01:14:52 
 */
public interface UserLoginLogService {

	 /**
     * 根据给定的参数新增一条数据
     * @param UserLoginLogEntity
     */
	void save( UserLoginLogEntity userLoginLogEntity,HttpServletRequest request,HttpServletResponse response,HttpSession session ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param UserLoginLogEntity
     */
	void remove( UserLoginLogEntity userLoginLogEntity ) throws Exception ;
	
    /**
     * 指定主键，修改给定信息项
     * @param UserLoginLogEntity
     */
	void modify( UserLoginLogEntity userLoginLogEntity ) throws Exception ;
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param UserLoginLogEntity
     * @return List<UserLoginLogEntity>
     */
	List<UserLoginLogEntity> find( UserLoginLogEntity userLoginLogEntity) throws Exception ;
	
}