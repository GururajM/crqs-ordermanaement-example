package com.store.ordermanagement.ordermangement.events;

import java.util.Map;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.store.ordermanagement.ordermangement.aggregates.OrderStatus;

// Event definition for order creation
public class OrderCreatedEvent {
	@TargetAggregateIdentifier
	private final String storeId;
	
	private final String orderId;
	private final Map<String, Integer> orderItems;
	private final String orderStatus;
	
	public OrderCreatedEvent(String storeId, String orderId, String orderStatus, Map<String, Integer> orderItems) {
		this.storeId = storeId;
		this.orderId = orderId;
		this.orderItems = orderItems;
		this.orderStatus = orderStatus;
	}
	
	public Map<String, Integer> getOrderItems() {
		return orderItems;
	}

	
	public String getStoreId() {
		return storeId;
	}
	
	public String getOrderId() {
		return orderId;
	}


	
	public String getOrderStatus() {
		return orderStatus;
	}
}
