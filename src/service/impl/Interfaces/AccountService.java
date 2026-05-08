package service.impl.Interfaces;

import model.Account;

public interface AccountService {
    boolean createAccount(Account account);

    Account login(String username, String password);

    boolean deposit(String username, double amount);

    boolean withdraw(String username, double amount);

    boolean transfer(String fromUsername, String toUsername, double amount);

    boolean changePassword(String username, String oldPassword, String newPassword);


}