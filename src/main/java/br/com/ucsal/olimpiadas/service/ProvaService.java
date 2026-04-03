package br.com.ucsal.olimpiadas.service;

import br.com.ucsal.olimpiadas.Prova;
import br.com.ucsal.olimpiadas.repository.ProvaRepository;
import java.util.List;
import java.util.Optional;

public class ProvaService {
    private final ProvaRepository repository;

    public ProvaService(ProvaRepository repository) {
        this.repository = repository;
    }

    public Optional<Prova> cadastrar(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            return Optional.empty();
        }

        Prova prova = new Prova();
        prova.setId(repository.nextId());
        prova.setTitulo(titulo);
        repository.save(prova);

        return Optional.of(prova);
    }

    public List<Prova> listar() {
        return repository.findAll();
    }

    public boolean existe(long id) {
        return repository.existsById(id);
    }

    public boolean vazio() {
        return repository.findAll().isEmpty();
    }
}

