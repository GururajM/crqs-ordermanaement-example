package com.store.ordermanagement.ordermangement.controllers;

import java.util.List;

// List all Events for a given store ID 
public interface StoreQueryService {
	    public List<Object> listEventsForStore(String storeId);
	}