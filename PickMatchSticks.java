/* Given n match sticks, a player can pick 1 or 2 or 3 sticks in a single turn.
 * Remaining number of sticks will be displayed.
 * The user plays with the computer.
 * The party that picks the last set of sticks wins.
 * Player can choose to who starts the game.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PickMatchSticks 
{
	int maxSticks;
	String isPlayerFirst;
	private void captureInput(String args[]) throws IOException
	{
		System.out.println("Enter value for Starting number of Sticks greater than 3:");
		BufferedReader buf1=new BufferedReader(new InputStreamReader(System.in));
		maxSticks=Integer.parseInt(buf1.readLine());
		
		System.out.println("Would you like to start first (Y/N) : ");
		BufferedReader buf2=new BufferedReader(new InputStreamReader(System.in));
		isPlayerFirst=buf2.readLine();
	}
	
	private void processPick(String args[]) throws IOException
	{
		int temp,end=0;
		int numberPickedByPlayer,numberPickedByComp;
		
		captureInput(args);
		
		int n=maxSticks;
		
		// Does computer start the game
		if(isPlayerFirst.equalsIgnoreCase("n") && n>0)
		{
			temp=n%4;
			if(temp==0)
				numberPickedByComp=1;
			else numberPickedByComp=temp;
				
			n=n-numberPickedByComp;
			System.out.printf("Computer Picks %d Stick(s) \nExisting Sticks =%d",numberPickedByComp,n);
		}
		while(n>0)
		{
			System.out.print("\nPlayer's Turn: Enter a value between 1 and 3 : ");
			BufferedReader buf2=new BufferedReader(new InputStreamReader(System.in));
			numberPickedByPlayer=Integer.parseInt(buf2.readLine());
			
			n=n-numberPickedByPlayer;
			if(n==0)
			{	
				end=1;
				break;
			}
			temp=n%4;
			if(temp==0)
				numberPickedByComp=1;
			else numberPickedByComp=temp;
			
			n=n-numberPickedByComp;
			System.out.printf("\nComputer's Turn : It Picked %d Stick(s) \nExisting Sticks = %d \n",numberPickedByComp,n);
		}
		if(end==1)
			System.out.printf("\nPlayer Wins!!");
		else
			System.out.printf("\nComputer Wins!!");
	}	
	
	public static void main(String args[]) throws IOException
	{
		PickMatchSticks pickSticks = new PickMatchSticks();
		pickSticks.processPick(args);
	}
}