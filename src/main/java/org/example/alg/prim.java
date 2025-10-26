package org.example.alg;

import java.util.*;

class Edge {
    int to;
    int weight;
    Edge(int to, int weight) {
        this.to = to;
        this.weight = weight;
    }
}

class Node implements Comparable<Node> {
    int vertex;
    int weight;
    Node(int vertex, int weight) {
        this.vertex = vertex;
        this.weight = weight;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.weight, other.weight);
    }
}

public class PrimAlgorithm {
    static int primMST(int n, List<List<Edge>> graph) {
        boolean[] visited = new boolean[n];
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(0, 0)); // стартовая вершина (0)
        int totalWeight = 0;
        int edgesUsed = 0;

        while (!pq.isEmpty() && edgesUsed < n) {
            Node current = pq.poll();
            int u = current.vertex;

            if (visited[u]) continue;
            visited[u] = true;
            totalWeight += current.weight;
            edgesUsed++;

            for (Edge edge : graph.get(u)) {
                if (!visited[edge.to]) {
                    pq.offer(new Node(edge.to, edge.weight));
                }
            }
        }

        if (edgesUsed != n) {
            System.out.println("Граф несвязный — MST невозможно построить полностью.");
            return -1;
        }

        return totalWeight;
    }

    public static void main(String[] args) {
        int n = 6;
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());

        // Добавляем рёбра (u, v, w)
        int[][] edges = {
                {0,1,13},{0,2,18},{0,3,17},{0,4,14},{0,5,22},
                {1,2,26},{1,4,19},{2,3,30},{3,5,22}
        };

        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            graph.get(u).add(new Edge(v, w));
            graph.get(v).add(new Edge(u, w)); // неориентированный
        }

        int total = primMST(n, graph);
        System.out.println("Сумма весов MST: " + total);
    }
}
