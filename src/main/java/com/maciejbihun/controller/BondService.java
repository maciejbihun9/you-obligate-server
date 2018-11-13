package com.maciejbihun.controller;

import com.maciejbihun.dto.BondDto;
import com.maciejbihun.models.Bond;
import org.springframework.http.ResponseEntity;

public interface BondService {

    ResponseEntity<Bond> save(BondDto bondDto);

}
