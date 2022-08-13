import java.io.*;
import java.util.*;

class Main
{
	public static void main(String[] args) throws IOException
	{
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("radio.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		//x1, y1, x2, y2
		int[][][] c = new int[N+1][M+1][4];
		c[0][0][0] = Integer.parseInt(st.nextToken());
		c[0][0][1] = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		c[0][0][2] = Integer.parseInt(st.nextToken());
		c[0][0][3] = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		String a = "x" + st.nextToken();
		st = new StringTokenizer(br.readLine());
		String b = "x" + st.nextToken();
		int[][] d = new int[N+1][M+1];
		for(int i = 0; i <= N; i++)
		{
			for(int j = 0; j <= M; j++)
			{
				if(j == 0)
				{
					if(a.charAt(i) != 'x')
					{
						c[i][0][0] = c[i-1][0][0];
						c[i][0][1] = c[i-1][0][1];
						c[i][0][2] = c[i-1][0][2];
						c[i][0][3] = c[i-1][0][3];
					}
					if(a.charAt(i) == 'N')
						c[i][0][1]++;
					if(a.charAt(i) == 'E')
						c[i][0][0]++;
					if(a.charAt(i) == 'S')
						c[i][0][1]--;
					if(a.charAt(i) == 'W')
						c[i][0][0]--;				
				} else {
					c[i][j][0] = c[i][j-1][0];
					c[i][j][1] = c[i][j-1][1];
					c[i][j][2] = c[i][j-1][2];
					c[i][j][3] = c[i][j-1][3];
					if(b.charAt(j) == 'N')
						c[i][j][3]++;
					if(b.charAt(j) == 'E')
						c[i][j][2]++;
					if(b.charAt(j) == 'S')
						c[i][j][3]--;
					if(b.charAt(j) == 'W')
						c[i][j][2]--;
				}
				d[i][j] = (c[i][j][0] - c[i][j][2]) * (c[i][j][0] - c[i][j][2]) 
					+ (c[i][j][1] - c[i][j][3]) * (c[i][j][1] - c[i][j][3]);
			}
		}
		/*
		for(int i = 0; i <= N; i++)
		{
			for(int j = 0; j <= M; j++)
				System.out.print(d[i][j] + " ");
			System.out.println();
		}
		System.out.println();*/
		for(int i = 1; i <= N; i++)
			d[i][0] += d[i-1][0];
		for(int j = 1; j <= M; j++)
			d[0][j] += d[0][j-1];
		for(int i = 1; i <= N; i++)
		{
			for(int j = 1; j <= M; j++)
				d[i][j] += Math.min(Math.min(d[i-1][j], d[i][j-1]), d[i-1][j-1]);
		}
		//System.out.println(d[N][M] - d[0][0]);
		PrintWriter pw = new PrintWriter("radio.out");
		pw.println(d[N][M] - d[0][0]);
		pw.close();
		/*
		for(int i = 0; i <= N; i++)
		{
			for(int j = 0; j <= M; j++)
				System.out.print(d[i][j] + " ");
			System.out.println();
		}
		System.out.println();
		for(int i = 0; i <= N; i++)
		{
			for(int j = 0; j <= M; j++)
				System.out.print(c[i][j][0] + " ");
			System.out.println();
		}
		System.out.println();
		for(int i = 0; i <= N; i++)
		{
			for(int j = 0; j <= M; j++)
				System.out.print(c[i][j][1] + " ");
			System.out.println();
		}
		System.out.println();
		for(int i = 0; i <= N; i++)
		{
			for(int j = 0; j <= M; j++)
				System.out.print(c[i][j][2] + " ");
			System.out.println();
		}
		System.out.println();
		for(int i = 0; i <= N; i++)
		{
			for(int j = 0; j <= M; j++)
				System.out.print(c[i][j][3] + " ");
			System.out.println();
		}*/
		//System.out.println(c[0][3][0]);
	}
}