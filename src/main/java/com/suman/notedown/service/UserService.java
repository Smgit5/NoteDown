package com.suman.notedown.service;

import com.suman.notedown.dto.userDtos.UserRegisterRequestDTO;
import com.suman.notedown.dto.userDtos.UserResponseDTO;
import com.suman.notedown.dto.userDtos.UserUpdateRequestDTO;
import com.suman.notedown.entity.User;
import com.suman.notedown.enums.Role;
import com.suman.notedown.exception.ResourceNotFoundException;
import com.suman.notedown.repository.UserRepository;
import com.suman.notedown.util.UserMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    //fetch current logged-in user
    public User fetchCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            System.out.println("Inside UserService :: currentUser - Authentication is null !");
            throw new RuntimeException("Authentication is null !");
        }
        String username = (String) authentication.getPrincipal();
        User currentUser = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User Not found !"));
        return currentUser;
    }

    public UserResponseDTO viewProfile() {
        User currentUser = fetchCurrentUser();
        return userMapper.toDTO(currentUser);
    }

    public UserResponseDTO updateProfile(UserUpdateRequestDTO userUpdateRequestDTO) {
        User currentUser = fetchCurrentUser();
        userMapper.toEntity(userUpdateRequestDTO, currentUser);
        User updatedUser = userRepository.save(currentUser);
        return userMapper.toDTO(updatedUser);
    }

    public UserResponseDTO register(UserRegisterRequestDTO userRegReqDTO) {
        User user = userMapper.toEntity(userRegReqDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER); // default role = ROLE_USER, admin can grant other roles later.
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public String deleteProfile() {
        User currentUser = fetchCurrentUser();
        userRepository.delete(currentUser);
        return "Your profile was successfully deleted.";
    }
}
