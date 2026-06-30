package com.example.socialnetworkproject.controller;

import com.example.socialnetworkproject.entity.PostEntity;
import com.example.socialnetworkproject.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/like")
    public String likePost(@RequestParam Long postId,
                           @RequestParam Long userId) {
        return postService.toggleLikePost(postId, userId);
    }

    @GetMapping("/user/{userId}/newsfeed")
    public Page<PostEntity> getNews(@PathVariable Long userId,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "5") int size){
        return postService.getTimeLine(userId, page, size);
    }
}
