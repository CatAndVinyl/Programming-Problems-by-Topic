import java.io.*;
import java.util.*;

class Main
{
	public static void main(String[] args) throws IOException
	{
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("nocross.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int[] a = new int[N+1];
		int[] b = new int[N+1];
		for(int i = 1; i <= N; i++)
		{
			st = new StringTokenizer(br.readLine());
			a[i] = Integer.parseInt(st.nextToken());
		}
		for(int i = 1; i <= N; i++)
		{
			st = new StringTokenizer(br.readLine());
			b[i] = Integer.parseInt(st.nextToken());
		}
		int[][] dp = new int[N+1][N+1];
		for(int i = 1; i <= N; i++)
		{
			for(int j = 1; j <= N; j++)
			{
				if(Math.abs(a[i] - b[j]) <= 4)
					dp[i][j] = Math.max(Math.max(dp[i-1][j], dp[i][j-1]), dp[i-1][j-1]+1);
				else
					dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
			}
		}/*
		for(int i = 0; i <= N; i++)
		{
			for(int j = 0; j <= N; j++)
				System.out.print(dp[i][j] + " ");
			System.out.println();
		}*/
		PrintWriter pw = new PrintWriter("nocross.out");
		pw.println(dp[N][N]);
		pw.close();
		//System.out.println(dp[N][N]);
	}
}