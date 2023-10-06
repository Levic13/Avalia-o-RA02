import java.util.Random;
class No {
    int valor;
    No esquerda, direita;

    public No(int valor) {
        this.valor = valor;
        this.esquerda = this.direita = null;
    }
}
class ArvoreBinaria {
    No raiz;

    public ArvoreBinaria() {
        this.raiz = null;
    }

    // Método de inserção
    public void inserir(int valor) {
        raiz = inserirRec(raiz, valor);
    }

    private No inserirRec(No no, int valor) {
        if (no == null) {
            no = new No(valor);
            return no;
        }

        if (valor < no.valor) {
            no.esquerda = inserirRec(no.esquerda, valor);
        } else if (valor > no.valor) {
            no.direita = inserirRec(no.direita, valor);
        }

        return no;
    }

    // Método de busca
    public boolean buscar(int valor) {
        return buscarRec(raiz, valor);
    }

    private boolean buscarRec(No no, int valor) {
        if (no == null) {
            return false;
        }

        if (valor == no.valor) {
            return true;
        }

        if (valor < no.valor) {
            return buscarRec(no.esquerda, valor);
        } else {
            return buscarRec(no.direita, valor);
        }
    }

    // Método de remoção
    public void remover(int valor) {
        raiz = removerRec(raiz, valor);
    }

    private No removerRec(No no, int valor) {
        if (no == null) {
            return null;
        }

        if (valor < no.valor) {
            no.esquerda = removerRec(no.esquerda, valor);
        } else if (valor > no.valor) {
            no.direita = removerRec(no.direita, valor);
        } else {
            if (no.esquerda == null) {
                return no.direita;
            } else if (no.direita == null) {
                return no.esquerda;
            }

            no.valor = valorMinimo(no.direita);

            no.direita = removerRec(no.direita, no.valor);
        }

        return no;
    }

    private int valorMinimo(No no) {
        int minimo = no.valor;
        while (no.esquerda != null) {
            minimo = no.esquerda.valor;
            no = no.esquerda;
        }
        return minimo;
    }

    // Método de impressão em ordem
    public void imprimirEmOrdem() {
        imprimirEmOrdemRec(raiz);
        System.out.println();
    }

    private void imprimirEmOrdemRec(No no) {
        if (no != null) {
            imprimirEmOrdemRec(no.esquerda);
            System.out.print(no.valor + " ");
            imprimirEmOrdemRec(no.direita);
        }
    }
}

public class Binario {
    public static void main(String[] args) {
        Random random = new Random(42); // Semente para repetibilidade

        int[] tamanhos = {100, 500, 1000, 10000, 20000};

        for (int tamanho : tamanhos) {
            int[] elementos = new int[tamanho];

            for (int j = 0; j < elementos.length; j++) {
                elementos[j] = random.nextInt(1000);
            }

            ArvoreBinaria arvore = new ArvoreBinaria();

            long inicioCriacao = System.nanoTime(); // Adicionado

            for (int elemento : elementos) {
                arvore.inserir(elemento);
            }

            long fimCriacao = System.nanoTime(); // Adicionado
            long tempoCriacao = fimCriacao - inicioCriacao; // Adicionado

            // 100 buscas aleatórias
            long inicioBuscas = System.nanoTime();

            for (int i = 0; i < 100; i++) {
                int valorBusca = random.nextInt(1000);
                arvore.buscar(valorBusca);
            }

            long fimBuscas = System.nanoTime();
            long tempoBuscas = fimBuscas - inicioBuscas;

            long inicioImpressao = System.nanoTime();
            arvore.imprimirEmOrdem();
            long fimImpressao = System.nanoTime();
            long tempoImpressao = fimImpressao - inicioImpressao;

            System.out.println("Tempo de criação para " + tamanho + " elementos: " + tempoCriacao + " ns");
            System.out.println("Tempo de inserção para " + tamanho + " elementos: " + tempoCriacao + " ns"); // Modificado
            System.out.println("Tempo para realizar 100 buscas aleatórias: " + tempoBuscas + " ns"); // Adicionado
            System.out.println("Tempo de impressão: " + tempoImpressao + " ns");
            System.out.println();

        }
    }
}