// Guess the Number Game
import java.util.*;
import javax.swing.JOptionPane;
public class GuessTheNumber {
    public static void main(String[] args) {
        Random rand = new Random();                         //Object of Random class to generate random numbers
        int numberToGuess = rand.nextInt(100) + 1; // Random number between 1 and 100
        int numberOfTries = 0;                              
        boolean hasWon = false;                 //Flag to check if the user has won the game

        while (!hasWon) {
            String userInput = JOptionPane.showInputDialog("Guess a number between 1 and 100:");
            int userGuess;

            try {
                userGuess = Integer.parseInt(userInput);
                numberOfTries++;

                if (userGuess < numberToGuess) {
                    JOptionPane.showMessageDialog(null, "Too low! Try again.");
                } else if (userGuess > numberToGuess) {
                    JOptionPane.showMessageDialog(null, "Too high! Try again.");
                } else {
                    hasWon = true;
                    JOptionPane.showMessageDialog(null, "Congratulations! You've guessed the number in " + numberOfTries + " tries.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
            }
        }
    }
}
