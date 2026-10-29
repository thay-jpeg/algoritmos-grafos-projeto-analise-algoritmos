# Sistema de Algoritmos em Grafos

## Tecnologias Necessárias

Para o desenvolvimento, compilação e execução correta do sistema, os seguintes requisitos devem ser atendidos:

* **Linguagem de Programação:** Java (JDK 17.0 ou superior).
* **Ambientes de Desenvolvimento sugeridos:** VS Code ou IntelliJ IDEA.
* **Dependência Externa (Geração de Gráficos):** [Graphviz](http://www.graphviz.org/) na versão 16.0 ou superior. 
  * *Atenção:* O executável do Graphviz (`dot`) precisa estar obrigatoriamente adicionado ao `PATH` do sistema operacional para a geração automatizada das imagens `.png`.

### Compilação e Execução

O projeto possui uma arquitetura modular sem dependência de ferramentas externas de build (como Maven ou Gradle). Para compilar e rodar o software manualmente via terminal, execute os comandos abaixo na raiz do projeto:

**1. Compilar o projeto:**
```bash
javac -d bin -sourcepath src src/Main.java
```

**2. Executar o sistema:**
```bash
java -cp bin Main
```

---

## Como Utilizar o Sistema

Ao iniciar o programa, um menu interativo de linha de comando (CLI) será exibido. 

### Formato do Arquivo de Entrada
Antes de utilizar os algoritmos, é necessário ter um arquivo `.txt` contendo a estrutura do grafo seguindo estritamente a sintaxe abaixo:

1. **Linha 1:** Flag de orientação (`orientado=sim` ou `orientado=nao`).
2. **Linha 2:** Quantidade de vértices (`V=n`).
3. **Linha 3 em diante:** Arestas no formato `(origem,destino):peso`.

*Exemplo:*
```text
orientado=sim
V=5
(0,1):11
(0,2):-4
(1,3):-5
```

### Opções do Menu Principal

* **1. Carregar grafo:** Ação obrigatória inicial. O usuário deve inserir o caminho do arquivo (ex: `data/grafo_teste.txt`) para instanciar o grafo na memória do sistema.
* **2. Desenho de grafo:** Aciona o Graphviz e gera o layout padrão do grafo carregado em um arquivo `.png`, ilustrando vértices e arestas com seus pesos.
* **3. Busca em Profundidade (DFS):** Solicita um vértice de origem e imprime a ordem de visitação explorando os ramos até a profundidade máxima. Alcança todos os vértices, mesmo em grafos desconexos.
* **4. Busca em Largura (BFS):** Solicita um vértice de origem e imprime a ordem de visitação em formato de camadas.
* **5. Ordenação Topológica:** Aplicável apenas a grafos orientados. Imprime a precedência linear (ordem de dependência) dos vértices.
* **6. Componentes Fortemente Conexas:** Aplicável apenas a grafos orientados. Imprime as componentes no terminal em ordem decrescente de tamanho e gera um gráfico `.png` colorindo os vértices de cada componente com uma cor distinta.
* **7. Dijkstra (Caminhos Mais Curtos):** Solicita um vértice de origem e lista o custo mínimo e a rota exata para alcançar todos os outros vértices do grafo.
* **8. Kruskal (Árvore Geradora Mínima):** Lista as arestas que compõem a Árvore Geradora Mínima, exibe o peso total e gera um gráfico `.png` com as arestas pertencentes à árvore destacadas em vermelho.
* **0. Sair:** Encerra a aplicação em segurança.