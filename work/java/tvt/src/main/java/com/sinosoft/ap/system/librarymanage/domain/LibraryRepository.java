package com.sinosoft.ap.system.librarymanage.domain;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sinosoft.ap.system.librarymanage.vo.LibraryEntityVO;

/***
 * @since 2017 年  04 月 07 日 01:09:54 
 */
@Repository
public interface LibraryRepository{

	 /**
     * 根据给定的参数新增一条数据
     * @param libraryEntity
     */
	void insert( LibraryEntity libraryEntity ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param librarySymbol
     */
	void delete( @Param(value = "librarySymbol") String librarySymbol) throws Exception ;
	
    /**
     * 指定主键，修改给定信息项
     * @param libraryEntity
     */
	void update( LibraryEntity libraryEntity ) throws Exception ;
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param libraryEntity
     * @return List<LibraryEntity>
     */
	List<LibraryEntity> select( LibraryEntity libraryEntity ) throws Exception ;
	/**
	 * 根据给定libraryCode 查询其所有的子信息
	 */
	List<LibraryEntity> selectChild( String libraryCode ) throws Exception ;
	
}