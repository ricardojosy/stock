package com.ricardojosyferreira.controllers;

import com.ricardojosyferreira.domain.User;
import com.ricardojosyferreira.domain.dto.UserDto;
import com.ricardojosyferreira.repositories.RoleRepository;
import com.ricardojosyferreira.repositories.UserRepository;
import com.ricardojosyferreira.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserService userService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testNewUser_Success() {
        // Mocking input data
        UserDto userDto = new UserDto("username", "password");

        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("hashedPassword");
        when(userRepository.save(any())).thenReturn(new User());

    }

    @Test
    public void testNewUser_UserAlreadyExists() {
        UserDto userDto = new UserDto("existingUsername", "password");

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User()));

        assertThrows(ResponseStatusException.class, () -> userController.newUser(userDto));
        verify(userRepository, never()).save(any());
    }

    @Test
    public void testGetUser_Success() throws InterruptedException {
        UUID userId = UUID.randomUUID();
        User mockUser = new User("username", "hashedPassword", Set.of());

        when(userService.getUser(userId)).thenReturn(mockUser);

        ResponseEntity<User> response = userController.getUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser, response.getBody());
    }

    @Test
    public void testGetUser_NotFound() throws InterruptedException {
        UUID userId = UUID.randomUUID();

        when(userService.getUser(userId)).thenReturn(null);

        ResponseEntity<User> response = userController.getUser(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdateUser_Success() throws InterruptedException {
        UUID userId = UUID.randomUUID();
        UserDto userDto = new UserDto("newUsername", "newPassword");
        User mockUser = new User("username", "hashedPassword", Set.of());

        when(userService.getUser(userId)).thenReturn(mockUser);
        when(passwordEncoder.encode(any())).thenReturn("newHashedPassword");
        when(userService.updateUser(any())).thenReturn(mockUser);

        ResponseEntity<User> response = userController.updateUser(userId, userDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("newUsername", response.getBody().getUsername());
    }

    @Test
    public void testDeleteUser_Success() throws InterruptedException {
        UUID userId = UUID.randomUUID();
        User mockUser = new User("username", "hashedPassword", Set.of());

        when(userService.getUser(userId)).thenReturn(mockUser);
        doNothing().when(userService).deleteUserById(userId);

        ResponseEntity<String> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
