package com.sinosoft.ap.component.uploadfile.web;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sinosoft.ap.component.uploadfile.FileUpLoadConstant;
import com.sinosoft.ap.component.uploadfile.service.FileUploadService;
import com.sinosoft.ap.util.fdfs.SaveFile;
import com.sinosoft.ap.util.id.PrimaryKeyUtil;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用于处理文件的上传
 *
 */
@RestController
@RequestMapping("/ap-system")
@Api(value="文件上传服务",tags={"文件上传服务"})
public class FileController {
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private SaveFile saveFile;
	
	/**
	 * 单文件上传
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadFileOnLocal")
	@ApiOperation(value="单文件本地上传",response=Result.class,httpMethod="POST")
	public @ResponseBody Result uploadFileOnLocal(HttpServletRequest req,
		      MultipartHttpServletRequest multiReq)throws Exception{
		try {
			MultipartFile file = multiReq.getFile("file");
			if (file.isEmpty()) {
				return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPLOAD_FILE_FAIL);
			}
			String separator = System.getProperty("file.separator");
			String path = System.getProperty("user.dir");
			path = path.endsWith(separator)? path:path+separator;
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.UPLOAD_FILE_SUCCESS,this.fileUploadService.uploadFileToLocal(file, path,null));
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPLOAD_FILE_FAIL);
		}
	}
	
	/**
	 * 多文件上传
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadFilesOnLocal")
	@ApiOperation(value="多文件本地上传",response=Result.class,httpMethod="POST")
	public Result uploadFilesOnLocal(HttpServletRequest request)throws Exception{
		try {
			List<MultipartFile> files = new ArrayList<>();
			files = ((MultipartHttpServletRequest)request).getFiles("file");
			if (files.isEmpty()) {
				return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPLOAD_FILE_FAIL);
			}
			List<String> paths= new ArrayList<>();
			for (MultipartFile file:files) {
				paths.add(this.fileUploadService.uploadFileToLocal(file, null,null));
			}
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.UPLOAD_FILE_SUCCESS,paths);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPLOAD_FILE_FAIL);
		}
	}
	
	/**
	 * 多文件上传
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadFilesOnFDFS")
	@ApiOperation(value="多文件服务器上传",response=Result.class,httpMethod="POST")
	public Result uploadFilesOnFDFS(HttpServletRequest request)throws Exception{
		try {
			List<MultipartFile> files = new ArrayList<>();
			files = ((MultipartHttpServletRequest)request).getFiles("file");
			if (files.isEmpty()) {
				return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPLOAD_FILE_FAIL);
			}
			List<String> paths= new ArrayList<>();
			for (MultipartFile file:files) {
				String temp = this.fileUploadService.uploadToTFS(file,true).get(FileUpLoadConstant.SERVER_PATH);
				paths.add(temp);
			}
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.UPLOAD_FILE_SUCCESS,paths);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPLOAD_FILE_FAIL);
		}
	}
	
	@RequestMapping("/deleteFilesOnFDFS")
	@ApiOperation(value="服务器删除文件",response=Result.class,httpMethod="POST")
	public Result deleteFilesOnFDFS(String URLS)throws Exception{
		try {
			if(StringUtil.checkNull(URLS)) {
				return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPDATA_FAIL_ERROE_ATTRIBUTE);
			}
			String[] temps = URLS.split(",");
			saveFile.setUp();
			Stream.of(temps).forEach(saveFile::delete);
			saveFile.destory();
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.DELETE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPLOAD_FILE_FAIL);
		}
	}
	
	@RequestMapping("/downLoadFile")
	@ApiOperation(value="从文件服务器下载文件",response=Result.class,httpMethod="POST")
	public Result downLoadFile(HttpServletResponse response,String URL)throws Exception{
		try {
			if(StringUtil.checkNull(URL)) {
				return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPDATA_FAIL_ERROE_ATTRIBUTE);
			}
			response.setContentType("application/x-gzip");
			String sufx = URL.substring(URL.lastIndexOf("."));
			response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(PrimaryKeyUtil.create()+sufx, "UTF-8"));
			OutputStream os = response.getOutputStream();
			byte[] bs = null;
			saveFile.setUp();
			bs = saveFile.fetchByte(URL);
			if(null==bs) {
				return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPDATA_FAIL_ERROE_ATTRIBUTE);
			}
			os.write(bs);
			os.flush();
			os.close();
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.DELETE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPLOAD_FILE_FAIL);
		}
	}
	
}
