package com.sinosoft.ap.system.usersettings.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.ap.system.usersettings.domain.UserHeadEntity;
import com.sinosoft.ap.system.usersettings.domain.UserHeadRepository;
import com.sinosoft.ap.util.file.FileUtil;

import org.springframework.beans.factory.annotation.Autowired;

/***
 * @since 2017 年  04 月 07 日 01:14:52 
 */
 @Service
public class UserHeadServiceImpl implements UserHeadService{

	@Autowired
	private UserHeadRepository userHeadRepository;

	 /**
     * 根据给定的参数新增一条数据
     * @param UserHeadEntity
     */
	@Override
	public void save(UserHeadEntity userHeadEntity) throws Exception {
		this.userHeadRepository.insert(userHeadEntity);
		
	}
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param UserHeadEntity
     */
	@Override
//	@Transactional(readOnly=false,transactionManager="apsystemTransactionManager",rollbackFor = {Exception.class})
	public void remove(UserHeadEntity userHeadEntity) throws Exception {
		List<UserHeadEntity> userHeadEntities = this.userHeadRepository.select(userHeadEntity);
		if (userHeadEntities!=null&&userHeadEntities.size()>0) {
			String file = userHeadEntities.get(0).getUserHeadAddress();
			FileUtil.deleteLocalFile(file);
		}
		this.userHeadRepository.delete(userHeadEntity);
	}
	
    /**
     * 指定主键，修改给定信息项
     * @param UserHeadEntity
     */
	@Override
	public void modify(UserHeadEntity userHeadEntity) throws Exception {
		this.userHeadRepository.update(userHeadEntity);
		
	}
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param UserHeadEntity
     * @return List<UserHeadEntity>
     */
	@Override
	public List<UserHeadEntity> find(UserHeadEntity userHeadEntity) throws Exception {
		
		return this.userHeadRepository.select(userHeadEntity);
	}
	
}