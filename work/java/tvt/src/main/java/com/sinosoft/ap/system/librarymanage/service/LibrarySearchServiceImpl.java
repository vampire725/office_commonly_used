package com.sinosoft.ap.system.librarymanage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sinosoft.ap.common.exception.NullAttributeException;
import com.sinosoft.ap.component.page.PageParam;
import com.sinosoft.ap.system.librarymanage.domain.LibraryEntity;
import com.sinosoft.ap.system.librarymanage.domain.LibraryRepository;
import com.sinosoft.ap.system.librarymanage.vo.LibraryEntityVO;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;

@Service
public class LibrarySearchServiceImpl implements LibrarySearchService{

	@Autowired
	private LibraryRepository libraryRepository;
	/**
	 * 根据给定参数，查找相关数据，支持多条件查询
	 * @param libraryEntity
	 * @return List<LibraryEntity>
	 */
	@Override
	public List<LibraryEntity> find(LibraryEntity libraryEntity) throws Exception {
		return this.libraryRepository.select(libraryEntity);
	}

	/**
	 * 根据给定参数，查找相关数据，支持多条件查询
	 * @param libraryEntity，PageParam
	 * @return Page<LibraryEntity>
	 */
	@Override
	public Page<LibraryEntity> find(LibraryEntity libraryEntity ,PageParam pageParam) throws Exception {
		PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize(), true);
		Page<LibraryEntity> page = (Page<LibraryEntity>) this.libraryRepository.select(libraryEntity);
		return page;
	}

	@Override
	public List<LibraryEntity> findChild(String librarySymbol) throws Exception {
		if(StringUtil.checkNull(librarySymbol)) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		return this.libraryRepository.selectChild(librarySymbol);
	}

}
