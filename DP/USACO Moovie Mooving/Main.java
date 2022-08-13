import java.io.*;
import java.util.*;

class Main
{
	public static int search(int val, int[] a)
	{
		if(val < a[0])
			return -1;
		int l = 0;
		int r = a.length - 1;
		int mid = -1;
		int ans = -1;
		while(l <= r)
		{
			mid = (l + r) / 2;
			if(a[mid] <= val)
			{
				l = mid + 1;
				ans = mid;
			} else 
				r = mid - 1;
		}
		return ans;
	}

	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int L = Integer.parseInt(st.nextToken());
		int[] lengths = new int[N];
		int[][] showTimes = new int[N][];
		for(int i = 0; i < N; i++)
		{
			st = new StringTokenizer(br.readLine());
			lengths[i] = Integer.parseInt(st.nextToken());
			int D = Integer.parseInt(st.nextToken());
			showTimes[i] = new int[D];
			for(int j = 0; j < D; j++)
				showTimes[i][j] = Integer.parseInt(st.nextToken());
		}
		int[][][] nextTime = new int[N][N][];
		for(int i = 0; i < N; i++)
		{
			for(int j = 0; j < N; j++)
			{
				nextTime[i][j] = new int[showTimes[i].length];
				for(int k = 0; k < showTimes[i].length; k++)
					nextTime[i][j][k] = search(showTimes[i][k] + lengths[i], showTimes[j]);
			}
		}
		int[][] dp = new int[1 << N][2];
		for(int mask = 0; mask < 1 << N; mask++)
		{
			/*
			int numMovies = 0;
			for(int i = 0; i < N; i++)
				numMovies += mask & (1 << i);
			*/
			if(dp[mask][0] == -1)
				continue;
			if(mask == 0)
			{
				for(int movie = 0; movie < N; movie++)
				{
					if(showTimes[movie][0] == 0)
						dp[1 << movie][0] = movie;
					else
						dp[1 << movie][0] = -1;			
				}
				continue;
			}
			boolean tmp = false;
			for(int movie = 0; movie < N; movie++)
			{
				if((mask & (1 << movie)) != 0)
					continue;
				int nextVal = nextTime[dp[mask][0]][movie][dp[mask][1]];
				int nextMask = mask + (1 << movie);
				if(nextVal == -1)
					continue;
				if(showTimes[movie][nextVal] + lengths[movie] > showTimes[dp[nextMask][0]][dp[nextMask][1]] + lengths[dp[nextMask][0]]) 
				{
					dp[nextMask][0] = movie;
					dp[nextMask][1] = nextVal;
					tmp = true;
				}
			}
			if(tmp)
				dp[mask][0] = -1;
		}
		for(int mask = 0; mask < 1 << N; mask++)
			System.out.println(mask + " " + dp[mask][0] + " " + dp[mask][1]);
	}
}
