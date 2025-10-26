package org.example.io;

import com.fasterxml.jackson.databind.*;
import org.example.graph.*;

import java.io.File;
import java.util.*;

public class InputLoader {

    static class GraphJson {
        public String name;
        public List<String> vertices;
        public List<Edge> edges;
    }

    static class DatasetJson {
        public String datasetName;
        public List<GraphJson> graphs;
    }

    // 🔹 ВАЖНО: именно этот метод вызывается в Main
    public static List<Graph> loadGraphs(String filePath) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        DatasetJson dataset = mapper.readValue(new File(filePath), DatasetJson.class);

        List<Graph> graphs = new ArrayList<>();
        for (GraphJson g : dataset.graphs) {
            graphs.add(new Graph(g.vertices, g.edges));
        }
        return graphs;
    }
}
