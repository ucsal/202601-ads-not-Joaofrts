package br.com.ucsal.olimpiadas;

import br.com.ucsal.olimpiadas.bootstrap.ApplicationBootstrap;
import br.com.ucsal.olimpiadas.service.NotaService;

public class App {

    private static final NotaService NOTA_SERVICE = new NotaService();

    public static void main(String[] args) {
        ApplicationBootstrap.createDefault().run();
    }

    public static int calcularNota(Tentativa tentativa) {
        return NOTA_SERVICE.calcularNota(tentativa);
    }
}