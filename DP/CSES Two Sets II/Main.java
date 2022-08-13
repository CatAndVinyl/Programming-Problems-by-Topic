import java.io.*;
import java.util.*;

class Main
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int[] val = new int[250000];
		for(int i = 1; i <= n; i++)
		{
			for(int j = val.length - i - 1; j >= 0; j--)
			{
				if(j == 0)
					val[i]++;
				if(val[j] > 0)
					val[i + j] += val[j];
				val[i] %= 1000000007;
				val[i + j] %= 1000000007;
			}
		}
		if(n*(n+1) % 4 != 0)
			System.out.println(0);
		else
			System.out.println(500000004L * val[n*(n+1)/4] % 1000000007);
/*
		int m = 1;
		for(int i = 0; i < 1000000005; i++)
			m = (m * 2) % 1000000007;
		System.out.println(m);
*/
	}
}
