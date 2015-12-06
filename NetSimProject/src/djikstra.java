package tollDalal.netsim;

import java.io.*;
import java.util.*;
 
public class Dijkstra {
   private static final Graph.Edge[] GRAPH = {
      new Graph.Edge("1", "2", 1500000),
      new Graph.Edge("1", "3", 1500000),
      new Graph.Edge("2", "4", 1500000),
      new Graph.Edge("2", "6", 2000000),
      new Graph.Edge("3", "7", 1500000),
      new Graph.Edge("3", "8", 2000000),
      new Graph.Edge("4", "5", 1500000),
      new Graph.Edge("5", "7", 2000000),
      new Graph.Edge("6", "9", 2000000),
      new Graph.Edge("7", "23", 2500000),
      new Graph.Edge("8", "10", 1500000),
      new Graph.Edge("9", "11", 2000000),
      new Graph.Edge("10", "13", 1500000),
      new Graph.Edge("11", "14", 2000000),
      new Graph.Edge("12", "14", 2000000),
      new Graph.Edge("12", "18", 2000000),
      new Graph.Edge("12", "21", 1500000),
      new Graph.Edge("13", "16", 2000000),
      new Graph.Edge("15", "17", 2000000),
      new Graph.Edge("15", "23", 1500000),
      new Graph.Edge("15", "26", 2000000),
      new Graph.Edge("16", "24", 2000000),
      new Graph.Edge("17", "19", 1500000),
      new Graph.Edge("18", "22", 1500000),
      new Graph.Edge("19", "21", 1500000),
      new Graph.Edge("20", "25", 2000000),
      new Graph.Edge("20", "26", 1500000),
      new Graph.Edge("22", "24", 1500000),
      new Graph.Edge("24", "25", 1500000),
   };
   //this is for testing purposes, I'm working on getting it to run through the entire thing
   private static final String START = "1";
   private static final String END = "24";
 
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
      public final String vert1, vert2;
      public final int distance;
      public Edge(String vert1, String vert2, int distance) {
         this.vert1 = vert1;
         this.vert2 = vert2;
         this.distance = distance;
      }
   }
 

   public static class Vertex implements Comparable<Vertex> {
      public final String name;
      public int distance = Integer.MAX_VALUE; // MAX_VALUE assumed to be infinity
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
            System.out.printf(" -> %s(%d)", this.name, this.distance);
         }
      }
 
      public int compareTo(Vertex other) {
         return Integer.compare(distance, other.distance);
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
         graph.get(e.vert1).neighbours.put(graph.get(e.vert2), e.distance);
         graph.get(e.vert2).neighbours.put(graph.get(e.vert1), e.distance); // also do this for an undirected graph
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
         v.distance = v == source ? 0 : Integer.MAX_VALUE;
         q.add(v);
      }
 
      dijkstra(q);
   }
 
   /** Implementation of dijkstra's algorithm using a binary heap. */
   private void dijkstra(final NavigableSet<Vertex> q) {      
      Vertex u, v;
      while (!q.isEmpty()) {
 
         u = q.pollFirst(); // vertex with shortest distanceance (first iteration will return source)
         if (u.distance == Integer.MAX_VALUE) break; // we can ignore u (and any other remaining vertices) since they are unreachable
 
         //look at distanceances to each neighbour
         for (Map.Entry<Vertex, Integer> a : u.neighbours.entrySet()) {
            v = a.getKey(); //the neighbour in this iteration
 
            final int alternatedistance = u.distance + a.getValue();
            if (alternatedistance < v.distance) { // shorter path to neighbour found
               q.remove(v);
               v.distance = alternatedistance;
               v.previous = u;
               q.add(v);
            } 
         }
      }
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
