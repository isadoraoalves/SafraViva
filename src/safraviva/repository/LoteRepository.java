package safraviva.repository;

import safraviva.model.LoteAlimento;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LoteRepository implements IRepository<LoteAlimento> {
    private static final String FICHEIRO = "lotes_safraviva.dat";
    private List<LoteAlimento> lotes;

    public LoteRepository() {
        this.lotes = new ArrayList<>();
        carregarDeFicheiro();
    }

    @Override
    public void salvar(LoteAlimento lote) {
        lotes.add(lote);
        persistirEmFicheiro();
    }

    @Override
    public List<LoteAlimento> listarTodos() {
        return new ArrayList<>(lotes);
    }

    @Override
    public void persistirEmFicheiro() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHEIRO))) {
            oos.writeObject(lotes);
        } catch (IOException e) {
            System.err.println("Erro ao guardar lotes em ficheiro: " + e.getMessage());
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
            this.lotes = (List<LoteAlimento>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler ficheiro de lotes: " + e.getMessage());
        }
    }
}