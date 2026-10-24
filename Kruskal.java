import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kruskal {
    private Grafo grafo;

    public Kruskal(Grafo grafo) {
        this.grafo = grafo;
    }

    // Classe auxiliar que permiti a ordenação pelo MENOR PESO
    private class ArestaKruskal implements Comparable<ArestaKruskal> {
        int u;
        int v;
        int peso;

        public ArestaKruskal(int u, int v, int peso) {
            this.u = u;
            this.v = v;
            this.peso = peso;
        }

        @Override
        public int compareTo(ArestaKruskal outra) {
            return Integer.compare(this.peso, outra.peso);
        }
    }

    // Conjuntos Disjuntos

    // Encontra a raiz do conjunto de um vértice
    private int encontrar(int[] pai, int i) {
        if (pai[i] == i) {
            return i;
        }
        return pai[i] = encontrar(pai, pai[i]); // Compressão de caminho
    }

    // Une dois subconjuntos em um único conjunto
    private void unir(int[] pai, int x, int y) {
        int raizX = encontrar(pai, x);
        int raizY = encontrar(pai, y);
        if (raizX != raizY) {
            pai[raizX] = raizY;
        }
    }

    public void executar() {
        if (grafo.isOrientado()) {
            System.out.println("\n Kruskal para Grafos NÃO-orientados.");
        }

        int numVertices = grafo.getNumVertices();
        List<ArestaKruskal> todasArestas = new ArrayList<>();

        // Coleta todas as arestas do grafo
        for (int u = 0; u < numVertices; u++) {
            for (Aresta aresta : grafo.getAdjacencias(u)) {
                int v = aresta.getDestino();
                int peso = aresta.getPeso();

                // Se o grafo for não-orientado, a aresta existe nos dois sentidos (u->v e v->u).
                // Pegamos apenas quando u < v para não duplicar a mesma aresta na lista.
                if (grafo.isOrientado() || u < v) {
                    todasArestas.add(new ArestaKruskal(u, v, peso));
                }
            }
        }

        // Ordena todas as arestas em ordem crescente de peso
        Collections.sort(todasArestas);

        // Inicializa a união
        int[] pai = new int[numVertices];
        for (int i = 0; i < numVertices; i++) {
            pai[i] = i;
        }

        List<ArestaKruskal> arvoreGeradoraMinima = new ArrayList<>();
        int custoTotal = 0;

        // Percorre as arestas ordenadas e adiciona à árvore se não formar ciclo
        for (ArestaKruskal aresta : todasArestas) {
            int raizU = encontrar(pai, aresta.u);
            int raizV = encontrar(pai, aresta.v);

            // Se as raízes são diferentes, os vértices estão em componentes separados (não forma ciclo)
            if (raizU != raizV) {
                arvoreGeradoraMinima.add(aresta);
                custoTotal += aresta.peso;
                unir(pai, raizU, raizV);

                // Uma árvore geradora com V vértices tem exatamente V - 1 arestas
                if (arvoreGeradoraMinima.size() == numVertices - 1) {
                    break;
                }
            }
        }

        imprimirResultado(arvoreGeradoraMinima, custoTotal);
    }

    private void imprimirResultado(List<ArestaKruskal> agm, int custoTotal) {
        System.out.println("\n--- Árvore Geradora Mínima (Kruskal) ---");
        System.out.println("Custo Total: " + custoTotal);
        System.out.print("Arestas selecionadas: ");
        for (ArestaKruskal a : agm) {
            System.out.print("(" + a.u + "," + a.v + "):" + a.peso + " ");
        }
        System.out.println();
    }
}