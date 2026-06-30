package com.example.socialnetworkproject.service;

import com.example.socialnetworkproject.entity.PostEntity;
import com.example.socialnetworkproject.entity.UserEntity;
import com.example.socialnetworkproject.repository.PostRepository;
import com.example.socialnetworkproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public String toggleLikePost(Long postId, Long userId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Bai viet khong ton tai"));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Nguoi dung nay khong ton tai"));

        if(post.getLikeUsers().contains(user)) {
            post.getLikeUsers().remove(user);
            postRepository.save(post);
            return "Da huy thich bai viet";
        }  else {
            post.getLikeUsers().add(user);
            postRepository.save(post);
            return "Da thich bai viet thanh cong";
        }
    }

    public Page<PostEntity> getTimeLine(Long userId, int page, int size) {
        Pageable pageable = (Pageable) PageRequest.of(page, size);

        Page<PostEntity> postEntities = postRepository.getNewsfeed(userId, pageable);
        return postEntities;
    }
}
