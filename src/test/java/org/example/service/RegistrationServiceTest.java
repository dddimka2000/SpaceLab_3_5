package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.entity.UserEntity;
import org.example.repository.UserRepository;
import org.example.service.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.mockito.Mockito.*;

public class RegistrationServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RegistrationService registrationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegistration() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("testUser");
        userEntity.setPass("password");

        registrationService.registration(userEntity);

        verify(userRepository, times(1)).save(userEntity);
    }
}
