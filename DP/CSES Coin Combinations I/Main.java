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
		int[] c = new int[n];
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < n; i++)
			c[i] = Integer.parseInt(st.nextToken());
		int[] val = new int[1000001];
		for(int i = 0; i <= x; i++)
		{
			int sum = 0;
			for(int j : c)
			{
				if(i - j > 0)
					sum += val[i - j];
				if(i - j == 0)
					sum += 1;
				sum %= 1000000007;				 
			}
			val[i] = sum;
		}
		System.out.println(val[x]);
	}
}
