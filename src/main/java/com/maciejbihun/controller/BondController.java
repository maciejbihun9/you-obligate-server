package com.maciejbihun.controller;

import com.maciejbihun.dto.BondDto;
import com.maciejbihun.models.Bond;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author Maciej Bihun
 */
public interface BondController {

    ResponseEntity<BondDto> createBondInObligationStrategy(BondDto bondDto);
    ResponseEntity<List<BondDto>> getObligationGroupBonds(int obligationGroupId);
    ResponseEntity<BondDto> getBond(int bondId);

}
