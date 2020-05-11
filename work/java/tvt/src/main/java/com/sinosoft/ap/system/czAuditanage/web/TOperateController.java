package com.sinosoft.ap.system.czAuditanage.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.sinosoft.ap.component.page.PageParam;
import com.sinosoft.ap.system.czAuditanage.domain.TOperateEntity;
import com.sinosoft.ap.system.czAuditanage.service.TOperateService;
import com.sinosoft.ap.system.usermanage.domain.UserEntity;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;

@RestController
@RequestMapping("/operate")
public class TOperateController {
	
	@Autowired
	public TOperateService ops;
	
	@RequestMapping("/saveOperate")
	public Result saveOperate(TOperateEntity operate){
		try{
//			List<TOperateEntity> count=this.ops.selectDistinct(operate);
//			if(count.size()<=0){
			TOperateEntity entity=this.ops.saveOperate(operate);
			return new Result(ResultConstant.SUCCESS_STATU,ResultConstant.INSERT_SUCCESS,entity);
//			}else{
//				return new Result(2, "用户名已存在");
//			}
		}catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}		
		
	}
	@RequestMapping("/removeOperate")
	public Result removeOperate(String id){
		try{
			Integer a=ops.removeOperate(id);
			return new Result(ResultConstant.SUCCESS_STATU,ResultConstant.DELETE_SUCCESS,a);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	@RequestMapping("/queryOperateList")
	public Result queryOperateList(TOperateEntity tOperateEntity,PageParam pageParam,ServletRequest request){
		Result result = new Result();
		result.setMessage(ResultConstant.SELECT_SUCCESS);
		result.setStatus(ResultConstant.SUCCESS_STATU);
		try {
			/**
			 * 判断是否需要分页
			 */
			if(null!=pageParam.getPageIndex()&&pageParam.getPageIndex()>0&&null!=pageParam.getPageSize()&&pageParam.getPageSize()>0){
				Page<TOperateEntity> operateEntities = this.ops.findOperateList(tOperateEntity, pageParam);
				pageParam.setTotleInfo(operateEntities.getTotal());
				pageParam.setTotlePage(operateEntities.getPages());
				result.setData(operateEntities);
				result.setSubdata(pageParam);
				return result;
			}
			List<TOperateEntity> entities = ops.findOperateList(tOperateEntity);
			result.setData(entities);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	@RequestMapping("/getOperateInfo")
	public Result getOperateInfo(String opId){
		Result result = new Result();
		result.setMessage(ResultConstant.SELECT_SUCCESS);
		result.setStatus(ResultConstant.SUCCESS_STATU);
		try {
			TOperateEntity tOperateEntity = new TOperateEntity();
			tOperateEntity.setOpId(opId);
			List<TOperateEntity> entities = ops.findOperateList(tOperateEntity);
			result.setData(entities.get(0));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
}
