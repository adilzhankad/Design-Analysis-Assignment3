package org.example.alg;

import org.example.graph.Edge;
import org.example.graph.Graph;
import java.util.*;

public class KruskalAlgorithm {

    // Класс для хранения результата работы алгоритма
    public static class Result {
        public List<Edge> mstEdges = new ArrayList<>();  // список рёбер MST
        public int totalWeight = 0;                      // суммарный вес
        public int comparisons = 0;                      // сколько сравнений
        public int unions = 0;                           // сколько объединений
        public long timeMs = 0;                          // время выполнения
    }

    // Основной метод, запускающий алгоритм Краскала
    public Result run(Graph graph) {
        Result res = new Result();
        long start = System.nanoTime();

        // 1️⃣ Сортируем рёбра по весу
        List<Edge> edges = new ArrayList<>(graph.getEdges());
        edges.sort(Comparator.comparingInt(e -> e.w));
        res.comparisons += edges.size();

        // 2️⃣ Создаём множество вершин
        DisjointSet ds = new DisjointSet();
        ds.makeSet(graph.getVertices());

        // 3️⃣ Проходим по рёбрам в порядке возрастания
        for (Edge e : edges) {
            String rootU = ds.find(e.u);
            String rootV = ds.find(e.v);

            // если вершины в разных множествах → добавляем ребро
            if (!rootU.equals(rootV)) {
                ds.union(rootU, rootV);
                res.mstEdges.add(e);
                res.totalWeight += e.w;
                res.unions++;
            }
        }

        long end = System.nanoTime();
        res.timeMs = (end - start) / 1_000_000; // переводим в миллисекунды
        return res;
    }
}
