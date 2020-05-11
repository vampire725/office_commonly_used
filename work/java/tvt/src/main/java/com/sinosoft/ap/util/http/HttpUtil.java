package com.sinosoft.ap.util.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.sinosoft.ap.common.constant.LogConstant;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HttpUtil {

	private static final HttpClient client = HttpClientBuilder.create().build();

	/**
	 * 
	 * @param parameters	post请求参数
	 * @param URL	post请求地址
	 * @return
	 */
	public static Object postRequest(Map<String, Object> parameters, String URL, ParamType type,String token) {
		List<NameValuePair> urlParameters = new ArrayList<>();
		if(type.equals(ParamType.ENTITY)) {
			if (null != parameters && parameters.size() > 0) {
				parameters.forEach(
						(k, v) -> urlParameters.add(new BasicNameValuePair(k, JSONObject.fromObject(v).toString())));
			}			
		}else if (type.equals(ParamType.LIST)) {
			if (null != parameters && parameters.size() > 0) {
				parameters.forEach(
						(k, v) -> urlParameters.add(new BasicNameValuePair(k, JSONArray.fromObject(v).toString())));
			}	
		}else if (type.equals(ParamType.STRING)) {
			if (null != parameters && parameters.size() > 0) {
				parameters.forEach(
						(k, v) -> urlParameters.add(new BasicNameValuePair(k, v.toString())));
			}	
		}
		HttpPost post = new HttpPost(URL);
		try {
			post.setHeader(LogConstant.AUTHORIZATION, token);
			post.setEntity(new UrlEncodedFormEntity(urlParameters, "UTF-8"));
			try {
				HttpResponse response = client.execute(post);
				HttpEntity entity = response.getEntity();
				String result = EntityUtils.toString(entity, "UTF-8");
				return result;
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				throw new HttpException(HostConstant.HOST_SERVICE_EXCEPTION);
			} catch (IOException e) {
				e.printStackTrace();
				throw new HttpException(HostConstant.HOST_SERVICE_EXCEPTION);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new HttpException(HostConstant.HOST_SERVICE_EXCEPTION);
		}
	}
	
	
	public static Object postRequest(Map<String, Object> parameters, String URL, ParamType type) {
		List<NameValuePair> urlParameters = new ArrayList<>();
		if(type.equals(ParamType.ENTITY)) {
			if (null != parameters && parameters.size() > 0) {
				parameters.forEach(
						(k, v) -> urlParameters.add(new BasicNameValuePair(k, JSONObject.fromObject(v).toString())));
			}			
		}else if (type.equals(ParamType.LIST)) {
			if (null != parameters && parameters.size() > 0) {
				parameters.forEach(
						(k, v) -> urlParameters.add(new BasicNameValuePair(k, JSONArray.fromObject(v).toString())));
			}	
		}else if (type.equals(ParamType.STRING)) {
			if (null != parameters && parameters.size() > 0) {
				parameters.forEach(
						(k, v) -> urlParameters.add(new BasicNameValuePair(k, v.toString())));
			}	
		}
		HttpPost post = new HttpPost(URL);
		try {
//			post.setHeader(LogConstant.AUTHORIZATION, token);
			post.setEntity(new UrlEncodedFormEntity(urlParameters, "UTF-8"));
			try {
				HttpResponse response = client.execute(post);
				HttpEntity entity = response.getEntity();
				String result = EntityUtils.toString(entity, "UTF-8");
				return result;
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				throw new HttpException(HostConstant.HOST_SERVICE_EXCEPTION);
			} catch (IOException e) {
				e.printStackTrace();
				throw new HttpException(HostConstant.HOST_SERVICE_EXCEPTION);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new HttpException(HostConstant.HOST_SERVICE_EXCEPTION);
		}
	}

	/**
	 * 
	 * @param parameters	get请求参数
	 * @param URL	get请求地址
	 * @return
	 */
	public static Object getRequest(Map<String, Object> parameters, String URL, ParamType type,String token) {
		StringBuffer stringBuffer = new StringBuffer(URL);
		if(type.equals(ParamType.ENTITY)) {
			if (null != parameters && parameters.size() > 0) {
				parameters.forEach((k, v) -> {
					stringBuffer.append(k).append("=").append(JSONObject.fromObject(v).toString()).append("&");
				});
			}
		}else if (type.equals(ParamType.LIST)) {
			if (null != parameters && parameters.size() > 0) {
				parameters.forEach((k, v) -> {
					stringBuffer.append(k).append("=").append(JSONArray.fromObject(v).toString()).append("&");
				});
			}
		}else if (type.equals(ParamType.STRING)) {
			if (null != parameters && parameters.size() > 0) {
				parameters.forEach((k, v) -> {
					stringBuffer.append(k).append("=").append(v).append("&");
				});
			}
		}
		if (stringBuffer.lastIndexOf("&") == (stringBuffer.length() - 1)) {
			URL = stringBuffer.substring(0, stringBuffer.length() - 1);
		} else {
			URL = stringBuffer.toString();
		}
		HttpPost post = new HttpPost(URL);
		try {
			post.setHeader(LogConstant.AUTHORIZATION, token);
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity, "UTF-8");
			return result;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new HttpException(HostConstant.HOST_SERVICE_EXCEPTION);
		} catch (IOException e) {
			e.printStackTrace();
			throw new HttpException(HostConstant.HOST_SERVICE_EXCEPTION);
		}
	}
	
	
	public static Object getRequest(Map<String, Object> parameters, String URL, ParamType type) {
		StringBuffer stringBuffer = new StringBuffer(URL);
		if(type.equals(ParamType.ENTITY)) {
			if (null != parameters && parameters.size() > 0) {
				parameters.forEach((k, v) -> {
					stringBuffer.append(k).append("=").append(JSONObject.fromObject(v).toString()).append("&");
				});
			}
		}else if (type.equals(ParamType.LIST)) {
			if (null != parameters && parameters.size() > 0) {
				parameters.forEach((k, v) -> {
					stringBuffer.append(k).append("=").append(JSONArray.fromObject(v).toString()).append("&");
				});
			}
		}else if (type.equals(ParamType.STRING)) {
			if (null != parameters && parameters.size() > 0) {
				parameters.forEach((k, v) -> {
					stringBuffer.append(k).append("=").append(v).append("&");
				});
			}
		}
		if (stringBuffer.lastIndexOf("&") == (stringBuffer.length() - 1)) {
			URL = stringBuffer.substring(0, stringBuffer.length() - 1);
		} else {
			URL = stringBuffer.toString();
		}
		HttpPost post = new HttpPost(URL);
		try {
//			post.setHeader(LogConstant.AUTHORIZATION, token);
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity, "UTF-8");
			return result;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new HttpException(HostConstant.HOST_SERVICE_EXCEPTION);
		} catch (IOException e) {
			e.printStackTrace();
			throw new HttpException(HostConstant.HOST_SERVICE_EXCEPTION);
		}
	}
	
	
	/**
	 * 
	 * @param URL	get请求地址
	 * @return
	 */
	public static Object getRequest(String URL,String token) {
		StringBuffer stringBuffer = new StringBuffer(URL);
		if (stringBuffer.lastIndexOf("&") == (stringBuffer.length() - 1)) {
			URL = stringBuffer.substring(0, stringBuffer.length() - 1);
		} else {
			URL = stringBuffer.toString();
		}
		HttpGet post = new HttpGet(URL);
		try {
			post.setHeader(LogConstant.AUTHORIZATION, token);
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity, "UTF-8");
			return result;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new HttpException(HostConstant.HOST_SERVICE_EXCEPTION);
		} catch (IOException e) {
			e.printStackTrace();
			throw new HttpException(HostConstant.HOST_SERVICE_EXCEPTION);
		}
	}
}
