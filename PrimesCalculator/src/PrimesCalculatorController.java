/** Thread controller and creator for calculating prime numbers from 2 to a given number.
 *  mmn15-1
 *  * @author Gad Maor
 *  * @version 1.0
 */
public class PrimesCalculatorController {

    /******************************************************
     *                      Fields                        *
     *****************************************************/
    /* maxThreads - maximum allowed number of concurrently running threads
       activeThreads - number of currently running threads
       numbersProcessed - the amount of numbers already processed
     */
    private int maxThreads, activeThreads, numbersProcessed;
    // numbers - a boolean array to store the results: if a number is prime, its cell will contain true
    private boolean numbers[];

    /******************************************************
     *                      Constructors                  *
     *****************************************************/
    /** Constructs a new controller for handling the prime numbers calculating threads.
     *
     * @param maxNumOfThreads maximum allowed number of concurrently running threads
     * @param numToCalculatePrimesTo Number until which to find all prime numbers
     */
    public PrimesCalculatorController(int maxNumOfThreads, int numToCalculatePrimesTo) {
        maxThreads = maxNumOfThreads;
        numbers = new boolean[numToCalculatePrimesTo + 1];// save a place for last number in the array
        for (int i=0; i < numbers.length; ++i){
            numbers[i] = false;
        }
    }

    /******************************************************
     *                      Methods                       *
     *****************************************************/
    public int getActiveThreads() {
        return activeThreads;
    }

    public void setActiveThreads(int activeThreads) {
        this.activeThreads = activeThreads;
    }

    /** Sets the given element of the numbers array to true, to indicate it's a prime number
     *
     * @param element The element number of numbers array to set
     */
    public void setNumbersElementToTrue(int element) {
        this.numbers[element] = true;
    }

    /** Waits until there is an available slot for a new thread
     *
     */
    public synchronized void waitForAvailableThread() {
        while (this.activeThreads == this.maxThreads) {
            try {
                wait();
            }
            catch (InterruptedException exception) {}
        }
    }

    /** Runs tasks after a thread completes its calculations:
     *  reduces number of active threads, increases number of numbers processed and notifies the
     *  PrimesCalculatorController object of the termination of a thread.
     *
     */
    public synchronized void threadDone() {
        --this.activeThreads;
        ++this.numbersProcessed;
        notifyAll();
    }

    /** Waits until all threads are done processing their numbers
     *
     */
    public synchronized void waitForLastThread() {
        while (this.numbersProcessed < numbers.length - 2) {
            try {
                wait();
            }
            catch (InterruptedException exception) {}
        }
    }

    /** Returns a tab separated list of the prime numbers from 2 until the length of the numbers array
     *
     * @return - The prime numbers from 2 to to numbers.length
     */
    public String getResults() {
        String res = "";
        for (int i=0; i < numbers.length; ++i) {
            if (numbers[i]) {
                res += i + " ";
            }
        }
        // Use trim to remove trailing space
        return res.trim();
    }
}
