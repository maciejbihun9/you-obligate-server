package com.maciejbihun.converters;

import com.maciejbihun.dto.BondDto;
import com.maciejbihun.dto.ObligationGroupDto;
import com.maciejbihun.models.Bond;
import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.models.RegisteredServiceObligationStrategy;

public class BondConverter  {

    public static BondDto convertToDto(Bond bond){
        return new BondDto(bond.getAmountOfUnitsToPay(), bond.getObligationStrategy().getId());
    }

    public static Bond convertToEntity(BondDto bondDto){
        return null;
    }

}
