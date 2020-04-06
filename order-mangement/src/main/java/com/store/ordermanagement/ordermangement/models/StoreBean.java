package com.store.ordermanagement.ordermangement.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.axonframework.modelling.command.AggregateMember;

import com.store.ordermanagement.ordermangement.aggregates.OrderChildEntity;

import lombok.Data;

@Data
public class StoreBean {
	private String storeName;
    private Map<String, Integer> storeStock;
    
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public Map<String, Integer> getStoreStock() {
		return storeStock;
	}
	public void setStoreStock(Map<String, Integer> storeStock) {
		this.storeStock = storeStock;
	}

}
