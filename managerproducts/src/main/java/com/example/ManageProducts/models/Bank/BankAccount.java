package com.example.ManageProducts.models.Bank;

public class BankAccount {
    private String accountName;
    private int securityCode;

    AccountChecker accountChecker;
    SecurityCodeChecker securityCodeChecker;
    CashManager cashManager;

    public BankAccount(String accountName, int securityCode) {
        this.accountName = accountName;
        this.securityCode = securityCode;

        accountChecker = new AccountChecker(accountName);
        securityCodeChecker = new SecurityCodeChecker(securityCode);
        cashManager = new CashManager(1000.00);
    }

    public void deposit(double amount) {
        if (accountChecker.isValid(accountName) && securityCodeChecker.isValid(securityCode)) {
            cashManager.deposit(amount);
            System.out.println("deposit suscess!");
        } else {
            System.out.println("deposit fail!");
        }
    }

    public void withdraw(double amount) {
        if (accountChecker.isValid(accountName) && securityCodeChecker.isValid(securityCode)
                && cashManager.haveEnoughMoney(amount)) {
            cashManager.withdraw(amount);
            System.out.println("withdraw suscess!");
        } else {
            System.out.println("withdraw fail!");
        }
    }
}
