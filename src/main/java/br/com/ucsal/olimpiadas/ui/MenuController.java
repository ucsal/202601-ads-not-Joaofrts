package br.com.ucsal.olimpiadas.ui;

import br.com.ucsal.olimpiadas.Questao;
import br.com.ucsal.olimpiadas.service.NotaService;
import br.com.ucsal.olimpiadas.service.ParticipanteService;
import br.com.ucsal.olimpiadas.service.ProvaService;
import br.com.ucsal.olimpiadas.service.QuestaoService;
import br.com.ucsal.olimpiadas.service.TentativaService;

public class MenuController {
    private final UserIO io;
    private final ParticipanteService participanteService;
    private final ProvaService provaService;
    private final QuestaoService questaoService;
    private final TentativaService tentativaService;
    private final NotaService notaService;
    private final FenBoardPrinter fenBoardPrinter;

    public MenuController(
            UserIO io,
            ParticipanteService participanteService,
            ProvaService provaService,
            QuestaoService questaoService,
            TentativaService tentativaService,
            NotaService notaService,
            FenBoardPrinter fenBoardPrinter) {
        this.io = io;
        this.participanteService = participanteService;
        this.provaService = provaService;
        this.questaoService = questaoService;
        this.tentativaService = tentativaService;
        this.notaService = notaService;
        this.fenBoardPrinter = fenBoardPrinter;
    }

    public void run() {
        while (true) {
            io.println("\n=== OLIMPIADA DE QUESTOES (V1) ===");
            io.println("1) Cadastrar participante");
            io.println("2) Cadastrar prova");
            io.println("3) Cadastrar questao (A-E) em uma prova");
            io.println("4) Aplicar prova (selecionar participante + prova)");
            io.println("5) Listar tentativas (resumo)");
            io.println("0) Sair");
            io.print("> ");

            String option = io.readLine();
            switch (option) {
                case "1" -> cadastrarParticipante();
                case "2" -> cadastrarProva();
                case "3" -> cadastrarQuestao();
                case "4" -> aplicarProva();
                case "5" -> listarTentativas();
                case "0" -> {
                    io.println("tchau");
                    return;
                }
                default -> io.println("opcao invalida");
            }
        }
    }

    private void cadastrarParticipante() {
        io.print("Nome: ");
        String nome = io.readLine();

        io.print("Email (opcional): ");
        String email = io.readLine();

        var participante = participanteService.cadastrar(nome, email);
        if (participante.isEmpty()) {
            io.println("nome invalido");
            return;
        }

        io.println("Participante cadastrado: " + participante.get().getId());
    }

    private void cadastrarProva() {
        io.print("Titulo da prova: ");
        String titulo = io.readLine();

        var prova = provaService.cadastrar(titulo);
        if (prova.isEmpty()) {
            io.println("titulo invalido");
            return;
        }

        io.println("Prova criada: " + prova.get().getId());
    }

    private void cadastrarQuestao() {
        if (provaService.vazio()) {
            io.println("nao ha provas cadastradas");
            return;
        }

        Long provaId = escolherProva();
        if (provaId == null) {
            return;
        }

        io.println("Enunciado:");
        String enunciado = io.readLine();

        String[] alternativas = new String[5];
        for (int i = 0; i < 5; i++) {
            char letra = (char) ('A' + i);
            io.print("Alternativa " + letra + ": ");
            alternativas[i] = letra + ") " + io.readLine();
        }

        io.print("Alternativa correta (A-E): ");
        char correta;
        try {
            correta = Questao.normalizar(io.readLine().trim().charAt(0));
        } catch (Exception e) {
            io.println("alternativa invalida");
            return;
        }

        var questao = questaoService.cadastrar(provaId, enunciado, alternativas, correta);
        if (questao.isEmpty()) {
            io.println("dados da questao invalidos");
            return;
        }

        io.println("Questao cadastrada: " + questao.get().getId() + " (na prova " + provaId + ")");
    }

    private void aplicarProva() {
        if (participanteService.vazio()) {
            io.println("cadastre participantes primeiro");
            return;
        }

        if (provaService.vazio()) {
            io.println("cadastre provas primeiro");
            return;
        }

        Long participanteId = escolherParticipante();
        if (participanteId == null) {
            return;
        }

        Long provaId = escolherProva();
        if (provaId == null) {
            return;
        }

        var questoesDaProva = questaoService.listarPorProva(provaId);
        if (questoesDaProva.isEmpty()) {
            io.println("esta prova nao possui questoes cadastradas");
            return;
        }

        var tentativa = tentativaService.iniciar(participanteId, provaId);

        io.println("\n--- Inicio da Prova ---");
        for (var questao : questoesDaProva) {
            io.println("\nQuestao #" + questao.getId());
            io.println(questao.getEnunciado());

            if (questao.getFenInicial() != null && !questao.getFenInicial().isBlank()) {
                io.println("Posicao inicial:");
                fenBoardPrinter.print(questao.getFenInicial(), io);
            }

            for (var alternativa : questao.getAlternativas()) {
                io.println(alternativa);
            }

            io.print("Sua resposta (A-E): ");
            char marcada;
            try {
                marcada = Questao.normalizar(io.readLine().trim().charAt(0));
            } catch (Exception e) {
                io.println("resposta invalida (marcando como errada)");
                marcada = 'X';
            }

            tentativaService.responder(tentativa, questao, marcada);
        }

        tentativaService.salvar(tentativa);

        int nota = notaService.calcularNota(tentativa);
        io.println("\n--- Fim da Prova ---");
        io.println("Nota (acertos): " + nota + " / " + tentativa.getRespostas().size());
    }

    private void listarTentativas() {
        io.println("\n--- Tentativas ---");
        for (var tentativa : tentativaService.listar()) {
            io.println(String.format(
                    "#%d | participante=%d | prova=%d | nota=%d/%d",
                    tentativa.getId(),
                    tentativa.getParticipanteId(),
                    tentativa.getProvaId(),
                    notaService.calcularNota(tentativa),
                    tentativa.getRespostas().size()));
        }
    }

    private Long escolherParticipante() {
        io.println("\nParticipantes:");
        for (var participante : participanteService.listar()) {
            io.println(String.format("  %d) %s", participante.getId(), participante.getNome()));
        }
        io.print("Escolha o id do participante: ");

        try {
            long id = Long.parseLong(io.readLine());
            if (!participanteService.existe(id)) {
                io.println("id invalido");
                return null;
            }
            return id;
        } catch (Exception e) {
            io.println("entrada invalida");
            return null;
        }
    }

    private Long escolherProva() {
        io.println("\nProvas:");
        for (var prova : provaService.listar()) {
            io.println(String.format("  %d) %s", prova.getId(), prova.getTitulo()));
        }
        io.print("Escolha o id da prova: ");

        try {
            long id = Long.parseLong(io.readLine());
            if (!provaService.existe(id)) {
                io.println("id invalido");
                return null;
            }
            return id;
        } catch (Exception e) {
            io.println("entrada invalida");
            return null;
        }
    }
}

