package com.store.ordermanagement.ordermangement.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.stereotype.Service;

// Implementation of store event service
@Service
	public class StoreQueryServiceImpl implements StoreQueryService {
	 
	    private final EventStore eventStore;
	 
	    public StoreQueryServiceImpl(EventStore eventStore) {
	        this.eventStore = eventStore;
	    }
	 
	    // List all events from event store 
	    @Override
	    public List<Object> listEventsForStore(String storeId) {
	        return eventStore.readEvents(storeId).asStream().map( s -> s.getPayload()).collect(Collectors.toList());
	    }
	}

