package com.apptree.User;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ROLE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME", unique = true, nullable = false, length = 50)
	private String name;

	@Column(name = "DESCRIPTION", length = 100)
	private String description;
}