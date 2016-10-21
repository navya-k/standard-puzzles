import java.util.HashMap;
import java.util.Scanner; 


public class Anagram {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		//	        String a = in.next();
		//	        String b = in.next();
		System.out.println(numberNeeded("abcdanbb", "anb"));
	}

	public static int numberNeeded(String first, String second) {
		/* This is how to declare HashMap */

		HashMap<String, Integer> hmap1 = createHashFromString(first);
		HashMap<String, Integer> hmap2 = createHashFromString(second);

		/* Display content using Iterator*/
		int discardCount = 0;

		for (String key : hmap1.keySet()) {
			System.out.println("key is: "+ key);
			if(hmap2.containsKey(key)){
				System.out.println("counts are: "+ hmap1.get(key) + " " + hmap2.get(key));
				if(hmap1.get(key) != hmap2.get(key))
					discardCount+= hmap1.get(key) - hmap2.get(key);
			}
			else discardCount++;
		}
		return discardCount;
	}

	private static HashMap<String, Integer> createHashFromString(String s){
		HashMap<String, Integer> hmap =  new HashMap<String, Integer>();
		int charCount = 0;
		for(int i = 0; i< s.length(); i++){
			String currentChar = s.substring(i, i+1);
			if(hmap.containsKey(currentChar))
				charCount = hmap.get(currentChar) + 1;
			else charCount = 1;
			/*Adding elements to HashMap*/
			hmap.put(currentChar, charCount);
		}
		return hmap;
	}
}
