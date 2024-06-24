package com.mcsoftware.atm.service.impl;

import com.mcsoftware.atm.model.dto.request.CardRequest;
import com.mcsoftware.atm.model.dto.response.CardResponse;
import com.mcsoftware.atm.model.dto.response.UserResponse;
import com.mcsoftware.atm.model.entity.Account;
import com.mcsoftware.atm.model.entity.Card;
import com.mcsoftware.atm.model.entity.User;
import com.mcsoftware.atm.repository.AccountRepository;
import com.mcsoftware.atm.repository.CardRepository;
import com.mcsoftware.atm.repository.UserRepository;
import com.mcsoftware.atm.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Override
    public CardResponse create(CardRequest cardRequest) {
        try {
            User user = userRepository.findById(cardRequest.getUser().getId())
                    .orElseThrow(() -> new NoSuchElementException("not found any user"));
            Account account = accountRepository.findById(cardRequest.getAccount().getId())
                    .orElseThrow(() -> new NoSuchElementException("not found any account"));

            assert user != null : "error user not found";
            assert account != null : "account user not found";

            Card card = Card.builder()
                    .cardNumber(cardRequest.getCardNumber())
                    .pin(cardRequest.getPin())
                    .user(user)
                    .account(account)
                    .build();

            Card initCard = cardRepository.save(card);
            return CardResponse.builder()
                    .id(initCard.getId())
                    .cardNumber(initCard.getCardNumber())
                    .pin(initCard.getPin())
                    .user(initCard.getUser().getId())
                    .account(initCard.getAccount().getId())
                    .build();
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public CardResponse getById(String id) {
        try {
            Card card = cardRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("not found any card"));
            if(card.getId().equals(id)){
                return CardResponse.builder()
                        .id(card.getId())
                        .cardNumber(card.getCardNumber())
                        .pin(card.getPin())
                        .user(card.getUser().getId())
                        .account(card.getAccount().getId())
                        .build();
            } else {
                return CardResponse.builder()
                        .id("not found card id")
                        .cardNumber("not found card number")
                        .pin("not found card pin")
                        .user("not found card user")
                        .account("not found any account associated to card")
                        .build();
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public CardResponse getAll() {
        return null;
    }

    @Override
    public CardResponse update(String id, CardRequest cardRequest) {
        return null;
    }

    @Override
    public CardResponse delete(String id) {
        return null;
    }

    @Override
    public CardResponse blockCard(CardRequest cardRequest) {
        return null;
    }

    @Override
    public UserResponse retrieveCard(String cardId, String userId) {
        return null;
    }

    @Override
    public CardResponse updatePin(String cardId) {
        return null;
    }
}
