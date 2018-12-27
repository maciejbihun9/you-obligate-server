package com.maciejbihun.converters;

import com.maciejbihun.dto.BondDto;
import com.maciejbihun.models.Bond;

public class BondConverter  {

    public static BondDto convertToDto(Bond bond){
        return new BondDto(bond.getNumberOfUnitsToServe(), bond.getRegisteredServiceObligationStrategy().getId());
    }

    public static Bond convertToEntity(BondDto bondDto){
        return null;
    }

}
