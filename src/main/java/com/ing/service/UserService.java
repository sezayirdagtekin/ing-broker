package com.ing.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ing.entity.Authorization;
import com.ing.entity.User;
import com.ing.repository.AuthorizationRepository;
import com.ing.repository.UserRepository;
import com.ing.request.UserRequest;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	 static Logger log= LoggerFactory.getLogger(UserService.class);
	 
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final  AuthorizationRepository authorizationRepository;


	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,AuthorizationRepository authorizationRepository) {

		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authorizationRepository = authorizationRepository;
	}

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Transactional
	public User create(UserRequest request) {
		 List<Authorization> roles= new ArrayList<>();
		 Authorization authorization= new Authorization();
		 authorization.setAuth("ROLE_USER");
		 roles.add(authorization);
		
		 
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));//Encode password
		user.setPassword(request.getPassword());
		user.setName(request.getName());
		user.setSurname(request.getSurname());
		user.setEmail(request.getEmail());
		user.setPhone(request.getPhone());
		user.setEnabled(true);
		user.setAuthorizations(roles);
		 
		User savedUser=userRepository.save(user);
		
		authorization.setUser(savedUser);
		authorizationRepository.save(authorization);
		
		log.info("user {}  {} create  with role {}",user.getName(),user.getSurname() ,user.getAuthorizations());
		return savedUser;
		
	}

	public List<User> getAllUsers() {

		return userRepository.findAll();
	}

}
