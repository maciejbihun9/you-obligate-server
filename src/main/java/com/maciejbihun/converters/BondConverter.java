package com.maciejbihun.converters;

import com.maciejbihun.dto.BondDto;
import com.maciejbihun.models.Bond;

public class BondConverter implements GenericConverter <Bond, BondDto> {

    @Override
    public Bond createFromDto(final BondDto dto) {
        return null;
    }

    @Override
    public BondDto createFromEntity(final Bond entity) {
        return null;
    }

    @Override
    public Bond updateEntity(final Bond entity, final BondDto dto) {
        return null;
    }
}
