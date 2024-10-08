package com.ing.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.stereotype.Service;

import com.ing.entity.Account;
import com.ing.entity.Asset;
import com.ing.entity.Customer;
import com.ing.entity.Order;
import com.ing.enums.Status;
import com.ing.exception.AssetNotFounException;
import com.ing.exception.CustomerNotFoundException;
import com.ing.exception.InsufficientBalanceException;
import com.ing.exception.InvalidOrderOperationException;
import com.ing.exception.OrderNotFoundException;
import com.ing.repository.OrderRepository;
import com.ing.request.OrderRequest;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

	private final OrderRepository repository;

	private final  AssetService  assetService;
	private final CustomerService customerService;
	private final AccountService accountService;

	
	public OrderService(OrderRepository repository, AssetService assetService, CustomerService customerService, AccountService accountService) {
		
		this.repository = repository;
		this.assetService = assetService;
		this.customerService = customerService;
		this.accountService = accountService;
	}

	@Transactional
	public Order create(OrderRequest request) throws CustomerNotFoundException, AccountNotFoundException, AssetNotFounException ,InsufficientBalanceException{
		
		Customer customer=customerService.findByUsername(request.getCustomerUsername());
		
		Account account = accountService.findByCustomer(customer);
		
		BigDecimal total = request.getPrice().multiply(new BigDecimal(request.getSize()));

		if (account.getBalance().compareTo(total) < 0) {
			throw new InsufficientBalanceException("Your balance is insufficient. Please deposit money. Current balance:" + account.getBalance());
		}

		Asset asset = assetService.findAssetByAssetCode(request.getAssetCode());
		Order order = Order.builder()
				     .assetCode(asset.getAssetCode())
				     .customer(customer)
				     .orderSide(request.getOrderSide())
				     .price(request.getPrice())
				     .size(request.getSize())
				     .createDate(LocalDateTime.now())
				     .status(Status.PENDING)
				     .build();
		
		return repository.save(order);
	}

	
	@Transactional
	public Order delete(Long id) throws OrderNotFoundException, InvalidOrderOperationException {

		Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found"));
		
		if(!order.getStatus().equals(Status.PENDING)) {
			throw new InvalidOrderOperationException("Only order with status PENDING can be cancelled.");
		}

		order.setStatus(Status.CANCELED);

		return repository.save(order);
	}

	public List<Order>  getCustomerOrders( LocalDateTime startDate,LocalDateTime endDate,String username) {

		  return repository.findByCreateDateBetweenAndCustomerUserUsername(startDate, endDate, username);
	}

	public List<Order> getMyOrders(String username) throws CustomerNotFoundException {
		return repository.findByCustomer(customerService.findByUsername(username));
	}

}
