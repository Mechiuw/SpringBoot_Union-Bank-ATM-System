package com.mcsoftware.atm.controller;

import com.mcsoftware.atm.constant.AppPath;
import com.mcsoftware.atm.constant.CardServicePath;
import com.mcsoftware.atm.model.dto.request.CardRequest;
import com.mcsoftware.atm.model.dto.response.CardResponse;
import com.mcsoftware.atm.model.dto.response.CommonResponse;
import com.mcsoftware.atm.model.entity.Card;
import com.mcsoftware.atm.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.CARD)
public class CardController {
    private final CardService cardService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CardRequest cardRequest){
        CardResponse cardResponse = cardService.create(cardRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("successfully created card")
                        .data(cardResponse)
                        .build()
        );
    }

    @GetMapping(AppPath.GET_BY_ID)
    public ResponseEntity<?> getById(@PathVariable String id){
        CardResponse cardResponse = cardService.getById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("successfully fetch card")
                        .data(cardResponse)
                        .build()
        );
    }

    @GetMapping(AppPath.GET_ALL)
    public ResponseEntity<?> getAll(){
        List<Card> cardList = cardService.getAll();
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("successfully fetch all cards")
                        .data(cardList)
                        .build()
        );
    }

    @PutMapping(AppPath.PUT_BY_ID)
    public ResponseEntity<?> update(@PathVariable String id,@RequestBody CardRequest cardRequest){
        CardResponse cardResponse = cardService.update(id,cardRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("successfully updated card")
                        .data(cardResponse)
                        .build()
        );
    }

    @DeleteMapping(AppPath.DELETE_BY_ID)
    public void delete(@PathVariable String id){
        cardService.delete(id);
        ResponseEntity.ok();
    }

    @PutMapping(CardServicePath.BLOCK_CARD)
    public ResponseEntity<?> blockCard(@PathVariable String cardId){
        CardResponse cardResponse = cardService.blockCard(cardId);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("successfully blocked card")
                        .data(cardResponse)
                        .build()
        );
    }
    @GetMapping(CardServicePath.RETRIEVE_CARD)
    public ResponseEntity<?> retrieveCard(@PathVariable String userId){
        List<Card> cards = cardService.retrieveCard(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("successfully retrieve all card for specific user")
                        .data(cards)
                        .build()
        );
    }

    @PutMapping(CardServicePath.UPDATE_PIN)
    public ResponseEntity<?> updatePin(@PathVariable String cardId, @RequestParam String newPin){
        CardResponse cardResponse = cardService.updatePin(cardId,newPin);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("successfully updated card pin")
                        .data(cardResponse)
                        .build()
        );
    }

}
