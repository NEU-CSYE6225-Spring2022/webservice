package com.neu.csye6225.springdemo.service;

import com.neu.csye6225.springdemo.model.User;
import com.neu.csye6225.springdemo.repository.UserRepository;
import com.neu.csye6225.springdemo.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserServiceImpl userServiceImpl;

    private User user;

    @Before
    public void setUp() {
        initMocks(this);
        userServiceImpl = new UserServiceImpl(userRepository,
                bCryptPasswordEncoder);
        user = new User("satish","bingi","bingis@gmail.com","1234567890");

        Mockito.when(userRepository.save(any()))
                .thenReturn(user);
        Mockito.when(userRepository.findByUsername("bingis@gmail.com"))
                .thenReturn(user);
    }

    @Test
    public void testFindUserByUsername() {
        final String email = "bingis@gmail.com";
        final User result = userRepository.findByUsername(email);
        assertEquals(email, result.getUsername());
        assertNotEquals("manoj@gmail.com",result.getUsername());
    }

    @Test
    public void testSaveUser() {
        final String email = "bingis@gmail.com";
        User result = userRepository.save(new User());
        assertEquals(email, result.getUsername());
    }

}
