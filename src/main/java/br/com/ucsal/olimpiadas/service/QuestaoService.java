package br.com.ucsal.olimpiadas.service;

import br.com.ucsal.olimpiadas.Questao;
import br.com.ucsal.olimpiadas.repository.QuestaoRepository;
import java.util.List;
import java.util.Optional;

public class QuestaoService {
    private final QuestaoRepository repository;

    public QuestaoService(QuestaoRepository repository) {
        this.repository = repository;
    }

    public Optional<Questao> cadastrar(long provaId, String enunciado, String[] alternativas, char correta) {
        try {
            Questao questao = new Questao();
            questao.setId(repository.nextId());
            questao.setProvaId(provaId);
            questao.setEnunciado(enunciado);
            questao.setAlternativas(alternativas);
            questao.setAlternativaCorreta(correta);
            repository.save(questao);
            return Optional.of(questao);
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    public void salvar(Questao questao) {
        if (questao.getId() == 0) {
            questao.setId(repository.nextId());
        }
        repository.save(questao);
    }

    public List<Questao> listarPorProva(long provaId) {
        return repository.findByProvaId(provaId);
    }

    public boolean vazio() {
        return repository.findAll().isEmpty();
    }
}

