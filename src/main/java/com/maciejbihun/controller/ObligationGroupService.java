package com.maciejbihun.controller;

import com.maciejbihun.models.ObligationGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

public interface ObligationGroupService {

    ObligationGroup saveObligationGroup(ObligationGroup obligationGroup);
    ResponseEntity<ObligationGroup> loadObligationGroupById(Long id);

}
