package com.mcsoftware.atm.service;

import com.mcsoftware.atm.model.dto.request.CardRequest;
import com.mcsoftware.atm.model.dto.response.CardResponse;
import com.mcsoftware.atm.model.dto.response.UserResponse;
import com.mcsoftware.atm.model.entity.Card;

import java.util.List;

public interface CardService {
    //CRUD SERVICE
    CardResponse create(CardRequest cardRequest);
    CardResponse getById(String id);
    List<Card> getAll();
    CardResponse update(String id,CardRequest cardRequest);
    void delete(String id);

    //BUSINESS LOGIC SERVICE
    CardResponse blockCard(CardRequest cardRequest);
    UserResponse retrieveCard(String cardId, String userId);
    CardResponse updatePin(String cardId);
}
