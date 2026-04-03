package br.com.ucsal.olimpiadas.repository;

import br.com.ucsal.olimpiadas.Participante;
import java.util.List;

public interface ParticipanteRepository {
    long nextId();

    void save(Participante participante);

    List<Participante> findAll();

    boolean existsById(long id);
}

