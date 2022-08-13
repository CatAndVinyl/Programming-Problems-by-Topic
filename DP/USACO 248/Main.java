import java.io.*;
import java.util.*;

class Main
{
	public static void main(String[] args) throws IOException
	{
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("248.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int[][] dp = new int[N][N];
		boolean[][] oneVal = new boolean[N][N];
		for(int i = 0; i < N; i++)
		{
			st = new StringTokenizer(br.readLine());
			dp[i][i] = Integer.parseInt(st.nextToken());
			oneVal[i][i] = true;
		}
		for(int s = 1; s <= N; s++)
		{
			for(int i = 0; i < N - s + 1; i++)
			{
				for(int j = 0; j < s-1; j++)
				{
					int m = Math.max(dp[i][i+j], dp[i+j+1][i+s-1]); 
					if(oneVal[i][i + j] && oneVal[i + j + 1][i + s-1] && (dp[i][i + j] == dp[i + j + 1][i + s-1]))
					{
						oneVal[i][i+s-1] = true;
						m = dp[i][i+j] + 1;		
					}
					dp[i][i + s-1] = Math.max(dp[i][i + s-1], m);
				}			
			}	
		}
		PrintWriter pw = new PrintWriter("248.out");
		pw.println(dp[0][N-1]);
		pw.close();
	}
}