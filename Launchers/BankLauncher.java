package Launchers;

import Accounts.Account;
import Accounts.CreditAccount;
import Accounts.SavingsAccount;
import Bank.Bank;
import Main.Main;
import java.util.*;
import Main.Field;

public class BankLauncher {
    private static ArrayList<Bank> banks = new ArrayList<>();
    private static Bank loggedBank;

    public BankLauncher() {
        banks = new ArrayList<>();
        loggedBank = null;
    }

    public static boolean isLogged() {
        return loggedBank == null;
    }

    public static Bank getLoggedBank() {
        return loggedBank;
    }

    public static ArrayList<Bank> getBanks() {
        return banks;
    }

    /**
     * Bank interaction initialization. Utilized only when logged in.
     */
    public static void bankInit() {
        Bank bank = BankLauncher.loggedBank;
        while(true) {
            if (bank != null) {
                System.out.println("Initializing bank interaction for user: " + bank.getId());
                Main.showMenuHeader("My " + bank.getName() + " Accounts");
                Main.showMenu(31,1);
                Main.setOption();

                switch (Main.getOption()) {
                    case 1: {
                        showAccounts();
                    }
                    case 2: {
                        newAccounts();
                    }
                    case 3: {
                        logout();
                    }
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else{
                System.out.println("No Bank is currently logged in.");
            }
        }
    }

    /**
     * Show accounts registered in the currently logged-in bank.
     */
    private static void showAccounts() {
        if (BankLauncher.isLogged()) {
            System.out.println("No bank is currently logged in.");
            return; // Exit the method if no accounts are present
        }

        Bank bank = BankLauncher.loggedBank;

        if (banks == null) {
            // If no accounts are present in the logged-in bank
            System.out.println("No accounts available in the bank.");
            return;
        }

        while (true) {
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    Main.showMenuHeader("Displaying Credit Accounts");
                    bank.showAccounts(CreditAccount.class);
                    break;
                case 2:
                    Main.showMenuHeader("Displaying Savings Accounts");
                    bank.showAccounts(SavingsAccount.class);
                    break;
                case 3:
                    Main.showMenuHeader("Displaying All Accounts");
                    bank.showAccounts(Account.class);
                case 4:
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    /**
     * Bank interaction when creating new accounts for the currently logged-in bank.
     */
    public static void newAccounts() {
        if (BankLauncher.isLogged()) {
            System.out.println("No bank is currently logged in.");
            return; // Exit the method if no accounts are present
        }

        Bank bank = BankLauncher.loggedBank;

        if (banks == null) {
            // If no accounts are present in the logged-in bank
            System.out.println("No accounts available in the bank.");
            return;
        }
        switch (Main.getOption()) {
            case 1:
                Main.showMenuHeader("New Credit Account");
                bank.createNewCreditAccount();
                break;
            case 2: Main.showMenuHeader("New Savings Account");
                bank.createNewSavingsAccount();
                break;
            default:
                System.out.println("Invalid choice!");

        }
    }

    /**
     * Handles bank login process.
     */
    public static void bankLogin() {
        switch (Main.getOption()) {
            case 1:
                if (banks == null) {
                    System.out.println("No banks registered. Please create a bank first.");
                    return;
                }

                String Id = Main.prompt("Enter Bank ID: ", true);
                String name = Main.prompt("Enter Bank Name: ", true);
                String passcode = Main.prompt("Enter PIN: ", true);

                int ID = Integer.parseInt(Id);
                for (Bank bank : banks) {
                    //Must use getBank()
                    if (bank.getId() == ID && bank.getName().equals(name) && bank.getPasscode().equals(passcode)) {
                        setLogSession(bank);
                        bankInit();
                        break;
                    }
                    System.out.println("Invalid ID or passcode or Name. Please try again.");
                    break;
                }
                break;
            case 2:
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                break;
        }
    }

    /**
     * Creates a new login session for the logged-in bank user.
     * @param b Bank to set as logged in.
     */
    public static void setLogSession(Bank b) {
        loggedBank = b;
    }

    /**
     * Logs out the current bank session.
     */
    public static void logout() {
        loggedBank = null;
        System.out.println("Bank session logged out.");
    }

    /**
     * Creates a new bank and adds it to the list.
     */
    public static void createNewBank() throws NumberFormatException{
        Field<Integer, Integer> idField = new Field<Integer, Integer>("ID", Integer.class, -1, new Field.IntegerFieldValidator());
        Field<String, String> nameField = new Field<String, String>("Name", String.class, "", new Field.StringFieldValidator());
        Field<String, Integer> passcodeField = new Field<String, Integer>("Passcode", String.class, 4, new Field.StringFieldLengthValidator());
        Field<Double, Double> depositLimitField = new Field<Double, Double>("Deposit Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
        Field<Double, Double> withdrawLimitField = new Field<Double, Double>("Withdraw Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
        Field<Double, Double> creditLimitField = new Field<Double, Double>("Credit Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
        Field<Double, Double> processingFeeField = new Field<Double, Double>("Processing Fee", Double.class, 0.0, new Field.DoubleFieldValidator());

        idField.setFieldValue("Enter Bank ID: ");

        Bank.BankIdComparator idComparator = new Bank.BankIdComparator();
        for (Bank bank : banks) {
            if (idComparator.compare(bank, new Bank(idField.getFieldValue(), "", "")) == 0) {
                System.out.println("Error: A bank with ID " + idField.getFieldValue() + " already exists.");
                return;
            }
        }
        nameField.setFieldValue("Enter Bank Name: ");

        int passcodeAttempts = 0;
        while (passcodeAttempts < 3) {
            passcodeField.setFieldValue("Enter Passcode: ");
            if (passcodeField.getFieldValue().length() == 4){
                break;
            } else {
                passcodeAttempts++;
                System.out.println("Invalid passcode! Enter a 4 digit passcode.");
                if (passcodeAttempts == 3) {
                    System.out.println("Too many invalid attempts! Bank creation canceled.");
                    return;
                }
            }
        }

        depositLimitField.setFieldValue("Enter Deposit Limit, 0 if set to default: ");
        withdrawLimitField.setFieldValue("Enter Withdraw Limit, 0 if set to default: ");
        creditLimitField.setFieldValue("Enter Credit Limit, 0 if set to default: ");
        processingFeeField.setFieldValue("Enter Processing Fee, 0 if set to default: ");

        if (depositLimitField.getFieldValue() == 0 &&
                withdrawLimitField.getFieldValue() == 0 &&
                creditLimitField.getFieldValue() == 0 &&
                processingFeeField.getFieldValue() == 0) {

            Bank newBank = new Bank(
                    idField.getFieldValue(),
                    nameField.getFieldValue(),
                    passcodeField.getFieldValue()
            );
            addBank(newBank);
        } else {
            Bank newBank = new Bank(
                    idField.getFieldValue(),
                    nameField.getFieldValue(),
                    passcodeField.getFieldValue(),
                    depositLimitField.getFieldValue(),
                    withdrawLimitField.getFieldValue(),
                    creditLimitField.getFieldValue(),
                    processingFeeField.getFieldValue()
            );
            addBank(newBank);
        }
        System.out.println("Bank created successfully!");
    }

    /**
     * Outputs a menu of all registered banks in this session.
     */
    public static void showBanksMenu() {
        if (banks == null){
            System.out.println("No banks available. Please create a bank first.");
        } else {
            Main.showMenuHeader("Registered Banks");
            for (Bank bank : banks) {
                System.out.println(bank.getName() + " - " + bank.getId());
            }
        }
    }

    /**
     * Adds a new bank to the list of banks.
     * @param b Bank to be added.
     */
    public static void addBank(Bank b) {
        if (getBank(new Bank.BankIdComparator(), b) != null) {
            System.out.println("A bank with this ID already exists.");
            return;
        }
        banks.add(b);
    }

    /**
     * Checks if a bank exists based on a given comparator.
     * @param bankComparator Criteria for searching.
     * @param bank Bank object to compare.
     * @return Bank object if it matches the criteria, null otherwise.
     */
    public static Bank getBank(Comparator<Bank> bankComparator, Bank bank) {
        for (int i = 0; i < bankSize(); i++) {
            if (bankComparator.compare(bank, banks.get(i)) == 0) {
                return banks.get(i);
            }
        }
        return null;
    }

    /**
     * Finds an account based on its account number across all registered banks.
     * @param accountNumber Account number to search for.
     * @return Account object if found, null otherwise.
     */
    public static Account findAccount(String accountNumber) {
        for (Bank bank : banks) {
            Account account = bank.getBankAccount(accountNumber);
            if (account != null) {
                return account;
            }
        }
        return null;
    }

    /**
     * Get the number of currently registered banks.
     * @return The count of registered banks.
     */
    public static int bankSize() {
        return banks.size();
    }
}
