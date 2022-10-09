import java.io.*;
import java.util.*;

class Main
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader("team.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int P = 1000000009;
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		int[] a = new int[N+1];
		int[] b = new int[M+1];
		st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= N; i++)
			a[i] = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= M; i++)
			b[i] = Integer.parseInt(st.nextToken());
		Arrays.sort(a);
		Arrays.sort(b);
		long[][][] dp = new long[N+1][M+1][K+1];
		long[][][] psum = new long[N+1][M+1][K+1];
		for(int i = 0; i <= N; i++)
		{
			for(int j = 0; j <= M; j++)
				psum[i][j][0] = 1;
		}
		for(int i = 1; i <= N; i++)
		{
			for(int j = 1; j <= M; j++)
			{
				for(int k = 1; k <= K; k++)
				{
					if(a[i] > b[j])
						dp[i][j][k] = psum[i-1][j-1][k-1];
					psum[i][j][k] = (P + psum[i][j-1][k] + psum[i-1][j][k] - psum[i-1][j-1][k] + dp[i][j][k]) % P;
				}
			}
		}
		PrintWriter pw = new PrintWriter("team.out");
		pw.println(psum[N][M][K]);
		pw.close();
	}
}