package com.example.socialnetworkproject.service;


import com.example.socialnetworkproject.entity.UserEntity;
import com.example.socialnetworkproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public String followUser(Long userId, Long targetUserId) {
        if (targetUserId.equals(userId)) {
            throw new RuntimeException("Ban khong the theo doi chinh minh");
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Khong tim thay nguoi dung"));

        UserEntity targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Khong tim thay nguoi dung muon theo doi"));

        if (user.getFollowing().contains(targetUser)) {
            throw new RuntimeException("Ban da theo doi nguoi nay roi");
        }
        user.getFollowing().add(targetUser);

        userRepository.save(user);
        return "Theo doi thanh cong";
    }

    @Transactional
    public String unfollowUser(Long userId, Long targetUserId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Khong tim thay nguoi dung"));

        UserEntity targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Khong tim thay nguoi dung muon huy theo doi"));

        if (!user.getFollowing().contains(targetUser)) {
            throw new RuntimeException("Ban chua theo doi nguoi nay");
        }
        user.getFollowing().remove(targetUser);

        userRepository.save(user);
        return "Huy theo doi thanh cong";
    }
}
