package com.example.socialnetworkproject.repository;

import com.example.socialnetworkproject.entity.UserEntity;
import com.example.socialnetworkproject.entity.VoucherHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherHistoryRepository extends JpaRepository<VoucherHistoryEntity,Long> {
    boolean existsByUserIdAndVoucherId(Long userId, Long voucherId);
    UserEntity findUserById(Long userId);

}
