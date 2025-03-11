package com.sudoku.ui.custom.screen;

import com.sudoku.ui.custom.button.CheckGameStatusButton;
import com.sudoku.ui.custom.button.FinnishGameButton;
import com.sudoku.ui.custom.button.ResetButton;
import com.sudoku.ui.custom.frame.MainFrame;
import com.sudoku.ui.custom.panel.MainPanel;
import com.sudoku.util.BoardService;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MainScreen {

    private final static Dimension dimension = new Dimension(600, 600);

    private final BoardService boardService;


    private JButton checkGameStatusButton;
    private JButton finnishGameButton;
    private JButton resetButton;


    public MainScreen(final Map<String, String> gameConfig) {
        this.boardService = new BoardService(gameConfig);
    }

    public void buildMainScreen(){
        JPanel mainpanel = new MainPanel(dimension);

        JFrame mainFrame = new MainFrame(dimension, mainpanel);
        addResetButton(mainpanel);
        addCheckGameStatusButton(mainpanel);
        addFinnishGameButton(mainpanel);

        mainFrame.revalidate();
        mainFrame.repaint();
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
