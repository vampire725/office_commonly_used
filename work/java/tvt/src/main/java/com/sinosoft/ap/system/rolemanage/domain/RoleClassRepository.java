package com.sinosoft.ap.system.rolemanage.domain;


import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface RoleClassRepository {

	void insert (RoleClassEntity entity) throws Exception;
	
	void deleteByRoles (List<String> roleId) throws Exception;
	
	void deleteByRole (String roleId) throws Exception;
	
	void deleteByClass (String classId) throws Exception;
	
	List<RoleEntity> select(String classId) throws Exception;
	
	List<RoleEntity> selectLike(String value) throws Exception;
	
	RoleEntity selectByName (String roleName) throws Exception;
	
	List<RoleEntity> selectUserRole(String userId) throws Exception;
}
