package safraviva.excecoes;

// Lancado quando um lote de comida ja passou da data e não pode ser doado
public class LoteExpiradoException extends SafraVivaException {
    public LoteExpiradoException(String mensagem) {
        super(mensagem);
    }
}