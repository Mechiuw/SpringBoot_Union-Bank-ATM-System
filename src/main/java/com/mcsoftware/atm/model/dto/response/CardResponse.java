package com.mcsoftware.atm.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CardResponse {
    private String cardNumber;
    private String pin;
    private String user;
    private String account;
}
