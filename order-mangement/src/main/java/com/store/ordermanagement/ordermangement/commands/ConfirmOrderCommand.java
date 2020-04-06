package com.store.ordermanagement.ordermangement.commands;

import java.util.Map;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.store.ordermanagement.ordermangement.aggregates.OrderStatus;

// Command for order confirmation
public class ConfirmOrderCommand {
	@TargetAggregateIdentifier
	private final String storeId;
	
	private final String orderId;	
	private final OrderStatus orderStatus;

	public String getStoreId() {
		return storeId;
	}
	
	public ConfirmOrderCommand(String storeId, String orderId, OrderStatus orderStatus) {
		this.storeId = storeId;
		this.orderId = orderId;
		this.orderStatus = orderStatus;
	}
	public String getOrderId() {
		return orderId;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
}
