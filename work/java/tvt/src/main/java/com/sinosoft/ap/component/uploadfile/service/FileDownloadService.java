package com.sinosoft.ap.component.uploadfile.service;

import javax.servlet.http.HttpServletResponse;

public interface FileDownloadService {

	boolean downloadFile(HttpServletResponse response,String file) throws Exception;
}
