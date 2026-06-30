package com.example.socialnetworkproject.controller;

import com.example.socialnetworkproject.dto.CommentDTO;
import com.example.socialnetworkproject.entity.CommentEntity;
import com.example.socialnetworkproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public CommentEntity createComment(@RequestParam Long postId,
                                       @RequestParam Long userId,
                                       @RequestParam String content,
                                       @RequestParam(required = false) Long parentCommentId) {
        return commentService.createComment(postId, userId, content, parentCommentId);
    }

    @GetMapping("/post/{postId}")
    public List<CommentDTO> getCommentTree(@PathVariable Long postId){
        return commentService.getCommentTreeByPostId(postId);
    }
}
