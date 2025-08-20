package com.apptree.User;

import java.util.Optional;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	@Transactional
	public UserResponse updateUser(UserRequest userRequest) {

		User user = userRepository.findById(userRequest.getId())
				.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: id=" + userRequest.getId()));

		if (userRequest.getFirstname() != null)
			user.setFirstname(userRequest.getFirstname());
		if (userRequest.getLastname() != null)
			user.setLastname(userRequest.getLastname());
		if (userRequest.getCountry() != null)
			user.setCountry(userRequest.getCountry());
		if (userRequest.getUsername() != null)
			user.setUsername(userRequest.getUsername());
		if (userRequest.getEmail() != null)
			user.setEmail(userRequest.getEmail());
		String roleName = Optional.ofNullable(userRequest.getRoleName()).orElse(null);
		if (roleName != null && !roleName.isBlank()) {
			Role role = roleRepository.findByName(roleName.toLowerCase())
					.orElseThrow(() -> new IllegalArgumentException("Rol inexistente: " + roleName));
			user.setRole(role);
		}
		userRepository.save(user);

		return new UserResponse("El usuario se actualizó satisfactoriamente");
	}

	public UserDTO getUser(Long id) {
		return userRepository.findById(id).map(this::toDTO).orElse(null);
	}

	private UserDTO toDTO(User user) {
		return UserDTO.builder().id(user.getId()).username(user.getUsername()).firstname(user.getFirstname())
				.lastname(user.getLastname()).country(user.getCountry()).email(user.getEmail())
				.roleName(user.getRole() != null ? user.getRole().getName() : null)
				.active(user.getActive() != null ? user.getActive() : 0).createdAt(user.getCreatedAt()).build();
	}
}
