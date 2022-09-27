package com.tts.userapi;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@Autowired
	UserRepository repository;
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers(@RequestParam(value="state", required=false)String state){
		if(state== null) {
			List<User> users = repository.findAll();
			return new ResponseEntity<>(users, HttpStatus.OK);
		}
		List<User> users=  repository.findByState(state);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<Optional<User>> getUserById(@PathVariable(value="id") Long id){
		Optional<User> users =repository.findById(id);
		if(!users.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@PostMapping("/users")
	public ResponseEntity<Void> createUser(@Valid @RequestBody User user){
		if(repository.findById(user.getId()).isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		repository.save(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<Void>  updateUser(@Valid @PathVariable(value="id") Long id, @RequestBody User user){
		if(repository.findById(user.getId()) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		repository.save(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deleteUser(@Valid @PathVariable(value="id") Long id){
		if(repository.findById(id).isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		repository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
