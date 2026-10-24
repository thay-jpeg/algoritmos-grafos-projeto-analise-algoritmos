import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BuscaGrafo {
    private Grafo grafo;

    public BuscaGrafo(Grafo grafo) {
        this.grafo = grafo;
    }

    // BFS
    public void buscaEmLargura(int verticeInicial) {
        int numVertices = grafo.getNumVertices();
        boolean[] visitados = new boolean[numVertices];
        Queue<Integer> fila = new LinkedList<>();

        visitados[verticeInicial] = true;
        fila.add(verticeInicial);

        System.out.println("\n--- Iniciando Busca em Largura (BFS) a partir do vértice " + verticeInicial + " ---");

        while (!fila.isEmpty()) {
            int u = fila.poll();
            System.out.print(u + " "); // Processa o vértice

            // Explora todos os vizinhos do vértice atual
            List<Aresta> vizinhos = grafo.getAdjacencias(u);
            for (Aresta aresta : vizinhos) {
                int v = aresta.getDestino();
                if (!visitados[v]) {
                    visitados[v] = true;
                    fila.add(v);
                }
            }
        }
        System.out.println();
    }

    // DFS
    public void buscaEmProfundidade(int verticeInicial) {
        int numVertices = grafo.getNumVertices();
        boolean[] visitados = new boolean[numVertices];

        System.out.println("\n--- Iniciando Busca em Profundidade (DFS) a partir do vértice " + verticeInicial + " ---");
        // Chama o método auxiliar recursivo
        dfsRecursivo(verticeInicial, visitados);
        System.out.println();
    }

    // Método auxiliar para a recursão da DFS
    private void dfsRecursivo(int u, boolean[] visitados) {
        visitados[u] = true;
        System.out.print(u + " "); // Processa o vértice

        List<Aresta> vizinhos = grafo.getAdjacencias(u);
        for (Aresta aresta : vizinhos) {
            int v = aresta.getDestino();
            if (!visitados[v]) {
                dfsRecursivo(v, visitados);
            }
        }
    }
}