package com.neu.csye6225.springdemo.service.impl;

import com.neu.csye6225.springdemo.model.User;
import com.neu.csye6225.springdemo.repository.UserRepository;
import com.neu.csye6225.springdemo.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("DomainUserDetailsServiceImpl")
public class DomainUserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(DomainUserDetailsServiceImpl.class);

    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public DomainUserDetailsServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            logger.info("User found in db with username:"+ username );
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    true, true, true, true, new ArrayList<>());
        } else {
            logger.info("User not found in db with username:"+ username );
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
