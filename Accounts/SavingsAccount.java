package Accounts;

import Bank.Bank;

public class SavingsAccount extends Account implements Withdrawal, Deposit, FundTransfer {

    private double balance;

    public SavingsAccount(Bank bank, String accountNumber , String ownerFname, String ownerLname, String ownerEmail, String pin, double balance) {
        super(bank, accountNumber, ownerFname, ownerLname, ownerEmail, pin);
        this.balance = balance;
    }

    public double getBalance() {
        return this.balance;
    }

    /**
     * Validates whether this savings account has enough balance to proceed with a transaction.
     * @param amount Amount to be adjusted.
     * @return True if transaction can proceed, false otherwise.
     */
    private boolean hasEnoughBalance(double amount) {
        return this.balance >= amount;
    }

    /**
     * Transfers money to another savings account.
     * @param account Recipient account.
     * @param amount Amount to transfer.
     * @return True if transfer is successful.
     * @throws IllegalAccountType If recipient is a CreditAccount.
     */
    @Override
    public boolean transfer(Account account, double amount) throws IllegalAccountType {
        if (!(account instanceof SavingsAccount)) {
            throw new IllegalAccountType("Cannot transfer funds to a CreditAccount.");
        }
        if (!hasEnoughBalance(amount)) {
            insufficientBalance();
            return false;
        }
        adjustAccountBalance(-amount);
        ((SavingsAccount) account).adjustAccountBalance(amount);
        addNewTransaction(account.getAccountNumber(), Transaction.Transactions.FundTransfer, "Fund Transfer");
        return true;
    }

    /**
     * Transfers money to another bank's savings account.
     * @param bank Recipient bank.
     * @param account Recipient account.
     * @param amount Amount to transfer.
     * @return True if transfer is successful.
     * @throws IllegalAccountType If recipient is a CreditAccount.
     */
    @Override
    public boolean transfer(Bank bank, Account account, double amount) throws IllegalAccountType {
        double totalAmount = amount + getBANK().getProcessingFee();
        if (!hasEnoughBalance(totalAmount)) {
            insufficientBalance();
            return false;
        }
        return transfer(account, totalAmount);
    }


    /**
     * Deposits cash into this savings account.
     * @param amount Amount to deposit.
     * @return True if deposit is successful.
     */
    @Override
    public boolean cashDeposit(double amount) {
        if (amount > 0 && amount <= getBANK().getDepositLimit()) {
            adjustAccountBalance(amount);
            System.out.println("Successfully deposited " + amount + ". New balance: " + getBalance());
        } else if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
        } else {
            System.out.println("Deposit amount exceeds the bank's deposit limit of " + getBANK().getDepositLimit());
        }
        return false;
    }

    /**
     * Withdraws an amount from this savings account.
     * @param amount Amount to withdraw.
     * @return True if withdrawal is successful.
     */
    @Override
    public boolean withdrawal(double amount) {
        if (amount > 0 && hasEnoughBalance(amount) && amount <= getBANK().getWithdrawLimit()) {
            adjustAccountBalance(-amount);
            System.out.println("Successfully withdrew " + amount + ". New balance: " + balance);
        } else if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
        } else if(amount > getBANK().getWithdrawLimit()) {
            insufficientBalance();
        }
        else {
            System.out.println("Withdrawal amount exceeds the bank's withdrawal limit of " + getBANK().getWithdrawLimit());
        }
        return false;
    }

    /**
     * Get the account balance statement.
     * @return String balance statement.
     */
    public String getAccountBalanceStatement() {
        return "Balance: " + this.balance;
    }

    /**
     * Warns the user about insufficient balance.
     */
    private void insufficientBalance() {
        System.out.println("Insufficient balance for this transaction.");
    }

    /**
     * Adjusts the account balance.
     * @param amount Amount to be added or subtracted.
     */
    private void adjustAccountBalance(double amount) {
        balance += amount;
        if (balance < 0.0) {
            balance = 0.0;
        }
    }

    @Override
    public String getAccountType() {
        return "Savings Account";
    }

    @Override
    public String toString() {
        return "SavingsAccount {" +
                "Owner: " + getOwnerFullName() +
                "\nAccount Number: " + getAccountNumber() +
                "\nBank: " + getBANK().getName() + // Assuming Bank class has a getName() method
                "\nBalance: $" + String.format("%.2f", balance) +
                "\nEmail: " + getOwnerEmail() +
                "}";
    }
}
