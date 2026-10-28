package algoritmos;



import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import modelo.Grafo;
import modelo.Aresta;

/**
 * Classe responsável por executar algoritmos de busca de Largura e Profundidade em um grafo.
 */
public class BuscaGrafo {
    private Grafo grafo;
    private boolean control;

    /**
     * Construtor da classe de busca, o qual recebe um grafo como parâmetro para realizar as buscas.
     */
    public BuscaGrafo(Grafo grafo) {
        this.grafo = grafo;
    }

    /**
     * Executa a Busca em Largura (BFS) garantindo que todos os vértices sejam visitados,
     * mesmo em grafos desconexos.
     * @param verticeInicial O vértice por onde a busca principal deve começar.
     */
    public void buscaEmLargura(int verticeInicial) {
        int numVertices = grafo.getNumVertices();
        boolean[] visitados = new boolean[numVertices];
        control = false;

        System.out.println("\nBusca em Largura (BFS): \n");
        
        //a busca a partir do vértice inicial escolhido
        bfsFila(verticeInicial, visitados);

        //exploração de componentes desconexas
        for (int i = 0; i < numVertices; i++) {
            if (!visitados[i]) {
                bfsFila(i, visitados);
            }
        }
        System.out.println();
    }

    /**
     * Método auxiliar privado que processa a Fila da BFS.
     * 
     * @param inicio O vértice de partida para este ciclo da busca.
     * @param visitados O vetor global de controle de vértices já visitados.
     */
    private void bfsFila(int inicio, boolean[] visitados) {
        Queue<Integer> fila = new LinkedList<>();
        visitados[inicio] = true;
        fila.add(inicio);

        while (!fila.isEmpty()) {
            int u = fila.poll();
            
            // controle de impressão
            if (control) System.out.print(" - ");
            System.out.print(u);
            control = true;

            List<Aresta> vizinhos = grafo.getAdjacencias(u);
            for (Aresta aresta : vizinhos) {
                int v = aresta.getDestino();
                if (!visitados[v]) {
                    visitados[v] = true;
                    fila.add(v);
                }
            }
        }
    }

    /**
     * Executa a Busca em Profundidade (DFS) garantindo que todos os vértices sejam visitados,
     * mesmo em grafos desconexos.
     *
     */
    public void buscaEmProfundidade() {
        int numVertices = grafo.getNumVertices();
        boolean[] visitados = new boolean[numVertices];
        control = false;

        System.out.println("\nBusca em Profundidade (DFS): \n");
        
        //busca a partir do vértice inicial
        //dfsRecursivo(verticeInicial, visitados);

        // exploração de componentes desconexas
        for (int i = 0; i < numVertices; i++) {
            if (!visitados[i]) {
                dfsRecursivo(i, visitados);
            }
        }
        System.out.println();
    }

    /**
     * Método auxiliar privado que realiza a recursão da DFS.
     * 
     * @param u O vértice atualmente sendo explorado.
     * @param visitados O vetor global de controle de vértices já visitados.
     */
    private void dfsRecursivo(int u, boolean[] visitados) {
        visitados[u] = true;
        
        //controle de impressão
        if (control) System.out.print(" - ");
        System.out.print(u);
        control = true;

        List<Aresta> vizinhos = grafo.getAdjacencias(u);
        for (Aresta aresta : vizinhos) {
            int v = aresta.getDestino();
            if (!visitados[v]) {
                dfsRecursivo(v, visitados);
            }
        }
    }
}