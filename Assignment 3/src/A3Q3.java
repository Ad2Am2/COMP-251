import java.util.*;

public class A3Q3 {

	public static long[] num_pieces(long[] pieces, int[][] instructions) {
		// Build graph
		HashMap<Integer, ArrayList<int[]>> graph = new HashMap<>();
		int[] inDegree = new int[pieces.length];
		for (int[] instruction : instructions) {
			int target = instruction[0];
			int source = instruction[1];
			int multiplier = instruction[2];

			if (!graph.containsKey(source)) {
				graph.put(source, new ArrayList<>());
			}
			graph.get(source).add(new int[]{target, multiplier});
			inDegree[target]++;
		}

		// Topological sorting
		Queue<Integer> queue = new LinkedList<>();
		for (int i = 0; i < pieces.length; i++) {
			if (inDegree[i] == 0) {
				queue.offer(i);
			}
		}

		while (!queue.isEmpty()) {
			int current = queue.poll();
			processNode(current, pieces, graph, queue, inDegree);
		}

		return pieces;
	}

	private static void processNode(int node, long[] pieces, HashMap<Integer, ArrayList<int[]>> graph, Queue<Integer> queue, int[] inDegree) {
		if (graph.containsKey(node)) {
			for (int[] instruction : graph.get(node)) {
				int target = instruction[0];
				int multiplier = instruction[1];

				pieces[target] += multiplier * pieces[node];
				inDegree[target]--;

				if (inDegree[target] == 0) {
					queue.offer(target);
				}
			}
		}
	}
}
