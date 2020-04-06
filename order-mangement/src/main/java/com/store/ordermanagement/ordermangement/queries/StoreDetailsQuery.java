package com.store.ordermanagement.ordermangement.queries;

import lombok.Data;

// Query definition for store details
@Data
public class StoreDetailsQuery {
	public StoreDetailsQuery(String storeId) {
		super();
		this.storeId = storeId;
	}

	public String getStoreId() {
		return storeId;
	}

	private final String storeId;
}
