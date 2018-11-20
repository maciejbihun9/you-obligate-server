package com.maciejbihun.controller;

import com.maciejbihun.dto.BondDto;
import com.maciejbihun.models.Bond;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BondController {

    ResponseEntity<Bond> createBondInObligationGroup(BondDto bondDto);
    ResponseEntity<String> subtractObligationUnitFromBond(Long bondId);

}
