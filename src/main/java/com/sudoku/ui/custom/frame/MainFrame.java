package com.sudoku.ui.custom.frame;

import com.sudoku.ui.custom.panel.MainPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(final Dimension dimension, final JPanel MainPanel){
        super("Sudoku");
        this.setPreferredSize(dimension);
        this.setSize(dimension);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.add(MainPanel);


    }

}
