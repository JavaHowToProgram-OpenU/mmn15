import java.util.Scanner;

/** This program calculates and prints all prime numbers from 2 to a given number,
 * using a given number of concurrently running multithreads.
 *  mmn15-1
 * @author Gad Maor
 * @version 1.0
 */
public class PrimesCalculatorDriver {
    public static void main(String[] args) {
        // Maximum number of concurrently running threads
        int maxNumberOfRunningThreads = -1;
        // The number until which to calculate the primes
        int numberToCalculateTo = -1;
        Scanner input = new Scanner(System.in);
        maxNumberOfRunningThreads = getData("maximum number of running thread", input);
        numberToCalculateTo = getData("The number until which to find prime numbers", input);
        // The thread controller to use for calculating the primes
        PrimesCalculatorController cont = new PrimesCalculatorController(maxNumberOfRunningThreads, numberToCalculateTo);
        for (int i=2; i < numberToCalculateTo; ++i) {
            // Wait for an available thread
            cont.waitForAvailableThread();
            // Increase number of active threads
            cont.setActiveThreads(cont.getActiveThreads() + 1);
            // Start the new thread to check if i is prime
            (new PrimesCalculatorThread(i, cont)).start();
        }
        // Wait for all threads to finish their calculations
        cont.waitForLastThread();
        System.out.println("The prime numbers between 2 and %d", numberToCalculateTo);
        System.out.println(cont.getResults());
    }

    /** This method checks that the user inputs a positive integer as data.
     *
     * @param data A message asking for a specific type of data
     * @param input The Scanner input object to get input from the user
     * @return The user's entered integer
     */
    private static int getData(String data, Scanner input) {
        boolean correctData = false;
        int userInput = 0;
        do {
            System.out.println("Please input %s as a positive integer: ", data);
            try {
                String nextToken = input.next();
                userInput = Integer.parseInt(nextToken);
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid data. Must input an integer!");
                continue;
            }
            if (userInput <= 2) {
                System.out.println("Invalid data. Must input a positive number!");
                continue;
            }
            // If we've reached here, means the number is a positive integer
            correctData = true;
        } while(!correctData);
        return userInput;
    }
}
