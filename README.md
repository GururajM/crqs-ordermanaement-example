# crqs-ordermanaement-example

# OrderManagement Application

# Introduction
The application is an order management application in the domain of online retail. The application is a Spring Boot microservice that used CQRS and Event Sourcing design pattern to handle data using AXON framework. The application used in-memory H2 database to store events. 
Domain-driven Design of the application:
Since the application domain is online retail, the application can be considered to be consisting of several entities. The aggregate root is the online store which maintains a record of the stock of products and all the orders received. The Store aggregate root consists of a dictionary of products and their quantity as stock. It also contains a list of orders identified by their order ID. Additionally the Store aggregate root also contains name for the store. The stock is not a child entity as each store has a single instance of stock.
The Order entity is the child entity to Store aggregate root. Each order is uniquely identified by its orderId. The Order entity also contains a dictionary of all the products that are listed to be purchased as well as their quantity. Additionally, a OrderStatus attribute is also present to indicate if the order is just created, if it is confirmed by the admin, or if it is rejected by the admin and cannot processed. If the order is confirmed by the admin, then it leads to stock being readjusted to reflect the latest availability of the products.

Note: The application requires AXON server. Run "docker run -d --name axonserver -p 8024:8024 -p 8124:8124 axoniq/axonserver".

Below is the domain model for the application:

![header image](https://github.com/GururajM/crqs-ordermanaement-example/blob/master/Domain_model.png)

# Use-Cases:
1.	Register a store
To be able to place orders a store needs to exist. So, we first create a store as below:

Post: 	http://localhost:8080/OMS/storeRegister

Body: {
  		"storeName" : "MyStore",      
            "storeStock": {"Phone" : 100, "Laptop":100, "Tablet":100, "Camera" : 100,   "Headphone":100, "Monitor":100},      
		  "username" : "admin",      
		  "password" : "password"
}

The usename and password are to be mentioned as only only admin can register a store. Any other usersnames or none will result in store not registered. The request will print a generated {storeId}. Please use this in subsequent requests.

2.	Update store stock
The admin can update the stock of the store. The admin can choose the specific products that should be restocked. 

Post: 	http://localhost:8080/OMS/updateStoreStock/{storeId}

Body: {
"storeStock": {"Phone" : 50, "Laptop":10}
}

3.	Create order
Any user can create orders. The used can choose to order a single product or several products. The request generated a {orderId} which should be used in subsequent requests along with {storeId} to process the requests.

Post: 	http://localhost:8080/OMS/{storeId}/orderCreate

Body: {
			"orderItemList": {"Phone" : 1, "Laptop":1}
}

4.	View order details
Both admin and the user can view the order details. 

Get:	http://localhost:8080/OMS/orderDetails/{storeId}/{orderId}

Body: { }

5.	Confirm order
Only admin can confirm the order. Once the order is confirmed, the store stock is automatically updated.

Post:	http://localhost:8080/OMS/orderConfirm/{storeId}/{orderId}

Body: {
"orderStatus": "CONFIRMED"
}

6.	View stock details
Only the admin can view the store’s stock details

Get: http://localhost:8080/OMS/stockDetails/{storeId}

Body: {
		}
    
7.	View all the events for a store
Additionally, the store’s events could also be check.

Get: http://localhost:8080/OMS /{storeId}/events

Body: {	}
