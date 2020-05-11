package com.sinosoft.ap.util.file;

import java.io.File;

import com.sinosoft.ap.util.string.StringUtil;

public class FileUtil {

	/**
	 * 删除本地文件
	 * @param file
	 * @return
	 */
	public static boolean deleteLocalFile (String file) {
		Boolean flag = false;
		if (StringUtil.checkNull(file)) {
			return flag;
		}
		File localfile = new File(file);
		if (!localfile.exists()) {  
	        return flag;  
	    } else {  
	        if (localfile.isFile()) {  
	            return deleteFile(file);  
	        } else { 
	            return deleteDirectory(file);  
	        }  
	    }
	}
	/** 
	 * 删除单个文件 
	 * @param   sPath    被删除文件的文件名 
	 * @return 单个文件删除成功返回true，否则返回false 
	 */  
	private static boolean deleteFile(String sPath) {  
	    boolean flag = false;  
	    File file = new File(sPath);  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}  
	
	/** 
	 * 删除目录（文件夹）以及目录下的文件 
	 * @param   sPath 被删除目录的文件路径 
	 * @return  目录删除成功返回true，否则返回false 
	 */  
	private static boolean deleteDirectory(String sPath) {  
	    if (!sPath.endsWith(File.separator)) {  
	        sPath = sPath + File.separator;  
	    }  
	    File dirFile = new File(sPath);  
	    if (!dirFile.exists() || !dirFile.isDirectory()) {  
	        return false;  
	    }  
	    boolean flag = true;  
	    File[] files = dirFile.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	        if (files[i].isFile()) {  
	            flag = deleteFile(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        }
	        else {  
	            flag = deleteDirectory(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        }  
	    }  
	    if (!flag) return false;  
	    if (dirFile.delete()) {  
	        return true;  
	    } else {  
	        return false;  
	    }  
	}  
}
