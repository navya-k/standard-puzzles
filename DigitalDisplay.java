import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DigitalDisplay 
{
	/* Each number in the display is a collection of 7 line segments.
	 * Declare a 2D Array where each row represents the input number 
	 * and each column represents one of the 7 segments used for that number.
	 * Value 0 or 1 of each element decide whether the segment is to be printed or not.
	 */ 
	static int[][] combinations=new int[][]{{1,1,1,0,1,1,1},{0,0,1,0,0,1,0},{1,0,1,1,1,0,1},{1,0,1,1,0,1,1},{0,1,1,1,0,1,0},
									{1,1,0,1,0,1,1},{1,1,0,1,1,1,1},{1,0,1,0,0,1,0},{1,1,1,1,1,1,1},{1,1,1,1,0,1,1}};
									
	// Symbols for horizontal and vertical line segments
	static String horLines[]={""," ---- "};
	static String verLines[]={" ","|"};
	
	public static void main(String args[]) throws IOException
	{
		String result="";
		System.out.println("Enter a Number below 9 :");
		
		// Take input from user 
		BufferedReader buf=new BufferedReader(new InputStreamReader(System.in));
		int number=Integer.parseInt(buf.readLine());
		
		// Fetch each element of number row from combinations array and concatenate to result
		result= horLines[combinations[number][0]]+"\n"+
				verLines[combinations[number][1]]+"    "+
				verLines[combinations[number][2]]+"\n"+
				horLines[combinations[number][3]]+"\n"+
				verLines[combinations[number][4]]+"    "+
				verLines[combinations[number][5]]+"\n"+
				horLines[combinations[number][6]];
		
		System.out.println(result);
	}
}