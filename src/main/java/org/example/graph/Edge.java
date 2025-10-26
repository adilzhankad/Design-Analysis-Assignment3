package org.example.graph;

public class Edge implements Comparable<Edge> {
    public String u; // первая вершина
    public String v; // вторая вершина
    public int w;    // вес

    public Edge() {} // нужен для Jackson JSON

    public Edge(String u, String v, int w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.w, other.w);
    }

    @Override
    public String toString() {
        return "(" + u + "-" + v + ", " + w + ")";
    }
}
