    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package lib;

    import java.util.*;

    /**
     *
     * @author FeLiXp90, JoaoFerrareis02, Kenko2002, etc .....
     */
    public class ArvoreBinaria<T> implements IArvoreBinaria<T> {

        protected No<T> raiz = null;
        protected Comparator<T> comparador;

        protected No<T> atual = null;
        private ArrayList<No<T>> pilhaNavegacao = null;
        private boolean primeiraChamada = true;

        public ArvoreBinaria(Comparator<T> comp) {
            comparador = comp;
        }

        @Override
        public void adicionar(T novoValor) {
            //Cria um novo nó, atribuindo o valor a adicionaro
            No<T> novoNo = new No<>(novoValor);
            //Se a raiz for nula, a raiz terá o novo nó.
            if (this.raiz == null) {
                this.raiz = novoNo;
            }
            //Se não, chame a função recursiva de adicionar, dando a raiz e o novo nó.
            else {
                this.raiz = adicionarRecursivo(this.raiz, novoNo);
            } 
        }
    
        /**
         * Função de adicionar elemento na árvore binária, retornando o elemento atual adicionado.
         * @param atual - Elemento atual.
         * @param novo - Elemento a ser adicionado
         */
        protected No<T> adicionarRecursivo(No<T> atual, No<T> novo) {
            //Atribui um comparador entre o valor do novo nó e o valor do nó atual dado. 
            int comp = comparador.compare(novo.getValor(), atual.getValor());
            //Se for menor que zero, vai para a esquerda da árvore.
            if (comp < 0) {
                //Caso o filho esquerdo for nulo, adiciona o novo nó.
                if (atual.getFilhoEsquerda() == null) {
                    atual.setFilhoEsquerda(novo);
                } 
                //Caso contrário, chama de novo o função recursiva de adicionar. 
                else {
                    atual.setFilhoEsquerda(adicionarRecursivo(atual.getFilhoEsquerda(), novo));
                }
            }
            //Caso contrário, vá para a direita da arvore. 
            else {
                //Caso o filho direito for nulo, adiciona o novo nó.
                if (atual.getFilhoDireita() == null) {
                    atual.setFilhoDireita(novo);
                }
                //Caso contrário, chama de novo o função recursiva de adicionar.
                else {
                    atual.setFilhoDireita(adicionarRecursivo(atual.getFilhoDireita(), novo));
                }
            }
            return atual;
        }


        @Override
        public T pesquisar(T valor) {

            if (raiz == null) { // A árvore está vazia, não há nada.
                return null;
            }

            atual = raiz;

            while (atual != null) {
                int comparacao = comparador.compare(valor, atual.getValor());

                /*
                 * Seguinte, se o valor atual for igual a valor, então comparacao == 0.
                 * Se for menor, comparacao < 0. Se for maior, comparacao > 0.
                 * Ele vai percorrer a árvore até achar o valor correspondente e retornar.
                 * Se não achar, retorna null.
                 */

                if (comparacao == 0) {
                    return atual.getValor(); // Valor encontrado na árvore.
                } else if (comparacao < 0) {
                    atual = atual.getFilhoEsquerda(); // O valor pode estar na subárvore esquerda.
                } else {
                    atual = atual.getFilhoDireita(); // O valor pode estar na subárvore direita.
                }
            }

            return null; // Valor não encontrado na árvore.
        }

        @Override
        public T remover(T valor) {
            //Chama a função recursiva.
            raiz = removerRecursivo(raiz, valor);
            //Retorna o valor removido.
            return valor;
        }

        protected No<T> removerRecursivo(No<T> atual, T valor) {
            //Caso o nó atual for nula, retorne nulo.
            if (atual == null) {
                return null;
            }
    
            // Faz a comparacao entre o valor e o valor do nó atual 
            int comparacao = comparador.compare(valor, atual.getValor());
    
            // Se a comparacao for menor que nulo, procure no nó filho a esquerda.
            if (comparacao < 0) {
                atual.setFilhoEsquerda(removerRecursivo(atual.getFilhoEsquerda(), valor));
            }
            // Se for maior que zero, procure no nó filho a direita. 
            else if (comparacao > 0) {
                atual.setFilhoDireita(removerRecursivo(atual.getFilhoDireita(), valor));
            }
            // Caso encontrou (comparacao = 0) 
            else {
                // Nó sem folhas
                if (atual.getFilhoEsquerda() == null && atual.getFilhoDireita() == null) {
                    return null;
                } else if (atual.getFilhoEsquerda() == null) {
                    // Nó com uma folha (nó filho a direita)
                    return atual.getFilhoDireita();
                } else if (atual.getFilhoDireita() == null) {
                    // Nó com uma folha (nó filho a esquerda)
                    return atual.getFilhoEsquerda();
                } else {
                    // Nó com duas folhas
                    // Procure o menor valor da sub-arvore
                    T minValue = encontrarValorMinimo(atual.getFilhoDireita());
                    atual.setValor(minValue);
                    // Delete o nó com o menor valor da subárvore a direita.
                    atual.setFilhoDireita(removerRecursivo(atual.getFilhoDireita(), minValue));
                }
            }
    
            return atual;
        }

        private T encontrarValorMinimo(No<T> no) {
            // Enquanto o no filho a esquerda não for nulo, chame a função de encontrar o valor mínimo.
            if (no.getFilhoEsquerda() != null) {
                return encontrarValorMinimo(no.getFilhoEsquerda());
            }
            return no.getValor();
        }


        @Override
        public int altura() {
            return this.raiz.obterAltura(); // Retorna a altura calculada da árvore, chamando a função obterAltura() do nó.
        }

        @Override
        public int quantidadeNos() {
            return contarNos(raiz);
        }

        private int contarNos(No<T> no) { //Ele vai atrás de todos os nós da arvore e soma. Por incrivel que pareça isso não dá erro e a recursão da altura sim, vai saber.
            if (no == null) {
                return 0; // Caso base: nó nulo, significa que a raiz é nula então temos 0 nós.
            }

            int nosNaEsquerda = contarNos(no.getFilhoEsquerda());
            int nosNaDireita = contarNos(no.getFilhoDireita());

            return 1 + nosNaEsquerda + nosNaDireita;
        }

        @Override
        public String caminharEmNivel() {
            StringBuilder resultado = new StringBuilder("["); // Inicializa um StringBuilder para construir em nível.
            if (raiz == null) {
                resultado.append("Vazio]"); //Se a arvore não existir, printa vazio.
            } else {
                ArrayList<No<T>> nivelAtual = new ArrayList<>(); // Cria uma lista para armazenar os nós do nível atual e inicia com a raiz.
                nivelAtual.add(raiz);

                while (!nivelAtual.isEmpty()) { // Enquanto houver nós no nível atual, continue
                    ArrayList<No<T>> proximoNivel = new ArrayList<>(); // Cria uma lista para armazenar os nós do próximo nível.

                    for (No<T> no : nivelAtual) { // Itera pelos nós do nível atual.
                        resultado.append(no.getValor().toString()); // Adiciona o valor do nó atual à representação em string.
                        /*
                         * Se houver um filho à esquerda, adiciona ao próximo nível.
                         * Se houver um filho à direita, adiciona ao proximo nível.
                         */
                        if (no.getFilhoEsquerda() != null) {
                            proximoNivel.add(no.getFilhoEsquerda());
                        }

                        if (no.getFilhoDireita() != null) {
                            proximoNivel.add(no.getFilhoDireita());
                        }

                        // Quando passar pelo nívelAtual, faz uma quebra de linha, pode ser um - ou um | tambem.
                        if (nivelAtual.indexOf(no) != nivelAtual.size() - 1) {
                            resultado.append("\n");
                        }
                    }

                    nivelAtual = proximoNivel; // Atualiza a lista do nível atual para a lista do próximo nível.
                }
            }

            resultado.append("]");
            return resultado.toString(); // Retorna a string final.
        }

        @Override
        public String caminharEmOrdem() {
            StringBuilder resultado = new StringBuilder("[");
            if (raiz == null) {
                resultado.append("Vazio]"); //Se a arvore não existir, printa vazio tambem.
            } else {
                reiniciarNavegacao(); // Limpa a pilha de navegação.
                atual = raiz;

                /*
                 * Aqui segue o mesmo raciocínio que obterPróximo. No primeiro loop interno,
                 * navegamos o mais à esquerda possível, empilhando cada nó visitado.
                 * Quando não é possível ir mais à esquerda, saímos do loop interno.
                 * Se atual tiver um filho direito, isso significa que ainda existem nós na
                 * subárvore à direita que precisam ser visitados antes de subir para o pai.
                 * Portanto, atual é atualizado para o filho direito e, em seguida, um novo loop
                 * interno começa, navegando o mais à esquerda possível novamente, empilhando
                 * cada nó visitado na pilha. Esse processo se repete até que não haja mais
                 * filhos à esquerda no nó atual, e então retornamos ao estágio anterior,
                 * onde removemos o último nó da pilha e adicionamos seu valor ao resultado.
                 */
                while (atual != null || !pilhaNavegacao.isEmpty()) {
                    while (atual != null) {
                        pilhaNavegacao.add(atual);
                        atual = atual.getFilhoEsquerda();
                    }

                    int tamanho = pilhaNavegacao.size();
                    atual = pilhaNavegacao.remove(tamanho - 1);

                    resultado.append(atual.getValor().toString()).append("\n");
                    atual = atual.getFilhoDireita();
                }
            }

            resultado.append("]");

            return resultado.toString();
        }

        @Override
        public T obterProximo() {
            if (raiz == null) {
                return null;
            }

            /*
             * Se esta não for a primeira chamada do método (o que é controlado pela variável
             * primeiraChamada), o método continua a partir do último nó visitado. Caso seja a primeira
             * chamada, ele realiza a inicialização.Na primeira chamada, ele marca primeiraChamada
             * como false e define o nó atual como a raiz da árvore. Em seguida, ele
             * começa a percorrer a árvore, indo para o filho mais à esquerda
             * e adicionando cada nó visitado à pilha de navegação.
             */
            if (primeiraChamada) {
                primeiraChamada = false;
                atual = raiz;

                while (atual != null) {
                    pilhaNavegacao.add(atual);
                    atual = atual.getFilhoEsquerda();
                }
            } else {
                /*
                 * Nas próximas chamadas de obterProximo, o método verifica se o nó atual tem um filho
                 * à direita. Se tiver, ele move-se para a direita e continua indo para o filho mais à
                 * esquerda desse nó, adicionando todos os nós ao longo do caminho à pilha de
                 * navegação.
                 */
                if (atual.getFilhoDireita() != null) {
                    atual = atual.getFilhoDireita();

                    while (atual != null) {
                        pilhaNavegacao.add(atual);
                        atual = atual.getFilhoEsquerda();
                    }
                } else {
                    /*
                     * Se o nó atual não tiver um filho à direita, significa que todos os nós à direita
                     * já foram visitados. Nesse caso, ele remove o nó atual da pilha de navegação e
                     * verifica se o nó atual é o filho direito do pai. Se for, continua subindo na
                     * árvore até encontrar um pai que não seja um filho direito.
                     */
                    No<T> pai = pilhaNavegacao.remove(pilhaNavegacao.size() - 1); // Se o nó atual não tiver filho à direita

                    while (!pilhaNavegacao.isEmpty() && atual == pai.getFilhoDireita()) {
                        atual = pai; // Volta para o pai enquanto o nó atual for o filho direito do pai.
                        pai = pilhaNavegacao.remove(pilhaNavegacao.size() - 1); // Remove o próximo pai da pilha.
                    }

                    atual = pai; // Define o nó atual como o pai atual.
                }
            }

            /*
             * Ou seja... desse jeito percorremos toda a árvore em ordem crescente, e vamos marcando
             * os nós na pilha durante a descida, quando voltamos para o pai desses nós, retiramos
             * os nós da pilha desmarcando eles e retornando seu valor.
             */
            return atual != null ? atual.getValor() : null; // Retorna o valor do nó atual ou null quando não houver mais elementos.
        }


        @Override
        public void reiniciarNavegacao() {
            if (pilhaNavegacao != null) { //Limpa a pilha caso tenha algo e seta a primeiraChamada.
                primeiraChamada = true;
                pilhaNavegacao.clear();
            }
        }

    }
