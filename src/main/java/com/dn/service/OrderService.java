package com.dn.service;

import com.dn.annotation.NeedSetFeildValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dn.dao.OrderDao;
import com.dn.model.Order;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class OrderService {

	@Autowired
	private OrderDao orderDao;

	@NeedSetFeildValue
	public Page<Order> pageQuery(String customerId, int pageNum, int pageSize) {
		Page<Order> page = PageHelper.startPage(pageNum, pageSize);
		this.orderDao.query(customerId);

		// 需要获得订单的客户姓名
		return page;
	}
}
