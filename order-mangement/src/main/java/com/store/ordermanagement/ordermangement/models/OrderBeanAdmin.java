package com.store.ordermanagement.ordermangement.models;

import java.util.Map;

// Model for order details for admin
public class OrderBeanAdmin {

	private String orderId;
	private Map<String, Integer> orderItemList;
	private String orderStatus;
	
    private String username;
	private String password;

    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Map<String, Integer> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(Map<String, Integer> orderItemList) {
		this.orderItemList = orderItemList;
	}
}

