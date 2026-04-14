import java.util.ArrayList;
import java.util.List;

public class MobileATM implements ATMInterface {
    private double balance;
    private List<String> transactionHistory;
    private boolean isAuthenticated;
    private String phoneNumber;

    public MobileATM() {
        this.balance = 7500.0;
        this.transactionHistory = new ArrayList<>();
        this.isAuthenticated = false;
        this.phoneNumber = "";
    }

    @Override
    public boolean authenticate(String userId, String pin) {
        if (userId.equals("mobileuser") && pin.equals("7890")) {
            isAuthenticated = true;
            phoneNumber = "+1-555-1234";
            transactionHistory.add("Mobile login successful - Linked to " + phoneNumber);
            return true;
        }
        return false;
    }

    @Override
    public void withdraw(double amount) {
        if (!isAuthenticated) {
            return;
        }
        if (amount > balance) {
            transactionHistory.add("Withdrawal failed - Insufficient balance");
            sendSMS("Withdrawal attempt failed - Insufficient balance");
        } else if (amount <= 0) {
            transactionHistory.add("Withdrawal failed - Invalid amount");
        } else {
            balance -= amount;
            transactionHistory.add("Mobile Withdrawal: $" + amount);
            sendSMS("Withdrawal of $" + amount + " successful. New balance: $" + balance);
        }
    }

    @Override
    public void deposit(double amount) {
        if (!isAuthenticated) {
            return;
        }
        if (amount <= 0) {
            transactionHistory.add("Deposit failed - Invalid amount");
        } else {
            balance += amount;
            transactionHistory.add("Mobile Deposited: $" + amount);
            sendSMS("Deposit of $" + amount + " successful. New balance: $" + balance);
        }
    }

    @Override
    public void transfer(String recipient, double amount) {
        if (!isAuthenticated) {
            return;
        }
        if (amount > balance) {
            transactionHistory.add("Transfer failed - Insufficient balance");
            sendSMS("Transfer to " + recipient + " failed - Insufficient balance");
        } else if (amount <= 0) {
            transactionHistory.add("Transfer failed - Invalid amount");
        } else {
            balance -= amount;
            transactionHistory.add("Mobile Transfer: $" + amount + " to " + recipient);
            sendSMS("Transfer of $" + amount + " to " + recipient + " successful. New balance: $" + balance);
        }
    }

    private void sendSMS(String message) {
        transactionHistory.add("[SMS to " + phoneNumber + "]: " + message);
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public List<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    @Override
    public void displayMenu() {
        // Menu will be handled by GUI
    }
}
