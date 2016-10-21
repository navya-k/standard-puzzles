 

import java.util.Arrays;
import java.util.Comparator;

public class Kafkuh {

	public static void main(String[] args) {
//		int n = 7;
//		int[] xPoints = {1,1,1,1,100,100,101};
//		int[] yPoints = {1,1,1,1,100,100,101};
//		int[] radii =   {1,2,4,8,20,30,4};
		
//		int n = 14;
//		int[] xPoints = {3975, 2035, 2037, 97, 3974, 99, 3972, 3974, 96, 3973, 3972, 3974, 3973, 3972};
//		int[] yPoints = {2710, 1842, 1846, 69, 2710, 72, 2713, 2710, 68, 2713, 2710, 2714, 2714, 2712};
//		int[] radii =   {33,   50,   41,   47, 16,   38, 27,   13,   50, 9,    50,   20,   40,   47};
		 
//		int[][] data = {{1, 1, 1},
//				{100, 100, 20},
//				{1, 1, 2},
//				{101, 101, 4},
//				{1, 1, 4},
//				{100, 100, 30},
//				{1, 1, 8}};
		
		int[][] data = {{3975, 2710, 33},
				{2035, 1842, 50},
				{2037, 1846, 41},
				{97, 69, 47},
				{3974, 2710, 16},
				{99, 72, 38},
				{3972, 2713, 27},
				{3974, 2710, 13},{96, 68, 50},
		{3973, 2713, 9},
		{3972, 2710, 50},
		{3974, 2714, 20},
		{3973, 2714, 40},
		{3972, 2712, 47}};
		
		
		Arrays.sort(data, new Comparator<int[]>() {
			@Override
			public int compare(final int[] entry1, final int[] entry2) {
				final int time1 = entry1[2];
				final int time2 = entry2[2];
				return Integer.compare(time2 ,time1);
			}
		});
		 
		 
		 
//		for(int i = 0; i < data.length / 2; i++)
//		{
//		    int[] temp = data[i];
//		    data[i] = data[data.length - i - 1];
//		    data[data.length - i - 1] = temp;
//		}  
	        
		 
		long startTime = System.currentTimeMillis();
	        int[] T = new int[data.length];
	        
	         
	        for(int i = 0; i < data.length; i++){ 
	        T[i] = 0;
	        }
		for(int i = 1; i < data.length; i++){ 
		  
			for(int j=i-1 ; j >=0 ; j--){
				if(CircleInsideCircle(data[i],data[j]) == 1){
				 T[i] = Math.max(T[i], T[j] + 1);
				}
				
					 
			}  
			 
		}
		int maxAlt = 0;
		for(int i = 0; i < data.length; i++){ 
		 if(maxAlt < T[i])
			 maxAlt = T[i];
			
		}
		System.out.println(maxAlt);
		 
        long endTime   = System.currentTimeMillis();
    	long totalTime = endTime - startTime;
//    	System.out.println("Time Taken "+totalTime+" milliseconds");
   
	}
	
	private static int CircleInsideCircle(int[] c1, int[] c2){
		if(Distance(c1[0],c1[1],c2[0],c2[1]) <= (c2[2] - c1[2])){
			return 1;
		}
		return 0;
	}
	
	private static double Distance(int x1, int y1, int x2, int y2){
		 
			return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
	}
}
