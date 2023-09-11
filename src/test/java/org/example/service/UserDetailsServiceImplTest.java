package org.example.service;

import org.example.entity.UserEntity;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadUserByUsernameWithEmail() {
        String email = "test@example.com";
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        assertEquals(userEntity.getLogin(), userDetails.getUsername());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testLoadUserByUsernameWithTelephone() {
        String telephone = "+123456789";
        UserEntity userEntity = new UserEntity();
        userEntity.setTelephone(telephone);

        when(userRepository.findByTelephone(telephone)).thenReturn(Optional.of(userEntity));

        UserDetails userDetails = userDetailsService.loadUserByUsername(telephone);

        assertEquals(userEntity.getLogin(), userDetails.getUsername());
        verify(userRepository, times(1)).findByTelephone(telephone);
    }

    @Test
    public void testLoadUserByUsernameWithLogin() {
        String login = "testUser";
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(login);

        when(userRepository.findByLogin(login)).thenReturn(Optional.of(userEntity));

        UserDetails userDetails = userDetailsService.loadUserByUsername(login);

        assertEquals(userEntity.getLogin(), userDetails.getUsername());
        verify(userRepository, times(1)).findByLogin(login);
    }
    @Test
    public void testLoadUserByUsernameWithNullUsername() {
        String username = null;

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));
    }


    @Test
    public void testLoadUserByUsernameNotFound() {
        String username = "nonexistentUser";

        when(userRepository.findByLogin(username)).thenReturn(Optional.empty());
        when(userRepository.findByEmail(username)).thenReturn(Optional.empty());
        when(userRepository.findByTelephone(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));
    }
}
