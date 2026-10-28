package util;

import modelo.Aresta;
import modelo.Grafo;
import java.io.FileWriter;
import java.io.IOException;

public class DesenhoGrafo {

    // Grafo Padrão
    public static void gerarImagemBase(Grafo grafo, String nomeImagemPng) {
        gerarDot(grafo, null, null, nomeImagemPng);
    }

    //Grafo com Caminho Destacado em Vermelho
    public static void gerarImagemCaminho(Grafo grafo, int[] predecessores, String nomeImagemPng) {
        gerarDot(grafo, predecessores, null, nomeImagemPng);
    }

    //Grafo com Vértices Coloridos
    public static void gerarImagemComponentes(Grafo grafo, int[] idComponente, String nomeImagemPng) {
        gerarDot(grafo, null, idComponente, nomeImagemPng);
    }

    // Método privado central que monta o arquivo .dot com base nos parâmetros recebidos
    private static void gerarDot(Grafo grafo, int[] predecessores, int[] idComponente, String nomeImagemPng) {

        // Adiciona "assets/" antes do nome gerado
        String caminhoDot = "assets/temp_" + System.currentTimeMillis() + ".dot";
        String caminhoPngFinal = "assets/" + nomeImagemPng;

        String[] paleta = {"lightblue", "lightgreen", "lightpink", "yellow", "orange", "plum", "cyan", "gold"};

        try (FileWriter writer = new FileWriter(caminhoDot)) {
            if (grafo.isOrientado()) {
                writer.write("digraph Grafo {\n");
            } else {
                writer.write("graph Grafo {\n");
            }

            int numVertices = grafo.getNumVertices();

            // Desenhando os Vértices
            for (int u = 0; u < numVertices; u++) {
                String corVertice = "lightblue"; // Cor padrão

                // Se recebemos um vetor de componentes, pinta o vértice conforme a sua componente
                if (idComponente != null) {
                    corVertice = paleta[idComponente[u] % paleta.length];
                }

                writer.write("  " + u + " [shape=circle, style=filled, fillcolor=\"" + corVertice + "\"];\n");

                // Desenhando as Arestas
                for (Aresta aresta : grafo.getAdjacencias(u)) {
                    int v = aresta.getDestino();
                    int peso = aresta.getPeso();

                    if (!grafo.isOrientado() && u > v) continue; // Evita duplicação

                    // Verifica se a aresta faz parte do caminho mínimo (Dijkstra)
                    boolean noCaminho = false;
                    if (predecessores != null) {
                        if (predecessores[v] == u || (!grafo.isOrientado() && predecessores[u] == v)) {
                            noCaminho = true;
                        }
                    }

                    // Se fizer parte do caminho, fica vermelha e grossa, senão fica preta normal
                    String estiloAresta = noCaminho ? "color=\"red\", penwidth=2.5" : "color=\"black\"";
                    String conexao = grafo.isOrientado() ? "->" : "--";

                    writer.write("  " + u + " " + conexao + " " + v + " [label=\"" + peso + "\", " + estiloAresta + "];\n");
                }
            }
            writer.write("}\n");
        } catch (IOException e) {
            System.out.println("Erro ao criar: " + e.getMessage());
            return;
        }

        // Passa a variável atualizada caminhoPngFinal para a execução
        executarGraphviz(caminhoDot, caminhoPngFinal);
    }

    // Executa o comando do sistema
    private static void executarGraphviz(String caminhoDot, String nomeImagemPng) {
        try {


            String[] comando = {"C:\\Program Files\\Graphviz\\bin\\dot.exe", "-Tpng", caminhoDot, "-o", nomeImagemPng};
            Process processo = Runtime.getRuntime().exec(comando);
            processo.waitFor();
            System.out.println("Desenho gerado com sucesso: " + nomeImagemPng);
        } catch (Exception e) {
            System.out.println("Erro ao executar o Graphviz: " + e.getMessage());
        }
    }
}