package com.maciejbihun.controller;

import com.maciejbihun.models.PurchaseCoupon;
import org.springframework.http.ResponseEntity;

/**
 * @author Maciej Bihun
 */
public interface PurchaseCouponController {

    ResponseEntity<PurchaseCoupon> getPurchaseCouponById(Long id);

}
