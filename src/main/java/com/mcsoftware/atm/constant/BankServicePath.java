package com.mcsoftware.atm.constant;

public class BankServicePath {
    public static final String LIST_ALL_BRANCH = "/all/{id}/branch";
    public static final String LIST_ALL_BRANCH_ATM = "/all/{id}/branch/atm";
    public static final String DEPOSIT_ATM = "/deposit/atm";
    public static final String WITHDRAWAL_ATM = "/withdrawal/atm";
    public static final String REQ_DEPOSIT = "/request-depo/{id}";
    public static final String REQ_WITHDRAWAL = "/request-withdraw/{id}";
}
