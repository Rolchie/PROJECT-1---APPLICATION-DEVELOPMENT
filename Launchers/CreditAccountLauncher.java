package Launchers;

import Accounts.Account;
import Accounts.CreditAccount;
import Bank.Bank;
import Main.Main;

import java.util.Scanner;

public class CreditAccountLauncher extends AccountLauncher {
    private static CreditAccount loggedAccount = null;
    private static AccountLauncher assocBank;

    /**
     * Initializes the Credit Account module, displaying the main menu.
     */
    public static void creditAccountInit() {
        Main.showMenuHeader("Credit Account");
        Main.showMenu(41, 1);
        Main.setOption();
        Main.showMenu(Main.getOption());

        switch(Main.getOption()) {
            case 1: {
                System.out.println(loggedAccount.getLoanStatement());
            }
            case 2: {
                creditPaymentProcess();
            }
            case 3: {
                creditRecompenseProcess();
            }
            case 4: {
                System.out.println(loggedAccount.getTransactionsInfo());
            }
            case 5: {
                BankLauncher.logout();
            }
            default:
                System.out.println("Invalid Choice!");
        }

    }

    /**
     * Processes a credit payment transaction.
     */
    public static void creditPaymentProcess() {

    }

    /**
     * Processes a credit recompense transaction.
     */
    public static void creditRecompenseProcess() {
        if (isLoggedIn() && getLoggedAccount() instanceof CreditAccount) {
            System.out.println("Processing Credit Recompense Transaction...");
            // Implement recompense logic
        } else {
            System.out.println("No Credit Account logged in.");
        }
    }

    /**
     * Retrieves the currently logged-in Credit Account.
     * @return The logged-in Credit Account instance, or null if none.
     */

    protected static CreditAccount getLoggedAccount() {
        return loggedAccount;
    }
}
