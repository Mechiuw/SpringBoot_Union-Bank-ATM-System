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

import java.util.Collections;
import java.util.List;
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
    public List<Card> getAll() {
        try {
            List<Card> cards = cardRepository.findAll();
            if (!cards.isEmpty()) {
                return cards;
            } else {
                System.err.println("not found any cards");
                return Collections.emptyList();
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public CardResponse update(String id, CardRequest cardRequest) {
        try {
            Card card = cardRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("not found card"));
            if (card.getId().equals(id)) {
                card.setCardNumber(cardRequest.getCardNumber());
                card.setPin(cardRequest.getPin());
                card.setUser(cardRequest.getUser());
                card.setAccount(cardRequest.getAccount());

                Card updatedCard = cardRepository.saveAndFlush(card);

                return CardResponse.builder()
                        .id(updatedCard.getId())
                        .cardNumber(updatedCard.getCardNumber())
                        .pin(updatedCard.getPin())
                        .user(updatedCard.getUser().getId())
                        .account(updatedCard.getAccount().getId())
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
    public void delete(String id) {
        try {
            Card card = cardRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("not found any card"));
            cardRepository.delete(card);
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public CardResponse blockCard(String cardId) {
        try {
            Card card = cardRepository.findById(cardId)
                    .orElseThrow(() -> new NoSuchElementException("not found any card"));
            if(card != null) {
                card.setCardNumber("BLOCKED");
                card.setPin("BLOCKED");
                Card updatedCard = cardRepository.saveAndFlush(card);
                return CardResponse.builder()
                        .id(updatedCard.getId())
                        .cardNumber(updatedCard.getCardNumber())
                        .pin(updatedCard.getPin())
                        .user(updatedCard.getUser().getId())
                        .account(updatedCard.getAccount().getId())
                        .build();
            } else {
                return CardResponse.builder()
                        .id("not found card id")
                        .cardNumber("not found card number")
                        .build();
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Card> retrieveCard(String userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow();
            if (user.getCardList() != null && !user.getCardList().isEmpty()) {
                return user.getCardList();
            } else {
                return Collections.emptyList();
            }
        } catch (NoSuchElementException e){
            System.err.println(e.getMessage());
            throw e;
        } catch (Exception e){
            System.err.println("Error retrieved when searching cards "+e.getMessage());
            throw e;
        }
    }

    @Override
    public CardResponse updatePin(String cardId,CardRequest cardRequest) {
        try {
            Card card = cardRepository.findById(cardId)
                    .orElseThrow(() -> new NoSuchElementException("not found any card"));
            if (card.getPin() != null) {
                card.setPin(cardRequest.getPin());
                Card updatedPinCard = cardRepository.saveAndFlush(card);
                return CardResponse.builder()
                        .id(updatedPinCard.getId())
                        .cardNumber(updatedPinCard.getCardNumber())
                        .pin(updatedPinCard.getPin())
                        .build();
            } else {
                return CardResponse.builder()
                        .id("not found card id")
                        .cardNumber("not found card number")
                        .pin("not updated card pin")
                        .build();
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
        }
    }
}
