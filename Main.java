import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

class Registro {
    private int codigo;

    public Registro(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}

class TabelaHash {
    private int tamanho;
    private ArrayList<LinkedList<Registro>> tabela;

    public TabelaHash(int tamanho) {
        this.tamanho = tamanho;
        tabela = new ArrayList<>(tamanho);
        for (int i = 0; i < tamanho; i++) {
            tabela.add(new LinkedList<>());
        }
    }

    public void inserir(Registro registro) {
        int indice = registro.getCodigo() % tamanho;
        tabela.get(indice).add(registro);
    }

    public boolean buscar(int codigo) {
        int indice = codigo % tamanho;
        return tabela.get(indice).contains(new Registro(codigo));
    }

    public int getNumeroColisoes() {
        int colisoes = 0;
        HashSet<Integer> indicesVisitados = new HashSet<>();
        for (LinkedList<Registro> lista : tabela) {
            for (int i = 1; i < lista.size(); i++) {
                if (!indicesVisitados.contains(i)) {
                    colisoes++;
                    indicesVisitados.add(i);
                }
            }
        }
        return colisoes;
    }
}

public class Main {
    public static void main(String[] args) {
        int[] tamanhos = {10, 100, 1000, 10000, 100000};
        String[] funcoesHash = {"restoDivisao", "multiplicacao", "dobramento"};
        Random random = new Random(123); // Seed

        for (int tamanho : tamanhos) {
            TabelaHash tabelaHash = new TabelaHash(tamanho);
            for (int i = 0; i < 20000; i++) {
                int codigo = random.nextInt(1000000000);
                Registro registro = new Registro(codigo);
                tabelaHash.inserir(registro);
            }
            int numeroColisoes = tabelaHash.getNumeroColisoes();
            System.out.println("Tamanho da tabela: " + tamanho);
            System.out.println("Número de colisões: " + numeroColisoes);
        }
    }
}
