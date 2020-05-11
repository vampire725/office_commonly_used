package com.sinosoft.ap.system.czAuditanage.domain;

import java.util.List;


public interface TOperateRepository {
		//添加
		void insertOperate(TOperateEntity operate)throws Exception;
		//删除
		Integer deleteById(List<String> list)throws Exception;
		//查询账号是否存在
		List<TOperateEntity> selectDistincts(TOperateEntity operate)throws Exception;
		List<TOperateEntity> selectOperateList(TOperateEntity operate)throws Exception;
}
