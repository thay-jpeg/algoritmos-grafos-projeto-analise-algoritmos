package algoritmos;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;
import modelo.Aresta;
import modelo.Grafo;

/**
 * Classe responsável por executar o algoritmo de Dijkstra, que encontra os
 * caminhos de custo mínimo de um vértice de origem para todos os outros
 * vértices em um grafo com pesos não negativos.
 */
public class Dijkstra {
    private Grafo grafo;

    /**
     * Construtor da classe Dijkstra, que recebe um grafo como parâmetro para realizar o algoritmo.
     */
    public Dijkstra(Grafo grafo) {
        this.grafo = grafo;
    }

    /**
     * Classe interna auxiliar que representa um nó na Fila de Prioridade.
     * Ela guarda a distância acumulada até o vértice para que a fila
     * consiga sempre entregar o vértice com o menor custo atual.
     */
    private class NoDijkstra implements Comparable<NoDijkstra> {
        int vertice;
        int distancia;

        public NoDijkstra(int vertice, int distancia) {
            this.vertice = vertice;
            this.distancia = distancia;
        }

        /**
         * Compara dois nós com base na distância.
         * Garante que o nó com a menor distância fique no topo da Fila de Prioridade.
         */
        @Override
        public int compareTo(NoDijkstra outro) {
            return Integer.compare(this.distancia, outro.distancia);
        }
    }

    /**
     * Executa o algoritmo de Dijkstra a partir de um vértice de origem.
     * 
     * @param verticeOrigem O vértice inicial a partir do qual as distâncias serão calculadas.
     */
    public void executar(int verticeOrigem) {
        int numVertices = grafo.getNumVertices();
        //verifica todos os pesos antes de iniciar o algoritmo
        for (int i = 0; i < numVertices; i++) {
            for (Aresta aresta : grafo.getAdjacencias(i)) {
                if (aresta.getPeso() < 0) {
                    System.out.println("Erro: pesos negativos. O algoritmo de Dijkstra não pode ser executado com pesos negativos.");
                return;
                }
            }
        }
        int[] distancias = new int[numVertices];
        int[] predecessores = new int[numVertices];
        boolean[] visitados = new boolean[numVertices];

        // inicializa distâncias com "Infinito" e predecessores como -1
        Arrays.fill(distancias, Integer.MAX_VALUE);
        Arrays.fill(predecessores, -1);

        distancias[verticeOrigem] = 0;

        PriorityQueue<NoDijkstra> filaPrioridade = new PriorityQueue<>();
        filaPrioridade.add(new NoDijkstra(verticeOrigem, 0));

        System.out.println("\nDijkstra (Origem: " + verticeOrigem + "): \n");

        while (!filaPrioridade.isEmpty()) {
            NoDijkstra atual = filaPrioridade.poll();
            int u = atual.vertice;

            // ignora se o vértice já foi fechado com o custo mínimo definitivo
            if (visitados[u]) continue;
            visitados[u] = true;

            // percorre os vizinhos para fazer o relaxamento das arestas, inspecionando os vizinhos do vértice atual
            List<Aresta> vizinhos = grafo.getAdjacencias(u);
            for (Aresta aresta : vizinhos) {
                int v = aresta.getDestino();
                int peso = aresta.getPeso();

                // verifica se encontrou um caminho mais curto, se sim, atualiza a distância e o predecessor, adicionando o vizinho na fila de prioridade
                if (!visitados[v] && distancias[u] != Integer.MAX_VALUE && (distancias[u] + peso < distancias[v])) {
                    distancias[v] = distancias[u] + peso;
                    predecessores[v] = u;
                    filaPrioridade.add(new NoDijkstra(v, distancias[v]));
                }
            }
        }

        imprimirResultado(verticeOrigem, distancias, predecessores);


    }

/**
     * Imprime o resultado final, mostrando o custo mínimo e o caminho percorrido.
     * 
     * @param origem O vértice de origem.
     * @param distancias Vetor com as distâncias mínimas calculadas.
     * @param predecessores Vetor com o rastreio do caminho.
     */
    private void imprimirResultado(int origem, int[] distancias, int[] predecessores) {
        for (int i = 0; i < grafo.getNumVertices(); i++) {
            System.out.print("Para o vertice " + i + " | Custo Minimo: ");
            if (distancias[i] == Integer.MAX_VALUE) {
                System.out.println("Inalcancavel");
            } else {
                System.out.print(distancias[i] + " | Caminho: ");
                imprimirCaminho(i, predecessores);
                System.out.println();
            }
        }
    }

/**
     * Reconstrói e imprime o caminho exato da origem até o destino usando uma Pilha.
     * 
     * @param destino O vértice final do caminho.
     * @param predecessores O vetor de rastreio de predecessores.
     */
    private void imprimirCaminho(int destino, int[] predecessores) {
        Stack<Integer> caminho = new Stack<>();
        int atual = destino;

        while (atual != -1) {
            caminho.push(atual);
            atual = predecessores[atual];
        }

        while (!caminho.isEmpty()) {
            System.out.print(caminho.pop());
            if (!caminho.isEmpty()) {
                System.out.print(" -> ");
            }
        }
    }
}