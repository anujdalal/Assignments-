package tollDalal.netsim;

import java.io.*;
import java.util.*;
 
public class dijkstra {
   private static final Graph.Edge[] GRAPH = {
		   
		   //attributes of each edge are set up according to the OCT file
		   new Graph.Edge("1", "2", 1500000, 184),
           new Graph.Edge("1", "3", 1500000, 840),
           new Graph.Edge("2", "4", 1500000, 707),
           new Graph.Edge("2", "6", 2000000, 882),
           new Graph.Edge("3", "7", 1500000, 120),
           new Graph.Edge("3", "8", 2000000, 497),
           new Graph.Edge("4", "5", 1500000, 572),
           new Graph.Edge("5", "7", 2000000, 941),
           new Graph.Edge("6", "9", 2000000, 128),
           new Graph.Edge("7", "23", 2500000, 492),
           new Graph.Edge("8", "10", 1500000, 344),
           new Graph.Edge("9", "11", 2000000, 782),
           new Graph.Edge("10", "13", 1500000, 548),
           new Graph.Edge("11", "14", 2000000, 318),
           new Graph.Edge("12", "14", 2000000, 273),
           new Graph.Edge("12", "18", 2000000,  50),
           new Graph.Edge("12", "21", 1500000, 390),
           new Graph.Edge("13", "16", 2000000, 327),
           new Graph.Edge("15", "17", 2000000, 735),
           new Graph.Edge("15", "23", 1500000, 337),
           new Graph.Edge("15", "26", 2000000, 576),
           new Graph.Edge("16", "24", 2000000, 455),
           new Graph.Edge("17", "19", 1500000, 758),
           new Graph.Edge("18", "22", 1500000, 332),
           new Graph.Edge("19", "21", 1500000, 160),
           new Graph.Edge("20", "25", 2000000, 942),
           new Graph.Edge("20", "26", 1500000, 549),
           new Graph.Edge("22", "24", 1500000, 310),
           new Graph.Edge("24", "25", 1500000, 403), 
   };
   //this is for testing purposes, I'm working on getting it to run through the entire thing
   private static final String START = "1";
   private static final String END = "26";
 
   public static void main(String[] args) {
      Graph g = new Graph(GRAPH);
      
      
     g.dijkstra(START);
     g.printPath(END);
     g.printAllPaths();
   }
}
 
class Graph {
   private final Map<String, Vertex> graph; // mapping of vertex names to Vertex objects, built from a set of Edges
 

   public static class Edge {
	   /*an Edge has the following properties:
	      * vert1 and vert2: the two vertices it connects,  
	      * Cij: the link capacity in bps, 
	      * metric: the cost of that link (100,000,000/Cij), for use with Dijkstra
	      * Lij: the physical length of the link in KM
	      */
	      public final String vert1, vert2;
	      public final int Cij, Lij; 
	      public int metric;
	      public Edge(String vert1, String vert2, int Cij, int Lij) {
	         this.vert1 = vert1;
	         this.vert2 = vert2;
	         this.Cij = Cij;;
	         this.Lij = Lij;
	         this.metric = (100000000/Cij);
	         
	      }
   }
 

   public static class Vertex implements Comparable<Vertex> {
      public final String name;
      //set the cost of all nodes to infinity initially
      public int cost = Integer.MAX_VALUE; 
      public Vertex previous = null;
      
      //create a hash map containing all vertex keys
      public final Map<Vertex, Integer> neighbours = new HashMap<>();
 
      public Vertex(String name) {
         this.name = name;
      }
 
      private void printPath() {
         if (this == this.previous) {
            System.out.printf("%s", this.name);
         } else if (this.previous == null) {
            System.out.printf("%s(unreached)", this.name);
         } else {
            this.previous.printPath();
            System.out.printf(" -> %s(%d)", this.name, this.cost);
         }
      }
 
      public int compareTo(Vertex other) {
         return Integer.compare(cost, other.cost);
      }
   }
 

   //parameter is an array of edges
   public Graph(Edge[] edges) {
      graph = new HashMap<>(edges.length);
 
      //one pass to find all vertices
      for (Edge e : edges) {
    	 
    	 //if the first vertex on the edge is not present, add the first vertex to the above HashMap
         if (!graph.containsKey(e.vert1)) graph.put(e.vert1, new Vertex(e.vert1));
         //if the second one if not present, add it as well
         if (!graph.containsKey(e.vert2)) graph.put(e.vert2, new Vertex(e.vert2));
      }
 
      //another pass to set neighbouring vertices
      for (Edge e : edges) {
    	 //
         graph.get(e.vert1).neighbours.put(graph.get(e.vert2), e.metric);
         graph.get(e.vert2).neighbours.put(graph.get(e.vert1), e.metric);
      }
   }
 
   /** Runs dijkstra using a specified source vertex */ 
   public void dijkstra(String startName) {
      if (!graph.containsKey(startName)) {
         System.err.printf("Graph doesn't contain start vertex \"%s\"\n", startName);
         return;
      }
      final Vertex source = graph.get(startName);
      NavigableSet<Vertex> q = new TreeSet<>();
 
      // set-up vertices
      for (Vertex v : graph.values()) {
         v.previous = v == source ? source : null;
         v.cost = v == source ? 0 : Integer.MAX_VALUE;
         q.add(v);
      }
 
      dijkstra(q);
   }
 
   /** Implementation of dijkstra's algorithm using a binary heap. */
   private void dijkstra(final NavigableSet<Vertex> q) {      
      Vertex u, v;
      while (!q.isEmpty()) {
 
         u = q.pollFirst(); // vertex with shortest distance (first iteration will return source)
         
         //if the cost of vertex u is "infinity", ignore u.
         if (u.cost == Integer.MAX_VALUE) break;
 
         //look at distances to each neighbour
         for (Map.Entry<Vertex, Integer> a : u.neighbours.entrySet()) {
            v = a.getKey(); //the neighbour in this iteration
 
            final int alternateCost = u.cost + a.getValue();
            if (alternateCost < v.cost) { // shorter path to neighbour found
               q.remove(v);
               v.cost = alternateCost;
               v.previous = u;
               q.add(v);
            } 
         }
      }
   }
   
   
   
   public void delays(){
	  //this method calculated the various delays in this network. 
	   Edge e = new Edge(null, null, 0, 0); 
	   int Fij = 0; //flow between link i to link j
	   double pij = (0.000005*e.Lij); //propagation delay for each link
	   int L = (1500*8); // AVG Length of each packet in bits
	   double ti = 0.0001; //processing delay
	   double Dpq = 0; //avg traffic per second between two nodes
	   int delta = 0; //total incoming packet rate
	   
	   
	   
	   
	   
	   
	   
	   return;
   }
 
   /** Prints a path from the source to the specified vertex */
  public void printPath(String endName) {
      if (!graph.containsKey(endName)) {
         System.err.printf("Graph doesn't contain end vertex \"%s\"\n", endName);
         return;
      }
 
      graph.get(endName).printPath();
      System.out.println();
   }
   
   public void printAllPaths() {
      for (Vertex v : graph.values()) {
         v.printPath();
         System.out.println();
      }
   }
}
