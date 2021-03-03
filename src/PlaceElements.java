import java.util.ArrayList;

public class PlaceElements {
	/**
	 * Number of elements in array
	 */
	private int NumElem;
	
	private int NumElemInv;
	/**
	 * Desired size of elements array
	 */
	private int ArraySize;
	/**
	 * array of chunk sizes. A "Chunk" can be though of in two ways:
	 * 	1) a grouping of similar elements in an array, or 
	 *  2) the blank space between each element.
	 */
	private int[] Chunks;
	/**
	 * a visual representation of elements, spaced apart by chunks, 
	 * in an array
	 */
	private boolean[] Elements;
	
	private boolean[] Inverse;
	
	/**
	 * Constructor for PlaceElements. Sets all fields (NumElem, ArraySize, and 
	 * both arrays)
	 */
	public PlaceElements(int numElem, int arraySize) {
		this.NumElem = numElem;
		this.ArraySize = arraySize;
		this.NumElemInv = ArraySize-NumElem; //inverse equivalent of numElem
		createChunkArray(NumElem); //Chunks of nonInverse
		createElementsArray(); //Elements of inverse array
		createInvertArray(Elements); //Inverse Elements array to get the array of original numElem		
	}
	
	//ARRAY METHODS
	/**
	 * Creates Chunks[] and Elements[]. This method is also responsible for 
	 * managing inversion. 
	 */
	private void createArrays() {
		if(NumElem>ArraySize/2) { //if there are more element cells than chunk cells,
			int inverse = ArraySize-NumElem; //inverse equivalent of numElem
			createChunkArray(inverse); //Chunks of inverse
			createElementsArray(); //Elements of inverse array
		}else {
			createChunkArray(NumElem); //Chunks of nonInverse
			createElementsArray();
		}
	}
	
	/**
	 * Calculates the size of each chunk in the elements array. 
	 * These values are stored in the chunks array.
	 */
	private void createChunkArray(int numElem) {
		int totalChunk = ArraySize-numElem; //Total chunk size, aka how many empty cells are there
		int numChunks;	//How many chunks, aka size of chunk array
		
		if(numElem == 0) { //If zero elements,
			numChunks = 1; //there will only be one chunk
		}else {
			if((ArraySize/numElem) == 2) {
				numChunks = ArraySize/2;
			}else {
				numChunks = numElem+1;
			}
		}
		
		FindOperands ots = new FindOperands(numChunks,totalChunk);
		Chunks = ots.getResults(); //n chunks that sum to totalChunk
	}
	
	/**
	 * Places elements in Elements[] array. Each element is spaced apart evenly
	 * based off the chunk sizes in Chunks[]
	 */
	private void createElementsArray() {
		int elemCounter = 0;
		this.Elements = new boolean[ArraySize];
		int chunkIndex = 0; //Determines which chunk is being counted
		for(int i=0; i<ArraySize; i++) { 
			i = i + Chunks[chunkIndex]; //move index to make a chunk of falses
			if(i<ArraySize && elemCounter<this.NumElem) { 		//prevents placing an element out of bounds
				Elements[i] = true; 	//Place element
				chunkIndex++; 			//set the next chunk sizes
				elemCounter++;
			}
		}
		if(elemCounter != this.NumElem) {
			System.err.println(elemCounter+" != "+this.NumElem);
		}
		
	}
	
	/**
	 * Inverts the Elements array. So, if the original array is [--U-], 
	 * the inverted array will be [UU-U}. See comments in invertArray() 
	 * for an explanation to why this is necessary
	 * @return an inverted Elements array
	 */

	private void createInvertArray(boolean[] arr) {
		/*
		 * Optimal element placement is inversely symmetrical.
		 * For example, an array of size 5 has a symmetry line between 2 and 3:
		 * 
		 * numElem  | element placement
		 * 0		| [-----]
		 * 1		| [--X--]
		 * 2		| [-X-X-]
		 * --------SYMETRY LINE------
		 * 3		| [X-X-X]
		 * 4		| [XX-XX]
		 * 5		| [XXXXX]
		 * 
		 * So, 5 is inverse of 0, 4 is inverse of 2, and 3 is inverse of 2. 
		 * Note that, for numElem<=2, chunks are "-", whereas for numElem>2, chunks are "X". 
		 * Since element placement is calculated based off these chunks, the algorithm creates 
		 * an array of its inverse for (numElem > (arraySize/2)). 
		 * Then, each element in this array is inverted to get the final answer
		 */
		this.Inverse = new boolean[arr.length];
		for(int i=0; i<arr.length; i++) {
			if(arr[i]) {
				Inverse[i] = false;
			}else {
				Inverse[i] = true;
			}
		}
	}
	
	//GETTER METHODS
	
	/**
	 * Getter method for Elements array
	 */
	public boolean[] getElements() {
		return this.Elements;
	}
	
	
	/**
	 * Getter method for Chunks array
	 */
	public int[] getChunks() {
		return this.Chunks;
	}
	
	public String knitToString() {
		String ret = elementsToString();
				//+"\n\t" + notationToString()
				//+"\n\t" + filledToString();
		return ret;
	}
	
