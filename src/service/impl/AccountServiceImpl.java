package service.impl;

import model.Account;
import model.EWalletSystem;
import service.impl.Interfaces.AccountService;

/**
 * Handles all business logic for accounts:
 * - Sign Up
 * - Login
 * - Deposit
 * - Withdraw
 * - Transfer
 * - Change Password
 */
public class AccountServiceImpl implements AccountService {

    /**
     * Create new account
     */
    @Override
    public boolean createAccount(Account account) {

        // get system instance
        EWalletSystem system = EWalletSystem.getInstance();

        String username = account.getUsername();

        // check if username already exists
        boolean exists = system.getAccounts().stream().anyMatch(acc -> acc.getUsername().equals(username));

        if (exists) {
            return false;
        }

        // username validation
        // must be at least 3 chars and letters only
        if (username.length() < 3 || !username.matches("[a-zA-Z]+")) {

            return false;
        }

        // password validation
        // password length must be between 6 and 16
        if (account.getPassword().length() < 6 || account.getPassword().length() > 16) {

            return false;
        }
        // password validation
        // must contain:
        // uppercase, lowercase, number, special char
        if (!account.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{6,16}$")) {

            return false;
        }

        // age validation
        // user must be 18+
        if (account.getAge() < 18) {
            return false;
        }

        // phone validation
        String phone = account.getPhoneNumber();

        // phone must:
        // start with +20
        // contain 13 chars
        // contain digits only after +20
        if (!phone.startsWith("+20") || phone.length() != 13 || !phone.substring(3).matches("\\d+")) {

            return false;
        }

        // add account to system
        system.getAccounts().add(account);

        return true;
    }

    /**
     * Login using username and password
     */
    @Override
    public Account login(String username, String password) {

        EWalletSystem system = EWalletSystem.getInstance();

        // search for matching account
        return system.getAccounts().stream()

                .filter(acc -> acc.getUsername().equals(username) && acc.getPassword().equals(password))

                .findFirst()

                .orElse(null);
    }

    /**
     * Add money to account balance
     */
    @Override
    public boolean deposit(String username, double amount) {

        EWalletSystem system = EWalletSystem.getInstance();

        // search for account
        Account account = system.getAccounts().stream()

                .filter(acc -> acc.getUsername().equals(username))

                .findFirst()

                .orElse(null);

        // account not found
        if (account == null) {
            return false;
        }

        // invalid amount
        if (amount <= 0) {
            return false;
        }

        // add money
        account.deposit(amount);

        return true;
    }

    /**
     * Withdraw money from account balance
     */
    @Override
    public boolean withdraw(String username, double amount) {

        EWalletSystem system = EWalletSystem.getInstance();

        // search for account
        Account account = system.getAccounts().stream()

                .filter(acc -> acc.getUsername().equals(username))

                .findFirst()

                .orElse(null);

        // account not found
        if (account == null) {
            return false;
        }

        // invalid amount
        if (amount <= 0) {
            return false;
        }

        // withdraw money
        return account.withdraw(amount);
    }

    /**
     * Transfer money between two accounts
     */
    @Override
    public boolean transfer(String fromUsername, String toUsername, double amount) {

        EWalletSystem system = EWalletSystem.getInstance();

        // find sender account
        Account sender = system.getAccounts().stream()

                .filter(acc -> acc.getUsername().equals(fromUsername))

                .findFirst()

                .orElse(null);

        // find receiver account
        Account receiver = system.getAccounts().stream()

                .filter(acc -> acc.getUsername().equals(toUsername))

                .findFirst()

                .orElse(null);

        // check accounts exist
        if (sender == null || receiver == null) {
            return false;
        }

        // invalid amount
        if (amount <= 0) {
            return false;
        }

        // user cannot transfer to himself
        if (fromUsername.equals(toUsername)) {
            return false;
        }

        // withdraw from sender
        // if balance not enough return false
        if (!sender.withdraw(amount)) {
            return false;
        }

        // add money to receiver
        receiver.deposit(amount);

        return true;
    }

    /**
     * Change account password
     */
    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {

        EWalletSystem system = EWalletSystem.getInstance();

        // search for account
        Account account = system.getAccounts().stream()

                .filter(acc -> acc.getUsername().equals(username))

                .findFirst()

                .orElse(null);

        // account not found
        if (account == null) {
            return false;
        }

        // check old password
        if (!account.getPassword().equals(oldPassword)) {
            return false;
        }

        // validate new password
        if (newPassword.length() < 6 || newPassword.length() > 16) {

            return false;
        }

        // update password
        // cannot use old password again
        if (oldPassword.equals(newPassword)) {
            return false;
        }
        account.setPassword(newPassword);

        return true;
    }
}