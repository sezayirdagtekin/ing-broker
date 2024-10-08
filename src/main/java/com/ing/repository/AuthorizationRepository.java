package com.ing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ing.entity.Authorization;


@Repository
public interface AuthorizationRepository  extends JpaRepository<Authorization, Long> {



}
