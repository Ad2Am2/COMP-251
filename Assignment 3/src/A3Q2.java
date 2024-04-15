import java.util.*; 

public class A3Q2 {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int num_of_vertices = sc.nextInt();
		Graph g = new Graph(num_of_vertices);
		LinkedList<Integer> buffer;
		for (int i=0; i<num_of_vertices; i++) {
			int id = sc.nextInt();
			g.addNode(id);
			int neighbors = sc.nextInt();
			int neighbor = 0;
			buffer = new LinkedList<Integer>();
			for (int j=0; j<neighbors; j++) {
				neighbor = sc.nextInt();
				buffer.add(neighbor);
			}
			g.addEdges(id, buffer);
		}
		sc.close();
		F1(g);
		System.out.println(Arrays.toString(g.issue1.toArray()));
		System.out.println(Arrays.toString(g.issue2.toArray()));
	}

	public static void F1(Graph g){
		boolean[] reachableFromIsland = new boolean[g.num_nodes];
		boolean[] islandReachable = new boolean[g.num_nodes];

		// Perform BFS from the island (node 0) to identify reachable nodes
		bfs(g, 0, reachableFromIsland);

		// Transpose the graph
		Hashtable<Integer, LinkedList<Integer>> adjTranspose = transposeGraph(g.adj);

		// Perform BFS from each node to check if the island is reachable
		for (int node : g.get_Order()) {
			bfs(g, node, islandReachable);
		}

		// Check for issues
		for (int node : g.get_Order()) {
			if (!reachableFromIsland[node]) {
				g.issue1.add(node);
			}
			if (!islandReachable[node]) {
				g.issue2.add(node);
			}
		}
	}

	// Helper method for BFS traversal
	public static void bfs(Graph g, int start, boolean[] visited) {
		Queue<Integer> queue = new LinkedList<>();
		queue.add(start);
		visited[start] = true;

		while (!queue.isEmpty()) {
			int current = queue.poll();
			for (int neighbor : g.adj.get(current)) {
				if (!visited[neighbor]) {
					visited[neighbor] = true;
					queue.add(neighbor);
				}
			}
		}
	}

	// Helper method to transpose the graph
	public static Hashtable<Integer, LinkedList<Integer>> transposeGraph(Hashtable<Integer, LinkedList<Integer>> adj) {
		Hashtable<Integer, LinkedList<Integer>> adjTranspose = new Hashtable<>();

		for (int u : adj.keySet()) {
			for (int v : adj.get(u)) {
				if (!adjTranspose.containsKey(v)) {
					adjTranspose.put(v, new LinkedList<>());
				}
				adjTranspose.get(v).add(u);
			}
		}

		return adjTranspose;
	}

}

class Graph{
	public int num_nodes;
	public Hashtable<Integer, LinkedList<Integer>> adj;
	public LinkedList<Integer> order;
	public LinkedList<Integer> issue1;
	public LinkedList<Integer> issue2;
	
	public Graph(int num_vertices) {
		this.num_nodes = num_vertices;
		adj = new Hashtable<Integer, LinkedList<Integer>>();
		order = new LinkedList<Integer>();
		issue1 = new LinkedList<Integer>();
		issue2 = new LinkedList<Integer>();
	}
	public void addEdges(int u, LinkedList<Integer> vs) {
		this.adj.put(u, vs);
	}
	public void addNode(int u) {
		order.add(u);
		adj.put(u, new LinkedList<Integer>());
	}
	public LinkedList<Integer> get_Order(){
		return this.order;
	}	
}



