package algoritmos;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import modelo.Aresta;
import modelo.Grafo;

/**
 * Classe responsável por executar o algoritmo de Kruskal, que encontra a
 * Árvore Geradora Mínima (AGM) de um grafo não-orientado e conexo.
 * A AGM é um subconjunto de arestas que conecta todos os vértices com o
 * menor peso total possível, sem formar ciclos.
 */
public class Kruskal {
    private Grafo grafo;

    public Kruskal(Grafo grafo) {
        this.grafo = grafo;
    }

    /**
     * Construtor da classe Kruskal, que recebe um grafo como parâmetro para realizar o algoritmo.
     */
    private class ArestaKruskal implements Comparable<ArestaKruskal> {
        int u;
        int v;
        int peso;

    /**
     * Classe interna auxiliar para representar uma aresta de forma independente,
     * permitindo a ordenação global de todas as arestas do grafo pelo menor peso.
     */
        public ArestaKruskal(int u, int v, int peso) {
            this.u = u;
            this.v = v;
            this.peso = peso;
        }

        /**
         * Compara as arestas pelo peso de forma crescente.
         */
        @Override
        public int compareTo(ArestaKruskal outra) {
            return Integer.compare(this.peso, outra.peso);
        }
    }

    // Conjuntos Disjuntos

    /**
     * Encontra a raiz do conjunto ao qual o vértice i pertence.
     * Utiliza a técnica de compressão de caminho para acelerar buscas futuras.
     * 
     * @param pai Vetor que representa a estrutura de conjuntos.
     * @param i O vértice a ser buscado.
     * @return A raiz do conjunto de i.
     */
    private int encontrar(int[] pai, int i) {
        if (pai[i] == i) {
            return i; //eh a propria raiz
        }
        return pai[i] = encontrar(pai, pai[i]); // compressão de caminho, apontando diretamente para a raiz
    }

    /**
     * Une dois subconjuntos distintos em um único conjunto.
     * 
     * @param pai Vetor que representa a estrutura de conjuntos.
     * @param x Vértice de um dos conjuntos.
     * @param y Vértice do outro conjunto.
     */
    private void unir(int[] pai, int x, int y) {
        int raizX = encontrar(pai, x);
        int raizY = encontrar(pai, y);

        //se as raízes forem diferentes, une uma na outra
        if (raizX != raizY) {
            pai[raizX] = raizY;
        }
    }

    /**
     * Executa o algoritmo de Kruskal e identifica as arestas que compõem a AGM.
     */
    public boolean[][] executar() {
        if (grafo.isOrientado()) {
            System.out.println("\nAviso: Kruskal eh um algoritmo para Grafos NAO-orientados. Pode apresentar um resultado nao esperado caso o grafo nao for o especificado.");
        }

        int numVertices = grafo.getNumVertices();
        List<ArestaKruskal> todasArestas = new ArrayList<>();

        // coleta todas as arestas do grafo
        for (int u = 0; u < numVertices; u++) {
            for (Aresta aresta : grafo.getAdjacencias(u)) {
                int v = aresta.getDestino();
                int peso = aresta.getPeso();

                // se o grafo for não-orientado, a aresta existe nos dois sentidos (u->v e v->u)
                // pega apenas quando u < v para não duplicar a mesma aresta na lista
                if (grafo.isOrientado() || u < v) {
                    todasArestas.add(new ArestaKruskal(u, v, peso));
                }
            }
        }

        //ordena todas as arestas em ordem crescente de peso
        Collections.sort(todasArestas);

        //inicializa a união
        int[] pai = new int[numVertices];
        for (int i = 0; i < numVertices; i++) {
            pai[i] = i;
        }

        List<ArestaKruskal> arvoreGeradoraMinima = new ArrayList<>();
        boolean[][] arestasNaAGM = new boolean[numVertices][numVertices];//Desenho AGM
        int custoTotal = 0;

        //percorre as arestas ordenadas e adiciona à árvore se não formar ciclo
        for (ArestaKruskal aresta : todasArestas) {
            int raizU = encontrar(pai, aresta.u);
            int raizV = encontrar(pai, aresta.v);

            //se as raízes são diferentes, os vértices estão em componentes separados
            if (raizU != raizV) {
                arvoreGeradoraMinima.add(aresta);
                arestasNaAGM[aresta.u][aresta.v] = true;
                arestasNaAGM[aresta.v][aresta.u] = true;
                custoTotal += aresta.peso;
                unir(pai, raizU, raizV);

                //AGM com V vértices tem exatamente V - 1 arestas
                if (arvoreGeradoraMinima.size() == numVertices - 1) {
                    break;
                }
            }
        }

        imprimirResultado(arvoreGeradoraMinima, custoTotal);
        return arestasNaAGM;
    }

    /**
     * Imprime as arestas da AGM.
     * 
     * @param agm Lista de arestas que compõem a AGM.
     * @param custoTotal A soma dos pesos de todas as arestas da árvore.
     */
    private void imprimirResultado(List<ArestaKruskal> agm, int custoTotal) {
        System.out.println("\nArvore Geradora Minima Kruskal: ");
        System.out.println("\npeso total: " + custoTotal);
        System.out.print("arestas: ");
        for (ArestaKruskal a : agm) {
            System.out.print("(" + a.u + "," + a.v + ") ");
        }
        System.out.println();
    }
}