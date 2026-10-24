import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

public class Dijkstra {
    private Grafo grafo;

    public Dijkstra(Grafo grafo) {
        this.grafo = grafo;
    }

    // Classe auxiliar interna para usar na Fila de Prioridade do Dijkstra
    private class NoDijkstra implements Comparable<NoDijkstra> {
        int vertice;
        int distancia;

        public NoDijkstra(int vertice, int distancia) {
            this.vertice = vertice;
            this.distancia = distancia;
        }

        // Ordena sempre pela menor distância acumulada
        @Override
        public int compareTo(NoDijkstra outro) {
            return Integer.compare(this.distancia, outro.distancia);
        }
    }

    public void executar(int verticeOrigem) {
        int numVertices = grafo.getNumVertices();
        int[] distancias = new int[numVertices];
        int[] predecessores = new int[numVertices];
        boolean[] visitados = new boolean[numVertices];

        // Inicializa distâncias com "Infinito" e  com -1
        Arrays.fill(distancias, Integer.MAX_VALUE);
        Arrays.fill(predecessores, -1);

        distancias[verticeOrigem] = 0;

        PriorityQueue<NoDijkstra> filaPrioridade = new PriorityQueue<>();
        filaPrioridade.add(new NoDijkstra(verticeOrigem, 0));

        System.out.println("\n--- Executando Dijkstra (Origem: " + verticeOrigem + ") ---");

        while (!filaPrioridade.isEmpty()) {
            NoDijkstra atual = filaPrioridade.poll();
            int u = atual.vertice;

            // Ignora se o vértice já foi fechado com o custo mínimo definitivo
            if (visitados[u]) continue;
            visitados[u] = true;

            // Percorre os vizinhos para fazer o relaxamento das arestas
            List<Aresta> vizinhos = grafo.getAdjacencias(u);
            for (Aresta aresta : vizinhos) {
                int v = aresta.getDestino();
                int peso = aresta.getPeso();

                if (peso < 0) {
                    System.out.println("Aviso: Aresta com peso negativo detectada (" + u + " -> " + v + "). Dijkstra pode falhar.");
                }

                // Verifica se encontrou um caminho mais curto
                if (!visitados[v] && distancias[u] != Integer.MAX_VALUE && (distancias[u] + peso < distancias[v])) {
                    distancias[v] = distancias[u] + peso;
                    predecessores[v] = u;
                    filaPrioridade.add(new NoDijkstra(v, distancias[v]));
                }
            }
        }

        imprimirResultado(verticeOrigem, distancias, predecessores);
    }

    // Exibe as distâncias finais e o caminho exato
    private void imprimirResultado(int origem, int[] distancias, int[] predecessores) {
        for (int i = 0; i < grafo.getNumVertices(); i++) {
            System.out.print("Para o vértice " + i + " | Custo Mínimo: ");
            if (distancias[i] == Integer.MAX_VALUE) {
                System.out.println("Inalcançável");
            } else {
                System.out.print(distancias[i] + " | Caminho: ");
                imprimirCaminho(i, predecessores);
                System.out.println();
            }
        }
    }

    // Reconstrói o caminho de trás para frente usando uma Pilha
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