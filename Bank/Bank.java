package Bank;

import java.util.Comparator;

import Launchers.BankLauncher;
import Main.Main;
import Main.Field;
import java.util.ArrayList;
import java.util.List;
import Accounts.CreditAccount;
import Accounts.SavingsAccount;
import Accounts.Account;


public class Bank {
    private final int id;
    private String name; // Add name field
    private String passcode;
    private final double depositLimit = 50000.0;
    private final double withdrawLimit = 50000.0;
    private final double creditLimit = 100000.0;
    private double processingFee = 10.0;
    private final List<Account> bankAccounts;

    // Updated constructor to accept a name
    public Bank(int id, String name, String passcode) {
        this.id = id;
        this.name = name;
        this.passcode = passcode;
        this.bankAccounts = new ArrayList<>();
    }

    //
    public Bank(int id, String name, String passcode, double depositLimit, double withdrawLimit, double creditLimit, double processingFee) {
        this.id = id;
        this.name = name;
        this.passcode = passcode;
        this.processingFee = processingFee;
        this.bankAccounts = new ArrayList<>();
    }

    /**
     * Get the deposit limit.
     *
     * @return depositLimit
     */
    public double getDepositLimit() {
        return depositLimit;
    }

    /**
     * Get the withdraw limit.
     *
     * @return withdrawLimit
     */
    public double getWithdrawLimit() {
        return withdrawLimit;
    }

    /**
     * Get the credit limit.
     *
     * @return creditLimit
     */
    public double getCreditLimit() {
        return creditLimit;
    }

    /**
     * Get the processing fee.
     *
     * @return processingFee
     */
    public double getProcessingFee() {
        return processingFee;
    }

    public int getId() {
        return this.id;
    }

    // Getter for bank name
    public String getName() {
        return this.name;
    }

    public String getPasscode() {
        return this.passcode;
    }

    /**
     * Show accounts based on type.
     *
     * @param accountType Type of account to be shown.
     */
    public <T extends Account> void showAccounts(Class<T> accountType) {
        for (Account account : bankAccounts) {
            if (accountType.isInstance(account)) {
                System.out.println(account.getAccountType() + " - " + account.getAccountNumber());
            }
        }
    }

    /**
     * Get an account object from this bank.
     *
     * @param accountNum Account number of the target account.
     * @return Account object if found, otherwise null.
     */
    public Account getBankAccount(String accountNum) {
        for (Account account : bankAccounts) {
            if (account.getAccountNumber().equals(accountNum)) {
                return account;
            }
        }
        return null;
    }

//     Creates new Account
    public ArrayList<Field<String, ?>> createNewAccount() {
        ArrayList<Field<String, ?>> createNew = new ArrayList<>();

        Field<String, String> accountNum = new Field<String, String>("accountNum", String.class, "[0-9\\-_.]+", new Field.StringFieldValidator());
        Field<String, String> fNameField = new Field<String, String>("First Name", String.class, "", new Field.StringFieldValidator());
        Field<String, String> lNameField = new Field<String, String>("Last name", String.class, "", new Field.StringFieldValidator());
        Field<String, String> emailField = new Field<String, String>("Email", String.class, "", new Field.StringFieldValidator());
        Field<String, String> pinField = new Field<String, String>("Pin", String.class, "", new Field.StringFieldValidator());

        while (true) {
            accountNum.setFieldValue("Enter account number: ");
            String accountNumVal = accountNum.getFieldValue();
            createNew.add(accountNum);
            if (accountExists(BankLauncher.getLoggedBank(), String.valueOf(accountNum))) {
                System.out.println("Account number already exist!");
                break;
            } else if (accountNumVal.isEmpty()) {
                System.out.println("Account number cannot be blank!");
                break;
            }

            fNameField.setFieldValue("Enter first name: ");
            String firstName = fNameField.getFieldValue();
            createNew.add(fNameField);
            if (firstName.isEmpty()) {
                System.out.println("First name cannot be blank!");
                break;
            }

            lNameField.setFieldValue("Enter last name: ");
            String lastName = lNameField.getFieldValue();
            createNew.add(lNameField);
            if (lastName.isEmpty()) {
                System.out.println("Last name cannot be blank!");
                break;
            }

            emailField.setFieldValue("Enter email: ");
            String email = emailField.getFieldValue();
            createNew.add(emailField);
            if (!email.matches("@.")) {
                System.out.println("Please enter a valid email!");
                break;
            }

            pinField.setFieldValue("Enter 4-digit PIN: ");
            createNew.add(pinField);

        }  return createNew;
    }


