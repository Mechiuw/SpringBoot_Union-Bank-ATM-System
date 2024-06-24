package com.mcsoftware.atm.service;

import com.mcsoftware.atm.model.dto.request.CardRequest;
import com.mcsoftware.atm.model.dto.response.CardResponse;

public interface CardService {
    //CRUD SERVICE
    CardResponse create(CardRequest cardRequest);
    CardResponse getById(String id);
    CardResponse getAll();
    CardResponse update(String id,CardRequest cardRequest);
    CardResponse delete(String id);

    //BUSINESS LOGIC SERVICE
    CardResponse blockCard(CardRequest cardRequest);
    CardResponse retrieveCard(String cardId, String userId);
}
