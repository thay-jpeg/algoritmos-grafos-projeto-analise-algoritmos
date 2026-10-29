package algoritmos;

import modelo.Grafo;
import modelo.Aresta;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Classe das Componentes Fortemente Conexas
 * num grafo orientado, utilizando o algoritmo de Kosaraju
 */
public class
ComponenteConexa {
    private Grafo grafo;

    /**
     * Construtor da classe, que recebe o grafo orientado onde as componentes serão procuradas
     */
    public ComponenteConexa(Grafo grafo) {
        this.grafo = grafo;
    }

    /**
     * Executa o algoritmo de Kosaraju ordenadas por tamanho decrescente
     */
    public int[] executar() {
        int numVertices = grafo.getNumVertices();

        Stack<Integer> pilha = new Stack<>();
        boolean[] visitados = new boolean[numVertices];

        //preenche a pilha com os vertices por ordem de termino, DFS original
        for (int i = 0; i < numVertices; i++) {
            if (!visitados[i]) {
                preencherOrdem(i, visitados, pilha);
            }
        }

        //obtem o grafo transposto
        Grafo grafoTransposto = grafo.obterGrafoTransposto();

        //faz a DFS no grafo transposto
        visitados = new boolean[numVertices]; //reinicia o vetor de estado
        List<List<Integer>> componentes = new ArrayList<>();

        while (!pilha.isEmpty()) {
            int v = pilha.pop();
            if (!visitados[v]) {
                List<Integer> componenteAtual = new ArrayList<>();
                dfsTransposto(grafoTransposto, v, visitados, componenteAtual);
                
                //ordena os vertices numericamente dentro da componente
                Collections.sort(componenteAtual);
                componentes.add(componenteAtual);
            }
        }

        //ordena a lista de componentes por tamanho decrescente
        componentes.sort((c1, c2) -> Integer.compare(c2.size(), c1.size()));
        int[] idComponente = new int[numVertices];

        System.out.println("\nComponentes Fortemente Conexas: \n");
        for (int i = 0; i < componentes.size(); i++) {
            System.out.print("Componente " + (i + 1) + ": ");
            List<Integer> comp = componentes.get(i);
            for (int j = 0; j < comp.size(); j++) {
                idComponente[comp.get(j)] = i;
                System.out.print(comp.get(j));
                if (j < comp.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
        return idComponente;
    }

    /**
     * Primeira DFS no grafo original que empilha os vértices ao terminar a sua exploração
     * 
     * @param u O vértice atual.
     * @param visitados O vetor de estado de visitação.
     * @param pilha A pilha que guarda a ordem de término.
     */
    private void preencherOrdem(int u, boolean[] visitados, Stack<Integer> pilha) {
        visitados[u] = true;
        for (Aresta aresta : grafo.getAdjacencias(u)) {
            int v = aresta.getDestino();
            if (!visitados[v]) {
                preencherOrdem(v, visitados, pilha);
            }
        }
        pilha.push(u);
    }

    /**
     * Segunda DFS no grafo transposto que agrupa os vertices na mesma componente
     * 
     * @param grafoT O grafo transposto.
     * @param u O vértice atual.
     * @param visitados O vetor de estado de visitação.
     * @param componente A lista que guarda os vértices da componente atual.
     */
    private void dfsTransposto(Grafo grafoT, int u, boolean[] visitados, List<Integer> componente) {
        visitados[u] = true;
        componente.add(u);
        for (Aresta aresta : grafoT.getAdjacencias(u)) {
            int v = aresta.getDestino();
            if (!visitados[v]) {
                dfsTransposto(grafoT, v, visitados, componente);
            }
        }
    }
}