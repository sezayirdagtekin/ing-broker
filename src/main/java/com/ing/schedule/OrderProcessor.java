package com.ing.schedule;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ing.entity.Order;
import com.ing.enums.Status;
import com.ing.exception.AssetNotFounException;
import com.ing.repository.OrderRepository;
import com.ing.service.AssetService;

import jakarta.transaction.Transactional;

@Service
public class OrderProcessor {

    static final Random RANDOM = new Random();
	static final Logger log= LoggerFactory.getLogger(OrderProcessor.class);
	
	private final OrderRepository repository;
	private final  AssetService  assetService;

	public OrderProcessor(OrderRepository repository, AssetService assetService) {
		this.repository = repository;
		this.assetService = assetService;
	}



	@Scheduled(fixedRate = 15000)
	@Transactional
	public void processOrders() throws AssetNotFounException {
		var orders = repository.findByStatus(Status.PENDING);
		for (Order order : orders) {
			log.info("Processing orders:{} ",order.getAssetCode());
			if(RANDOM.nextBoolean()) { //  lets say randomly match
				log.info("Order {} matched at price:{}",order.getAssetCode(),order.getPrice());
			
			    order.setStatus(Status.MATCHED);
			  repository.save(order);
			  assetService.updateAsset(order.getAssetCode(), order.getOrderSide(), order.getSize());
			}
			else {
				log.info("Order  {}  status still {}",  order.getAssetCode(),order.getStatus().name());
			}
			
		}

	}
	
	

}