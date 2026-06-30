package com.example.socialnetworkproject.service;

import com.example.socialnetworkproject.entity.VoucherEntity;
import com.example.socialnetworkproject.entity.VoucherHistoryEntity;
import com.example.socialnetworkproject.repository.VoucherHistoryRepository;
import com.example.socialnetworkproject.repository.VoucherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VoucherService {
    @Autowired
    private VoucherHistoryRepository voucherHistoryRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Transactional
    public double applyVoucher(String code, Long userId, double currentOrderPrice){
        VoucherEntity voucher = voucherRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Ma giam gia khong hop le"));

        LocalDateTime now = LocalDateTime.now();
        if(now.isBefore(voucher.getStartDate()) || now.isAfter(voucher.getEndDate())){
            throw new RuntimeException("Voucher da het han hoac chua den gio su dung");
        }
        if(voucher.getUsedCount() >= voucher.getMaxUsage()){
            throw new RuntimeException("Ma giam gia da het luot su dung");
        }
        if(currentOrderPrice < voucher.getMinOrderValue()){
            throw new RuntimeException("Don hang chua dat gia tri toi thieu de ap dung ma");
        }
        if(voucherHistoryRepository.existsByUserIdAndVoucherId(userId, voucher.getId())){
            throw new RuntimeException("Ban da dung ma giam gia nay roi");
        }

        voucher.setUsedCount(voucher.getUsedCount() + 1);
        voucherRepository.save(voucher);

        VoucherHistoryEntity  voucherHistory = new VoucherHistoryEntity();
        voucherHistory.setUser(voucherHistoryRepository.findUserById(userId));
        voucherHistory.setVoucher(voucher);
        voucherHistory.setUsedAt(now);
        voucherHistoryRepository.save(voucherHistory);

        return voucher.getDiscountAmount();
    }
}
