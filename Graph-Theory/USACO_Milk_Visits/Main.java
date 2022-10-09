import java.io.*;
import java.util.*;

class Query
{
	int u;
	int v;
	int color;
	int ans;

	public Query(int a, int b, int col)
	{
		u = a;
		v = b;
		color = col;
		ans = 0;
	}
}

class Color
{
	Stack<Integer> stack;

	public Color()
	{
		stack = new Stack<Integer>();
	}
}

public class Main
{
	public static int binlog( int bits ) // returns 0 for bits=0
	{
	    int log = 0;
	    if( ( bits & 0xffff0000 ) != 0 ) { bits >>>= 16; log = 16; }
	    if( bits >= 256 ) { bits >>>= 8; log += 8; }
	    if( bits >= 16  ) { bits >>>= 4; log += 4; }
	    if( bits >= 4   ) { bits >>>= 2; log += 2; }
	    return log + ( bits >>> 1 );
	}

	//generate graph / adjLists
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

	//must be a tree; can easihly be modified to work with any functional graph
	//by adding all nodes with indegree 0 to initial dfs stack
	//graph is size N+1 with N nodes (0 is not a node), stores adj vertices
	public static void computeBLT(int N, int[][] graph, int[][] bt)
	{
		int[] next = new int[N+1];
		boolean[] vis = new boolean[N+1];
		Stack<Integer> dfsStack = new Stack<Integer>();
		dfsStack.push(1);
		while(!dfsStack.empty())
		{
			int u = dfsStack.pop();
			vis[u] = true;
			for(int v : graph[u])
			{
				if(!vis[v])
				{
					next[v] = u;
					dfsStack.add(v);
				}
			}
		}
		for(int k = 1; k <= N; k++)
			bt[0][k] = next[k];
		for(int k = 1; k < bt.length; k++)
		{
			for(int i = 1; i <= N; i++)
			{
				if(bt[k-1][i] != 0)
					bt[k][i] = bt[k-1][bt[k-1][i]];
			}
		}
	}

	public static void computeDepth(int N, int[][] graph, int[] d)
	{
		Stack<Integer> dfsStack = new Stack<Integer>();
		dfsStack.push(1);
		d[1] = 1;
		while(!dfsStack.empty())
		{
			int u = dfsStack.pop();
			for(int v : graph[u])
			{
				if(d[v] == 0)
				{
					d[v] = d[u] + 1;
					dfsStack.push(v);
				}
			}
		}
	}

	public static int findLCA(int a, int b, int[] d, int[][] bt)
	{
		int low = (d[a] > d[b]) ? a : b;
		int dist = Math.abs(d[a] - d[b]);
		int sum = 0;
		for(int i = 0; sum < dist; i++)
		{
			if((dist & (1 << i)) != 0)
			{
				low = bt[i][low];
				sum += 1 << i;
			}
		}
		a = (d[a] > d[b]) ? b : a;
		b = low;
		if(a == b)
			return a;
		for(int i = bt.length-1; i >= 0; i--)
		{
			if(bt[i][a] != bt[i][b])
			{
				a = bt[i][a];
				b = bt[i][b];
			}
		}
		return bt[0][a];
	}

	public static void solve(int u, int N, int[] w, boolean[] vis, Color[] colors, int[][] graph, Query[][] qMat, int[] d, int[][] bt)
	{
		vis[u] = true;
		colors[w[u]].stack.push(u);
		for(Query q : qMat[u])
		{
			if(!colors[q.color].stack.empty() && d[colors[q.color].stack.peek()] >= d[findLCA(q.u,q.v,d,bt)])
				q.ans = 1;
		}
		for(int v : graph[u])
		{
			if(!vis[v])
				solve(v, N, w, vis, colors, graph, qMat, d, bt);
		}
		colors[w[u]].stack.pop();
	}

	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader("milkvisits.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int[] w = new int[N+1];
		st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= N; i++)
			w[i] = Integer.parseInt(st.nextToken());
		int[] from = new int[N-1];
		int[] to = new int[N-1];
		for(int i = 0; i < N-1; i++)
		{
			st = new StringTokenizer(br.readLine());
			from[i] = Integer.parseInt(st.nextToken());
			to[i] = Integer.parseInt(st.nextToken());
		}
		int[][] graph = packU(N, from, to);
		Query[] queries = new Query[M];
		int[] qc = new int[N+1];
		for(int i = 0; i < M; i++)
		{
			st = new StringTokenizer(br.readLine());
			queries[i] = new Query(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			qc[queries[i].u]++;
			qc[queries[i].v]++;
		}
		Query[][] qMat = new Query[N+1][];
		for(int i = 1; i <= N; i++)
			qMat[i] = new Query[qc[i]];
		int[] qctr = new int[N+1];
		for(Query q : queries)
		{
			qMat[q.u][qctr[q.u]] = q;
			qctr[q.u]++;
			qMat[q.v][qctr[q.v]] = q;
			qctr[q.v]++;	
		}
		int[][] bt = new int[binlog(N-1)+1][N+1];
		computeBLT(N, graph, bt);
		int[] d = new int[N+1];
		computeDepth(N, graph, d);
		Color[] colors = new Color[N+1];
		for(int i = 1; i <= N; i++)
			colors[i] = new Color();
		boolean[] vis = new boolean[N+1];
		solve(1, N, w, vis, colors, graph, qMat, d, bt);
		FastWriter out = new FastWriter("milkvisits.out");
		for(int i = 0; i < M; i++)
			out.print(queries[i].ans);
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
