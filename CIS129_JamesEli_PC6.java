/*************************************************************************
 * Title: Driver License Grading Machine (DriveLicGrama)
 * File: CIS129_JamesEli_PC6.java
 * Author: James Eli
 * Date: 6/23/2016
 *
 * This program grades a 20 question driver license test, and reports the 
 * results to the user. Input validation is performed to assure the values 
 * are in an acceptable format and range.
 * 
 * Uses Scanner class, so is not safe for multi-threaded use.
 * 
 * Submitted in partial fulfillment of the requirements of PCC CIS-219.
 *************************************************************************/

// Scanner class used for user input.
import java.util.Scanner;

public class CIS129_JamesEli_PC6 {
    // Class-wide declarations.
    // Create a single one-time class-level shared Scanner object for keyboard input.
    // All keyboard input is handled through this class.
    private static Scanner scanner = new Scanner(System.in);
    // Constants.
    private static final int NUM_TEST_QUESTIONS = 20;          // Number for questions in test.
    private static final int NUM_CORRECT_ANSWERS_TO_PASS = 15; // Minimum correct answers to pass test.
    // Character array holding correct answers to test questions.
    private static final char[] correctAnswers = new char[] { 'A', 'D', 'A', 'A', 'C', 'A', 'B', 'A', 'C', 'D', 
                                                              'B', 'C', 'D', 'A', 'D', 'C' ,'C', 'B', 'D', 'A' };

   /*********************************************************************
    * Function: displayWrongQuestionNumbers()
    * Input:    int numWrong, number of questions wrong.
    *           char[] answers, char array of user's test answers.
    * Return:   n/a
    * 
    * Routine displays incorrect answers of test. Will not accept an empty 
    * array, nor null array as an argument. 
    *********************************************************************/
    private static void displayWrongQuestionNumbers( int numWrong, final char[] answers ) {
      // Assert validity of parameters. 
      assert ( numWrong >= 0 && numWrong <= NUM_TEST_QUESTIONS ) : "displayWrongQuestionNumbers: numWrong out of limits";
      assert ( answers != null ) : "displayWrongQuestionNumbers: answers==null";
      assert ( answers.length == NUM_TEST_QUESTIONS) : "displayWrongQuestionNumbers: answers length mismatch";
      
      // Loop through entire test.
      for ( int i=0; i<NUM_TEST_QUESTIONS; i++ ) {
        // Find only incorrect answers and display them.
        if ( answers[i] != correctAnswers[i] ) 
          System.out.print( (i + 1) + ( (--numWrong > 0) ? ", " : ".\r\n" ) ); 
      }
    }

    /*********************************************************************
     * Start of main program. Command line arguments are ignored.
     *********************************************************************/
    public static void main( String[] args ) {
      // Variables.
      String s;                  // String to temporarily hold user input before converting to double
      int numCorrectAnswers = 0; // Number of test answers user got correct.
      int numWrongAnswers = 0;   // Number of test answers user got wrong.
      char[] studentAnswers = new char[NUM_TEST_QUESTIONS]; // Array holds user's test answers.
      
      // Assert parallel arrays are same size. In the event someone modifies this program in the future.
      assert ( studentAnswers.length == correctAnswers.length ) : "studentAnswers & correctAnswers not same length";

      // Display program header and user prompt.
      System.out.println( "Driver License Written Test Grader" );
      System.out.println( "Please enter the answers to the questions as prompted.\r\n" );

      // Get user's answers. Looping through all questions in test.
      for ( int i=0; i<NUM_TEST_QUESTIONS; i++ ) {
        char answer; // Temporary holds user's input.

        do { // Loop until user inputs a valid response.
          answer = '\0'; // Prime empty. 
          System.out.println( "Enter the answer to question #" + (i + 1) + " [A, B, C or D]: " );
          try {
            // Capture input as a string and trim leading/trailing whitespace.
            s = scanner.nextLine().trim();
            // Note: we don't test for s==null because we are catching the exception.
            if ( s.length() > 1 ) // Test for single character.
              System.out.println( "Please enter one character." );
            else { 
              answer = s.toUpperCase().charAt( 0 ); // Convert to upper case.
              if ( answer < 'A' || answer > 'D' )   // Validate character.
                System.out.println( "Entry not A, B, C or D." );
            }
            /* 
             * Possible exceptions thrown inside the try braces are:
             * nextLine:
             *   NoSuchElementException - if no line was found.
             *   IllegalStateException - if this scanner is closed.
             * charAt:
             *   IndexOutOfBoundsException - if beginIndex is negative or larger than the length of this String object.
             */
            } catch ( IllegalStateException illegalStateException ) {
              // Call me paranoid. Really no way to recover from this, so we bail.
              System.err.println( "Program unable to access console input stream." );
              System.exit(0);
            } catch ( IndexOutOfBoundsException | NullPointerException emptyStringException ) {
              // Empty string received as input. Alert and try again.
              System.out.println( "Empty input received, please try again." );
            } catch ( Exception otherExceptions ) {
              // It should be possible to retry. Alert and try again.
              System.out.println( "I don't understand your input, please try again." );
            }
            // Continue loop if character was invalid.
          } while ( answer < 'A' || answer > 'D' );
        
        // Insert validated user's answer into array of test answers. 
        studentAnswers[i] = answer;
        // Compare answers with correct test results, recording right/wrong totals.
        if ( studentAnswers[i] == correctAnswers[i] )
          numCorrectAnswers++;
        else 
          numWrongAnswers++;
      } // End of for loop.

      // Determine if user passed.
      if ( numCorrectAnswers >= NUM_CORRECT_ANSWERS_TO_PASS )
        System.out.println( "Congratulations you passed!" );
      else
        System.out.println( "Sorry, you failed." );
      // Display number of correct answers.
      System.out.format( "Number of correct answers: %d out of %d. ", numCorrectAnswers, NUM_TEST_QUESTIONS );
      // Display number of wrong answers, if any.
      if ( numCorrectAnswers != NUM_TEST_QUESTIONS ) {
        System.out.println( "Number of wrong answers: " + numWrongAnswers );
        System.out.print( "The following questions were answered wrong: " );
        displayWrongQuestionNumbers(numWrongAnswers, studentAnswers );
      }
      
      // Program terminates here.
      
    } // End of main module.

    // Prevent instantiation this class.
    private CIS129_JamesEli_PC6() { }

} // End of CIS129_JamesEli_PC6 class
