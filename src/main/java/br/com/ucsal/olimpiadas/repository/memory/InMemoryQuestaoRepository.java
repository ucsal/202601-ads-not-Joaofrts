package br.com.ucsal.olimpiadas.repository.memory;

import br.com.ucsal.olimpiadas.Questao;
import br.com.ucsal.olimpiadas.repository.QuestaoRepository;
import java.util.ArrayList;
import java.util.List;

public class InMemoryQuestaoRepository implements QuestaoRepository {
    private long nextId = 1;
    private final List<Questao> questoes = new ArrayList<>();

    @Override
    public long nextId() {
        return nextId++;
    }

    @Override
    public void save(Questao questao) {
        questoes.add(questao);
    }

    @Override
    public List<Questao> findAll() {
        return List.copyOf(questoes);
    }

    @Override
    public List<Questao> findByProvaId(long provaId) {
        return questoes.stream().filter(q -> q.getProvaId() == provaId).toList();
    }
}

