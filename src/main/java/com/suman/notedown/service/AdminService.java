package com.suman.notedown.service;

import com.suman.notedown.dto.pageDtos.PageResponseDTO;
import com.suman.notedown.dto.userDtos.UserResponseDTO;
import com.suman.notedown.dto.userDtos.UserRoleUpdateDTO;
import com.suman.notedown.dto.userDtos.UserStatusDTO;
import com.suman.notedown.entity.User;
import com.suman.notedown.exception.InvalidOperationException;
import com.suman.notedown.exception.ResourceNotFoundException;
import com.suman.notedown.repository.UserRepository;
import com.suman.notedown.util.PaginationUtility;
import com.suman.notedown.util.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        user.setRole(roleUpdateDTO.getRole());
        return userMapper.toDTO(userRepository.save(user));
    }


    public PageResponseDTO<UserResponseDTO> viewAllUsers(Pageable pageable) {
        Page<User> pageOfUsers = userRepository.findAll(pageable);
        Page<UserResponseDTO> pageOfUserResponseDtos = pageOfUsers.map(userMapper::toDTO);
        return PaginationUtility.toPageResponseDTO(pageOfUserResponseDtos);
    }

    public UserResponseDTO viewUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        return userMapper.toDTO(user);
    }

    public String modifyUserStatus(Integer id, UserStatusDTO userStatusDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        User currentUser = userService.fetchCurrentUser();
        if(currentUser.getId().equals(id)) {
            throw new InvalidOperationException("Self ban is not allowed.");
        }
        user.setEnabled(userStatusDTO.isEnabled());
        userRepository.save(user);
        String status = userStatusDTO.isEnabled() ? "unbanned" : "banned";
        return "User with id " + id + " has been " + status;
    }
}
