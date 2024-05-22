package org.example;
import java.util.ArrayList;
import java.util.List;

public class FloydWarshall {
    private Graph graph;
    private int[][] dist;
    private int[][] next;

    public FloydWarshall(Graph graph) {
        this.graph = graph;
        int size = graph.getCities().size();
        dist = new int[size][size];
        next = new int[size][size];
        initialize();
        floydWarshall();
    }

    private void initialize() {
        int size = graph.getCities().size();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                } else {
                    dist[i][j] = graph.getAdjMatrix()[i][j];
                }
                if (graph.getAdjMatrix()[i][j] != Integer.MAX_VALUE / 2) {
                    next[i][j] = j;
                } else {
                    next[i][j] = -1;
                }
            }
        }
    }

    private void floydWarshall() {
        int size = graph.getCities().size();
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
    }

    public int getShortestDistance(String from, String to) {
        int fromIndex = graph.getCities().indexOf(from);
        int toIndex = graph.getCities().indexOf(to);
        return dist[fromIndex][toIndex];
    }

    public List<String> getPath(String from, String to) {
        int fromIndex = graph.getCities().indexOf(from);
        int toIndex = graph.getCities().indexOf(to);
        if (next[fromIndex][toIndex] == -1) {
            return null;
        }
        List<String> path = new ArrayList<>();
        path.add(from);
        while (fromIndex != toIndex) {
            fromIndex = next[fromIndex][toIndex];
            path.add(graph.getCities().get(fromIndex));
        }
        return path;
    }

    public String getGraphCenter() {
        int size = graph.getCities().size();
        int minMaxDist = Integer.MAX_VALUE;
        String center = null;
        for (int i = 0; i < size; i++) {
            int maxDist = 0;
            for (int j = 0; j < size; j++) {
                if (dist[i][j] > maxDist) {
                    maxDist = dist[i][j];
                }
            }
            if (maxDist < minMaxDist) {
                minMaxDist = maxDist;
                center = graph.getCities().get(i);
            }
        }
        return center;
    }
}
