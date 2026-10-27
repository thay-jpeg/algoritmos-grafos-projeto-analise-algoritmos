package algoritmos;

import java.util.List;
import java.util.Stack;
import modelo.Aresta;
import modelo.Grafo;

/**
 * Classe da execucao do algoritmo de Ordenação Topológica.
 * Aplica-se exclusivamente a grafos orientados e acíclicos (DAGs).
 */
public class OrdenacaoTopologica {
    private Grafo grafo;

    /**
     * Construtor da classe, recebe o grafo sobre o qual o algoritmo será executado.
     */
    public OrdenacaoTopologica(Grafo grafo) {
        this.grafo = grafo;
    }

    /**
     * Executa a ordenação topológica.
     * Utiliza uma DFS modificada para registrar a ordem de término.
     */
    public void executar() {
        int numVertices = grafo.getNumVertices();
        boolean[] visitados = new boolean[numVertices];
        
        //pilha armazena os vértices na ordem em que terminam de ser explorados
        Stack<Integer> pilhaOrdem = new Stack<>();

        // caso o grafo possua múltiplas componentes isoladas
        for (int i = 0; i < numVertices; i++) {
            if (!visitados[i]) {
                dfsTopologico(i, visitados, pilhaOrdem);
            }
        }

        System.out.println("\nOrdenacao Topologica: \n");
        boolean primeiro = true;
        while (!pilhaOrdem.isEmpty()) {
            if (!primeiro) {
                System.out.print(" - ");
            }
            System.out.print(pilhaOrdem.pop());
            primeiro = false;
        }
        System.out.println();
    }

    /**
     * Método auxiliar recursivo que realiza a DFS e empilha os vértices ao terminar de explorá-los.
     * 
     * @param u O vértice atual sendo explorado.
     * @param visitados O vetor de controle de vértices já visitados.
     * @param pilha A pilha onde os vértices são armazenados no momento de "saída" da recursão.
     */
    private void dfsTopologico(int u, boolean[] visitados, Stack<Integer> pilha) {
        visitados[u] = true;

        //explora todos os vizinhos primeiro
        List<Aresta> vizinhos = grafo.getAdjacencias(u);
        for (Aresta aresta : vizinhos) {
            int v = aresta.getDestino();
            if (!visitados[v]) {
                dfsTopologico(v, visitados, pilha);
            }
        }
        
        pilha.push(u);
    }
}