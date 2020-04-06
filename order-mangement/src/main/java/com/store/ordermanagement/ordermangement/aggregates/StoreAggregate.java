package com.store.ordermanagement.ordermangement.aggregates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import com.store.ordermanagement.ordermangement.commands.ConfirmOrderCommand;
import com.store.ordermanagement.ordermangement.commands.CreateOrderCommand;
import com.store.ordermanagement.ordermangement.commands.RegisterStoreCommand;
import com.store.ordermanagement.ordermangement.commands.UpdateStockCommand;
import com.store.ordermanagement.ordermangement.events.OrderConfirmedEvent;
import com.store.ordermanagement.ordermangement.events.OrderCreatedEvent;
import com.store.ordermanagement.ordermangement.events.StockUpdatedEvent;
import com.store.ordermanagement.ordermangement.events.StoreRegisteredEvent;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import lombok.Data;

// Aggregate root of the application domain
@Aggregate
@Data
public class StoreAggregate {

    @AggregateIdentifier
    private String storeId;		// Unique identifier for a store

	private String storeName;

    @AggregateMember
    private List<OrderChildEntity> orders = new ArrayList<>();	// List of all orders at the store

	private Map<String, Integer> storeStock;	// Stock of iterms, name and quantity

    public StoreAggregate() {

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
	
	public List<OrderChildEntity> getOrders() {
		return orders;
	}

	// Command handler for store registration
    @CommandHandler
    public StoreAggregate(RegisterStoreCommand registerStoreCommand) {
        apply(new StoreRegisteredEvent(registerStoreCommand.getStoreId(), registerStoreCommand.getStoreName(), registerStoreCommand.getStoreStock()));
    } 
    
    // Event handler for store registration
    @EventSourcingHandler
    protected void on(StoreRegisteredEvent storeRegisteredEvent){
        this.storeId = storeRegisteredEvent.getStoreId();
        this.storeName = storeRegisteredEvent.getStoreName();
        this.storeStock = storeRegisteredEvent.getStoreStock();
    }

    // Command handler for updating stock
    @CommandHandler
    public void handleUpdateStock(UpdateStockCommand updateStockCommand) {
        apply(new StockUpdatedEvent(updateStockCommand.getStoreId(), updateStockCommand.getStoreStock()));
    } 
    
    // Event handler for updating stock
    @EventSourcingHandler
    protected void handleStoreStockUpdate(StockUpdatedEvent stockUpdatedEvent){
    	for (Map.Entry<String, Integer> orderItem : this.storeStock.entrySet()) {
    		if(stockUpdatedEvent.getStoreStock().containsKey(orderItem.getKey())) {
    			// Either add (updated by admin) or deduct from stock (order approved)
    			Integer offset = stockUpdatedEvent.getStoreStock().get(orderItem.getKey());
    			orderItem.setValue(orderItem.getValue() + offset);
    		}
    	}
    }
    
    // Command handler order creation
    @CommandHandler
    public void handleCreateOrder(CreateOrderCommand createOrderCommand) {
    	System.out.println("create order");
        apply(new OrderCreatedEvent(createOrderCommand.getStoreId(), createOrderCommand.getOrderId(), 
        		String.valueOf(OrderStatus.CREATED), createOrderCommand.getOrderItems()));
    }
    
    // Event handler for order creation
    @EventSourcingHandler
    protected void handleOrderCreated(OrderCreatedEvent orderCreatedEvent){
    	OrderChildEntity orderEntity = new OrderChildEntity(orderCreatedEvent.getOrderId(), 
    			Enum.valueOf(OrderStatus.class, orderCreatedEvent.getOrderStatus()), orderCreatedEvent.getOrderItems());    			
        this.orders.add(orderEntity);	// Add order to the list of orders for the store
    }
    
    // Command handler for order confirmation or rejection
    @CommandHandler
    public void handleConfirmOrder(ConfirmOrderCommand confirmOrderCommand) {
        apply(new OrderConfirmedEvent(confirmOrderCommand.getStoreId(), confirmOrderCommand.getOrderId(), String.valueOf(confirmOrderCommand.getOrderStatus())));
    }
    
    // Event handler for order confirmation or rejection
    @EventSourcingHandler
    protected void handleOrderConfirmed(OrderConfirmedEvent orderConfirmedEvent){
    	for (OrderChildEntity orderEntity : this.orders) {
			if(orderEntity.getOrderId().equals(orderConfirmedEvent.getOrderId())) {
				// Set the order status as set by admin
				orderEntity.setOrderStatus(Enum.valueOf(OrderStatus.class, orderConfirmedEvent.getOrderStatus()));
				// If admin set order to confirm, them it should be reflected in store stock
				if(orderConfirmedEvent.getOrderStatus() == String.valueOf(OrderStatus.CONFIRMED)) {
					Map<String, Integer> tempMap = new HashMap<>(orderEntity.getOrderItemList());
					tempMap.replaceAll ((k,v) -> v != null ? -1*v : 0);		// Multiply all values with -1 so that it is subtracted from stock
					
					// Call event to update the stock
					apply(new StockUpdatedEvent(orderConfirmedEvent.getStoreId(), tempMap));
				}
			}
		}
    }
    
    
}

