package Launchers;

import Accounts.SavingsAccount;
import Accounts.Account;
import Bank.Bank;
import Main.Main;
import java.util.Scanner;

public class SavingsAccountLauncher extends AccountLauncher {
    private static SavingsAccount loggedAccount = null;
    private static AccountLauncher assocBank;
    /**
     * Initializes the Savings Account module, displaying the main menu.
     */
    public static void savingsAccountInit() {
        Main.showMenuHeader("Savings Account");
        Main.showMenu(51, 1);
        Scanner input = new Scanner(System.in);
        int option = input.nextInt();
        switch (option)
        {
            case 1: {
                System.out.println(loggedAccount.getAccountBalanceStatement());
            }
            case 2: {
                Main.showMenuHeader("Deposit");
                System.out.print("Enter Amount: ");
                double amount = input.nextDouble();
                depositProcess(amount);
            }
            case 3: {
                Main.showMenuHeader("Withdraw");
                System.out.print("Enter Amount: ");
                double amount = input.nextDouble();
                withdrawProcess(amount);
            }
            case 4: {
                Main.showMenuHeader("Fund Transfer");
                System.out.print("Enter Account Number: ");
                String AccNum = input.nextLine();
                System.out.print("Enter Amount: ");
                double amount = input.nextDouble();
                fundTransferProcess(AccNum,amount);}

            case 5: {
                getLoggedAccount().getTransactionsInfo();
            }
            case 6: BankLauncher.logout();

            default:
                System.out.println("Invalid Choice!");
        }
    }

    /**
     * Processes a deposit transaction.
     */
    public static void depositProcess(double amount) {

    }

    /**
     * Processes a withdrawal transaction.
     */
    public static void withdrawProcess(double amount) {
//        if (loggedAccount != null) {
//            SavingsAccount account = getLoggedAccount();
//
//            if (amount > 0 && account.hasEnoughBalance(amount) && amount => Bank.getWithdrawLimit()) {
//                account.withdrawal(amount);
//                System.out.println("Successfully withdrew " + amount + ". New balance: " + account.getBalance());
//            } else if (amount <= 0) {
//                System.out.println("Invalid withdrawal amount. Please enter a positive value.");
//            } else {
//                System.out.println("Insufficient balance. Current balance: " + account.getBalance());
//            }
//        } else {
//            System.out.println("No Savings Account logged in.");
//        }

    }

    /**
     * Processes a fund transfer transaction.
     */
    public static void fundTransferProcess(String targetAccountNumber, double amount) {
//        if (loggedAccount != null) {
//            SavingsAccount sourceAccount = getLoggedAccount();
//            if (amount > 0 && amount <= sourceAccount.getBalance()) {
//                // Assuming Bank has a method to find an account by number
//                SavingsAccount targetAccount = loggedAccount.getAccountNumber();
//                if (targetAccount != null) {
//                    sourceAccount.withdrawal(amount);
//                    targetAccount.cashDeposit(amount);
//                    System.out.println("Successfully transferred " + amount + " to account " + targetAccountNumber + ".");
//                } else {
//                    System.out.println("Target account not found.");
//                }
//            } else if (amount <= 0) {
//                System.out.println("Invalid transfer amount. Please enter a positive value.");
//            } else {
//                System.out.println("Insufficient balance for transfer. Current balance: " + sourceAccount.getBalance());
//            }
//        } else {
//            System.out.println("No Savings Account logged in.");
//        }
    }

    /**
     * Retrieves the currently logged-in Savings Account.
     * @return The logged-in Savings Account instance, or null if none.
     */

    protected static SavingsAccount getLoggedAccount() {
        return loggedAccount;
    }
}