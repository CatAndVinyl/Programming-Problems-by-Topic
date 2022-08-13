import java.io.*;
import java.util.*;

class Main
{
	public static int minCoin(int x, int[] c, int[] state, boolean[] ready)
	{
		if(x < 0)
			return 1000000;
		if(x == 0)
			return 0;
		if(ready[x])
			return state[x];
		int low = 1000000;
		for(int i : c)
			low = Math.min(low, minCoin(x - i, c, state, ready)+1);
		ready[x] = true;
		state[x] = low;
		return low;
	}
 
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
		int[] s = new int[1000001];
		boolean[] r = new boolean[1000001];
		if(minCoin(x,c,s,r) != 1000000)
			System.out.println(minCoin(x, c, s, r));
		else
			System.out.println(-1);
	}
}