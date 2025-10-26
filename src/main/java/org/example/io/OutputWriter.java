package org.example.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.example.alg.KruskalAlgorithm;
import org.example.alg.PrimAlgorithm;
import org.example.graph.Graph;

import java.io.File;
import java.util.*;

public class OutputWriter {

    public static void writeResults(
            List<Graph> graphs,
            Map<String, KruskalAlgorithm.Result> kruskalResults,
            Map<String, PrimAlgorithm.Result> primResults,
            String outputPath
    ) throws Exception {

        List<Map<String, Object>> outputList = new ArrayList<>();

        for (Graph g : graphs) {
            String graphName = "Graph_" + g.vertexCount() + "_" + g.edgeCount();

            Map<String, Object> record = new LinkedHashMap<>();
            record.put("graph", graphName);
            record.put("vertexCount", g.vertexCount());
            record.put("edgeCount", g.edgeCount());

            KruskalAlgorithm.Result kr = kruskalResults.get(graphName);
            PrimAlgorithm.Result pr = primResults.get(graphName);

            if (kr != null) {
                Map<String, Object> k = new LinkedHashMap<>();
                k.put("totalWeight", kr.totalWeight);
                k.put("edges", kr.mstEdges);
                k.put("timeMs", kr.timeMs);
                k.put("comparisons", kr.comparisons);
                k.put("unions", kr.unions);
                record.put("kruskal", k);
            }

            if (pr != null) {
                Map<String, Object> p = new LinkedHashMap<>();
                p.put("totalWeight", pr.totalWeight);
                p.put("edges", pr.mstEdges);
                p.put("timeMs", pr.timeMs);
                p.put("comparisons", pr.comparisons);
                p.put("heapOps", pr.heapOps);
                record.put("prim", p);
            }

            outputList.add(record);
        }

        ObjectWriter writer = new ObjectMapper().writerWithDefaultPrettyPrinter();
        writer.writeValue(new File(outputPath), outputList);

        System.out.println("\n✅ Results saved to: " + outputPath);
    }
}
