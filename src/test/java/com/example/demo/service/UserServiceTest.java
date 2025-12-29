package com.example.demo.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldReturnUserWhenUserExists() {
        User user = new User(1L, "John", "john@gmail.com");

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertEquals("John", result.getName());
    }

    @Test
    void shouldCreateUserWhenEmailDoesNotExist() {
        User user = new User(null, "John", "john@example.com");

        when(userRepository.existsByEmail("john@example.com"))
                .thenReturn(false);

        when(userRepository.save(user))
                .thenReturn(user);

        User result = userService.createUser(user);

        assertNotNull(result);
        assertEquals("John", result.getName());
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        User user = new User(null, "John", "john@example.com");

        when(userRepository.existsByEmail("john@example.com"))
                .thenReturn(true);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(user);
        });

        assertNotNull(exception);
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(1L);
        });

        assertNotNull(exception);
    }

    @Test
    void shouldReturnUserById() {
        // Arrange
        User user = new User(1L, "John", "john@example.com");
        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserById(1L);

        // Assert
        assertEquals("John", result.getName());
    }

}
