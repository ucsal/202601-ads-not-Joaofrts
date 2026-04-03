package br.com.ucsal.olimpiadas.repository.memory;

import br.com.ucsal.olimpiadas.Tentativa;
import br.com.ucsal.olimpiadas.repository.TentativaRepository;
import java.util.ArrayList;
import java.util.List;

public class InMemoryTentativaRepository implements TentativaRepository {
    private long nextId = 1;
    private final List<Tentativa> tentativas = new ArrayList<>();

    @Override
    public long nextId() {
        return nextId++;
    }

    @Override
    public void save(Tentativa tentativa) {
        tentativas.add(tentativa);
    }

    @Override
    public List<Tentativa> findAll() {
        return List.copyOf(tentativas);
    }
}

