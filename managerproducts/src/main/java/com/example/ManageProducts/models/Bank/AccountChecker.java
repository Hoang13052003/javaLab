package com.example.ManageProducts.models.Bank;

public class AccountChecker {
    private String accountName;

    public AccountChecker(String accountName) {
        this.accountName = accountName;
    }

    public boolean isValid(String accountName) {
        return this.accountName.equals(accountName);
    }
}
