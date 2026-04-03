package br.com.ucsal.olimpiadas.seed;

import br.com.ucsal.olimpiadas.Questao;
import br.com.ucsal.olimpiadas.service.ProvaService;
import br.com.ucsal.olimpiadas.service.QuestaoService;

public class DataSeeder {
    private final ProvaService provaService;
    private final QuestaoService questaoService;

    public DataSeeder(ProvaService provaService, QuestaoService questaoService) {
        this.provaService = provaService;
        this.questaoService = questaoService;
    }

    public void seed() {
        var prova = provaService.cadastrar("Olimpiada 2026 - Nivel 1 - Prova A");
        if (prova.isEmpty()) {
            return;
        }

        Questao q1 = new Questao();
        q1.setProvaId(prova.get().getId());
        q1.setEnunciado("""
                Questao 1 - Mate em 1.
                E a vez das brancas.
                Encontre o lance que da mate imediatamente.
                """);
        q1.setFenInicial("6k1/5ppp/8/8/8/7Q/6PP/6K1 w - - 0 1");
        q1.setAlternativas(new String[] {"A) Qh7#", "B) Qf5#", "C) Qc8#", "D) Qh8#", "E) Qe6#"});
        q1.setAlternativaCorreta('C');

        questaoService.salvar(q1);
    }
}

