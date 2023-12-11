package lib.graph;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Comparator;

public class Graph<T> {
    private Map<Integer, Node<T>> nodes;
    private int[][] adjacencyMatrix;
    private Comparator<T> comparator;

    public Graph(Comparator<T> comparator) {
        this.comparator = comparator;
        this.nodes = new HashMap<>();
        this.adjacencyMatrix = new int[0][0];
    }

    public void addNode(T value) {
        if (nodeWithValueExists(value)) {
            throw new IllegalArgumentException("Node with value '" + value + "' already exists.");
        }

        int nodeId = nodes.size(); //generateNodeId();
        Node<T> newNode = new Node<>(value, nodeId);
        nodes.put(nodeId, newNode);
        updateAdjacencyMatrix();
    }

    private boolean nodeWithValueExists(T value) {
        return nodes.values().stream().anyMatch(node -> comparator.compare(node.getValue(), value) == 0);
    }

    public void addEdge(int nodeId1, int nodeId2, Integer edgeWeight) {
        int weight = (edgeWeight != null) ? edgeWeight : 1;
        Node<T> node1 = nodes.get(nodeId1);
        Node<T> node2 = nodes.get(nodeId2);

        if (node1 != null && node2 != null) {
            int index1 = getNodeIndex(nodeId1);
            int index2 = getNodeIndex(nodeId2);

            if (index1 != -1 && index2 != -1) {
                adjacencyMatrix[index1][index2] = weight;
                adjacencyMatrix[index2][index1] = weight;
            }
        }
    }

    // Métodos auxiliares para manipulação da matriz de adjacência

    private int getNodeIndex(int nodeId) {
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (nodes.get(nodeId) == nodes.get(i)) {
                return i;
            }
        }
        return -1;
    }

    private void updateAdjacencyMatrix() {
        int size = nodes.size();
        int[][] newMatrix = new int[size][size];

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            System.arraycopy(adjacencyMatrix[i], 0, newMatrix[i], 0, adjacencyMatrix[i].length);
        }

        adjacencyMatrix = newMatrix;
    }

    //* Ciclos */
    public boolean hasCycle() {
        Set<Integer> visited = new HashSet<>();
        Set<Integer> recursionStack = new HashSet<>();

        for (Integer nodeId : nodes.keySet()) {
            if (!visited.contains(nodeId)) {
                if (hasCycleUtil(nodeId, visited, recursionStack)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean hasCycleUtil(Integer nodeId, Set<Integer> visited, Set<Integer> recursionStack) {
        visited.add(nodeId);
        recursionStack.add(nodeId);

        for (Integer neighborId : getNeighbors(nodeId)) {
            if (!visited.contains(neighborId)) {
                if (hasCycleUtil(neighborId, visited, recursionStack)) {
                    return true;
                }
            } else if (recursionStack.contains(neighborId)) {
                return true; // Ciclo detectado
            }
        }

        recursionStack.remove(nodeId);
        return false;
    }

    private Set<Integer> getNeighbors(Integer nodeId) {
        Set<Integer> neighbors = new HashSet<>();
        int index = getNodeIndex(nodeId);

        if (index != -1) {
            for (int i = 0; i < adjacencyMatrix[index].length; i++) {
                if (adjacencyMatrix[index][i] != 0) {
                    neighbors.add(i);
                }
            }
        }

        return neighbors;
    }
    
    //Find paths
     public List<List<Node<T>>> findAllPaths() {
        List<List<Node<T>>> paths = new ArrayList<>();
        List<Node<T>> currentPath = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();

        for (Integer startNodeId : nodes.keySet()) {
            findAllPathsUtil(startNodeId, visited, currentPath, paths);
        }

        return paths;
    }

    private void findAllPathsUtil(int currentNodeId, Set<Integer> visited,
                                  List<Node<T>> currentPath, List<List<Node<T>>> paths) {
        visited.add(currentNodeId);
        currentPath.add(nodes.get(currentNodeId));

        // Adiciona o caminho atual à lista de caminhos se alcançamos um nó de destino
        if (currentPath.size() > 1) {
            paths.add(new ArrayList<>(currentPath));
        }

        for (Integer neighborId : getNeighbors(currentNodeId)) {
            if (!visited.contains(neighborId)) {
                findAllPathsUtil(neighborId, visited, currentPath, paths);
            }
        }

        // Remove o nó atual do caminho e marca como não visitado para explorar outras possibilidades
        visited.remove(currentNodeId);
        currentPath.remove(currentPath.size() - 1);
    }

    //Getters and Setters

    public Map<Integer, Node<T>> getNodes() {
        return nodes;
    }

}

