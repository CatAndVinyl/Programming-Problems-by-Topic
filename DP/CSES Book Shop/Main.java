import java.io.*;
import java.util.*;

class Main
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int x = Integer.parseInt(st.nextToken());
		int[] p = new int[n];
		int[] c = new int[n];
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < n; i++)
			p[i] = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < n; i++)
			c[i] = Integer.parseInt(st.nextToken());
		int[] sum = new int[101001];
		for(int i = 0; i < n; i++)
		{
			for(int j = x; j >= 0; j--)
			{
				if(j == 0)
					sum[p[i]] = Math.max(sum[p[i]], c[i]);
				if(sum[j] > 0)
					sum[j + p[i]] = Math.max(sum[j + p[i]], sum[j] + c[i]);	
			}
		}
		int ans = 0;
		for(int i = 0; i <= x; i++)
			ans = Math.max(ans, sum[i]);
		System.out.println(ans);
	}
}
