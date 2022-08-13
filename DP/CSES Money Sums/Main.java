import java.io.*;
import java.util.*;

class Main
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int[] c = new int[n];
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < n; i++)
			c[i] = Integer.parseInt(st.nextToken());
		boolean[] dp = new boolean[100001];
		dp[0] = true;
		for(int i : c)
		{
			for(int j = dp.length - i - 1; j >= 0; j--)
			{
				if(dp[j])
					dp[j + i] = true;
			}			 
		}
		int ans = 0;
		for(int i = 1; i < dp.length; i++)
		{
			if(dp[i])
				ans++;
		}
		System.out.println(ans);
		for(int i = 1; i < dp.length; i++)
		{
			if(dp[i])
				System.out.print(i + " ");
		}
	}
}
