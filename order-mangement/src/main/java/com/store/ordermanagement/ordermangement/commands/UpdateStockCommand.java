package com.store.ordermanagement.ordermangement.commands;

import java.util.Map;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

// Command for stock update
@Data
public class UpdateStockCommand {
	@TargetAggregateIdentifier
	private final String storeId;

	private final Map<String, Integer> storeStock;

	public String getStoreId() {
		return storeId;
	}

	public Map<String, Integer> getStoreStock() {
		return storeStock;
	}

	public UpdateStockCommand(String storeId, Map<String, Integer> storeStock) {
		super();
		this.storeId = storeId;
		this.storeStock = storeStock;
	}
}
