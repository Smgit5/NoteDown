package com.suman.notedown.controller;

import com.suman.notedown.entity.Note;
import com.suman.notedown.entity.User;
import com.suman.notedown.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "Hey, your app is healthy !";
    }


}
