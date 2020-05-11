package com.sinosoft.ap.system.permissionrolerel.web;

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
import com.sinosoft.ap.system.permissionrolerel.domain.PermissionOperationRelEntity;
import com.sinosoft.ap.system.permissionrolerel.service.PermissionOperationRelService;
import com.sinosoft.ap.util.cluster.web.Cluster;
import com.sinosoft.ap.util.list.ListUtil;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/ap-system")
@Api(value="操作许可关联管理",description="操作许可关联管理")
public class PermissionOperationRelManageController {

	@Autowired
	private PermissionOperationRelService permissionOperationRelService;
	
	@Autowired
	private Cluster cluster;
	
	@Value("${ap.cluster.token.key}") 
	private String tokenKey;
	/**
	 * 新增许可操作关联关系
	 * permissionId与operationId不能为空，
	 * 当需要为许可绑定多个操作时，用 “,”将多个操作Id拼接成一个字符串传入即可
	 * 在新增当前绑定关系之前，会将之前所有绑定关系清空
	 * @param permissionOperationRelEntity
	 * @return
	 */
	@Info("新增许可操作关联关系")
	@SuppressWarnings("unchecked")
	@RequestMapping("/savePermissionOperationRel")
	@ResponseBody
	@ApiOperation(value="新增许可操作关联关系",response=Result.class,httpMethod="POST")
	public Object savePermissionOperationRel(PermissionOperationRelEntity permissionOperationRelEntity,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param = JSONObject.fromObject(permissionOperationRelEntity);
		Object result = cluster.getParentResult("savePermissionOperationRel", param,analysis(request));
		if(null!=result) {
			return result;
		}
		String permissionId = permissionOperationRelEntity.getPermissionId();
		String operationId = permissionOperationRelEntity.getOperetionId();
		if(null==permissionId||permissionId.isEmpty()){
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.INSERT_FAIL_ERROE_ATTRIBUTE);
		}
		try {
			PermissionOperationRelEntity permissionOperationRelEntity2 = new PermissionOperationRelEntity();
			permissionOperationRelEntity2.setPermissionId(permissionId);
			this.permissionOperationRelService.remove(permissionOperationRelEntity2);
			List<PermissionOperationRelEntity> list  =  new ArrayList<>();
			if(!StringUtil.checkNull(operationId)){
				String[] operationIds = operationId.split(",");
				for(String id:operationIds){
					if(null==id||null==id){
						return new Result(ResultConstant.FAIL_STATU, ResultConstant.INSERT_FAIL_ERROE_ATTRIBUTE);
					}
					PermissionOperationRelEntity temp = new PermissionOperationRelEntity();
					temp.setPermissionId(permissionId);
					temp.setOperetionId(id.trim());
					list.add(temp);
				}
				if(!ListUtil.checkNull(list)){
					permissionOperationRelService.saveList(list);
				} 
			}
		
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.INSERT_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
}