package com.store.ordermanagement.ordermangement.models;

import java.util.Map;

import lombok.Data;

@Data
public class StoreBeanAdmin {
	private String storeName;
    private Map<String, Integer> storeStock;
    private String username;
    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private String password;
    
    
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