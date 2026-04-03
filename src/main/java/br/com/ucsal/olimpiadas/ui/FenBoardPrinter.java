package br.com.ucsal.olimpiadas.ui;

public class FenBoardPrinter {
    public void print(String fen, UserIO io) {
        String[] partes = fen.split(" ");
        String[] ranks = partes[0].split("/");

        io.println("");
        io.println("    a b c d e f g h");
        io.println("   -----------------");

        for (int r = 0; r < 8; r++) {
            String rank = ranks[r];
            StringBuilder linha = new StringBuilder();
            linha.append(8 - r).append(" | ");

            for (char c : rank.toCharArray()) {
                if (Character.isDigit(c)) {
                    int vazios = c - '0';
                    for (int i = 0; i < vazios; i++) {
                        linha.append(". ");
                    }
                } else {
                    linha.append(c).append(' ');
                }
            }

            linha.append("| ").append(8 - r);
            io.println(linha.toString());
        }

        io.println("   -----------------");
        io.println("    a b c d e f g h");
        io.println("");
    }
}

