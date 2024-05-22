package org.example;
import java.io.*;
import java.util.*;

public class Main {
    private static Graph graph;
    private static FloydWarshall fw;

    public static void main(String[] args) {
        graph = loadGraphFromFile("guategrafos.txt");
        if (graph != null) {
            fw = new FloydWarshall(graph);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Seleccione una opción:");
                System.out.println("1. Mostrar la ruta más corta entre dos ciudades");
                System.out.println("2. Mostrar la ciudad que es el centro del grafo");
                System.out.println("3. Modificar el grafo");
                System.out.println("4. Finalizar el programa");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline
                switch (choice) {
                    case 1:
                        System.out.print("Ingrese la ciudad origen: ");
                        String city1 = scanner.nextLine();
                        System.out.print("Ingrese la ciudad destino: ");
                        String city2 = scanner.nextLine();
                        int distance = fw.getShortestDistance(city1, city2);
                        if (distance < Integer.MAX_VALUE / 2) {
                            System.out.println("La ruta más corta de " + city1 + " a " + city2 + " tiene una distancia de " + distance + " KM.");
                            System.out.println("Ruta: " + fw.getPath(city1, city2));
                        } else {
                            System.out.println("No hay ruta disponible entre " + city1 + " y " + city2 + ".");
                        }
                        break;
                    case 2:
                        System.out.println("La ciudad que es el centro del grafo es: " + fw.getGraphCenter());
                        break;
                    case 3:
                        System.out.println("Seleccione una opción de modificación:");
                        System.out.println("a. Hay interrupción de tráfico entre un par de ciudades");
                        System.out.println("b. Se establece una conexión entre ciudad1 y ciudad2 con valor de x KM de distancia");
                        String modChoice = scanner.nextLine();
                        if (modChoice.equals("a")) {
                            System.out.print("Ingrese la ciudad origen: ");
                            String removeCity1 = scanner.nextLine();
                            System.out.print("Ingrese la ciudad destino: ");
                            String removeCity2 = scanner.nextLine();
                            graph.removeEdge(removeCity1, removeCity2);
                        } else if (modChoice.equals("b")) {
                            System.out.print("Ingrese la ciudad origen: ");
                            String addCity1 = scanner.nextLine();
                            System.out.print("Ingrese la ciudad destino: ");
                            String addCity2 = scanner.nextLine();
                            System.out.print("Ingrese la distancia en KM: ");
                            int addDistance = scanner.nextInt();
                            scanner.nextLine();  // Consume newline
                            graph.addEdge(addCity1, addCity2, addDistance);
                        }
                        fw = new FloydWarshall(graph);
                        System.out.println("Las rutas más cortas se han recalculado.");
                        break;
                    case 4:
                        System.out.println("Programa finalizado.");
                        return;
                    default:
                        System.out.println("Opción no válida. Inténtelo de nuevo.");
                }
            }
        }
    }

    private static Graph loadGraphFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            Set<String> citiesSet = new HashSet<>();
            for (String l : lines) {
                String[] parts = l.split(",");
                citiesSet.add(parts[0].trim());
                citiesSet.add(parts[1].trim());
            }
            Graph graph = new Graph(citiesSet.size());
            for (String city : citiesSet) {
                graph.addCity(city);
            }
            for (String l : lines) {
                String[] parts = l.split(",");
                String city1 = parts[0].trim();
                String city2 = parts[1].trim();
                int distance = Integer.parseInt(parts[2].trim());
                graph.addEdge(city1, city2, distance);
            }
            return graph;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
