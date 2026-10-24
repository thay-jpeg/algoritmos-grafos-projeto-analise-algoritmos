public class Main {
    public static void main(String[] args) {
        // Nome do arquivo que você acabou de criar
        String caminhoArquivo = "grafo_teste.txt";

        System.out.println("Iniciando a leitura do arquivo...");


        Grafo meuGrafo = LerArq.carregarGrafo(caminhoArquivo);

        // Verifica se a leitura deu certo
        if (meuGrafo != null) {

            // Imprime a estrutura para validar se as arestas entraram certo
            meuGrafo.imprimirGrafo();

            //Instancia a classe de buscas passando o grafo
            BuscaGrafo buscas = new BuscaGrafo(meuGrafo);

            // BSF
            buscas.buscaEmLargura(0);

            // DFS
            buscas.buscaEmProfundidade(0);

        } else {
            System.out.println("Falha ao carregar o grafo. Verifique o arquivo.");
        }
        //teste Dijkstra
        Dijkstra algoritmoDijkstra = new Dijkstra(meuGrafo);
        algoritmoDijkstra.executar(0);

        //teste Kruskal
        Kruskal algoritmoKruskal = new Kruskal(meuGrafo);
        algoritmoKruskal.executar();
    }
}