package com.sinosoft.ap.system.usermanage.web;

import static com.sinosoft.ap.util.token.GetToken.analysis;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.sinosoft.ap.common.aop.annotation.Info;
import com.sinosoft.ap.common.exception.NullAttributeException;
import com.sinosoft.ap.component.page.PageParam;
import com.sinosoft.ap.system.usermanage.domain.UserEntity;
import com.sinosoft.ap.system.usermanage.domain.UserRepository;
import com.sinosoft.ap.system.usermanage.service.UserSearchService;
import com.sinosoft.ap.util.cluster.web.Cluster;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/ap-system")
@Api(value="用户查询",description="用户查询")
public class UserSearchController {

	@Autowired
	private UserSearchService userSearchService;
	
	@Autowired
	private Cluster cluster;
	
	@Value("${ap.cluster.token.key}") 
	private String tokenKey;
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * 查询用户，并且判断是否需要根据用户名模糊查询，是够需要分页查询
	 * @param userEntity
	 * @param pageParam
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Info("查询用户，并且判断是否需要根据用户名模糊查询，是够需要分页查询")
	@RequestMapping("/findUser")
	@ResponseBody
	@ApiOperation(value="查询用户，并且判断是否需要根据用户名模糊查询，是够需要分页查询",response=Result.class,httpMethod="POST")
	public Object findUser(UserEntity userEntity,PageParam pageParam,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param = JSONObject.fromObject(userEntity);
		param.putAll(JSONObject.fromObject(pageParam));
		Object results = cluster.getParentResult("findUser", param,analysis(request));
		if(null!=results) {
			return results;
		}
		Result result = new Result();
		result.setMessage(ResultConstant.SELECT_SUCCESS);
		result.setStatus(ResultConstant.SUCCESS_STATU);
		try {
			userEntity = dealUserEntity(userEntity);
			/**
			 * 判断是否需要分页
			 */
			if(null!=pageParam.getPageIndex()&&pageParam.getPageIndex()>0&&null!=pageParam.getPageSize()&&pageParam.getPageSize()>0){
				Page<UserEntity> userEntities = new Page<>();
				if(null==userEntity.getOrganId()||userEntity.getOrganId().isEmpty()){
					userEntity.setOrganId(null);
				}
				userEntities = this.userSearchService.find(userEntity, pageParam);
				List<UserEntity> entities = new ArrayList<>();
				entities = userEntities.getResult();
				pageParam.setTotleInfo(userEntities.getTotal());
				pageParam.setTotlePage(userEntities.getPages());
				result.setData(entities);
				result.setSubdata(pageParam);
				return result;
			}
			List<UserEntity> entities = new ArrayList<>();
			entities = userSearchService.find(userEntity);
			result.setData(entities);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}

	/**
	 * 根据userId查询用户详情
	 * userId不能为空
	 * @param userId
	 * @return
	 */
	@Info("根据userId查询用户详情")
	@RequestMapping("/findUserInfo")
	@ResponseBody
	@ApiOperation(value="根据userId查询用户详情",response=Result.class,httpMethod="POST")
	public Object findUserInfo(String userId,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		Object result = cluster.getParentResult("findUserInfo", param,analysis(request));
		if(null!=result) {
			return result;
		}
		if(StringUtil.checkNull(userId)){
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		UserEntity userEntity = new UserEntity();
		userEntity.setUserId(userId);
		try {
			List<UserEntity> userEntities = new ArrayList<>();
			userEntities= userSearchService.find(userEntity);
			if(userEntities.size()<1){
				return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.SELECT_SUCCESS,new ArrayList<>());
			}
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.SELECT_SUCCESS, userEntities.get(0));
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	private UserEntity dealUserEntity( UserEntity userEntity ) throws Exception {
		String account = userEntity.getUserAccount();
		if(null!=account&&account.endsWith(":f")){
			if (account.contains("_")||account.contains("%")||account.contains("<")||account.contains(">")) {
				throw new NullAttributeException(ResultConstant.SELECT_FAIL_ERROE_ATTRIBUTE);
			}
			userEntity.setUserAccount("LIKE '%"+account.substring(0, account.length()-2)+"%'");
		}else if(null!=account&&account!="") {
			userEntity.setUserAccount("= '"+account+"'");
		}else {
			userEntity.setUserAccount(null);
		}
		return userEntity;
	}

	
	/**
	 * 根据组织机构ID查询用户
	 * @param userEntity
	 * @param pageParam
	 * @return
	 */
	@Info("根据组织机构ID查询用户")
	@RequestMapping("/findUserByOrganId")
	@ResponseBody
	@ApiOperation(value="根据组织机构ID查询用户",response=Result.class,httpMethod="POST")
	public Object findUserByOrganId(String organId,ServletRequest request){
		if(StringUtil.checkNull(organId)) {
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.NULL_ATTRIBUTE);
		}
		String[] organIds = organId.split(",");
		Map<String, Object> param = new HashMap<>();
		param.put("organId", organId);
		Object results = cluster.getParentResult("findUserByOrganId", param,analysis(request));
		if(null!=results) {
			return results;
		}
		Result result = new Result();
		result.setMessage(ResultConstant.SELECT_SUCCESS);
		result.setStatus(ResultConstant.SUCCESS_STATU);
		try {
			Map<String, String> map = new HashMap<>();
			Stream.of(organIds).forEach(n -> {
				UserEntity userEntity = new UserEntity();
				userEntity.setOrganId(n);
				List<UserEntity> entities = new ArrayList<>();
				try {
					entities = userSearchService.find(userEntity);
					map.put(n, JSONArray.fromObject(entities).toString());
				} catch (Exception e) {
					map.put(n, "");
				}
			});
			result.setData(map);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	/**
	 * 根据组织机构编码以及许可名称查询用户
	 * @param userEntity
	 * @param pageParam
	 * @return
	 */
	@Info("根据组织机构编码以及许可名称查询用户")
	@RequestMapping("/findUserByOrganCodeAndPermissionName")
	@ResponseBody
	@ApiOperation(value="根据组织机构编码以及许可名称查询用户",response=Result.class,httpMethod="POST")
	public Object findUserByOrganCodeAndPermissionName(String organCode,String permissionName,ServletRequest request){
		if(StringUtil.checkNull(organCode)&&StringUtil.checkNull(permissionName)) {
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.NULL_ATTRIBUTE);
		}
		Result result = new Result();
		result.setMessage(ResultConstant.SELECT_SUCCESS);
		result.setStatus(ResultConstant.SUCCESS_STATU);
		try {
			result.setData(userRepository.findUserByOrganCodeAndPermissionName(organCode, permissionName));
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(ResultConstant.SELECT_FAIL_ERROE_ATTRIBUTE);
			result.setStatus(ResultConstant.FAIL_STATU);
		}
		return result;
	}
	/**
	 * 根据组织机构编码以及许可名称查询用户
	 * @param userEntity
	 * @param pageParam
	 * @return
	 */
	@Info("根据组织机构编码以及多许可名称查询用户")
	@RequestMapping("/findUserLikeOrganCodeAndPermissionName")
	@ResponseBody
	@ApiOperation(value="根据组织机构编码以及多许可名称查询用户",response=Result.class,httpMethod="POST")
	public Object findUserLikeOrganCodeAndPermissionName(String organCode,String permissionName,ServletRequest request){
		if(StringUtil.checkNull(organCode)&&StringUtil.checkNull(permissionName)) {
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.NULL_ATTRIBUTE);
		}
		Result result = new Result();
		result.setMessage(ResultConstant.SELECT_SUCCESS);
		result.setStatus(ResultConstant.SUCCESS_STATU);
		try {
			result.setData(userRepository.findUserLikeOrganCodeAndPermissionName(organCode, permissionName));
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(ResultConstant.SELECT_FAIL_ERROE_ATTRIBUTE);
			result.setStatus(ResultConstant.FAIL_STATU);
		}
		return result;
	}
}
