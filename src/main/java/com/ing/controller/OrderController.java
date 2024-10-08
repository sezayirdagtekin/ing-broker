package com.ing.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ing.entity.Order;
import com.ing.exception.AssetNotFounException;
import com.ing.exception.CustomerNotFoundException;
import com.ing.exception.InsufficientBalanceException;
import com.ing.exception.InvalidOrderOperationException;
import com.ing.exception.OrderNotFoundException;
import com.ing.request.OrderRequest;
import com.ing.request.OrderSearchequest;
import com.ing.response.OrderResponse;
import com.ing.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/order")
@Tag(name = "Order Controller", description = "Controller for order operations")
public class OrderController {

	private OrderService service;
	
	public OrderController(OrderService service) {
		this.service = service;
	}

	@PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@Operation(summary = "Create order", description = "Creater order for customer")
	public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) throws CustomerNotFoundException, AccountNotFoundException,  AssetNotFounException, InsufficientBalanceException {	
		Order body = service.create(request);
		OrderResponse response= OrderResponse.builder()
						.assetCode(body.getAssetCode())
						.orderSide(body.getOrderSide())
						.price(body.getPrice())
						.size(body.getSize())
						.status(body.getStatus())
						.build();					
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping("/search")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@Operation(summary = "Get customer order", description = "Get customer order detail by customer  username and in date range.example date: 2024-10-05 12:30:45")
	public ResponseEntity<List<OrderResponse>> getCustomerOrders(@RequestBody OrderSearchequest request) {
		
		List<Order> orders = service.getCustomerOrders(request.getStart(), request.getEnd(), request.getUsername());
		List<OrderResponse> responseList= new ArrayList<>();
		orders.forEach(o->{
		OrderResponse dto= OrderResponse.builder()
					 .assetCode(o.getAssetCode())
					 .orderSide(o.getOrderSide())
					 .price(o.getPrice())
					 .size(o.getSize())
					 .status(o.getStatus())
					 .build();
			
			responseList.add(dto);
		});		
		return new ResponseEntity<>(responseList, HttpStatus.OK);
	}

	@DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@Operation(summary = "Delete order", description = "Delete order with order Id. Status is updated.(Deleted)")
	public ResponseEntity<OrderResponse> deleteOrder(@RequestParam Long orderId) throws OrderNotFoundException, InvalidOrderOperationException {
		
		Order body = service.delete(orderId);
		OrderResponse response= OrderResponse.builder()
					  .assetCode(body.getAssetCode())
					  .orderSide(body.getOrderSide())
					  .price(body.getPrice())
					  .size(body.getSize())
					  .status(body.getStatus())
					  .build();
						
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}
	

	@PostMapping("/myorders")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@Operation(summary = "My orders", description = "Customers get their own orders")
	public ResponseEntity<List<OrderResponse>> getMyOrders() throws CustomerNotFoundException {

		String username = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
				.filter(Authentication::isAuthenticated).map(Authentication::getName).orElse(null);
		    
		List<Order> orders = service.getMyOrders(username);
		List<OrderResponse> responseList= new ArrayList<>();
		orders.forEach(o->{	OrderResponse dto= OrderResponse.builder()
								         .assetCode(o.getAssetCode())
										 .orderSide(o.getOrderSide())
										 .price(o.getPrice())
										 .size(o.getSize())
										 .status(o.getStatus())
										 .build();
								
							responseList.add(dto);
		});
		
		return new ResponseEntity<>(responseList, HttpStatus.OK);
	}

}
