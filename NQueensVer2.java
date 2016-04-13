import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NQueensVer2 {
	
	int maxQueens=0;
	int[] arrayOfQueenPostions;
	
	private void captureInput(String args[]) throws IOException
	{
		System.out.println("Enter value n for nxn board : ");
		BufferedReader buf=new BufferedReader(new InputStreamReader(System.in));
		maxQueens=Integer.parseInt(buf.readLine());

		if(maxQueens<4)
		{
			System.out.printf("No solutions available for n value = %d",maxQueens);
			System.exit(0);
		}
	}
	
	private void processQueens(String args[]) throws IOException
	{
		int i=0;
		captureInput(args);
		
		//Define an array of size n
		arrayOfQueenPostions=new int[maxQueens];
		
		// Initialize array to avoid conflicts
		for(i=0;i<maxQueens;i++)
			arrayOfQueenPostions[i]=-1; 
		
		// find positions for queens such that no two queens attack each other 
		boolean solutionFound=findPositions(0, 0);
		int solNum=0;
		while(solutionFound)
		{
			printOutput(++solNum);// print the output in the form of a grid
			solutionFound=findPositions(maxQueens-1, (arrayOfQueenPostions[maxQueens-1]+1));// Find next solution
		}
		
		System.out.println("Finished Finding Solutions");
		
		
	}
	
	public static void main(String args[]) throws IOException
	{
		NQueensVer2 nQueens = new NQueensVer2();
		nQueens.processQueens(args);
	}
	
	private void printOutput(int solNum) 
	{
		// print the position values where each value denotes the column number.
		System.out.printf("Solution %d : ",solNum);
		for(int i=0;i<maxQueens;i++)
			System.out.print(arrayOfQueenPostions[i]+" ");
		System.out.println("\n");
		
		for(int i=0;i<maxQueens;i++)
		{
			for(int j=0;j<maxQueens;j++)
			{
				if(arrayOfQueenPostions[i]==j)
					System.out.print("Q ");
				else
					System.out.print("* ");
			}
			System.out.println();
		}
		System.out.println();
	}

	private boolean findPositions(int row, int col) 
	{
		for(;row<maxQueens;)
		{
			boolean foundPOS = false;
			while(col<maxQueens)
			{
				if(isAvailable(row,col))
				{
					arrayOfQueenPostions[row]=col;
					foundPOS = true;
					break;
				}
				col++;
			}
			
			// If found a position in the current row, search for next queen in the first column of next row
			if (foundPOS == true)
			{
				col = 0;
				++row;
				continue;
			}
			
			if (row == 0)
				return false;
			
			// Did not find slot for the queen in (row,col). Now, move the queen one slot to the left at previous row.
			col=arrayOfQueenPostions[row-1]+1;
			--row;
		}
		
		// found a position for queens.
		return true;
	}
	
	private boolean isAvailable(int i, int j) 
	{
		// loop through the rows 
		for(int p=0;p<i;p++)
		{		
			// Check if any queen exists on position p,j OR  along the diagonal of i,j
			if(arrayOfQueenPostions[p]==j || Math.abs(i-p)==Math.abs(j-(arrayOfQueenPostions[p])))
				return false;
		}
		return true;	
	}	
}