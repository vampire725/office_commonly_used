package com.sinosoft.ap.system.permissionmanage.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import com.alibaba.druid.util.StringUtils;
import com.sinosoft.ap.system.operationmanage.domain.OperationEntity;
import com.sinosoft.ap.system.operationmanage.service.OperationSearchService;
import com.sinosoft.ap.system.usermanage.service.UserSearchService;
import com.sinosoft.ap.util.list.ListUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.ap.common.exception.NullAttributeException;
import com.sinosoft.ap.system.permissionmanage.domain.PermissionEntity;
import com.sinosoft.ap.system.permissionmanage.domain.PermissionRepository;
import com.sinosoft.ap.system.permissionrolerel.domain.PermissionOperationRelEntity;
import com.sinosoft.ap.system.permissionrolerel.domain.PermissionOrganRelEntity;
import com.sinosoft.ap.system.permissionrolerel.domain.PermissionResourceRelEntity;
import com.sinosoft.ap.system.permissionrolerel.domain.PermissionRoleRelEntity;
import com.sinosoft.ap.system.permissionrolerel.domain.UserPermissionRelEntity;
import com.sinosoft.ap.system.permissionrolerel.service.PermissionOperationRelService;
import com.sinosoft.ap.system.permissionrolerel.service.PermissionOrganRelService;
import com.sinosoft.ap.system.permissionrolerel.service.PermissionResourceRelService;
import com.sinosoft.ap.system.permissionrolerel.service.PermissionRoleRelService;
import com.sinosoft.ap.system.permissionrolerel.service.UserPermissionRelService;
import com.sinosoft.ap.system.resourcemanage.domain.ResourceEntity;
import com.sinosoft.ap.system.resourcemanage.service.ResService;
import com.sinosoft.ap.util.id.PrimaryKeyUtil;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/***
 * @since 2017 年  04 月 07 日 01:12:20 
 */
 @Service
public class PermissionManageServiceImpl implements PermissionManageService{

	@Autowired
	private PermissionRepository permissionRepository;
	
	@Autowired
	private PermissionOrganRelService permissionOrganRelService;
	
	@Autowired
	private UserPermissionRelService userPermissionRelService;
	
	@Autowired
	private PermissionRoleRelService permissionRoleRelService;
	
	@Autowired
	private PermissionOperationRelService permissionOperationRelService;

	@Autowired
	private UserSearchService userSearchService;

	@Autowired
	private OperationSearchService operationSearchService;

	@Autowired
	private PermissionSearchService permissionSearchService;
	
	@Autowired
	private PermissionResourceRelService permissionResourceRelService;
	
	@Autowired
	private ResService resService;

	@Value("${stateless.protocol}")
	private String superAdminProcol;

	@Value("${stateless.protocol.code}")
	private String superAdminProcolCode;
	
	 /**
     * 根据给定的参数新增一条数据
     * @param permissionEntity
     */
	@Override
	public PermissionEntity save(PermissionEntity permissionEntity) throws Exception {
		permissionEntity.setPermissionCreatetime(new Date());
		permissionEntity.setPermissionId(PrimaryKeyUtil.create());
		this.permissionRepository.insert(permissionEntity);
		return permissionEntity;
	}
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param permissionEntity
     */
	@Override
//	@Transactional(readOnly=false,transactionManager="apsystemTransactionManager",rollbackFor = {Exception.class})
	public void remove(PermissionEntity permissionEntity) throws Exception {
		String permissionId = permissionEntity.getPermissionId();
		if(StringUtil.checkNull(permissionId)) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		PermissionOperationRelEntity permissionOperationRelEntity = new PermissionOperationRelEntity();
		permissionOperationRelEntity.setPermissionId(permissionId);
		this.permissionOperationRelService.remove(permissionOperationRelEntity);
		PermissionOrganRelEntity permissionOrganRelEntity = new PermissionOrganRelEntity();
		permissionOrganRelEntity.setPermissionId(permissionId);
		this.permissionOrganRelService.remove(permissionOrganRelEntity);
		PermissionRoleRelEntity permissionRoleRelEntity = new PermissionRoleRelEntity();
		permissionRoleRelEntity.setPermissionId(permissionId);
		this.permissionRoleRelService.remove(permissionRoleRelEntity);
		UserPermissionRelEntity userPermissionRelEntity = new UserPermissionRelEntity();
		userPermissionRelEntity.setPermissionId(permissionId);
		this.userPermissionRelService.remove(userPermissionRelEntity);
		this.permissionRepository.delete(permissionEntity);
		this.permissionRepository.delete(permissionEntity);
		
	}
	
    /**
     * 指定主键，修改给定信息项
     * @param permissionEntity
     */
	@Override
	public void modify(PermissionEntity permissionEntity) throws Exception {
		String permissionId = permissionEntity.getPermissionId();
		if(StringUtil.checkNull(permissionId)) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		this.permissionRepository.update(permissionEntity);
		
	}

