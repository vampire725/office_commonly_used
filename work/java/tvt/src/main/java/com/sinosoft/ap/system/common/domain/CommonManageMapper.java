package com.sinosoft.ap.system.common.domain;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sinosoft.ap.system.common.vo.RoleManageVO;
import com.sinosoft.ap.system.usermanage.domain.UserEntity;

@Repository
public interface CommonManageMapper {

	List<UserEntity> selectUserInfoByRole(RoleManageVO entity)throws Exception;

	List<UserEntity> selectUserInfoByRoles(List<String> list)throws Exception;
}
