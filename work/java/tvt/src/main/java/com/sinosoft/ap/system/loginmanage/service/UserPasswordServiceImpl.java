package com.sinosoft.ap.system.loginmanage.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.sinosoft.ap.system.loginmanage.domain.UserPasswordEntity;
import com.sinosoft.ap.system.loginmanage.domain.UserPasswordRepository;

import org.springframework.beans.factory.annotation.Autowired;

/***
 * @since 2017 年  04 月 07 日 01:14:52 
 */
 @Service
public class UserPasswordServiceImpl implements UserPasswordService{

	@Autowired
	private UserPasswordRepository userPasswordRepository;

	 /**
     * 根据给定的参数新增一条数据
     * @param UserPasswordEntity
     */
	@Override
	public void save(UserPasswordEntity userPasswordEntity) throws Exception {
		this.userPasswordRepository.insert(userPasswordEntity);
		
	}
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param UserPasswordEntity
     */
	@Override
	public void remove(UserPasswordEntity userPasswordEntity) throws Exception {
		this.userPasswordRepository.delete(userPasswordEntity);
		
	}
	
    /**
     * 指定主键，修改给定信息项
     * @param UserPasswordEntity
     */
	@Override
	public void modify(UserPasswordEntity userPasswordEntity) throws Exception {
		this.userPasswordRepository.update(userPasswordEntity);
		
	}
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param UserPasswordEntity
     * @return List<UserPasswordEntity>
     */
	@Override
	public List<UserPasswordEntity> find(UserPasswordEntity userPasswordEntity) throws Exception {
		
		return this.userPasswordRepository.select(userPasswordEntity);
	}
	
}