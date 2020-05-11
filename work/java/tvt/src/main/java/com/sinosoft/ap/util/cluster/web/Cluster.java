package com.sinosoft.ap.util.cluster.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sinosoft.ap.common.exception.NullAttributeException;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;
import net.sf.json.JSONException;
import net.sf.json.JSONNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sinosoft.ap.util.http.HttpUtil;
import com.sinosoft.ap.util.http.ParamType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
public class Cluster {
	
	private static Logger logger = LogManager.getLogger(Cluster.class);
	
	private static String sysInfo = "系统提示";
	
	@Value("${ap.cluster.isParent:false}")
	private boolean isParent;
	
	@Value("${ap.cluster.parentPath:weizhi}")
	private String parentURL;
//	
//	@Autowired
//	private RestTemplate restTemplate;
//	
	public Object getParentResult(String URL,Map<String, Object> param,String token){
		List<String> removeParam = new ArrayList<>();
		if(!isParent) {
            for (String key : param.keySet()) {
                Object object = param.get(key);
                if(!(object instanceof String) && object.toString().equals("null")){
                    param.put(key,"");
					removeParam.add(key);
                }
            }
            for (String string:removeParam){
            	param.remove(string);
			}
            if(this.parentURL.equals("weizhi")){
            	throw new NullAttributeException("父级节点路径未配置");
			}
			URL = this.parentURL+URL;
			logger.info(sysInfo,"由于本系统是子系统，故需请求父级管理节点"+URL);
			logger.info(sysInfo,"请求参数为："+param.toString());
			String temp =  (String) HttpUtil.postRequest(param, URL, ParamType.STRING,token);
			logger.info(sysInfo,"返回结果为："+temp);
            JSONObject jsonObject = null;
            try {
                jsonObject = JSONObject.fromObject(temp);
            }catch (Exception e){
                jsonObject = JSONObject.fromObject(new Result(ResultConstant.FAIL_STATU, "请求父节点失败"));
            }
			return dealWithJsonObject(jsonObject);
		}
		logger.info(sysInfo,"本系统已经是父级管理节点");
		return null;
	}
	
	public Object getParentObject(String URL,Map<String, Object> param,String token){
		if(!isParent) {
			URL = this.parentURL+URL;
			logger.info(sysInfo,"由于本系统是子系统，故需请求父级管理节点"+URL);
			logger.info(sysInfo,"请求参数为："+param.toString());
			String temp =  (String) HttpUtil.postRequest(param, URL, ParamType.STRING,token);
			logger.info(sysInfo,"返回结果为："+temp);
			return temp;
		}
		logger.info(sysInfo,"本系统已经是父级管理节点");
		return null;
	}
		
	@SuppressWarnings({ "unchecked"})
	public JSONObject dealWithJsonObject (JSONObject jsonObject) {
		JSONObject temps = new JSONObject();
		jsonObject.forEach((k,v) -> {
			if(v instanceof JSONObject) {
				if(((JSONObject) v).isNullObject()) {
					temps.put(k, "");
				}else {
					JSONObject tt = JSONObject.fromObject(v);
					tt = dealWithJsonObject(tt);
					temps.put(k, tt);
				}
			}else
			if(v instanceof JSONArray) {
				JSONArray jsonArray = new JSONArray();
				((JSONArray) v).forEach(n -> {
					if(n instanceof JSONObject) {
						n = dealWithJsonObject((JSONObject) n);		
						jsonArray.add(n);
					}else {
						jsonArray.add(n);
					}
				});
				temps.put(k, jsonArray);
			}
			if(!(v instanceof JSONObject)&&!(v instanceof JSONArray)) {
				temps.put(k, v);
			}
		});
		return temps;
	}
	
}
