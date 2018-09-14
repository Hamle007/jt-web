package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Item;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private HttpClientService httpClient;
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public Item findItemById(Long itemId) {
		String url = "http://manage.jt.com/web/item/findItemById";
		Map<String,String> params = new HashMap<>();
		params.put("itemId", itemId+"");
		String result = httpClient.doGet(url, params);
		System.out.println(result);
		Item item = null;
		try {
			item = objectMapper.readValue(result, Item.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}

	
}
