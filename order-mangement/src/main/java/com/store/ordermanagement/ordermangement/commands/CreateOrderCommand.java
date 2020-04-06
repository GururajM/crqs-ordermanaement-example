package com.store.ordermanagement.ordermangement.commands;

import java.util.Map;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.store.ordermanagement.ordermangement.aggregates.OrderStatus;

// Command for order creation
public class CreateOrderCommand {
	@TargetAggregateIdentifier
	private final String storeId;
		
	private final String orderId;
	private final Map<String, Integer> orderItems;
	private final OrderStatus orderStatus;
		
	public CreateOrderCommand(String storeId, String orderId, OrderStatus orderStatus, Map<String, Integer> orderItems) {
		this.storeId = storeId;
		this.orderId = orderId;
		this.orderItems = orderItems;
		this.orderStatus = orderStatus;
	}

	public String getStoreId() {
		return storeId;
	}
	
	public Map<String, Integer> getOrderItems() {
		return orderItems;
	}
	
	public String getOrderId() {
		return orderId;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	
}
