package com.sinosoft.ap.system.usermanage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import com.sinosoft.ap.system.loginmanage.domain.UserLoginRepository;
import com.sinosoft.ap.system.permissionmanage.domain.PermissionEntity;
import com.sinosoft.ap.system.permissionmanage.service.PermissionSearchService;
import com.sinosoft.ap.system.permissionrolerel.service.UserRoleRelService;
import com.sinosoft.ap.util.list.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sinosoft.ap.component.page.PageParam;
import com.sinosoft.ap.system.usermanage.domain.UserEntity;
import com.sinosoft.ap.system.usermanage.domain.UserRepository;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

@Service
public class UserSearchServiceImpl implements UserSearchService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserLoginRepository userLoginRepository;

	@Autowired
	private PermissionSearchService permissionSearchService;

	@Autowired
	private UserRoleRelService userRoleRelService;
	
	@Override
	public List<UserEntity> find(String organId) throws Exception {
	
		return this.userRepository.selectUserOrganRel(organId);
	}

	/**
	 * 根据给定参数，查找相关数据，支持多条件查询
	 * @param userEntity
	 * @return List<UserEntity>
	 */
	@Override
	public List<UserEntity> find(UserEntity userEntity) throws Exception {
		return this.userRepository.select(userEntity);
	}

	@Override
	public Page<UserEntity> find(UserEntity userEntity, PageParam pageParam) throws Exception {
		PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize(), true);
		return (Page<UserEntity>) this.userRepository.select(userEntity);
	}

	@Override
	public String findOrganCodeByUserId(String userId) throws Exception {
		return this.userRepository.findOrganCodeByUserId(userId);
	}

	@Override
	public List<PermissionEntity> getPermission(String userId)throws Exception{
		//特例和特殊
		List<PermissionEntity> permissionEntitys =new ArrayList<>();
		List<PermissionEntity> self=this.permissionSearchService.findtUserPermission(userId);
		List<PermissionEntity> selfs=new ArrayList<>();
		if(!ListUtil.checkNull(self)){
			for(PermissionEntity pe:self){
				if(null!=pe.getPermissionDestroytime()){
					if(pe.getPermissionStatus()==0){
						selfs.add(pe);
					}
				}else{
					selfs.add(pe);
				}
			}
		}
		if(!ListUtil.checkNull(selfs)){
			permissionEntitys.addAll(selfs);
		}
		//机构绑定的（包含继承自最上级的）
		List<PermissionEntity> organPermissions = userLoginRepository.selectUserOrganPermission(userId);
		if(!ListUtil.checkNull(organPermissions)){
			permissionEntitys.addAll(organPermissions);
		}
		//继承自角色的许可(角色绑的一定是通用许可)
		List<PermissionEntity> rolePermissions = userLoginRepository.selectUserRolePermission(userId);
		if(!ListUtil.checkNull(rolePermissions)){
			permissionEntitys.addAll(rolePermissions);
		}
		if(!ListUtil.checkNull(permissionEntitys)){
//			HashSet<PermissionEntity> hs = new HashSet<>(permissionEntitys);
//			permissionEntitys.clear();
//			permissionEntitys.addAll(hs);
			permissionEntitys=permissionEntitys.stream().collect(
					collectingAndThen(
							toCollection(() -> new TreeSet<>(comparing(PermissionEntity::getPermissionId))), ArrayList::new)
			);
		}

		return permissionEntitys;
	}
}
