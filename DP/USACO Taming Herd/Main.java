import java.io.*;
import java.util.*;

class Main
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader("taming.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int[] a = new int[N+1];
		st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= N; i++)
			a[i] = Integer.parseInt(st.nextToken());
		int[][] d = new int[N+1][N+1];
		for(int i = 1; i <= N; i++)
		{
			for(int j = 0; j <= N - i; j++)
			{
				d[i][i + j] = d[i][i + j - 1];
				if(a[i + j] != j)
					d[i][i + j]++;		
			}
		}
		int[][] dp = new int[N+1][N+1];
		for(int i = 1; i <= N; i++)
			dp[1][i] = d[1][i];
		PrintWriter pw = new PrintWriter("taming.out");
		pw.println(dp[1][N]);
		for(int b = 2; b <= N; b++)
		{
			for(int i = 1; i <= N; i++)
			{
				dp[b][i] = Integer.MAX_VALUE;
				for(int j = b; j <= i; j++)
					dp[b][i] = Math.min(dp[b][i], dp[b-1][j-1] + d[j][i]);
			}
			pw.println(dp[b][N]);	
		}
		pw.close();/*
		for(int i = 0; i <= N; i++)
		{
			for(int j = 0; j <= N; j++)
				//System.out.print(d[i][j] + " ");
				System.out.print(dp[i][j] + " ");
			//System.out.println();
			System.out.println();
		}*/
	}
}