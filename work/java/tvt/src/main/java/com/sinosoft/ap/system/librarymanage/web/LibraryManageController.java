package com.sinosoft.ap.system.librarymanage.web;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import static com.sinosoft.ap.util.token.GetToken.analysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinosoft.ap.common.aop.annotation.Info;
import com.sinosoft.ap.system.librarymanage.domain.LibraryEntity;
import com.sinosoft.ap.system.librarymanage.service.LibraryManageService;
import com.sinosoft.ap.util.cluster.web.Cluster;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/ap-system")
@Api(value="数据字典管理",description="数据字典管理")
public class LibraryManageController {

	@Autowired
	private LibraryManageService libraryManageService;
	
	@Autowired
	private Cluster cluster;
	
	/**
	 * 根据传入ID删除指定数据字典
	 * @param libraryId
	 * @return
	 */
	@Info("根据传入ID删除指定数据字典")
	@RequestMapping("/removeLibrary")
	@ResponseBody
	@ApiOperation(value="根据传入ID删除指定数据字典",response=Result.class,httpMethod="POST")
	public Object removeLibrary(String libraryId,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param.put("libraryId", libraryId);
		Object result = cluster.getParentResult("removeLibrary", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			return libraryManageService.remove(libraryId);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	/**
	 * 新增数据字典
	 * @param libraryEntity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Info("新增数据字典")
	@RequestMapping("/saveLibrary")
	@ResponseBody
	@ApiOperation(value="新增数据字典",response=Result.class,httpMethod="POST")
	public Object saveLibrary(LibraryEntity libraryEntity,ServletRequest request){
//		Map<String, Object> param = new HashMap<>();
//		if(null!=libraryEntity) {
//			JSONObject jsonObject = new JSONObject();
//			jsonObject = JSONObject.fromObject(libraryEntity);
//			param = jsonObject;
//		}
//		Object result = cluster.getParentResult("saveLibrary", param,analysis(request));
//		if(null!=result) {
//			return result;
//		}
		try {
			return libraryManageService.save(libraryEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	/**
	 * 修改数据字典，其中 libraryId不能为空
	 * @param libraryEntity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Info("修改数据字典")
	@RequestMapping("/modifyLibrary")
	@ResponseBody
	@ApiOperation(value="修改数据字典",response=Result.class,httpMethod="POST")
	public Object modifyLibrary(LibraryEntity libraryEntity,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		if(null!=libraryEntity) {
			JSONObject jsonObject = new JSONObject();
			jsonObject = JSONObject.fromObject(libraryEntity);
			param = jsonObject;			
		}
		Object result = cluster.getParentResult("modifyLibrary", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			return libraryManageService.modify(libraryEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	@InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        CustomDateEditor dateEditor = new CustomDateEditor(simpleDateFormat, true);
        binder.registerCustomEditor(Date.class,dateEditor);
    }
	
}