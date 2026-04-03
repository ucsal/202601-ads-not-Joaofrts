package br.com.ucsal.olimpiadas.ui;

import java.util.Scanner;

public class ConsoleUserIO implements UserIO {
    private final Scanner scanner;

    public ConsoleUserIO(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void print(String value) {
        System.out.print(value);
    }

    @Override
    public void println(String value) {
        System.out.println(value);
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}

