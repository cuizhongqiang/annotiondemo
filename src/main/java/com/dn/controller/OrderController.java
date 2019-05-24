package com.dn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dn.service.OrderService;



@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;


	@RequestMapping("query")
	public Object query(String customerId, int pageNum, int pageSize) {
		return this.orderService.pageQuery(customerId, pageNum, pageSize);

	}
}
