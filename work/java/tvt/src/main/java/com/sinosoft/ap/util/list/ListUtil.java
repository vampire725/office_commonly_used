package com.sinosoft.ap.util.list;

import java.util.List;

public class ListUtil {

	/**
	 * 对list进行非空判断
	 * @param list
	 * @return
	 */
	public static <T> boolean checkNull(List<T> list) {
		if(null==list||list.size()<1) {
			return true;
		}
		return false;
	}
}
