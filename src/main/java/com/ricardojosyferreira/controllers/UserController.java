package com.ricardojosyferreira.controllers;

import com.ricardojosyferreira.domain.User;
import com.ricardojosyferreira.domain.Role;
import com.ricardojosyferreira.domain.User;
import com.ricardojosyferreira.domain.dto.UserDto;
import com.ricardojosyferreira.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.ricardojosyferreira.repositories.UserRepository;
import com.ricardojosyferreira.repositories.RoleRepository;
import com.ricardojosyferreira.domain.dto.UserDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')" )
    public ResponseEntity<Void> newUser(@RequestBody UserDto dto) {

        var basicRole = roleRepository.findByName(Role.Values.BASIC.name());

        var userFromDb = userRepository.findByUsername(dto.username());
        if (userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var user = new User(dto.username(), passwordEncoder.encode(dto.password()), Set.of(basicRole));
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        var users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_BASIC') || hasAuthority('SCOPE_ADMIN')" )
    public ResponseEntity<User> getUser(@PathVariable("id") UUID id) throws InterruptedException {
        User user = userService.getUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable("id") UUID id, @RequestBody @Validated UserDto dto) throws InterruptedException {
        User user = userService.getUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable("id") UUID id) throws InterruptedException {
        User user = userService.getUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        if (user.getUsername().equalsIgnoreCase("admin")) {
            return new ResponseEntity<>("User admin can not be deleted!", HttpStatus.BAD_REQUEST);
        }
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

}