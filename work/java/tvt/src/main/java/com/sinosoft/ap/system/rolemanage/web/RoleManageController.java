package com.sinosoft.ap.system.rolemanage.web;

import static com.sinosoft.ap.util.token.GetToken.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinosoft.ap.common.aop.annotation.Info;
import com.sinosoft.ap.system.rolemanage.domain.RoleClassEntity;
import com.sinosoft.ap.system.rolemanage.domain.RoleClassRepository;
import com.sinosoft.ap.system.rolemanage.domain.RoleEntity;
import com.sinosoft.ap.system.rolemanage.service.RoleManageService;
import com.sinosoft.ap.util.cluster.web.Cluster;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/ap-system")
@Api(value="角色管理",description="角色管理")
public class RoleManageController {

	@Autowired
	private RoleManageService roleManageService;
	
	@Autowired
	private Cluster cluster;
	
	@Autowired
	private RoleClassRepository roleClassRepository;
	
	@Value("${ap.cluster.token.key}") 
	private String tokenKey;
	/**
	 * 根据id删除角色
	 * 其中，roleId不能为空
	 * @param roleId
	 * @return
	 */
	@Info("根据id删除角色")
	@RequestMapping("/removeRole")
	@ResponseBody
	@ApiOperation(value="根据id删除角色",response=Result.class,httpMethod="POST")
	public Object removeByAttribute(String roleId,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param.put("roleId", roleId);
		Object result = cluster.getParentResult("removeRole", param,analysis(request));
		if(null!=result) {
			return result;
		}
		RoleEntity entity = new RoleEntity();
	    entity.setRoleId(roleId);
		try {
			roleManageService.remove(entity);
			roleClassRepository.deleteByRole(roleId);
			return new Result(ResultConstant.SUCCESS_STATU,
					ResultConstant.DELETE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	/**
	 * 保存角色
	 * @param roleEntity
	 * @return
	 */
	@Info("保存角色")
	@RequestMapping("/saveRole")
	@ResponseBody
	@ApiOperation(value="保存角色",response=Result.class,httpMethod="POST")
	public Object saveRole(RoleEntity roleEntity,ServletRequest request,String classId){
//		Map<String, Object> param = new HashMap<>();
//		param = JSONObject.fromObject(roleEntity);
//		Object result = cluster.getParentResult("saveRole", param,analysis(request));
//		if(null!=result) {
//			return result;
//		}
		if(StringUtil.checkNull(classId)) {
			return new Result(ResultConstant.FAIL_STATU,"请选择分类"); 
		}
		try {
			String roleName = roleEntity.getRoleName();
			RoleEntity roleEntity2 = roleClassRepository.selectByName(roleName);
			if(null!=roleEntity2&&!StringUtil.checkNull(roleEntity2.getRoleId())) {
				return new Result(ResultConstant.FAIL_STATU, "角色名称重复");
			}
			RoleEntity roleEntityResult = roleManageService.save(roleEntity);
			List<String> list = new ArrayList<>();
			list.add(roleEntityResult.getRoleId());
			RoleClassEntity roleClassEntity = new RoleClassEntity();
			roleClassEntity.setClassId(classId);
			roleClassEntity.setRoleId(list);
			this.roleClassRepository.insert(roleClassEntity);
			return new Result(ResultConstant.SUCCESS_STATU, 
					ResultConstant.INSERT_SUCCESS,roleEntityResult
					);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	/**
	 *修改角色，roleId不能为空，同时除roleId外至少包含一条以上非空数据才能修改成功
	 * @param roleEntity
	 * @return
	 */
	@Info("修改角色")
	@SuppressWarnings("unchecked")
	@RequestMapping("/modifyRole")
	@ResponseBody
	@ApiOperation(value="修改角色",response=Result.class,httpMethod="POST")
	public Object modifyByPrimaryColumn(RoleEntity roleEntity,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param = JSONObject.fromObject(roleEntity);
		Object result = cluster.getParentResult("modifyRole", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			roleManageService.modify(roleEntity);
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.UPDATA_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
}