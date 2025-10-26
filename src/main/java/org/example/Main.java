package org.example;

import org.example.graph.*;
import org.example.io.*;
import org.example.alg.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        List<Graph> graphs = InputLoader.loadGraphs("src/main/resources/datasets/small.json");
        KruskalAlgorithm kruskal = new KruskalAlgorithm();

        for (Graph g : graphs) {
            KruskalAlgorithm.Result result = kruskal.run(g);

            System.out.println("\nGraph:");
            System.out.println("Vertices: " + g.vertexCount() + ", Edges: " + g.edgeCount());
            System.out.println("MST weight = " + result.totalWeight);
            System.out.println("Edges in MST: " + result.mstEdges);
            System.out.println("Comparisons: " + result.comparisons + ", Unions: " + result.unions);
            System.out.println("Time: " + result.timeMs + " ms");
        }
    }
}
