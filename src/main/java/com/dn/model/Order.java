package com.dn.model;

import com.dn.annotation.NeedSetValue;
import com.dn.dao.UserDao;

import java.io.Serializable;


public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8821751371277937944L;

	private String id;

	private String customerId;

   @NeedSetValue(beanClass = UserDao.class,param = "customerId",method = "find",targetFiled = "name")
	private String customerName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

}
