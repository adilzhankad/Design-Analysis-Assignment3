package org.example;

import org.example.graph.*;
import org.example.io.*;
import org.example.alg.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        // список всех датасетов
        String[] inputFiles = {
                "src/main/resources/datasets/small.json",
                "src/main/resources/datasets/medium.json",
                "src/main/resources/datasets/large.json"
        };

        // папка для результатов
        String resultsFolder = "results";

        KruskalAlgorithm kruskal = new KruskalAlgorithm();
        PrimAlgorithm prim = new PrimAlgorithm();

        for (String inputPath : inputFiles) {
            // определяем имя датасета (small / medium / large)
            String datasetName = inputPath.substring(inputPath.lastIndexOf("/") + 1).replace(".json", "");
            String outputPath = resultsFolder + "/output_" + datasetName + ".json";

            System.out.println("\n===============================");
            System.out.println("Processing dataset: " + datasetName);
            System.out.println("===============================");

            List<Graph> graphs = InputLoader.loadGraphs(inputPath);

            Map<String, KruskalAlgorithm.Result> krResults = new HashMap<>();
            Map<String, PrimAlgorithm.Result> prResults = new HashMap<>();

            for (Graph g : graphs) {
                String graphName = "Graph_" + g.vertexCount() + "_" + g.edgeCount();

                System.out.println("\n--- " + graphName + " ---");
                System.out.println("Vertices: " + g.vertexCount() + ", Edges: " + g.edgeCount());

                KruskalAlgorithm.Result kr = kruskal.run(g);
                PrimAlgorithm.Result pr = prim.run(g);

                System.out.println("Kruskal → weight: " + kr.totalWeight + " | time: " + kr.timeMs + " ms");
                System.out.println("Prim → weight: " + pr.totalWeight + " | time: " + pr.timeMs + " ms");

                krResults.put(graphName, kr);
                prResults.put(graphName, pr);
            }

            // записываем результаты в JSON
            OutputWriter.writeResults(graphs, krResults, prResults, outputPath);
        }

        System.out.println("\n All datasets processed successfully!");
    }
}
