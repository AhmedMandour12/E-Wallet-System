package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user account in the E-Wallet system.
 * Holds user data and simple behavior (like adding transactions).
 */
public class Account {

    // ─── Fields (Data) ───────────────────────────────────────────

    private String username;
    private String password;
    private String phoneNumber;
    private int age;
    private double balance;
    private List<String> transactionHistory;

    // ─── Constructors ───────────────────────────────────────────

    /**
     * Constructor used for login
     */
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    /**
     * Constructor used for sign up
     */
    public Account(String username, String password, String phoneNumber, int age) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    // ─── Getters ───────────────────────────────────────────────

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public double getBalance() {
        return balance;
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    // ─── Setters ───────────────────────────────────────────────

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    // ─── Behavior Methods ──────────────────────────────────────

    // add transaction to history
    public void addTransaction(String transaction) {
        this.transactionHistory.add(transaction);
    }

    // add money to balance
    public void deposit(double amount) {

        balance += amount;
    }

    // withdraw money from balance
    public boolean withdraw(double amount) {

        // invalid amount
        if (amount <= 0) {
            return false;
        }

        // check balance
        if (amount > balance) {
            return false;
        }

        balance -= amount;
        return true;
    }
}