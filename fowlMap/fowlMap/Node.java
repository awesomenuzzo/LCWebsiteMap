package fowlMap;

import edu.princeton.cs.algs4.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.lang.Math.sqrt;

public class Node {
    int nodeID;
    java.awt.geom.Point2D position;
    ArrayList<Node> neighbors;
    HashMap<Node, Double> weights;
    ArrayList<DirectedEdge> edges;

    Node(int nodeID, double x, double y){
        this.nodeID = nodeID;
        this.position.setLocation(x,y);
        this.neighbors = new ArrayList<Node>();
        this.weights = new HashMap<>();
    }

    public String toStringAll(){
        return "NodeId: " + this.nodeID + " X: " + this.position.getX() + " Y: " + this.position.getY();
    }
    public int getNodeID(){
        return this.nodeID;
    }


    public void addNeighbor(Node neighborNode){
        this.neighbors.add(neighborNode);
    }

    public Double getDistance(double x1, double y1, double x2, double y2){
        return sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
    }
    public void addWeights(){
        for (Node n: this.neighbors) {
            Double weight = getDistance(this.position.getX(), this.position.getY(), n.position.getX(), n.position.getY());
            this.weights.put(n, weight);
        }
    }

    public Point2D getPosition(){
        return this.position;
    }

    public Double getX(){
        return this.position.getX();
    }
    public Double getY(){
        return this.position.getY();
    }
    
//    public void makeEdges(){
//        for (Map.Entry<Node, Double> entry: this.weights.entrySet()) {
//            Node n = entry.getKey();
//            Double weight = entry.getValue();
//            DirectedEdge edge = new DirectedEdge(this.nodeID, n.nodeID, weight);
//            this.edges.add(edge);
//        }
//    }


    public static HashMap generateNodes(String fileName){
        HashMap<Integer, Node> nodes = new HashMap<Integer, Node>();
        String line = "";
        String splitBy = ",";
        int count = 0;
        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] row = line.split(splitBy);
                Node n = new Node(Integer.parseInt(row[0]), Double.parseDouble(row[1]), Double.parseDouble(row[2]));
                if(!nodes.containsKey(n.nodeID)){
                    nodes.put(n.nodeID, n);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        try{
        BufferedReader br2 = new BufferedReader(new FileReader(fileName));
        while ((line = br2.readLine()) != null)   //returns a Boolean value
        {
            String[] row = line.split(splitBy);
            Node a = nodes.get(Integer.parseInt(row[0]));
//            System.out.println(row[3]);
            if(row.length >3){
                String[] list = row[3].split("\\|");
                for (String s : list) {
                    if(s.contains("\"")) s = s.replace("\"", "");
                    Integer i = Integer.parseInt(s);
                    a.addNeighbor(nodes.get(i));
                }
            }

        }
    }catch (IOException e){
        e.printStackTrace();
    }
        return nodes;
    }

    public static EdgeWeightedDigraph generateGraph(HashMap<Integer, Node> nodes){
        ArrayList<DirectedEdge> edges = new ArrayList<>();
        EdgeWeightedDigraph graph = new EdgeWeightedDigraph(nodes.size() +1);
        for (Map.Entry<Integer, Node> entry: nodes.entrySet()) {
            Node n = entry.getValue();
            n.addWeights();
        }
        for (Map.Entry<Integer, Node> entry: nodes.entrySet()) {
            Node n = entry.getValue();
            for (Node neighbor: n.neighbors) {
                DirectedEdge e = new DirectedEdge(n.nodeID, neighbor.nodeID, n.weights.get(neighbor));
                graph.addEdge(e);
            }
        }

        return graph;
    }

    public static DijkstraSP getDijkstra(EdgeWeightedDigraph G, int start){
        Map<Integer, DijkstraSP> cache = new HashMap<>();
        if(cache.containsKey(start)){
            return cache.get(start);
        }else{
            return new DijkstraSP(G, start);
        }
    }

    public static Iterable<DirectedEdge> dijkstraSearch(EdgeWeightedDigraph G, int start, int end){
        DijkstraSP d = getDijkstra(G, start);
        if(d.hasPathTo(end)){
            return d.pathTo(end);
        }
        else return null;
    }

    public static void main(String[] args) {
        HashMap<Integer, Node> nodes = generateNodes("src/fowlMap/nodes5.csv");
        EdgeWeightedDigraph graph = generateGraph(nodes);
        Iterable<DirectedEdge> d = dijkstraSearch(graph, 1, 3);
        System.out.println(d.toString());

//        for (Map.Entry<Integer, Node> entry: nodes.entrySet()){
//            Node n = entry.getValue();
//            n.addWeights();
//            for (Map.Entry<Node, Double> w :entry.getValue().weights.entrySet()) {
//
//                System.out.println("Node : "+ n.getNodeID() + " Neighbor: "+ w.getKey().getNodeID() + " Weight: " + w.getValue());
//            }
//
//        }


//            System.out.println(entry.getKey());
//            System.out.println(entry.getValue().toString());
//
//
//        }
    }
}