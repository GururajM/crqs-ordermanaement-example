package com.store.ordermanagement.ordermangement.repository;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.axonframework.modelling.command.Repository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import com.store.ordermanagement.ordermangement.aggregates.StoreAggregate;
import com.store.ordermanagement.ordermangement.queries.StoreDetailsQuery;

// Projector for event replay
@Service
public class StoreProjector {
	private final Repository<StoreAggregate> storeRepository;

	public StoreProjector(Repository<StoreAggregate> storeRepository) {
		this.storeRepository = storeRepository;
	}

	@QueryHandler
	public StoreAggregate getLibrary(StoreDetailsQuery query) throws InterruptedException, ExecutionException {
		CompletableFuture<StoreAggregate> future = new CompletableFuture<StoreAggregate>();
		storeRepository.load("" + query.getStoreId()).execute(future::complete);
		return future.get();
	}
}
