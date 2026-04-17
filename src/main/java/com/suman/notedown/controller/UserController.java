package com.suman.notedown.controller;

import com.suman.notedown.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/health")
    public String healthCheck() {
        return "Hey, your app is healthy !";
    }


}
