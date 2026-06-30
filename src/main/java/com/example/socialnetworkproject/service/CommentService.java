package com.example.socialnetworkproject.service;

import com.example.socialnetworkproject.dto.CommentDTO;
import com.example.socialnetworkproject.entity.CommentEntity;
import com.example.socialnetworkproject.entity.PostEntity;
import com.example.socialnetworkproject.entity.UserEntity;
import com.example.socialnetworkproject.repository.CommentRepository;
import com.example.socialnetworkproject.repository.PostRepository;
import com.example.socialnetworkproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public CommentEntity createComment(Long postId, Long userId, String content, Long parentCommentId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Bai viet khong ton tai"));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Nguoi dung khong ton tai"));

        CommentEntity comment = new CommentEntity();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());

        if(parentCommentId != null){
            CommentEntity commentEntity = commentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new RuntimeException("Binh luan cha khong ton tai"));
            comment.setParent(commentEntity);
        }

        return commentRepository.save(comment);
    }

    public List<CommentDTO> getCommentTreeByPostId(Long postId){
        List<CommentEntity> allComments = commentRepository.findByPostIdOrderByCreatedAtAsc(postId);

        Map<Long, CommentDTO> dtoMap = new HashMap<>();
        List<CommentDTO> rootComments = new ArrayList<>();

        for(CommentEntity commentEntity : allComments){
            CommentDTO dto = new CommentDTO();
            dto.setId(commentEntity.getId());
            dto.setContent(commentEntity.getContent());
            dto.setUsername(commentEntity.getUser().getUsername());
            dto.setCreatedAt(commentEntity.getCreatedAt());

            dtoMap.put(commentEntity.getId(), dto);
        }

        for(CommentEntity entity : allComments){
            CommentDTO currentDTO = dtoMap.get(entity.getId());

            if(entity.getParent() == null){
                rootComments.add(currentDTO);
            }
            else{
                Long parentId = entity.getParent().getId();
                CommentDTO parentDTO = dtoMap.get(parentId);
                parentDTO.getReplies().add(currentDTO);
            }
        }
        return rootComments;
    }
}
