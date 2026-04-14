import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class BasicATM implements ATMInterface {
    private double balance;
    private List<String> transactionHistory;
    private boolean isAuthenticated;
    private String correctUserId;
    private String correctPin;
    private String currentUsername = "";
    private final String BANK_NAME = "State Bank of India";
    private JFrame menuFrame;

    public BasicATM() {
        this.balance = 5000.0;
        this.transactionHistory = new ArrayList<>();
        this.isAuthenticated = false;
        this.correctUserId = "user123";
        this.correctPin = "1234";
    }

    public BasicATM(String userId, String pin) {
        this.balance = 5000.0;
        this.transactionHistory = new ArrayList<>();
        this.isAuthenticated = false;
        this.correctUserId = userId;
        this.correctPin = pin;
    }

    @Override
    public boolean authenticate(String userId, String pin) {
        if (userId.equals(correctUserId) && pin.equals(correctPin)) {
            isAuthenticated = true;
            currentUsername = userId;
            transactionHistory.add("Login successful");
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
            transactionHistory.add("Withdrawn: $" + amount);
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
            transactionHistory.add("Deposited: $" + amount);
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
            transactionHistory.add("Transferred: $" + amount + " to " + recipient);
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

    // GUI Methods
    public void setupCredentials() {
        String userId = JOptionPane.showInputDialog(
            null,
            "Enter your desired User ID:",
            "Set User ID",
            JOptionPane.QUESTION_MESSAGE
        );

        if (userId == null || userId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "User ID cannot be empty. Using defaults.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String pin = JOptionPane.showInputDialog(
            null,
            "Enter your desired PIN:",
            "Set PIN",
            JOptionPane.QUESTION_MESSAGE
        );

        if (pin == null || pin.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "PIN cannot be empty. Using defaults.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.correctUserId = userId;
        this.correctPin = pin;
        JOptionPane.showMessageDialog(
            null,
            "Credentials set successfully!\nUser ID: " + userId + "\nPIN: " + pin,
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void authenticateGUI() {
        String userId = JOptionPane.showInputDialog(
            null,
            "Enter User ID:",
            "Authentication",
            JOptionPane.QUESTION_MESSAGE
        );

        if (userId == null) {
            System.exit(0);
        }

        String pin = JOptionPane.showInputDialog(
            null,
            "Enter PIN:",
            "Authentication",
            JOptionPane.QUESTION_MESSAGE
        );

        if (pin == null) {
            System.exit(0);
        }

        if (this.authenticate(userId, pin)) {
            showMenu();
        } else {
            JOptionPane.showMessageDialog(
                null,
                "Authentication failed! Invalid User ID or PIN.",
                "Login Error",
                JOptionPane.ERROR_MESSAGE
            );
            System.exit(0);
        }
    }

    public void showMenu() {
        menuFrame = new JFrame(BANK_NAME + " Menu");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(450, 550);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setResizable(false);

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        JLabel bankLabel = new JLabel(BANK_NAME);
        bankLabel.setFont(new Font("Arial", Font.BOLD, 20));
        bankLabel.setForeground(Color.WHITE);
        bankLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeLabel = new JLabel("Welcome, " + currentUsername);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(bankLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(welcomeLabel);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        buttonPanel.setBackground(new Color(245, 245, 245));

        // Create buttons
        JButton withdrawBtn = createButton("Withdraw", e -> {
            handleWithdraw();
            menuFrame.repaint();
            menuFrame.revalidate();
        });
        JButton depositBtn = createButton("Deposit", e -> {
            handleDeposit();
            menuFrame.repaint();
            menuFrame.revalidate();
        });
        JButton transferBtn = createButton("Transfer", e -> {
            handleTransfer();
            menuFrame.repaint();
            menuFrame.revalidate();
        });
        JButton balanceBtn = createButton("View Balance", e -> {
            handleViewBalance();
            menuFrame.repaint();
            menuFrame.revalidate();
        });
        JButton historyBtn = createButton("Transaction History", e -> {
            handleTransactionHistory();
            menuFrame.repaint();
            menuFrame.revalidate();
        });
        JButton quitBtn = createButton("Quit", e -> handleQuit());

        buttonPanel.add(withdrawBtn);
        buttonPanel.add(depositBtn);
        buttonPanel.add(transferBtn);
        buttonPanel.add(balanceBtn);
        buttonPanel.add(historyBtn);
        buttonPanel.add(quitBtn);

        // Main frame setup
        menuFrame.add(headerPanel, BorderLayout.NORTH);
        menuFrame.add(buttonPanel, BorderLayout.CENTER);
        menuFrame.setVisible(true);
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setBackground(new Color(25, 45, 85));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setOpaque(true);
        button.addActionListener(action);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void handleWithdraw() {
        String amountStr = JOptionPane.showInputDialog(
            null,
            "Enter amount to withdraw:",
            "Withdraw",
            JOptionPane.QUESTION_MESSAGE
        );

        if (amountStr != null && !amountStr.trim().isEmpty()) {
            try {
                double amount = Double.parseDouble(amountStr);
                withdraw(amount);
                JOptionPane.showMessageDialog(
                    null,
                    "===== " + BANK_NAME + " =====\n"
                    + "Username: " + currentUsername + "\n"
                    + "===========================\n\n"
                    + "Withdrawal processed successfully!\n"
                    + "Amount: $" + String.format("%.2f", amount) + "\n"
                    + "New Balance: $" + String.format("%.2f", getBalance()),
                    "Withdrawal Successful",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                    null,
                    "Invalid amount. Please enter a valid number.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void handleDeposit() {
        String amountStr = JOptionPane.showInputDialog(
            null,
            "Enter amount to deposit:",
            "Deposit",
            JOptionPane.QUESTION_MESSAGE
        );

        if (amountStr != null && !amountStr.trim().isEmpty()) {
            try {
                double amount = Double.parseDouble(amountStr);
                deposit(amount);
                JOptionPane.showMessageDialog(
                    null,
                    "===== " + BANK_NAME + " =====\n"
                    + "Username: " + currentUsername + "\n"
                    + "===========================\n\n"
                    + "Deposit processed successfully!\n"
                    + "Amount: $" + String.format("%.2f", amount) + "\n"
                    + "New Balance: $" + String.format("%.2f", getBalance()),
                    "Deposit Successful",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                    null,
                    "Invalid amount. Please enter a valid number.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void handleTransfer() {
        String recipient = JOptionPane.showInputDialog(
            null,
            "Enter recipient account number:",
            "Transfer",
            JOptionPane.QUESTION_MESSAGE
        );

        if (recipient != null && !recipient.trim().isEmpty()) {
            String amountStr = JOptionPane.showInputDialog(
                null,
                "Enter amount to transfer:",
                "Transfer Amount",
                JOptionPane.QUESTION_MESSAGE
            );

            if (amountStr != null && !amountStr.trim().isEmpty()) {
                try {
                    double amount = Double.parseDouble(amountStr);
                    transfer(recipient, amount);
                    JOptionPane.showMessageDialog(
                        null,
                        "===== " + BANK_NAME + " =====\n"
                        + "Username: " + currentUsername + "\n"
                        + "===========================\n\n"
                        + "Transfer processed successfully!\n"
                        + "Amount: $" + String.format("%.2f", amount) + "\n"
                        + "Recipient: " + recipient + "\n"
                        + "New Balance: $" + String.format("%.2f", getBalance()),
                        "Transfer Successful",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(
                        null,
                        "Invalid amount. Please enter a valid number.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }
    }

    private void handleViewBalance() {
        JOptionPane.showMessageDialog(
            null,
            "===== " + BANK_NAME + " =====\n"
            + "Username: " + currentUsername + "\n"
            + "============================\n\n"
            + "Your Current Balance: $" + String.format("%.2f", getBalance()),
            "Account Balance",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void handleTransactionHistory() {
        List<String> history = getTransactionHistory();
        StringBuilder historyText = new StringBuilder("===== " + BANK_NAME + " =====\n")
                .append("Username: ").append(currentUsername).append("\n")
                .append("=============================\n\n")
                .append("--- Transaction History ---\n");

        if (history.isEmpty()) {
            historyText.append("No transactions yet!");
        } else {
            for (String transaction : history) {
                historyText.append(transaction).append("\n");
            }
        }

        historyText.append("\nCurrent Balance: $").append(String.format("%.2f", getBalance()));

        JOptionPane.showMessageDialog(
            null,
            historyText.toString(),
            "Transaction History",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void handleQuit() {
        int confirm = JOptionPane.showConfirmDialog(
            null,
            "Are you sure you want to exit?",
            "Exit Confirmation",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(
                null,
                "Thank you for using ATM. Goodbye!",
                "Exit",
                JOptionPane.INFORMATION_MESSAGE
            );
            isAuthenticated = false;
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BasicATM atm = new BasicATM();
            
            // Ask user if they want to set custom credentials
            int response = JOptionPane.showConfirmDialog(
                null,
                "Do you want to set custom login credentials?",
                "Custom Credentials",
                JOptionPane.YES_NO_OPTION
            );

            if (response == JOptionPane.YES_OPTION) {
                atm.setupCredentials();
            }
            
            // Start authentication
            atm.authenticateGUI();
        });
    }
}
