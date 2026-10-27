package modelo;
// A interface Comparable é usada para manter a lista ordenada automaticamente
public class Aresta implements Comparable<Aresta> {
    private int destino;
    private int peso;

    // Construtor
    public Aresta(int destino, int peso) {
        this.destino = destino;
        this.peso = peso;
    }

    // Getters para acessar os valores depois
    public int getDestino() {
        return destino;
    }

    public int getPeso() {
        return peso;
    }

    // Regra de comparação  para ordenar pelo vértice de destino  (crescente)
    @Override
    public int compareTo(Aresta outraAresta) {
        return Integer.compare(this.destino, outraAresta.destino);
    }
}