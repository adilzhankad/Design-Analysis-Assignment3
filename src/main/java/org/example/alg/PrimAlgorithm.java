package org.example.alg;

import org.example.graph.Edge;
import org.example.graph.Graph;

import java.util.*;

public class PrimAlgorithm {

    public static class Result {
        public List<Edge> mstEdges = new ArrayList<>();
        public int totalWeight = 0;
        public int comparisons = 0;
        public int heapOps = 0;
        public long timeMs = 0;
    }

    public Result run(Graph graph) {
        Result res = new Result();
        long startTime = System.nanoTime();

        // если граф пустой
        if (graph.getVertices().isEmpty()) return res;

        // стартовая вершина
        String startVertex = graph.getVertices().get(0);

        Set<String> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.w));

        visited.add(startVertex);
        pq.addAll(graph.getAdjacency().get(startVertex));
        res.heapOps += pq.size();

        while (!pq.isEmpty() && visited.size() < graph.vertexCount()) {
            Edge e = pq.poll();
            res.heapOps++;

            // выбираем вершину, которая ещё не посещена
            String next = !visited.contains(e.u) ? e.u : e.v;
            if (visited.contains(next)) continue;

            res.mstEdges.add(e);
            res.totalWeight += e.w;
            visited.add(next);

            for (Edge ne : graph.getAdjacency().get(next)) {
                if (!visited.contains(ne.v)) {
                    pq.offer(ne);
                    res.heapOps++;
                }
            }

            res.comparisons++;
        }

        long endTime = System.nanoTime();
        res.timeMs = (endTime - startTime) / 1_000_000; // в миллисекундах
        return res;
    }
}
