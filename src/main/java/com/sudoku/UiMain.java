package com.sudoku;

import com.sudoku.ui.custom.frame.MainFrame;
import com.sudoku.ui.custom.panel.MainPanel;
import com.sudoku.ui.custom.screen.MainScreen;

import javax.swing.*;
import java.awt.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class UiMain {
    public static void main(String[] args){
        final var gameConfig = Stream.of(args)
                .collect(toMap(
                        k->k.split(";")[0],
                        v->v.split(";")[0]
                ));

        var mainScreen = new MainScreen(gameConfig);
        mainScreen.buildMainScreen();



    }
}
