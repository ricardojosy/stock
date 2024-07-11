package com.ricardojosyferreira.services;

import com.ricardojosyferreira.domain.Role;
import com.ricardojosyferreira.domain.User;
import com.ricardojosyferreira.repositories.RoleRepository;
import com.ricardojosyferreira.repositories.UserRepository;
import com.ricardojosyferreira.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUsers() {
        List<User> userList = new ArrayList<>();
        Role role1 = new Role(1L, Role.Values.ADMIN.name());
        Role role2 = new Role(2L, Role.Values.BASIC.name());
        Set<Role> roles = new HashSet<>();
        roles.add(role1);
        roles.add(role2);
        userList.add(new User("user1", "xpto", roles));
        userList.add(new User("user2", "xpto", roles));

        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getUsers();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetUser_Success() {
        UUID userId = UUID.randomUUID();
        Role role1 = new Role(1L, Role.Values.ADMIN.name());
        Role role2 = new Role(2L, Role.Values.BASIC.name());
        Set<Role> roles = new HashSet<>();
        roles.add(role1);
        roles.add(role2);
        User user = new User(userId,"user1", "xpto", roles);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUser(userId);

        assertEquals(user.getId(), result.getId());
    }

    @Test
    public void testGetUser_NotFound() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> userService.getUser(userId));
    }

    @Test
    public void testUpdateUser() {
        UUID userId = UUID.randomUUID();
        Role role1 = new Role(1L, Role.Values.ADMIN.name());
        Role role2 = new Role(2L, Role.Values.BASIC.name());
        Set<Role> roles = new HashSet<>();
        roles.add(role1);
        roles.add(role2);
        User user = new User(userId,"user1", "xpto", roles);

        when(userRepository.save(any())).thenReturn(user);

        User updatedUser = userService.updateUser(user);

        assertEquals(user.getId(), updatedUser.getId());
    }

    @Test
    public void testDeleteUserById() {
        UUID userId = UUID.randomUUID();

        userService.deleteUserById(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    // Você pode adicionar mais testes conforme necessário para outros métodos

}
