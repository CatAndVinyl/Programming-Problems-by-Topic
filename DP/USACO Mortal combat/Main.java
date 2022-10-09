import java.io.*;
import java.util.*;

class Main
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader("cowmbat.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		int[] a = new int[N];
		st = new StringTokenizer(br.readLine());
		String s = st.nextToken();
		for(int i = 0; i < N; i++)
			a[i] = s.charAt(i) - 97;
		int[][] sp = new int[M][M];
		for(int i = 0; i < M; i++)
		{
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M; j++)
				sp[i][j] = Integer.parseInt(st.nextToken());
		}
		for(int k = 0; k < M; k++)
		{
			for(int i = 0; i < M; i++)
			{
				for(int j = 0; j < M; j++)
					sp[i][j] = Math.min(sp[i][j], sp[i][k] + sp[k][j]);
			}
		}
		/*
		for(int i = 0; i < M; i++)
		{
			for(int j = 0; j < M; j++)
				System.out.print(sp[i][j]);
			System.out.println();
		}*/
		int[][] psum = new int[N][M];
		for(int i = 0; i < M; i++)
			psum[0][i] = sp[a[0]][i]; 
		for(int i = 1; i < N; i++)
		{
			for(int j = 0; j < M; j++)
				psum[i][j] = psum[i-1][j] + sp[a[i]][j];
		}/*
		for(int i = 0; i < N; i++)
		{
			for(int j = 0; j < M; j++)
				System.out.print(psum[i][j] + " ");
			System.out.println();
		}*/
		final int mbuffer = 100000001;
		int[] minD = new int[N];
		for(int i = 0; i < N; i++)
				minD[i] = mbuffer;
		int[][] dp = new int[M][N];
		for(int i = 0; i < M; i++)
		{
			dp[i][K-1] = psum[K-1][i];
			minD[K-1] = Math.min(minD[K-1], psum[K-1][i]);
		}
		for(int i = K; i < N; i++)
		{
			for(int j = 0; j < M; j++)
			{
				dp[j][i] = Math.min(dp[j][i-1] + sp[a[i]][j], minD[i-K] + psum[i][j] - psum[i-K][j]);
				minD[i] = Math.min(minD[i], dp[j][i]);			
			}
		}/*
		for(int i = 0; i < N; i++)
		{
			for(int j = 0; j < M; j++)
				System.out.print(dp[i][j] + " ");
			System.out.println();
		}*/
		PrintWriter pw = new PrintWriter("cowmbat.out");
		pw.println(minD[N-1]);
		pw.close();
	}
}