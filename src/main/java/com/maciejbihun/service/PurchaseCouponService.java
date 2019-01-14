package com.maciejbihun.service;

import com.maciejbihun.models.PurchaseCoupon;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Maciej Bihun
 */
public interface PurchaseCouponService {

    Optional<PurchaseCoupon> getPurchaseCouponById(Long id);

}
