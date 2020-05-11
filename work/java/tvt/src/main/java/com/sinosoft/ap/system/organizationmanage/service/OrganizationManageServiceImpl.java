package com.sinosoft.ap.system.organizationmanage.service;

import java.util.Date;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.ap.common.exception.NullAttributeException;
import com.sinosoft.ap.system.organizationmanage.domain.OrganizationEntity;
import com.sinosoft.ap.system.organizationmanage.domain.OrganizationRepository;
import com.sinosoft.ap.system.permissionrolerel.domain.PermissionOrganRelEntity;
import com.sinosoft.ap.system.permissionrolerel.service.PermissionOrganRelService;
import com.sinosoft.ap.system.userorganizationrel.domain.UserOrganizationRelEntity;
import com.sinosoft.ap.system.userorganizationrel.service.UserOrganizationRelService;
import com.sinosoft.ap.util.id.PrimaryKeyUtil;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;

/***
 * @since 2017 年  04 月 07 日 01:12:06 
 * 
 */
 @Service
public class OrganizationManageServiceImpl implements OrganizationManageService{

	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Autowired
	private UserOrganizationRelService userOrganizationRelService;
	
	@Autowired
	private PermissionOrganRelService permissionOrganRelService;

	 /**
     * 根据给定的参数新增一条数据
     * @param OrganizationEntity
     */
	@Override
	public OrganizationEntity save(OrganizationEntity organizationEntity) throws Exception {
		organizationEntity.setOrganId(PrimaryKeyUtil.create());
		organizationEntity.setOrganCreatetime(new Date());
		this.organizationRepository.insert(organizationEntity);
		return organizationEntity;
	}
	
    /**
     * 根据给定条件删除一条数据
     * @param OrganizationEntity
     */
	@Override
//	@Transactional(readOnly=false,transactionManager="apsystemTransactionManager",rollbackFor = {Exception.class})
	public void remove(String organId) throws Exception {
		if(StringUtil.checkNull(organId)) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		UserOrganizationRelEntity userOrganizationRelEntity = new UserOrganizationRelEntity();
		userOrganizationRelEntity.setOrganId(organId);
		this.userOrganizationRelService.remove(userOrganizationRelEntity);
		PermissionOrganRelEntity permissionOrganRelEntity = new PermissionOrganRelEntity();
		permissionOrganRelEntity.setOrganId(organId);
		this.permissionOrganRelService.remove(permissionOrganRelEntity);
		this.organizationRepository.delete(organId);
		
	}
	
    /**
     * 指定主键，修改给定信息项
     * @param OrganizationEntity
     */
	@Override
	@Transactional(rollbackFor = {IllegalArgumentException.class})
	public void modify(OrganizationEntity organizationEntity) throws Exception {
		if(StringUtil.checkNull(organizationEntity.getOrganId())) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		this.organizationRepository.update(organizationEntity);
		
	}
	
}