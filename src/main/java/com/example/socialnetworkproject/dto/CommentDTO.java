package com.example.socialnetworkproject.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CommentDTO {
    private Long id;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private List<CommentDTO> replies = new ArrayList<>();
}
