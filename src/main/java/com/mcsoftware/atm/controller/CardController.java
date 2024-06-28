package com.mcsoftware.atm.controller;

import com.mcsoftware.atm.constant.AppPath;
import com.mcsoftware.atm.constant.CardServicePath;
import com.mcsoftware.atm.model.dto.request.CardRequest;
import com.mcsoftware.atm.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.CARD)
public class CardController {
    private final CardService cardService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CardRequest cardRequest){}

    @GetMapping(AppPath.GET_BY_ID)
    public ResponseEntity<?> getById(@PathVariable String id){}

    @GetMapping(AppPath.GET_ALL)
    public ResponseEntity<?> getAll(){}

    @PutMapping(AppPath.PUT_BY_ID)
    public ResponseEntity<?> update(@PathVariable String id,@RequestBody CardRequest cardRequest){}

    @DeleteMapping(AppPath.DELETE_BY_ID)
    public ResponseEntity<?> delete(@PathVariable String id){}

    @PutMapping(CardServicePath.BLOCK_CARD)
    public ResponseEntity<?> blockCard(@PathVariable String cardId){}
    @GetMapping(CardServicePath.RETRIEVE_CARD)
    public ResponseEntity<?> retrieveCard(@PathVariable String userId){}

    @PutMapping(CardServicePath.UPDATE_PIN)
    public ResponseEntity<?> updatePin(@PathVariable String cardId, @RequestParam String newPin){}

}
