package br.com.ucsal.olimpiadas;

import br.com.ucsal.olimpiadas.repository.memory.InMemoryParticipanteRepository;
import br.com.ucsal.olimpiadas.repository.memory.InMemoryProvaRepository;
import br.com.ucsal.olimpiadas.repository.memory.InMemoryQuestaoRepository;
import br.com.ucsal.olimpiadas.repository.memory.InMemoryTentativaRepository;
import br.com.ucsal.olimpiadas.service.NotaService;
import br.com.ucsal.olimpiadas.service.ParticipanteService;
import br.com.ucsal.olimpiadas.service.ProvaService;
import br.com.ucsal.olimpiadas.service.QuestaoService;
import br.com.ucsal.olimpiadas.service.TentativaService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExemploTest {

    @Test
    void deveCalcularNotaComBaseNasRespostasCorretas() {
        Tentativa tentativa = new Tentativa();

        Resposta r1 = new Resposta();
        r1.setCorreta(true);
        tentativa.getRespostas().add(r1);

        Resposta r2 = new Resposta();
        r2.setCorreta(false);
        tentativa.getRespostas().add(r2);

        Resposta r3 = new Resposta();
        r3.setCorreta(true);
        tentativa.getRespostas().add(r3);

        int nota = new NotaService().calcularNota(tentativa);
        assertEquals(2, nota);
        assertEquals(2, App.calcularNota(tentativa));
    }

    @Test
    void deveCadastrarParticipanteEProvaComIdsSequenciais() {
        ParticipanteService participanteService = new ParticipanteService(new InMemoryParticipanteRepository());
        ProvaService provaService = new ProvaService(new InMemoryProvaRepository());

        var p1 = participanteService.cadastrar("Ana", "ana@email.com");
        var p2 = participanteService.cadastrar("Bruno", "");
        var prova = provaService.cadastrar("Prova A");

        assertTrue(p1.isPresent());
        assertTrue(p2.isPresent());
        assertTrue(prova.isPresent());
        assertEquals(1L, p1.get().getId());
        assertEquals(2L, p2.get().getId());
        assertEquals(1L, prova.get().getId());
    }

    @Test
    void deveRegistrarTentativaComRespostaCorretaEIncorreta() {
        QuestaoService questaoService = new QuestaoService(new InMemoryQuestaoRepository());
        TentativaService tentativaService = new TentativaService(new InMemoryTentativaRepository());

        var questao = questaoService.cadastrar(
                1L,
                "Qual alternativa correta?",
                new String[] {"A) 1", "B) 2", "C) 3", "D) 4", "E) 5"},
                'C');

        assertTrue(questao.isPresent());

        Tentativa tentativa = tentativaService.iniciar(1L, 1L);
        tentativaService.responder(tentativa, questao.get(), 'C');
        tentativaService.responder(tentativa, questao.get(), 'A');
        tentativaService.salvar(tentativa);

        assertEquals(2, tentativa.getRespostas().size());
        assertTrue(tentativa.getRespostas().get(0).isCorreta());
        assertFalse(tentativa.getRespostas().get(1).isCorreta());
    }
}
