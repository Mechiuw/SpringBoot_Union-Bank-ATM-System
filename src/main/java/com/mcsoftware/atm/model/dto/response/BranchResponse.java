package com.mcsoftware.atm.model.dto.response;

import com.mcsoftware.atm.model.entity.ATM;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BranchResponse {
    private String id;
    private String name;
    private String location;
    private String bank;
    private List<ATM> atmList;
}
