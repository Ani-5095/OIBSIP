import java.util.ArrayList;
import java.util.List;

public class PremiumATM implements ATMInterface {
    private double balance;
    private List<String> transactionHistory;
    private boolean isAuthenticated;
    private final double WITHDRAWAL_LIMIT = 5000.0;
    private final double DAILY_LIMIT = 20000.0;
    private double dailyWithdrawn = 0;

    public PremiumATM() {
        this.balance = 10000.0;
        this.transactionHistory = new ArrayList<>();
        this.isAuthenticated = false;
    }

    @Override
    public boolean authenticate(String userId, String pin) {
        if (userId.equals("premiumuser") && pin.equals("5678")) {
            isAuthenticated = true;
            transactionHistory.add("Premium login successful");
            return true;
        }
        return false;
    }

    @Override
    public void withdraw(double amount) {
        if (!isAuthenticated) {
            return;
        }
        if (amount > WITHDRAWAL_LIMIT) {
            transactionHistory.add("Withdrawal failed - Exceeds limit: $" + WITHDRAWAL_LIMIT);
        } else if (dailyWithdrawn + amount > DAILY_LIMIT) {
            transactionHistory.add("Withdrawal failed - Daily limit exceeded");
        } else if (amount > balance) {
            transactionHistory.add("Withdrawal failed - Insufficient balance");
        } else if (amount <= 0) {
            transactionHistory.add("Withdrawal failed - Invalid amount");
        } else {
            balance -= amount;
            dailyWithdrawn += amount;
            transactionHistory.add("Premium Withdrawal: $" + amount + " (Total today: $" + dailyWithdrawn + ")");
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
            transactionHistory.add("Premium Deposited: $" + amount);
        }
    }

    @Override
    public void transfer(String recipient, double amount) {
        if (!isAuthenticated) {
            return;
        }
        if (amount > balance) {
            transactionHistory.add("Transfer failed - Insufficient balance");
        } else if (amount <= 0) {
            transactionHistory.add("Transfer failed - Invalid amount");
        } else {
            balance -= amount;
            transactionHistory.add("Premium Transfer: $" + amount + " to " + recipient);
        }
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
