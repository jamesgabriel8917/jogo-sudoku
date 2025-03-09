package com.sudoku.ui.custom.button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class FinnishGameButton extends JButton {

    public FinnishGameButton(final ActionListener actionListener) {
        this.setText("Terminar jogo");
        this.addActionListener(actionListener);
    }
}
