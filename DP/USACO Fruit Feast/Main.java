import java.io.*;
import java.util.*;

class Main
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader("feast.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int T = Integer.parseInt(st.nextToken());
		int A = Integer.parseInt(st.nextToken());
		int B = Integer.parseInt(st.nextToken());
		boolean[] dp = new boolean[T + 1];
		dp[0] = true;
		for(int i = A; i < dp.length; i++)
		{
			if(dp[i - A])
				dp[i] = true;
		}
		for(int i = B; i < dp.length; i++)
		{
			if(dp[i - B])
				dp[i] = true;
		}
		for(int i = 0; i < dp.length; i++)
		{
			if(dp[i])
				dp[i/2] = true;
		}
		for(int i = A; i < dp.length; i++)
		{
			if(dp[i - A])
				dp[i] = true;
		}
		for(int i = B; i < dp.length; i++)
		{
			if(dp[i - B])
				dp[i] = true;		
		}
		int ind = T;
		while(!dp[ind])
		{
			ind--;
		}
		PrintWriter pw = new PrintWriter("feast.out");
		pw.println(ind);
		pw.close();
	}
}
