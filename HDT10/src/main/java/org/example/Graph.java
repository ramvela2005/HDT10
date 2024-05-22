package org.example;
import java.util.*;

public class Graph {
    private int numVertices;
    private int[][] adjMatrix;
    private Map<String, Integer> cityIndex;
    private List<String> cities;

    public Graph(int numVertices) {
        this.numVertices = numVertices;
        this.adjMatrix = new int[numVertices][numVertices];
        this.cityIndex = new HashMap<>();
        this.cities = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            Arrays.fill(adjMatrix[i], Integer.MAX_VALUE / 2); // Avoid overflow
        }
    }

    public void addCity(String city) {
        if (!cityIndex.containsKey(city)) {
            cityIndex.put(city, cities.size());
            cities.add(city);
        }
    }

    public void addEdge(String city1, String city2, int weight) {
        int index1 = cityIndex.get(city1);
        int index2 = cityIndex.get(city2);
        adjMatrix[index1][index2] = weight;
    }

    public void removeEdge(String city1, String city2) {
        int index1 = cityIndex.get(city1);
        int index2 = cityIndex.get(city2);
        adjMatrix[index1][index2] = Integer.MAX_VALUE / 2;
    }

    public int[][] getAdjMatrix() {
        return adjMatrix;
    }

    public List<String> getCities() {
        return cities;
    }

    public String getCity(int index) {
        return cities.get(index);
    }
}

