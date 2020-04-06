package com.store.ordermanagement.ordermangement.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.ordermanagement.ordermangement.aggregates.OrderChildEntity;
import com.store.ordermanagement.ordermangement.aggregates.OrderStatus;
import com.store.ordermanagement.ordermangement.aggregates.StoreAggregate;
import com.store.ordermanagement.ordermangement.commands.ConfirmOrderCommand;
import com.store.ordermanagement.ordermangement.commands.RegisterStoreCommand;
import com.store.ordermanagement.ordermangement.commands.UpdateStockCommand;
import com.store.ordermanagement.ordermangement.models.OrderBean;
import com.store.ordermanagement.ordermangement.models.OrderBeanAdmin;
import com.store.ordermanagement.ordermangement.models.StoreBean;
import com.store.ordermanagement.ordermangement.models.StoreBeanAdmin;
import com.store.ordermanagement.ordermangement.queries.OrderDetailsQuery;
import com.store.ordermanagement.ordermangement.queries.StoreDetailsQuery;


@RestController
@RequestMapping(value = "/OMS")
public class StoreController {

	private final CommandGateway commandGateway;
	private final QueryGateway queryGateway;
	
    public StoreController(CommandGateway commandGateway, QueryGateway queryGateway) {
    	this.commandGateway = commandGateway;
    	this.queryGateway = queryGateway;
    }
    
    @PostMapping(value = "/storeRegister")
    public String createOrder(@RequestBody StoreBeanAdmin storeBeanAdmin){
    	 // Check if admin 
    	 String randomId = UUID.randomUUID().toString();
         Properties prop = new Properties();
    	 try (InputStream input = StoreController.class.getClassLoader().getResourceAsStream("login.properties")) {
             prop.load(input);

             if(prop.getProperty("username").equals(storeBeanAdmin.getUsername()) 
            		 && prop.getProperty("password").equals(storeBeanAdmin.getPassword())) {
            	 // Send store registration command 
                 commandGateway.send(new RegisterStoreCommand(randomId, storeBeanAdmin.getStoreName(), storeBeanAdmin.getStoreStock()));
                 return "Store registered with ID: "+randomId;
             }else {
            	 return "Incorrect login credentials";
             }             
         } catch (IOException ex) {
             ex.printStackTrace();
             return "Fatal error";
         }

    }
    
    @PostMapping(value = "/updateStoreStock/{storeId}")
    public String confirmOrder(@PathVariable(value = "storeId") String storeId,
                                                          @RequestBody StoreBeanAdmin storeBeanAdmin){
        Properties prop = new Properties();
   	 	try (InputStream input = StoreController.class.getClassLoader().getResourceAsStream("login.properties")) {
            prop.load(input);

            if(prop.getProperty("username").equals(storeBeanAdmin.getUsername()) 
           		 && prop.getProperty("password").equals(storeBeanAdmin.getPassword())) {
            	// Send stock update command
            	commandGateway.send(new UpdateStockCommand(storeId, storeBeanAdmin.getStoreStock()));
            	return "Store stock updated";
            }else {
           	 	return "Incorrect login credentials";
            }             
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Fatal error";
        }
    }
    
    @PostMapping(value = "/orderConfirm/{storeId}/{orderId}")
    public String confirmOrder(@PathVariable(value = "orderId") String orderId, 
    												@PathVariable(value = "storeId") String storeId,
                                                          @RequestBody OrderBeanAdmin orderBeanAdmin){
        Properties prop = new Properties();
   	 	try (InputStream input = StoreController.class.getClassLoader().getResourceAsStream("login.properties")) {
            prop.load(input);

            if(prop.getProperty("username").equals(orderBeanAdmin.getUsername()) 
           		 && prop.getProperty("password").equals(orderBeanAdmin.getPassword())) {
            	// Send order confirmation command
            	commandGateway.send(new ConfirmOrderCommand(storeId, orderId, Enum.valueOf(OrderStatus.class, orderBeanAdmin.getOrderStatus())));
            	return "Store stock updated";
            }else {
           	 	return "Incorrect login credentials";
            }             
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Fatal error";
        }
    }
    
    
	@GetMapping("/orderDetails/{storeId}/{orderId}")
	public OrderBean getOrder(@PathVariable(value = "storeId") String storeId, 
			@PathVariable(value = "orderId") String orderId) throws InterruptedException, ExecutionException {
		// Replay events on given store ID
		CompletableFuture<StoreAggregate> future = queryGateway.query(new StoreDetailsQuery(storeId), StoreAggregate.class);
		List<OrderChildEntity> orderList = future.get().getOrders();
		OrderBean orderBean = new OrderBean();
		orderList.forEach(order -> {if(order.getOrderId().equals(orderId)) {
										orderBean.setOrderId(orderId);
										orderBean.setOrderStatus(String.valueOf(order.getOrderStatus()));
										orderBean.setOrderItemList(order.getOrderItemList());
									}
		});
		return orderBean;
	}
	

	@GetMapping("/stockDetails/{storeId}")
	public StoreBean getStock(@PathVariable(value = "storeId") String storeId, 
			@RequestBody StoreBeanAdmin storeBeanAdmin) throws InterruptedException, ExecutionException {
        Properties prop = new Properties();
   	 	try (InputStream input = StoreController.class.getClassLoader().getResourceAsStream("login.properties")) {
            prop.load(input);

            if(prop.getProperty("username").equals(storeBeanAdmin.getUsername()) 
           		 && prop.getProperty("password").equals(storeBeanAdmin.getPassword())) {
        		CompletableFuture<StoreAggregate> future = queryGateway.query(new StoreDetailsQuery(storeId), StoreAggregate.class);
        		StoreBean storeBean = new StoreBean();
        		storeBean.setStoreName(future.get().getStoreName());
        		storeBean.setStoreStock(future.get().getStoreStock());
        		return storeBean;
            }else {
           	 	return null;
            }             
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

	
}