    /**
     * Create a new credit account.
     *
     * @return New CreditAccount object.
     */
    public CreditAccount createNewCreditAccount() {
        ArrayList<Field<String, ?>> fields = createNewAccount();
        Bank bank = BankLauncher.getLoggedBank();
        CreditAccount credit;

        String accountNum = fields.get(0).getFieldValue();
        String firstName = fields.get(1).getFieldValue();
        String lastName = fields.get(2).getFieldValue();
        String email = fields.get(3).getFieldValue();
        String pin = fields.get(4).getFieldValue();

        credit = new CreditAccount(bank, accountNum, firstName, lastName, email, pin);
        addNewAccount(credit);
        return credit;
    }

    /**
     * Create a new savings account.
     *
     * @return New SavingsAccount object.
     */
    public SavingsAccount createNewSavingsAccount() {
        ArrayList<Field<String, ?>> fields = createNewAccount();
        Bank bank = BankLauncher.getLoggedBank();
        SavingsAccount savings;

        String accountNum = fields.get(0).getFieldValue();
        String firstName = fields.get(1).getFieldValue();
        String lastName = fields.get(2).getFieldValue();
        String email = fields.get(3).getFieldValue();
        String pin = fields.get(4).getFieldValue();


        while (true) {
            Field<Double, Double> balField = new Field<Double, Double>("Credit", Double.class, 100.00, new Field.DoubleFieldValidator());
            balField.setFieldValue("Enter deposit amount: ", true);
            if (balField.getFieldValue() <= getDepositLimit()) {
                double balance = balField.getFieldValue();
                savings = new SavingsAccount(bank, accountNum, firstName, lastName, email, pin, balance);
                addNewAccount(savings);
                return savings;
            } else {
                System.out.println("Deposit must be less than " + getDepositLimit());
            }
        }
    }

    /**
     * Adds a new account to this bank.
     *
     * @param account Account object to be added.
     */
    public void addNewAccount(Account account) {
        // Check if the account number already exists
        for (Account existingAccount : bankAccounts) {
            if (existingAccount.getAccountNumber().equals(account.getAccountNumber())) {
                System.out.println("Account number already exists! Cannot add the account.");
            }
        }

        // If the account number is unique, add the account
        bankAccounts.add(account);
        System.out.println("Account added successfully!");
    }


    /**
     * Checks if an account exists in this bank.
     *
     * @param accountNum Account number to check.
     * @return True if account exists, false otherwise.
     */
    public static boolean accountExists(Bank bank, String accountNum) {
        if (bank == null || accountNum == null || accountNum.isEmpty()) {
            return false; // Return false if the bank is null or account number is invalid
        }

        // Check if the account exists in the bank
        return BankLauncher.findAccount(accountNum) != null; // Returns true if account exists, false if not
    }

    @Override
    public String toString() {
        return "Bank ID: " + getId()
                + "Name: " + getName();
    }

    public static class BankComparator implements Comparator<Bank> {
        @Override
        public int compare(Bank b1, Bank b2) {
            return b1.getName().compareTo(b2.getName());
        }
    }

    public static class BankIdComparator implements Comparator<Bank> {
        @Override
        public int compare(Bank b1, Bank b2) {
            return Integer.compare(b1.getId(), b2.getId());
        }
    }

    public static class BankCredentialsComparator implements Comparator<Bank> {
        @Override
        public int compare(Bank b1, Bank b2) {
            // Compare IDs first
            if (b1.getId() < b2.getId()) {
                return -1;
            }
            if (b1.getId() > b2.getId()) {
                return 1;
            }

            // Compare names if IDs are equal
            if (b1.getName().compareTo(b2.getName()) < 0) {
                return -1;
            }
            if (b1.getName().compareTo(b2.getName()) > 0) {
                return 1;
            }

            // Compare passcodes if names are also equal
            if (b1.passcode.compareTo(b2.passcode) < 0) {
                return -1;
            }
            if (b1.passcode.compareTo(b2.passcode) > 0) {
                return 1;
            }

            return 1;
        }
    }
}


