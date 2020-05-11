package com.sinosoft.ap.system.organizationmanage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinosoft.ap.common.exception.NullAttributeException;
import com.sinosoft.ap.system.organizationmanage.domain.OrganizationEntity;
import com.sinosoft.ap.system.organizationmanage.domain.OrganizationRepository;
import com.sinosoft.ap.system.organizationmanage.domain.UserOrganizationTree;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;

@Service
public class OrganizationSearchServiceImpl implements OrganizationSearchService {

	@Autowired
	private OrganizationRepository organizationRepository;

	/**
	 * 根据给定参数，查找相关数据，支持多条件查询
	 * @param OrganizationEntity
	 * @return List<OrganizationEntity>
	 */
	@Override
	public List<OrganizationEntity> find(OrganizationEntity organizationEntity) throws Exception {
		return this.organizationRepository.select(organizationEntity);
	}

	@Override
	public List<UserOrganizationTree> findUserOrganTree(String organId) throws Exception {
		List<UserOrganizationTree> userTree = new ArrayList<>();
		List<UserOrganizationTree> organTree = new ArrayList<>();
		userTree = setType(this.organizationRepository.selectUserTree(organId),"user");
		organTree = setType(this.organizationRepository.selectOrganTree(organId),"organ");
		organTree.addAll(userTree);
		return organTree;
	}
	
    private List<UserOrganizationTree> setType(List<UserOrganizationTree> tree, String type) {
		if(tree==null||tree.size()<1) {
			return new ArrayList<>();
		}
		for(int i=0; i<tree.size(); i++) {
			tree.get(i).setType(type);
		}
		return tree;
	}

	@Override
	public OrganizationEntity findByUser(String userId) throws Exception {
		if(StringUtil.checkNull(userId)) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		return this.organizationRepository.selectByUserId(userId);
	}

}
