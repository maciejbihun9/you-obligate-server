package com.maciejbihun.controller.impl;

import com.maciejbihun.controller.MarketTransactionsController;
import com.maciejbihun.dto.BondDto;
import com.maciejbihun.dto.CouponPurchaseDataObjectDto;
import com.maciejbihun.exceptions.ThereIsNoEnoughMoneyInAnAccountException;
import com.maciejbihun.exceptions.ThereIsNoEnoughUnitsToServeInBondException;
import com.maciejbihun.models.Bond;
import com.maciejbihun.models.User;
import com.maciejbihun.models.UserPrincipal;
import com.maciejbihun.service.BondService;
import com.maciejbihun.service.MarketTransactionsService;
import com.maciejbihun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author Maciej Bihun
 */
@RestController(value = "/market-transactions")
public class MarketTransactionsControllerImpl implements MarketTransactionsController {

    @Autowired
    MarketTransactionsService marketTransactionsService;

    @Autowired
    UserService userService;

    @Autowired
    BondService bondService;

    @Override
    @PostMapping("/make-coupons-purchase")
    public ResponseEntity<Object> makeCouponsPurchase(@RequestBody CouponPurchaseDataObjectDto couponPurchaseDataObjectDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserPrincipal userPrincipal = userService.loadUserByUsername(username);
        User user = userPrincipal.getUser();

        BondDto bondDto = couponPurchaseDataObjectDto.getBond();

        // check if amount of units to buy is still available
        Optional<Bond> currentBondOptional = bondService.getBond(bondDto.getId().intValue());
        Bond currentBond = currentBondOptional.get();

        // check if bond contains enough number of units to serve
        try {
            marketTransactionsService.makeCouponPurchase(user, currentBond, couponPurchaseDataObjectDto.getAmountOfUnitsToBuy());
        } catch (ThereIsNoEnoughMoneyInAnAccountException e) {
            // return conflict status and bond with updated data
            return new ResponseEntity<>(currentBond, HttpStatus.CONFLICT);
        } catch (ThereIsNoEnoughUnitsToServeInBondException e) {
            return new ResponseEntity<>(currentBond, HttpStatus.CONFLICT);
        }
        // if everything is ok I want return just ok status
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
