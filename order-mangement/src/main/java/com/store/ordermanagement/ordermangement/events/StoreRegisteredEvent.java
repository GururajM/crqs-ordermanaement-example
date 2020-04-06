package com.store.ordermanagement.ordermangement.events;

import java.util.Map;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

// Event definition for store registration
public class StoreRegisteredEvent {
	@TargetAggregateIdentifier
	private final String storeId;

	private final String storeName;
	private final Map<String, Integer> storeStock;
	
	public StoreRegisteredEvent(String storeId, String storeName, Map<String, Integer> storeStock) {
		// TODO Auto-generated constructor stub
		this.storeId = storeId;
		this.storeName = storeName;
		this.storeStock = storeStock;
	}
		
	public String getStoreId() {
		return storeId;
	}

	public String getStoreName() {
		return storeName;
	}
	
	public Map<String, Integer> getStoreStock() {
		return storeStock;
	}
}
