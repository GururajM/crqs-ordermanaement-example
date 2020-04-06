package com.store.ordermanagement.ordermangement.events;

import java.util.Map;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

// Event definition for store stock update
public class StockUpdatedEvent {
	@TargetAggregateIdentifier
	private final String storeId;

	private final Map<String, Integer> storeStock;

	public String getStoreId() {
		return storeId;
	}

	public Map<String, Integer> getStoreStock() {
		return storeStock;
	}

	public StockUpdatedEvent(String storeId, Map<String, Integer> storeStock) {
		super();
		this.storeId = storeId;
		this.storeStock = storeStock;
	}
}
