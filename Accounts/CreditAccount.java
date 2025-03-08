package Accounts;

import Bank.Bank;

public class CreditAccount extends Account{
    private double loan;

    public CreditAccount(Bank BANK, String ACCOUNTNUMBER, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin) {
        super(BANK, ACCOUNTNUMBER, OWNERFNAME, OWNERLNAME, OWNEREMAIL, pin);
        this.loan = loan;
    }

    public String getLoanStatement() {
        //pass
    }

    private boolean canCredit(double amountAdjustment) {
        //pass
    }

    private void adjustLoanAmount(double amountAdjustment) {
        //pass
    }

    public String toString() {
        //pass
    }
}