package com.example.socialnetworkproject.repository;

import com.example.socialnetworkproject.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    @Query("SELECT p FROM PostEntity p WHERE p.user.id = :userId OR p.user.id IN " +
            "(SELECT f.id FROM UserEntity u JOIN u.following f WHERE u.id = :userId) " +
            "ORDER BY p.createdAt DESC")
    Page<PostEntity> getNewsfeed(@Param("userId") Long userId, Pageable pageable);
}
