package modelo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Grafo {
    private int numVertices;
    private boolean orientado;

    // Lista de Adjacência: uma lista que guarda uma lista de arestas para cada vértice
    private List<List<Aresta>> adjacencias;

    // Construtor
    public Grafo(int numVertices, boolean orientado) {
        this.numVertices = numVertices;
        this.orientado = orientado;
        this.adjacencias = new ArrayList<>();

        // Inicializa uma lista vazia para cada vértice do grafo (de 0 até numVertices - 1)
        for (int i = 0; i < numVertices; i++) {
            adjacencias.add(new ArrayList<>());
        }
    }

    // Metodo para popular o grafo
    public void adicionarAresta(int u, int v, int peso) {
        // Cria e adiciona a aresta na lista do vértice de origem (u -> v)
        adjacencias.get(u).add(new Aresta(v, peso));
        // Ordena a lista para manter a regra crescente
        Collections.sort(adjacencias.get(u));

        // Se o grafo não for orientado, precisa adicionar a ida e a volta (v -> u)
        if (!orientado) {
            adjacencias.get(v).add(new Aresta(u, peso));
            Collections.sort(adjacencias.get(v));
        }

    }

    // Getter para Busca
    public int getNumVertices() {
        return numVertices;
    }

    public List<Aresta> getAdjacencias(int v) {
        return adjacencias.get(v);
    }

    public boolean isOrientado() {
        return orientado;
    }

    // Metodo extra para ajudar a testar se a leitura deu certo
    public void imprimirGrafo() {
        System.out.println("\n--- Estrutura do Grafo na Memória ---");
        for (int i = 0; i < numVertices; i++) {
            System.out.print("Vértice " + i + ":");
            for (Aresta aresta : adjacencias.get(i)) {
                System.out.print(" -> (Destino: " + aresta.getDestino() + ", Peso: " + aresta.getPeso() + ")");
            }
            System.out.println();
        }
    }

    /**
     * Gera e devolve o grafo transposto.
     * 
     * @return Um novo objeto Grafo representando o transposto do grafo atual.
     */
    public Grafo obterGrafoTransposto() {
        Grafo grafoT = new Grafo(this.numVertices, this.orientado);
        for (int u = 0; u < this.numVertices; u++) {
            for (Aresta aresta : this.adjacencias.get(u)) {

                //inverte a direção, de u -> v pra v -> u
                grafoT.adicionarAresta(aresta.getDestino(), u, aresta.getPeso());
            }
        }
        return grafoT;
    }
}