package br.com.ucsal.olimpiadas.service;

import br.com.ucsal.olimpiadas.Questao;
import br.com.ucsal.olimpiadas.Resposta;
import br.com.ucsal.olimpiadas.Tentativa;
import br.com.ucsal.olimpiadas.repository.TentativaRepository;
import java.util.List;

public class TentativaService {
    private final TentativaRepository repository;

    public TentativaService(TentativaRepository repository) {
        this.repository = repository;
    }

    public Tentativa iniciar(long participanteId, long provaId) {
        Tentativa tentativa = new Tentativa();
        tentativa.setId(repository.nextId());
        tentativa.setParticipanteId(participanteId);
        tentativa.setProvaId(provaId);
        return tentativa;
    }

    public void responder(Tentativa tentativa, Questao questao, char marcada) {
        Resposta resposta = new Resposta();
        resposta.setQuestaoId(questao.getId());
        resposta.setAlternativaMarcada(marcada);
        resposta.setCorreta(questao.isRespostaCorreta(marcada));
        tentativa.getRespostas().add(resposta);
    }

    public void salvar(Tentativa tentativa) {
        repository.save(tentativa);
    }

    public List<Tentativa> listar() {
        return repository.findAll();
    }
}

