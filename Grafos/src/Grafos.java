    import java.util.*;

    public class Grafos {
        private LinkedHashMap<String, ArrayList<String>> grafo;
        private LinkedHashMap<String, ArrayList<Integer>> pesos;
        private HashMap<String, Integer> cor;

        public Grafos() {
            this.grafo = new LinkedHashMap<>();
            this.cor = new HashMap<>();
            this.pesos = new LinkedHashMap<>();
        }

       public void cores(String cor, String chave){
            switch (cor){
                case "WHITE":
                    this.cor.put(chave, 0);
                    break;
                case "GREY":
                    this.cor.put(chave, 1);
                    break;
                case "BLACK":
                    this.cor.put(chave, 2);
                    break;
            }
       }

       public void bfs(String partida, String chegada){
           if(!this.grafo.containsKey(partida) || !this.grafo.containsKey(chegada)){
               return;
           }

            HashMap<String, String> veioDe = new HashMap<>();
            Queue<String> listaDePesquisa = new LinkedList<>();
            ArrayList<String> vertices = this.getVertices();

            listaDePesquisa.add(partida);

            for(String a : vertices){
                this.cores("WHITE", a);
            }

           this.cores("GREY", partida);

            while(!listaDePesquisa.isEmpty()){
                String arestasA = listaDePesquisa.poll();
                ArrayList<String> verticesA = this.grafo.get(arestasA);

                listaDePesquisa.addAll(verticesA);

                for(String i : verticesA){

                    if(this.cor.get(i) == 0){
                        this.cores("GREY", i);

                        veioDe.put(i, arestasA);
                        listaDePesquisa.add(i);

                        if(i.equals(chegada)){
                            listaDePesquisa.clear();
                            break;
                        }
                    }
                }
                this.cores("BLACK", arestasA);
            }

           if (!veioDe.containsKey(chegada)) {
               System.out.println("Caminho não encontrado.");
           } else {
               LinkedList<String> caminho = new LinkedList<>();
               String atual = chegada;
               while (atual != null) {
                   caminho.addFirst(atual);
                   atual = veioDe.get(atual);
               }
               System.out.println("Caminho encontrado: " + caminho);
           }
       }

        public void djkstra(String partida, String chegada, int[][] matrizPesos) {
            HashMap<String, String> veioDe = new HashMap<>();
            HashMap<String, Integer> veioDePesos = new HashMap<>();
            HashMap<String, Integer> indexPesos = new HashMap<>();
            ArrayList<String> vertices = this.getVertices();
            ArrayList<String> processados = new ArrayList<>();
            Queue<String> listaDePesquita = new LinkedList<>();

            for(String vertice : vertices){
                veioDe.put(vertice, null);
                veioDePesos.put(vertice, Integer.MAX_VALUE);
                indexPesos.put(vertice, vertices.indexOf(vertice));
            }

            veioDePesos.put(partida, 0);
            listaDePesquita.add(partida);

            while(!listaDePesquita.isEmpty()){
                String arestaA = listaDePesquita.poll();
                if(arestaA.equals(chegada)){
                    LinkedList<String> caminho = new LinkedList<>();
                    String atual = chegada;
                    while (atual != null) {
                        caminho.addFirst(atual);
                        atual = veioDe.get(atual);
                    }
                    System.out.println("Caminho encontrado: " + caminho);
                    return;
                }

                ArrayList<String> vericesA = this.grafo.get(arestaA);
                for(int i = 0; i < vericesA.size(); i++){
                    String arestaP = vericesA.get(i);
                    int peso = matrizPesos[indexPesos.get(arestaA)][indexPesos.get(arestaP)];

                    if(peso + veioDePesos.get(arestaA) < veioDePesos.get(arestaP)){
                        veioDePesos.put(arestaP, peso + veioDePesos.get(arestaA));
                        veioDe.put(arestaP, arestaA);
                        listaDePesquita.add(arestaP);
                    }
                }
                processados.add(arestaA);
            }
        }

        public int[][] floydWarshall(int[][] matrizPesos, int num){
            int[][] grafo = new int[num][num];

            for(int i = 0; i < num; i++){
                for(int j = 0; j < num; j++){
                    if(j == i){
                        grafo[i][j] = 0;
                    } else if(matrizPesos[i][j] == 0) {
                        grafo[i][j] = Integer.MAX_VALUE;
                    } else {
                        grafo[i][j] = matrizPesos[i][j];
                    }
                }
            }

            for (int k = 0; k < num; k++) {
                for (int i = 0; i < num; i++) {
                    for (int j = 0; j < num; j++) {
                        if (grafo[i][k] != Integer.MAX_VALUE && grafo[k][j] != Integer.MAX_VALUE
                                && grafo[i][k] + grafo[k][j] < grafo[i][j]) {
                            grafo[i][j] = grafo[i][k] + grafo[k][j];
                        }
                    }
                }
            }

            return grafo;
        }

        public void dfs() {
            ArrayList<String> vertices = this.getVertices();
            HashMap<String, Integer> descoberto = new HashMap<>();
            HashMap<String, Integer> processado = new HashMap<>();
            HashMap<String, String> veioDe = new HashMap<>();
            int[] contador = {0};

            for (String i : vertices) {
                this.cores("WHITE", i);
                descoberto.put(i, 0);
                processado.put(i, 0);
                veioDe.put(i, null);
            }

            for (String i : vertices) {
                if (this.cor.get(i) == 0) {
                    this.dfsRecursivo(i, descoberto, processado, veioDe, contador);
                }
            }

            System.out.println("Descoberto: " + descoberto);
            System.out.println("Processado: " + processado);
        }

        public void dfsRecursivo(String aresta, HashMap<String, Integer> descobertos,
                                 HashMap<String, Integer> processados, HashMap<String, String> veioDe, int[] contador) {
            contador[0]++;
            descobertos.put(aresta, contador[0]);
            this.cores("GREY", aresta);

            ArrayList<String> vertices = this.grafo.get(aresta);
            for(String i : vertices){
                if(this.cor.get(i) == 0){
                    veioDe.put(i, aresta);
                    dfsRecursivo(i, descobertos, processados, veioDe, contador);
                }
            }

            this.cores("BLACK", aresta);
            contador[0]++;
            processados.put(aresta, contador[0]);
        }

        public boolean criarAresta(String aresta) {
            if (!grafo.containsKey(aresta)) {
                grafo.put(aresta, new ArrayList<>());
                pesos.put(aresta, new ArrayList<>());
                return true;
            }
            return false;
        }

        public boolean inserirVertices(String key, String valor) {
            if (grafo.containsKey(key) && !grafo.get(key).contains(valor)) {
                grafo.get(key).add(valor);
                return true;
            }
            return false;
        }

        public boolean inserirPesos(String key, int peso) {
            if (pesos.containsKey(key)) {
                pesos.get(key).add(peso);
                return true;
            }
            return false;
        }

        public ArrayList<String> getVertices() {
            ArrayList<String> vertices = new ArrayList<>(this.grafo.keySet());

            return vertices;
        }
        @Override
        public String toString() {
            return grafo.toString();
        }

        public static void main(String[] args) {
            Grafos grafo = new Grafos();

            grafo.criarAresta("A");
            grafo.criarAresta("B");
            grafo.criarAresta("C");
            grafo.criarAresta("D");
            grafo.criarAresta("E");

            grafo.inserirVertices("A", "B");
            grafo.inserirVertices("A", "C");
            grafo.inserirVertices("A", "E");
            grafo.inserirVertices("C", "A");
            grafo.inserirVertices("C", "D");
            grafo.inserirVertices("C", "E");
            grafo.inserirVertices("B", "A");
            grafo.inserirVertices("B", "C");
            grafo.inserirVertices("B", "D");

            int[][] matrizPesos = {
                    {0, 20, 4, 0, 23},
                    {20, 0, 17, 58, 0},
                    {4, 17, 3, 3, 15},
                    {0, 58, 3, 0, 0},
                    {23, 0, 15, 0, 0}
            };

            int[][] arr = grafo.floydWarshall(matrizPesos, 5);

            for(int i = 0; i < 5; i++){
                for(int j = 0; j < 5; j++){
                    if(arr[i][j] == Integer.MAX_VALUE){
                        System.out.print("INF ");
                    }else{
                        System.out.print(arr[i][j] + " ");
                    }
                }
                System.out.print("\n");
            }

            //grafo.djkstra("A", "E", matrizPesos);
        }
    }
