package com.sinosoft.ap.system.organizationmanage.web;

import static com.sinosoft.ap.util.token.GetToken.analysis;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinosoft.ap.common.aop.annotation.Info;
import com.sinosoft.ap.system.organizationmanage.domain.OrganizationEntity;
import com.sinosoft.ap.system.organizationmanage.service.OrganizationManageService;
import com.sinosoft.ap.util.cluster.web.Cluster;
import com.sinosoft.ap.util.id.PrimaryKeyUtil;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/ap-system")
@Api(value="机构管理",description="机构管理")
public class OrganizationManageController {

	@Autowired
	private OrganizationManageService organizationManageService;
	
	@Autowired
	private Cluster cluster;
	
	@Value("${ap.cluster.token.key}") 
	private String tokenKey;
	
	/**
	 * 移除组织机构
	 * @param organId
	 * @return
	 */
	@Info("移除组织机构")
	@RequestMapping("/removeOrganization")
	@ResponseBody
	@ApiOperation(value="移除组织机构",response=Result.class,httpMethod="POST")
	public Object removeByAttribute(String organId,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param.put("organId", organId);
		Object result = cluster.getParentResult("removeOrganization", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			this.organizationManageService.remove(organId);
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.DELETE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	/**
	 * 新增组织机构
	 * @param organizationEntity
	 * @return
	 */
	@Info("新增组织机构")
	@SuppressWarnings("unchecked")
	@RequestMapping("/saveOrganization")
	@ResponseBody
	@ApiOperation(value="新增组织机构",response=Result.class,httpMethod="POST")
	public Object saveOrganization(OrganizationEntity organizationEntity,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param = JSONObject.fromObject(organizationEntity);
		Object result = cluster.getParentResult("saveOrganization", param,analysis(request));
		if(null!=result) {
			return result;
		}
		String id = PrimaryKeyUtil.create();
	    organizationEntity.setOrganId(id);
	    organizationEntity.setOrganCreatetime(new Date());
		try {
			this.organizationManageService.save(organizationEntity);
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.INSERT_SUCCESS,organizationEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	/**
	 * 修改组织机构
	 * 其中organId不能为空，同时除去organId其他字段至少有一条是才能修改成功
	 * @param organizationEntity
	 * @return
	 */
	@Info("修改组织机构")
	@SuppressWarnings("unchecked")
	@RequestMapping("/modifyOrganization")
	@ResponseBody
	@ApiOperation(value="修改组织机构",response=Result.class,httpMethod="POST")
	public Object modifyByPrimaryColumn(OrganizationEntity organizationEntity,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param = JSONObject.fromObject(organizationEntity);
		Object result = cluster.getParentResult("modifyOrganization", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			this.organizationManageService.modify(organizationEntity);
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.UPDATA_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
}