package com.sinosoft.ap.system.czAuditanage.service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sinosoft.ap.common.exception.NullAttributeException;
import com.sinosoft.ap.component.page.PageParam;
import com.sinosoft.ap.system.czAuditanage.domain.TOperateEntity;
import com.sinosoft.ap.system.czAuditanage.domain.TOperateRepository;
import com.sinosoft.ap.util.id.PrimaryKeyUtil;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;


@Service
public class TOperateServiceImpl implements TOperateService{
	
	@Autowired	
	private TOperateRepository toperateRepository;

	@Override
	public TOperateEntity saveOperate(TOperateEntity operate) throws Exception {
		if(StringUtil.checkNull(operate.getOpId())) {
			operate.setOpId(PrimaryKeyUtil.create());
		}
		operate.setOpName(operate.getOpName().replace(" ", ""));
		operate.setOpTime(new Date());
//		operate.setOpResult(1);
		toperateRepository.insertOperate(operate);
		return operate;
	}

	@Override
	public Integer removeOperate(String id) throws Exception {
		if(StringUtil.checkNull(id)) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		List<String> ids=Arrays.asList(id.split(","));
		return toperateRepository.deleteById(ids);
	}

	@Override
	public List<TOperateEntity> selectDistinct(TOperateEntity operate) throws Exception {
	 return toperateRepository.selectDistincts(operate);
	}

	@Override
	public Page<TOperateEntity> findOperateList(TOperateEntity operate,
			PageParam param) throws Exception {
		SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTimeStr = operate.getStartTimeStr();
		String endTimeStr = operate.getEndTimeStr();
		if(!StringUtil.checkNull(startTimeStr)){
			operate.setStartTime(datetimeFormat.parse(startTimeStr));
		}
		if(!StringUtil.checkNull(endTimeStr)){
			operate.setEndTime(datetimeFormat.parse(endTimeStr));
		}
		PageHelper.startPage(param.getPageIndex(), param.getPageSize(), true);
		return (Page<TOperateEntity>) toperateRepository.selectOperateList(operate);
	}

	@Override
	public List<TOperateEntity> findOperateList(TOperateEntity operate)
			throws Exception {
		SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTimeStr = operate.getStartTimeStr();
		String endTimeStr = operate.getEndTimeStr();
		if(!StringUtil.checkNull(startTimeStr)){
			operate.setStartTime(datetimeFormat.parse(startTimeStr));
		}
		if(!StringUtil.checkNull(endTimeStr)){
			operate.setEndTime(datetimeFormat.parse(endTimeStr));
		}
		return toperateRepository.selectOperateList(operate);
	}

}
