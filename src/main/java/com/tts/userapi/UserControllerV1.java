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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;


@Api(description="User API Version 1.0")
@RestController
@RequestMapping("/v1")
public class UserControllerV1 {
	
	@Autowired
	UserRepository repository;
	
	//put in front of mapings to put them on the swagger ui
	@ApiOperation(value = "Get all user items", response = User.class, 
            responseContainer = "List")
	@ApiResponses(value= {
			@ApiResponse(code=200, message="Succefully accessed data."),
			@ApiResponse(code=401, message="Unauthorized to view user data"),
			@ApiResponse(code=403, message="Forbidden access to user data"),
			@ApiResponse(code=404, message="Bad request, please try again with correct command"),
	})
	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers(@RequestParam(value="state", required=false)String state){
		if(state== null) {
			List<User> users = repository.findAll();
			return new ResponseEntity<>(users, HttpStatus.OK);
		}
		List<User> users=  repository.findByState(state);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Get user by id number", response = User.class, 
            responseContainer = "List")
	@ApiResponses(value= {
			@ApiResponse(code=200, message="Succefully accessed user data."),
			@ApiResponse(code=401, message="Unauthorized to view user data"),
			@ApiResponse(code=403, message="Forbidden access to user data"),
			@ApiResponse(code=404, message="Bad request, please try again with correct command"),
	})
	@GetMapping("/users/{id}")
	public ResponseEntity<Optional<User>> getUserById(@PathVariable(value="id") Long id){
		Optional<User> users =repository.findById(id);
		if(!users.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Add a user", response = User.class, 
            responseContainer = "List")
	@ApiResponses(value= {
			@ApiResponse(code=200, message="Succefully added user data."),
			@ApiResponse(code=401, message="Unauthorized to add user data"),
			@ApiResponse(code=403, message="Forbidden mutation to user data"),
			@ApiResponse(code=404, message="Bad request, please try again with correct command"),
	})
	@PostMapping("/users")
	public ResponseEntity<Void> createUser(@Valid @RequestBody User user){
		if(repository.findById(user.getId()).isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		repository.save(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Update a user", response = User.class, 
            responseContainer = "List")
	@ApiResponses(value= {
			@ApiResponse(code=200, message="Succefully updated user data."),
			@ApiResponse(code=401, message="Unauthorized to update user data"),
			@ApiResponse(code=403, message="Forbidden mutation to user data"),
			@ApiResponse(code=404, message="Bad request, please try again with correct command"),
	})
	@PutMapping("/users/{id}")
	public ResponseEntity<Void> updateUser(@Valid @PathVariable(value="id") Long id, @RequestBody User user){
		if(repository.findById(user.getId()) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		repository.save(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "Delete a user", response = User.class, 
            responseContainer = "List")
	@ApiResponses(value= {
			@ApiResponse(code=200, message="Succefully deleted user data."),
			@ApiResponse(code=401, message="Unauthorized to delete user data"),
			@ApiResponse(code=403, message="Forbidden mutation to user data"),
			@ApiResponse(code=404, message="Bad request, please try again with correct command"),
	})
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deleteUser(@Valid @PathVariable(value="id") Long id){
		if(repository.findById(id).isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		repository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
