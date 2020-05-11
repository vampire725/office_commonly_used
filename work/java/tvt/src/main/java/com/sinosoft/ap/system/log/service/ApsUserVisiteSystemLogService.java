package com.sinosoft.ap.system.log.service;


import java.util.Map;

import javax.servlet.ServletRequest;

import com.sinosoft.ap.common.aop.annotation.Info;
import com.sinosoft.ap.system.log.domain.ApsUserVisiteSystemLog;

public interface ApsUserVisiteSystemLogService {

	@Info("根据条件添加")
	public int insert(ApsUserVisiteSystemLog info)throws Exception;
	@Info("根据主键删除")
	public int deleteByKey(ApsUserVisiteSystemLog info)throws Exception;
	@Info("根据主键更新")
	public int updateByKey(ApsUserVisiteSystemLog info)throws Exception;
	
	/**
	 * 保存日志信息表
	 * @param userInfo
	 */
	public void saveUserLogSysteInfos(String  userid,String systemCode);
}
