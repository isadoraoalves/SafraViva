package safraviva.repository;

import java.util.List;

public interface IRepository<T> {
    void salvar(T entidade);
    List<T> listarTodos();
    void persistirEmFicheiro();
    void carregarDeFicheiro();
}