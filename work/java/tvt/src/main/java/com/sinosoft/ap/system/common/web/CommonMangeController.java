package com.sinosoft.ap.system.common.web;

import static com.sinosoft.ap.util.token.GetToken.analysis;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinosoft.ap.common.aop.annotation.Info;
import com.sinosoft.ap.system.common.service.CommonManageService;
import com.sinosoft.ap.system.common.vo.OrganizationEntityVO;
import com.sinosoft.ap.system.common.vo.RoleManageVO;
import com.sinosoft.ap.system.organizationmanage.domain.OrganizationEntity;
import com.sinosoft.ap.system.organizationmanage.domain.OrganizationRepository;
import com.sinosoft.ap.system.usermanage.domain.UserEntity;
import com.sinosoft.ap.system.usermanage.service.UserSearchService;
import com.sinosoft.ap.util.cluster.web.Cluster;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;

@RestController
@RequestMapping("/ap-system")
public class CommonMangeController {
	
	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Autowired
	private CommonManageService commonservice ;
	
	@Autowired
	private UserSearchService userSearchService;
	
	@Autowired
	private Cluster cluster;
	/**
	 * 根据机构编码查询组织机构
	 * @return
	 */
	@Info("根据机构编码查询组织机构")
	@RequestMapping("/selectOrganInfoById")
	@ResponseBody
	public Object selectOrganInfoById(OrganizationEntity entity,ServletRequest request){
		if(StringUtil.checkNull(entity.getOrganId())) {
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.NULL_ATTRIBUTE);
		}
		Map<String, Object> param = new HashMap<>();
		param = JSONObject.fromObject(entity);
		Object result = cluster.getParentResult("selectOrganInfoById", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			List<OrganizationEntityVO> list = new ArrayList<OrganizationEntityVO>();
			List<OrganizationEntity> select = this.organizationRepository.select(entity);
			if(select.size()>0){
				OrganizationEntityVO vo = new OrganizationEntityVO();
				vo.setOrganCode(select.get(0).getOrganCode());
				vo.setOrganCreatetime(select.get(0).getOrganCreatetime());
				vo.setOrganDesc(select.get(0).getOrganDesc());
				vo.setOrganId(select.get(0).getOrganId());
				vo.setOrganName(select.get(0).getOrganName());
				vo.setOrganParentId(select.get(0).getOrganParentId());
				vo.setOrganPath(select.get(0).getOrganPath());
				vo.setOrganLevel(select.get(0).getOrganLevel());
				vo.setOrganSort(select.get(0).getOrganSort());
				entity.setOrganId(select.get(0).getOrganParentId());
				List<OrganizationEntity> select2 = this.organizationRepository.select(entity);
				if(select2 !=null&&select2.size()>0){
					vo.setParentOrganCode(select2.get(0).getOrganCode());
				}
				list.add(vo);
			}
			return new Result(ResultConstant.SUCCESS_STATU, 
					ResultConstant.SELECT_SUCCESS, 
					list);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	/**
	 * 根据机构编码查询组织机构
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Info("根据机构编码查询组织机构")
	@RequestMapping("/selectUserInfoByRole")
	@ResponseBody
	public Object selectUserInfoByRole(RoleManageVO entity,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param=JSONObject.fromObject(entity);
		Object result = cluster.getParentResult("selectUserInfoByRole", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			List<UserEntity> list = this.commonservice.selectUserInfoByRole(entity);
			return new Result(ResultConstant.SUCCESS_STATU, 
					ResultConstant.SELECT_SUCCESS, 
					list);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	/**
	 * 根据组织机构ID查询用户
	 * @param organId
	 * @param request
	 * @return
	 */
	@Info("根据组织机构ID查询用户")
	@RequestMapping("/findUserByOrganIdCommon")
	@ResponseBody
	@ApiOperation(value="根据组织机构ID查询用户",response=Result.class,httpMethod="POST")
	public Object findUserByOrganId(String organId,ServletRequest request){
		if(StringUtil.checkNull(organId)) {
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.NULL_ATTRIBUTE);
		}
		Result result = new Result();
		result.setMessage(ResultConstant.SELECT_SUCCESS);
		result.setStatus(ResultConstant.SUCCESS_STATU);
		try {
			UserEntity userEntity = new UserEntity();
			userEntity.setOrganId(organId);
			List<UserEntity> find = userSearchService.find(userEntity);
			result.setData(find);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}


	/**
	 * 根据机构编码查询组织机构
	 * @return
	 */
	@Info("根据多个角色查询用户信息")
	@RequestMapping("/selectUserInfoByRoles")
	@ResponseBody
	public Object selectUserInfoByRoles(String roleNames,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param.put("roleNames", roleNames);
		Object result = cluster.getParentResult("selectUserInfoByRoles", param,analysis(request));
		if(null!=result) {
			return result;
		}

		if(StringUtil.checkNull(roleNames)) {
//			System.out.println("组织机构编码不能为空");
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.NULL_ATTRIBUTE);
		}
		try {
			String[] roles=roleNames.split(",");
			List<String> roleList= Arrays.asList(roles);
			List<UserEntity> list = this.commonservice.selectUserInfoByRoles(roleList);
			return new Result(ResultConstant.SUCCESS_STATU,
					ResultConstant.SELECT_SUCCESS,
					list);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
}
