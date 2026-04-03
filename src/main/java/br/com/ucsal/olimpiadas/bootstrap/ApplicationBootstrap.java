package br.com.ucsal.olimpiadas.bootstrap;

import br.com.ucsal.olimpiadas.repository.ParticipanteRepository;
import br.com.ucsal.olimpiadas.repository.ProvaRepository;
import br.com.ucsal.olimpiadas.repository.QuestaoRepository;
import br.com.ucsal.olimpiadas.repository.TentativaRepository;
import br.com.ucsal.olimpiadas.repository.memory.InMemoryParticipanteRepository;
import br.com.ucsal.olimpiadas.repository.memory.InMemoryProvaRepository;
import br.com.ucsal.olimpiadas.repository.memory.InMemoryQuestaoRepository;
import br.com.ucsal.olimpiadas.repository.memory.InMemoryTentativaRepository;
import br.com.ucsal.olimpiadas.seed.DataSeeder;
import br.com.ucsal.olimpiadas.service.NotaService;
import br.com.ucsal.olimpiadas.service.ParticipanteService;
import br.com.ucsal.olimpiadas.service.ProvaService;
import br.com.ucsal.olimpiadas.service.QuestaoService;
import br.com.ucsal.olimpiadas.service.TentativaService;
import br.com.ucsal.olimpiadas.ui.ConsoleUserIO;
import br.com.ucsal.olimpiadas.ui.FenBoardPrinter;
import br.com.ucsal.olimpiadas.ui.MenuController;
import br.com.ucsal.olimpiadas.ui.UserIO;
import java.util.Scanner;

public class ApplicationBootstrap {
    private final MenuController menuController;

    public ApplicationBootstrap(MenuController menuController) {
        this.menuController = menuController;
    }

    public static ApplicationBootstrap createDefault() {
        ParticipanteRepository participanteRepository = new InMemoryParticipanteRepository();
        ProvaRepository provaRepository = new InMemoryProvaRepository();
        QuestaoRepository questaoRepository = new InMemoryQuestaoRepository();
        TentativaRepository tentativaRepository = new InMemoryTentativaRepository();

        ParticipanteService participanteService = new ParticipanteService(participanteRepository);
        ProvaService provaService = new ProvaService(provaRepository);
        QuestaoService questaoService = new QuestaoService(questaoRepository);
        TentativaService tentativaService = new TentativaService(tentativaRepository);
        NotaService notaService = new NotaService();

        UserIO io = new ConsoleUserIO(new Scanner(System.in));
        MenuController menuController = new MenuController(
                io,
                participanteService,
                provaService,
                questaoService,
                tentativaService,
                notaService,
                new FenBoardPrinter());

        new DataSeeder(provaService, questaoService).seed();
        return new ApplicationBootstrap(menuController);
    }

    public void run() {
        menuController.run();
    }
}

