package org.example;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

public class GraphTest {
    private Graph graph;

    @BeforeEach
    public void setUp() {
        graph = new Graph(4);
        graph.addCity("A");
        graph.addCity("B");
        graph.addCity("C");
        graph.addCity("D");
        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 2);
        graph.addEdge("C", "D", 3);
        graph.addEdge("A", "D", 10);
    }

    @Test
    public void testAddEdge() {
        int[][] adjMatrix = graph.getAdjMatrix();
        assertEquals(1, adjMatrix[graph.getCities().indexOf("A")][graph.getCities().indexOf("B")]);
        assertEquals(2, adjMatrix[graph.getCities().indexOf("B")][graph.getCities().indexOf("C")]);
        assertEquals(3, adjMatrix[graph.getCities().indexOf("C")][graph.getCities().indexOf("D")]);
        assertEquals(10, adjMatrix[graph.getCities().indexOf("A")][graph.getCities().indexOf("D")]);
    }

    @Test
    public void testRemoveEdge() {
        graph.removeEdge("A", "B");
        int[][] adjMatrix = graph.getAdjMatrix();
        assertEquals(Integer.MAX_VALUE / 2, adjMatrix[graph.getCities().indexOf("A")][graph.getCities().indexOf("B")]);
    }

    @Test
    public void testFloydWarshall() {
        FloydWarshall fw = new FloydWarshall(graph);
        assertEquals(6, fw.getShortestDistance("A", "D"));
        List<String> path = fw.getPath("A", "D");
        assertEquals(Arrays.asList("A", "B", "C", "D"), path);
    }

}


