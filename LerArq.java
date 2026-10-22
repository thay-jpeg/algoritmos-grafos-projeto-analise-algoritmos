import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//Função de ler arquivos
public class LerArq {

    public static void carregar(String caminhoArquivo) {
        // Usamos o try-with-resources para garantir que o arquivo seja fechado no final
        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo))) {

            // PASSO 1: Ler a primeira linha (Orientação)
            String linhaOrientacao = leitor.readLine();
            boolean ehOrientado = false;
            if(linhaOrientacao.equals("orientado=sim")) {
                ehOrientado = true;
            } else
                ehOrientado = false;
            // PASSO 2: Ler a segunda linha (Quantidade de vértices)
            String linhaVertices = leitor.readLine();
            int quantidadeV = 0;
            linhaVertices = linhaVertices.replace("V=","");
            quantidadeV = Integer.parseInt(linhaVertices);


            // Grafo nGrafo = new Grafo(quantidadeV, ehOrientado);

            // PASSO 4: Ler as arestas (da terceira linha até o fim do arquivo)
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

                // PASSO 5: Adicionar a aresta no grafo
                // Ex: meuGrafo.adicionarAresta(u, v, peso);
            }

        } catch (IOException e) {
            System.out.println("Erro ao abrir ou ler o arquivo: " + e.getMessage());
        }
    }
}