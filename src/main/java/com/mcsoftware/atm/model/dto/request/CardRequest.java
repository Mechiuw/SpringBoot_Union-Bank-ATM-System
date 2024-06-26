package com.mcsoftware.atm.model.dto.request;

import com.mcsoftware.atm.model.entity.Account;
import com.mcsoftware.atm.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CardRequest {
    private String pin;
    private User user;
    private Account account;
}
