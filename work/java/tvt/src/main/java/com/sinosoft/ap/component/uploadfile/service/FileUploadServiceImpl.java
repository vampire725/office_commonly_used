package com.sinosoft.ap.component.uploadfile.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sinosoft.ap.component.uploadfile.FileUpLoadConstant;
import com.sinosoft.ap.util.fdfs.SaveFile;
import com.sinosoft.ap.util.id.PrimaryKeyUtil;
import com.sinosoft.ap.util.string.StringUtil;

@Service
public class FileUploadServiceImpl implements FileUploadService {
		
	@Autowired
	private SaveFile saveFile;
	
	@Override
	public String uploadFileToLocal(MultipartFile file,String storePath, String fileName) throws Exception {
		String separator = File.separator;
		String fileInfo = file.getOriginalFilename();
		if(StringUtil.checkNull(fileName)) {
			fileName = PrimaryKeyUtil.create();
		}
		if(StringUtil.checkNull(storePath)) {
			storePath = System.getProperty("java.io.tmpdir");
		}
		String fileType = fileInfo.substring(fileInfo.lastIndexOf("."));
		storePath =  storePath.endsWith(separator)?storePath:(storePath+separator);
		File temp = new File(storePath+fileName+fileType);
		temp.setWritable(true, false);
		if (!temp.getParentFile().exists()) {
			temp.getParentFile().mkdirs();
		}
		file.transferTo(temp);
		return temp.getAbsolutePath();
	}
	
	@Override
	public Map<String, String> uploadToTFS(MultipartFile file, boolean delete) throws Exception {
		Map<String, String> result = new HashMap<>();
		saveFile.setUp();
		String tempPath = this.uploadFileToLocal(file, null, null);
		String serverPath = saveFile.largeUpload(tempPath);
		result.put(FileUpLoadConstant.LOCAL_PATH, tempPath);
		result.put(FileUpLoadConstant.SERVER_PATH, serverPath);
		if(delete) {
			File tempFile = new File(tempPath);
			tempFile.delete();
			result.remove(FileUpLoadConstant.LOCAL_PATH);
		}
		saveFile.destory();
		return result;
	}
	
}
