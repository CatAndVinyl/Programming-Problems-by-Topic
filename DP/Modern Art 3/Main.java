import java.io.*;
import java.util.*;

class Main
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int[] a = new int[N];
		int[][] dp = new int[N][N];
		for(int i = 0; i < N; i++)
		{
			for(int j = 0; j < N; j++)
				dp[i][j] = Integer.MAX_VALUE;
		}
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++)
		{
			a[i] = Integer.parseInt(st.nextToken());
			dp[i][i] = 1;
		}
		for(int s = 0; s < N; s++)
		{
			for(int i = 0; i < N - s; i++)
			{
				for(int j = 0; j < s; j++)
				{
					if(a[i] == a[i+s])
						dp[i][i+s] = Math.min(dp[i][i+s], dp[i][i+j] + dp[i+j+1][i+s] - 1);
					else
						dp[i][i+s] = Math.min(dp[i][i+s], dp[i][i+j] + dp[i+j+1][i+s]);
				}
			}
		}
		System.out.println(dp[0][N-1]);
	}
}
