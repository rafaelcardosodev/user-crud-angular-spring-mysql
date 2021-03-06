package com.betha.login.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betha.login.exception.ResourceNotFoundException;
import com.betha.login.model.User;
import com.betha.login.model.dao.UserDAO;
import com.betha.login.repository.UserRepository;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	UserDAO userDao = new UserDAO();
	
	//get all Users
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	//get user by id
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getuserById(@PathVariable Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User id doesn't exist: " + id));
		return ResponseEntity.ok(user);
	}
	
	//get user register
	@GetMapping("/users/register")
	public List<String> getUserRegister() {
		return userDao.getRegister();
	}
	
	//create user rest api
	@PostMapping("/users")
	public User createUser(@RequestBody User user) {
		return userRepository.save(user);
	}
	
	//update user rest api
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userInfo) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User id doesn't exist: " + id));
		user.setName(userInfo.getName());
		user.setEmail(userInfo.getEmail());
		user.setAddress(userInfo.getAddress());
		user.setPassword(userInfo.getPassword());
		user.setPhone(userInfo.getPassword());
		user.setRegister(userInfo.getRegister());
		
		User updatedUser = userRepository.save(user);
		return ResponseEntity.ok(updatedUser);
	}
	
	//delete user rest api
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User id doesn't exist: " + id));
		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
}
