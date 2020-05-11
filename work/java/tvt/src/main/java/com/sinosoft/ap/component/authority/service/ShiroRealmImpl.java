//package com.sinosoft.ap.component.authority.service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import com.sinosoft.ap.system.loginmanage.service.UserLoginService;
//import com.sinosoft.ap.system.permissionmanage.domain.PermissionEntity;
//import com.sinosoft.ap.system.usermanage.domain.UserEntity;
//import com.sinosoft.ap.system.usermanage.service.UserSearchService;
//
//@Service
//public class ShiroRealmImpl extends AuthorizingRealm{
//	
//	@Autowired
//	private UserLoginService userLoginService;
//	
//	@Autowired
//	private UserSearchService userSearchService;
//	
//	/**
//	 * shiro的登录验证和权限验证
//	 */
//	@Override
//	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
//		try {
//			UserEntity userEntity = new UserEntity();
//			String currentUserName = (String) super.getAvailablePrincipal(arg0);
//			userEntity.setUserAccount(currentUserName);
//			List<UserEntity> userEntities = userSearchService.find(userEntity);
//			if(userEntities.size()<1){
//				return null;
//			}
//			userEntity = userEntities.get(0);
//			String userId = userEntity.getUserId();
//			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//			List<PermissionEntity> organPermissionEntities = new ArrayList<>();
//			organPermissionEntities = this.userLoginService.findUserOrganPermission(userId);
//			List<PermissionEntity> userPermissionEntities = new ArrayList<>();
//			userPermissionEntities = this.userLoginService.findUserPermission(userId);
//			userPermissionEntities.addAll(organPermissionEntities);
//			
//			List<String> permissions = new ArrayList<>();
//			List<String> ids = new ArrayList<>();
//			if(userPermissionEntities.size()<0){
//				return null;
//			}
//			for(PermissionEntity aEntity:userPermissionEntities){
//				if (!ids.contains(aEntity.getPermissionId())) {
//					ids.add(aEntity.getPermissionId());
//					permissions.add(aEntity.getPermissionName());
//				}
//			}
//			info.addStringPermissions(permissions);
//			return info;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	@Override
//	protected AuthenticationInfo doGetAuthenticationInfo(
//			AuthenticationToken arg0) throws AuthenticationException {
//		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)arg0;
//		String userName = usernamePasswordToken.getUsername();
//		String userPassword = new String(usernamePasswordToken.getPassword());
//		try {
//			return new SimpleAuthenticationInfo(userName, userPassword, getName());
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//	
//
//}
