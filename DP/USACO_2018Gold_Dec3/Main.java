//http://www.usaco.org/index.php?page=viewproblem2&cpid=863

import java.io.*;
import java.util.*;

class Main
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader("teamwork.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		int[] a = new int[N];
		for(int i = 0; i < N; i++)
		{
			st = new StringTokenizer(br.readLine());
			a[i] = Integer.parseInt(st.nextToken());
		}
		int[][] interval = new int[N][N];
		for(int i = 0; i < N; i++)
		{
			int h = a[i];
			for(int j = i; j < N; j++)
			{
				h = Math.max(h, a[j]);
				interval[i][j] = (j - i + 1) * h; 
				//System.out.print(interval[i][j] + " ");
			}
			//System.out.println();
		}
		int[] dp = new int[N];
		for(int i = 0; i < N; i++)
		{
			if(i < K)
				dp[i] = interval[0][i];
			for(int k = 1; k <= K && k <= i; k++)
				dp[i] = Math.max(dp[i], dp[i-k] + interval[i-k+1][i]);
			//System.out.println(dp[i]);
		}
		PrintWriter pw = new PrintWriter("teamwork.out");
		pw.println(dp[N-1]);
		pw.close();
	}
}