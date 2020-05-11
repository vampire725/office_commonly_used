package com.sinosoft.ap.system.librarymanage.service;

import com.sinosoft.ap.system.librarymanage.domain.LibraryEntity;
import com.sinosoft.ap.util.result.Result;

/***
 * @since 2017 年  04 月 07 日 01:09:54 
 */
public interface LibraryManageService {

	 /**
     * 根据给定的参数新增一条数据
     * @param libraryEntity
     */
	 Result save( LibraryEntity libraryEntity) throws Exception ;
	
    /**
     * 根据给定id删除数据
     * @param libraryId
     */
	Result remove( String libraryId ) throws Exception ;
	
    /**
     * 指定主键，修改给定信息项
     * @param libraryEntity
     */
	Result modify(LibraryEntity libraryEntity ) throws Exception ;
	
}