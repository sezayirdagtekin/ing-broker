package com.ing.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ing.entity.Customer;
import com.ing.entity.Order;
import com.ing.enums.Status;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByStatus(Status status);

	List<Order> findByCreateDateBetweenAndCustomerUserUsername(LocalDateTime startDate, LocalDateTime endDate,
			String username);

	List<Order> findByCustomer(Customer customer);

}
