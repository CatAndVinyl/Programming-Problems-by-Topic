import java.io.*;
import java.util.*;

class Main
{
	static int MOD = 1000000007;
	public static void main(String[] args) throws IOException
	{
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("palpath.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		char[][] a = new char[N][N];
		for(int i = 0; i < N; i++)
		{
			st = new StringTokenizer(br.readLine());
			String s = st.nextToken();
			for(int j = 0; j < N; j++)
				a[i][j] = s.charAt(j);
		}
		int[][][] dp = new int[N][N][N];
		for(int i = 0; i < N; i++)
			dp[0][i][i] = 1;
		for(int d = 1; d < N; d++)
		{
			for(int r1 = 0; r1 < N; r1++)
			{
				int c1 = N - r1 - d - 1;
				if(c1 < 0 || c1 >= N)
					continue;
				for(int r2 = r1; r2 < N; r2++)
				{
					int c2 = N - 1 - r2 + d;
					//System.out.println(r1 + " " + c1 + "_" + r2 + " " + c2);
					if(c2 < c1 || c2 >= N)
						continue;
					if(a[r1][c1] != a[r2][c2])
						continue;
					dp[d][r1][r2] += dp[d-1][r1][r2];
					dp[d][r1][r2] %= MOD;
					if(r1 + 1 < N)
						dp[d][r1][r2] += dp[d-1][r1+1][r2];
					dp[d][r1][r2] %= MOD;
					if(r2 - 1 >= 0)
						dp[d][r1][r2] += dp[d-1][r1][r2-1];
					dp[d][r1][r2] %= MOD;
					if((r1 + 1 < N) && (r2 - 1 >= 0))
						dp[d][r1][r2] += dp[d-1][r1+1][r2-1];
					dp[d][r1][r2] %= MOD;
				}
			}
		}/*
		for(int i = 0; i < N; i++)
		{
			for(int j = 0; j < N; j++)
				System.out.print(dp[2][i][j] + " ");
			System.out.println();
		}*/
		PrintWriter pw = new PrintWriter("palpath.out");
		pw.println(dp[N-1][0][N-1]);
		pw.close();
	}
}