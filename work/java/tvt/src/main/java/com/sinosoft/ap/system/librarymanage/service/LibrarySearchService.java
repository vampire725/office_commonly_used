package com.sinosoft.ap.system.librarymanage.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.sinosoft.ap.component.page.PageParam;
import com.sinosoft.ap.system.librarymanage.domain.LibraryEntity;

public interface LibrarySearchService {

	/**
	 * 根据给定参数，查找相关数据，支持多条件查询
	 * @param libraryEntity
	 * @return
	 * @throws Exception
	 */
	List<LibraryEntity> find( LibraryEntity libraryEntity) throws Exception ;

	/**
	 * 根据给定参数，查找相关数据，支持多条件查询,分页查询
	 * @param libraryEntity,PageParam
	 * @return Page<LibraryEntity>
	 */
	Page<LibraryEntity> find( LibraryEntity libraryEntity ,PageParam pageParam) throws Exception ;

	/**
	 * 根据给定libraryCode 查询其所有的子信息
	 */
	List<LibraryEntity> findChild( String librarySymbol ) throws Exception ;

}
