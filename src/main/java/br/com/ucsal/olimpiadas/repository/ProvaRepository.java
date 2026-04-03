package br.com.ucsal.olimpiadas.repository;

import br.com.ucsal.olimpiadas.Prova;
import java.util.List;

public interface ProvaRepository {
    long nextId();

    void save(Prova prova);

    List<Prova> findAll();

    boolean existsById(long id);
}

