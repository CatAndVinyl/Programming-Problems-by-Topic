import java.io.*;
import java.util.*;

public class Main
{
	public static int[][] packU(int n, int[] from, int[] to) {
		return packU(n, from, to, from.length);
	}
 
 	//1-indexed
	public static int[][] packU(int n, int[] from, int[] to, int sup) {
		int[][] g = new int[n + 1][];
		int[] p = new int[n + 1];
		for (int i = 0; i < sup; i++) p[from[i]]++;
		for (int i = 0; i < sup; i++) p[to[i]]++;
		//make it 0 indexed by changing i = 1 to i = 0 and i <= n to i < n
		for (int i = 1; i <= n; i++) g[i] = new int[p[i]];
		for (int i = 0; i < sup; i++) {
			g[from[i]][--p[from[i]]] = to[i];
			g[to[i]][--p[to[i]]] = from[i];
		}
		return g;
	}

	public static boolean dfs(int v, int d, int[][] graph, int[] dep, int[] c, int[] next, int threshold)
	{
		dep[v] = d;
		c[d]++;
		if(d >= threshold)
			return true;
		for(int u : graph[v])
		{
			if(dep[u] == 0 && dfs(u, d + 1, graph, dep, c, next, threshold))
			{
				next[v] = u;
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int T = Integer.parseInt(st.nextToken());
		FastWriter out = new FastWriter(System.out);
		while(T-- > 0)
		{
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			int[] from = new int[M];
			int[] to = new int[M];
			for(int i = 0; i < M; i++)
			{
				st = new StringTokenizer(br.readLine());
				from[i] = Integer.parseInt(st.nextToken());
				to[i] = Integer.parseInt(st.nextToken());
			}
			int[][] graph = packU(N, from, to);
			int[] dep = new int[N+1];
			int[] c = new int[N+1];
			int[] next = new int[N+1];
			if(dfs(1, 1, graph, dep, c, next, (N+1)/2))
			{
				out.println("PATH");
				int p = 1;
				int ct = 1;
				while(next[p] != 0)
				{
					ct++;
					p = next[p];
				}
				out.println(ct);
				p = 1;
				for(int i = 0; i < ct; i++)
				{
					out.print(p + " ");
					p = next[p];
				}
				out.println();
			} else {
				int[][] lvl = new int[N+1][];
				for(int i = 1; i <= N; i++)
					lvl[i] = new int[c[i]];
				int[] ctr = new int[N+1];
				for(int i = 1; i <= N; i++)
				{
					lvl[dep[i]][ctr[dep[i]]] = i;
					ctr[dep[i]]++;
				}
				out.println("PAIRING");
				int p = 0;
				int md = 1;
				while(p * 2 < (N+1)/2)
				{
					p += c[md] / 2;
					md++;
				}
				out.println(p);
				for(int i = 1; i < md; i++)
				{
					for(int j = 0; j < c[i] - 1; j += 2)
						out.println(lvl[i][j] + " " + lvl[i][j+1]);
				}
			}
			/*
			for(int i = 1; i <= N; i++)
			{
				System.out.println(dep[i] + " " + c[i]);
			}*/
		}
		out.flush();
	}
}

//code by uwi CF
class FastWriter
	{
		private static final int BUF_SIZE = 1<<13;
		private final byte[] buf = new byte[BUF_SIZE];
		private final OutputStream out;
		private int ptr = 0;
 
		private FastWriter(){out = null;}
 
		public FastWriter(OutputStream os)
		{
			this.out = os;
		}
 
		public FastWriter(String path)
		{
			try {
				this.out = new FileOutputStream(path);
			} catch (FileNotFoundException e) {
				throw new RuntimeException("FastWriter");
			}
		}
 
		public FastWriter write(byte b)
		{
			buf[ptr++] = b;
			if(ptr == BUF_SIZE)innerflush();
			return this;
		}
 
		public FastWriter write(char c)
		{
			return write((byte)c);
		}
 
		public FastWriter write(char[] s)
		{
			for(char c : s){
				buf[ptr++] = (byte)c;
				if(ptr == BUF_SIZE)innerflush();
			}
			return this;
		}
 
		public FastWriter write(String s)
		{
			s.chars().forEach(c -> {
				buf[ptr++] = (byte)c;
				if(ptr == BUF_SIZE)innerflush();
			});
			return this;
		}
 
		private static int countDigits(int l) {
			if (l >= 1000000000) return 10;
			if (l >= 100000000) return 9;
			if (l >= 10000000) return 8;
			if (l >= 1000000) return 7;
			if (l >= 100000) return 6;
			if (l >= 10000) return 5;
			if (l >= 1000) return 4;
			if (l >= 100) return 3;
			if (l >= 10) return 2;
			return 1;
		}
 
		public FastWriter write(int x)
		{
			if(x == Integer.MIN_VALUE){
				return write((long)x);
			}
			if(ptr + 12 >= BUF_SIZE)innerflush();
			if(x < 0){
				write((byte)'-');
				x = -x;
			}
			int d = countDigits(x);
			for(int i = ptr + d - 1;i >= ptr;i--){
				buf[i] = (byte)('0'+x%10);
				x /= 10;
			}
			ptr += d;
			return this;
		}
 
		private static int countDigits(long l) {
			if (l >= 1000000000000000000L) return 19;
			if (l >= 100000000000000000L) return 18;
			if (l >= 10000000000000000L) return 17;
			if (l >= 1000000000000000L) return 16;
			if (l >= 100000000000000L) return 15;
			if (l >= 10000000000000L) return 14;
			if (l >= 1000000000000L) return 13;
			if (l >= 100000000000L) return 12;
			if (l >= 10000000000L) return 11;
			if (l >= 1000000000L) return 10;
			if (l >= 100000000L) return 9;
			if (l >= 10000000L) return 8;
			if (l >= 1000000L) return 7;
			if (l >= 100000L) return 6;
			if (l >= 10000L) return 5;
			if (l >= 1000L) return 4;
			if (l >= 100L) return 3;
			if (l >= 10L) return 2;
			return 1;
		}
 
		public FastWriter write(long x)
		{
			if(x == Long.MIN_VALUE){
				return write("" + x);
			}
			if(ptr + 21 >= BUF_SIZE)innerflush();
			if(x < 0){
				write((byte)'-');
				x = -x;
			}
			int d = countDigits(x);
			for(int i = ptr + d - 1;i >= ptr;i--){
				buf[i] = (byte)('0'+x%10);
				x /= 10;
			}
			ptr += d;
			return this;
		}
 
		public FastWriter write(double x, int precision)
		{
			if(x < 0){
				write('-');
				x = -x;
			}
			x += Math.pow(10, -precision)/2;
			//		if(x < 0){ x = 0; }
			write((long)x).write(".");
			x -= (long)x;
			for(int i = 0;i < precision;i++){
				x *= 10;
				write((char)('0'+(int)x));
				x -= (int)x;
			}
			return this;
		}
 
		public FastWriter writeln(char c){
			return write(c).writeln();
		}
 
		public FastWriter writeln(int x){
			return write(x).writeln();
		}
 
		public FastWriter writeln(long x){
			return write(x).writeln();
		}
 
		public FastWriter writeln(double x, int precision){
			return write(x, precision).writeln();
		}
 
		public FastWriter write(int... xs)
		{
			boolean first = true;
			for(int x : xs) {
				if (!first) write(' ');
				first = false;
				write(x);
			}
			return this;
		}
 
		public FastWriter write(long... xs)
		{
			boolean first = true;
			for(long x : xs) {
				if (!first) write(' ');
				first = false;
				write(x);
			}
			return this;
		}
 
		public FastWriter writeln()
		{
			return write((byte)'\n');
		}
 
		public FastWriter writeln(int... xs)
		{
			return write(xs).writeln();
		}
 
		public FastWriter writeln(long... xs)
		{
			return write(xs).writeln();
		}
 
		public FastWriter writeln(char[] line)
		{
			return write(line).writeln();
		}
 
		public FastWriter writeln(char[]... map)
		{
			for(char[] line : map)write(line).writeln();
			return this;
		}
 
		public FastWriter writeln(String s)
		{
			return write(s).writeln();
		}
 
		private void innerflush()
		{
			try {
				out.write(buf, 0, ptr);
				ptr = 0;
			} catch (IOException e) {
				throw new RuntimeException("innerflush");
			}
		}
 
		public void flush()
		{
			innerflush();
			try {
				out.flush();
			} catch (IOException e) {
				throw new RuntimeException("flush");
			}
		}
 
		public FastWriter print(byte b) { return write(b); }
		public FastWriter print(char c) { return write(c); }
		public FastWriter print(char[] s) { return write(s); }
		public FastWriter print(String s) { return write(s); }
		public FastWriter print(int x) { return write(x); }
		public FastWriter print(long x) { return write(x); }
		public FastWriter print(double x, int precision) { return write(x, precision); }
		public FastWriter println(char c){ return writeln(c); }
		public FastWriter println(int x){ return writeln(x); }
		public FastWriter println(long x){ return writeln(x); }
		public FastWriter println(double x, int precision){ return writeln(x, precision); }
		public FastWriter print(int... xs) { return write(xs); }
		public FastWriter print(long... xs) { return write(xs); }
		public FastWriter println(int... xs) { return writeln(xs); }
		public FastWriter println(long... xs) { return writeln(xs); }
		public FastWriter println(char[] line) { return writeln(line); }
		public FastWriter println(char[]... map) { return writeln(map); }
		public FastWriter println(String s) { return writeln(s); }
		public FastWriter println() { return writeln(); }
}
