import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class FindOperands {
	/**
	 * The desired number of operands that will sum to a given amount
	 */
	private int numTotalOps;
	/**
	 * The desired sum of all operands
	 */
	private int sumTo;
	/**
	 * Array of operands that should sum to the desired "sumTo" value
	 */
	private int[] results;
	private int[] uniqueOps;
	public boolean isSorted;


	
	/**
	 * Constructor sets numOps and sumTo, then runs an algorithm
	 * to set results
	 */
	public FindOperands(int numOps, int sumTo){
		this.numTotalOps = numOps;
		this.sumTo = sumTo;
		this.results = opSumAlgorithm(); //Find results 
		//System.out.println(Arrays.toString(results));
		this.uniqueOps = findUniqueElem(results); //Each unique operand
		isSorted = false;
		sortResults();
		//System.out.println(Arrays.toString(results));

	}
	
	//FIND ANSWER
	/**
	 * An algorithm that takes the desired number of operands (n) and their desired sum (s) as input.
	 * It then calculates the values of all n operands and outputs them as an int array 
	 */
	private int[] opSumAlgorithm() {
		int[] res = new int[numTotalOps];
		int numOps = this.numTotalOps; //make copy so loop doesn't subtract from class variable
		while(numOps>0) {
			float div = (float)sumTo / (float)numOps; //1) Divide sum by number of operators
			int tempOp =  Math.round(div); //2) Round this answer to the closest integer
			res[numOps-1] = tempOp; //3) record answer by adding this operand to results array
			sumTo = sumTo - tempOp; //4) Since this tempOp is now one of the known operands in the sum,
									//it gets subtracted from the sum. With each iteration, 
									//this step makes the sum smaller, which simplifies the problem.
									//Eventually, the unknown operand will be trivial: "op1 = sum/1"
			numOps = numOps-1; //5) Iterate operand down one. Time to find the next operand!
		}
		return res;
	}	
	
	private void sortResults() {
		switch(uniqueOps.length) { //Switch on number of unique operands
			case 0: 
				isSorted = true; //An empty set is always sorted
				break;
			case 1: 
				isSorted = true; //If one unique element, array is already sorted
				break;
			case 2: 
				results = sortAlgorithm();
				isSorted = true; 
				break;
			default: 
				throw new Error("More than two unique operands");
		}
		
	}
	
	public int[] sortAlgorithm() {
		ArrayList<Integer> sorted = new ArrayList<Integer>();
		int[] majMin = findMajMin();
		int parent = majMin[1];
		int child = majMin[0];
		int numParent = occursInArray(parent, results);
		int numChild = occursInArray(child, results);
		
		//If the amount of each unique element differs by one, 
		//The sorting is a simple alternating pattern
		if(numChild-numParent==1) {
			while(sorted.size()<numParent+numChild) {
				sorted.add(child);
				if(sorted.size()<numParent+numChild) { //if elem at index is a parent
					sorted.add(parent);
				}
			}
		//If not, the sorting is a parent-child pattern, where there are numParent parents
		//who get dealt children in a card-dealing fashion. 
		}else {
			//Place parents
			for(int i=0; i<numParent; i++) {
				sorted.add(parent);
			}
			//"Deal" children to parents
			while(sorted.size()<numParent+numChild) {
				for(int i=0; i<sorted.size();i++) {
					if(sorted.get(i)==parent && sorted.size()<numParent+numChild) { //if elem at index is a parent
						sorted.add(i+1, child); //insert child to the right of parent
					}
				}
				
			}
		}
		return castToIntArray(sorted);
	}
	
	private int[] findMajMin() {
		if(uniqueOps.length == 2) {
			int op1 = uniqueOps[0];
			int op2 = uniqueOps[1];
			int majority;
			int minority;
			if(occursInArray(op1, results) > occursInArray(op2, results)) {
				majority = op1;
				minority = op2;
			}else {
				majority = op2;
				minority = op1;
			}
			
			int[] majMin = {majority, minority};
			return majMin;
		}else {
			return null;
		}
	}
	
	private int[] findUniqueElem(int[] arr) {
		ArrayList<Integer> uniqueElem = new ArrayList<Integer>();
		for(int elem : arr) {
			if(!uniqueElem.contains(elem)) {
				uniqueElem.add(elem);
			}
		}
		return castToIntArray(uniqueElem);
	}
	
	private int occursInArray(int elem, int[] arr) {
		int occurances = 0; //How many time operand occurs in results array
		for(int curElem : arr) {
			if(curElem == elem) {
				occurances++;
			}
		}
		return occurances;
	}
	
	/**
	 * Concerts an ArrayList<Integer> to an int array
	 */
	private int[] castToIntArray(ArrayList<Integer> al) {
		int[] res = new int[al.size()];
		for(int i=0; i<al.size(); i++) {
			res[i] = al.get(i);
		}
		return res;
	}
		//VERIFY ANSWER

	//GETTER METHODS
	public int getNumOps() {
		return numTotalOps;
	}
	
	public int getSumTo() {
		return sumTo;
	}
	
	public int[] getResults() {
		return results;
	}
	
	public String opsToString() {		
		String ret = "";
		if(isSorted) { 
			ret = ret+"  Chunks: ";
			//Add each  operand in results array
			for(int op : results) { 
				ret = ret+op+" ";
			}
		}
		return ret;
	}
	
}