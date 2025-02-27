package com.sudoku;

import com.sudoku.model.Board;

import java.util.Scanner;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toMap;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);
    private static Board board;
    private final static int BOARD_LIMIT  = 9;


    public static void main(String[] args) {

        final var positions = Stream.of(args)
                .collect(toMap(
                        k->k.split(";")[0],
                        v->v.split(";")[0]
                ));

        var option = -1;

        while (true){
            System.out.println("Selecione uma das opções a seguir");
            System.out.println("1 - Iniciar um novo Jogo");
            System.out.println("2 - Colocar um novo número");
            System.out.println("3 - Remover um número");
            System.out.println("4 - Visualizar jogo atual");
            System.out.println("5 - Verificar status do jogo");
            System.out.println("6 - limpar jogo");
            System.out.println("7 - Finalizar jogo");
            System.out.println("8 - Sair");

            option = scanner.nextInt();

            switch (option){
                case 1 -> startGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 8 -> System.exit(0);
                default -> System.out.println("Opção inválida, selecione uma das opções do menu");
            }

        }

    }
}