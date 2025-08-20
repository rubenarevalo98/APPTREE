package com.apptree.User;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4200" })
public class UserController {

	private final UserService userService;

	@GetMapping("{id}")
	public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
		UserDTO userDTO = userService.getUser(id);
		if (userDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(userDTO);
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponse> updateUser(@Validated @RequestBody UserRequest userRequest) {
		try {
			return ResponseEntity.ok(userService.updateUser(userRequest));
		} catch (IllegalArgumentException ex) {
			return ResponseEntity.notFound().build();
		}
	}
}