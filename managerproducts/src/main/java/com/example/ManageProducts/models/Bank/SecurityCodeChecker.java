package com.example.ManageProducts.models.Bank;

public class SecurityCodeChecker {
    private int securityCode;

    public SecurityCodeChecker(int securityCode) {
        this.securityCode = securityCode;
    }

    public boolean isValid(int securityCode) {
        return this.securityCode == securityCode;
    }
}
