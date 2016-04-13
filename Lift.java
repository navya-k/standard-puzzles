import java.io.FileInputStream;
import java.io.IOException;
import java.util.StringTokenizer;

public class Lift
{
	String[][] requestsArray; 
	String[][] currentDirectionRequestsArray;
	int currentTime=0;
	int currentDestination,numberOfRequests;
	
	public static void main(String args[]) throws IOException
	{
		Lift lift=new Lift();
		lift.createRequestArray(args);
		lift.processLift();
	}
	private void createRequestArray(String args[]) throws IOException
	{
		String s="";
		int c;
		
		// Take input from the file
		FileInputStream fs=new FileInputStream(args[0]);
		while((c=fs.read())!=-1)
			s+=Character.toString((char)c);
				
		//Tokenize Input
		StringTokenizer strTokens=new StringTokenizer(s,"|\n\r",false);
		
		// count number of requests
		numberOfRequests=(strTokens.countTokens())/4;
		
		// define array sizes
		requestsArray=new String[numberOfRequests][4];
		currentDirectionRequestsArray=new String[numberOfRequests][4];
		
		//store input in 2D request array
		for(int i=0;i<numberOfRequests;i++)
		{
			for(int j=0;j<4;j++)
			{
				if(strTokens.hasMoreTokens())
					requestsArray[i][j]=strTokens.nextToken();
			}
		}
	}
		
	private void processLift() 
	{	
		int currentRow=0,currentLiftPosition=0;
		String currentDirection="";
		int currentTargetFloor,difference;
		
		System.out.println("Idle at floor 0");
		
		//loop thru each request in the requestsArray
		for(int i=0;i<numberOfRequests;i++)
		{
			if(requestsArray[i][0]!=null && !(requestsArray[i][0].equals(" ")))
			{
				// Store row number, current direction and current time
				currentRow=i;
				currentTargetFloor=Integer.parseInt(requestsArray[i][0]);
				if(currentDirection.equals(""))
					currentDirection=requestsArray[i][1];
				else 
				{
					difference=currentLiftPosition-currentTargetFloor;
					if(difference>0) currentDirection="down";
					else currentDirection="up";
				}
				if(currentTime==0)
					currentTime=Integer.parseInt(requestsArray[i][2]);		
				
				//print time, source and destination floors
				System.out.printf("\nTIME - %d seconds: ",currentTime);
				System.out.printf("Going %s from floor %d to floor %d \n",currentDirection,currentLiftPosition,currentTargetFloor);
				
				
				//check for intermediate requests before finishing the 1st request
				currentLiftPosition=processRequest(currentRow,currentTargetFloor,currentDirection,currentLiftPosition);	
			}
		}
		// print total time
		System.out.println("\nTOTAL TRAVEL TIME: "+currentTime);
	}
	
	private int processRequest(int currentRow,int currentTargetFloor,String currentDirection,int startingPosition) 
	{
		int i=currentRow,p=0;
		
		//make sure current direction requests array is empty
		emptyCurrentDirectionQueue();	
		
		// short list all requests in the current direction
		while(p<4 && ++i<numberOfRequests)
		{
			if(requestsArray[i][1].equals(currentDirection))
			{	
				for(int q=0;q<4;q++)
					currentDirectionRequestsArray[p][q]=requestsArray[i][q];
				p++;
			}
		}
				
		//Check for requests before reaching the source of the current request 
		checkRequestsAtIntermediateFloors(currentTargetFloor,currentDirection,startingPosition);
				
		// update Travel Time since source is reached
		if(currentTime<=currentTargetFloor)
			currentTime=currentTargetFloor;
		else currentTime+=(Math.abs(currentTargetFloor-startingPosition));
	
		// store source in a temporary variable
		int currentSource=currentTargetFloor;
		
		//take destination for the source
		currentTargetFloor=Integer.parseInt(requestsArray[currentRow][3]);
		
		// print travel time , source and destination floors
		System.out.printf("\nTIME - %d seconds: ",currentTime);
		System.out.printf("Going %s from floor %d to Floor %d \n",currentDirection,currentSource,currentTargetFloor);
		
		
		//Now that the source of the original request is reach, check for requests before reaching the destination of the current request 
		checkRequestsAtIntermediateFloors(currentTargetFloor,currentDirection,startingPosition);
		
		// update travel time
		if(currentTime<=currentTargetFloor)
			currentTime=currentTargetFloor;
		else 
			currentTime+=(Math.abs(currentSource-currentTargetFloor));
			
		// eliminate current request from original array as it is served
		eliminateFromOriginalRequestsArray(currentRow,currentSource);
		
		//return lift's current position
		return currentTargetFloor;

	}

