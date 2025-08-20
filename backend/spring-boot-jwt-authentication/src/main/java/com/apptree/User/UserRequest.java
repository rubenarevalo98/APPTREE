package com.apptree.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
	private Long id;
	private String username;
	private String firstname;
	private String lastname;
	private String country;
	private String email;
	private String roleName;
}