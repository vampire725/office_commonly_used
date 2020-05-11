package com.sinosoft.ap.system.rolemanage.web;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinosoft.ap.common.aop.annotation.Info;
import com.sinosoft.ap.system.rolemanage.domain.RoleClassEntity;
import com.sinosoft.ap.system.rolemanage.domain.RoleClassRepository;
import com.sinosoft.ap.system.rolemanage.domain.RoleEntity;
import com.sinosoft.ap.system.rolemanage.domain.RoleRepository;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/ap-system")
@Api(value="角色分类管理",description="角色分类管理")
public class RoleClass {
	
	@Autowired
	private RoleClassRepository roleClassRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Info("分类关联角色")
	@RequestMapping("/bindRole")
	@ResponseBody
	@ApiOperation(value="分类关联角色",response=Result.class,httpMethod="POST")
	public Object bindRole(String roleId,String classId){
		if (StringUtil.checkNull(classId)||StringUtil.checkNull(roleId)) {
			return new Result(ResultConstant.FAIL_STATU, "请指定角色和分类");
		}
		RoleClassEntity roleClassEntity = new RoleClassEntity();
		roleClassEntity.setClassId(classId);
		roleClassEntity.setRoleId(Arrays.asList(roleId.split(",")));
		try {
			this.roleClassRepository.deleteByRoles(Arrays.asList(roleId.split(",")));
			this.roleClassRepository.insert(roleClassEntity);
			return new Result(ResultConstant.SUCCESS_STATU, "绑定成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, "绑定失败");
		}
	}
	
	@Info("查询分类下角色")
	@RequestMapping("/selectRole")
	@ResponseBody
	@ApiOperation(value="查询分类下角色",response=Result.class,httpMethod="POST")
	public Object selectRole(String classId){
		if (StringUtil.checkNull(classId)) {
			return new 	Result(ResultConstant.FAIL_STATU, "请指定分类");
		}
		try {
			return new Result(ResultConstant.SUCCESS_STATU, "查询成功",this.roleClassRepository.select(classId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, "查询失败");
		}
	}
	
	@Info("模糊查询角色")
	@RequestMapping("/selectRoleLike")
	@ResponseBody
	@ApiOperation(value="模糊查询角色",response=Result.class,httpMethod="POST")
	public Object selectRoleLike(String value){
		if (StringUtil.checkNull(value)) {
			return new 	Result(ResultConstant.FAIL_STATU, "请指定角色");
		}
		try {
			return new Result(ResultConstant.SUCCESS_STATU, "查询成功",this.roleClassRepository.selectLike("%"+value+"%"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, "查询失败");
		}
	}
	
	@Info("查询全部角色")
	@RequestMapping("/selectAllRole")
	@ResponseBody
	@ApiOperation(value="查询全部角色",response=Result.class,httpMethod="POST")
	public Object selectAllRole(){
		try {
			return new Result(ResultConstant.SUCCESS_STATU, "查询成功",this.roleRepository.select(new RoleEntity()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, "查询失败");
		}
	}
	
	@Info("查询用户角色")
	@RequestMapping("/selectUserRole")
	@ResponseBody
	@ApiOperation(value="查询用户角色",response=Result.class,httpMethod="POST")
	public Object selectUserRole(String userId){
		try {
			return new Result(ResultConstant.SUCCESS_STATU, "查询成功",this.roleRepository.selectUserRole(userId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, "查询失败");
		}
	}
}
