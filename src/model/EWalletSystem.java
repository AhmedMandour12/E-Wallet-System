package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the system storage (fake database).
 * It stores all accounts in one shared place (Singleton).
 */
public class EWalletSystem {

    // ─── Single instance (Singleton) ───────────────────────────
    private static EWalletSystem instance;

    // ─── Data ──────────────────────────────────────────────────
    private final String name = "EWalletSystem";
    private List<Account> accounts = new ArrayList<>();

    // ─── Private constructor (no one can create object directly)
    private EWalletSystem() {}

    // ─── Get the single instance ───────────────────────────────
    public static EWalletSystem getInstance() {
        if (instance == null) {
            instance = new EWalletSystem();
        }
        return instance;
    }

    // ─── Getters ───────────────────────────────────────────────
    public String getName() {
        return name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}