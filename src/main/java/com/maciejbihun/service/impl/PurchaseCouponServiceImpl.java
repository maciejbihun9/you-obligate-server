package com.maciejbihun.service.impl;

import com.maciejbihun.models.PurchaseCoupon;
import com.maciejbihun.repository.PurchaseCouponRepository;
import com.maciejbihun.service.PurchaseCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Maciej Bihun
 */
@Service
public class PurchaseCouponServiceImpl implements PurchaseCouponService {

    @Autowired
    PurchaseCouponRepository purchaseCouponRepository;

    @Override
    public Optional<PurchaseCoupon> getPurchaseCouponById(Long id) {
        return purchaseCouponRepository.findById(id);
    }
}
