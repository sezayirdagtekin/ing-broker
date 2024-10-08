package com.ing.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ing.entity.Account;
import com.ing.entity.Customer;


@Repository
public interface AccountRepository  extends JpaRepository<Account, Long> {

	Optional<Account> findByIban(String iban);

	Optional<Account>  findByCustomer(Customer customer);

}
