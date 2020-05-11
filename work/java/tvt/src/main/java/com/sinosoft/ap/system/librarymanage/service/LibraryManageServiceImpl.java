package com.sinosoft.ap.system.librarymanage.service;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import com.sinosoft.ap.util.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.sinosoft.ap.common.exception.NullAttributeException;
import com.sinosoft.ap.system.librarymanage.domain.LibraryEntity;
import com.sinosoft.ap.system.librarymanage.domain.LibraryRepository;
import com.sinosoft.ap.util.id.PrimaryKeyUtil;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.string.StringUtil;

/***
 * @since 2017 年  04 月 07 日 01:09:54 
 */
@Service
public class LibraryManageServiceImpl implements LibraryManageService{

	@Autowired
	private LibraryRepository libraryRepository;

	 /**
     * 根据给定的参数新增一条数据
     * @param libraryEntity
     */
	@Override
	public Result save(LibraryEntity libraryEntity) throws Exception {
		if(!StringUtil.checkNull(libraryEntity.getLibraryCode())){
			LibraryEntity code = new LibraryEntity();
			code.setLibraryCode(libraryEntity.getLibraryCode());
			code.setLibraryParentId(libraryEntity.getLibraryParentId());
			List<LibraryEntity> list = libraryRepository.select(code);
			if(list.size()>0){
				return new Result(ResultConstant.FAIL_STATU, "信息项编号重复");
			}
		}else{
			return new Result(ResultConstant.FAIL_STATU,ResultConstant.NULL_ATTRIBUTE);
		}
		if(null==libraryEntity.getLibrarySymbol() || StringUtil.checkNull(libraryEntity.getLibraryLevel())){
			return new Result(ResultConstant.FAIL_STATU,ResultConstant.NULL_ATTRIBUTE);
		}
		String symbol = libraryEntity.getLibrarySymbol();
		if(!symbol.equals("") && symbol.split("_").length != Integer.valueOf(libraryEntity.getLibraryLevel())){
			return new Result(ResultConstant.FAIL_STATU,"字典级别和字典识别码不匹配");
		}else if(symbol.equals("") && Integer.valueOf(libraryEntity.getLibraryLevel()) != 0){
			return new Result(ResultConstant.FAIL_STATU,"字典级别和字典识别码不匹配");
		}
        String libraryCode = libraryEntity.getLibraryCode();
        String symbolBase64Str = StringUtil.StrToBase64(libraryCode);
        if(symbol.equals("")){
            symbol = symbolBase64Str;
        }else{
            symbol = symbol + "_" + symbolBase64Str;
        }
//		String symbol_save = StringUtil.randomStrFromStr(symbol,3);
//		while (true){
//			LibraryEntity symbolCheck = new LibraryEntity();
//			symbolCheck.setLibrarySymbol(symbol_save);
//			List<LibraryEntity> list = libraryRepository.select(symbolCheck);
//			if(list.size() == 0){
//				break;
//			}
//			symbol_save = StringUtil.randomStrFromStr(symbol,3);
//		}
		libraryEntity.setLibrarySymbol(symbol);
		libraryEntity.setLibraryId(PrimaryKeyUtil.create());
		libraryEntity.setLibraryCreatetime(new Date());
		this.libraryRepository.insert(libraryEntity);
		return new Result(ResultConstant.SUCCESS_STATU,
				ResultConstant.INSERT_SUCCESS,
				libraryEntity);
	}
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param libraryId
     */
	@Override
	public Result remove( String libraryId ) throws Exception {
		if(StringUtil.checkNull(libraryId)) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		LibraryEntity libraryEntity = new LibraryEntity();
		libraryEntity.setLibraryId(libraryId);
		List<LibraryEntity> list = libraryRepository.select(libraryEntity);
		String librarySymbol = "";
		if(list.size()>0){
			librarySymbol = list.get(0).getLibrarySymbol();
			this.libraryRepository.delete(librarySymbol);
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.DELETE_SUCCESS);
		}
		return new Result(ResultConstant.FAIL_STATU, "不存在此ID的字典数据");
	}
	
    /**
     * 指定主键，修改给定信息项
     * @param libraryEntity
     */
	@Override
	public Result modify(LibraryEntity libraryEntity) throws Exception {
		String libraryId = libraryEntity.getLibraryId();
		if(StringUtil.checkNull(libraryId)) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		if(!StringUtil.checkNull(libraryEntity.getLibraryCode())){
			LibraryEntity code = new LibraryEntity();
			code.setLibraryCode(libraryEntity.getLibraryCode());
			code.setLibraryParentId(libraryEntity.getLibraryParentId());
			List<LibraryEntity> list = libraryRepository.select(code);
			if(list.size()>1||(list.size()==1&&(!libraryId.equals(list.get(0).getLibraryId())))){
				return new Result(ResultConstant.FAIL_STATU, "信息项编号重复");
			}
		}
		libraryEntity.setLibrarySymbol(null);
		libraryEntity.setLibraryLevel(null);
		this.libraryRepository.update(libraryEntity);
		return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.UPDATA_SUCCESS);
	}
	
}