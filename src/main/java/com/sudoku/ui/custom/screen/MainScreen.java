package com.sudoku.ui.custom.screen;

import com.sudoku.model.Space;
import com.sudoku.service.EventEnum;
import com.sudoku.service.NotifierService;
import com.sudoku.ui.custom.button.CheckGameStatusButton;
import com.sudoku.ui.custom.button.FinnishGameButton;
import com.sudoku.ui.custom.button.ResetButton;
import com.sudoku.ui.custom.frame.MainFrame;
import com.sudoku.ui.custom.input.NumberText;
import com.sudoku.ui.custom.panel.MainPanel;
import com.sudoku.ui.custom.panel.SudokuSector;
import com.sudoku.util.BoardService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;


import javax.swing.JPanel;
import java.util.List;

import static com.sudoku.service.EventEnum.CLEAR_SPACE;

public class MainScreen {

    private final static Dimension dimension = new Dimension(600, 600);

    private final BoardService boardService;
    private final NotifierService notifierService;

    private JButton checkGameStatusButton;
    private JButton finnishGameButton;
    private JButton resetButton;


    public MainScreen(final Map<String, String> gameConfig) {

        this.boardService = new BoardService(gameConfig);
        this.notifierService = new NotifierService();
    }

    public void buildMainScreen(){
        JPanel mainpanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainpanel);
        for (int r = 0; r < 9; r+=3) {
            var endRow = r+2;
            for (int c = 0; c < 9; c+=3) {
                var endCol = c+2;
                var spaces = getSpacesFromSector(boardService.getSpaces(), c, endCol, r, endRow);

                mainpanel.add(generateSection(spaces));
            }

        }

        addResetButton(mainpanel);
        addCheckGameStatusButton(mainpanel);
        addFinnishGameButton(mainpanel);

        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private List<Space> getSpacesFromSector(final List<List<Space>> spaces, final int initCol, final int endCol, final int initRow, final int endRow){
        List<Space> spaceSector = new ArrayList<>();

        for (int r = initRow; r <= endRow ; r++) {
            for (int c = initCol; c <= endCol ; c++) {
                spaceSector.add(spaces.get(c).get(r));
            }
        }

        return spaceSector;
    }

    private JPanel generateSection(final List<Space> spaces){
        List<NumberText> fields = new ArrayList<>(spaces.stream()
                .map(NumberText::new).toList());
        fields.forEach(t->notifierService.subscriber(CLEAR_SPACE, t));

        return new SudokuSector(fields);
    }

    private void addResetButton(final JPanel mainPanel){
        resetButton = new ResetButton(e-> {
            var dialogResult = JOptionPane.showConfirmDialog(
                    null,
                    "Deseja realmente reinicar o jogo??",
                    "Limpar o jogo",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                    );
            if(dialogResult == 0){
                boardService.reset();
                notifierService.notify(CLEAR_SPACE);
            }
        });
        mainPanel.add(resetButton);
    }

    private void addCheckGameStatusButton(final JPanel mainPanel){
        checkGameStatusButton = new CheckGameStatusButton(e -> {
            var hasErrors = boardService.hasErros();
            var gameStatusEnum = boardService.getStats();
            var message = switch (gameStatusEnum){
                case NON_STARTED -> "O jogo nao foi iniciado";
                case INCOMPLETE -> "O jogo esta incompleto";
                case COMPLETE -> "O jogo esta completo";
            };

            message += hasErrors ? " e contem erros" : " e não contem erros";
            JOptionPane.showMessageDialog(null, message);

        });
        mainPanel.add(checkGameStatusButton);
    }

    private void addFinnishGameButton(final JPanel mainPanel){
        finnishGameButton = new FinnishGameButton(e->{
            if (boardService.gameIsFinnished()){
                JOptionPane.showMessageDialog(null, "Parabens, voce concluiu o jogo");

                resetButton.setEnabled(false);
                checkGameStatusButton.setEnabled(false);
                finnishGameButton.setEnabled(false);
            }else{
                JOptionPane.showMessageDialog(null, "Seu jogo tem alguma inconsistencia, ajuste e tente novamente");
            }
        });

        mainPanel.add(finnishGameButton);
    }


}
