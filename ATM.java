import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ATM {
            private static Scanner scanner = new Scanner(System.in);
            private static double balance = 5000.0;
            private static List<String> transactionHistory = new ArrayList<>();
            private static boolean isAuthenticated = false;

            public static void main(String[] args) {
                        if (authenticate()) {
                                    isAuthenticated = true;
                                    showMenu();
                        } else {
                                    System.out.println("Authentication failed!");
                        }
            }

            private static boolean authenticate() {
                        System.out.print("Enter User ID: ");
                        String userId = scanner.nextLine();
                        System.out.print("Enter PIN: ");
                        String pin = scanner.nextLine();

                        return userId.equals("user123") && pin.equals("1234");
            }

            private static void showMenu() {
                        while (isAuthenticated) {
                                    System.out.println("\n--- ATM Menu ---");
                                    System.out.println("1. Withdraw");
                                    System.out.println("2. Deposit");
                                    System.out.println("3. Transfer");
                                    System.out.println("4. Transaction History");
                                    System.out.println("5. Quit");
                                    System.out.print("Select option: ");

                                    int choice = scanner.nextInt();
                                    scanner.nextLine();

                                    switch (choice) {
                                                case 1:
                                                            withdraw();
                                                            break;
                                                case 2:
                                                            deposit();
                                                            break;
                                                case 3:
                                                            transfer();
                                                            break;
                                                case 4:
                                                            displayTransactionHistory();
                                                            break;
                                                case 5:
                                                            quit();
                                                            break;
                                                default:
                                                            System.out.println("Invalid option!");
                                    }
                        }
            }

            private static void withdraw() {
                        System.out.print("Enter amount to withdraw: ");
                        double amount = scanner.nextDouble();
                        if (amount > balance) {
                                    System.out.println("Insufficient balance!");
                        } else {
                                    balance -= amount;
                                    transactionHistory.add("Withdrawn: " + amount);
                                    System.out.println("Withdrawal successful! New balance: " + balance);
                        }
            }

            private static void deposit() {
                        System.out.print("Enter amount to deposit: ");
                        double amount = scanner.nextDouble();
                        balance += amount;
                        transactionHistory.add("Deposited: " + amount);
                        System.out.println("Deposit successful! New balance: " + balance);
            }

            private static void transfer() {
                        System.out.print("Enter recipient account number: ");
                        String recipient = scanner.nextLine();
                        System.out.print("Enter amount to transfer: ");
                        double amount = scanner.nextDouble();
                        if (amount > balance) {
                                    System.out.println("Insufficient balance!");
                        } else {
                                    balance -= amount;
                                    transactionHistory.add("Transferred: " + amount + " to " + recipient);
                                    System.out.println("Transfer successful! New balance: " + balance);
                        }
            }

            private static void displayTransactionHistory() {
                        System.out.println("\n--- Transaction History ---");
                        if (transactionHistory.isEmpty()) {
                                    System.out.println("No transactions yet!");
                        } else {
                                    for (String transaction : transactionHistory) {
                                                System.out.println(transaction);
                                    }
                        }
                        System.out.println("Current Balance: " + balance);
            }

            private static void quit() {
                        System.out.println("Thank you for using ATM. Goodbye!");
                        isAuthenticated = false;
                        System.exit(0);
            }
}