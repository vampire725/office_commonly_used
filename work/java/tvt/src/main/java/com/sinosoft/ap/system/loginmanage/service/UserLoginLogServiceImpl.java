package com.sinosoft.ap.system.loginmanage.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.sinosoft.ap.system.loginmanage.domain.UserLoginLogEntity;
import com.sinosoft.ap.system.loginmanage.domain.UserLoginLogRepository;
import com.sinosoft.ap.util.visitweb.VisitInfo;

import org.springframework.beans.factory.annotation.Autowired;

/***
 * @since 2017 年  04 月 07 日 01:14:52 
 */
 @Service
public class UserLoginLogServiceImpl implements UserLoginLogService{

	@Autowired
	private UserLoginLogRepository userLoginLogRepository;

	 /**
     * 根据给定的参数新增一条数据
     * @param UserLoginLogEntity
     */
	@Override
	public void save(UserLoginLogEntity userLoginLogEntity,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		userLoginLogEntity.setLoginBrower(VisitInfo.getRequestBrowserInfo(request));
		userLoginLogEntity.setLoginIp(VisitInfo.getIpAddr(request));
		userLoginLogEntity.setLoginTime(new Date());
		userLoginLogEntity.setLoginOs(VisitInfo.getRequestSystemInfo(request));
		this.userLoginLogRepository.insert(userLoginLogEntity);
		
	}
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param UserLoginLogEntity
     */
	@Override
	public void remove(UserLoginLogEntity userLoginLogEntity) throws Exception {
		this.userLoginLogRepository.delete(userLoginLogEntity);
		
	}
	
    /**
     * 指定主键，修改给定信息项
     * @param UserLoginLogEntity
     */
	@Override
	public void modify(UserLoginLogEntity userLoginLogEntity) throws Exception {
		this.userLoginLogRepository.update(userLoginLogEntity);
		
	}
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param UserLoginLogEntity
     * @return List<UserLoginLogEntity>
     */
	@Override
	public List<UserLoginLogEntity> find(UserLoginLogEntity userLoginLogEntity) throws Exception {
		
		return this.userLoginLogRepository.select(userLoginLogEntity);
	}
	
}