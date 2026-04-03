package br.com.ucsal.olimpiadas.repository.memory;

import br.com.ucsal.olimpiadas.Participante;
import br.com.ucsal.olimpiadas.repository.ParticipanteRepository;
import java.util.ArrayList;
import java.util.List;

public class InMemoryParticipanteRepository implements ParticipanteRepository {
    private long nextId = 1;
    private final List<Participante> participantes = new ArrayList<>();

    @Override
    public long nextId() {
        return nextId++;
    }

    @Override
    public void save(Participante participante) {
        participantes.add(participante);
    }

    @Override
    public List<Participante> findAll() {
        return List.copyOf(participantes);
    }

    @Override
    public boolean existsById(long id) {
        return participantes.stream().anyMatch(p -> p.getId() == id);
    }
}

