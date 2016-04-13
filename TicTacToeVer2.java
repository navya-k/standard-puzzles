import java.applet.Applet;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

	
@SuppressWarnings("serial")
public class TicTacToeVer2 extends Applet implements ActionListener
{
	int[][] arrayOfPositions=new int[3][3];
	JTextField[][] textFieldArray=new JTextField[3][3];
	Font font=new Font("", Font .BOLD, 70);
		
	Boolean hasPlayerWon=false, hasComputerWon=false;
	int compMoveRow,compMoveCol;
	int moves=0;
		
	public void init()
	{
		// Create the game window
		setLayout(new GridLayout(3,3));
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				textFieldArray[i][j]=new JTextField();
				textFieldArray[i][j].setFont(font);
				add(textFieldArray[i][j]);
				textFieldArray[i][j].addActionListener(this);
			}
		}
	}
		
	public void actionPerformed(ActionEvent arg0) 
	{
		String temp="";
		//Loop through Text Fields 
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{	
				// is the Text Field i used
				if(arg0.getSource()==textFieldArray[i][j])
				{	
					temp=textFieldArray[i][j].getText();
					if(temp.equalsIgnoreCase("x"))
					{
						// Record Player's Move 
						arrayOfPositions[i][j]=1;
						moves++;
						textFieldArray[i][j].setEnabled(false);
						
						// Check player's win condition
						hasPlayerWon=hasWon(1);
							
						if(!hasPlayerWon)
						{
							// Play Computer's Move
							compNextMove();
							//record computer's move
							arrayOfPositions[compMoveRow][compMoveCol]=2;
							moves++;
							textFieldArray[compMoveRow][compMoveCol].setText("o");
							textFieldArray[compMoveRow][compMoveCol].setEnabled(false);
							
							// Check computer's win
							hasComputerWon=hasWon(2);
						}
					}
					// If player enters character(s) other than x
					else
					{
						System.out.println("Input Character not understood");
						System.exit(0);
					}
				}
			}
		}
		if(hasPlayerWon && !hasComputerWon)
			exit("PLAYER WON!!");
		if(hasComputerWon && !hasPlayerWon)
			exit("PLAYER LOST!!");
		else if(moves>=9) 
			exit("DRAW!!");
	}
		
	private Boolean hasWon(int playerValue) 
	{
		if(arrayOfPositions[0][0]==playerValue && arrayOfPositions[0][1]==playerValue && arrayOfPositions[0][2]==playerValue )
			return true;
				
		if(arrayOfPositions[1][0]==playerValue && arrayOfPositions[1][1]==playerValue && arrayOfPositions[1][2]==playerValue)
			return true;
			
		if(arrayOfPositions[2][0]==playerValue && arrayOfPositions[2][1]==playerValue && arrayOfPositions[2][2]==playerValue)
			return true;
			
		if(arrayOfPositions[0][0]==playerValue && arrayOfPositions[1][0]==playerValue && arrayOfPositions[2][0]==playerValue)
			return true;
			
		if(arrayOfPositions[0][1]==playerValue &&	arrayOfPositions[1][1]==playerValue && arrayOfPositions[2][1]==playerValue)
			return true;
			
		if(arrayOfPositions[0][2]==playerValue &&	arrayOfPositions[1][2]==playerValue && arrayOfPositions[2][2]==playerValue)
			return true;
		
		if(arrayOfPositions[0][0]==playerValue && arrayOfPositions[1][1]==playerValue && arrayOfPositions[2][2]==playerValue)
			return true;
			
		if(arrayOfPositions[0][2]==playerValue && arrayOfPositions[1][1]==playerValue && arrayOfPositions[2][0]==playerValue)
			return true;
			
		return false;
	}

	private void compNextMove() 
	{
		if(moves>=3)
		{
			//1. two Os in a single dimension OR two Xs in a single dimension
			if(TwoInRow(2) || TwoInRow(1))
				return;
				
			//2. Create Fork for O if possible
			for(int row=0;row<3;row++)
			{
				for(int col=0;col<3;col++)
				{
					// Is fork possible  when O is placed at row,col position
					if(arrayOfPositions[row][col]==0 && forkPossible(row,col,2))
					{
						compMoveRow=row;
						compMoveCol=col;
						return;
					}
				}
			}
			//3. Block Opponent's Fork
			boolean flag=true;
			int row1,col1=0;
			for(int row=0;row<3;row++)
			{
				for(int col=0;col<3;col++)
				{
					// Is the opponent fork possible at row,col position
					if(arrayOfPositions[row][col]==0 && forkPossible(row,col,1))
					{
						// Block opponent fork
						arrayOfPositions[row][col]=2;
						
						// Is another fork enabled
						for(row1=0;row1<3;row1++)
						{
							for(col1=0;col1<3;col1++)
							{
								if(arrayOfPositions[row1][col1]==0 && forkPossible(row1,col1,1) && row!=row1 && col!=col1)
								{
									// disable both fork options and place O elsewhere
									arrayOfPositions[row][col]=-1;
									arrayOfPositions[row1][col1]=-1;
									flag=false;
								}
							}
						}
						// If only one fork exists confirm move
						if(flag)
						{
							compMoveRow=row;
							compMoveCol=col;
							return;
						}
					}
				}
			}
		}
			
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				if(arrayOfPositions[i][j]==-1)
					arrayOfPositions[i][j]=0;
			}	
		}
		
		//4. if center is empty
		if(arrayOfPositions[1][1]==0)
		{
			compMoveRow=1;
			compMoveCol=1;
			return;
		}
			
		//6. Opposite Corners
		int[] rowColResult=new int[2];
		rowColResult=oppositeCorners();
		if(rowColResult[0]!=-1 && rowColResult[1]!=-1)
		{
			arrayOfPositions[rowColResult[0]][rowColResult[1]]=2;
			moves++;
			textFieldArray[rowColResult[0]][rowColResult[1]].setText("o");
			textFieldArray[rowColResult[0]][rowColResult[1]].setEnabled(false);
			return;
		}
					
		// 5. is corner Empty
		if(isCornerEmpty())
			return;
			
		//6. is side Empty
		if(isSideEmpty())
			return;
	}
	// End of computerMove()
		
	private int[] oppositeCorners() 
	{
		int[] coords=new int[2];
		coords[0]=-1;
		coords[1]=-1;
		if(arrayOfPositions[0][0]==1 && arrayOfPositions[2][2]==0)
		{
			coords[0]=2;
			coords[1]=2;
			return coords;
		}
		if(arrayOfPositions[0][2]==1 && arrayOfPositions[2][0]==0) 
		{
			coords[0]=2;
			coords[1]=0;
			return coords;
		}
		if(arrayOfPositions[2][0]==1 && arrayOfPositions[0][2]==0)
		{
			coords[0]=0;
			coords[1]=2;
			return coords;
		}
		if(arrayOfPositions[2][2]==1 && arrayOfPositions[0][0]==0)
		{
			coords[0]=0;
			coords[1]=0;
			return coords;
		}
		return coords;
	}
	
	private boolean TwoInRow(int xOrO) 
	{
		String comparisonSample="";
		
		// for each row find in two signs in a row exist
		for(int i=0;i<3;i++)
		{
			comparisonSample="";
			for(int j=0;j<3;j++)
				comparisonSample+=Integer.toString(arrayOfPositions[i][j]);
				
			if(compareCurrentStringPattern("rows",comparisonSample,xOrO) && arrayOfPositions[i][compMoveCol]==0 )
			{	
				compMoveRow=i;
				return true;
			}
		}
		// Two in a Column
		for(int i=0;i<3;i++)
		{
			comparisonSample="";
			for(int j=0;j<3;j++)
				comparisonSample+=Integer.toString(arrayOfPositions[j][i]);
					
			if(compareCurrentStringPattern("cols",comparisonSample,xOrO) && arrayOfPositions[compMoveRow][i]==0)
			{	
				compMoveCol=i;
				return true;
			}
					
		}
		// Two in a diagonal1
		comparisonSample="";
		for(int i=0;i<3;i++)
			comparisonSample+=Integer.toString(arrayOfPositions[i][i]);
			
		if(compareCurrentStringPattern("dia1",comparisonSample,xOrO) && arrayOfPositions[compMoveRow][compMoveCol]==0 )
			return true;
			
		// Two in a diagonal2
		int j=2;
		comparisonSample="";
		for(int i=0;i<3;i++)
			comparisonSample+=Integer.toString(arrayOfPositions[i][j--]);
				
		if(compareCurrentStringPattern("dia2",comparisonSample,xOrO) &&  arrayOfPositions[compMoveRow][compMoveCol]==0)
			return true;
				
		return false;
	}
	
	private boolean forkPossible(int row, int col,int xOrO) 
	{
		String comparisonSample1="",comparisonSample2="",comparisonSample3="",comparisonSample4="";
		arrayOfPositions[row][col]=xOrO;
			
		// set sample patterns obtained if position at row,col is occupied 
		for(int j=0;j<3;j++)
		{	
			//row pattern
			comparisonSample1+=Integer.toString(arrayOfPositions[row][j]);
			//column pattern
			comparisonSample2+=Integer.toString(arrayOfPositions[j][col]);
			// diagonal 1 pattern
			comparisonSample3+=Integer.toString(arrayOfPositions[j][j]);
			//diagonal 2 pattern
			for(int l=2;l>=0;l--)
				comparisonSample4+=Integer.toString(arrayOfPositions[j][l]);
		}
			
		// Case 1. Row and Column
		if(compareCurrentStringPattern("rows",comparisonSample1,xOrO))
		{
			if(compareCurrentStringPattern("cols",comparisonSample2,xOrO))
				return true;
		}
			
		// case 2. row and Diagonal1		
		if(compareCurrentStringPattern("rows",comparisonSample1,xOrO))
		{
			if(compareCurrentStringPattern("dia1",comparisonSample3,xOrO))
				return true;
		}
			
		// case 3. Row and Diagonal 2
		if(compareCurrentStringPattern("cols",comparisonSample1,xOrO))
		{
			if(compareCurrentStringPattern("dia2",comparisonSample4,xOrO))
				return true;
		}
		
		// Case 4 Col and diagonal 1
		if(compareCurrentStringPattern("cols",comparisonSample2,xOrO))
		{
			if(compareCurrentStringPattern("dia1",comparisonSample3,xOrO))
				return true;
		}
		
		// Case 5 Col and diagonal 2
		if(compareCurrentStringPattern("cols",comparisonSample2,xOrO))
		{
			if(compareCurrentStringPattern("dia2",comparisonSample4,xOrO))
				return true;
		}
			
		arrayOfPositions[row][col]=0;
		return false;
	}
		
	private boolean isCornerEmpty() 
	{
		boolean flag=true;
		if(arrayOfPositions[0][0]==0)
		{
			compMoveRow=0;
			compMoveCol=0;
		}
		else if(arrayOfPositions[0][2]==0) 
		{
			compMoveRow=0;
			compMoveCol=2;
		}
		else if(arrayOfPositions[2][0]==0)
		{
			compMoveRow=2;
			compMoveCol=0;
		}
		else if(arrayOfPositions[2][2]==0)
		{
			compMoveRow=2;
			compMoveCol=2;
		}
		else flag=false;
			
		return flag;
	}
		
	private boolean isSideEmpty() 
	{
		boolean flag=true;
		if(arrayOfPositions[0][1]==0)
		{
			compMoveRow=0;
			compMoveCol=1;
		}
		else if(arrayOfPositions[1][0]==0) 
		{
			compMoveRow=1;
			compMoveCol=0;
		}
		else if(arrayOfPositions[1][2]==0)
		{
			compMoveRow=1;
			compMoveCol=2;
		}
		else if(arrayOfPositions[2][1]==0)
		{
			compMoveRow=2;
			compMoveCol=1;
		}
		else flag=false;
			
		return flag;
	}

	private boolean compareCurrentStringPattern(String type,String comparisonSample, int xOrO) 
	{
		String val=Integer.toString(xOrO);
		String pattern1=val+"0"+val, pattern2=val+val+"0", pattern3="0"+val+val;
		int temp=-1;
		
		if(comparisonSample.equals(pattern1))
			temp=1;
		if(comparisonSample.equals(pattern2))
					temp=2;
		if(comparisonSample.equals(pattern3))
				temp=0;
			
		if(temp!=-1)
		{
			if(type.equals("rows"))
				compMoveCol=temp;
			
			if(type.equals("cols"))
				compMoveRow=temp;
					
			if(type.equals("dia1"))
			{
				compMoveRow=temp;
				compMoveCol=temp;
			}	
			if(type.equals("dia2"))
			{	
				compMoveRow=temp;
				if(temp==1)
					compMoveCol=temp;
				if(temp==0)
					compMoveCol=2;
				if(temp==2)
					compMoveCol=0;
			}
			return true;
		}
		return false;
	}
	
	private void exit(String string)
	{
		JOptionPane.showMessageDialog(null, string);
		System.exit(0);
	}
}