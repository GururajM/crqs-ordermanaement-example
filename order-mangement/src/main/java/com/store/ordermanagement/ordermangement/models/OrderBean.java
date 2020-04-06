package com.store.ordermanagement.ordermangement.models;

import java.util.Map;

import com.store.ordermanagement.ordermangement.aggregates.OrderStatus;

// Model for order details
public class OrderBean {

	private String orderId;
	private Map<String, Integer> orderItemList;
	private String orderStatus;

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
