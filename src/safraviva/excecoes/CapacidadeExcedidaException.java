package safraviva.excecoes;

// Lancado quando a carga ultrapassa a capacidade da carrinha do voluntário
public class CapacidadeExcedidaException extends SafraVivaException {
    public CapacidadeExcedidaException(String mensagem) {
        super(mensagem);
    }
}