package safraviva.excecoes;

// Esta e a classe-mae de todos os erros do nosso sistema
public class SafraVivaException extends Exception {
    public SafraVivaException(String mensagem) {
        super(mensagem);
    }
}