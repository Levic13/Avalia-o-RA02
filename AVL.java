// Leandro Cardoso Vieira
import java.util.Random;
class No {
    int valor, altura;
    No esquerda, direita;

    No(int valor) {
        this.valor = valor;
        this.altura = 1;
    }
}
class ArvoreAVL {
    No raiz;

    int altura(No no) {
        if (no == null)
            return 0;
        return no.altura;
    }

    int fatorBalanceamento(No no) {
        if (no == null)
            return 0;
        return altura(no.esquerda) - altura(no.direita);
    }

    No rotacaoDireita(No y) {
        No x = y.esquerda;
        No T = x.direita;

        x.direita = y;
        y.esquerda = T;

        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;

        return x;
    }

    No rotacaoEsquerda(No x) {
        No y = x.direita;
        No T = y.esquerda;

        y.esquerda = x;
        x.direita = T;

        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;

        return y;
    }

    No inserir(No no, int valor) {
        if (no == null)
            return new No(valor);

        if (valor < no.valor)
            no.esquerda = inserir(no.esquerda, valor);
        else if (valor > no.valor)
            no.direita = inserir(no.direita, valor);
        else
            return no;

        no.altura = Math.max(altura(no.esquerda), altura(no.direita)) + 1;

        int balanceamento = fatorBalanceamento(no);

        if (balanceamento > 1 && valor < no.esquerda.valor)
            return rotacaoDireita(no);

        if (balanceamento < -1 && valor > no.direita.valor)
            return rotacaoEsquerda(no);

        if (balanceamento > 1 && valor > no.esquerda.valor) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        if (balanceamento < -1 && valor < no.direita.valor) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    No noMinimo(No no) {
        No atual = no;
        while (atual.esquerda != null)
            atual = atual.esquerda;
        return atual;
    }

    No remover(No no, int valor) {
        if (no == null)
            return no;

        if (valor < no.valor)
            no.esquerda = remover(no.esquerda, valor);
        else if (valor > no.valor)
            no.direita = remover(no.direita, valor);
        else {
            if ((no.esquerda == null) || (no.direita == null)) {
                No temp = null;
                if (temp == no.esquerda)
                    temp = no.direita;
                else
                    temp = no.esquerda;

                if (temp == null) {
                    temp = no;
                    no = null;
                } else
                    no = temp;
            } else {
                No temp = noMinimo(no.direita);
                no.valor = temp.valor;
                no.direita = remover(no.direita, temp.valor);
            }
        }

        if (no == null)
            return no;

        no.altura = Math.max(altura(no.esquerda), altura(no.direita)) + 1;

        int balanceamento = fatorBalanceamento(no);

        if (balanceamento > 1 && fatorBalanceamento(no.esquerda) >= 0)
            return rotacaoDireita(no);

        if (balanceamento > 1 && fatorBalanceamento(no.esquerda) < 0) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        if (balanceamento < -1 && fatorBalanceamento(no.direita) <= 0)
            return rotacaoEsquerda(no);

        if (balanceamento < -1 && fatorBalanceamento(no.direita) > 0) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    boolean buscar(No no, int valor) {
        if (no == null)
            return false;

        if (valor < no.valor)
            return buscar(no.esquerda, valor);
        else if (valor > no.valor)
            return buscar(no.direita, valor);
        else
            return true;
    }

    void emOrdem(No no) {
        if (no != null) {
            emOrdem(no.esquerda);
            System.out.print(no.valor + " ");
            emOrdem(no.direita);
        }
    }
    void imprimirEmOrdem(No no) {
        if (no != null) {
            imprimirEmOrdem(no.esquerda);
            System.out.print(no.valor + " ");
            imprimirEmOrdem(no.direita);
        }
    }
}

public class AVL {
    public static void main(String[] args) {
        Random random = new Random(42); // Semente para repetibilidade

        int[] tamanhos = {100, 500, 1000, 10000, 20000};

        for (int tamanho : tamanhos) {
            int[] elementos = new int[tamanho];

            for (int j = 0; j < elementos.length; j++) {
                elementos[j] = random.nextInt(1000);
            }

            ArvoreAVL arvore = new ArvoreAVL();

            long inicioInsercao = System.nanoTime();

            for (int elemento : elementos) {
                arvore.raiz = arvore.inserir(arvore.raiz, elemento);
            }

            long fimInsercao = System.nanoTime();
            long tempoInsercao = fimInsercao - inicioInsercao;

            // 100 buscas aleatórias
            long inicioBuscas = System.nanoTime();

            for (int i = 0; i < 100; i++) {
                int valorBusca = random.nextInt(1000);
                arvore.buscar(arvore.raiz, valorBusca);
            }

            long fimBuscas = System.nanoTime();
            long tempoBuscas = fimBuscas - inicioBuscas;

            long inicioImpressao = System.nanoTime();
            arvore.imprimirEmOrdem(arvore.raiz);
            long fimImpressao = System.nanoTime();
            long tempoImpressao = fimImpressao - inicioImpressao;

            System.out.println( + tamanho + " elementos: " );
            System.out.println("Tempo de inserção para "+ tempoInsercao + " ns");
            System.out.println("Tempo para realizar 100 buscas aleatórias: " + tempoBuscas + " ns");
            System.out.println("Tempo de impressão: " + tempoImpressao + " ns");

            System.out.println();
        }
    }
}




