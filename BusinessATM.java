import java.util.ArrayList;
import java.util.List;

public class BusinessATM implements ATMInterface {
    private double balance;
    private List<String> transactionHistory;
    private boolean isAuthenticated;
    private final double TRANSFER_LIMIT = 50000.0;
    private int dailyTransfers = 0;
    private final int MAX_DAILY_TRANSFERS = 10;

    public BusinessATM() {
        this.balance = 50000.0;
        this.transactionHistory = new ArrayList<>();
        this.isAuthenticated = false;
    }

    @Override
    public boolean authenticate(String userId, String pin) {
        if (userId.equals("businessuser") && pin.equals("3456")) {
            isAuthenticated = true;
            transactionHistory.add("Business account login successful");
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
        } else if (amount <= 0) {
            transactionHistory.add("Withdrawal failed - Invalid amount");
        } else {
            balance -= amount;
            transactionHistory.add("Business Withdrawal: $" + amount);
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
            transactionHistory.add("Business Deposited: $" + amount);
        }
    }

    @Override
    public void transfer(String recipient, double amount) {
        if (!isAuthenticated) {
            return;
        }
        if (dailyTransfers >= MAX_DAILY_TRANSFERS) {
            transactionHistory.add("Transfer failed - Daily transfer limit reached (Max: " + MAX_DAILY_TRANSFERS + ")");
        } else if (amount > TRANSFER_LIMIT) {
            transactionHistory.add("Transfer failed - Exceeds limit: $" + TRANSFER_LIMIT);
        } else if (amount > balance) {
            transactionHistory.add("Transfer failed - Insufficient balance");
        } else if (amount <= 0) {
            transactionHistory.add("Transfer failed - Invalid amount");
        } else {
            balance -= amount;
            dailyTransfers++;
            transactionHistory.add("Business Transfer: $" + amount + " to " + recipient + " (Transfer #" + dailyTransfers + ")");
        }
    }

    @Override
    public double getBalance() {
        return balance;
    }

    public void generateMonthlyReport() {
        transactionHistory.add("Monthly Report Generated - Total transactions: " + transactionHistory.size());
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
