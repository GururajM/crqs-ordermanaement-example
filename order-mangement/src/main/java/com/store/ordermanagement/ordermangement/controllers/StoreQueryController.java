package com.store.ordermanagement.ordermangement.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/OMS")
public class StoreQueryController {
	 private final StoreQueryService storeQueryService;
	 
	 

    public StoreQueryController(StoreQueryService storeQueryService) {
		super();
		this.storeQueryService = storeQueryService;
	}



	@GetMapping("/{storeId}/events")
    public List<Object> listEventsForStore(@PathVariable(value = "storeId") String storeId){
        return storeQueryService.listEventsForStore(storeId);
    }
}
