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
		for(int i : c)
		{
			for(int j = 0; j <= x; j++)
			{
				if(j - i == 0)
					val[j] += 1;
				if(j - i > 0)
					val[j] += val[j - i];
				val[j] %= 1000000007;
			}
		}
		System.out.println(val[x]);
	}
}
