import modelo.Grafo;
import modelo.Aresta;
import util.LerArq;
import util.DesenhoGrafo;
import algoritmos.BuscaGrafo;
import algoritmos.OrdenacaoTopologica;
import algoritmos.ComponenteConexa;
import algoritmos.Dijkstra;
import algoritmos.Kruskal;
import java.util.Scanner;

/**
 * Classe de execução do programa.
 * Apresenta o menu interativo.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Grafo grafoAtual = null;
        int opcao = -1;

        
        while (opcao != 0) {
            System.out.println("\n==================================================");
            System.out.println("          Sistema de Algoritmos em Grafos");
            System.out.println("==================================================");
            System.out.println("1. Carregar grafo");
            System.out.println("2. Desenho de grafo");
            System.out.println("3. Busca em profundidade");
            System.out.println("4. Busca em largura");
            System.out.println("5. Ordenacao topologica");
            System.out.println("6. Componentes fortemente conexas");
            System.out.println("7. Dijkstra");
            System.out.println("8. Kruskal");
            System.out.println("0. Sair");
            System.out.println("==================================================\n");
            System.out.print("Escolha uma opcao: ");
            
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("\nEntrada invalida. Por favor, digite um numero correspondente ao menu.");
                scanner.nextLine();
                continue;
            }

            //bloqueia a execução de algoritmos sem um grafo instanciado
            if (opcao >= 2 && opcao <= 8 && grafoAtual == null) {
                System.out.println("\nErro: Nenhum grafo carregado. Por favor, execute a opcao 1 primeiro.");
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.print("\nIntroduza o caminho do ficheiro (ex: data/grafo_teste.txt): ");
                    String caminho = scanner.nextLine();
                    
                    if(caminho.isEmpty()) {
                        System.out.println("Erro: caminho do arquivo não pode ser vazio.");
                        break;
                    }

                    grafoAtual = LerArq.carregarGrafo(caminho);
                    if (grafoAtual != null)
                        System.out.println("\nGrafo carregado com sucesso!");
                    break;
                case 2:
                    System.out.println("\nFuncionalidade de desenho...");
                    DesenhoGrafo.gerarImagemBase(grafoAtual, "meu_grafo.png");

                    break;
                case 3:
                    BuscaGrafo buscaDfs = new BuscaGrafo(grafoAtual);
                    buscaDfs.buscaEmProfundidade();
                    break;
                case 4:
                    System.out.print("\nOrigem da Busca em largura: ");
                    int origemBfs = scanner.nextInt();

                    if (origemBfs < 0 || origemBfs >= grafoAtual.getNumVertices()) {
                        System.out.println("Erro: vertice de origem invalido.");
                        break;
                    }

                    BuscaGrafo buscaBfs = new BuscaGrafo(grafoAtual);
                    buscaBfs.buscaEmLargura(origemBfs);
                    break;
                case 5:
                    if (!grafoAtual.isOrientado()) {
                        System.out.println("\nErro: A ordenacao topologica so pode ser aplicada a grafos orientados.");
                    } else {
                        OrdenacaoTopologica ordTopologica = new OrdenacaoTopologica(grafoAtual);
                        ordTopologica.executar();
                    }
                    break;
                case 6:
                    if (!grafoAtual.isOrientado()) {
                        System.out.println("\nErro: Componentes fortemente conexas exigem grafos orientados.");
                    } else {
                        ComponenteConexa cfc = new ComponenteConexa(grafoAtual);
                        cfc.executar();
                    }
                    break;
                case 7:
                    if (grafoAtual == null) {
                        break;
                    }

                    int numVertices = grafoAtual.getNumVertices();
                    boolean flag = false;

                    for (int i = 0; i < numVertices; i++) {
                    for (Aresta aresta : grafoAtual.getAdjacencias(i)) {
                    if (aresta.getPeso() < 0) {
                    flag = true;
                    System.out.println("Erro: pesos negativos. O algoritmo de Dijkstra não pode ser executado devido aos pesos negativos.");
                }
            }
            if(flag) break;
        }
                if(!flag) {
                    System.out.print("\nIntroduza o vertice de origem para o algoritmo de Dijkstra: ");
                    int origemDijkstra = scanner.nextInt();

                    if(origemDijkstra < 0 || origemDijkstra >= numVertices) {
                        System.out.println("Erro: vertice de origem invalido.");
                        break;
                    }

                    int predecessores[];
                    Dijkstra dijkstra = new Dijkstra(grafoAtual);
                    predecessores = dijkstra.executar(origemDijkstra);
                    DesenhoGrafo.gerarImagemCaminho(grafoAtual,predecessores,"caminho.png");
            }
                    break;
                case 8:
                    Kruskal kruskal = new Kruskal(grafoAtual);
                    kruskal.executar();
                    break;
                case 0:
                    System.out.println("A encerrar o programa...");
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        }
        scanner.close();
    }
}