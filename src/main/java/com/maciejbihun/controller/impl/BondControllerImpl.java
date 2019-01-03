package com.maciejbihun.controller.impl;

import com.maciejbihun.controller.BondController;
import com.maciejbihun.converters.BondConverter;
import com.maciejbihun.dto.BondDto;
import com.maciejbihun.exceptions.AmountOfUnitsExceededException;
import com.maciejbihun.models.Bond;
import com.maciejbihun.service.BondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maciej Bihun
 */
@Controller
public class BondControllerImpl implements BondController {

    @Autowired
    BondService bondService;

    /**
     * Returns created bond with CREATED status if everything went ok.
     */
    @Override
    public ResponseEntity<BondDto> createBondInObligationStrategy(BondDto bondDto) {
        try {
            Bond bondInObligationStrategy = bondService.createBondInObligationStrategy(bondDto.getObligationStrategyId(),
                    bondDto.getAmountOfUnitsToPay());
            return new ResponseEntity<>(BondConverter.convertToDto(bondInObligationStrategy), HttpStatus.CREATED);
        } catch (Exception e) {
            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
            multiValueMap.set("info", e.getMessage());
            if (e instanceof AmountOfUnitsExceededException){
                return new ResponseEntity<>(multiValueMap, HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(multiValueMap, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<BondDto>> getObligationGroupBonds(int obligationGroupId) {
        List<BondDto> obligationGroupBondsDtos = new ArrayList<>();
        List<Bond> obligationGroupBonds = bondService.getObligationGroupBonds(obligationGroupId);
        obligationGroupBonds.forEach(bond -> {
            obligationGroupBondsDtos.add(BondConverter.convertToDto(bond));
        });
        return new ResponseEntity<>(obligationGroupBondsDtos, HttpStatus.FOUND);
    }

}
