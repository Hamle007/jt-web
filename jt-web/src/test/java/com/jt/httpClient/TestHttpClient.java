package com.jt.httpClient;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestHttpClient {

	// 测试get提交
	@Test
	public void test01() throws ClientProtocolException, IOException {
		String url = "http://manage.jt.com/item/query?page=1&rows=1";
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = httpClient.execute(httpGet);
		if(response.getStatusLine().getStatusCode() == 200){
			String data = EntityUtils.toString(response.getEntity());
			System.out.println(data);
		}
	}
	
}
