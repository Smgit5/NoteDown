package com.suman.notedown.service;

import com.suman.notedown.dto.userDtos.UserResponseDTO;
import com.suman.notedown.dto.userDtos.UserRoleUpdateDTO;
import com.suman.notedown.dto.userDtos.UserStatusDTO;
import com.suman.notedown.entity.User;
import com.suman.notedown.repository.UserRepository;
import com.suman.notedown.util.UserMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserService userService;
    public AdminService(UserRepository userRepository, UserMapper userMapper, UserService userService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userService = userService;
    }
    public UserResponseDTO updateRole(Integer userId, UserRoleUpdateDTO roleUpdateDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        user.setRole(roleUpdateDTO.getRole());
        return userMapper.toDTO(userRepository.save(user));
    }


    public List<UserResponseDTO> viewAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDTO).toList();
    }

    public UserResponseDTO viewUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return userMapper.toDTO(user);
    }

    public String modifyUserStatus(Integer id, UserStatusDTO userStatusDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        User currentUser = userService.fetchCurrentUser();
        if(currentUser.getId().equals(id)) {
            throw new RuntimeException("Self ban is not allowed.");
        }
        user.setEnabled(userStatusDTO.isEnabled());
        userRepository.save(user);
        String status = userStatusDTO.isEnabled() ? "unbanned" : "banned";
        return "User with id " + id + " has been " + status;
    }
}