	private void emptyCurrentDirectionQueue() 
	{
		for(int i=0;i<numberOfRequests;i++)
		{
			for(int j=0;j<4;j++)
				currentDirectionRequestsArray[i][j]=null;
		}
	}

	private void eliminateFromOriginalRequestsArray(int currentRow, int target) 
	{
		for(int p=0;p<5;p++)
		{
			if(requestsArray[p][0].equals(Integer.toString(target)) && p==currentRow)
			{
				for(int q=0;q<4;q++)
					requestsArray[p][q]=" ";
			}
		}
	}

	private void eliminateCurrentDirectionQueue(int i)
	{
		int k;
		String temp;
		
		for(int p=0;currentDirectionRequestsArray[p][0]!=null;p++)
		{
			if(p==i)
			{				
				for( k=i+1;currentDirectionRequestsArray[k][0]!=null;k++)
				{
					for(int l=0;l<4;l++)
					{
						temp=currentDirectionRequestsArray[k][l];
						currentDirectionRequestsArray[p][l]=temp;
					}
				}
				for(int q=0;q<4;q++)
					currentDirectionRequestsArray[k-1][q]=null;
			}
		}
		
	}
	private void eliminateIntermediateRequestFromRequestsArray(int i,int target)
	{
		for(int p=0;p<5;p++)
		{
			if(requestsArray[p][0]==currentDirectionRequestsArray[i][0])
			{
				for(int q=0;q<4;q++)
				{
					requestsArray[p][q]=" ";
				}
			}
		}

	}

	private void checkRequestsAtIntermediateFloors(int currentTargetFloor,String direction,int currentLiftPosition)
	{	
		int intermediateFloorDestination,thisFloor,secondWhenPressed;
			
		// loop through short listed queue
		for(int i=0;currentDirectionRequestsArray[i][0]!=null;i++)
		{
			// currently indexed floor and time when button is pressed are stored
			thisFloor=Integer.parseInt(currentDirectionRequestsArray[i][0]);
			secondWhenPressed=Integer.parseInt(currentDirectionRequestsArray[i][2]);
			
			// stop at passing by floors provided time the button was pressed is less than the time it takes to reach that floor
			if(currentLiftPosition<thisFloor && thisFloor<currentTargetFloor && (secondWhenPressed)<=(currentTime+thisFloor))
			{		
				System.out.print("\n\nIntermediate Request Found:\n"); 
				
				// update Travel time and print it
				currentTime+=(Integer.parseInt(currentDirectionRequestsArray[i][2]));
				System.out.printf("\n\tTIME - %d seconds: ",currentTime);
				System.out.print("Stop at floor number "+thisFloor+"\n");
				currentLiftPosition=thisFloor;
				
				//Take destination
				intermediateFloorDestination=Integer.parseInt(currentDirectionRequestsArray[i][3]);
				
				// Is destinaion of intermediate request less than target request floor
				if(	intermediateFloorDestination<=currentTargetFloor)
				{
					// finish servicing intermediate request 
					currentTime=intermediateFloorDestination;
					System.out.printf("\n\tTIME - %d seconds: ",currentTime);
					System.out.printf("Stop at floor number  %d\n",intermediateFloorDestination);
					currentLiftPosition=intermediateFloorDestination;
					
					System.out.print("\nIntermediate Request Served:\n\n"); 
					
					//eliminate request from currentDirectionQueue and requestsArray
					eliminateIntermediateRequestFromRequestsArray(i,thisFloor);
					eliminateCurrentDirectionQueue(i);
				}
				else System.out.println("\tCurrent passenger requested cant be served.\n\tGet down at floor "+currentTargetFloor);
			}
		}
	}
}