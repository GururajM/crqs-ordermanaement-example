package com.store.ordermanagement.ordermangement.commands;

import java.util.Map;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

//Command for store registration
@Data
public class RegisterStoreCommand {
	@TargetAggregateIdentifier
	private final String storeId;
	
	private final String storeName;
	private final Map<String, Integer> storeStock;
	
	public RegisterStoreCommand(String storeId, String storeName, Map<String, Integer> storeStock) {
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
