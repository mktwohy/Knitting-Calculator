import java.util.InputMismatchException;
import java.util.Scanner;

public class MainDriver {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in); //Scanner for input 
		System.out.println("To quit the program, enter any character(s)");
		int numElem;
		int arraySize;
		boolean quit = false;
		while(!quit) {	
			try {
				//Get user input
				numElem = getInput(input, "number of elements"); //Ask user for n
				arraySize = getInput(input, "array size"); 	//Ask user for s
				
				//Error checking for invalid values
				if(numElem<=arraySize) {
					PlaceElements temp = new PlaceElements(numElem, arraySize);
					System.out.println(temp.chunksToString());
					System.out.println(temp.elementsToString());
					System.out.println(temp.inverseToString());

				}else {
					System.err.print("Sum cannot be less than the number of elements");
				}
				System.out.println("\n");
			}catch(InputMismatchException e) { 
				System.out.println("Quitting...");
				quit = true;
			}
			
			
			
		}
	}
	
	/**
	 * Asks user for a specified integer input
	 * @param input Scanner for input
	 * @param valueType String that is used in the print statement to specify what 
	 * type of value to enter. For example, "Number of Operands" or "That Sum To." 
	 * @return user's desired value (an integer)
	 */
	private static int getInput(Scanner input, String valueType) {
		int d = -1; //dimension initialized as -1 so it enters while loop
		while(d<0) { //keep asking for input until user enters dimension > 0 
			System.out.print("Enter the "+valueType+": ");
			d = input.nextInt(); //Get user input

			if(d<0) { //Prevents user from entering value less than 0
				System.err.println(valueType+" cannot be negative");
			}
			input.nextLine(); //catch "\n"
		}
		return d;
	}
}
