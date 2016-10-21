import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

class Job implements Comparable<Job> {
	int start;
	int end;
	int profit;

	Job(int start, int end, int profit) {
		this.start = start;
		this.end = end;
		this.profit = profit;
	}

	@Override
	public int compareTo(Job j) {
		if (this.end <= j.end)
			return -1;
		return 1;
	}
}

class WAS {

	public int maximizeDynamic(Job[] jobs) {
		int T[] = new int[jobs.length];
		int org[] = new int[jobs.length];
		Arrays.fill(org, -1);
		Arrays.sort(jobs);
		for (int i = 0; i < T.length; i++)
			T[i] = jobs[i].profit;
		for (int i = 1; i < jobs.length; i++) {
			for (int j = i - 1; j >= 0; j--) {
				if (jobs[i].start >= jobs[j].end) {
					T[i] = Math.max(T[i], jobs[i].profit + T[j]);
					org[i] = j;
					break;
				}
			}
		}
		int res = Integer.MIN_VALUE;
		int index = 0;
		for (int i = 0; i < T.length; i++) {
			if (res < T[i]) {
				res = T[i];
				index = i;
			}
		}
		System.out.println("\n Jobs picked are:");
		while (index >= 0) {
			if (index == 0) {
				System.out.print(jobs[index].profit);
				break;
			}
			System.out.print(jobs[index].profit + " ");
			index = org[index];
		}
		return res;
	}

	public static void main(String args[]) {
		Job jobs[] = new Job[5];
		jobs[0] = new Job(1, 3, 2);
		jobs[1] = new Job(4,7,4);
		jobs[2] = new Job(5,8,10)  ;          
		jobs[3] = new Job(9,15,3) ;
		jobs[4] = new Job(11,16,5); 
		WAS mp = new WAS();
		System.out.println("\nMax Profit:" + mp.maximizeDynamic(jobs));

	}
}