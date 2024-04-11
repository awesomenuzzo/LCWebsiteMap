package fowlMap;

import edu.princeton.cs.algs4.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

class mGraph {

    public static int[] parseLine(String line){
        Pattern spaces =  Pattern.compile("\\s+");
        String[] words = spaces.split(line);
        return new int[]{Integer.parseInt(words[0]), Integer.parseInt(words[1])};
    }

    public static EdgeWeightedGraph readG(In file) {
//        In file = new In(filename);
        int vertices = file.readInt();
        int edges = file.readInt();
        EdgeWeightedGraph g = new EdgeWeightedGraph(vertices, edges);
        for (int i = 0; i< edges; i++) {
            int v = file.readInt();
            int u = file.readInt();
            int w = file.readInt();
            Edge e = new Edge(v, u, w);
            g.addEdge(e);
        }

        return g;

    }


    public DijkstraUndirectedSP getDijkstra(EdgeWeightedGraph G, int start){
       Map<Integer, DijkstraUndirectedSP> cache = new HashMap<>();
       if(cache.containsKey(start)){
           return cache.get(start);
       }else{
           return new DijkstraUndirectedSP(G, start);
       }
    }

    public Iterable<Edge> dijkstraSearch(EdgeWeightedGraph G, int start, int end){
        DijkstraUndirectedSP d = getDijkstra(G, start);
        if(d.hasPathTo(end)){
            return d.pathTo(end);
        }
        else return null;
    }

    public static void main(String[] args) {
        EdgeWeightedGraph G = new EdgeWeightedGraph(8, 12);


    }
}