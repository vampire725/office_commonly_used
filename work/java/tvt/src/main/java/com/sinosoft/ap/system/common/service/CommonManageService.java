package com.sinosoft.ap.system.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sinosoft.ap.system.common.vo.RoleManageVO;
import com.sinosoft.ap.system.usermanage.domain.UserEntity;
@Service
public interface CommonManageService {

	List<UserEntity> selectUserInfoByRole(RoleManageVO entity)throws Exception;

	List<UserEntity> selectUserInfoByRoles(List<String> roleNames)throws Exception;

}
