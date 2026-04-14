import java.util.List;
import javax.swing.*;

public class ATMGUIMain {
    private ATMInterface atmInterface;
    private boolean isAuthenticated = false;
    private String currentUsername = "";
    private final String BANK_NAME = "MyBank ATM";

    public ATMGUIMain() {
        // Ask user if they want to set custom credentials
        int response = JOptionPane.showConfirmDialog(
            null,
            "Do you want to set custom login credentials?",
            "Custom Credentials",
            JOptionPane.YES_NO_OPTION
        );

        if (response == JOptionPane.YES_OPTION) {
            setupCredentials();
        } else {
            // Use default credentials
            atmInterface = new BasicATM();
        }
        
        // Start authentication
        authenticate();
    }

    private void setupCredentials() {
        String userId = JOptionPane.showInputDialog(
            null,
            "Enter your desired User ID:",
            "Set User ID",
            JOptionPane.QUESTION_MESSAGE
        );

        if (userId == null || userId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "User ID cannot be empty. Using defaults.", "Error", JOptionPane.ERROR_MESSAGE);
            atmInterface = new BasicATM();
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
            atmInterface = new BasicATM();
            return;
        }

        atmInterface = new BasicATM(userId, pin);
        JOptionPane.showMessageDialog(
            null,
            "Credentials set successfully!\nUser ID: " + userId + "\nPIN: " + pin,
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void authenticate() {
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

        if (atmInterface.authenticate(userId, pin)) {
            isAuthenticated = true;
            currentUsername = userId;
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

    private void showMenu() {
        while (isAuthenticated) {
            String[] menuOptions = {
                "Withdraw",
                "Deposit",
                "Transfer",
                "View Balance",
                "Transaction History",
                "Quit"
            };

            String menuMessage = "===== " + BANK_NAME + " =====\n"
                    + "Welcome, " + currentUsername + "\n"
                    + "============================\n\n"
                    + "Select an operation:\n\n"
                    + "1. Withdraw\n"
                    + "2. Deposit\n"
                    + "3. Transfer\n"
                    + "4. View Balance\n"
                    + "5. Transaction History\n"
                    + "6. Quit";

            int choice = JOptionPane.showOptionDialog(
                null,
                menuMessage,
                BANK_NAME + " Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                menuOptions,
                menuOptions[0]
            );

            switch (choice) {
                case 0:
                    handleWithdraw();
                    break;
                case 1:
                    handleDeposit();
                    break;
                case 2:
                    handleTransfer();
                    break;
                case 3:
                    handleViewBalance();
                    break;
                case 4:
                    handleTransactionHistory();
                    break;
                case 5:
                    handleQuit();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Operation cancelled.");
            }
        }
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
                atmInterface.withdraw(amount);
                JOptionPane.showMessageDialog(
                    null,
                    "===== " + BANK_NAME + " =====\n"
                    + "Username: " + currentUsername + "\n"
                    + "===========================\n\n"
                    + "Withdrawal processed successfully!\n"
                    + "Amount: $" + String.format("%.2f", amount) + "\n"
                    + "New Balance: $" + String.format("%.2f", atmInterface.getBalance()),
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
                atmInterface.deposit(amount);
                JOptionPane.showMessageDialog(
                    null,
                    "===== " + BANK_NAME + " =====\n"
                    + "Username: " + currentUsername + "\n"
                    + "===========================\n\n"
                    + "Deposit processed successfully!\n"
                    + "Amount: $" + String.format("%.2f", amount) + "\n"
                    + "New Balance: $" + String.format("%.2f", atmInterface.getBalance()),
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
                    atmInterface.transfer(recipient, amount);
                    JOptionPane.showMessageDialog(
                        null,
                        "===== " + BANK_NAME + " =====\n"
                        + "Username: " + currentUsername + "\n"
                        + "===========================\n\n"
                        + "Transfer processed successfully!\n"
                        + "Amount: $" + String.format("%.2f", amount) + "\n"
                        + "Recipient: " + recipient + "\n"
                        + "New Balance: $" + String.format("%.2f", atmInterface.getBalance()),
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
            + "Your Current Balance: $" + String.format("%.2f", atmInterface.getBalance()),
            "Account Balance",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void handleTransactionHistory() {
        List<String> history = atmInterface.getTransactionHistory();
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

        historyText.append("\nCurrent Balance: $").append(String.format("%.2f", atmInterface.getBalance()));

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
        SwingUtilities.invokeLater(() -> new ATMGUIMain());
    }
}
