package Bank;

import java.util.ArrayList;
import Accounts.Account;
import Accounts.CreditAccount;
import Accounts.SavingsAccount;
import java.lang.reflect.Field;

public class Bank {
    private int ID;
    private String name;
    private String passcode;
    private double DEPOSITLIMIT;
    private double WITHDRAWLIMIT;
    private double CREDITLIMIT;
    private double processingFee;
    private ArrayList<Account>BANKACCOUNTS;

    public Bank(int ID, String name, String passcode) {
        this.ID = ID;
        this.name = name;
        this.passcode;
    }

    public Bank(int ID, String name, String passcode, double DEPOSITLIMIT, double WITHDRAWLIMIT, double CREDITLIMIT, double processingFee) {
    this.ID = ID;
    this.name = name;
    this.passcode = passcode;
    this.DEPOSITLIMIT = DEPOSITLIMIT;
    this.WITHDRAWLIMIT = WITHDRAWLIMIT;
    this.CREDITLIMIT = CREDITLIMIT;
    this.processingFee = processingFee;
    }

    public void<T> showAccounts(Class<T>accountType) {
        //pass
    }

    public Account getBankAccount(Bank bank, String accountNum) {
        //pass
    }

    public ArrayList<Field<String>> createNewAccount() {
        //pass
    }

    public CreditAccount createNewCreditAccount() {
        //pass
    }

    public SavingsAccount createNewSavingsAccount() {
        //pass
    }

    public void addNewAccount(Account account) {
        //pass
    }

    public static boolean accountExists(Bank bank, String accountNum) {
        //pass
    }

    public String toString() {
        //pass
    }


}
