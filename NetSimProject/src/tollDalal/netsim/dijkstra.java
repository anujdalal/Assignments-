package tollDalal.netsim;

import java.io.*;
import java.util.*;
 
public class dijkstra {
   private static final Graph.Edge[] GRAPH = {
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
  // private static final String END = "24";
 
   public static void main(String[] args) {
      Graph g = new Graph(GRAPH);
     g.dijkstra(START);
     //g.printPath(END);
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
      public int cost = Integer.MAX_VALUE; // MAX_VALUE assumed to be infinity
      public Vertex previous = null;
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
 

   public Graph(Edge[] edges) {
      graph = new HashMap<>(edges.length);
 
      //one pass to find all vertices
      for (Edge e : edges) {
         if (!graph.containsKey(e.vert1)) graph.put(e.vert1, new Vertex(e.vert1));
         if (!graph.containsKey(e.vert2)) graph.put(e.vert2, new Vertex(e.vert2));
      }
 
      //another pass to set neighbouring vertices
      for (Edge e : edges) {
         graph.get(e.vert1).neighbours.put(graph.get(e.vert2), e.metric);
         graph.get(e.vert2).neighbours.put(graph.get(e.vert1), e.metric); // also do this for an undirected graph
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
 
         u = q.pollFirst(); // vertex with shortest distanceance (first iteration will return source)
         if (u.cost == Integer.MAX_VALUE) break; // we can ignore u (and any other remaining vertices) since they are unreachable
 
         //look at distanceances to each neighbour
         for (Map.Entry<Vertex, Integer> a : u.neighbours.entrySet()) {
            v = a.getKey(); //the neighbour in this iteration
 
            final int alternatedistance = u.cost + a.getValue();
            if (alternatedistance < v.cost) { // shorter path to neighbour found
               q.remove(v);
               v.cost = alternatedistance;
               v.previous = u;
               q.add(v);
            } 
         }
      }
   }
 
   /** Prints a path from the source to the specified vertex */
  /* public void printPath(String endName) {
      if (!graph.containsKey(endName)) {
         System.err.printf("Graph doesn't contain end vertex \"%s\"\n", endName);
         return;
      }
 
      graph.get(endName).printPath();
      System.out.println();
   }*/
   
   public void printAllPaths() {
      for (Vertex v : graph.values()) {
         v.printPath();
         System.out.println();
      }
   }
}
