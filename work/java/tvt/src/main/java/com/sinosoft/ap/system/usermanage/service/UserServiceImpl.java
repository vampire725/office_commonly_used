package com.sinosoft.ap.system.usermanage.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.ap.common.exception.NullAttributeException;
import com.sinosoft.ap.system.loginmanage.domain.UserPasswordEntity;
import com.sinosoft.ap.system.loginmanage.service.UserPasswordService;
import com.sinosoft.ap.system.permissionrolerel.domain.UserPermissionRelEntity;
import com.sinosoft.ap.system.permissionrolerel.domain.UserRoleRelEntity;
import com.sinosoft.ap.system.permissionrolerel.service.UserPermissionRelService;
import com.sinosoft.ap.system.permissionrolerel.service.UserRoleRelService;
import com.sinosoft.ap.system.usermanage.domain.UserDetailsEntity;
import com.sinosoft.ap.system.usermanage.domain.UserEntity;
import com.sinosoft.ap.system.usermanage.domain.UserRepository;
import com.sinosoft.ap.system.userorganizationrel.domain.UserOrganizationRelEntity;
import com.sinosoft.ap.system.userorganizationrel.service.UserOrganizationRelService;
import com.sinosoft.ap.system.usersettings.domain.UserHeadEntity;
import com.sinosoft.ap.system.usersettings.service.UserHeadService;
import com.sinosoft.ap.util.file.FileUtil;
import com.sinosoft.ap.util.id.PrimaryKeyUtil;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;

/***
 * @since 2017 年  04 月 07 日 01:14:51 
 */
 @Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserOrganizationRelService userOrganizationRelService;
	
	@Autowired
	private UserPermissionRelService userPermissionRelService;
	
	@Autowired
	private UserRoleRelService userRoleRelService;
	
	@Autowired
	private UserHeadService userHeadService;
	
	@Autowired
	private UserPasswordService userPasswordService;

	 /**
     * 根据给定的参数新增一条数据
     * @param UserEntity
     */
	@Override
	public UserEntity save(UserEntity userEntity) throws Exception {
		this.userRepository.insert(userEntity);
		UserDetailsEntity userDetailsEntity = new UserDetailsEntity();
		BeanUtils.copyProperties(userEntity, userDetailsEntity);
		userDetailsEntity.setUserDetailsId(PrimaryKeyUtil.create());
		this.userRepository.insertD(userDetailsEntity);
		return userEntity;
	}
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param UserEntity
     */
	@Override
//	@Transactional(readOnly=false,transactionManager="apsystemTransactionManager",rollbackFor = {Exception.class})
	public void remove(UserEntity userEntity) throws Exception {
		String userId = userEntity.getUserId();
		if(StringUtil.checkNull(userId)) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		UserOrganizationRelEntity userOrganizationRelEntity = new UserOrganizationRelEntity();
		userOrganizationRelEntity.setUserId(userId);
		UserRoleRelEntity userRoleRelEntity = new UserRoleRelEntity();
		userRoleRelEntity.setUserId(userId);
		UserPermissionRelEntity userPermissionRelEntity = new UserPermissionRelEntity();
		userPermissionRelEntity.setUserId(userId);
		UserHeadEntity userHeadEntity = new UserHeadEntity();
		userHeadEntity.setUserId(userId);
		UserPasswordEntity userPasswordEntity = new UserPasswordEntity();
		userPasswordEntity.setUserId(userId);
		String file = userHeadEntity.getUserHeadAddress();
		this.userHeadService.remove(userHeadEntity);
		this.userOrganizationRelService.remove(userOrganizationRelEntity);
		this.userPermissionRelService.remove(userPermissionRelEntity);
		this.userRoleRelService.remove(userRoleRelEntity);
		this.userPasswordService.remove(userPasswordEntity);
		FileUtil.deleteLocalFile(file);
		this.userRepository.delete(userEntity);
		
	}
	
    /**
     * 指定主键，修改给定信息项
     * @param UserEntity
     */
	@Override
	public void modify(UserEntity userEntity) throws Exception {
		String userId = userEntity.getUserId();
		if(StringUtil.checkNull(userId)) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		this.userRepository.update(userEntity);
		
	}
	
}