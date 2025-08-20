package com.apptree.Auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apptree.Jwt.JwtService;
import com.apptree.User.Role;
import com.apptree.User.RoleRepository;
import com.apptree.User.User;
import com.apptree.User.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	/**
	 * Login con username o email (el campo 'username' del request actúa como login
	 * genérico).
	 */
	public AuthResponse login(LoginRequest request) {
		String login = request.getUsername(); // puede ser username o email
		String rawPassword = request.getPassword();

		// Autentica (DaoAuthenticationProvider usará tu UserDetailsService)
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, rawPassword));

		// Carga el usuario por username o, si no existe, por email
		UserDetails user = userRepository.findByUsername(login).map(u -> (UserDetails) u)
				.orElseGet(() -> userRepository.findByEmail(login).map(u -> (UserDetails) u)
						.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado")));

		String token = jwtService.getToken(user);

		return AuthResponse.builder().token(token).build();
	}

	@Transactional
	public AuthResponse register(RegisterRequest request) {
		userRepository.findByUsername(request.getUsername()).ifPresent(u -> {
			throw new IllegalArgumentException("Nombre de usuario ya existe");
		});
		userRepository.findByEmail(request.getEmail()).ifPresent(u -> {
			throw new IllegalArgumentException("Email ya existe");
		});

		Role roleUser = roleRepository.findByName("user")
				.orElseThrow(() -> new IllegalStateException("Rol 'user' no encontrado en la tabla ROLE"));

		User user = User.builder().username(request.getUsername()).email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword())).firstname(request.getFirstname())
				.lastname(request.getLastname()).country(request.getCountry()).role(roleUser).active(1)
				.createdAt(LocalDateTime.now()).build();

		userRepository.save(user);

		return AuthResponse.builder().token(jwtService.getToken(user)).build();
	}
}
