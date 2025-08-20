package com.apptree.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private Long id;
	private String username;
	private String firstname;
	private String lastname;
	private String country;
	private String email;
	private String roleName;
	private Integer active;
	private LocalDateTime createdAt;
}