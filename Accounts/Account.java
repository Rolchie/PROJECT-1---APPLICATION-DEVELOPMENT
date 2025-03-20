package Accounts;

import java.util.ArrayList;
import Bank.Bank;

public abstract class Account {

    private final Bank bank;
    private final String accountNumber;
    private final String ownerFname;
    private final String ownerLname;
    private final String ownerEmail;
    private String pin;
    private final ArrayList<Transaction> transactions;

    // Add pin attribute
    public Account(Bank bank, String accountNumber, String ownerFname, String ownerLname, String ownerEmail, String pin) {
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.ownerFname = ownerFname;
        this.ownerLname = ownerLname;
        this.ownerEmail = ownerEmail;
        this.pin = pin;
        this.transactions = new ArrayList<>();
    }

    // Get Owner Full Name
    public String getOwnerFullName() {
        return this.ownerLname + ", " + this.ownerFname;
    }

    // Getter for pin
    public String getPin() {
        return this.pin;
    }

    // Get Account number
    public String getAccountNumber() {
        return accountNumber;
    }

    // Getter for bank
    public Bank getBANK() {
        return this.bank;
    }

    // Getter for owner email
    public String getOwnerEmail() {
        return this.ownerEmail;
    }

    /**
     * Add a new transaction log to this account.
     * @param account Account number of source account that triggered this transaction.
     * @param type Type of transaction triggered.
     * @param description Description of the transaction.
     */
    public void addNewTransaction(String account, Transaction.Transactions type, String description) {
        transactions.add(new Transaction(account, type, description));
    }

    /**
     * Get all information for every transaction that has been logged into this account.
     * @return List of transactions in string format.
     */
    public String getTransactionsInfo() {
        StringBuilder transactionInfo = new StringBuilder();
        for (Transaction transaction : transactions) {
            transactionInfo.append("Account: ").append(transaction.accountNumber)
                    .append(", Type: ").append(transaction.transactionType)
                    .append(", Description: ").append(transaction.description)
                    .append("\n");
        }
        return transactionInfo.toString();
    }

    // Abstract method to be implemented by subclasses
    public abstract String getAccountType();


    public String toString(String description) {
        return "Account: " + getAccountNumber()
                + "\nType: " + getAccountType()
                + "\nDescription" + description;
    }
}
