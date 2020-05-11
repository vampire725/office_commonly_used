package com.sinosoft.ap.system.log.service;

import static com.sinosoft.ap.util.token.GetToken.analysis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinosoft.ap.system.log.domain.ApsUserVisiteSystemLog;
import com.sinosoft.ap.system.log.domain.ApsUserVisiteSystemLogMapper;
import com.sinosoft.ap.system.organizationmanage.domain.OrganizationEntity;
import com.sinosoft.ap.system.organizationmanage.service.OrganizationSearchService;
import com.sinosoft.ap.system.resourcemanage.domain.ResourceEntity;
import com.sinosoft.ap.system.resourcemanage.service.ResourceService;
import com.sinosoft.ap.system.usermanage.domain.UserEntity;
import com.sinosoft.ap.system.usermanage.service.UserSearchService;
import com.sinosoft.ap.util.id.PrimaryKeyUtil;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;
import com.sinosoft.ap.util.token.JavaWebToken;

@Service
public class ApsUserVisiteSystemLogServiceImpl implements ApsUserVisiteSystemLogService{

	@Autowired
	private  ApsUserVisiteSystemLogMapper apsUserVisiteSystemLogMapper;
	@Autowired
	private OrganizationSearchService organService ;
	@Autowired
	private ResourceService resourceService ;
	@Autowired
	private JavaWebToken javaWebToken;
	
	@Autowired
	private UserSearchService userSearchService;
	@Override
	public int insert(ApsUserVisiteSystemLog info) throws Exception {
		if(StringUtil.checkNull(info.getLogId())) {
			info.setLogId(PrimaryKeyUtil.create());
		}
		info.setVisiteTime(new Date());
		return apsUserVisiteSystemLogMapper.insertSelective(info);
	}

	@Override
	public int deleteByKey(ApsUserVisiteSystemLog info) throws Exception {
		return apsUserVisiteSystemLogMapper.deleteByPrimaryKey(info.getLogId());
	}

	@Override
	public int updateByKey(ApsUserVisiteSystemLog info) throws Exception {
		return apsUserVisiteSystemLogMapper.updateByPrimaryKeySelective(info);
	}

	@Override
	public void saveUserLogSysteInfos(String  userId,String SystemCode) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					ApsUserVisiteSystemLog log = new ApsUserVisiteSystemLog();
					
					UserEntity u = new UserEntity();
					u.setUserId(userId);
					ApsUserVisiteSystemLog userId2 = apsUserVisiteSystemLogMapper.selectUserInfosByUserId(userId);
					
					ResourceEntity rs = new ResourceEntity();
					rs.setResourceId(SystemCode);
					List<ResourceEntity> find = resourceService.find(rs);
					
					
					log.setLogId(PrimaryKeyUtil.create());
					log.setOrganCode(userId2.getOrganCode());
					log.setOrganName(userId2==null?"":userId2.getOrganName());
					log.setParentOrganId(userId2==null?"":userId2.getParentOrganId());
					log.setSystemCode(SystemCode);
					log.setSystemName(find==null?"":find.get(0).getResourceName());
					log.setSystemType(find==null?0:Integer.parseInt(find.get(0).getResourceType()));
					log.setUserId(userId);
					log.setUserName(userId2==null?"":userId2.getUserName());
					log.setVisiteTime(new Date());
					insert(log);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		t.start();
	}

}
