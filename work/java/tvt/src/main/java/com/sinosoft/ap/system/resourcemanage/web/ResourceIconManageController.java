package com.sinosoft.ap.system.resourcemanage.web;

import static com.sinosoft.ap.util.token.GetToken.analysis;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinosoft.ap.common.aop.annotation.Info;
import com.sinosoft.ap.system.resourcemanage.domain.ResourceIconEntity;
import com.sinosoft.ap.system.resourcemanage.domain.ResourceIconRelEntity;
import com.sinosoft.ap.system.resourcemanage.service.ResourceIconRelService;
import com.sinosoft.ap.system.resourcemanage.service.ResourceIconService;
import com.sinosoft.ap.util.cluster.web.Cluster;
import com.sinosoft.ap.util.entity.EntityUtil;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/ap-system")
@Api(value="资源图标管理",description="资源图标管理")
public class ResourceIconManageController {

	@Autowired
	private ResourceIconService resourceIconService;
	
	@Autowired
	private ResourceIconRelService resourceIconRelService;
	
	@Autowired
	private Cluster cluster;
	
	@Value("${ap.cluster.token.key}") 
	private String tokenKey;
	/**
	 * 删除资源图标
	 * @param resourceIconEntity
	 * @return
	 */
	@Info("删除资源图标")
	@SuppressWarnings("unchecked")
	@RequestMapping("/removeResourceIcon")
	@ResponseBody
	@ApiOperation(value="删除资源图标",response=Result.class,httpMethod="POST")
	public Object removeByAttribute(ResourceIconEntity resourceIconEntity,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param = JSONObject.fromObject(resourceIconEntity);
		Object result = cluster.getParentResult("removeResourceIcon", param,analysis(request));
		if(null!=result) {
			return result;
		}
		if(null==resourceIconEntity.getResourceIconId()||resourceIconEntity.getResourceIconId().isEmpty()){
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.NULL_ATTRIBUTE);
		}
		ResourceIconRelEntity resourceIconRelEntity = new ResourceIconRelEntity();
		resourceIconRelEntity.setResourceIconId(resourceIconEntity.getResourceIconId());
		try {
			resourceIconService.remove(resourceIconEntity);
			resourceIconRelService.remove(resourceIconRelEntity);
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.DELETE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	/**
	 * 修改资源图标
	 * @param resourceIconEntity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/modifyResourceIcon")
	@ResponseBody
	@ApiOperation(value="修改资源图标",response=Result.class,httpMethod="POST")
	public Object modifyByPrimaryColumn(ResourceIconEntity resourceIconEntity,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param = JSONObject.fromObject(resourceIconEntity);
		Object result = cluster.getParentResult("modifyResourceIcon", param,analysis(request));
		if(null!=result) {
			return result;
		}
		if(null==resourceIconEntity.getResourceIconId()||resourceIconEntity.getResourceIconId().isEmpty()){
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPDATA_FAIL_ERROE_ATTRIBUTE);
		}
		try {
			if(!EntityUtil.isEmpty(resourceIconEntity)){
				return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPDATA_FAIL_ERROE_ATTRIBUTE);
			}
			resourceIconService.modify(resourceIconEntity);
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.UPDATA_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
}