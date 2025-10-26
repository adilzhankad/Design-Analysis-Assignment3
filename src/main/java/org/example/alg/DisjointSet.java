package org.example.alg;

import java.util.HashMap;
import java.util.Map;

public class DisjointSet {

    private final Map<String, String> parent = new HashMap<>();
    private final Map<String, Integer> rank = new HashMap<>();

    // Создаёт отдельное множество для каждой вершины
    public void makeSet(Iterable<String> vertices) {
        for (String v : vertices) {
            parent.put(v, v);
            rank.put(v, 0);
        }
    }

    // Находит корень множества вершины (сжатие пути)
    public String find(String v) {
        if (!parent.get(v).equals(v)) {
            parent.put(v, find(parent.get(v)));
        }
        return parent.get(v);
    }

    // Объединяет два множества (если они разные)
    public void union(String u, String v) {
        String rootU = find(u);
        String rootV = find(v);

        if (rootU.equals(rootV)) return;

        int rankU = rank.get(rootU);
        int rankV = rank.get(rootV);

        if (rankU < rankV) {
            parent.put(rootU, rootV);
        } else if (rankU > rankV) {
            parent.put(rootV, rootU);
        } else {
            parent.put(rootV, rootU);
            rank.put(rootU, rankU + 1);
        }
    }
}
