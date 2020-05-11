package com.sinosoft.ap.system.organizationmanage.service;

import com.sinosoft.ap.system.organizationmanage.domain.OrganizationEntity;

/***
 * @since 2017 年  04 月 07 日 01:12:06 
 */
public interface OrganizationManageService {

	 /**
     * 根据给定的参数新增一条数据
     * @param OrganizationEntity
     */
	OrganizationEntity save( OrganizationEntity organizationEntity ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param OrganizationEntity
     */
	void remove( String organId ) throws Exception ;
	
    /**
     * 指定主键，修改给定信息项
     * @param OrganizationEntity
     */
	void modify( OrganizationEntity organizationEntity ) throws Exception ;
	
}