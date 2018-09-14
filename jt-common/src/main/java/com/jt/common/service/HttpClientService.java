package com.jt.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class HttpClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientService.class);

    @Autowired(required=false)
    private CloseableHttpClient httpClient;

    @Autowired(required=false)
    private RequestConfig requestConfig;

    /**
     * 编辑get请求
     */
    public String doGet(String uri, Map<String,String> params, String charset) {
    	String result = null;
    	// 1.判断字符集
    	if(StringUtils.isEmpty(charset)){
    		charset = "utf-8";
    	}
    	// 2.判断参数
    		/*
    		uri = uri + "?";
    		for(Map.Entry<String,String> e : params.entrySet()){
    			uri = uri + e.getKey() + "=" + e.getValue() + "&";
    		}
    		uri = uri.substring(0, uri.length()-1);
    		*/
    		try{
    			if(params != null){
	    			URIBuilder builder = new URIBuilder(uri);
	    			for (Map.Entry<String, String> entry : params.entrySet()) {
						builder.addParameter(entry.getKey(), entry.getValue());
					}
	    			uri = builder.build().toString();
    			}
    			System.out.println(uri);
    			HttpGet httpGet = new HttpGet(uri);
    	    	httpGet.setConfig(requestConfig);
    	    	CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
    	    	if(httpResponse.getStatusLine().getStatusCode() == 200){
    	    		result = EntityUtils.toString(httpResponse.getEntity(),charset);
    	    	}
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    		System.out.println(uri);
    	return result;
    }
    
    public String doGet(String uri) {
    	return doGet(uri, null, null);
    }
    
    public String doGet(String uri, Map<String,String> params) {
    	return doGet(uri, params, null);
    }
    
    public String doPost(String uri, Map<String,String> params, String charset) {
    	String result = null;
    	// 1.判断字符集
    	if(StringUtils.isEmpty(charset)){
    		charset = "utf-8";
    	}
    	// 定义请求对象
    	HttpPost httpPost = new HttpPost(uri);
    	httpPost.setConfig(requestConfig);
    	if(params != null){
	    	List<NameValuePair> parameters = new ArrayList<>();
	    	for (Map.Entry<String, String> entry : params.entrySet()) {
	    		BasicNameValuePair param = new BasicNameValuePair(entry.getKey(), entry.getValue());
	    		parameters.add(param);
			}
	    	try {
	    		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters , charset);
	    		httpPost.setEntity(formEntity);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	try {
    		CloseableHttpResponse response = httpClient.execute(httpPost);
    		if(response.getStatusLine().getStatusCode() == 200){
    			result = EntityUtils.toString(response.getEntity(),charset);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return result;
    }
    
    public String doPost(String uri) {
    	return doPost(uri, null, null);
    }
    
    public String doPost(String uri, Map<String,String> params) {
    	return doPost(uri, params, null);
    }
}
