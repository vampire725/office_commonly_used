package com.sinosoft.ap.system.librarymanage.web;

import static com.sinosoft.ap.util.token.GetToken.analysis;

import com.sinosoft.ap.common.exception.NullAttributeException;
import com.sinosoft.ap.util.string.StringUtil;
import io.swagger.annotations.Api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinosoft.ap.common.aop.annotation.Info;
import com.sinosoft.ap.system.librarymanage.service.LibrarySearchService;
import com.sinosoft.ap.system.librarymanage.vo.LibraryEntityVO;
import com.sinosoft.ap.util.cluster.web.Cluster;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.system.librarymanage.domain.LibraryEntity;
import com.sinosoft.ap.util.result.ResultConstant;

@RestController
@RequestMapping("/ap-system")
@Api(value="数据字典查询",tags={"数据字典查询"},position=1)
public class LibrarySearchController {
	
	
	@Autowired
	private Cluster cluster;
	
	@Autowired
	private LibrarySearchService librarySearchService;

//	/**
//	 * 根据父资源的libraryCode属性查询其所有子信息
//	 * @param libraryCode
//	 * @return
//	 */
//	@Info("根据父资源的libraryCode属性查询其所有子信息")
//	@RequestMapping("/findChildLibrary")
//	@ResponseBody
//	public Object findChildLibrary(String libraryCode,ServletRequest request){
//		Map<String, Object> param = new HashMap<>();
//		if(null!=libraryCode) {
//			param.put("libraryCode", libraryCode);
//		}
//		Object result = cluster.getParentResult("findChildLibrary", param,analysis(request));
//		if(null!=result) {
//			return result;
//		}
//		try {
//			return new Result(ResultConstant.SUCCESS_STATU,
//					ResultConstant.SELECT_SUCCESS,
//					librarySearchService.findChild(libraryCode));
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
//		}
//	}

	/**
	 * 根据父资源的libraryCode属性查询其所有子信息
	 * @param librarySymbol
	 * @return
	 */
	@Info("根据父资源的libraryCode属性查询其所有子信息")
	@RequestMapping("/findChildLibrary")
	@ResponseBody
	@ApiOperation(value="根据父资源的libraryCode属性查询其所有子信息",response=Result.class,httpMethod="POST")
	public Object findChildLibrary(String librarySymbol,String enableSelf,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		if(null!=librarySymbol) {
			param.put("librarySymbol", librarySymbol);
		}
		if(null!=enableSelf){
			param.put("enableSelf", enableSelf);
		}
		Object result = cluster.getParentResult("findChildLibrary", param,analysis(request));
		if(null!=result) {
			return result;
		}
		if(StringUtil.checkNull(librarySymbol)) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}else{
			if(StringUtil.checkNull(enableSelf)){
				librarySymbol = librarySymbol + "_";
			}
		}
		try {
			return new Result(ResultConstant.SUCCESS_STATU,
					ResultConstant.SELECT_SUCCESS,
					librarySearchService.findChild(librarySymbol));
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}

	/**
	 * 查询所有数据字典
	 * @param entity
	 * 必须同时传入 pageIndex和pageSize才能启动分页功能
	 * @return
	 */
	@Info("查询所有数据字典")
	@RequestMapping("/findLibrary")
	@ResponseBody
	public Object findLibrary(LibraryEntity entity,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		if(null!=entity) {
			JSONObject jsonObject = new JSONObject();
			jsonObject = JSONObject.fromObject(entity);
			param = jsonObject;			
		}
		Object result = cluster.getParentResult("findLibrary", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			return new Result(ResultConstant.SUCCESS_STATU, 
					ResultConstant.SELECT_SUCCESS, 
					librarySearchService.find(entity));
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	
//	@InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
//    }
}
