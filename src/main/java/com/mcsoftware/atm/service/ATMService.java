package com.mcsoftware.atm.service;

import com.mcsoftware.atm.model.dto.request.ATMRequest;
import com.mcsoftware.atm.model.dto.response.ATMResponse;
import com.mcsoftware.atm.model.entity.ATM;

import java.util.List;

public interface ATMService {
    ATMResponse create(ATMRequest atmRequest);
    ATMResponse getById(String id);
    List<ATM> getAll();
    ATMResponse update(String id, ATMRequest atmRequest);
    void delete(String id);
    ATMResponse checkCashBalance(String id);
}
