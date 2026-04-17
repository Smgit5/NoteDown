package com.suman.notedown.security;

import com.suman.notedown.entity.User;
import com.suman.notedown.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> checkUser = userRepository.findByUsername(username);
        if(checkUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found !");
        } else {
            User foundUser = checkUser.get();
            return foundUser;
        }
    }
}
