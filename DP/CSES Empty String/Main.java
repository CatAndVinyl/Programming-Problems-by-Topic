import java.io.*;
import java.util.*;

class Main
{
	static long MOD = 1000000007;
	public static long choose(long N, long K)
	{
		if(K == 0)
			return 1;
		return N * choose(N - 1, K - 1) / K % MOD; 
	}

	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		String s = st.nextToken();
		int n = s.length();
		long[][] combination = new long[n][n];
		for(int i = 0; i < n; i++)
		{
			for(int j = 0; j < i; j++)
				combination[i][j] = choose(i, j);	
		}
		long[][] dp = new long[n][n];
		for(int s = 0; s < n; s++)
		{
			for(int i = 0; i < n; i++)
			{
				for(int j = 0; j < s; j++)
				{
					int l = dp[i][i+j]
					if(a[i] == a[i+j])
							
				}
			}
		}
	}
}