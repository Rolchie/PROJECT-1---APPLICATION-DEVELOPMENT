package Accounts;

import Bank.Bank;

public class SavingsAccount extends Account implements Withdrawal, Deposit, FundTransfer{
    private double balance;

    public SavingsAccount(Bank BANK, String ACCOUNTNUMBER, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin) {
        super(BANK, ACCOUNTNUMBER, OWNERFNAME, OWNERLNAME, OWNEREMAIL, pin);
        this.balance = balance;
    }

    public String getAccountBalanceStatement() {
        return "Account Balance: $" + String.format("%.2f", balance);
    }

    private boolean hasEnoughBalance(double amount) {
        return balance >= amount;
    }

    private void insufficientBalance() {
        System.out.println("Insufficient balance for this transaction.");
    }

    private void adjustAccountBalance(double amount) {
        balance += amount;
        if (balance < 0.0) {
            balance = 0.0; // Reset to 0.0 if balance goes negative
        }
    }

    @Override
    public boolean withdrawal(double amount) {
        if (hasEnoughBalance(amount)) {
            adjustAccountBalance(-amount);
            return true; // Withdrawal successful
        } else {
            insufficientBalance();
            return false; // Withdrawal failed
        }
    }

    @Override
    public boolean cashDeposit(double amount) {
        if (amount > getBankAccount().getDepositLimit()) {
            System.out.println("Deposit amount exceeds bank's deposit limit.");
            return false; // Deposit failed
        }
        adjustAccountBalance(amount);
        return true; // Deposit successful
    }

    @Override
    public boolean transfer(Bank bank, Account account, double amount) throws IllegalAccountType {
        if (account instanceof CreditAccount) {
            throw new IllegalAccountType("Cannot fund transfer to a CreditAccount.");
        }
        if (hasEnoughBalance(amount)) {
            adjustAccountBalance(-amount);
            // Assuming the recipient account is a SavingsAccount
            ((SavingsAccount) account).adjustAccountBalance(amount);
            return true; // Transfer successful
        } else {
            insufficientBalance();
            return false; // Transfer failed
        }
    }

    @Override
    public boolean transfer(Account account, double amount) throws IllegalAccountType {
        if (account instanceof CreditAccount) {
            throw new IllegalAccountType("Cannot fund transfer to a CreditAccount.");
        }
        if (hasEnoughBalance(amount)) {
            adjustAccountBalance(-amount);
            // Assuming the recipient account is a SavingsAccount
            ((SavingsAccount) account).adjustAccountBalance(amount);
            return true; // Transfer successful
        } else {
            insufficientBalance();
            return false; // Transfer failed
        }
    }

    public String toString() {
        return "SavingsAccount{" +
                "Account Number='" + getAccountNumber() + '\'' +
                ", Owner='" + getOwnerFullname() + '\'' +
                ", Balance=" + String.format("%.2f", balance) +
                '}';
    }
}
