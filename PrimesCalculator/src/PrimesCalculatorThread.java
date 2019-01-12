/** Represents a single thread able to calculate if a given number is prime or not.
 *  mmn15-1
 * @author Gad Maor
 * @version 1.0
 */
public class PrimesCalculatorThread extends Thread{

    /******************************************************
     *                      Fields                        *
     *****************************************************/
    private int numberToCheck;
    private PrimesCalculatorController controller;

    /******************************************************
     *                      Constructors                  *
     *****************************************************/
    /** Constructs a new thread with which to calculate if a given number is prime
     *
     * @param num The number to check
     * @param cont The PrimesCalculatorController controller object controlling all calculating threads
     */
    public PrimesCalculatorThread(int num, PrimesCalculatorController cont) {
        numberToCheck = num;
        controller = cont;
    }

    /******************************************************
     *                      Methods                       *
     *****************************************************/
    public void run() {
        int i;
        for (i=2; i < this.numberToCheck; ++i) {
            if (this.numberToCheck % i == 0) { // No need to continue checking, numberToCheck is not prime
                break;
            }
        }
        if (i == this.numberToCheck) { // If we finished the loop and have reach the numberToCheck -> it's prime
            controller.setNumbersElementToTrue(i);
        }
        controller.threadDone(); // Notify that this thread has completed its job
    }

}
