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
        maxNumberOfRunningThreads = getData("Maximum number of running thread");
        numberToCalculateTo = getData("The number until which to find prime numbers");
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
        System.out.println("The prime numbers between 2 and %d");
    }
    public static int getData(String data) {

    }
}
