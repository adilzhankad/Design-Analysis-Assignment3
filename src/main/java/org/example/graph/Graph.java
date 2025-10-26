package org.example.graph;

import java.util.*;

public class Graph {
    private List<String> vertices;
    private List<Edge> edges;
    private Map<String, List<Edge>> adjacency;

    public Graph(List<String> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
        this.adjacency = new HashMap<>();


        for (String v : vertices) {
            adjacency.put(v, new ArrayList<>());
        }

        for (Edge e : edges) {
            adjacency.get(e.u).add(e);
            adjacency.get(e.v).add(new Edge(e.v, e.u, e.w)); // обратное ребро
        }
    }

    public List<String> getVertices() { return vertices; }
    public List<Edge> getEdges() { return edges; }
    public Map<String, List<Edge>> getAdjacency() { return adjacency; }

    public int vertexCount() { return vertices.size(); }
    public int edgeCount() { return edges.size(); }
}
