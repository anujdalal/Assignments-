import java.io.*;
import java.util.*;
 
public class topology {
   private static final Graph.Edge[] GRAPH = {
		   new Graph.Edge("1", "2", 184),
           new Graph.Edge("1", "3", 840),
           new Graph.Edge("2", "4", 707),
           new Graph.Edge("2", "6", 882),
           new Graph.Edge("3", "7", 120),
           new Graph.Edge("3", "8", 497),
           new Graph.Edge("4", "5", 572),
           new Graph.Edge("5", "7", 941),
           new Graph.Edge("6", "9", 128),
           new Graph.Edge("7", "23", 492),
           new Graph.Edge("8", "10", 344),
           new Graph.Edge("9", "11", 782),
           new Graph.Edge("10", "13", 548),
           new Graph.Edge("11", "14", 318),
           new Graph.Edge("12", "14", 273),
           new Graph.Edge("12", "18",  50),
           new Graph.Edge("12", "21", 390),
           new Graph.Edge("13", "16", 327),
           new Graph.Edge("15", "17", 735),
           new Graph.Edge("15", "23", 337),
           new Graph.Edge("15", "26", 576),
           new Graph.Edge("16", "24", 455),
           new Graph.Edge("17", "19", 758),
           new Graph.Edge("18", "22", 332),
           new Graph.Edge("19", "21", 160),
           new Graph.Edge("20", "25", 942),
           new Graph.Edge("20", "26", 549),
           new Graph.Edge("22", "24", 310),
           new Graph.Edge("24", "25", 403),    
   };
   private static final String START = "1";
   private static final String END = "25";
 
   public static void main(String[] args) {
      Graph g = new Graph(GRAPH);
      g.dijkstra(START);
      g.printPath(END);
      //g.printAllPaths();
   }
}
 
class Graph {
   private final Map<String, Vertex> graph; // mapping of vertex names to Vertex objects, built from a set of Edges
 
   /** One edge of the graph (only used by Graph constructor) */
   public static class Edge {
      public final String v1, v2;
      public final int dist;
      public Edge(String v1, String v2, int dist) {
         this.v1 = v1;
         this.v2 = v2;
         this.dist = dist;
      }
   }
 
   /** One vertex of the graph, complete with mappings to neighbouring vertices */
   public static class Vertex implements Comparable<Vertex> {
      public final String name;
      public int dist = Integer.MAX_VALUE; // MAX_VALUE assumed to be infinity
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
            System.out.printf(" -> %s(%d)", this.name, this.dist);
         }
      }
 
      public int compareTo(Vertex other) {
         return Integer.compare(dist, other.dist);
      }
   }
 
   /** Builds a graph from a set of edges */
   public Graph(Edge[] edges) {
      graph = new HashMap<>(edges.length);
 
      //one pass to find all vertices
      for (Edge e : edges) {
         if (!graph.containsKey(e.v1)) graph.put(e.v1, new Vertex(e.v1));
         if (!graph.containsKey(e.v2)) graph.put(e.v2, new Vertex(e.v2));
      }
 
      //another pass to set neighbouring vertices
      for (Edge e : edges) {
         graph.get(e.v1).neighbours.put(graph.get(e.v2), e.dist);
         //graph.get(e.v2).neighbours.put(graph.get(e.v1), e.dist); // also do this for an undirected graph
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
         v.dist = v == source ? 0 : Integer.MAX_VALUE;
         q.add(v);
      }
 
      dijkstra(q);
   }
 
   /** Implementation of dijkstra's algorithm using a binary heap. */
   private void dijkstra(final NavigableSet<Vertex> q) {      
      Vertex u, v;
      while (!q.isEmpty()) {
 
         u = q.pollFirst(); // vertex with shortest distance (first iteration will return source)
         if (u.dist == Integer.MAX_VALUE) break; // we can ignore u (and any other remaining vertices) since they are unreachable
 
         //look at distances to each neighbour
         for (Map.Entry<Vertex, Integer> a : u.neighbours.entrySet()) {
            v = a.getKey(); //the neighbour in this iteration
 
            final int alternateDist = u.dist + a.getValue();
            if (alternateDist < v.dist) { // shorter path to neighbour found
               q.remove(v);
               v.dist = alternateDist;
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
   /** Prints the path from the source to every vertex (output order is not guaranteed) */
   public void printAllPaths() {
      for (Vertex v : graph.values()) {
         v.printPath();
         System.out.println();
      }
   }
}