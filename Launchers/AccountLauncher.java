package Launchers;

import Accounts.IllegalAccountType;
import Bank.Bank;
import Accounts.CreditAccount;
import Accounts.SavingsAccount;
import Accounts.Account;
import java.util.Scanner;
import Main.Main;
import Main.Field;

public class AccountLauncher {
    protected static Account loggedAccount; // Changed from private to protected
    protected static Bank assocBank;

    public AccountLauncher() {
        loggedAccount = null;
        assocBank = null;
    }

    /**
     * Verifies if some account is currently logged in.
     *
     * @return True if an account is logged in, false otherwise.
     */
    static boolean isLoggedIn() {
        return loggedAccount != null;
    }

    /**
     * Handles the account login process.
     * Bank must be selected first before logging in.
     */
    public static void accountLogin() {
        while (assocBank == null) {
            assocBank = selectBank();
            if (assocBank == null) {
                System.out.println("Invalid bank selection. Please try again.");
            }
        }

        //Login
        int tries = 0;
        login:

        while (true) {
            Main.showMenuHeader("Account Login");
            String accNum = Main.prompt("Enter Account Number: ", true);
            Account check = BankLauncher.findAccount(accNum);
            if (check != null) {
                int pinTry = 0;
                while (true) {
                    String pin = Main.prompt("Enter 4-digit PIN: ", true);
                    if (pinTry == 2) {
                        System.out.println("Too many unsuccessful attempts!\n");
                        break login;
                    }
                    if (check.getPin().equals(pin)) {
                        Account Acc = checkCredentials(accNum, pin);
                        if (Acc != null) {
                            setLogSession(loggedAccount);
                            loggedAccount = BankLauncher.findAccount(accNum);
                            if (loggedAccount.getClass() == CreditAccount.class) {
                                CreditAccountLauncher.accountLogin();
                                System.out.println("Login successful!\n");
                                CreditAccountLauncher.creditAccountInit();
                                return;
                            } else if (loggedAccount.getClass() == SavingsAccount.class) {
                                SavingsAccountLauncher.accountLogin();
                                System.out.println("Login successful!\n");
                                SavingsAccountLauncher.savingsAccountInit();
                                return;
                            }
                        }
                    } else {
                        System.out.println("Incorrect Pin\n");
                        pinTry++;
                    }
                }
            }
            else if(tries == 2) {
                System.out.println("Too many unsuccessful attempts!\n");
                break login;
            } else {
                System.out.println("Account not Found!\n");
                tries++;
            }
        }
    }


        /**
         * Selects a bank before prompting the user to login.
         */
        private static Bank selectBank () {
            BankLauncher.showBanksMenu();
            Field<Integer, Integer> bankID = new Field<Integer,Integer>("ID", Integer.class, -1, new Field.IntegerFieldValidator());
            Field<String, String> bankName = new Field<String,String>("Name", String.class, "", new Field.StringFieldValidator());
            bankID.setFieldValue("Enter Bank ID to select: ");
            bankName.setFieldValue("Enter bank name: ");
            boolean bankFound = false; // Variable to track if the bank ID is found

            for (Bank bank : BankLauncher.getBanks()) {
                if (bank.getId() == bankID.getFieldValue() && bank.getName().equals(bankName.getFieldValue())) {
                    System.out.println("Bank selected: " + bankName.getFieldValue());
                    return bank;
                }
            }
            return null;
        }



    /**
     * Creates a login session for the logged-in account.
     * @param account The account that has successfully logged in.
     */
    private static void setLogSession(Account account) {
        if (account != null) {
            loggedAccount = account;
            System.out.println("User " + account.getOwnerFullName() + " has successfully logged in.");
        } else {
            System.out.println("Error: Account cannot be null.");
        }
    }

    /**
     * Destroys the login session for the previously logged-in account.
     */
    private static void destroyLogSession() {
        if (getLoggedAccount() != null) {
            System.out.println("Destroying session for user: " + getLoggedAccount());
            loggedAccount = null;
            System.out.println("User has been logged out.");
        } else {
            System.out.println("No active user session to destroy.");
        }
    }

    /**
     * Checks inputted credentials during account login.
     * @param accountNum Account number.
     * @param pin 4-digit pin.
     * @return Account object if credentials match, null otherwise.
     */
    public static Account checkCredentials(String accountNum, String pin) {
        if(assocBank != null) {
            Account account = assocBank.getBankAccount(accountNum);
            if(accountNum.equals(loggedAccount.getAccountNumber()) && pin.length() == 4 && pin.matches("\\d{4}")) {
                return account;
            }
        }
        return null;
    }

    protected static Account getLoggedAccount() {
        return loggedAccount;
    }
}
