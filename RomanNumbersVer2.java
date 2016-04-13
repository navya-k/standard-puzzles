/* Asks for a natural number between 0 and 1000 
 * and converts it into its 
 * Roman numeral equivalent. */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RomanNumbersVer2 
{
	public static void main(String args[]) throws IOException
	{
		System.out.println("Enter a Number below 1000 :");
		
		// Take user input
		BufferedReader buf=new BufferedReader(new InputStreamReader(System.in));
		int natNum=Integer.parseInt(buf.readLine());
		
		// convert method call and print output
		String romanNumeral=convert(natNum);
		System.out.print(romanNumeral);
	}
	public static String convert(int n) 
	{
		String result="";
		int i=3,r=0;
		int[] temp=new int[3];
		
		// Declare a 2D array - rows represent hundreds,tens and units places resp
		String[][] symbols = {{"","C","CC","CCC","CD","D","DC","DCC","DCCC","CM"},
						 {"","X","XX","XXX","XL","L","LX","LXX","LXXX","XC"},
						 {"","I","II","III","IV","V","VI","VII","VIII","IX"}};
			
		//Storing digits of the number in an temporary array
		while(n!=0 && n<1000)
		{	
			r=n%10;
			temp[--i]=r; 
			n=n/10;
		}
		
		// fetching Roman numerals from symbols array 
		for(int j=0;j<temp.length;j++) 
			// look for jth row & temp[j]th column value and concatenate to result
			result= result + symbols[j][temp[j]];  
		
		return result;
	}
}