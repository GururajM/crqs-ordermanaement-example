package com.store.ordermanagement.ordermangement.events;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.store.ordermanagement.ordermangement.aggregates.OrderStatus;

// Event definition for order confirmation
public class OrderConfirmedEvent {
	@TargetAggregateIdentifier
	private final String storeId;
	
	private final String orderId;
	
	private final String orderStatus;

	public String getStoreId() {
		return storeId;
	}
	
	public OrderConfirmedEvent(String storeId, String orderId, String orderStatus) {
		this.storeId = storeId;
		this.orderId = orderId;
		this.orderStatus = orderStatus;
	}
	
	public String getOrderId() {
		return orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}
}
