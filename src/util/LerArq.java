package util;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import modelo.Grafo;

//Função de ler arquivos
public class LerArq {

    public static void carregar(String caminhoArquivo) {
        // try-with-resources para garantir que o arquivo seja fechado no final
        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo))) {

            // Ler a primeira linha (Orientação)
            String linhaOrientacao = leitor.readLine();
            boolean ehOrientado = false;
            if(linhaOrientacao.equals("orientado=sim")) {
                ehOrientado = true;
            } else
                ehOrientado = false;
            // Ler a segunda linha (Quantidade de vértices)
            String linhaVertices = leitor.readLine();
            int quantidadeV = 0;
            linhaVertices = linhaVertices.replace("V=","");
            quantidadeV = Integer.parseInt(linhaVertices);


            // Grafo nGrafo = new Grafo(quantidadeV, ehOrientado);

            // Ler as arestas (da terceira linha até o fim do arquivo)
            String linhaAresta;
            while ((linhaAresta = leitor.readLine()) != null) {
               //limpa a string tirando ()
               linhaAresta = linhaAresta.replace("(", "").replace(")","");
               //divide a string em 2 partes com peso e vertices
               String[] Fatia = linhaAresta.split(":");
               //divide a string entre vertice e aresta
               String[] VerticeAresta = Fatia[0].split(",");
               //valores encontrados
               int peso = Integer.parseInt(Fatia[1]);
               int u = Integer.parseInt(VerticeAresta[0]);
               int v = Integer.parseInt(VerticeAresta[1]);

                // Adicionar a aresta no grafo
                // Ex: meuGrafo.adicionarAresta(u, v, peso);
            }

        } catch (IOException e) {
            System.out.println("Erro ao abrir ou ler o arquivo: " + e.getMessage());
        }
    }

    public static Grafo carregarGrafo(String caminhoArquivo) {
        Grafo grafo = null;

        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo))) {

            //Orientação e número de vértices
            boolean ehOrientado = leitor.readLine().equals("orientado=sim");
            int quantidadeV = Integer.parseInt(leitor.readLine().replace("V=", ""));

            //Cria a estrutura vazia do Grafo
            grafo = new Grafo(quantidadeV, ehOrientado);

            //Extrai as arestas e popula a Lista de Adjacência
            String linhaAresta;
            while ((linhaAresta = leitor.readLine()) != null) {
                linhaAresta = linhaAresta.replace("(", "").replace(")", "");
                String[] fatia = linhaAresta.split(":");
                String[] verticeAresta = fatia[0].split(",");

                int u = Integer.parseInt(verticeAresta[0]);
                int v = Integer.parseInt(verticeAresta[1]);
                int peso = Integer.parseInt(fatia[1]);

                grafo.adicionarAresta(u, v, peso);
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        return grafo;
    }
}