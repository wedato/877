package com.example.projetsem2qrcode.service;

import com.example.projetsem2qrcode.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserServiceImpl underTest;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private LoginAttemptService loginAttemptService;
    @Mock
    private EmailService emailService;


    @BeforeEach
    void setUp() {
        underTest = new UserServiceImpl(userRepository,passwordEncoder,loginAttemptService,emailService);
    }


}