	@Override
	public List<OperationEntity> userAuth (String userId) throws Exception{
		List<PermissionEntity> permissionEntities = new ArrayList<>();
		List<PermissionEntity> temp = new ArrayList<>();
		if (userId.equals(superAdminProcolCode)) {
			List<PermissionEntity> list = new ArrayList<>();
			PermissionEntity permissionEntity = new PermissionEntity();
			list = this.permissionSearchService.find(permissionEntity);
			temp = list;
		}else {
//			List<PermissionEntity> organPermissionEntities = new ArrayList<>();
//			organPermissionEntities = this.userLoginService.findUserOrganPermission(userId);
//			List<PermissionEntity> userPermissionEntities = new ArrayList<>();
//			userPermissionEntities = this.userLoginService.findUserPermission(userId);
//			userPermissionEntities.addAll(organPermissionEntities);
			//
			List<PermissionEntity>  userPermissionEntities=userSearchService.getPermission(userId);
			temp = userPermissionEntities;
		}
		List<String> ids = new ArrayList<>();
		if (null!=temp&&temp.size()>0) {
			for (PermissionEntity permissionEntity:temp) {
				if (!ids.contains(permissionEntity.getPermissionId())) {
					ids.add(permissionEntity.getPermissionId());
					permissionEntities.add(permissionEntity);
				}
			}
		}
		if (ListUtil.checkNull(ids)) {
			return new ArrayList<>();
		}else {
			if(userId.equals(superAdminProcolCode)) {
				List<OperationEntity> temps = this.operationSearchService.find(new OperationEntity());
				return temps;
			}else{
				List<OperationEntity> operationEntities = new ArrayList<>();
//				operationEntities = this.operationSearchService.findByPermissionId(ids);
				//
				operationEntities=getOperation(permissionEntities);
				return operationEntities;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<OperationEntity> getOperation(List<PermissionEntity> permissionEntities)throws Exception{
		List<OperationEntity> operationEntities = new ArrayList<>();
		List<PermissionEntity> currents = new ArrayList<>();
		List<PermissionEntity> specials = new ArrayList<>();
		List<PermissionEntity> cases = new ArrayList<>();
		if(ListUtil.checkNull(permissionEntities)){
			return operationEntities;
		}
		permissionEntities.forEach(permission->{
			if(permission.getPermissionSymbol()==0){
				currents.add(permission);
			}else if(permission.getPermissionSymbol()==1){
				specials.add(permission);
			}else if(permission.getPermissionSymbol()==2){
				cases.add(permission);
			}
		});
		List<String> ids=new ArrayList<>();
		if(!ListUtil.checkNull(specials)){
			for(PermissionEntity special:specials){
				ids.add(special.getPermissionId());
			}
			if(!ListUtil.checkNull(currents)){
				List<String> intersection=new ArrayList<>();
				intersection.addAll(ids);
				List<String> idss=new ArrayList<>();
				for(PermissionEntity current:currents){
					idss.add(current.getPermissionId());
				}
				for(PermissionEntity currentt:currents){
					String currentId=currentt.getPermissionId();
					String curreId=currentt.getResourceId();
					boolean boo = true;
					for(PermissionEntity speciall:specials){
						String spereId=speciall.getResourceId();
						if(spereId.equals(curreId)){
							boo=false;
							break;
						}
					}
					if(boo){
						intersection.add(currentId);
					}
				}
				operationEntities=this.operationSearchService.findByPermissionId(intersection);
				operationEntities=operationEntities.stream().collect(collectingAndThen(
						toCollection(() -> new TreeSet<>(comparing(OperationEntity::getOperationId))), ArrayList::new)
				);
			}else{
				operationEntities=this.operationSearchService.findByPermissionId(ids);
				operationEntities=operationEntities.stream().collect(collectingAndThen(
						toCollection(() -> new TreeSet<>(comparing(OperationEntity::getOperationId))), ArrayList::new)
				);
			}
		}else{
			if(!ListUtil.checkNull(currents)){
				List<String> idss=new ArrayList<>();
				for(PermissionEntity current:currents){
					idss.add(current.getPermissionId());
				}
				operationEntities=this.operationSearchService.findByPermissionId(idss);
				operationEntities=operationEntities.stream().collect(collectingAndThen(
						toCollection(() -> new TreeSet<>(comparing(OperationEntity::getOperationId))), ArrayList::new)
				);
			}
		}
		
		if(!ListUtil.checkNull(cases)){
			for(PermissionEntity pe:cases){
				ids.add(pe.getPermissionId());
			}
			List<OperationEntity> casesOperationEntities=this.operationSearchService.findByPermissionId(ids);
			operationEntities.addAll(casesOperationEntities);
			operationEntities=operationEntities.stream().collect(collectingAndThen(
					toCollection(() -> new TreeSet<>(comparing(OperationEntity::getOperationId))), ArrayList::new)
			);
		}
		return operationEntities;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getResourceId(String userId) throws Exception {
		List<String> resourceIds=new ArrayList<>();
		if (userId.equals(superAdminProcolCode)) {
			ResourceEntity resourceEntity=new ResourceEntity();
//			resourceEntity.setResourceType("4");
//			resourceEntity.setResourceLevel(2);
			List<ResourceEntity> resources=this.resService.find(resourceEntity);
			if(!ListUtil.checkNull(resources)){
				for(ResourceEntity resource:resources){
					resourceIds.add(resource.getResourceId());
				}
			}
		}else {
			List<PermissionEntity>  userPermissionEntities=userSearchService.getPermission(userId);
			if(!ListUtil.checkNull(userPermissionEntities)){
				List<PermissionEntity> ups=new ArrayList<>();
				for(PermissionEntity per:userPermissionEntities){
					if(!StringUtils.isEmpty(per.getResourceId())){
						ups.add(per);
					}
				}
				ups=ups.stream().collect(
						collectingAndThen(
								toCollection(() -> new TreeSet<>(comparing(PermissionEntity::getResourceId))), ArrayList::new)
				);
				for(PermissionEntity perr:ups){
						resourceIds.add(perr.getResourceId());
				}
			}
		}
		return resourceIds;
	}


}