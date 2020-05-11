package com.sinosoft.ap.system.organizationmanage.web;

import static com.sinosoft.ap.util.token.GetToken.analysis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinosoft.ap.common.aop.annotation.Info;
import com.sinosoft.ap.system.organizationmanage.domain.OrganizationEntity;
import com.sinosoft.ap.system.organizationmanage.domain.OrganizationRepository;
import com.sinosoft.ap.system.organizationmanage.service.OrganizationSearchService;
import com.sinosoft.ap.system.organizationmanage.vo.OrganizationEntityVO;
import com.sinosoft.ap.util.cluster.web.Cluster;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;

@RestController
@RequestMapping("/ap-system")
@Api(value="机构查询",description="机构查询")
public class OrganizationSearchController {
	
	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Autowired
	private OrganizationSearchService organizationSearchService;
	
	@Autowired
	private Cluster cluster;
	
	@Value("${ap.cluster.token.key}") 
	private String tokenKey;
	/**
	 * 查询所有组织机构
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Info("查询所有组织机构")
	@RequestMapping("/findOrganization")
	@ResponseBody
	@ApiOperation(value="查询所有组织机构",response=Result.class,httpMethod="POST")
	public Object findOrganization(OrganizationEntityVO entity,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param = JSONObject.fromObject(entity);
		Object result = cluster.getParentResult("findOrganization", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			return new Result(ResultConstant.SUCCESS_STATU, 
					ResultConstant.SELECT_SUCCESS, 
					this.organizationSearchService.find(null));
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}

	/**
	 * 查询组织机构与用户树
	 * @return
	 */
	@Info("查询组织机构与用户树")
	@RequestMapping("/findUserOrganizationTree")
	@ResponseBody
	@ApiOperation(value="查询组织机构与用户树",response=Result.class,httpMethod="POST")
	public Object findUserOrganizationTree(ServletRequest request,String organId){
		Map<String, Object> param = new HashMap<>();
		param.put("organId", organId);
		Object result = cluster.getParentResult("findUserOrganizationTree", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			return new Result(ResultConstant.SUCCESS_STATU, 
					ResultConstant.SELECT_SUCCESS, 
					this.organizationSearchService.findUserOrganTree(organId));
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	/**
	 * 查询所有组织机构
	 * @param entity
	 * @return
	 */
	@Info("查询所有组织机构")
	@RequestMapping("/getOrganizationInfo")
	@ResponseBody
	@ApiOperation(value="查询所有组织机构",response=Result.class,httpMethod="POST")
	public Object getOrganizationInfo(String organParentId,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param.put("organParentId", organParentId);
		Object result = cluster.getParentResult("getOrganizationInfo", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			OrganizationEntity organizationEntity = new OrganizationEntity();
			organizationEntity.setOrganId(organParentId);
			return new Result(ResultConstant.SUCCESS_STATU, 
					ResultConstant.SELECT_SUCCESS, 
					this.organizationSearchService.find(organizationEntity));
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	/**
	 * 查询组织机构详情
	 * @param entity
	 * @return
	 */
	@Info("查询组织机构详情")
	@RequestMapping("/findOrganizationInfo")
	@ResponseBody
	@ApiOperation(value="查询所有组织机构",response=Result.class,httpMethod="POST")
	public Object findOrganizationInfo(String organId,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param.put("organId", organId);
		Object result = cluster.getParentResult("findOrganizationInfo", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			OrganizationEntity organizationEntity = new OrganizationEntity();
			organizationEntity.setOrganId(organId);
			return new Result(ResultConstant.SUCCESS_STATU, 
					ResultConstant.SELECT_SUCCESS, 
					this.organizationSearchService.find(organizationEntity));
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}	
	/**
	 * 查询组织机构与用户树
	 * @return
	 */
	@Info("查询组织机构与用户树")
	@RequestMapping("/selectOrganListByUserId")
	@ResponseBody
	@ApiOperation(value="查询组织机构与用户树",response=Result.class,httpMethod="POST")
	public Object selectOrganListByUserId(String userId ,ServletRequest request){
		if(StringUtil.checkNull(userId)) {
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.NULL_ATTRIBUTE);
		}
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		Object result = cluster.getParentResult("selectOrganListByUserId", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			return new Result(ResultConstant.SUCCESS_STATU, 
					ResultConstant.SELECT_SUCCESS, 
					this.organizationRepository.selectOrganListByUserId(userId));
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	/**
	 * 根据机构编码查询组织机构
	 * @return
	 */
	@Info("根据机构编码查询组织机构")
	@RequestMapping("/selectByCodeLike")
	@ResponseBody
	@ApiOperation(value="根据机构编码查询组织机构",response=Result.class,httpMethod="POST")
	public Object selectByCodeLike(String code ){
		if(StringUtil.checkNull(code)) {
//			System.out.println("组织机构编码不能为空"
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.NULL_ATTRIBUTE);
		}
		try {
			return new Result(ResultConstant.SUCCESS_STATU, 
					ResultConstant.SELECT_SUCCESS, 
					this.organizationRepository.selectByCodeLike(code));
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}

}
