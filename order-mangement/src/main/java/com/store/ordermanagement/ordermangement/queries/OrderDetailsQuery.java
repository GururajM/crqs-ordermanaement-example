package com.store.ordermanagement.ordermangement.queries;

// Query definition for order details
public class OrderDetailsQuery {
	private final String storeId;
	private final String orderId;
	
	public OrderDetailsQuery(String storeId, String orderId) {
		this.storeId = storeId;
		this.orderId = orderId;
	}
	
	public String getOrderId() {
		return orderId;
	}
	
	public String getStoreId() {
		return storeId;
	}
}