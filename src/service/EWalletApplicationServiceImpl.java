package service;

import model.Account;
import service.impl.AccountServiceImpl;
import service.impl.Interfaces.AccountService;
import service.impl.Interfaces.ApplicationService;

import java.util.Scanner;

public class EWalletApplicationServiceImpl implements ApplicationService {

    private Scanner sc = new Scanner(System.in);
    private AccountService service = new AccountServiceImpl();

    // ================= START =================
    @Override
    public void Start() {

        while (true) {

            System.out.println("\n========== E-WALLET SYSTEM ==========");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            System.out.println("=====================================");
            System.out.print("Choose an option: ");



            try {
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    case 1:
                        login();
                        break;

                    case 2:
                        signUp();
                        break;

                    case 3:
                        System.out.println("Goodbye");
                        return;

                    default:
                        System.out.println("Invalid choice");
                }

            } catch (Exception e) {
                System.out.println("Please enter a valid number");
                sc.nextLine(); // clean buffer
            }
        }
    }

    // ================= LOGIN =================
    private void login() {

        int attempts = 0;

        while (attempts < 3) {

            System.out.print("Enter username: ");
            String username = sc.nextLine();

            System.out.print("Enter password: ");
            String password = sc.nextLine();

            // check empty input
            if (username.isBlank() || password.isBlank()) {

                System.out.println("Username and password cannot be empty");
                continue;
            }

            // try login
            Account account = service.login(username, password);

            // login success
            if (account != null) {

                System.out.println("Login successful");
                userMenu(account);
                return;
            }

            // failed login
            attempts++;

            System.out.println("Invalid username or password");

            // max attempts reached
            if (attempts == 3) {

                System.out.println("Too many failed attempts");
                return;
            }
        }
    }

    // ================= SIGN UP =================
    private void signUp() {
        String username;

        while (true) {

            System.out.print("Enter username: ");
            username = sc.nextLine();

            if (username.length() < 3 || !username.matches("[a-zA-Z]+")) {

                System.out.println("Invalid username");
                continue;
            }

            break;
        }

        String password;

        while (true) {

            System.out.print("Enter password: ");
            password = sc.nextLine();

            // password must be at least 6 chars
            if (!password.matches(
                    "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{6,16}$")) {

                System.out.println(
                        "Password must contain uppercase, lowercase, number and special character");

                continue;
            }

            break;
        }

        String phone;

        while (true) {

            System.out.print("Enter phone: ");
            phone = sc.nextLine();

            if (!phone.startsWith("+20") ||
                    phone.length() != 13 ||
                    !phone.substring(3).matches("\\d+")) {

                System.out.println("Invalid phone number");
                continue;
            }

            break;
        }

        int age;
        while (true) {
            try {

                System.out.print("Enter age: ");
                age = sc.nextInt();
                sc.nextLine();
                if (age < 18) {
                    System.out.println("Invalid age");
                    continue;

                }
                break;

            } catch (Exception e) {
                System.out.println("Invalid age");
                sc.nextLine();
            }

        }


        Account account = new Account(username, password, phone, age);

        if (service.createAccount(account)) {
            System.out.println("Account created successfully");
            userMenu(account);
        } else {
            System.out.println("Failed to create account because there is already an account with that username");
        }
    }

    // ================= USER MENU =================
    private void userMenu(Account account) {

        boolean running = true;

        while (running) {

            System.out.println("\n1- Deposit");
            System.out.println("2- Withdraw");
            System.out.println("3- Transfer");
            System.out.println("4- Change Password");
            System.out.println("5- Show Account Details");
            System.out.println("6- Logout");

            try {
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    case 1:
                        deposit(account);
                        running = askAgain();
                        break;

                    case 2:
                        withdraw(account);
                        running = askAgain();
                        break;

                    case 3:
                        transfer(account);
                        running = askAgain();
                        break;

                    case 4:
                        changePassword(account);
                        running = askAgain();
                        break;
                    case 5:
                        showAccountDetails(account);
                        running = askAgain();
                        break;

                    case 6:
                        System.out.println("Logged out");
                        return;


                    default:
                        System.out.println("Invalid choice");
                }

            } catch (Exception e) {
                System.out.println("Please enter a valid number");
                sc.nextLine();
            }
        }
    }

    // ================= ASK AGAIN =================
    private boolean askAgain() {

        System.out.print("Do you want another operation? (yes/no): ");
        String answer = sc.nextLine();

        return answer.equalsIgnoreCase("yes");
    }

    // ================= DEPOSIT =================
    private void deposit(Account account) {

        System.out.print("Enter amount: ");

        double amount;
        try {
            amount = sc.nextDouble();
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid amount");
            sc.nextLine();
            return;
        }

        if (amount <= 0) {
            System.out.println("Invalid amount");
            return;
        }

        if (service.deposit(account.getUsername(), amount)) {
            System.out.println("Deposit successful");
            System.out.println("Balance: " + account.getBalance());
        } else {
            System.out.println("Deposit failed");
        }
    }

    // ================= WITHDRAW =================
    private void withdraw(Account account) {

        System.out.print("Enter amount: ");

        double amount;
        try {
            amount = sc.nextDouble();
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid amount");
            sc.nextLine();
            return;
        }

        if (amount <= 0) {
            System.out.println("Invalid amount");
            return;
        }

        if (service.withdraw(account.getUsername(), amount)) {
            System.out.println("Withdraw successful");
            System.out.println("Balance: " + account.getBalance());
        } else {
            System.out.println("Withdraw failed (check balance)");
        }
    }

    // ================== TRANSFER =================
    private void transfer(Account account) {

        System.out.print("Enter username to transfer to: ");
        String toUsername = sc.nextLine();

        System.out.print("Enter amount: ");

        double amount;

        try {

            amount = sc.nextDouble();
            sc.nextLine();

        } catch (Exception e) {

            System.out.println("Invalid amount");
            sc.nextLine();
            return;
        }

        if (amount <= 0) {

            System.out.println("Invalid amount");
            return;
        }

        if (service.transfer(account.getUsername(), toUsername, amount)) {

            System.out.println("Transfer successful");

        } else {

            System.out.println("Transfer failed");
        }
    }

    // ================== CHANGE_PASSWORD =================
    private void changePassword(Account account) {
        System.out.print("Enter Old password: ");
        String oldPassword = sc.nextLine();

        System.out.print("Enter new password: ");
        String newPassword = sc.nextLine();

        if (newPassword.length() < 6 || newPassword.length() > 16) {

            System.out.println("Invalid password");
            return;
        }
        if (service.changePassword(account.getUsername(), oldPassword, newPassword)) {
            System.out.println("Change password successful");
        } else {
            System.out.println("Change password failed");
        }


    }
    // ================= SHOW ACCOUNT DETAILS =================
    private void showAccountDetails(Account account) {

        System.out.println("\n===== ACCOUNT DETAILS =====");

        System.out.println("Username: " + account.getUsername());

        System.out.println("Phone: " + account.getPhoneNumber());

        System.out.println("Age: " + account.getAge());

        System.out.println("Balance: " + account.getBalance());
    }


}