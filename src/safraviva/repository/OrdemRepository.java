package safraviva.repository;

import safraviva.model.OrdemDistribuicao;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrdemRepository implements IRepository<OrdemDistribuicao> {
    private static final String FICHEIRO = "ordens_safraviva.dat";
    private List<OrdemDistribuicao> ordens;

    public OrdemRepository() {
        this.ordens = new ArrayList<>();
        carregarDeFicheiro();
    }

    @Override
    public void salvar(OrdemDistribuicao ordem) {
        ordens.add(ordem);
        persistirEmFicheiro();
    }

    @Override
    public List<OrdemDistribuicao> listarTodos() {
        return new ArrayList<>(ordens);
    }

    @Override
    public void persistirEmFicheiro() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHEIRO))) {
            oos.writeObject(ordens);
        } catch (IOException e) {
            System.err.println("Erro ao guardar ordens em ficheiro: " + e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void carregarDeFicheiro() {
        File file = new File(FICHEIRO);
        if (!file.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            this.ordens = (List<OrdemDistribuicao>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar ordens de ficheiro: " + e.getMessage());
        }
    }
}
