import java.util.List;

public interface ATMInterface {
    boolean authenticate(String userId, String pin);
    void withdraw(double amount);
    void deposit(double amount);
    void transfer(String recipient, double amount);
    double getBalance();
    List<String> getTransactionHistory();
    void displayMenu();
}
