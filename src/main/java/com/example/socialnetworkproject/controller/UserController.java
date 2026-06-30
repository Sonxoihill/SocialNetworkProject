package com.example.socialnetworkproject.controller;

import com.example.socialnetworkproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/follow")
    public String follow(@RequestParam Long userId, @RequestParam Long targetUserId){
        return userService.followUser(userId, targetUserId);
    }

    @PostMapping("/unfollow")
    public String unfollow(@RequestParam Long userId, @RequestParam Long targetUserId){
        return userService.unfollowUser(userId, targetUserId);
    }
}
