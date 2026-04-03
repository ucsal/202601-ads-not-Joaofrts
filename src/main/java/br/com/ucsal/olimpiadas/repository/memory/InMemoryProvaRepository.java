package br.com.ucsal.olimpiadas.repository.memory;

import br.com.ucsal.olimpiadas.Prova;
import br.com.ucsal.olimpiadas.repository.ProvaRepository;
import java.util.ArrayList;
import java.util.List;

public class InMemoryProvaRepository implements ProvaRepository {
    private long nextId = 1;
    private final List<Prova> provas = new ArrayList<>();

    @Override
    public long nextId() {
        return nextId++;
    }

    @Override
    public void save(Prova prova) {
        provas.add(prova);
    }

    @Override
    public List<Prova> findAll() {
        return List.copyOf(provas);
    }

    @Override
    public boolean existsById(long id) {
        return provas.stream().anyMatch(p -> p.getId() == id);
    }
}

