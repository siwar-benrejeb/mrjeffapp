package com.mrjeffapp.provider.api;


import com.mrjeffapp.provider.api.model.LoginRequest;
import com.mrjeffapp.provider.domain.User;
import com.mrjeffapp.provider.domain.projection.LoginProjectionUser;
import com.mrjeffapp.provider.exception.AuthenticationException;
import com.mrjeffapp.provider.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private UserRepository userRepository;

	private final ProjectionFactory projectionFactory;

	@Autowired
	public UserController(UserRepository userRepository, ProjectionFactory projectionFactory) {
		this.userRepository = userRepository;
		this.projectionFactory = projectionFactory;
	}

	@PostMapping("/login")
	public LoginProjectionUser login(@Valid @RequestBody LoginRequest loginRequest) {

		Optional<User> userOptional = userRepository.findUserByEmailAndActiveTrue(loginRequest.getUsername());

		if(!userOptional.isPresent()) {
			throw new AuthenticationException("Invalid user credentials");
		}

		User user = userOptional.get();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		boolean match = encoder.matches(loginRequest.getPassword(), user.getPassword());

		if(!match) {
			throw new AuthenticationException("Invalid user credentials");
		}

		LoginProjectionUser loginProjection = projectionFactory.createProjection(LoginProjectionUser.class, user);
		return loginProjection;
	}

}
