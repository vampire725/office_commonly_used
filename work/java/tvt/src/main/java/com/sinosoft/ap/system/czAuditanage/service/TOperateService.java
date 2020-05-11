package com.sinosoft.ap.system.czAuditanage.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.sinosoft.ap.component.page.PageParam;
import com.sinosoft.ap.system.czAuditanage.domain.TOperateEntity;

public interface TOperateService {
	//保存
	public TOperateEntity saveOperate(TOperateEntity operate)throws Exception;
	//删除
	public Integer removeOperate(String id)throws Exception;
	//查询账号是否存在
	List<TOperateEntity> selectDistinct(TOperateEntity operate)throws Exception;
	/**
	 * 分页查询操作列表
	 * @param operate
	 * @param param
	 * @return
	 * @throws Exception
	 * 2018年10月30日
	 * lt
	 * Page<TOperateEntity>
	 */
	Page<TOperateEntity> findOperateList(TOperateEntity operate,PageParam param)throws Exception;
	/**
	 * 根据条件查询操作列表
	 * @param operate
	 * @return
	 * @throws Exception
	 * 2018年10月30日
	 * lt
	 * List<TOperateEntity>
	 */
	List<TOperateEntity> findOperateList(TOperateEntity operate)throws Exception;

}
