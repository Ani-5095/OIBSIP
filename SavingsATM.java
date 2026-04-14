import java.util.ArrayList;
import java.util.List;

public class SavingsATM implements ATMInterface {
    private double balance;
    private List<String> transactionHistory;
    private boolean isAuthenticated;
    private final double INTEREST_RATE = 0.05; // 5% annual interest

    public SavingsATM() {
        this.balance = 15000.0;
        this.transactionHistory = new ArrayList<>();
        this.isAuthenticated = false;
    }

    @Override
    public boolean authenticate(String userId, String pin) {
        if (userId.equals("savingsuser") && pin.equals("9012")) {
            isAuthenticated = true;
            transactionHistory.add("Savings account login successful");
            return true;
        }
        return false;
    }

    @Override
    public void withdraw(double amount) {
        if (!isAuthenticated) {
            return;
        }
        if (amount > balance * 0.5) { // Can only withdraw 50% of balance
            transactionHistory.add("Withdrawal failed - Cannot exceed 50% of balance");
        } else if (amount > balance) {
            transactionHistory.add("Withdrawal failed - Insufficient balance");
        } else if (amount <= 0) {
            transactionHistory.add("Withdrawal failed - Invalid amount");
        } else {
            balance -= amount;
            transactionHistory.add("Savings Withdrawal: $" + amount);
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
            transactionHistory.add("Savings Deposited: $" + amount);
        }
    }

    @Override
    public void transfer(String recipient, double amount) {
        if (!isAuthenticated) {
            return;
        }
        if (amount > balance * 0.3) { // Can only transfer 30% of balance
            transactionHistory.add("Transfer failed - Cannot exceed 30% of balance");
        } else if (amount > balance) {
            transactionHistory.add("Transfer failed - Insufficient balance");
        } else if (amount <= 0) {
            transactionHistory.add("Transfer failed - Invalid amount");
        } else {
            balance -= amount;
            transactionHistory.add("Savings Transfer: $" + amount + " to " + recipient);
        }
    }

    @Override
    public double getBalance() {
        return balance;
    }

    public void applyInterest() {
        double interestAmount = balance * INTEREST_RATE / 12; // Monthly interest
        balance += interestAmount;
        transactionHistory.add("Interest credited: $" + String.format("%.2f", interestAmount));
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
