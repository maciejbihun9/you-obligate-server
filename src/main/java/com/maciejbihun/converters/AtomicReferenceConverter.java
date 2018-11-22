package com.maciejbihun.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Maciej Bihun
 */
@Converter
public class AtomicReferenceConverter implements AttributeConverter<AtomicReference<BigDecimal>, BigDecimal> {

    @Override
    public BigDecimal convertToDatabaseColumn(AtomicReference<BigDecimal> atomicReference) {
        return atomicReference.get();
    }

    @Override
    public AtomicReference convertToEntityAttribute(BigDecimal bigDecimal) {
        return new AtomicReference<>(bigDecimal);
    }
}
