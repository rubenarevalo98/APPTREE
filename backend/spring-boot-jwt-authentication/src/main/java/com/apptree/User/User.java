package com.apptree.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS", uniqueConstraints = { @UniqueConstraint(name = "UK_USERS_USERNAME", columnNames = "USERNAME"),
		@UniqueConstraint(name = "UK_USERS_EMAIL", columnNames = "EMAIL") })
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Basic
	@Column(name = "USERNAME", nullable = false, length = 50)
	private String username;

	@Column(name = "EMAIL", nullable = false, length = 100)
	private String email;

	@Column(name = "PASSWORD", nullable = false, length = 255)
	private String password;

	@Column(name = "FIRSTNAME", length = 50)
	private String firstname;

	@Column(name = "LASTNAME", length = 50)
	private String lastname;

	@Column(name = "COUNTRY", length = 60)
	private String country;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")
	private Role role;

	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt;

	@Column(name = "ACTIVE")
	private Integer active; // 0/1

	@PrePersist
	public void prePersist() {
		if (this.createdAt == null)
			this.createdAt = LocalDateTime.now();
		if (this.active == null)
			this.active = 1;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (role == null || role.getName() == null) {
			return List.of();
		}
		String authority = "ROLE_" + role.getName().toUpperCase();
		return List.of(new SimpleGrantedAuthority(authority));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return active != null && active == 1;
	}
}