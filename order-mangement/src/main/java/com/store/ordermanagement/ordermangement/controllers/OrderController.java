package com.store.ordermanagement.ordermangement.controllers;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.ordermanagement.ordermangement.aggregates.OrderStatus;
import com.store.ordermanagement.ordermangement.commands.CreateOrderCommand;
import com.store.ordermanagement.ordermangement.models.OrderBean;


@RestController
@RequestMapping(value = "/OMS")
public class OrderController {

	private final CommandGateway commandGateway;
	private final QueryGateway queryGateway;
	
    public OrderController(CommandGateway commandGateway, QueryGateway queryGateway) {
    	this.commandGateway = commandGateway;
    	this.queryGateway = queryGateway;
    }
    
    @PostMapping(value = "/{storeId}/orderCreate")
    public String createOrder(@PathVariable(value = "storeId") String storeId, @RequestBody OrderBean orderBean){
    	String randonId = UUID.randomUUID().toString();
        commandGateway.send(new CreateOrderCommand(storeId, UUID.randomUUID().toString(), 
        								OrderStatus.CREATED, orderBean.getOrderItemList()));
        return "Order created with ID: "+randonId;
    }
    
}