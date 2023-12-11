
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import lib.graph.Graph;
import lib.graph.Node;

import static org.junit.Assert.*;

import java.util.Comparator;
import java.util.List;

@RunWith(JUnit4.class)
public class GraphTest {
    @Test
    public void testGraphCreation() {
        Graph<Integer> graph = new Graph<Integer>(Comparator.naturalOrder());

        // Adicione nós ao grafo
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);

        // Verifique se os nós foram adicionados corretamente
        assertEquals(3, graph.getNodes().size());
    }

    @Test
    public void testFindAllPaths() {
        Graph<Integer> graph = new Graph<Integer>(Comparator.naturalOrder());

        // Adicione nós e arestas para criar um grafo
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 3, 1);
        graph.addEdge(1, 3, 1);

        // Encontre todos os caminhos possíveis
        List<List<Node<Integer>>> paths = graph.findAllPaths();

        // Verifique se a quantidade de caminhos é como esperado
        assertEquals(2, paths.size());
    }

    @Test
    public void testCycleDetection() {
        Graph<Integer> graph = new Graph<Integer>(Comparator.naturalOrder());

        // Adicione nós e arestas para criar um ciclo
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 3, 1);
        graph.addEdge(3, 1, 1);

        // Verifique se o ciclo é detectado 
        assertTrue(graph.hasCycle());
    }

}
