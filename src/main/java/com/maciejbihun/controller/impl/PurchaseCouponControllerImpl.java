package com.maciejbihun.controller.impl;

import com.maciejbihun.controller.PurchaseCouponController;
import com.maciejbihun.models.PurchaseCoupon;
import com.maciejbihun.service.PurchaseCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author Maciej Bihun
 */
@RestController(value = "/purchase-coupons")
public class PurchaseCouponControllerImpl implements PurchaseCouponController {

    @Autowired
    PurchaseCouponService purchaseCouponService;

    @Override
    @GetMapping("/{purchaseCouponId}")
    public ResponseEntity<PurchaseCoupon> getPurchaseCouponById(@PathVariable("purchaseCouponId") Long purchaseCouponId) {
        Optional<PurchaseCoupon> optionalPurchaseCoupon = purchaseCouponService.getPurchaseCouponById(purchaseCouponId);
        return optionalPurchaseCoupon.map(purchaseCoupon -> new ResponseEntity<>(purchaseCoupon, HttpStatus.FOUND))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
