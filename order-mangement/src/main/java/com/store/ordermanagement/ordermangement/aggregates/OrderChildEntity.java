package com.store.ordermanagement.ordermangement.aggregates;

import java.util.Map;

import org.axonframework.modelling.command.EntityId;

import lombok.Data;

// Child entity of the domain
@Data
public class OrderChildEntity {

    @EntityId
    private final String orderId;		// Unique identifier for the order

	private OrderStatus orderStatus;	// Order status
    private Map<String, Integer> orderItemList;		// Order items and quantity
    
    public OrderChildEntity(String orderId, OrderStatus orderStatus, Map<String, Integer> orderItemList) {
		super();
		this.orderId = orderId;
		this.orderStatus = orderStatus;
		this.orderItemList = orderItemList;
	}
    
    // Order is uniquely identified just by the identifier
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderChildEntity other = (OrderChildEntity) obj;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		return true;
	}

	public String getOrderId() {
		return orderId;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Map<String, Integer> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(Map<String, Integer> orderItemList) {
		this.orderItemList = orderItemList;
	}

}
