package br.com.ucsal.olimpiadas.service;

import br.com.ucsal.olimpiadas.Tentativa;

public class NotaService {
    public int calcularNota(Tentativa tentativa) {
        int acertos = 0;
        for (var resposta : tentativa.getRespostas()) {
            if (resposta.isCorreta()) {
                acertos++;
            }
        }
        return acertos;
    }
}

