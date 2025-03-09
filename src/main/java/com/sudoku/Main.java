package com.sudoku;

import com.sudoku.model.Board;
import com.sudoku.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

import static com.sudoku.util.BoardService.BOARD_LIMIT;
import static com.sudoku.util.BoardTemplate.BOARD_TEMPLATE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);
    private static Board board;


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

    private static void finishGame() {
        if(isNull(board)){
            System.out.println("O jogo não foi iniciado");
            return;
        }

        if (board.gameIsFinished()){
            System.out.println("Parabens, voce conmcluiu o jogo");
            showCurrentGame();
            board = null;
        }else if (board.hasErrors()){
            System.out.println("Seu jogo tem erros, verifique");
            showCurrentGame();
        }else {
            System.out.println("O jogo esta incompleto");
            showCurrentGame();
        }


    }

    private static void clearGame() {
        if(isNull(board)){
            System.out.println("O jogo não foi iniciado");
            return;
        }

        board.reset();



    }

    private static void showGameStatus() {
        if(isNull(board)){
            System.out.println("O jogo não foi iniciado");
            return;
        }

        System.out.printf("Status do jogo %s \n", board.getStatus().getLabel());

        if (board.hasErrors()){
            System.out.println("O jogo contem erros");
        }else {
            System.out.println("O jogo nao contem erros");
        }


    }

    private static void showCurrentGame() {
        if(isNull(board)){
            System.out.println("O jogo não foi iniciado");
            return;
        }

        var args = new Object[81];
        var argPos = 0;
        for (int i = 0; i < BOARD_LIMIT; i++) {
            for (var col: board.getSpaces()) {
                args[argPos ++] = " " + ((isNull(col.get(i).getActual())) ? " " : col.get(i).getActual());
            }
        }

        System.out.println("Seu jogo se encontra desta forma");
        System.out.printf((BOARD_TEMPLATE) + "%n", args);

    }

    private static void removeNumber() {
        if(isNull(board)){
            System.out.println("O jogo não foi iniciado");
            return;
        }

        System.out.println("Informe a coluna em que o numero sera removido");
        var col =  runUntilGetValidNumber(0,8);
        System.out.println("Informe a linha em que o numero sera removido");
        var row =  runUntilGetValidNumber(0,8);

        if(!board.clearValue(col, row)){
            System.out.println("A posicao tem um valor fixo ");
        }


    }

    private static void inputNumber() {
        if(isNull(board)){
            System.out.println("O jogo não foi iniciado");
            return;
        }

        System.out.println("Informe a coluna em que o numero sera inserido");
        var col =  runUntilGetValidNumber(0,8);
        System.out.println("Informe a linha em que o numero sera inserido");
        var row =  runUntilGetValidNumber(0,8);
        System.out.println("informe o nuemro que vai nessa posicao");
        var value  = runUntilGetValidNumber(1,9);

        if(!board.changeValue(col, row, value)){
            System.out.println("A posicao tem um valor fixo ");
        }

    }

    private static void startGame(Map<String, String> positions) {
        if(nonNull(board)){
            System.out.println("O jogo ja foi iniciado");
            return;
        }

        List<List<Space>> spaces = new ArrayList<>();

        for(int i = 0 ; i<BOARD_LIMIT; i++){
            spaces.add(new ArrayList<>());
            for (int j = 0; j<BOARD_LIMIT; j++){
                var positionConfig = positions.get("%s, %s".formatted(i, j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
                

            }
        }
            board = new Board(spaces);
            System.out.println("Jogo criado");

    }

    private static int runUntilGetValidNumber(final int min, final int max){
        var current  = scanner.nextInt();

        while(current < min || current > max){
            System.out.printf("Iforme um numero entre %s e %s \n", min, max);
            current = scanner.nextInt();
        }

        return current;
    }

}