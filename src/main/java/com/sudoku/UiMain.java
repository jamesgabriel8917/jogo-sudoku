package com.sudoku;

import com.sudoku.ui.custom.frame.MainFrame;
import com.sudoku.ui.custom.panel.MainPanel;

import javax.swing.*;
import java.awt.*;

public class UiMain {
    public static void main(String[] args){
        var dimension = new Dimension(600, 600);
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();



    }
}
