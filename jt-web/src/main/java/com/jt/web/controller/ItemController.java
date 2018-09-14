package com.jt.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jt.web.pojo.Item;
import com.jt.web.service.ItemService;

@Controller
@RequestMapping("/items")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value="/{itemId}", method=RequestMethod.GET)
	public String findItemById(@PathVariable Long itemId, Model model) {
		Item item = itemService.findItemById(itemId);
		model.addAttribute("item", item);
		return "item";
	}
}
