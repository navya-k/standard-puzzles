import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
	
public class NQueensWithRecursion {

		int maxQueens=0,sol=0;
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
		
		private void processQueensWithRecursion(String args[]) throws IOException
		{
			int i=0;
			captureInput(args);
			
			//Define an array of size n
			arrayOfQueenPostions=new int[maxQueens];
			
			// Initialize array to avoid conflicts
			for(i=0;i<maxQueens;i++)
				arrayOfQueenPostions[i]=-1; 
			
			// find positions for queens such that no two queens attack each other 
			findPositions(0);
								
			System.out.println("\n\nFinished Finding Solutions");
		
		}
		
		public static void main(String args[]) throws IOException
		{
			NQueensWithRecursion nQueens = new NQueensWithRecursion();
			nQueens.processQueensWithRecursion(args);
		}
		
		private void printOutput(int solNum) 
		{
			// print the position values where each value denotes the column number.
			System.out.printf("\n\nSolution %d : ",solNum);
			for(int i=0;i<maxQueens;i++)
				System.out.print(arrayOfQueenPostions[i]+" ");
			
			for(int i=0;i<maxQueens;i++)
			{
				System.out.println();
				for(int j=0;j<maxQueens;j++)
				{
					if(arrayOfQueenPostions[i]==j)
						System.out.print("Q ");
					else
						System.out.print("* ");
				}
			}
		}

		private void findPositions(int row) 
		{
			for(int col=0;col<maxQueens;++col)
			{
				if(isAvailable(row, col))
				{
					arrayOfQueenPostions[row]=col;
					if (row==maxQueens-1)
						printOutput(++sol);
					else 
						findPositions(row+1);
				}
			}
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