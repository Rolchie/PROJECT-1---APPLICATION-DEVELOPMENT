package Accounts;

import Bank.Bank;

public class CreditAccount extends Account implements Payment, Recompense {
    private double loan;

    public CreditAccount(Bank bank, String accountNumber, String ownerFname, String ownerLname, String ownerEmail, String pin) {
        super(bank, accountNumber, ownerFname, ownerLname, ownerEmail, pin);
        this.loan = loan;
    }

    /**
     * Get the loan statement of this credit account.
     * @return String loan statement.
     */
    public String getLoanStatement() {
        return "Loan Balance: " + this.loan;
    }

    /**
     * Checks if this credit account can perform additional credit transactions.
     * @param amountAdjustment The amount of credit to be adjusted.
     * @return True if the account can proceed with the credit transaction.
     */
    private boolean canCredit(double amountAdjustment) {
        return (this.loan + amountAdjustment) <= getBANK().getCreditLimit();
    }

    /**
     * Adjust the owner’s current loan. Result of adjustment cannot be less than 0.
     * @param amountAdjustment Amount to be adjusted.
     */
    private void adjustLoanAmount(double amountAdjustment) {
        loan -= amountAdjustment;
        if (loan < 0) {
            loan = 0;
        }
    }

    /**
     * Pay an amount of money to a selected account. The target account cannot be of type CreditAccount.
     * @param account Target account to pay money into.
     * @param amount Amount to pay.
     * @return True if the pay transaction was successful, false otherwise.
     * @throws IllegalAccountType If the target account is a CreditAccount.
     */
    @Override
    public boolean pay(Account account, double amount) throws IllegalAccountType {
        if (account instanceof CreditAccount) {
            throw new IllegalAccountType("Credit Accounts cannot pay into other Credit Accounts.");
        }

        if (account instanceof SavingsAccount) {
            System.out.println("Payment of $" + amount + " made to Savings Account.");

            if (!canCredit(amount)) {
                System.out.println("Payment failed: Not enough available credit.");
                return false;
            }
        }
        return false;
    }

    /**
     * Recompense some amount of money to the bank and reduce the loan value recorded in this account.
     * @param amount Amount of money to be recompensed.
     * @return True if the compensation was successful.
     */
    @Override
    public boolean recompense(double amount) {
        // Check if the amount is valid and does not exceed current credit
        if (amount > 0 && amount >= loan) {
            adjustLoanAmount(amount);
            return true; // Compensation was successful
        } else if(amount < loan  ){
            System.out.println("Invalid recompense amount! Exceeds loan amount. ");
        }
        else {
            System.out.println("Recompense amount must be positive.");
        }
        return false; // Compensation failed
    }

    @Override
    public String getAccountType() {
        return "Credit Account";
    }

    @Override
    public String toString() {
        return "CreditAccount {" +
                "Owner: " + getOwnerFullName() + ", " +
                "\nAccount Number: " + getAccountNumber() +
                "\nBank: " + getBANK().getName() +
                "\nLoan Balance: $" + String.format("%.2f", loan) +
                "\nEmail: " + getOwnerEmail() +
                "}";
    }
}