package br.com.ucsal.olimpiadas.service;

import br.com.ucsal.olimpiadas.Participante;
import br.com.ucsal.olimpiadas.repository.ParticipanteRepository;
import java.util.List;
import java.util.Optional;

public class ParticipanteService {
    private final ParticipanteRepository repository;

    public ParticipanteService(ParticipanteRepository repository) {
        this.repository = repository;
    }

    public Optional<Participante> cadastrar(String nome, String email) {
        if (nome == null || nome.isBlank()) {
            return Optional.empty();
        }

        Participante participante = new Participante();
        participante.setId(repository.nextId());
        participante.setNome(nome);
        participante.setEmail(email);

        repository.save(participante);
        return Optional.of(participante);
    }

    public List<Participante> listar() {
        return repository.findAll();
    }

    public boolean existe(long id) {
        return repository.existsById(id);
    }

    public boolean vazio() {
        return repository.findAll().isEmpty();
    }
}

