import java.io.*;
import java.util.*;

class Main
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int[][] adjMatrix = new int[n][n];
		for(int i = 0; i < m; i++)
		{
			st = new StringTokenizer(br.readLine());
			int p = Integer.parseInt(st.nextToken())-1;
			int q = Integer.parseInt(st.nextToken())-1;
			adjMatrix[p][q]++;
		}
		int[][] dp = new int[1 << n][n];
		dp[1][0] = 1;
		for(int mask = 1; mask < 1 << n; mask++)
		{
			if(((mask & (1 << (n-1))) != 0) && (mask != ((1 << n) - 1)))
				continue;
			if((mask & 1) != 0)
			{
				for(int curr = 0; curr < n; curr++)
				{
					if((mask & (1 << curr)) != 0) 
					{
						for(int prev = 0; prev < n; prev++)
						{
							for(int t = 0; t < adjMatrix[prev][curr]; t++)
							{
								dp[mask][curr] += dp[mask ^ (1 << curr)][prev];
								dp[mask][curr] %= 1000000007;
							}
						}
					}
				}
			}
		}/*
		for(int mask = 1; mask < 1 << n; mask++)
		{
			for(int i = 0; i < n; i++)
				System.out.print(dp[mask][i] + " ");
			System.out.println();
		}*/
		
		System.out.println(dp[(1 << n) - 1][n-1]);
		/*
		System.out.println(dp[(1 << 7) - 1][6]);
		System.out.println(dp[(1 << 7) - 1][6] % 21);
		System.out.println(dp[(1 << 8) - 1][7]);
		System.out.println(dp[(1 << 8) - 1][7] % 21);
		*/
	}
}