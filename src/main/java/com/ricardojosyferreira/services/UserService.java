package com.ricardojosyferreira.services;

import com.ricardojosyferreira.domain.User;
import com.ricardojosyferreira.repositories.RoleRepository;
import com.ricardojosyferreira.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	public List<User> getUsers() {
		return userRepository.findAll();
	}

	public User getUser(UUID id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return user.get();
	}

	public User updateUser(User user) {
		return userRepository.save(user);
	}

	public void deleteUserById(UUID id) {
		userRepository.deleteById(id);
	}
}
