package com.maciejbihun.repository;

import com.maciejbihun.models.PurchaseCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseCouponRepository extends JpaRepository<PurchaseCoupon, Long> {
}
