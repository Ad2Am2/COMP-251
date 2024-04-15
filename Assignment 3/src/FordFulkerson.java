import java.util.*;
import java.io.File;

public class FordFulkerson {

	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> path = new ArrayList<Integer>();
		int numNodes = graph.getNbNodes();
		int[] rbtColor = new int[numNodes];
		return pathDFSHelper(source, destination, graph, rbtColor);
	}

	public static ArrayList<Integer> pathDFSHelper(Integer source, Integer destination, WGraph graph, int[] rbtColor){
		ArrayList<Integer> path = new ArrayList<Integer>();
		rbtColor[source] = 1;

		if (source.equals(destination)) {
			path.add(source);
			return path;
		}


		ArrayList<Edge> listOfEdges = graph.listOfEdgesSorted();

		int length = listOfEdges.size();


		for (int i = 0; i < listOfEdges.size(); i++) {

			Edge e = listOfEdges.get(i);

			if (rbtColor[e.nodes[1]] == 0 && e.nodes[0] == source){
				path = pathDFSHelper(e.nodes[1], destination, graph, rbtColor);

				if (!(path.isEmpty())) {
					path.add(0, source);
					return path;
				}

			}
		}


		rbtColor[source] = 2;


		return path;
	}



	public static String fordfulkerson( WGraph graph){
		String answer="";
		int maxFlow = 0;

		WGraph flow = residualGraph(graph, null);
		WGraph rGraph = residualGraph(graph, flow);
		ArrayList<Integer> path = pathDFS(rGraph.getSource(), rGraph.getDestination(), rGraph);
		while (!path.isEmpty()) {
			maxFlow += findMinEdge(getAllEdges(rGraph, path));
			augment(flow, rGraph, path);
			rGraph = residualGraph(graph, flow);
			path = pathDFS(rGraph.getSource(), rGraph.getDestination(), rGraph);
		}

		answer += maxFlow + "\n" + graph.toString();	
		return answer;
	}

	private static WGraph residualGraph(WGraph graph, WGraph previousGraph) {


		WGraph outputGraph = new WGraph();


		// Make empty residual graph
		if (previousGraph == null) {

			ArrayList<Edge> edges = graph.getEdges();

			for (int i = 0; i < edges.size(); i++) {
				Edge edge = edges.get(i);

				outputGraph.setEdge(edge.nodes[0], edge.nodes[1], 0);
			}
			return outputGraph;


		}

		outputGraph.setSource(graph.getSource());
		outputGraph.setDestination(graph.getDestination());



		ArrayList<Edge> edges = graph.getEdges();

		for (int i = 0; i < edges.size(); i++) {
			Edge edge = edges.get(i);

			int flow = getFlow(previousGraph, edge);
			int capacity = edge.weight;
			if (!(flow >= capacity)) {
				Edge forwardEdge = new Edge(edge.nodes[0], edge.nodes[1], capacity-flow);
				outputGraph.addEdge(forwardEdge);
			} else if (flow > 0) {
				Edge backwardEdge = new Edge(edge.nodes[1], edge.nodes[0], -flow);
				outputGraph.addEdge(backwardEdge);
			}
		}
		return outputGraph;
	}

	private static int getFlow(WGraph residualGraph, Edge edge) {

		Edge e = residualGraph.getEdge(edge.nodes[0], edge.nodes[1]);

		return e.weight;

	}


	private static Edge[] getAllEdges(WGraph graph, ArrayList<Integer> path) {
		Edge[] edgeList = new Edge[path.size()-1];

		for (int i = 0; i < path.size()-1; i++){
			edgeList[i] = graph.getEdge(path.get(i),path.get(i+1));
		}
		return edgeList;
	}


	private static int findMinEdge (Edge[] edges) {
		int minEdge = Integer.MAX_VALUE;
		for (Edge edge : edges) {
			minEdge = Math.min(minEdge, edge.weight);
		}
		return minEdge;
	}



	private static WGraph augment(WGraph flow, WGraph rGraph, ArrayList<Integer> path) {
		Edge[] edgeList = getAllEdges(rGraph, path);

		int beta = findMinEdge(edgeList);

		for (Edge edge : edgeList) {
			if (edge.weight > 0) {
				flow.setEdge(edge.nodes[0], edge.nodes[1], beta);
			}
		}
		return flow;
	}


	public static void main(String[] args){
		String file = args[0];
		File f = new File(file);
		WGraph g = new WGraph(file);
	    System.out.println(fordfulkerson(g));
	 }
}