	private String notationToString(){
		String notation = combineLinesSlashes();
		String ret = "  Notation: [";
		ret = ret+ notation;
		ret = ret+"]";
		
		return ret;
	}
	
	private String combineLinesSlashes() {
		String slashes = buildSlashes();
		String lines = buildLines(slashes);
		String sl = ""; //Strings and slashes combined
		
		if(slashes.length()==lines.length()) {
			for(int i=0; i<lines.length();i++) {
				if(slashes.charAt(i) == '/') {
					sl = sl + "/";
				}else if(slashes.charAt(i) == '\\') {
					sl = sl + "\\";
				}else if(lines.charAt(i)=='|') {
					sl = sl + "|";
				}else {
					sl = sl + " ";
				}
			}
		}
		return sl;
	}
	
	private String buildLines(String slashes) {
		String lines = "";
		int index=0;
		for(int i=0; i<this.ArraySize; i++) {
			if(Elements[index]) { //If there is an element at current index
				if(needsLine(slashes,index)) {
					lines = lines + "|"; 	//Add a line
				}else {
					lines = lines + " ";
				}
			}
		}
		return lines;
	}
	private boolean needsLine(String slashes, int index) {
		boolean ret = false;
		if(index>1 && index<slashes.length()-1) {
			if(slashes.charAt(index-1)=='/' || slashes.charAt(index+1)=='\\') {
				ret = true;
			}
		}else if(index>1){
			if(slashes.charAt(index-1)=='/') {
				ret = true;
			}
		}else if(index<slashes.length()-1) {
			if(slashes.charAt(index+1)=='\\') {
				ret = true;
			}
		}
		
		return ret;
	}
	
	private String buildSlashes() {
		String ret = "";
		for(int i=0; i<Elements.length; i++) {
			if(Elements[i]==true) {
				ret = ret+" ";
			}else {
				
				int elemIndex = findIndexOfClosestElem(i);
				if(elemIndex==-1) { //No closest element found
					ret = ret+" ";
				}else if(elemIndex-i > 0) {
					ret = ret+"/";
				}else if(elemIndex-i < 0) {
					ret = ret+"\\";
				}
			}
		}
		return ret;
	}
	
	//TODO This is absolutely disgusting
	private int findIndexOfClosestElem(int skipIndex) {
		boolean elemToRight = false;
		boolean elemToLeft = false;
		int leftBound;
		int rightBound;
		
		//Move right from skipped index until index reaches an element or the end of the list
		int i = skipIndex;
		while(Elements[i]!=true && i<Elements.length-1) {
			i++;
			if(Elements[i]==true) {
				elemToRight = true;
			}
		}
		rightBound = i; 
		
		//Move left from skipped index  until index reaches an element or the end of the list
		i = skipIndex;
		while(Elements[i]!=true && i>0) {
			i--;

			if(Elements[i]==true) {
				elemToLeft = true;
			}
		}
		leftBound = i;
		
		int rightDistance = rightBound - skipIndex;
		int leftDistance = skipIndex - leftBound;

		if(elemToRight && elemToLeft) { //If there's both right and left elements
			if(rightDistance<leftDistance) {
				return rightBound; 
			}else {
				return leftBound;
			}
		}else if(elemToRight) { //If only right element
			return rightBound;
		}else if(elemToLeft) { 	//If only left element
			return leftBound;
		}else { 				//If neither bounds are elements 
			return -1;
		}
	}
	
	
	
	private String filledToString() {
		String ret = "  Filled:   [";
		for(boolean elem : Elements) {
			ret = ret+" ";
		}
		ret = ret+"]";
		return ret;
	}
	
	
	/**
	 * ToString method for Elements array
	 */
 	public String elementsToString() {
 		//int numElem = 0;
		String ret = "  Elements: [";
		for(boolean elem : Elements) {
			if(elem==true) {
				ret = ret+"*";
				//numElem++;
			}else {
				ret = ret+"-";
			}
		}
		ret = ret+"]";
		//ret = ret+numElem;
		
		return ret;
	}
 	
	/**
	 * ToString method for Elements array
	 */
 	public String inverseToString() {
 		//int numElem = 0;
		String ret = "  Inverse:  [";
		for(boolean elem : Inverse) {
			if(elem) {
				ret = ret+"*";
				//numElem++;
			}else {
				ret = ret+"-";
			}
		}
		ret = ret+"]";
		//ret = ret+numElem;
		
		return ret;
	}
	
 	/**
	 * ToString method for Chunks array
	 */
 	public String chunksToString() {
		String ret = "  Chunks:   ["; //Open bracket
		for(int i=0; i<Chunks.length;i++) {
			int curChunk = Chunks[i];
			if(curChunk>0) {
				ret = ret+curChunk; //Add chunk value 
				while(curChunk>1) { //Add respective number of spaces
					ret = ret+" ";
					curChunk = curChunk-1;
				}
			}
			
			if(i<Chunks.length-1) { //If its not the last chunk in the array
				ret = ret+" "; //Add an extra space
			}
		}
		ret = ret+"]"; //Close bracket
		return ret;
	}
}
