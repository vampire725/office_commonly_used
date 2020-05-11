package com.sinosoft.ap.system.loginmanage.domain;

import java.util.List;
import org.springframework.stereotype.Repository;

/***
 * @since 2017 年  04 月 07 日 01:14:52 
 */
@Repository
public interface UserLoginLogRepository{

	 /**
     * 根据给定的参数新增一条数据
     * @param UserLoginLogEntity
     */
	void insert( UserLoginLogEntity userLoginLogEntity ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param UserLoginLogEntity
     */
	void delete( UserLoginLogEntity userLoginLogEntity ) throws Exception ;
	
    /**
     * 指定主键，修改给定信息项
     * @param UserLoginLogEntity
     */
	void update( UserLoginLogEntity userLoginLogEntity ) throws Exception ;
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param UserLoginLogEntity
     * @return List<UserLoginLogEntity>
     */
	List<UserLoginLogEntity> select( UserLoginLogEntity userLoginLogEntity ) throws Exception ;
	
}