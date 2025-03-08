package Accounts;

import Bank.Bank;
import java.util.ArrayList;

public abstract class Account {
    private Bank BANK;
    private String ACCOUNTNUMBER;
    private String OWNERFNAME;
    private String OWNERLNAME;
    private String OWNEREMAIL;
    private String pin;
    private ArrayList<Transaction>TRANSACTIONS;

    public Account(Bank BANK, String ACCOUNTNUMBER, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin) {
        this.BANK = BANK;
        this.ACCOUNTNUMBER = ACCOUNTNUMBER;
        this.OWNERFNAME = OWNERFNAME;
        this.OWNERLNAME = OWNERLNAME;
        this.OWNEREMAIL = OWNEREMAIL;
        this.pin = pin;
    }

    public String getOwnerFullname() {
        return this.OWNERLNAME + ", " + this.OWNERFNAME;
    }

    public void addNewTrancsaction(String accountNum, Transaction.Transactions type, String description) {
        Transaction transaction = new Transaction(accountNum, type, description);
        TRANSACTIONS.add(transaction);
    }

    public String getTransactionsInfo() {
        StringBuilder info = new StringBuilder();
        for (Transaction transaction : TRANSACTIONS) {
            info.append(transaction.toString()).append("\n");
        }
        return info.toString();
    }

    public String getAccountNumber() {
        return this.ACCOUNTNUMBER;
    }

    @Override
    public String toString() {
        return "Account{" +
                "BANK=" + BANK +
                ", ACCOUNTNUMBER='" + ACCOUNTNUMBER + '\'' +
                ", OWNERFNAME='" + OWNERFNAME + '\'' +
                ", OWNERLNAME='" + OWNERLNAME + '\'' +
                ", OWNEREMAIL='" + OWNEREMAIL + '\'' +
                ", TRANSACTIONS=" + TRANSACTIONS +
                '}';
    }
}
